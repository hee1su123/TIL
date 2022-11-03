# TCP 프로토콜
- 연결형, 신뢰성 전송 프로토콜
- 가상 회선 패킷 교환 방식
- 3 way handshaking 을 통해 논리적 연결을 성립
- 신뢰성을 보장하기 위해 오류제어, 흐름제어, 혼잡제어 등을 실행
- header 가 비교적으로 큼
- PDU(protocol data unit) : segment

## 3 way handshaking
- 서버와 클라이언트의 논리적 연결 수립 과정
    - Client -> Server : Syn(synchronization) / 연결 요청
    - Server -> Client : Syn + Ack(Acknowledgement) / 연결 요청 + 연결 승인
    - Client -> Server : Ack / 연결 승인
    - [ISN](./ISN.md)

## 4 way handshaking
- 서버와 클라이언트의 연결 해제 과정
    - Client -> Server : Fin 세그먼트
    - Server -> Client : Ack
    - Server -> Client : Fin 세그먼트
    - Client -> Server : Ack
    - [4 way handshake](./4way_handshake)

## 오류제어, 흐름제어
- 흐름제어 : 데이터의 보내는 속도와 받는 속도의 균형 유지
- 오류제어 : 체크섬, Time-out 을 통해 무결성 및 오류 검사
    - 재전송 : 시간 초과로 전달되지 않은 데이터 및 훼손된 데이터 재전송
    - 정렬 : 전달 받은 데이터를 순서에 맞게 정렬
    - 폐기 : 중복된 데이터 감지 및 폐기

## Header
- 20 ~ 60 bytes 로 고정된 값이 아님 (가변적)
- checksum(체크섬) 2 bytes 를 포함하며 이를 통해 무결성 검사 진행