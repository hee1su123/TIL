package com.lgcns.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RunManager {

    public static void main(String[] args) throws Exception {
        String timeWindow = readTimeWindow();
        AccuracyResult result = calculateAccuracy(Paths.get("MONITORING.TXT"), timeWindow);
        System.out.println(result.correct + "/" + result.total);
    }

    static String readTimeWindow() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        String line = reader.readLine();
        if (line == null) {
            return "";
        }
        return line.trim();
    }

    static AccuracyResult calculateAccuracy(Path monitoringPath, String timeWindow) throws IOException {
        Map<String, RequestPair> requests = new LinkedHashMap<String, RequestPair>();
        List<String> lines = Files.readAllLines(monitoringPath, StandardCharsets.UTF_8);

        for (String line : lines) {
            MonitoringRecord record = MonitoringRecord.parse(line);
            if (record == null) {
                continue;
            }

            RequestPair pair = requests.get(record.requestId);
            if (pair == null) {
                pair = new RequestPair();
                requests.put(record.requestId, pair);
            }

            if ("P".equals(record.dataType)) {
                pair.prediction = record;
            } else if ("A".equals(record.dataType)) {
                pair.actual = record;
            }
        }

        int correct = 0;
        int total = 0;

        for (RequestPair pair : requests.values()) {
            if (pair.prediction == null || pair.actual == null) {
                continue;
            }
            if (!pair.prediction.timestamp.startsWith(timeWindow)) {
                continue;
            }

            total++;
            if (pair.prediction.dataValue == pair.actual.dataValue) {
                correct++;
            }
        }

        return new AccuracyResult(correct, total);
    }

    static final class MonitoringRecord {
        private final String requestId;
        private final String timestamp;
        private final String dataType;
        private final int dataValue;

        private MonitoringRecord(String requestId, String timestamp, String dataType, int dataValue) {
            this.requestId = requestId;
            this.timestamp = timestamp;
            this.dataType = dataType;
            this.dataValue = dataValue;
        }

        static MonitoringRecord parse(String line) {
            if (line == null || line.trim().isEmpty()) {
                return null;
            }

            String[] parts = line.split("#");
            if (parts.length != 4) {
                return null;
            }

            try {
                return new MonitoringRecord(
                    parts[0].trim(),
                    parts[1].trim(),
                    parts[2].trim(),
                    Integer.parseInt(parts[3].trim())
                );
            } catch (NumberFormatException ex) {
                return null;
            }
        }
    }

    static final class RequestPair {
        private MonitoringRecord prediction;
        private MonitoringRecord actual;
    }

    static final class AccuracyResult {
        private final int correct;
        private final int total;

        private AccuracyResult(int correct, int total) {
            this.correct = correct;
            this.total = total;
        }
    }
}
