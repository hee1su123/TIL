# C++ STL multiset
```multiset<Class T, Class Compare, Class Alloc>```
- set 과 달리 중복된 key 값을 저장 가능
- 삽입 시 자동 정렬

## comparator 사용법1 : 구조체 cmp 연산자 operator() 오버로딩
```C++
struct cmp {
    bool operator() (const pair<int, int> a, const pair<int, int> b) const{
        if (a.first == b.first)
            return a.second < b.second;
        return a.first < b.first;
    }
};

int main() {

    multiset<pair<int,int>, cmp> arr;

    return 0;
}
```

## comparator 사용법2 bool 반환 함수
```C++
bool cmp(pair<int, int> a, pair<int, int> b) {
    if (a.first == b.first)
        return a.second < b.second;
    return a.first < b.first;
}

int main() {

    multiset<pair<int,int>, cmp> arr;

    return 0;
}
```

## upper_bound, lower_bound
```C++

int main(void){
    multiset<int> ms;
 
    ms.insert(1);    
    ms.insert(2);
    ms.insert(3);
    ms.insert(4);        
    ms.insert(5);
    ms.insert(6);
    ms.insert(7);
    ms.insert(7);
    ms.insert(7); // 7만 세번 삽입
    ms.insert(8);
 
    for(auto iter = ms.begin(); iter != ms.end(); iter++){
        cout << *iter << " " ;
    }
    cout << endl;
    // 출력 : 1 2 3 4 5 6 7 7 7 8
    
    auto start = ms.lower_bound(7);
    cout << "lower_bound : " << *start << endl;
    // 출력 : lower_bound : 7
   
    auto end = ms.upper_bound(7);
    cout << "upper_bound : " << *end << endl;
    // 출력 : upper_bound : 8

    return 0;
}
```

## Reference
- [Cplusplus 공식 문서](https://cplusplus.com/reference/set/multiset/?kw=multiset)
- [Multiset 블로그](https://blockdmask.tistory.com/80)