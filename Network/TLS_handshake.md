# TLS handshake
- RSA key exchange algorithm
- DH(Diffie-Hellman) key exchange Algorithm

## RSA
- ClientHello : 클라이언트가 보내는 핸드셰이크 개시 메시지
    - 클라이언트 TLS 버전
    - 지원하는 암호화 방식
    - 'Client Random Data'(바이트 문자열)
- ServerHello : 서버가 응답으로 보내는 메시지
    - 공개키를 포함한 SSL 인증서
    - 서버가 선택한 암호화 방식
    - 'Server Random Data'(바이트 문자열)
- 인증 : 클라이언트가 서버로부터 전달받은 SSL인증서를 발행기관을 통해 검증
- pre-master secret : 무작위 바이트로 세션키 생성에 사용됨
    - SSL 인증서를 통해 전달받은 공개키로 암호화 한 후 서버로 전달
- 클라이언트와 서버 둘다 'Client Random Data', 'Server Random Data', 'pre-master secret' 를 사용하여 세션키를 생성
- 클라이언트 준비 완료 : 클라이언트가 세션키로 암호화된 "완료" 메시지 전송
- 서버 준비 완료 : 서버가 세션키로 암호화된 "완료" 메시지 전송
- 핸드셰이크 완료

## DH
- RSA 핸드셰이크의 경우에서처럼 클라이언트가 'pre-master secret'을 생성하여 서버로 전송하는 대신, 클라이언트와 서버가 서로 교환한 DH 매개변수를 사용하여 일치하는 예비 마스터 암호를 별도로 계산

## Reference
- [책 - 'CS 전공지식 노트']
- [CloudFlare - 'TLS 핸드셰이크'](https://www.cloudflare.com/ko-kr/learning/ssl/what-happens-in-a-tls-handshake/)
- [블로그 - 'TLS handshake'](https://sunrise-min.tistory.com/entry/TLS-Handshake%EB%8A%94-%EC%96%B4%EB%96%BB%EA%B2%8C-%EC%A7%84%ED%96%89%EB%90%98%EB%8A%94%EA%B0%80)