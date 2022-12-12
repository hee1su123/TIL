#  static 변수
- 객체 생성과 관련 없이 사용 가능
- 클래스 생성 시점에서 생성된다
    - Java 의 경우 JVM 이 필요한 대부분의 클래스를 처음부터 로딩하기 때문에 생성 시점이 JVM이 시작되는 시점으로 볼 수 있다.
- data 메모리 영역에 올라가며, Java 의 경우 GC 에 의해 관리가 되지 않는다
- 전역변수와 비슷하지만 get, set 함수를 만들 수 있다
- 여러 스레드에서 해당 전역 변수를 참조하게 되어 '동시성 문제' 가 발생 가능하다
- 단위 테스트 진행 시 전역으로 관리 되는 변수이기 때문에 정확한 테스트가 어려울 수 있다

## Reference
- [블로그 - 'static 변수'](https://jyoel.tistory.com/44)
- [블로그 - 'Java static & memory'](https://velog.io/@yonii/JAVA-Static%EC%9D%B4%EB%9E%80)
- [책 - 'CS 지식의 정석']