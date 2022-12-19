# Stack overflow 오류
- 스택 포인터가 스택의 경계를 넘어설 때 일어나는 오류
- 발생 원인
    - 무한루프, 재귀적 무한반복 시 발생
    - 너무 큰 지역변수
    - 존재하지 않는 인덱스 참조
- 해결방안
    - 컴파일러 옵션에서 stack 영역의 크기 증가
    - 지역변수의 크기를 줄임
    - 지역변수를 전역변수로 사용

## Reference
- [위키피디아 - '스택 오버플로'](https://ko.wikipedia.org/wiki/%EC%8A%A4%ED%83%9D_%EC%98%A4%EB%B2%84%ED%94%8C%EB%A1%9C)
- [위키피디아 - 'Stack overflow'](https://en.wikipedia.org/wiki/Stack_overflow)
- [블로그 - 'Stack overflow](https://penguin-kim.tistory.com/15)
- [블로그 - 'Stack overflow란?'](https://keepdev.tistory.com/21)