# Transaction
- 정의: 트랜잭션이란 데이터베이스에서 수행하는 작업의 단위로 하나 이상의 쿼리문을 포함하며 데이터의 무결성을 유지하기 위해 ACID “원자성, 일관성, 고립성, 지속성” 4가지 특성을 만족해야합니다.

## ACID
- Atomicity / 원자성
    - all or nothing
    - 트랜잭션의 작업이 모두 수행되거나 모두 수행되지 않아야 하는 성질
- Consistency / 일관성
    - 데이터베이스의 상태가 일관성이 있어야하는 성질
    - 예시) 데이터 타입이 바뀌거나 이름이 없는 고객 정보가 추가되거나 등등
- Isolation / 독립성, 고립성
    - 트랜잭션이 수행될 때 다른 트랜잭션이 끼어들지 못하는 성질
- Durability / 지속성
    - 성공적으로 수행된 트랜잭션은 영원히 반영되어야 하는 성질

## Reference
- 책 - '면접을 위한 CS 전공지식 노트'
- [Hanamon - ACID](https://hanamon.kr/%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4-%ED%8A%B8%EB%9E%9C%EC%9E%AD%EC%85%98%EC%9D%98-acid-%EC%84%B1%EC%A7%88/)
