package com.example.coursemanagesystem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AIScheduleServiceTest {

    private static final String API_URL = "https://api.coze.cn/open_api/v2/chat";
    private static final String TOKEN = "pat_ngHdQ5VYsPsUodAYk2X4oeB2p5zrzRfCKJkTGJAtWXCha4LGqQMsmiWAqPnxUMh7";
    private static final String BOT_ID = "7473104170443325480";
    private static final String USER_ID = "SmallCarrot";

    public static void main(String[] args) {
        try {
            // ===== 构建 prompt =====
            String query = "请根据以下排课信息安排课程时间和教室，并以 JSON 数组格式返回结果，例如："
                    + "[{\"courseId\":\"CS101\",\"startTime\":\"2025-09-01T08:00:00\",\"endTime\":\"2025-09-01T10:00:00\",\"location\":\"A101\"}]\n"
                    + "课程名称：操作系统\n"
                    + "班级：计科一班\n"
                    + "总课时：48\n"
                    + "上课时间：2025-09-01 ~ 2025-12-30";

            JSONObject payload = new JSONObject()
                    .put("bot_id", BOT_ID)
                    .put("user", USER_ID)
                    .put("query", query)
                    .put("stream", false);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Authorization", "Bearer " + TOKEN)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("响应状态码: " + response.statusCode());
            System.out.println("原始响应:\n" + response.body());

            JSONObject json = new JSONObject(response.body());

            // === 提取 messages 数组中的 content 字段 ===
            JSONArray messages = json.getJSONArray("messages");
            JSONObject firstMsg = messages.getJSONObject(0);
            String content = firstMsg.optString("content", "");

            System.out.println("\nAI 回复的 content:\n" + content);

            // === 尝试三种方式解析 content ===
            System.out.println("\n--- 尝试解析 JSON 数组 ---");
            try {
                JSONArray resultArray = new JSONArray(content);
                for (int i = 0; i < resultArray.length(); i++) {
                    JSONObject item = resultArray.getJSONObject(i);
                    System.out.println("课程ID: " + item.optString("courseId"));
                    System.out.println("开始时间: " + item.optString("startTime"));
                    System.out.println("结束时间: " + item.optString("endTime"));
                    System.out.println("教室: " + item.optString("location"));
                    System.out.println("------------");
                }
            } catch (Exception e) {
                System.out.println("⚠️ 不是 JSON 数组: " + e.getMessage());
            }

            System.out.println("\n--- 尝试解析 JSON 对象 ---");
            try {
                JSONObject obj = new JSONObject(content);
                System.out.println(obj.toString(2));
            } catch (Exception e) {
                System.out.println("⚠️ 不是 JSON 对象: " + e.getMessage());
            }

            System.out.println("\n--- 尝试原始文本拆分 ---");
            if (!content.startsWith("{") && !content.startsWith("[")) {
                String[] lines = content.split("\\r?\\n");
                for (String line : lines) {
                    System.out.println(line);
                }
            }

        } catch (Exception e) {
            System.err.println("请求或解析失败：");
            e.printStackTrace();
        }
    }
}
