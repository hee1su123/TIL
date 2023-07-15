# Url Encoding, Decoding in Spring Boot

## 문제 발생

```HTML
<form id="myForm" action="#" th:action="@{/test}" method="post">
    <input type="text" id="wordInput" name="word" placeholder="Enter a word">
    <button type="submit">Send</button>
</form>
```
```Java
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestDto {
    private String word;
}
```
```Java
@PostMapping("/test")
public String save(@ModelAttribute TestDto dto) {
    System.out.println(dto.getWord());
    return "index";
}
```
- form 데이터로 " " 공백 문자를 전송 시 서버에서 "%20" 으로 전달되는 현상 발생.

## 해결 방안 및 발생 이유
- 프로젝트마다 다르겠지만 해당 프로젝트에서는 Interceptor 나 Filter 어딘가에서 URL Encoding 이 자동으로 적용되고 있는 것 같다.
```Java
@PostMapping("/test")
public String save(@ModelAttribute TestDto dto) throws UnsupportedEncodingException {
    dto.setWord(URLDecoder.decode(dto.getWord(), "UTF-8"));
    System.out.println(dto.getWord());
    return "index";
}
```
- dto setter 를 이용해 word 를 URLDecoding 해서 세팅
- Exception 이 나오는 것을 위해 ```throws UnsupportedEncodingException``` 추가