# DISTINCT
- DISTINCT 키워드는 옆에 온 모든 컬럼을 고려하여 중복 제거를 진행

```SQL
SELECT DISTINCT COL1, COL2
```
- COL1, COL2 값이 모두 동일한 row 들을 중복으로 적용
- DISTINCT 는 SELECT 구문에 여러 컬럼명이 올 때, 하나에 대해서만 적용이 불가능

```SQL
SELECT (DISTINCT COL1), COL2
```
위의 SQL 구문은 오류