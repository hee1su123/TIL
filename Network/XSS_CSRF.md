## XSS(크로스 사이트 스크립팅)
- 공격하려는 사이트에 스크립트를 넣는 기법. 사용자로부터 입력받은 값을 검사하지 않고 사용할 경우 쉽게 나타날 수 있는 현상이다. 공격 방법에 따라 Stored XSS, Reflected XSS 로 나뉘며 Reflected XSS 의 경우 브라우저에서 차단해 성공 가능성이 상대적으로 낮다. 

## CSRF(Cross-Site Request Fogery)
- 사용자가 의도와 무관하게 수정,삭제,등록 등 특정 웹사이트에 요청을 보내게 하는 공격 방법. 웹사이트는 믿을 수 있는 사용자로부터 보내진 요청이라 판단해 공격에 노출된다.

## 대응방안
- XSS
    - 쿠키에 중요한 정보를 담지 않고 서버에 저장.
    - 정보를 암호화
    - httponly 속성
    - secure coding
- CSFR
    - Referrer 검증
    - Security Token 사용