# Stream
- lambda 를 사용할 수 있는 기술
- 데이터를 담는 저장소가 아니다. 데이터의 흐름
- 병렬처리가 가능
    - 병렬 처리 : 하나의 작업을 둘 이상의 작업으로 나누어 동시에 진행
- 스트림으로 처리하는 데이터는 오직 한번만 처리된다
- 제한이 없다. 실시간으로 데이터가 들어오는 것도 가능
- 스트림이 처리하는 데이터 소스를 변경하지 않는다
- 0 또는 다수의 중개 오퍼레이션과 하나의 종료 오퍼레이션으로 구성된다
    - 중개 오퍼레이션은 lazy 하며 종료 오퍼레이션이 실행 될때 처리된다

## Stream 생성
- 스트림 인스턴스 생성

## Intermediate Operations (중개 오퍼레이션)
- Stream 을 반환
- filter, map 등으로 원하는 방법으로 데이터를 가공한느 단계 

## Terminal Operations (종료 오퍼레이션)
- Stream 을 반환하지 않음
- 최종 결과를 만들어내는 단계

## Reference
- [블로그 - 'Java Stream 총정리' ](https://futurecreator.github.io/2018/08/26/java-8-streams/)
- [인프런 - '백기선 더 자바, Java 8'](https://www.inflearn.com/course/the-java-java8/dashboard)