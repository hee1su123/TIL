## SpringFramework Annotations

### @Around

### @Aspect

### @Autowired
- 생성자/필드/setter 3가지 경우, 해당 객체에 해당하는 빈을 찾아 주입한다
- 기본값이 true 로 의존성 주입 실패 시 실행 실패
- 생성자가 하나일 경우 생략 가능

### @Bean
- 개발자가 컨트롤 할 수 없는 외부 라이브러리를 Bean 으로 등록 하기 위한 어노테이션.
- 쉽게 말해 @Component 를 사용 할 수 없어서 사용하는 어노테이션. 개발자가 직접 생성한 클래스에는 선언이 불가능

### @Component
- 직접 작성/컨트롤 가능한 클래스를 Bean 에 등록하기 위해 사용하는 어노테이션.

### @Configuration
- IOC Container 에게 해당 클래스를 Bean 구성 클래스 임을 알려주는 역할
- 클래스에 선언 하면 IOC 에서 Bean 으로 만들 무엇인가 해당 클래스에 있다고 인식하며, 메서드에 @Bean 을 선언하여 사용 가능하게 된다.

### @PathVariable

### @RestController
- @Controller 와 @ResponseBody 가 결합된 어노테이션
- @ResponseBody를 하위 메서드에 선언 하지 않아도 문자열과 JSON 등을 전송 가능

#### 실제 겪은 오류
- Thymeleaf를 사용하여 String 값을 반환할때 RestController를 선언해놔서 계속 정상작동하지 않았음.

### @SpringBootTest

### @Transactional
- 테스트 코드에 붙은 경우, 서비스 등에 붙은 경우 다른 기능을 보여줌
- JPA를 사용하는경우 꼭 필요한 어노테이션.
- 영속 상태를 유지하기 위한 어노테이션.


## Jpa Annotations

### @Column
- 컬럼 매핑
- 속성
    - name: 필드와 매핑할 테이블의 컬럼 이름 / 기본값 객체의 필드 이름
    - insertable, updatable: 등록, 변경 가능 여부 / 기본값 TRUE
    - nullable(DDL): null 값의 허용 여부를 설정. false로 설정 시 DDL 생성 시에 not null 제약조건이 붙는다. 
    - unique(DDL): @Table의 uniqueConstraints와 같지만 한 컬럼에 간단히 유니크 제약조건을 걸 때 사용한다.
    - length(DDL): 문자 길이 제약조건, String타입에만 사용 / 기본값 255
    - columnDefinition(DDL): 데이터베이스 컬럼 정보를 직접 줄 수 있다
        - ex) varchar(100) default 'EMPTY'
    - precision, scale(DDL): BigDecimal 타입에서 사용한다(BigInteger도 사용할 수 있다). precision은 소수점을 포함한 전체 자릿수를, scale은 소수의 자릿수다. 참고로 double, float 타입에는 적용되지 않는다. 아주 큰 숫자나 정밀한 소수를 다루어야 할 때만 사용한다 / 기본값 precision=19, scale=2

### @Temporal
- 날짜 타입 매핑(자바8 부터 LocalDate, LocalDateTime 지원)

### @Enumerated
- enum 매핑
- 속성 
    - EnumType.ORDINAL: enum 순서를 저장(기본값)
    - EnumType.STRING: enum 이름을 저장
    - ORDINAL 사용시 매우 위험함. 처음 0번으로 저장되면 나중에 해당 값이 1이 되어도 데이터베이스에는 그대로 0으로 저장되어 있어 혼란을 가져옴.

### @Lob
- Lob: 가변의 길이를 갖는 큰 데이터를 저장하는데 사용되는 데이터형
- @Lob: 일반적인 데이터베이스에서 저장하는 길이인 255개 이상의 문자를 저장하고 싶을 때 지정
    - BLOB: byte[], java.sql.BLOB
    - CLOB: String, char[], java.sql.CLOB

### @Transient
- 특정 필드를 컬럼에 매핑하지 않음

### @Id
- 기본키 직접 할당

### @GenartedValue
- 기본키 자동 생성
- 종류
- IDENTITY: 데이터베이스에 위임(다른 데이터베이스를 사용하면 다른 방법으로 생성됨)
    - Jpa는 커밋 시점에서 INSERT SQL 을 실행하므로 AUTO_INCREMENT는 데이터베이스에 INSERT SQL 을 실행하기 전까지는 ID 값을 알 수 없다. 이를 해결하기 이해 보통의 경우와 달리 em.persist() 실행 시 예외적으로 INSERT SQL을 실행하여 ID 값을 받아온다.
- SEQUENCE: 유일한 값을 순서대로 생성하는 특별한 데이텁이스 오브젝트
    - allocationSize 기본값 50: 위의 IDENTITY 와 비슷하게 DB를 호출하여 SEQUENCE에서 ID 값을 받아오기 전까지는 ID 값을 알 수 없다. 이를 해결하기 위해 값을 50씩 올려 DB를 호출하지 않고 메모리에서 값을 증가 시켜 ID 값을 받고, 50을 넘을때 한번 더 호출하여 50개를 더 받아온다.
- TABLE: 키 생성 전용 테이븡를 만들어서 시퀀스를 흉내내는 전략. 모든 데이터베이스에 적용 가능하지만 성능이 좋지 않음
- AUTO