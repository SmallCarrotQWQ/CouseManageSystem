package com.example.coursemanagesystem.service.impl;

import com.example.coursemanagesystem.entity.*;
import com.example.coursemanagesystem.mapper.*;
import com.example.coursemanagesystem.service.AiScheduleService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AiScheduleServiceImpl implements AiScheduleService {

    private static final String API_URL = "https://api.coze.cn/open_api/v2/chat";
    private static final String TOKEN = "pat_ngHdQ5VYsPsUodAYk2X4oeB2p5zrzRfCKJkTGJAtWXCha4LGqQMsmiWAqPnxUMh7";
    private static final String BOT_ID = "7473104170443325480";
    private static final String USER_ID = "SmallCarrot";

    @Autowired
    private ScheduleTaskMapper scheduleTaskMapper;

    @Autowired
    private ScheduleResultMapper scheduleResultMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private ClassroomMapper classroomMapper;

    @Override
    @Transactional
    public boolean autoSchedule(String scheduleTaskId) {
        try {
            int deletedCount = scheduleResultMapper.deleteByScheduleId(scheduleTaskId);
            System.out.println("已删除 " + deletedCount + " 条旧排课记录");

            List<ScheduleTask> taskList = scheduleTaskMapper.getScheduleTasksByScheduleId(scheduleTaskId);
            if (taskList == null || taskList.isEmpty()) {
                throw new IllegalArgumentException("排课任务不存在，ID: " + scheduleTaskId);
            }

            List<Teacher> teachers = teacherMapper.getAllTeachers();
            List<Classroom> classrooms = classroomMapper.getAllClassrooms();

            String query = buildAiQuery(scheduleTaskId, taskList, teachers, classrooms);

            JSONArray resultArray = callAiSchedulingService(query);

            processSchedulingResults(scheduleTaskId, taskList, resultArray);

            int deletedTaskCount = scheduleTaskMapper.deleteByScheduleId(scheduleTaskId);
            System.out.println("已删除 " + deletedTaskCount + " 条排课任务记录");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("排课失败: " + e.getMessage(), e);
        }
    }

    private String buildAiQuery(String scheduleTaskId, List<ScheduleTask> taskList,
                                List<Teacher> teachers, List<Classroom> classrooms) {
        StringBuilder queryBuilder = new StringBuilder("请根据以下信息安排课程时间和教室：\n");
        queryBuilder.append("排课任务ID：").append(scheduleTaskId).append("\n\n");

        queryBuilder.append("课程信息：\n");
        for (ScheduleTask task : taskList) {
            queryBuilder.append("课程ID：").append(task.getCourseId()).append("\n")
                    .append("课程名称：").append(task.getCourseName()).append("\n")
                    .append("班级：").append(task.getClassName()).append("\n")
                    .append("总课时：").append(task.getTotalHours()).append("\n")
                    .append("上课时间：").append(task.getCourseTime()).append("\n")
                    .append("---\n");
        }

        queryBuilder.append("\n教师信息：\n");
        for (Teacher teacher : teachers) {
            queryBuilder.append("教师ID：").append(teacher.getTeacherId()).append("\n")
                    .append("教师姓名：").append(teacher.getTeacherName()).append("\n")
                    .append("教授课程：").append(teacher.getTeacherCourse()).append("\n")
                    .append("每周最大课时：").append(teacher.getMaxHoursPerWeek()).append("\n")
                    .append("---\n");
        }

        queryBuilder.append("\n教室信息：\n");
        for (Classroom classroom : classrooms) {
            queryBuilder.append("教室ID：").append(classroom.getClassroomId()).append("\n")
                    .append("最大容量：").append(classroom.getMaxStudents()).append("\n")
                    .append("设备功能：").append(classroom.getFunctions()).append("\n")
                    .append("---\n");
        }

        return queryBuilder.toString();
    }

    private JSONArray callAiSchedulingService(String query) throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("bot_id", BOT_ID);
        requestBody.put("user", USER_ID);
        requestBody.put("query", query);
        requestBody.put("stream", false);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("AI接口请求失败: " + response.body());
        }

        JSONObject jsonResponse = new JSONObject(response.body());
        String content = jsonResponse.getJSONArray("messages")
                .getJSONObject(0)
                .getString("content");

        int startIndex = content.indexOf('[');
        int endIndex = content.lastIndexOf(']') + 1;
        if (startIndex == -1 || endIndex <= startIndex) {
            throw new RuntimeException("返回内容不是合法的 JSON 数组格式：" + content);
        }

        String jsonPart = content.substring(startIndex, endIndex);
        if (jsonPart.trim().startsWith("{")) {
            JSONObject errObj = new JSONObject(jsonPart);
            if (errObj.optBoolean("error", false)) {
                throw new RuntimeException("AI返回错误：" + errObj.getString("message"));
            }
        }

        return new JSONArray(jsonPart);
    }

    private void processSchedulingResults(String scheduleTaskId, List<ScheduleTask> taskList, JSONArray resultArray) {
        for (int i = 0; i < resultArray.length(); i++) {
            JSONObject obj = resultArray.getJSONObject(i);
            String courseId = obj.getString("courseId");

            ScheduleTask task = taskList.stream()
                    .filter(t -> t.getCourseId().equals(courseId))
                    .findFirst()
                    .orElse(null);
            if (task == null) continue;

            ScheduleResult result = new ScheduleResult();
            result.setScheduleId(scheduleTaskId);
            result.setCourseId(courseId);
            result.setCourseName(task.getCourseName());
            result.setClassName(task.getClassName());
            result.setCourseTime(obj.getString("courseTime"));
            result.setTotalHours(task.getTotalHours());
            result.setStartTime(LocalDateTime.parse(obj.getString("startTime")));
            result.setEndTime(LocalDateTime.parse(obj.getString("endTime")));
            result.setLocation(obj.getString("location"));

            scheduleResultMapper.deleteByPrimaryKey(scheduleTaskId, courseId);
            scheduleResultMapper.insert(result);
        }
    }
}