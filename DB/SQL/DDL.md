# DDL Data Define Language
- 데이터 정의어
- DB 구조, 데이터 형식, 접근 방식 등 DB 를 구축하거나 수정할 목적으로 사용하는 언어
- CREATE, ALTER, DROP 3가지 유형

## CREATE
SCHEMA, DOMAIN, TABLE, VIEW, INDEX 를 정의

### CREATE SCHEMA
- SCHEMA 를 정의
    - SCHEMA : 데이터베이스에서 자료의 구조, 자료의 표현, 자료간의 관계를 형식 언어로 정의한 구조
    ```
    CREATE SCHEMA 스키마명 AUTHORIZATION 사용자_id;
    ```

### CREATE DOMAIN
- DOMAIN 을 정의
    - DOMAIN
        - 하나의 속성이 취할 수 있는 동일한 유형의 원자값들의 집합
        - 특정 속성에서 사용할 데이터의 범위를 사용자가 정의하는 사용자 정의 데이터 타입
    ```
    CREATE DOMAIN 도메인명 [AS] 데이터_타입
        [DEFAULT] 기본값
        [CONSTRAINT] 제약조건명 [CHECK] 범위값
    ```

### CREATE TABLE
- TABLE 을 정의
    - TABLE
        - 데이터베이스의 설계 단계에서는 테이블을 REALATION 이라고 부르며, 조작이나 검색 단계에서는 TABLE 이라고 부른다
        - 보통 구분 없음
    ```
    CREATE TABLE 테이블명
        (속성명 데이터_타입 [DEFAULT] 기본값 [NOT NULL],
        [PRIMARY KEY] 기본키_속성명,
        [UNIQUE KEY] 대체키_속성명,
        [FOREIGN KEY] 외래키_속성명
            [REFERENCES] 참조테이블(기본키_속성명)
            [ON DELETE] 옵션
            [ON UPDATE] 옵션,
        [CONSTRAINT] 제약조건명 [CHECK] 조건식);
    ```

### CREATE VIEW
- VIEW 를 정의
    - VIEW : 물리적으로 구현되지는 않지만 뷰 정의가 시스템 내에 정의되어 있다가 VIEW 이름을 사용하면 실행 시간에 VIEW 정의가 대체되어 수행됨
    ```
    CREATE VIEW 뷰명 AS SELECT 속성명
    ```

### CREATE INDEX
- INDEX 를 정의
    - INDEX : 검색 시간을 단축시키기 위해 만든 보조적인 데이터 구조
    ```
    CREATE [UNIQUE] INDEX 인덱스명 
    ON 테이블명 (속성명[ASC/DESC]) 
    [CLUSTER];
    ```

## ALTER
TABLE 에 대한 정의를 변경하는데 사용
```
ALTER TABLE 테이블명 ADD 속성명 데이터_타입 [DEFAULT 기본값];
ALTER TABLE 테이블명 ALTER 속성명 [SET DEFAULT 기본값];
ALTER TABLE 테이블명 DROP COLUMN 속성명 [CASCADE];
```
- ADD : 새로운 속성을 추가
- ALTER : 특정 속성의 DEFAULT 값을 변경
- DROP COLUMN : 특정 속성을 삭제

### ALTER TABLE

## DROP
SCHEMA, DOMAIN, TABLE, VIEW, INDEX 를 삭제
```
DROP SCHEMA 스키마명 [CASCADE | RESTRICT];
DROP DOMAIN 도메인명 [CASCADE | RESTRICT];
DROP TABLE 테이블명 [CASCADE | RESTRICT];
DROP VIEW 뷰명 [CASCADE | RESTRICT];
DROP INDEX 인덱스명 [CASCADE | RESTRICT];
DROP CONSTRAINT 제약조건명;
```
- CASCADE : 제거할 요소를 참조하는 다른 개체를 함께 제거
- RESTRICT : 다르 ㄴ개체가 제거할 요소를 참조 중일 때는 제거를 취소

### DROP