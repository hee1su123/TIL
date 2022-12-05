# k진수 소수 구하기
[문제링크](https://school.programmers.co.kr/learn/courses/30/lessons/92335)

## 트러블 슈팅
- 소수 판별법이 너무 오래 걸려서 생겼던 문제
    - ```for (int i = 2; i < num; i++)``` 을 사용 시 너무 오래 걸림
    - ```num / i``` 로 변경 후에는 i 값이 1 만큼 증가한 후에 나뉜 값이라 오류가 생김
    - ```num / (i-1)``` 로 변경 후 정답
```C
bool check_prime(string number) {
    long long num = stoll(number);

    if (num == 2 || num == 3 || num == 5)
        return true;
    else if (num == 1 || num == 4)
        return false;        
    
    for (int i = 2; i < num / (i-1); i++)
        if (num % i == 0)
            return false;
    
    return true;
}
```