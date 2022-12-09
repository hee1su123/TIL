# RedBlack Tree
- 자가 균형 이진 탐색 트리
- 다음의 조건들을 만족한다
    - 모든 노드는 Red / Black 중 하나
    - 루트와 리프 노드는 모두 Black
    - 빨강노드가 연속으로 오지 못한다
    - 모든 리프노드까지 같은 개수의 Black 노드가 존재한다

## Insert 삽입 과정
- 삽입되는 노드는 항상 Red
- 삽입노드 N 기준
    - 부모노드 P
    - 조상노드 G
    - 삼촌노드 U(부모의 형제노드)
- Restructuring vs Recolouring
    - 삼촌노드 Black -> Restructuring
    - 삼촌노드 Red -> Recolouring

### Restructuring
- N, P, G 노드들을 오름차순 정렬
- 중간값을 부모로 만들고 나머지를 자식으로 재구성
- 부모노드를 Black, 자식들을 Red

### Recolouring
- G 를 Red 로 변경
- P, U 를 Black 으로 변경
    - 이때 G가 루트노드인 경우 다시 Black으로 변경
    - G를 Red 로 바꾸었을때 또다시 Red가 연속으로 오는 경우 Restructuring, Recolouring을 다시 진행(반복)

## Reference
- [블로그 - '레드-블랙 트리'](https://code-lab1.tistory.com/62)
