package com.lgcns.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class RunManager {

    public static void main(String[] args) throws Exception {
        Map<String, String> predictedMap = new HashMap<String, String>();
        Map<String, String> actualMap = new HashMap<String, String>();

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
            String dataType = parts[2].trim();
            String dataValue = parts[3].trim();

            if ("P".equals(dataType)) {
                predictedMap.put(requestId, dataValue);
            } else if ("A".equals(dataType)) {
                actualMap.put(requestId, dataValue);
            }
        }
        br.close();

        int correct = 0;
        int total = 0;

        for (Map.Entry<String, String> entry : predictedMap.entrySet()) {
            String requestId = entry.getKey();
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
