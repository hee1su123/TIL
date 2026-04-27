# Java Snippet 치트시트

> 기출문제 기반으로 실제 시험에 나올 수 있는 핵심 코드 패턴 정리

---

## 1. 파일 읽기 (가장 기본 — 거의 매번 출제)

### 1-1. 텍스트 파일 한 줄씩 읽기
```java
BufferedReader br = new BufferedReader(new FileReader("MONITORING.TXT"));
String line;
while ((line = br.readLine()) != null) {
    line = line.trim();
    if (line.isEmpty()) continue;
    // 처리 로직
}
br.close();
```

### 1-2. 구분자로 파싱 (# , | 탭 등)
```java
String[] parts = line.split("#");
String reqId     = parts[0];
String timestamp = parts[1];
String type      = parts[2];
String value     = parts[3];
```

### 1-3. CSV 파싱 (콤마 구분)
```java
String[] parts = line.split(",");
// 따옴표 제거가 필요한 경우
String cleaned = parts[0].replace("\"", "").trim();
```

---

## 2. JSON 처리 (Gson — SUB3/SUB4 단골)

### 2-1. JSON 파일 읽기
```java
import com.google.gson.*;

BufferedReader br = new BufferedReader(new FileReader("MODELS.JSON"));
StringBuilder sb = new StringBuilder();
String line;
while ((line = br.readLine()) != null) {
    sb.append(line);
}
br.close();

JsonObject json = JsonParser.parseString(sb.toString()).getAsJsonObject();
```

### 2-2. JSON Object 순회
```java
for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
    String key = entry.getKey();                         // "resnet3.0"
    JsonArray arr = entry.getValue().getAsJsonArray();    // ["agent01","agent03"]
    
    List<String> list = new ArrayList<>();
    for (JsonElement e : arr) {
        list.add(e.getAsString());
    }
}
```

### 2-3. JSON 문자열 → 객체
```java
String jsonStr = "{\"name\":\"test\",\"value\":123}";
JsonObject obj = JsonParser.parseString(jsonStr).getAsJsonObject();

String name  = obj.get("name").getAsString();
int value    = obj.get("value").getAsInt();
long bigVal  = obj.get("bigValue").getAsLong();
double dVal  = obj.get("doubleVal").getAsDouble();
```

### 2-4. JSON 응답 생성
```java
JsonObject result = new JsonObject();
result.addProperty("correct", 8);
result.addProperty("total", 10);
result.addProperty("latency", 300);
// result.toString() → {"correct":8,"total":10,"latency":300}
```

### 2-5. JSON Array 생성
```java
JsonArray arr = new JsonArray();
arr.add("item1");
arr.add("item2");

JsonObject obj = new JsonObject();
obj.add("items", arr);
// {"items":["item1","item2"]}
```

### 2-6. 필드 존재 여부 체크
```java
if (obj.has("latency")) {
    long latency = obj.get("latency").getAsLong();
}
```

---

## 3. 콘솔 입/출력

### 3-1. 콘솔 입력 받기
```java
BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
String input = stdin.readLine().trim();
```

### 3-2. 콘솔 출력 (정확한 포맷 필수!)
```java
System.out.println(matched + "/" + total);     // 8/10
System.out.println(String.format("%d/%d", matched, total));
```

> ⚠️ **주의**: 불필요한 로그 출력 금지! `System.out.println`으로 답만 출력

---

## 4. 자료구조 활용 패턴

### 4-1. HashMap — Key별 데이터 저장
```java
Map<String, String> pFlags = new HashMap<>();
Map<String, String> aFlags = new HashMap<>();

pFlags.put(reqId, value);  // 저장
pFlags.get(reqId);         // 조회
pFlags.containsKey(reqId); // 존재 체크
```

### 4-2. 그룹핑 (Key → List)
```java
Map<String, List<String>> groups = new HashMap<>();

// 방법1: computeIfAbsent (추천)
groups.computeIfAbsent(groupKey, k -> new ArrayList<>()).add(item);

// 방법2: 수동
if (!groups.containsKey(groupKey)) {
    groups.put(groupKey, new ArrayList<>());
}
groups.get(groupKey).add(item);
```

### 4-3. TreeMap — 정렬된 순서 유지
```java
TreeMap<String, List<String>> sorted = new TreeMap<>();
// Key 기준 자동 오름차순 정렬
```

### 4-4. Set — 중복 제거 / 빠른 포함 여부 체크
```java
Set<String> agentSet = new HashSet<>(agentList);
if (agentSet.contains(agentId)) {
    // ...
}
```

### 4-5. Map 순회
```java
for (Map.Entry<String, String> entry : map.entrySet()) {
    String key = entry.getKey();
    String val = entry.getValue();
}
```

---

## 5. Jetty HTTP 서버 (SUB3/SUB4 핵심!)

### 5-1. 기본 서버 세팅
```java
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import javax.servlet.http.*;
import java.io.*;

Server server = new Server(8080);
server.setHandler(new AbstractHandler() {
    @Override
    public void handle(String target, Request baseRequest,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        if ("/monitoring".equals(target) && "POST".equalsIgnoreCase(request.getMethod())) {
            handleMonitoring(request, response);
            baseRequest.setHandled(true);
        } else if ("/performance".equals(target) && "POST".equalsIgnoreCase(request.getMethod())) {
            handlePerformance(request, response);
            baseRequest.setHandled(true);
        }
    }
});
server.start();
server.join();  // 서버 종료 없이 대기 (문항3~4 요구사항)
```

### 5-2. HTTP 요청 Body 읽기
```java
private static String readBody(HttpServletRequest request) throws IOException {
    BufferedReader br = request.getReader();
    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = br.readLine()) != null) {
        sb.append(line);
    }
    return sb.toString();
}

// 사용
JsonObject data = JsonParser.parseString(readBody(request)).getAsJsonObject();
```

### 5-3. HTTP 응답 보내기
```java
// 200 OK + 빈 응답
response.setStatus(200);

// 200 OK + JSON 응답
response.setStatus(200);
response.setContentType("application/json");
response.getWriter().write(result.toString());
```

### 5-4. 다중 엔드포인트 라우팅 패턴
```java
public void handle(String target, Request baseRequest,
        HttpServletRequest request, HttpServletResponse response) throws IOException {

    String method = request.getMethod();

    switch (target) {
        case "/monitoring":
            if ("POST".equalsIgnoreCase(method)) handleMonitoring(request, response);
            break;
        case "/performance":
            if ("POST".equalsIgnoreCase(method)) handlePerformance(request, response);
            break;
        case "/status":
            if ("GET".equalsIgnoreCase(method)) handleStatus(request, response);
            break;
    }
    baseRequest.setHandled(true);
}
```

### 5-5. Query Parameter 읽기 (GET 요청용)
```java
// GET /search?keyword=test&page=1
String keyword = request.getParameter("keyword");
String page = request.getParameter("page");
```

---

## 6. 시간/날짜 처리 패턴

### 6-1. 문자열 시간 범위 비교 (가장 빈출!)
```java
String timeWindow = "2025041010"; // yyyyMMddHH
String startTs = timeWindow + "0000"; // 20250410100000
String endTs   = timeWindow + "5959"; // 20250410105959

// 문자열 비교 (yyyyMMddHHmmss 는 사전순 = 시간순)
if (timestamp.compareTo(startTs) >= 0 && timestamp.compareTo(endTs) <= 0) {
    // 범위 내
}
```

### 6-2. 시간 그룹핑 (시간대별)
```java
String hourKey = timestamp.substring(0, 10); // yyyyMMddHH
```

### 6-3. 날짜 그룹핑 (일별)
```java
String dateKey = timestamp.substring(0, 8);  // yyyyMMdd
```

---

## 7. 통계/집계 패턴

### 7-1. 일치율 / 정확도
```java
int total = 0;
int correct = 0;

for (...) {
    total++;
    if (predicted == actual) correct++;
}

System.out.println(correct + "/" + total);
```

### 7-2. 평균값 (정수 처리)
```java
long sum = 0;
int count = 0;

for (...) {
    sum += value;
    count++;
}

long average = (count > 0) ? sum / count : 0;
```

### 7-3. 최댓값 / 최솟값
```java
int max = Integer.MIN_VALUE;
int min = Integer.MAX_VALUE;

for (int val : values) {
    max = Math.max(max, val);
    min = Math.min(min, val);
}
```

### 7-4. 빈도수 카운팅
```java
Map<String, Integer> countMap = new HashMap<>();
for (String item : list) {
    countMap.merge(item, 1, Integer::sum);
    // 또는
    countMap.put(item, countMap.getOrDefault(item, 0) + 1);
}
```

---

## 8. 정렬 패턴

### 8-1. List 정렬
```java
List<String> list = new ArrayList<>(map.keySet());
Collections.sort(list);  // 오름차순

// 내림차순
Collections.sort(list, Collections.reverseOrder());
```

### 8-2. 커스텀 정렬 (숫자 포함 문자열 등)
```java
list.sort((a, b) -> {
    int numA = Integer.parseInt(a.replaceAll("[^0-9]", ""));
    int numB = Integer.parseInt(b.replaceAll("[^0-9]", ""));
    return Integer.compare(numA, numB);
});
```

### 8-3. Map을 Value 기준 정렬
```java
List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
entries.sort((a, b) -> b.getValue().compareTo(a.getValue())); // 내림차순
```

---

## 9. 파일 출력

### 9-1. 파일 쓰기
```java
BufferedWriter bw = new BufferedWriter(new FileWriter("OUTPUT.TXT"));
bw.write("결과: " + result);
bw.newLine();
bw.close();
```

### 9-2. PrintWriter (더 편리)
```java
PrintWriter pw = new PrintWriter(new FileWriter("OUTPUT.TXT"));
pw.println("line1");
pw.printf("%s/%d%n", name, count);
pw.close();
```

---

## 10. ProcessBuilder — 외부 프로그램 실행

### 10-1. 기본 실행 (실행 후 종료 대기)
```java
ProcessBuilder pb = new ProcessBuilder("MOCK.EXE");
pb.directory(new File("."));                // 실행 디렉토리 지정
pb.redirectErrorStream(true);               // stderr → stdout 합치기
Process process = pb.start();

// 출력 읽기
BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
String line;
while ((line = br.readLine()) != null) {
    System.out.println(line);
}

int exitCode = process.waitFor();           // 종료 대기
System.out.println("Exit code: " + exitCode);
```

### 10-2. 인자 전달
```java
// MOCK.EXE 8080 param2
ProcessBuilder pb = new ProcessBuilder("MOCK.EXE", "8080", "param2");

// 또는 List로 전달
List<String> cmd = new ArrayList<>();
cmd.add("MOCK.EXE");
cmd.add(String.valueOf(port));
ProcessBuilder pb = new ProcessBuilder(cmd);
```

### 10-3. 백그라운드 실행 (종료 대기 없이)
```java
ProcessBuilder pb = new ProcessBuilder("MOCK.EXE");
pb.directory(new File("."));
pb.redirectErrorStream(true);
Process process = pb.start();

// 출력을 별도 스레드에서 읽기 (블로킹 방지)
new Thread(() -> {
    try {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println("[MOCK] " + line);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}).start();

// 메인 로직 계속 진행...
```

### 10-4. 환경변수 설정
```java
ProcessBuilder pb = new ProcessBuilder("MOCK.EXE");
Map<String, String> env = pb.environment();
env.put("PORT", "8080");
env.put("DATA_DIR", "./data");
Process process = pb.start();
```

### 10-5. 프로세스에 입력 보내기
```java
Process process = pb.start();

// 프로세스 stdin에 데이터 전달
OutputStream os = process.getOutputStream();
os.write("input data\n".getBytes());
os.flush();
os.close();
```

### 10-6. 타임아웃 있는 실행
```java
Process process = pb.start();
boolean finished = process.waitFor(30, TimeUnit.SECONDS); // 30초 타임아웃
if (!finished) {
    process.destroyForcibly();  // 강제 종료
    System.out.println("프로세스 타임아웃!");
}
```

### 10-7. 출력을 파일로 리다이렉트
```java
ProcessBuilder pb = new ProcessBuilder("MOCK.EXE");
pb.redirectOutput(new File("output.log"));
pb.redirectError(new File("error.log"));
Process process = pb.start();
process.waitFor();
```

---

## 11. Thread / 동시성 처리

### 11-1. 기본 Thread 생성 및 실행
```java
// 방법1: Runnable
Thread t = new Thread(() -> {
    System.out.println("작업 수행 중...");
    // 로직
});
t.start();

// 방법2: Thread 상속
Thread t = new Thread() {
    @Override
    public void run() {
        System.out.println("작업 수행 중...");
    }
};
t.start();
```

### 11-2. Thread 종료 대기
```java
Thread t = new Thread(() -> {
    // 오래 걸리는 작업
});
t.start();
t.join();  // t 스레드 종료까지 대기
System.out.println("스레드 완료 후 실행");
```

### 11-3. 여러 Thread 동시 실행 후 전부 대기
```java
List<Thread> threads = new ArrayList<>();

for (int i = 0; i < 5; i++) {
    final int taskId = i;
    Thread t = new Thread(() -> {
        System.out.println("Task " + taskId + " 실행");
        // 작업 수행
    });
    t.start();
    threads.add(t);
}

// 모든 스레드 완료 대기
for (Thread t : threads) {
    t.join();
}
System.out.println("모든 작업 완료");
```

### 11-4. Thread-Safe 자료구조 (동시 접근)
```java
import java.util.concurrent.*;

// Thread-Safe Map
Map<String, JsonObject> dataStore = new ConcurrentHashMap<>();

// Thread-Safe List
List<String> results = Collections.synchronizedList(new ArrayList<>());

// Thread-Safe Counter
AtomicInteger counter = new AtomicInteger(0);
counter.incrementAndGet();  // ++
counter.get();              // 조회
```

### 11-5. synchronized — 임계 영역 보호
```java
private static final Object lock = new Object();
private static Map<String, String> sharedMap = new HashMap<>();

// 방법1: synchronized 블록
synchronized (lock) {
    sharedMap.put(key, value);
    int count = sharedMap.size();
}

// 방법2: synchronized 메서드
private static synchronized void addData(String key, String value) {
    sharedMap.put(key, value);
}
```

### 11-6. Thread.sleep — 대기
```java
try {
    Thread.sleep(1000);  // 1초 대기
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
}
```

### 11-7. ExecutorService — 스레드 풀 (고급)
```java
import java.util.concurrent.*;

ExecutorService executor = Executors.newFixedThreadPool(4); // 4개 스레드 풀

// 작업 제출
for (int i = 0; i < 10; i++) {
    final int taskId = i;
    executor.submit(() -> {
        System.out.println("Task " + taskId + " by " + Thread.currentThread().getName());
    });
}

executor.shutdown();                         // 새 작업 접수 중단
executor.awaitTermination(30, TimeUnit.SECONDS); // 완료 대기
```

### 11-8. Future — 비동기 결과 받기
```java
ExecutorService executor = Executors.newFixedThreadPool(2);

Future<Integer> future = executor.submit(() -> {
    Thread.sleep(2000);
    return 42;  // 결과 반환
});

// 다른 작업 수행...

int result = future.get();  // 결과 대기 (블로킹)
System.out.println("결과: " + result);

executor.shutdown();
```

### 11-9. 실전 패턴 — HTTP 서버 + 외부 프로그램 동시 실행
```java
public static void main(String[] args) throws Exception {
    loadConfig();

    // 1. HTTP 서버를 별도 스레드에서 시작
    Server server = new Server(8080);
    server.setHandler(/* handler */);
    server.start();

    // 2. 외부 프로그램 실행 (서버가 준비된 후)
    ProcessBuilder pb = new ProcessBuilder("MOCK.EXE");
    pb.directory(new File("."));
    pb.redirectErrorStream(true);
    Process mockProcess = pb.start();

    // 3. 외부 프로그램 출력 읽기
    BufferedReader br = new BufferedReader(
            new InputStreamReader(mockProcess.getInputStream()));
    String line;
    while ((line = br.readLine()) != null) {
        System.out.println(line);
    }

    mockProcess.waitFor();
    server.stop();
}
```

### 11-10. 실전 패턴 — Jetty 핸들러에서 ConcurrentHashMap 사용
```java
// Jetty는 멀티스레드로 요청을 처리하므로 Thread-Safe 자료구조 필요!
private static Map<String, JsonObject> pRecords = new ConcurrentHashMap<>();
private static Map<String, JsonObject> aRecords = new ConcurrentHashMap<>();

private static void handleMonitoring(HttpServletRequest request,
        HttpServletResponse response) throws IOException {
    JsonObject data = JsonParser.parseString(readBody(request)).getAsJsonObject();
    String key = data.get("agentId").getAsString() + "#" + data.get("requestId").getAsString();
    String dataType = data.get("dataType").getAsString().trim();

    if ("P".equals(dataType)) {
        pRecords.put(key, data);   // ConcurrentHashMap이므로 안전
    } else if ("A".equals(dataType)) {
        aRecords.put(key, data);
    }
    response.setStatus(200);
}
```

---

## 12. 전체 뼈대 코드 (복붙용)

### 🔹 콘솔형 (SUB1/SUB2 패턴)
```java
package com.lgcns.test;

import java.io.*;
import java.util.*;

public class RunManager {
    public static void main(String[] args) throws Exception {
        // 1. 파일 읽기
        BufferedReader br = new BufferedReader(new FileReader("DATA.TXT"));
        String line;
        Map<String, String> dataMap = new HashMap<>();
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split("#");
            // 파싱 로직
        }
        br.close();

        // 2. (선택) 콘솔 입력
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String input = stdin.readLine().trim();

        // 3. 처리 로직
        int total = 0, correct = 0;
        // ...

        // 4. 콘솔 출력
        System.out.println(correct + "/" + total);
    }
}
```

### 🔹 HTTP 서버형 (SUB3/SUB4 패턴)
```java
package com.lgcns.test;

import java.io.*;
import java.util.*;
import com.google.gson.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import javax.servlet.http.*;

public class RunManager {
    private static Map<String, JsonObject> dataStore = new HashMap<>();
    private static Map<String, List<String>> configMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        // 1. 설정 파일 로드
        loadConfig();

        // 2. HTTP 서버 시작
        Server server = new Server(8080);
        server.setHandler(new AbstractHandler() {
            @Override
            public void handle(String target, Request baseRequest,
                    HttpServletRequest request, HttpServletResponse response)
                    throws IOException {
                switch (target) {
                    case "/data":
                        handleData(request, response);
                        break;
                    case "/query":
                        handleQuery(request, response);
                        break;
                }
                baseRequest.setHandled(true);
            }
        });
        server.start();
        server.join();
    }

    private static void loadConfig() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("CONFIG.JSON"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line);
        br.close();

        JsonObject json = JsonParser.parseString(sb.toString()).getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            List<String> list = new ArrayList<>();
            for (JsonElement e : entry.getValue().getAsJsonArray()) {
                list.add(e.getAsString());
            }
            configMap.put(entry.getKey(), list);
        }
    }

    private static String readBody(HttpServletRequest request) throws IOException {
        BufferedReader br = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line);
        return sb.toString();
    }

    private static void handleData(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        JsonObject data = JsonParser.parseString(readBody(request)).getAsJsonObject();
        String id = data.get("id").getAsString();
        dataStore.put(id, data);
        response.setStatus(200);
    }

    private static void handleQuery(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        JsonObject req = JsonParser.parseString(readBody(request)).getAsJsonObject();

        // 처리 로직
        JsonObject result = new JsonObject();
        result.addProperty("count", 0);

        response.setStatus(200);
        response.setContentType("application/json");
        response.getWriter().write(result.toString());
    }
}
```

---

## 💡 실전 팁

1. **상대경로 필수** — `new FileReader("DATA.TXT")` (절대경로 ❌)
2. **불필요한 출력 금지** — 디버그 `println` 제출 전 반드시 삭제
3. **프로그램 종료 조건** — SUB1/2는 자동종료, SUB3/4는 종료 없이 대기(`server.join()`)
4. **대소문자 정확히** — JSON key, endpoint path 모두 시험지와 **정확히** 일치
5. **dataType trim 필수** — JSON에 `"P "` 처럼 공백이 들어올 수 있음 → `.trim()`
6. **정수 처리** — 평균값 등은 `long / int` 정수 나눗셈 (문제에서 정수 보장)
7. **선행문항 복사** — SUB1→SUB2→SUB3→SUB4 순서로 소스 복사 후 확장

---

# 실기 시험 빠른 분석/설계 전략

## 🎯 핵심 원칙: "문제지 받으면 코딩 전 5분 투자"

---

## STEP 1. 문제지 훑기 (2분)

**코딩하지 말고 문제지 4문항을 전부 먼저 읽어라!**

```
✅ 체크포인트:
□ 문항 수 & 배점 확인 (보통 SUB1~SUB4, 20+30+20+30)
□ SUB1~2는 콘솔형? SUB3~4는 HTTP 서버형? → 구현 규모 파악
□ 최종 문항(SUB4)이 뭘 요구하는지 → 역방향으로 자료구조 설계
```

> ⚠️ **SUB4를 먼저 봐야 하는 이유**: SUB1에서 만든 자료구조를 SUB4까지 확장해야 하므로, 
> 처음부터 확장 가능한 구조로 설계해야 나중에 뒤엎지 않는다.

### 이번 기출 예시
```
SUB1: 파일 읽기 → 정확도 출력          → Map<reqId, value> 필요
SUB2: + 시간범위 필터링                → timestamp도 저장해야 함
SUB3: + HTTP 서버 + JSON + 모델매핑    → agentId 추가, Jetty 서버
SUB4: + latency 추가                   → P레코드에 latency 필드
```
→ **SUB4를 보면** agentId, requestId, timestamp, dataType, dataValue, latency가 다 필요
→ **SUB1부터 이 구조를 고려해서 설계**

---

## STEP 2. 데이터 파일 + 기대출력 확인 (2분)

**코드 치기 전에 반드시 데이터 파일과 COMPARE 폴더를 열어봐라**

```
✅ 체크포인트:
□ 데이터 파일 형식 (구분자, 필드 수, 타입)
□ CMP_CONSOLE.TXT (기대 출력) 확인
□ 데이터에 함정이 있는지 (불규칙한 ID, 공백, 빈 줄 등)
```

### 이번 기출에서 발견한 함정들
```
req00   ← "req00" (숫자 2자리가 아님, 이걸 req001과 혼동하면 안됨)
req0010 ← "req0010" (4자리)
"P "    ← JSON의 dataType에 공백이 포함될 수 있음 → trim() 필수
```

---

## STEP 3. 자료구조 설계 (1분)

**종이나 주석에 3줄 적기:**

```java
// 1. 데이터 저장소 (뭘 key로, 뭘 value로?)
// 2. 입력 → 처리 → 출력 흐름
// 3. SUB3~4에서 확장할 부분
```

### 설계 패턴 빠른 판단표

| 문제 유형 | Key 설계 | Value 설계 |
|----------|---------|-----------|
| 요청별 매칭 | requestId | flag/value |
| 에이전트별 + 요청별 | agentId#requestId | 전체 데이터(JsonObject) |
| 시간대별 그룹핑 | timestamp.substring(0,10) | List<요청> |
| 모델별 에이전트 | modelName | List<agentId> |
| 빈도수/카운팅 | 대상 항목 | Integer (count) |

### 이번 기출 최적 설계 (한눈에)
```
SUB1: Map<reqId, pValue>, Map<reqId, aValue> → 비교
SUB2: + Map<reqId, timestamp> → 필터링
SUB3: Map<"agentId#reqId", JsonObject> (P/A 각각) + MODELS.JSON
SUB4: + latency 필드 추가 (P record에만)
```

---

## STEP 4. 코딩 순서 & 시간 배분

### 90분 기준 권장 시간표

```
[00:00 ~ 00:05]  문제 분석 + 자료구조 설계 (STEP 1~3)
[00:05 ~ 00:25]  SUB1 코딩 + 검증 (20분)
[00:25 ~ 00:40]  SUB2 코딩 + 검증 (15분) ← SUB1 복사 후 수정
[00:40 ~ 01:05]  SUB3 코딩 + 검증 (25분) ← HTTP 서버가 시간 소요
[01:05 ~ 01:20]  SUB4 코딩 + 검증 (15분) ← SUB3 복사 후 필드 추가
[01:20 ~ 01:30]  최종 검수 + 제출 (10분)
```

### 코딩 순서 원칙

```
1. SUB1: 파일 읽기 + 파싱 + 계산 + 출력
   → CMP_CONSOLE.TXT로 즉시 검증
   → ✅ 확인되면 SUB1/src 전체 → SUB2/src로 복사

2. SUB2: 콘솔 입력 추가 + 필터링 로직
   → CMP_CONSOLE.TXT로 검증
   → ✅ 확인되면 SUB2/src 전체 → SUB3/src로 복사

3. SUB3: Jetty 서버 + JSON 핸들러 (뼈대 코드 복붙!)
   → MOCK.EXE로 검증
   → ✅ 확인되면 SUB3/src 전체 → SUB4/src로 복사

4. SUB4: 필드 추가 + 계산 로직 수정
   → MOCK.EXE로 검증
```

---

## STEP 5. 빠른 판단 의사결정 트리

### "데이터를 어떻게 읽나?"
```
파일(.TXT)에서 읽기    → BufferedReader + split
HTTP로 받기            → Jetty + request.getReader()
콘솔에서 입력 받기      → BufferedReader(System.in)
JSON 파일에서 읽기      → Gson JsonParser
```

### "결과를 어떻게 출력하나?"
```
콘솔 출력              → System.out.println
HTTP JSON 응답         → response.getWriter().write(json.toString())
파일 출력              → BufferedWriter / PrintWriter
```

### "데이터를 어떻게 매칭하나?"
```
1:1 매칭 (P↔A)        → 같은 Key로 두 Map에 저장 후 비교
그룹핑                 → Map<groupKey, List<item>>
필터링                 → if 조건 + compareTo (시간범위)
모델→에이전트 역매핑    → Map<modelName, List<agentId>> + Set으로 변환
```

---

## 💡 실전 치트 행동

### 시험 시작 직후 (코딩 전)
1. **SUB4 문제를 먼저 읽는다** → 최종 자료구조 역산
2. **데이터 파일을 연다** → 함정 패턴 파악
3. **CMP_CONSOLE.TXT를 연다** → 정답을 먼저 본다
4. **주석으로 설계 3줄 적는다**

### SUB3 진입 시 (HTTP 서버)
1. **뼈대 코드를 통째로 복붙** (치트시트의 HTTP 서버형 템플릿)
2. Jetty import문은 외울 필요 없다 → **치트시트 참고**
3. `readBody()` 유틸 메서드 먼저 만들기
4. 엔드포인트 하나씩 추가하며 MOCK.EXE로 점진적 테스트

### 막혔을 때
```
SUB1, SUB2에서 막힌다 → 데이터 파일을 손으로 따라가며 기대값 계산
SUB3에서 막힌다      → Jetty 서버 뼈대만 올리고 SUB4로 넘어가지 말 것
                       (SUB3이 안되면 SUB4도 0점)
시간 부족하다        → SUB1+SUB2 완벽히 마무리 (50점 확보)
                       SUB3 부분점수 없으므로, 동작하게 만들기에 집중
```

---

## ✅ 시험 직전 최종 체크리스트

```
□ 치트시트/뼈대코드 머릿속에 있는가?
  - 파일 읽기 (BufferedReader + split)
  - 콘솔 입력 (BufferedReader + System.in)
  - Gson (JsonParser.parseString + JsonObject)
  - Jetty (Server + AbstractHandler + handle)

□ 자주 까먹는 것:
  - br.close() 빠뜨리지 않기
  - line.trim() + isEmpty() 체크
  - response.setStatus(200) 빠뜨리지 않기
  - baseRequest.setHandled(true) 빠뜨리지 않기
  - dataType.trim() (JSON 공백 함정)

□ 제출 전:
  - System.out.println 디버그 로그 제거
  - 4개 SUB 폴더에 소스 파일 존재 확인
  - 각 SUB에서 컴파일 가능 확인
```

# SUB3/SUB4 큰 흐름

## 1. 시작할 때 바로 쓸 것
- `import` 먼저 작성
- 전역 `Map` 3개 선언
- `MODEL_AGENT_MAP`
- `PREDICTED_MAP`
- `ACTUAL_MAP`
- `main()`에서 `loadModels()` 호출
- `Jetty Server(8080)` 생성
- `/monitoring`, `/performance` 두 개만 분기
- `server.start()`
- `server.join()`

## 2. SUB3에서 해야 할 큰 일
- `MODELS.JSON` 읽어서 모델별 agent 목록 저장
- `/monitoring`은 받은 JSON을 저장
- `dataType`이 `P`면 예측값 저장
- `dataType`이 `A`면 실제값 저장
- 저장 키는 `agentId + "#" + requestId`
- 응답은 `200 OK`, body 없음

## 3. /performance에서 할 일
- 요청에서 `modelName`, `timeWindow` 꺼내기
- `modelName`에 해당하는 agent 목록 찾기
- `timeWindow` 범위 계산하기
- 저장된 `P` 데이터 전체 순회
- agent가 대상 모델인지 확인
- 시간이 범위 안인지 확인
- 같은 key의 `A` 데이터가 있는지 확인
- 있으면 `total` 증가
- `P.dataValue == A.dataValue`면 `correct` 증가
- 결과 JSON 응답
- `SUB3` 결과: `correct`, `total`

## 4. SUB4에서 추가할 것
- `SUB3` 코드 복사 후 시작
- `P` 데이터에만 `latency`가 들어온다고 생각
- `/monitoring` 검증에 `latency` 추가
- `/performance`에서 `latencySum` 누적
- 마지막에 평균 `latency` 계산
- `SUB4` 결과: `correct`, `total`, `latency`

## 5. 시험장에서 쓰는 순서
1. 서버 뼈대 작성
2. `loadModels()` 작성
3. `readBody()` 같은 공통 유틸 작성
4. `/monitoring` 완성
5. `/performance` 완성
6. `SUB3` 먼저 테스트
7. `SUB3`를 `SUB4`로 복사
8. `latency`만 추가

## 6. 외워둘 체크포인트
- Java 8 기준으로 작성
- 상대경로 사용
- `/monitoring` 응답 body 없음
- 서버는 종료되면 안 됨
- `POST /monitoring`
- `POST /performance`
- 불필요한 출력 하지 않기
- `SUB4`는 결국 `SUB3 + latency`
