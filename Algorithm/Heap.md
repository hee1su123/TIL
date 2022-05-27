# Heap(힙정렬)
- 최대값, 최소값을 빠르게 찾기 위한 트리 기반의 자료구조
- 부모 노드의 값이 항상 자식 노드의 값보다 크다는 특징을 갖는다(최대 힙)
- 좌우 노드에 대한 정렬은 정의 하지 않는다
- 완전 이진 트리 형태를 갖으며, 배열에 빈틈 없이 배치가 가능하다. 또한 부모와 자식간의 계산을 편하게 하기 이해 첫 인덱스를 1로 사용한다(parent: x -> left: 2x / right: 2x+1)
## Heap 구현
- 삽입, 추출 알고리즘 구현
- 업힙 연산: 요소를 삽입하는 연산이다
    - 가장 낮은 위치에 요소를 삽입
    - 부모 노드와 비교 후 위치 조정
    - 위 과정 반복
- 다운힙 연산: 요소를 추출하는 연산
    - 루트를 추출
    - 가장 낮은 위치의 요소를 루트로 가져옴
    - 자식 노드와 비교 후 위치 조정 반복
```C
class Heap {
private:
    int Heap[1000] // 임의의 크기
    int cnt = 0;
public:
    int size() {
        return cnt;
    }
    
    void insert(int data) {
        
        Heap[++cnt] = data;
        int tmp;

        int current = cnt;
        int parent = cnt/2;

        while (cuurent > 1 && Heap[parent] < Heap[current]) {
            tmp = Heap[parent];
            Heap[parent] = Heap[current];
            Heap[current] = tmp;
            current = parent;
            parent = current/2;
        }
    }

    int extract() {

        int result = Heap[1];
        int tmp;

        tmp = Heap[1];
        Heap[1] = Heap[cnt];
        Heap[cnt] = tmp;
        cnt--;

        int current = 1;
        int child = 2*current;
        if (child+1 <= cnt) {
            child = (Heap[child] > Heap[child+1]) ? child : child+1;
        }

        whlie (child <= cnt && Heap[current] < Heap[child]) {
            tmp = Heap[current];
            Heap[current] = Heap[child];
            Heap[child] = tmp;

            current = child;
            child = 2*child;
            if (child+1 <= cnt) {
                child = (Heap[child] > Heap[child+1]) ? child : child+1;
            }
        }

        return result;
    }
}
```

