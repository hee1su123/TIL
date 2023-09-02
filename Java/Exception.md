# Checked and Unchecked Exception + Error / 예외처리 + 에러

##  Checked Exception 
- 반드시 처리 되어야 한다 : try catch / throws
- 프로그래머의 실수가 아님
- 예상가능하며 예방할 수 없는 경우에 사용
- ex: file not readable / network connection

## Unchecked Exception (RuntimeException, Error)
- Checked Exception 이 아닌 경우에 사용
### RuntimeException
- 코드 로직에 의해 주로 발생
- 프로그래머의 실수인 경우가 많음
- ex: ArithmeticException / ArrayIndexOutOfBoundsException

### Error
- 시스템 비정상적 상황이 발생한 경우
- ex: 메모리 부족 / 스택오버플로


## More
- C# 의 경우 Checked Exception 은 삭제 되었으며, 사용될 필요 없다고 생각되는 경우도 많다고 한다
- they are used when you want to force the user of your API to think how to handle the exceptional situation (if it is recoverable) - 'stack over flow'


## Reference
- [StackOverFlow - 'Understanding checked vs unchecked exceptions in java](https://stackoverflow.com/questions/6115896/understanding-checked-vs-unchecked-exceptions-in-java)
- [StackvOverFlow - 'When to choose checked and unchecked exceptions'](https://stackoverflow.com/questions/27578/when-to-choose-checked-and-unchecked-exceptions)
- [StackOVerFlow - 'Why throws exception necessary'](https://stackoverflow.com/questions/11589302/why-is-throws-exception-necessary-when-calling-a-function)
- [블로그 - 'Java Exception 정리'](https://devlog-wjdrbs96.tistory.com/351)