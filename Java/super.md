## super
- 자식 클래스가 부모 클래스로부터 상속받은 멤버를 참조할때 사용하는 변수. this를 사용하여 클래스 내 변수와 지역변수를 구분하듯이 부모 클래스와 자식 클래스의 멤버의 이름이 같은 경우 super을 사용하여 구분한다

```C
class testMain {
    public static void main(String[] args) {
        Child child = new Child();
        child.print();
    }
}

class Parent {
    int num = 1;
}

class Child extends Parent {
    int num = 2;

    void print() {
        System.out.println("x = " + x);
        System.out.println("this.x = " + this.x);
        System.out.println("super.x = " + super.x);
    }
}
```

출력결과
```
x = 2
this.x = 2
super.x = 1
```

## super()
- 부모 클래스의 생성자를 호출하는 메서드.
- Object 클래스를 제외하면 모든 클래스는 생성자 첫줄에 부모클래스 혹은 자신의 또다른 생성자를 호출한다. 이때 호출되는 함수가 super()

```C
class testMain {
    public static void main(String[] args) {
        Dog dog = new Dog();
        System.out.println("Dog.age = " + Dog.age);
        System.out.println("Dog.sex = " + Dog.sex);
        System.out.println("Dog.owner = " + Dog.owner);
    }
}

class Animal {
    String age;
    String sex;

    Animal(String age, String sex) {
        // Object 클래스의 생성자가 super() 로 자동 호출됨
        this.age = 1;
        this.sex = male;
    }
}

class Dog extends Animal {
    String owner;

    Dog(String age, String sex, String owner) {
        super(age, sex);
        this.owner = me;
    }
}
```

출력결과
```
Dog.age = 1
Dog.sex = male
Dog.owner = me
```