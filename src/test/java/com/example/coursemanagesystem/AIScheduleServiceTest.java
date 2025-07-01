package com.example.coursemanagesystem;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

public class AIScheduleServiceTest {

    public static void main(String[] args) {
        // 测试1: 成功响应 - 直接返回数组格式
        System.out.println("=== 测试1: 成功响应(数组格式) ===");
        String successResponse1 = simulateSuccessResponseArray();
        testParseResponse(successResponse1);

        // 测试2: 成功响应 - 包装在data字段中
        System.out.println("\n=== 测试2: 成功响应(对象格式) ===");
        String successResponse2 = simulateSuccessResponseObject();
        testParseResponse(successResponse2);

        // 测试3: 错误响应
        System.out.println("\n=== 测试3: 错误响应 ===");
        String errorResponse = simulateErrorResponse();
        testParseResponse(errorResponse);

        // 测试4: 空响应
        System.out.println("\n=== 测试4: 空响应 ===");
        testParseResponse("");
    }

    private static void testParseResponse(String response) {
        System.out.println("原始响应:\n" + response);

        try {
            // 1. 先尝试解析为JSON对象
            try {
                JSONObject jsonResponse = JSONObject.parseObject(response);

                // 检查错误响应
                if (jsonResponse.containsKey("error") && jsonResponse.getBoolean("error")) {
                    System.out.println("[结果] 排课失败: " + jsonResponse.getString("message"));
                    return;
                }

                // 检查data字段
                if (jsonResponse.containsKey("data")) {
                    System.out.println("[解析] 检测到data字段");
                    Object data = jsonResponse.get("data");

                    if (data instanceof JSONArray) {
                        System.out.println("[解析] data是数组格式");
                        processResults((JSONArray) data);
                    } else {
                        System.out.println("[解析] data是对象格式，包装为数组");
                        JSONArray array = new JSONArray();
                        array.add(data);
                        processResults(array);
                    }
                    return;
                }

                // 其他对象格式处理
                System.out.println("[解析] 是JSON对象但无error/data字段");
                JSONArray array = new JSONArray();
                array.add(jsonResponse);
                processResults(array);

            } catch (Exception e) {
                System.out.println("[解析] 不是JSON对象格式，尝试解析为数组");
            }

            // 2. 尝试解析为JSON数组
            try {
                JSONArray results = JSONArray.parseArray(response);
                if (results != null) {
                    System.out.println("[解析] 是JSON数组格式");
                    processResults(results);
                    return;
                }
            } catch (Exception e) {
                System.out.println("[解析] 不是JSON数组格式");
            }

            // 3. 其他情况
            System.out.println("[结果] 无法识别的响应格式");

        } catch (Exception e) {
            System.out.println("[异常] 解析过程中发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void processResults(JSONArray results) {
        System.out.println("[结果] 成功解析排课结果，数量: " + results.size());
        for (int i = 0; i < results.size(); i++) {
            JSONObject result = results.getJSONObject(i);
            System.out.println("  [课程" + (i+1) + "]");
            System.out.println("    scheduleId: " + result.getString("scheduleId"));
            System.out.println("    courseId: " + result.getString("courseId"));
            System.out.println("    courseName: " + result.getString("courseName"));
            System.out.println("    className: " + result.getString("className"));
            System.out.println("    courseTime: " + result.getString("courseTime"));
            System.out.println("    totalHours: " + result.getInteger("totalHours"));
            System.out.println("    startTime: " + result.getString("startTime"));
            System.out.println("    endTime: " + result.getString("endTime"));
            System.out.println("    location: " + result.getString("location"));
            System.out.println();
        }
    }

    // 模拟成功响应 - 直接返回数组格式
    private static String simulateSuccessResponseArray() {
        JSONArray result = new JSONArray();

        // 根据AI提示词中的示例数据构建响应
        JSONObject course1 = new JSONObject();
        course1.put("scheduleId", "SC20250001");
        course1.put("courseId", "CS101");
        course1.put("courseName", "Java基础");
        course1.put("className", "软工23-1");
        course1.put("courseTime", "周一第1-2节");
        course1.put("totalHours", 32);
        course1.put("startTime", "2025-09-01T08:10:00");
        course1.put("endTime", "2025-09-01T09:35:00");
        course1.put("location", "教学楼101");
        result.add(course1);

        JSONObject course2 = new JSONObject();
        course2.put("scheduleId", "SC20250002");
        course2.put("courseId", "CS102");
        course2.put("courseName", "数据结构");
        course2.put("className", "软工23-1");
        course2.put("courseTime", "周三第5-6节");
        course2.put("totalHours", 32);
        course2.put("startTime", "2025-09-03T13:30:00");
        course2.put("endTime", "2025-09-03T14:55:00");
        course2.put("location", "教学楼201");
        result.add(course2);

        return result.toJSONString();
    }

    // 模拟成功响应 - 包装在data字段中
    private static String simulateSuccessResponseObject() {
        JSONObject response = new JSONObject();
        response.put("code", 0);
        response.put("msg", "");

        JSONObject data = new JSONObject();
        data.put("scheduleId", "SC20250003");
        data.put("courseId", "CS103");
        data.put("courseName", "操作系统");
        data.put("className", "软工23-2");
        data.put("courseTime", "周二第3-4节");
        data.put("totalHours", 32);
        data.put("startTime", "2025-09-02T09:45:00");
        data.put("endTime", "2025-09-02T11:10:00");
        data.put("location", "教学楼301");

        response.put("data", data);
        return response.toJSONString();
    }

    // 模拟错误响应
    private static String simulateErrorResponse() {
        JSONObject error = new JSONObject();
        error.put("error", true);
        error.put("message", "无法完成排课：张三的可用时间不足以安排Java基础课程的32课时。");
        return error.toJSONString();
    }
}