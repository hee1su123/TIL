# Template Method Pattern (템플릿 메서드 패턴)
- 상속을 통해 기능을 확장할 때 사용하는 방법이다
- 자주 변경되며 확장 가능성이 있는 기능을 서브 클래스에서 만들도록 하는 방법이다
- 슈퍼 클래스(부모 클래스)에서 미리 추상메서드(abstract) 또는 오버라이드 가능한 메서들르 정의해두며, 서브 클래스에서 구현
- hook 메서드 : 선택적 오버라이드가 가능한 메서드

## 코드 구현
```Java
public abstract class SuperClass {
    public void templateMethod() {
        // 기본 알고리즘 코드
        hookMethod();
        abstractMethod();
    }

    protected hookMethod() {  }
    public abstract void abstractMethod();

}

public class SubClass extends SuperClass {
    protected void hookMethod() {

    }

    public void abstractMethod() {

    }
}

```

## Reference
- 책 - '토비의 스프링 3.1 Vol.1'