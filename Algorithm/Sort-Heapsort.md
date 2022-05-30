# Heap(힙정렬)
- 최대값, 최소값을 빠르게 찾기 위한 트리 기반의 자료구조
- 부모 노드의 값이 항상 자식 노드의 값보다 크다는 특징을 갖는다(최대 힙)
- 좌우 노드에 대한 정렬은 정의 하지 않는다
- 완전 이진 트리 형태를 갖으며, 배열에 빈틈 없이 배치가 가능하다. 또한 부모와 자식간의 계산을 편하게 하기 이해 첫 인덱스를 1로 사용한다(parent: x -> left: 2x / right: 2x+1)

## Heap 삽입, 추출 이해하기
- 삽입: 9를 추가하는 방법

```
0. 주어진 힙

      8      
    6   3      
   4 2 1     

1. 맨 마지막에 원소를 삽입

      8      
    6   3    
   4 2 1 9   

2-1. 부모 노드와 비교 후 자리 변경

      8      
    6   3    
   4 2 1 9   

      8      
    6   9    
   4 2 1 3   

2-2. 다시 부모 노드와 비교 후 자리 변경

      8     
    6   9   
   4 2 1 3   

      9     
    6   8    
   4 2 1 3   

3. 힙의 특성을 유지 한 상태로 값을 삽입 완료

      9      
    6   8    
   4 2 1 3   
```

 - 추출: 항상 맨 위의 루트 노드가 제거 된다
 ```
0. 주어진 힙

      8     
    6   7    
   2 5 4 3   

1. 루트 노드와 맨 끝에 있는 원소를 교체

      8      
    6   7    
   2 5 4 3   

      3      
    7   6    
   2 5 4 8   

2. 맨 뒤에 있는 원소를 삭제 후 반환 

      3      
    6   7    
   2 5 4 X   

3-1. 변경된 노드를 더 큰 자식 노드와 비교
두 자식이 둘다 현재 노드보다 값이 더 크다면 어떤 자식과 해도 상관 없지만, 하나의 원칙을 정해두고 일정하게 변경 하는 것이 좋음

      3     
    6   7    
   2 5 4     

      7      
    6   3    
   2 5 4    

3-2. 3의 값이 들어 있는 노드를 위의 과정을 반복

      7     
    6   3   
   2 5 4    

      7     
    6   4    
   2 5 3     


4. 힙의 특성을 그대로 유지한 상태로 원소 삭제 완료

      7      
    6   4   
   2 5 3     
 ```

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

