package com.lgcns.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class RunManager {

    private static final Map<String, List<String>> MODEL_AGENT_MAP =
        new HashMap<String, List<String>>();
    private static final Map<String, JsonObject> PREDICTED_MAP =
        new HashMap<String, JsonObject>();
    private static final Map<String, JsonObject> ACTUAL_MAP =
        new HashMap<String, JsonObject>();

    public static void main(String[] args) throws Exception {
        loadModels();

        Server server = new Server(8080);
        server.setHandler(new AbstractHandler() {
            @Override
            public void handle(String target, Request baseRequest,
                    HttpServletRequest request, HttpServletResponse response) throws IOException {
                route(target, request, response);
                baseRequest.setHandled(true);
            }
        });
        server.start();
        server.join();
    }

    private static void loadModels() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("MODELS.JSON"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        JsonObject json = JsonParser.parseString(sb.toString()).getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            List<String> agentList = new ArrayList<String>();
            JsonArray agents = entry.getValue().getAsJsonArray();
            for (JsonElement agent : agents) {
                agentList.add(agent.getAsString().trim());
            }
            MODEL_AGENT_MAP.put(entry.getKey(), agentList);
        }
    }

    private static void route(String target, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if ("POST".equalsIgnoreCase(request.getMethod()) && "/monitoring".equals(target)) {
            handleMonitoring(request, response);
        } else if ("POST".equalsIgnoreCase(request.getMethod()) && "/performance".equals(target)) {
            handlePerformance(request, response);
        } else {
            writeEmptyResponse(response, HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private static void handleMonitoring(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String body = readBody(request);
        JsonObject data = JsonParser.parseString(body).getAsJsonObject();
        if (!isValidMonitoring(data)) {
            writeEmptyResponse(response, HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String agentId = getTrimmedString(data, "agentId");
        String requestId = getTrimmedString(data, "requestId");
        String dataType = getTrimmedString(data, "dataType");
        data.addProperty("agentId", agentId);
        data.addProperty("requestId", requestId);
        data.addProperty("dataType", dataType);

        saveMonitoring(agentId, requestId, dataType, data);
        writeEmptyResponse(response, HttpServletResponse.SC_OK);
    }

    private static void handlePerformance(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String body = readBody(request);
        JsonObject req = JsonParser.parseString(body).getAsJsonObject();
        String modelName = getTrimmedString(req, "modelName");
        String timeWindow = getTrimmedString(req, "timeWindow");
        JsonObject result = calculatePerformance(modelName, timeWindow);
        writeJsonResponse(response, result.toString());
    }

    private static synchronized void saveMonitoring(String agentId, String requestId,
            String dataType, JsonObject data) {
        String key = buildKey(agentId, requestId);
        if ("P".equals(dataType)) {
            PREDICTED_MAP.put(key, data);
        } else if ("A".equals(dataType)) {
            ACTUAL_MAP.put(key, data);
        }
    }

    private static synchronized JsonObject calculatePerformance(String modelName, String timeWindow) {
        String startTimestamp = timeWindow + "0000";
        String endTimestamp = timeWindow + "5959";

        List<String> agentList = MODEL_AGENT_MAP.get(modelName);
        Set<String> agentSet = new HashSet<String>();
        if (agentList != null) {
            agentSet.addAll(agentList);
        }

        int correct = 0;
        int total = 0;
        long latencySum = 0L;

        for (Map.Entry<String, JsonObject> entry : PREDICTED_MAP.entrySet()) {
            JsonObject predicted = entry.getValue();
            String agentId = getTrimmedString(predicted, "agentId");
            String timestamp = getTrimmedString(predicted, "timestamp");

            if (!agentSet.contains(agentId)) {
                continue;
            }
            if (timestamp.compareTo(startTimestamp) < 0 || timestamp.compareTo(endTimestamp) > 0) {
                continue;
            }

            JsonObject actual = ACTUAL_MAP.get(entry.getKey());
            if (actual == null) {
                continue;
            }

            total++;
            latencySum += predicted.get("latency").getAsLong();

            int predictedValue = predicted.get("dataValue").getAsInt();
            int actualValue = actual.get("dataValue").getAsInt();
            if (predictedValue == actualValue) {
                correct++;
            }
        }

        JsonObject result = new JsonObject();
        result.addProperty("correct", correct);
        result.addProperty("total", total);
        result.addProperty("latency", total == 0 ? 0 : latencySum / total);
        return result;
    }

    private static String readBody(HttpServletRequest request) throws IOException {
        BufferedReader br = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    private static void writeEmptyResponse(HttpServletResponse response, int statusCode) {
        response.setStatus(statusCode);
        response.setContentLength(0);
    }

    private static void writeJsonResponse(HttpServletResponse response, String body) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(body);
    }

    private static String buildKey(String agentId, String requestId) {
        return agentId + "#" + requestId;
    }

    private static String getTrimmedString(JsonObject obj, String key) {
        if (obj == null || !obj.has(key) || obj.get(key).isJsonNull()) {
            return "";
        }
        return obj.get(key).getAsString().trim();
    }

    private static boolean isValidMonitoring(JsonObject data) {
        if (data == null) {
            return false;
        }
        if (!data.has("agentId") || !data.has("requestId") || !data.has("timestamp")
            || !data.has("dataType") || !data.has("dataValue")) {
            return false;
        }

        String dataType = getTrimmedString(data, "dataType");
        if (!"P".equals(dataType) && !"A".equals(dataType)) {
            return false;
        }
        return !"P".equals(dataType) || data.has("latency");
    }

}
