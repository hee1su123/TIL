package com.lgcns.test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RunManager {

    private static final Map<String, List<String>> MODEL_AGENT_MAP =
        new HashMap<String, List<String>>();
    private static final Map<String, JsonObject> PREDICTED_MAP =
        new HashMap<String, JsonObject>();
    private static final Map<String, JsonObject> ACTUAL_MAP =
        new HashMap<String, JsonObject>();

    public static void main(String[] args) throws Exception {
        loadModels();

        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            final Socket socket = serverSocket.accept();
            socket.setSoTimeout(30000);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    handleClient(socket);
                }
            }).start();
        }
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

    private static void handleClient(Socket socket) {
        try {
            while (true) {
                HttpRequest request = readRequest(socket);
                if (request == null) {
                    break;
                }

                if ("POST".equalsIgnoreCase(request.method) && "/monitoring".equals(request.path)) {
                    handleMonitoring(socket, request.body);
                } else if ("POST".equalsIgnoreCase(request.method) && "/performance".equals(request.path)) {
                    handlePerformance(socket, request.body);
                } else {
                    writeEmptyResponse(socket, 404, "Not Found");
                }

                if ("close".equalsIgnoreCase(request.connection)) {
                    break;
                }
            }
        } catch (IOException ignored) {
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }

    private static HttpRequest readRequest(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        String headerText = readHeaderText(inputStream);
        if (headerText == null || headerText.isEmpty()) {
            return null;
        }

        String[] lines = headerText.split("\r\n");
        if (lines.length == 0) {
            return null;
        }

        String[] requestLine = lines[0].split(" ");
        if (requestLine.length < 2) {
            return null;
        }

        Map<String, String> headers = new HashMap<String, String>();
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            int delimiter = line.indexOf(':');
            if (delimiter < 0) {
                continue;
            }

            String name = line.substring(0, delimiter).trim().toLowerCase();
            String value = line.substring(delimiter + 1).trim();
            headers.put(name, value);
        }

        if ("100-continue".equalsIgnoreCase(headers.get("expect"))) {
            outputStream.write("HTTP/1.1 100 Continue\r\n\r\n".getBytes(StandardCharsets.ISO_8859_1));
            outputStream.flush();
        }

        int contentLength = parseInt(headers.get("content-length"));
        byte[] bodyBytes = readExact(inputStream, contentLength);
        String body = new String(bodyBytes, StandardCharsets.UTF_8);

        return new HttpRequest(requestLine[0], requestLine[1], body, headers.get("connection"));
    }

    private static String readHeaderText(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int matched = 0;
        int current;

        while ((current = inputStream.read()) != -1) {
            buffer.write(current);

            if ((matched == 0 && current == '\r')
                || (matched == 1 && current == '\n')
                || (matched == 2 && current == '\r')
                || (matched == 3 && current == '\n')) {
                matched++;
                if (matched == 4) {
                    return new String(buffer.toByteArray(), StandardCharsets.ISO_8859_1);
                }
            } else {
                matched = current == '\r' ? 1 : 0;
            }
        }

        if (buffer.size() == 0) {
            return null;
        }
        return new String(buffer.toByteArray(), StandardCharsets.ISO_8859_1);
    }

    private static byte[] readExact(InputStream inputStream, int contentLength) throws IOException {
        if (contentLength <= 0) {
            return new byte[0];
        }

        byte[] body = new byte[contentLength];
        int offset = 0;
        while (offset < contentLength) {
            int readCount = inputStream.read(body, offset, contentLength - offset);
            if (readCount < 0) {
                break;
            }
            offset += readCount;
        }

        if (offset == contentLength) {
            return body;
        }

        byte[] exactBody = new byte[offset];
        System.arraycopy(body, 0, exactBody, 0, offset);
        return exactBody;
    }

    private static void handleMonitoring(Socket socket, String body) throws IOException {
        JsonObject data = JsonParser.parseString(body).getAsJsonObject();
        if (!isValidMonitoring(data)) {
            writeEmptyResponse(socket, 400, "Bad Request");
            return;
        }

        String agentId = getTrimmedString(data, "agentId");
        String requestId = getTrimmedString(data, "requestId");
        String dataType = getTrimmedString(data, "dataType");
        data.addProperty("agentId", agentId);
        data.addProperty("requestId", requestId);
        data.addProperty("dataType", dataType);

        saveMonitoring(agentId, requestId, dataType, data);
        writeEmptyResponse(socket, 200, "OK");
    }

    private static void handlePerformance(Socket socket, String body) throws IOException {
        JsonObject req = JsonParser.parseString(body).getAsJsonObject();
        String modelName = getTrimmedString(req, "modelName");
        String timeWindow = getTrimmedString(req, "timeWindow");
        JsonObject result = calculatePerformance(modelName, timeWindow);
        writeJsonResponse(socket, result.toString());
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
            int predictedValue = predicted.get("dataValue").getAsInt();
            int actualValue = actual.get("dataValue").getAsInt();
            if (predictedValue == actualValue) {
                correct++;
            }
        }

        JsonObject result = new JsonObject();
        result.addProperty("correct", correct);
        result.addProperty("total", total);
        return result;
    }

    private static void writeEmptyResponse(Socket socket, int statusCode, String reason) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        String response =
            "HTTP/1.1 " + statusCode + " " + reason + "\r\n"
                + "Content-Length: 0\r\n"
                + "Connection: Keep-Alive\r\n"
                + "\r\n";
        outputStream.write(response.getBytes(StandardCharsets.ISO_8859_1));
        outputStream.flush();
    }

    private static void writeJsonResponse(Socket socket, String body) throws IOException {
        byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
        OutputStream outputStream = socket.getOutputStream();
        String response =
            "HTTP/1.1 200 OK\r\n"
                + "Content-Type: application/json\r\n"
                + "Content-Length: " + bodyBytes.length + "\r\n"
                + "Connection: Keep-Alive\r\n"
                + "\r\n";
        outputStream.write(response.getBytes(StandardCharsets.ISO_8859_1));
        outputStream.write(bodyBytes);
        outputStream.flush();
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
        return "P".equals(dataType) || "A".equals(dataType);
    }

    private static int parseInt(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0;
        }
        return Integer.parseInt(value.trim());
    }

    private static final class HttpRequest {
        private final String method;
        private final String path;
        private final String body;
        private final String connection;

        private HttpRequest(String method, String path, String body, String connection) {
            this.method = method;
            this.path = path;
            this.body = body;
            this.connection = connection;
        }
    }
}
