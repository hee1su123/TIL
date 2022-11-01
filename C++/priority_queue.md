# C++ STL priority_queue
- push(), pop(), top(), empty(), size() 메서드 제공
- 기본값으로 사용 시 vector 컨테이너가 사용된다
- 기본값으로 사용 시 최대 힙으로 구현된다

## 작은 값이 우선순위가 되기 위한 조건
```C++
priority_queue<int, vector<int>, greater<int>> pq;
```
- int : Type of elements
- vector<int> : Type of underlying container
- greater<int> (Compare) : strict weak ordering (정렬 기준)

## 구조체 내부 연산자 오버로딩
```C++
struct Student {
    int id;
    int math, eng;
    Student(int num, int m, int e) : id(num), math(m), eng(e) {}    // 생성자

    // 학번이 작은것이 Top 을 유지 한다
    bool operator<(const Student s) const {
        return this->id > s.id;
    }
};

int main() {
    priority_queue<Student> pq;
}
```
- operator< 에 따라 우선순위가 정해짐
- 연산자 오버로딩

## Comparator 사용
```C++
struct Student {
    int id;
    int math, eng;
    Student(int num, int m, int e) : id(num), math(m), eng(e) {}    // 생성자
};

struct cmp {
    bool operator()(Student a, Student b) {
        return a.id < b.id;
    }
};

int main() {
    priority_queue<Student, vector<Student>, cmp> pq;
}
```

## Reference
- [Cplusplus 공식 문서](https://cplusplus.com/reference/queue/priority_queue/)
- [priority_queue 블로그](https://kbj96.tistory.com/15)