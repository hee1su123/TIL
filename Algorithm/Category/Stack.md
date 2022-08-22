# Stack
- LIFO(Last-in-First-out, 후입선출) 구조.
- top() : 가장 최근 삽입된 값 가져옴
- push() : 요소를 컬렉션에 추가함
- pop() : 가장 최근 삽입된 요소 제거함

## Stack 구현
### 구현이 정확하지 않아 공부하면서 조금씩 수정/보완 할 예정이다...!
1. 배열을 이용한 구현
```C
template <class T> class stack_array {
private:
    vector<T> arr;
public:
    T top() {
        return arr.back();
    }
    void push(T val) {
        arr.push_back(val);
    }
    void pop() {
        arr.pop_back();
    }
}
```

2. 연결 리스트를 이용한 구현
```C
struct ListNode {
    T data;
    Node *next;
}

template <class T> class stack_list {
private:
    ListNode *last = new ListNode();
public:
    T top() {
        return last->data;
    }
    void push(T val) {
        last = new ListNode(val, last);
    }
    void pop() {
        ListNode *tmp = last;
        last = last->next;
        delete(tmp);
    }
}
```