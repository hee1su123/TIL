# Java 변수의 종류
- 인스턴스 변수
- 클래스 변수
- 지역 변수(로컬 변수)

```Java
public class test {

	int iv; // 인스턴스 변수
	static int cv; // 클래스 변수
	
	void method() {
		int lv; // 지역 변수
	}
}
```

![변수](../Image/%EB%B3%80%EC%88%98%EC%9D%98%EC%A2%85%EB%A5%98.jpg)

## 인스턴스 변수
- 인스턴스가 생성될 때 생성
- 각각의 인스턴스마다 고유의 값을 갖는다

## 클래스 변수
- 인스턴스 변수에 static 을 붙여준다
- 모든 인스턴스가 공통된 값을 갖는다
- 클래스가 로딩될 때 생성되므로 메모리에 한번만 적재된다

## 지역 변수
- 메서드 내에서 선언된다
- 메서드 실행 시 메모리를 할당 받으며 실행이 끝나면 소멸된다

## Reference
- [블로그 - '선언 위치에 따른 변수의 종류](https://itmining.tistory.com/20)