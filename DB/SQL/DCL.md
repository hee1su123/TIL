# DCL Data Control Language
- 데이터의 보안, 무결성, 회복, 병행제어 등을 정의하는 언어
- 데이터베이스 관리자(DBA) 가 데이터 관리를 목적으로 사용
- COMMIT, ROLLBACK, GRANT, REVOKE
## GRANT / REVOKE
- 데이터베이스 관리자가 데이터베이스 사용자에게 권한을 부여하거나 취소
### 사용자 등급 지정 및 해제
```
GRANT 사용자등급 TO 사용자_ID_리스트 [IDENTIFIED BY] 암호;
REVOKE 사용자등급 FROM 사용자_ID_리스트;
```
### 테이블 및 속성에 대한 권한 부여 및 취소
```
GRANT 권한_리스트 ON 개체 TO 사용자 [WITH GRANT OPTION];
REVOKE [GRANT OPTION FOR] 권한_리스트 ON 개체 FROM 사용자 [CASCADE];
```
- 권한 종류 : ALL, SELECT, INSERT, DELETE, UPDATE, ALTER
- WITH GRANT OPTION : 부여받은 권한을 다른 사용자에게 다시 부여할 수 있는 권한을 부여
- GRANT OPTION FOR : 다른 사용자에게 권한을 부여할 수 있는 권한을 취소
- CASCADE : 권한 취소 시, 권한을 부여 받았던 사용자가 다른 사용자에게 부여한 권한도 연쇄적으로 취소

## COMMIT
- 트랜잭션 처리가 정상적으로 완료된 후, 트랜잭션이 수행한 내용을 데이터베이스에 반영하는 명령어

## ROLLBACK
- 변경되었으나 아직 COMMIT 되지 않은 모든 내용을 취소하고 데이터베이스를 이전 상태로 되돌리는 명렁어