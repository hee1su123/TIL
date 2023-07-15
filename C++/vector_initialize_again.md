# vector 초기화 방법
- 생성자
- push_back()
- 초기화 목록

## 생성자
```C++
int main() {
    int N, M, num;

    cin >> N >> M >> num;
    
    vector<vector<int>> vec(N, vector<int>(M, num));

    return 0;
}
```
## push_back() 함수
```C++
int main() {
    int N, M, num;
    
    cin >> N >> M >> num;
    
    vector<vector<int>> vec;

    for (int i = 0; i < N; i++) {
        vector<int> tmp;
        for (int j = 0;j < M; j++) {
            tmp.push_back(num);
        }
        vec.push_back(tmp);
    }

    return 0;
}
```
## 초기화 목록
```C++
int main() {
    int N, M, num;
    
    cin >> N >> M >> num;
    
    vector<vector<int>> vec {
        {num, num, num},
        {num, num, num},
        {num, num, num}
    };

    return 0;
}
```
## Reference
- [2차원 벡터 초기화](https://www.techiedelight.com/ko/initialize-two-dimensional-vector-cpp/)