package com.example.coursemanagesystem.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONArray; // 新增导入
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.coursemanagesystem.entity.ScheduleResult;
import com.example.coursemanagesystem.entity.ScheduleTask;
import com.example.coursemanagesystem.mapper.ScheduleResultMapper;
import com.example.coursemanagesystem.mapper.ScheduleTaskMapper;
import com.example.coursemanagesystem.service.AiScheduleService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AiScheduleServiceImpl implements AiScheduleService {

    private static final String API_URL = "https://api.coze.cn/open_api/v2/chat";
    private static final String TOKEN = "pat_ngHdQ5VYsPsUodAYk2X4oeB2p5zrzRfCKJkTGJAtWXCha4LGqQMsmiWAqPnxUMh7";
    private static final String BOT_ID = "7473104170443325480";

    @Autowired
    private ScheduleTaskMapper scheduleTaskMapper;

    @Autowired
    private ScheduleResultMapper scheduleResultMapper;

    @Override
    public boolean autoSchedule(String scheduleTaskId) {
        // 获取多个任务
        List<ScheduleTask> tasks = scheduleTaskMapper.getScheduleTasksByScheduleId(scheduleTaskId);
        if (tasks.isEmpty()) {
            throw new IllegalArgumentException("排课任务不存在，ID: " + scheduleTaskId);
        }

        OkHttpClient client = new OkHttpClient();

        for (ScheduleTask scheduleTask : tasks) {
            // 构造 prompt
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", "请根据以下课程信息安排时间和教室：" +
                    "\n课程名称：" + scheduleTask.getCourseName() +
                    "\n班级：" + scheduleTask.getClassName() +
                    "\n总课时：" + scheduleTask.getTotalHours() +
                    "\n上课时间：" + scheduleTask.getCourseTime());

            JSONObject messagesObj = new JSONObject();
            messagesObj.put("messages", new JSONObject[]{userMessage});
            messagesObj.put("bot_id", BOT_ID);
            messagesObj.put("user", "SmallCarrot");

            RequestBody requestBody = RequestBody.create(
                    messagesObj.toJSONString(),
                    MediaType.parse("application/json")
            );

            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(requestBody)
                    .addHeader("Authorization", "Bearer " + TOKEN)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) return false;

                String responseBody = response.body().string();
                JSONObject jsonResponse = JSONObject.parseObject(responseBody);
                String aiReply = jsonResponse.getJSONObject("messages").getString("content");

                JSONArray resultArray = JSONArray.parseArray(aiReply);
                for (Object obj : resultArray) {
                    JSONObject record = (JSONObject) obj;

                    ScheduleResult result = new ScheduleResult();
                    result.setScheduleId(scheduleTaskId);
                    result.setCourseId(scheduleTask.getCourseId());
                    result.setCourseName(scheduleTask.getCourseName());
                    result.setClassName(scheduleTask.getClassName());
                    result.setCourseTime(scheduleTask.getCourseTime());
                    result.setTotalHours(scheduleTask.getTotalHours());
                    result.setStartTime(LocalDateTime.parse(record.getString("startTime")));
                    result.setEndTime(LocalDateTime.parse(record.getString("endTime")));
                    result.setLocation(record.getString("location"));

                    scheduleResultMapper.insert(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

}
