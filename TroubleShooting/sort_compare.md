# sort compare 함수 오류
- 정렬 시 compare 함수를 만들어서 사용하는 경우 나오는 오류

## Case1
- 실행 시 오류가 나옴...
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

- 정상적으로 실행된다
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

## Case2
- 실행 시 오류
```C
bool compare(int a, int b) {
    if (a > b)
        return false;
    return true
}
```

- 정상 작동
```C
bool compare(int a, int b) {
    if (a < b)
        return true;
    return false;
}
```

## Case3 : string 으로 된 숫자 대소 비교
- 실행 시 오류
```C++
bool comp(string s1, string s2) {
    if (s1.size() < s2.size())
        return true;
    else if (s1.size() == s2.size()) {
        for (int i = 0; i < s1.size(); i++) {
            if (s1[i] < s2[i])
                return true;
            else if (s1[i] > s2[i])
                return false;
        }
        return true;
    }
    else {
        return false;
    }
}
```
- 정상 작동
```C++
bool comp(string s1, string s2) {
    if (s1.size() < s2.size())
        return true;
    else if (s1.size() == s2.size()) {
        for (int i = 0; i < s1.size(); i++) {
            if (s1[i] < s2[i])
                return true;
        }
    }
    return false;
}
```

## 이유
- 아직 찾지 못함...
- 같은 값일때는 false 를 반환해야 하는건가...?