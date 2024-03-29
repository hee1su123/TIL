# DML Data Manipulate Language
- 데이터베이스 사용자가 저장된 데이터를 실질적으로 관리하는데 사용
- SELECT, INSERT, DELETE, UPDATE

## 삽입문
- 새로운 튜플을 삽입할 때 사용
```
INSERT INTO 테이블명([속성명1, 속성명2, ...])
VALUES (데이터1, 데이터2, ...);
```
- 대응하는 속성과 데이터는 개수와 유형이 일치해야한다
- 모든 속성 사용 시 생략 가능
- SELECT 문을 사용하여 다른 테이블의 검색 결과를 삽입할 수 있다
    - <사원> 테이블에 있는 편집부의 모든 튜플을 편집부원 테이블에 삽입
    ```
    INSERT INTO 편집부원(이름, 생일)
    SELECT 이름, 생일
    FROM 사원
    WHERE 부서='편집';
    ```

## 삭제문
- 기본 테이블에 있는 튜플들 중에서 특정 튜플(행)을 삭제할 때 사용
```
DELETE
FROM 테이블명
[WHERE] 조건;
```
- 모든 레코드를 삭제할 때는 WHERE 생략
- 테이블 구조는 남아 있기 때문에 DROP 과 다름

## 갱신문
- 기본 테이블에 있는 튜플들 중에서 특정 튜플의 내용을 변경할 때 사용
```
UPDATE 테이블명
SET 속성명=데이터
[WHERE] 조건;
```

