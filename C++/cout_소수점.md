# 소수점 출력
[예시]
```C++
const double dNum = 1234.56789;
 
// 1 : Default 전체 자리수 6 + 반올림
cout << dNum << endl;
 
// 2 : precistion(5) 를 사용해 자리수 5로 조절 (반올림 하지 않음)
cout.precision(5);
cout << dNum << endl;
 
// 3 : fixed 를 사용해 소숫점 아래 값을 고정
cout << fixed;
cout.precision(6);
cout << dNum << endl;
 
// 4 : cout.unsetf(ios::fixed) == cout << fixed / 같은 표현
cout.unsetf(ios::fixed);
cout << dNum << endl;
 
// 5
cout.setf(ios::fixed);
cout.precision(5);
cout << dNum << endl;
```

[출력]
```
// 1
1234.57

// 2
1234.6

// 3
1234.567890

// 4
1234.57

// 5
1234.56789
```