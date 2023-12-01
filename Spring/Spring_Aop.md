# AOP (Aspect Oriented Programming / 관점 지향 프로그래밍)



## Terminology (용어)

#### **Aspect(관점)**

- 여러 클래스에 걸쳐있는 코드를 모듈화한 것
- Spring AOP에서는 Aspect 를 @Aspect 어노테이션이 붙은 일반 클래스(@AspectJ 스타일)를 사용하여 구현

#### **Join point(조인 포인트)**

- Advice 가 적용될 수 있는 위치

- 메소드의 실행이나 예외의 처리와 같은 프로그램의 실행 중인 시점
- Spring AOP 에서는 조인 포인트가 항상 메소드 실행을 나타낸다

#### **Advice(어드바이스)**

- 타겟에 제공할 부가기능 모듈(메서드)

- 특정 Join point 에서 Aspect 에 의해 취해진 행동
- **“around”, “before”, “after”** 등 다양한 유형의 Advice 가 있다
- 많은 AOP 프레임워크들은, Advice 를 인터셉터로 모델링하고 조인 포인트 주위에 인터셉터 체인을 유지한다

#### **Point cut(포인트컷)**

- Join point 와 일치하는 술어 (Advice 를 적용할 타겟을 선별하는 정규 표현식)
- Advice 는 Point cut 표현식과 연관되어 있으며 Point cut (예: 특정 이름의 메소드 실행)에 의해 일치하는 모든 조인 포인트에서 실행된다
- Join point 개념은 AOP의 핵심이며, Spring은 기본적으로 AspectJ 의 Point cut 표현식 언어를 사용한다

#### **Target object(타겟 객체)**

- 하나 이상의 Aspect 에 의해 Advice 되는 객체
- Spring AOP는 런타임 프록시를 사용하여 구현되므로, 이 객체는 항상 프록시된 객체이다

#### **AOP proxy(관점 지향 프록시)**

- AOP 프레임워크에 의해 생성된 객체로 Aspect 를 적용하고 Advice 를 실행하기 위해 생성되는 객체
- Spring 프레임워크에서는 AOP 프록시가 JDK 동적 프록시 또는 CGLIB 프록시이다

#### **Weaving(위빙)**

- Weaving은 Pointcut에 의해서 결정된 타겟의 Join Point에 부가기능(Advice)를 삽입하는 과정을 뜻한다

- 컴파일 시간(예: AspectJ 컴파일러 사용), 로드 시간, 또는 런타임에 수행된다

- Spring AOP와 같은 순수 Java AOP 프레임워크는 런타임에 Weaving 을 수행

  

## Type (종류)

- Before
- After Returning
- After Throwing
- After Finally
- Around



## 개념

- 접근 제어, 기능 추가를 위해 Spring AOP 에서 Proxy 를 활용한다
- 빈 등록 시 @Aspect 클래스를 조회해, pointcut 에 해당하는 객체를 프록시로 만들어 빈으로 등록한다
- 해당 객체를 직접 호출하지 않고, 프록시 객체가 호출되어 Advice 가 실행(execute) 된 후 실제 객체가 호출된다 (기능 추가)



## Reference

- [스프링 공식문서 - AOP](https://docs.spring.io/spring-framework/reference/core/aop.html)

- [블로그 - 'AOP'](https://catsbi.oopy.io/fb62f86a-44d2-48e7-bb9d-8b937577c86c)

- [블로그 - 'Weaving'](https://velog.io/@dnjwm8612/AOP-Weaving-Proxy)

