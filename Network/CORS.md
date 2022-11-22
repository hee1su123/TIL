# CORS(Cross-Origin Resource Sharing, 교차 출처 리소스 공유)
- CORS error : 브라우저의 SOP(Same-Origin Policy, 동일 출처 정책)을 위반하면 생기는 에러이다
    - 동일 출처 : 프로토콜, 호스트, 포트가 모두 같은 경우
    - SOP : 다른 출처의 리소스 접근을 금지하는 정책
- 보안상의 이유로 제한되어 있으며, 정해진 규칙에 맞다면 자원 공유를 허락해준다

## 교차 출처 요청
- https://domain-a.com 의 프론트 엔드 JavaScript 코드가 XMLHttpRequest를 사용하여 https://domain-b.com/data.json 을 요청하는 경우를 교차 출처 요청이라고 한다

## 동작 원리
- 클라이언트에서 HTTP 요청 헤더에 Origin 을 담아 전달
- 서버 응답 헤더에 Acces-Control-Allow-Origin 값을 받아 요청 헤더 Origin 과 비교
- 단순요청(Simple Request)
    - HTTP Reqeuest Method 가 GET, POST, HEAD 중 하나일 경우
    - 가능한 헤더
        - Accept
        - Accept-Language
        - Content-Language
        - Content-Type: application/x-www-form-urlencoded, multipart/form-data, text/plain
- 예비요청(Preflighted Request)
    - 단순 요청이 아닌 경우
    - 먼저 Options 메서드로 요청을 보낸후 응답을 받고 CORS요청을 보내도 되는지 판단 후 본래의 CORS 요청을 보냄

## CORS 에러 해결 방법
- 서버에서 Acces-Control-Allow-Origin 세팅
```Javascript
res.setHeader('Access-Control-Allow-origin', '*'); // 전체 허용
res.setHeader('Access-Control-Allow-origin', 'https://domain-a.com');// 특정 출처 허용

```

## Reference
- [Wiki - 'CORS'](https://ko.wikipedia.org/wiki/%EA%B5%90%EC%B0%A8_%EC%B6%9C%EC%B2%98_%EB%A6%AC%EC%86%8C%EC%8A%A4_%EA%B3%B5%EC%9C%A0)
- [Mozila - 'CORS'](https://developer.mozilla.org/ko/docs/Web/HTTP/CORS)
- [Mozila - 'CORS errors'](https://developer.mozilla.org/ko/docs/Web/HTTP/CORS/Errors)
- [Beomy 블로그 - 'CORS'](https://beomy.github.io/tech/browser/cors/)
- [난기수 블로그 - 'CORS'](https://nankisu.tistory.com/70)
- [DevScroll 블로그 - 'CORS'](https://inpa.tistory.com/entry/WEB-%F0%9F%93%9A-CORS-%F0%9F%92%AF-%EC%A0%95%EB%A6%AC-%ED%95%B4%EA%B2%B0-%EB%B0%A9%EB%B2%95-%F0%9F%91%8F#CORS_(Cross_Origin_Resource_Sharing))