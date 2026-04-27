package com.lgcns.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class RunManager {

    public static void main(String[] args) throws Exception {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String timeWindow = stdin.readLine().trim();
        String startTimestamp = timeWindow + "0000";
        String endTimestamp = timeWindow + "5959";

        Map<String, String> predictedMap = new HashMap<String, String>();
        Map<String, String> actualMap = new HashMap<String, String>();
        Map<String, String> predictedTimestampMap = new HashMap<String, String>();

        BufferedReader br = new BufferedReader(new FileReader("MONITORING.TXT"));
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            String[] parts = line.split("#");
            if (parts.length != 4) {
                continue;
            }

            String requestId = parts[0].trim();
            String timestamp = parts[1].trim();
            String dataType = parts[2].trim();
            String dataValue = parts[3].trim();

            if ("P".equals(dataType)) {
                predictedMap.put(requestId, dataValue);
                predictedTimestampMap.put(requestId, timestamp);
            } else if ("A".equals(dataType)) {
                actualMap.put(requestId, dataValue);
            }
        }
        br.close();

        int correct = 0;
        int total = 0;

        for (Map.Entry<String, String> entry : predictedMap.entrySet()) {
            String requestId = entry.getKey();
            String timestamp = predictedTimestampMap.get(requestId);

            if (timestamp == null) {
                continue;
            }
            if (timestamp.compareTo(startTimestamp) < 0 || timestamp.compareTo(endTimestamp) > 0) {
                continue;
            }
            if (!actualMap.containsKey(requestId)) {
                continue;
            }

            total++;
            if (entry.getValue().equals(actualMap.get(requestId))) {
                correct++;
            }
        }

        System.out.println(correct + "/" + total);
    }
}
