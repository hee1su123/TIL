## CORS(교차 출처 리소스 공유)
- Cross-Origin Resource Sharing
- 추가 HTTP 헤더를 사용하여, 한 출처에서 실행 중인 웹 애플리케이션이 다른 출처의 선택한 자원에 접근할 수 있는 권한을 부여하도록 브라우저에 알려주는 체제
- 웹 애플리케이션은 리소스가 자신의 출처와 다를 때 교차 출처 HTTP 요청을 실행한다

### 예시
https://domain-a.com 의 프론트 엔드 JavaScript 코드가 XMLHttpRequest를 사용하여 https://domain-b.com/data.json 을 요청하는 경우를 교차 출처 요청이라고 한다
- 보안상의 이유로 제한되어 있으며, 권한 부여를 해줘야 접근이 가능하다  


------ 
추가 필요