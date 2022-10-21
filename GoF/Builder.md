## Builder Pattern
복잡한 단계를 거쳐 생성되는 객체의 구현을 서브 클래스에게 넘겨주는 패턴

### 코드 구현
<br></br>
Main
- 복잡한 Computer 객체의 생성 단계를 ComputerFactory 에게 넘김

```Java
public class Main {
    public static void main(String[] args) {

        // 팩토리 생성
        ComputerFactory factory = new ComputerFactory();

        // 팩토리에 특정한 설계도 입력
        factory.setBlueprint(new MacBlueprint());

        // 특정 설계도 기반으로 컴퓨터 만들고 반환
        factory.setComputer();
        Computer computer = factory.getComputer();

        System.out.println(computer.toString());
    }
}
```
<br></br>

ComputerFactory
- 컴퓨터 설계도 ( Blueprint ) 를 갖고 있음
- 설계도를 기반으로 컴퓨터를 만듬 ( setComputer )
- 만든 컴퓨터 반환 ( getComputer )

```Java
public class ComputerFactory {

    private Blueprint print;

    public void setBlueprint(Blueprint print) {
        this.print = print;
    }

    public void setComputer() {
        print.setCpu();
        print.setRam();
        print.setStorage();
    }

    public Computer getComputer() {
        return print.getComputer();
    }
}
```
<br></br>

Computer
```Java
public class Computer {
    private String cpu;
    private String ram;
    private String storage;

    public Computer(String cpu, String ram, String storage) {
        super();
        this.cpu = cpu;
        this.ram = ram;
        this.storage = storage;
    }

    --- getter & setter 생략 ---
    ----------------------------

    @Override
    public String toString() {
        return cpu + ", " + ram + ", " + storage;
    }
}
```
<br></br>

Blueprint ( =Abstract Builder )
```Java
public abstract class Blueprint {

    abstract public void setCpu();
    abstract public void setRam();
    abstract public void setStorage();

    abstract public Computer getComputer();

}
```
<br></br>

MacBlueprint ( =Concrete Builder )
```Java
public class MacBlueprint extends Blueprint {

    private Computer computer;

    public Computer("default","default","default");

    @Override
    public void setCpu() {
        computer.setCpu("i7");
    }

    @Override
    public void setRam() {
        computer.setRam("8G");
    }

    @Override
    public void setStorage() {
        computer.setStorage("256G ssd");
    }

    @Override
    public Computer getComputer() {
        return computer;
    }
}
```
<br></br>

### Template Method Pattern 과의 구분
- Template Method Pattern 는 각 하위 클래스가 정의해야 하는 몇 가지 메서드를 정의하는 방법에 불과하다

- Builder Pattern 은 더 복잡한 객체를 생성할 때 사용

- 우리가 서로 자동차 모델을 만들고 싶다고 가정. 각 모델에는 다른 엔진, 조명 등이 있습니다.

    - Template Method Pattern 을 사용한다면, 우리는 자동차의 각 조합에 대해 하나의 클래스를 만들거나, 몇몇 좋지 않은 상속 계층을 사용해야 했다. 이러한 많은 메서드에는 중복된 코드가 포함되어 있습니다.

    - Builder Pattern 으로 우리는 대신 다른 부품들을 가지고 그것들을 완전한 자동차로 조합할 수 있습니다. 따라서 필요에 따라 모든 모델의 엔진을 재사용할 수 있으며 차량의 각 부품을 맞춤 제작할 수도 있습니다.


### Reference
- [[자바 디자인 패턴 이해] 7강 Strategy Pattern 강의](https://www.youtube.com/watch?v=UEjsbd3IZvA&t=622s)