# @RestController
- @Controller 와 @ResponseBody 가 결합된 어노테이션
- @ResponseBody를 하위 메서드에 선언 하지 않아도 문자열과 JSON 등을 전송 가능

## 실제 겪은 오류
- Thymeleaf를 사용하여 String 값을 반환할때 RestController를 선언해놔서 계속 정상작동하지 않았음.