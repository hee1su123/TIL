# Factory Method Pattern (팩토리 메서드 패턴)
- 슈퍼 클래스(부모 클래스) 코드에서 서브 클래스(자식 클래스)에서 구현할 메서드를 호출해서 사용한다. 해당 메서드는 인터페이스 타입으로 오브젝트를 리턴하므로 정확한 클래스를 알지 못한다
- 위와 같이 오브젝트 생성 방법과 클래스를 결정할 수 있도록 미리 정의해둔 메서드를 '팩토리 메서드'라고 한다
- 팩토리 메서드를 사용하여 오브젝트나 클래스의 생성 로직을 서브 클래스로 위임하여 슈퍼 클래스의 나머지 로직과 독립 시키는 것을 '팩토리 메서드 패턴'이라고 한다

## Reference
책 - '토비의 스프링 3.1 Vol.1'