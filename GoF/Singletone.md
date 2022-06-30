## Singletone Pattern
들어가기 앞서  
- 객체: 속성과 기능을 갖춘 것  
- 클래스: 속성과 기능을 정의한 것  
- 인스턴스: 속성과 기능을 가진 것 중 실제 하는 것  

싱글톤 패턴
- 하나의 클래스에 오직 하나의 인스턴스만 가지는 패턴  
- 데이터베이스 연결 모듈에 주로 사용됨

### Java 코드
- 시스템에서 접근할 수 있는 스피커 클래스  
- 싱글톤으로 구현하는 이유:  
    - 시스템에서 볼륨을 올리면 연결되어 있는 수많은 스피커의 볼륨을 다 올려줘야함
```C
public class SystempSpeaker {

    // 하나만 있어야 하므로 static
    static private SystemSpeaker instance;

    private int volume;

    // 다른곳에서 생성하지 못하게 private
    private SystemSpeaker() {
        volume = 5;
    }

    public static SystemSpeaker getInstance() {
        if (instance == null) {
            instance = new SystemSpeaker();
        }
        return instance;
    }

    getVolume() {}
    setVolume() {}
}
```
```C
public class Main {
    public static void main(String[] args) {
        // 같은 인스턴스이므로 같은 스피커
        SystemSpeaker speaker = SystemSpeaker.getInstance();
        SystemSpeaker speaker2 = SystemSpeaker.getInstance();
    }
}
```

### 싱글톤 단점
- TDD(Test Driven Developement) 를 진행 시 문제 발생
- 모듈간의 결합도가 올라감( 결합도는 낮을수록 이상적인 모듈이고 응집도는 높을수록 이상적인 모듈이다 )
    - 해결방안으로 의존성 주입을 사용