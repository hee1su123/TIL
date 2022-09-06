# sort compare 함수 오류
- 정렬 시 compare 함수를 만들어서 사용하는 경우 나오는 오류

```C
bool desc(int a, int b) {
    if (a >= b)
        return true;
    return false;
}

int main() {
    vector<int> arr = {1, 2, 3, 4, 4, 5};
    sort(arr.begin(), arr.end(), desc);
}
```
- 실행 시 오류가 나옴...

```C
bool desc(int a, int b) {
    if (a > b) // 등호가 없게 수정
        return true;
    return false;
}

int main() {
    vector<int> arr = {1, 2, 3, 4, 4, 5};
    sort(arr.begin(), arr.end(), desc);
}
```
- 정상적으로 실행된다

## 이유
- 아직 찾지 못함...