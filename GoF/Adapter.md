## Adapter Pattern
- 한 클래스의 인터페이스를 클라이언트에서 사용하고자 하는 다른 인터페이스로 변환한다.
- 인터페이스 호환성 문제 때문에 쓸 수 없었던 클래스들을 연결해서 사용 가능.

<br></br>

### Diagram
![어댑터 패턴 다이어그램](../Image/GoF/Adapter.jpg)

<br></br>

### 어댑터 패턴 구현 예시
- 두 수에 대한 연산을 수행하는 Adatper 라는 객체 생성
    - 두배 반환 : Float twice(Float)
    - 절반 반환 : Float half(Float)
- Math 클래스에 두배와 절반을 구하는 함수가 이미 구현되어 있음

<br></br>

- Math 클래스
```Java
public class Math {
    public static double twoTime(double num) {
        return num * 2;
    }

    public static double half(double num) {
        return num / 2;
    }
}
```

<br></br>

- Adapter 인터페이스
```Java
public interface Adapter {
    public Float twice(Float f);
    public Float half(Float f);
}
```

- AdapterImpl 클래스
```Java
public class AdapterImpl implements Adatper {

    @Override
    public Float twice(Float f) {
        // Float 자료형을 Math.twoTime 함수에 바로 사용 할 수 없어서 형 변환하여 사용 후 다시 형변환 후 반환
        return (float) Math.twoTime(f.doubleValue());
    }

    @Override
    public Float half(Float f) {
        return (float) Math.half(f.doubleValue());
    }
}
```

<br></br>

- Main 클래스
```Java
public class Main {
    public static void main(String[] args) {
        Adapter adapter = new AdapterImpl();

        System.out.println(adapter.twice(100f));
        System.out.println(adapter.half(100f))
    }
}
```

- 원래의 Math 클래스의 메서드가 새로 생기거나 바뀌었을 때, Adapter 인터페이스나 Main 클래스를 변경하지 않고 AdapterImpl 만 수정하며 적용 가능하다.
- 로그를 찍는 기능을 추가하고 싶을 때 AdapterImpl 에 추가하면 적용 가능하다.

### Reference
- [[자바 디자인 패턴 이해] 2강 Adapter Pattern 강의](https://www.inflearn.com/course/%EC%9E%90%EB%B0%94-%EB%94%94%EC%9E%90%EC%9D%B8-%ED%8C%A8%ED%84%B4/unit/3173)
