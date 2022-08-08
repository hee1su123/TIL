## Interface & Abstract Class
- 공통점 : Interface와 Abstract Class는 상속(extends)받거나, 구현(implements)하는 Class가 Interface나 Abstract Class 안에 있는 Abstract Method를 구현하도록 강제하는 공통점을 가지고 있다.
- 차이점 : 사용 목적이 다르다.
### 인터페이스 Interface
- Interface는 부모, 자식 관계인 상속 관계에 얽메이지 않고, 공통 기능이 필요 할때, Abstract Method를 정의해놓고 구현(implements)하는 Class에서 각 기능들을 Overridng하여 여러가지 형태로 구현할 수 있기에 다형성과 연관되어 있다.
### 추상클래스 Abstract
- Abstract Class는 부모와 자식 즉, 상속 관계에서 Abstract Class를 상속(extends)받으며 같은 부모 Class(여기서는 Abstract Class)를 상속받는 자식 Class들 간에 공통 기능을 각각 구현하고, 확장시키며 상속과 관련되어 있다.
### Table
|Interface|Abstract Class|
|:---:|:---:|
|클래스 X|클래스 O|
|클래스와 관련 X|클래스와 관련 O|
|여러개 사용 가능|한개만 사용 가능|
|구현 객체와 같은 동작을 보장하기 위한 목적|상속 받아 기능을 확장하기 위한 목적|

