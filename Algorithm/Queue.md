# Queue

## Queue 구현
### 구현이 정확하지 않아 공부하면서 조금씩 수정/보완 할 예정..
1. 배열을 이용한 구현
- 원형 큐(Circular Queue) 로 구현한다. 그 이유는 선형 큐를 구현하게 되면 push/pop 을 반복 하며 큐의 내용이 배열의 끝에 도착하고, 큐의 내용을 배열의 시작점으로 복사해야하는 필요성이 생긴다. 원형 큐로 구현하게 되면 배열의 복사를 할 필요가 없어진다.
- front 와 rear 이 같은 경우는 비어있는 큐를 의미한다
- 큐를 넣을 배열 선언시 크기를 n으로 선언하면 실제로 사용 가능한 큐의 크기는 n-1 이다. 그 이유는 rear 와 front 가 같을 때 비어있는 큐를 의미하는데, 모든 배열이 꽉찬 상태에서 rear이 한칸 앞으로 더 나아가면 front와 같은 위치를 가르키지만 큐가 비어있지 않게 되기 때문이다.
```C
template <class T> class queue_array {
private:
    // 배열을 원 형태로 만들어 주는 함수
    int inclement(int n) { return (n+1) % size; }
    int declement(int n) { return (n+size-1) % size; }

    int size;
    int front;
    int rear; // 삽입할 위치
    int cnt;

    T *arr;
public:
    queue_array(int size) {
        array = new T[size];
        front = rear = 0; // 빈 상태의 큐를 의미
        cnt = 0;
        this.size = size;
    }
    ~queue_arr() {
        delete [] arr;
    }

    T front() {
        return arr[front];
    }
    T rear() {
        return arr[declement(rear)];
    }
    void push(T &val) {
        arr[rear] = val;
        rear = inclement(rear);
        cnt++;
    }
    T pop() {
        T ret = arr[front];
        front = inclement(front);
        cnt--;
        return ret;
    }
}
```

2. 연결 리스트를 이용한 구현
- 스택을 구현 했을때 사용한 싱글리스트가 아닌 더블리스트를 사용하여 구현한다.
```C
template <class T> class queue_list {
private:
    list<T> lst;
public:
    queue_list() {}
    ~queue_list() {}

    T front() {
        return lst.front();
    }
    T rear() {
        return lst.back();
    }
    void push(T &val) {
        lst.push_back(val);
    }
    T pop() {
        int ret = lst.front();
        lst.pop_front();
        return ret;
    }
}
```