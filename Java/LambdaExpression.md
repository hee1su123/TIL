# 람다식
- Java 에서 함수형 프로그래밍을 구현하는 방식
- 클래스를 생성하지 않고 함수의 호출만으로 기능 수행

## 함수형 프로그래밍(FP/Functional Programing)
- 순수 함수를 구현하고 호출함으로써 외부 자료에 영향을 주지 않고 매개변수만을 사용
- 매개변수만을 사용하여 수행되므로 외부에 영항을 미치지 않아 병렬처리 가능
- 안정적인 확장성 있는 프로그래밍 방식

## 람다식 문법
- str -> {System.out.println(str);} : 하나의 매개변수는 괄호 생략 가능. 두개는 생략 불가능
- str -> System.out.println(str); : 중괄호 내부 하나의 문장인 경우 중괄호 생략 가능
- str -> {return str.length();} : 하나의 문장이지만 return문은 중괄호 생략 불가능
- str -> str.length(); : return과 중괄호를 같이 생략은 가능

## 람다식 예제
```C
public interface MyNumber {
    int getMaxNumber(int num1, int num2);
}

public class Test {
    public static void main(String[] args) {
        MyNumber maxNum = (x, y) -> x >= y ? x : y;

        int max = maxNum.getMaxNumber(10,20); // 결과값: 20
    }
}
```
- 람다식을 위한 함수형 인터페이스는 함수를 하나만 선언하며 어노테이션 @FunctionalInterface 을 추가
- 하나의 함수가 더 존재하면 어느 함수를 호출해야 하는지 모호하게 되어 컴파일 에러가 나올 수 있음
- 람다식 표현을 사용하게 되면 익명 내부 클래스에 내부적으로 구현부가 자동으로 작성됨
- ex) 기존의 (인터페이스 -> 클래스 구현부 -> 메서드 사용) 객체지향 프로그래밍에서 클래스 구현부가 내부적으로 생기므로 더 간결함
- 함수를 변수처럼 사용가능