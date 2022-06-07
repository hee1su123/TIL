## Annotations

### @Around

### @Aspect

### @Autowired
- 생성자가 하나일 경우 생략 가능

### @Component

### @Configuration

### @PathVariable

### @RestController
- @Controller 와 @ResponseBody 가 결합된 어노테이션
- @ResponseBody를 하위 메서드에 선언 하지 않아도 문자열과 JSON 등을 전송 가능

#### 실제 겪은 오류
- Thymeleaf를 사용하여 String 값을 반환할때 RestController를 선언해놔서 계속 정상작동하지 않았음.

### @SpringBootTest

### @Transactional
- 테스트 코드에 붙은 경우, 서비스 등에 붙은 경우 다른 기능을 보여줌
- JPA를 사용하는경우 꼭 필요한 어노테이션. JPA는 @Transactional 어노테이션 안에서만 작동한다