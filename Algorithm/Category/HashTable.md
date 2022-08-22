# HashTable
- 자료를 해쉬함수 결과 값에 해당하는 위치에 배치하는 방법
- 대부분의 연산이 O(1) 의 시간 복잡도
- C++ 의 경우 기존의 std::map 은 해쉬테이블이 아닌 자동으로 정렬이 되는 이진트리의 한 종류인 레드블랙트리 구조로 구현되어 있었다. C++ 11 부터 std::unordered_map 이라는 컨테이너가 등장해 해쉬테이블을 이용해 구현되어 있다.

## 해쉬함수 : key 값의 데이터를 고정 크기 값으로 변환하는 함수
- 함수값 충돌을 최소화
- 테이블에 균일하게 분포
- 

## 로드팩터
```
n : 테이블에 저장 / k : 버킷의 개수
load factor = n / k
```
- 로드팩터가 증가할 수록 성능 감소
- 언어마다 해당 수치를 넘어가는 경우 해쉬테이블 공간 재할당

## 충돌
1. 개별 체이닝
- 충돌 발생 시 기존에 있던 데이터와 연결 리스트를 이용해 연결
- 충돌이 적은 경우 대부분의 탐색이 O(1)이지만 모든 해쉬 충돌이 발생 했다고 가정했을 경우 시간복잡도가 O(n)이 된다

2. 오픈 어드레싱
- 충돌 발생 시 탐사를 통해 빈 공간을 찾은 후 데이터를 삽입하는 방식
- 모든 데이터가 해쉬함수 결과값에 위치하지는 않는다
- 데이터가 뭉치는 경향을 보임
- 일반적으로 체이닝 방식보다 성능이 높지만 로드팩터가 일정 수치를 넘어가면 급격하게 성능이 감소한다.

## 해쉬테이블 구현
### 모든 메소드를 구현하진 못함. 반환값도 실제 STL 의 unordered_map 과는 다름.
```C
class MyHashMap {
private:
    vector<list<pair<int, int>>> *arr;
    int size = 10000;
    int hash;
    int flag;
public:
    MyHashMap() {
        arr = new vector<list<pair<int, int>>> (size);
    }
    ~MyHashMap() {
        delete arr;
    }
    
    void insert(int key, int value) {
        flag = 0;
        hash = key%size;
        
        for (list<pair<int,int>>::iterator it = (*arr)[hash].begin(); it != (*arr)[hash].end(); it++) {
            if ((*it).first == key) {
                (*it).second = value;
                flag = 1;
                break;
            }
        }
        if (flag == 0) {
            (*arr)[hash].push_back(make_pair(key, value));
        }
    }
    
    int find(int key) {
        flag = 0;
        hash = key%size;
        
        for (list<pair<int,int>>::iterator it = (*arr)[hash].begin(); it != (*arr)[hash].end(); it++) {
            if ((*it).first == key) {
                return (*it).second;
            }
        }
        return -1;
    }
    
    void erase(int key) {
        flag = 0;
        hash = key%size;
        
        for (list<pair<int,int>>::iterator it = (*arr)[hash].begin(); it != (*arr)[hash].end(); it++) {
            if ((*it).first == key) {
                (*arr)[hash].erase(it++);
            }
        }
    }
};
```