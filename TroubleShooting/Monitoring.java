package com.lgcns.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class RunManager {

    private static final Gson GSON = new Gson();
    private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

    public static void main(String[] args) throws Exception {
        ModelRegistry registry = ModelRegistry.load("MODELS.JSON");
        MonitoringStore store = new MonitoringStore();
        SimpleHttpServer server = new SimpleHttpServer("127.0.0.1", 8080, store, registry);
        server.start();
        new CountDownLatch(1).await();
    }

    static final class SimpleHttpServer {
        private final String host;
        private final int port;
        private final MonitoringStore store;
        private final ModelRegistry registry;

        private SimpleHttpServer(String host, int port, MonitoringStore store, ModelRegistry registry) {
            this.host = host;
            this.port = port;
            this.store = store;
            this.registry = registry;
        }

        void start() throws IOException {
            final ServerSocket serverSocket = new ServerSocket();
            serverSocket.setReuseAddress(true);
            serverSocket.bind(new InetSocketAddress(host, port));

            Thread acceptThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    acceptLoop(serverSocket);
                }
            }, "sub3-http-accept");
            acceptThread.setDaemon(false);
            acceptThread.start();
        }

        void acceptLoop(ServerSocket serverSocket) {
            while (true) {
                try {
                    final Socket socket = serverSocket.accept();
                    socket.setTcpNoDelay(true);
                    socket.setSoTimeout(5000);
                    EXECUTOR.execute(new Runnable() {
                        @Override
                        public void run() {
                            handleConnection(socket);
                        }
                    });
                } catch (IOException ignored) {
                    // The exam server runs until the process is terminated.
                }
            }
        }

        void handleConnection(Socket socket) {
            try {
                HttpRequest request = readRequest(socket);
                if (request == null) {
                    return;
                }

                if (!"POST".equalsIgnoreCase(request.method)) {
                    writeJsonResponse(socket, 405, "Method Not Allowed", "{\"error\":\"method_not_allowed\"}");
                    return;
                }

                if ("/monitoring".equals(request.path)) {
                    handleMonitoring(socket, request.body);
                    return;
                }

                if ("/performance".equals(request.path)) {
                    handlePerformance(socket, request.body);
                    return;
                }

                writeJsonResponse(socket, 404, "Not Found", "{\"error\":\"not_found\"}");
            } catch (IOException ex) {
                // Ignore connection-level failures.
            } finally {
                try {
                    socket.close();
                } catch (IOException ignored) {
                }
            }
        }

        void handleMonitoring(Socket socket, String body) throws IOException {
            try {
                MonitoringEvent event = GSON.fromJson(body, MonitoringEvent.class);
                if (event == null || !event.isValid()) {
                    writeJsonResponse(socket, 400, "Bad Request", "{\"error\":\"invalid_request\"}");
                    return;
                }
                store.save(event);
                writeEmptyResponse(socket, 200, "OK");
            } catch (JsonSyntaxException ex) {
                writeJsonResponse(socket, 400, "Bad Request", "{\"error\":\"invalid_json\"}");
            }
        }

        void handlePerformance(Socket socket, String body) throws IOException {
            try {
                PerformanceRequest request = GSON.fromJson(body, PerformanceRequest.class);
                if (request == null || isBlank(request.modelName) || isBlank(request.timeWindow)) {
                    writeJsonResponse(socket, 400, "Bad Request", "{\"error\":\"invalid_request\"}");
                    return;
                }

                PerformanceResponse response = store.calculatePerformance(
                    registry.getAgents(request.modelName),
                    request.timeWindow
                );
                writeJsonResponse(socket, 200, "OK", GSON.toJson(response));
            } catch (JsonSyntaxException ex) {
                writeJsonResponse(socket, 400, "Bad Request", "{\"error\":\"invalid_json\"}");
            }
        }
    }

    static final class HttpRequest {
        private final String method;
        private final String path;
        private final String body;

        private HttpRequest(String method, String path, String body) {
            this.method = method;
            this.path = path;
            this.body = body;
        }
    }

    static HttpRequest readRequest(Socket socket) throws IOException {
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
            if (line.isEmpty()) {
                continue;
            }
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

        return new HttpRequest(requestLine[0], requestLine[1], body);
    }

    static String readHeaderText(InputStream inputStream) throws IOException {
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

            if (buffer.size() > 65536) {
                throw new IOException("header too large");
            }
        }

        return buffer.size() == 0 ? null : new String(buffer.toByteArray(), StandardCharsets.ISO_8859_1);
    }

    static byte[] readExact(InputStream inputStream, int contentLength) throws IOException {
        if (contentLength <= 0) {
            return new byte[0];
        }

        byte[] body = new byte[contentLength];
        int offset = 0;
        while (offset < contentLength) {
            int read = inputStream.read(body, offset, contentLength - offset);
            if (read < 0) {
                throw new IOException("unexpected end of stream");
            }
            offset += read;
        }
        return body;
    }

    static void writeJsonResponse(Socket socket, int statusCode, String reason, String body) throws IOException {
        byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
        OutputStream outputStream = socket.getOutputStream();
        String headers =
            "HTTP/1.1 " + statusCode + " " + reason + "\r\n"
                + "Content-Type: application/json; charset=UTF-8\r\n"
                + "Content-Length: " + bodyBytes.length + "\r\n"
                + "Connection: close\r\n"
                + "\r\n";
        outputStream.write(headers.getBytes(StandardCharsets.ISO_8859_1));
        outputStream.write(bodyBytes);
        outputStream.flush();
    }

    static void writeEmptyResponse(Socket socket, int statusCode, String reason) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        String headers =
            "HTTP/1.1 " + statusCode + " " + reason + "\r\n"
                + "Content-Length: 0\r\n"
                + "Connection: close\r\n"
                + "\r\n";
        outputStream.write(headers.getBytes(StandardCharsets.ISO_8859_1));
        outputStream.flush();
    }

    static int parseInt(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    static final class MonitoringStore {
        private final Map<String, List<MonitoringEvent>> predictionsByRequestId =
            new ConcurrentHashMap<String, List<MonitoringEvent>>();
        private final Map<String, MonitoringEvent> actualByRequestId =
            new ConcurrentHashMap<String, MonitoringEvent>();

        void save(MonitoringEvent event) {
            if ("P".equals(event.dataType)) {
                List<MonitoringEvent> events = predictionsByRequestId.get(event.requestId);
                if (events == null) {
                    List<MonitoringEvent> newEvents =
                        Collections.synchronizedList(new ArrayList<MonitoringEvent>());
                    List<MonitoringEvent> existing = predictionsByRequestId.putIfAbsent(event.requestId, newEvents);
                    events = existing == null ? newEvents : existing;
                }
                events.add(event);
            } else if ("A".equals(event.dataType)) {
                actualByRequestId.put(event.requestId, event);
            }
        }

        PerformanceResponse calculatePerformance(List<String> allowedAgents, String timeWindow) {
            if (allowedAgents.isEmpty()) {
                return new PerformanceResponse(0, 0);
            }

            Set<String> allowedAgentSet = new HashSet<String>(allowedAgents);
            int correct = 0;
            int total = 0;

            for (List<MonitoringEvent> predictionEvents : predictionsByRequestId.values()) {
                synchronized (predictionEvents) {
                    for (MonitoringEvent prediction : predictionEvents) {
                        if (!allowedAgentSet.contains(prediction.agentId)) {
                            continue;
                        }
                        if (!prediction.timestamp.startsWith(timeWindow)) {
                            continue;
                        }

                        MonitoringEvent actual = actualByRequestId.get(prediction.requestId);
                        if (actual == null) {
                            continue;
                        }

                        total++;
                        if (prediction.dataValue.intValue() == actual.dataValue.intValue()) {
                            correct++;
                        }
                    }
                }
            }

            return new PerformanceResponse(correct, total);
        }
    }

    static final class ModelRegistry {
        private final Map<String, List<String>> modelAgents;

        private ModelRegistry(Map<String, List<String>> modelAgents) {
            this.modelAgents = modelAgents;
        }

        static ModelRegistry load(String relativePath) throws IOException {
            Reader reader = Files.newBufferedReader(Paths.get(relativePath), StandardCharsets.UTF_8);
            try {
                JsonObject jsonObject = GSON.fromJson(reader, JsonObject.class);
                Map<String, List<String>> values = new ConcurrentHashMap<String, List<String>>();
                if (jsonObject != null) {
                    for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                        List<String> agents = new ArrayList<String>();
                        JsonArray array = entry.getValue().getAsJsonArray();
                        for (JsonElement agent : array) {
                            agents.add(agent.getAsString());
                        }
                        values.put(entry.getKey(), agents);
                    }
                }
                return new ModelRegistry(values);
            } finally {
                reader.close();
            }
        }

        List<String> getAgents(String modelName) {
            List<String> agents = modelAgents.get(modelName);
            return agents == null ? Collections.<String>emptyList() : agents;
        }
    }

    static final class MonitoringEvent {
        private String agentId;
        private String requestId;
        private String timestamp;
        private String dataType;
        private Integer dataValue;

        boolean isValid() {
            return !isBlank(agentId)
                && !isBlank(requestId)
                && !isBlank(timestamp)
                && ("P".equals(dataType) || "A".equals(dataType))
                && dataValue != null;
        }
    }

    static final class PerformanceRequest {
        private String modelName;
        private String timeWindow;
    }

    static final class PerformanceResponse {
        private final int correct;
        private final int total;

        private PerformanceResponse(int correct, int total) {
            this.correct = correct;
            this.total = total;
        }
    }

    static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
