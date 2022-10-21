## Strategy Pattern (전략 패턴)
- 객체의 행위를 바꾸고 싶은 경우 '직접' 수정하지 않고 전략이라고 부르는 '캡슐화한 알고리즘'을 컨텍스트 안에서 바꿔주면서 상호 교체가 가능하게 만드는 패턴.
- 객체가 할 수 있는 행위들 각각을 전략 클래스로 만들어 놓고, 행위의 수정이 필요한 경우 전략을 바꾸는 것만으로 행위의 수정이 가능하도록 하는 패턴

### 코드 구현
사용 이유
- 컨텍스트 코드의 수정 없이 새로운 전략을 추가할 수 있다
- OCP의 원칙을 준수 할 수 있다
    - OCP (Open Closed Principle) : 기존의 코드를 변경하지 않으면서 기능을 추가할 수 있도록 설계가 되어야 한다.

Main
- 이동 시 버스 또는 기차를 이용
- 버스의 이동 전략이 도로 -> 선로 변경

```Java
public class Main {
    public static void main(String args[]){
        Moving train = new Train();
        Moving bus = new Bus();

        /*
            기존의 기차와 버스의 이동 방식
            1) 기차 - 선로
            2) 버스 - 도로
         */
        train.setMovableStrategy(new RailLoadStrategy());
        bus.setMovableStrategy(new LoadStrategy());

        train.move();
        bus.move();

        /*
            선로를 따라 움직이는 버스가 개발
         */
        bus.setMovableStrategy(new RailLoadStrategy());
        bus.move();
    }
}
```
<br></br>
Moving 클래스
- 운송 수단 클래스
- move() 메서드를 이용해 이동
    - move() 메서드를 직접 구현하지 않고 전략 클래스를 이용하여 설정한다.

```Java
public class Moving {
    private MovableStrategy movableStrategy;

    public void move() {
        movableStrategy.move();
    }
    public void setMovableStrategy(MovableStrategy movableStrategy) {
        this.movableStrategy = movableStrategy;
    }
}
```
```Java
public class Bus extends Moving {

}

public class Train extends Moving {

}
```
<br></br>
MovableStrategy 인터페이스
- 이동 전략을 표현하는 인터페이스

```Java
public class MovableStrategy {
    public void move();
}
```
```Java
public class RailLoadStrategy implements MovableStrategy{
    public void move(){
        System.out.println("선로를 통해 이동");
    }
}

public class LoadStrategy implements MovableStrategy{
    public void move() {
        System.out.println("도로를 통해 이동");
    }
}
```

### Reference
- '책' - [면접을 위한 CS 전공지식 노트]
- [[자바 디자인 패턴 이해] 1강 Strategy Pattern 강의](https://www.youtube.com/watch?v=UEjsbd3IZvA&t=622s)
- [[디자인패턴] 전략 패턴(Strategy Pattern) 블로그](https://victorydntmd.tistory.com/292)