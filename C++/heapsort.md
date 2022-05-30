# C++ Heap 구조 정렬 알고리즘

## make_heap
- void make_heap(iterator first, iterator last)
- void make_heap(iterator first, iterator last, comp)
- 구간 [first, last) 을 힙 구조로 변경하며, comp 를 인자로 받지 않으면 최대힙의 형태로, 인자로 받으면 이를 조건자로 활용하여 힙 구조를 형성.

## push_heap
- void push_heap(iterator first, iterator last)
- void push_heap(iterator first, iterator last, comp)
- 구간 [first, last)의 힙 구조에 원소를 추가. push_back 함수를 먼저 사용 하고 사용한다. comp 를 인자로 받지 않으면 최대힙의 형태로, 인자로 받으면 이를 조건자로 활용하여 힙 구조를 형성.

## pop_heap
- void pop_heap(iterator first, iterator last)
- void pop_heap(iterator first, iterator last, comp)
- 구간 [first, last)의 힙 구조에 원소를 제거. 사용 후 pop_back 함수를 사용해준다. comp 를 인자로 받지 않으면 최대힙의 형태로, 인자로 받으면 이를 조건자로 활용하여 힙 구조를 형성.

## sort_heap
- void sort_heap(iterator first, iterator last)
- void sort_heap(iterator first, iterator last, comp)
- 힙 구조를 구간 [first, last) 에서 정렬시킨다. comp 를 인자로 받지 않으면 오름차순으로, 인자로 받으면 이를 조건자로 활용하여 정렬한다.


## 함수 사용해보기
```C++
vector<int> v = {10,20,30,40,50,60};

make_heap(v.begin(), v.end());
// v = {60,50,30,40,20,10}


v.push_back(35);
// v = {60,50,30,40,20,10,35}
push_heap(v.begin(), v.end());
// v = {60,50,35,40,20,10,30}


pop_heap(v.begin(), v.end());
// v = {30,50,35,40,20,10,60}
// v = {50,40,35,30,20,10,60}
v.pop_back();
// v = {50,40,35,30,20,10}


sort_heap(v.begin(), v.end())
// v = {10,20,30,35,40,50}
```