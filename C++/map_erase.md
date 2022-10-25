# STL map 의 erase 함수
- 다른 STL 컨테이너들과 같은 방식으로 다루면 '런타임 에러' 발생

## 오류 예시
```C++
for (auto iter = mp.begin(); iter != mp.end(); iter++) {
    mp.erase(iter);
}
```
- mp.erse(iter) 실행 후 반복자 iter 는 무효화 된다
- 해당 반복자는 이미 지워진 요소를 가리키고 있으므로 ++ 연산이 불가능

## 해결방안
```C++
for (auto iter = mp.begin(); iter != mp.end();) {
    mp.erase(iter++);
}
```
- erase 함수를 사용과 동시에 ++ 연산자를 사용 시 내부적으로 복사 후 증가를 시키기 때문에 erase에 의해 제거된 원소를 참조 하지 않고 복사된 것을 참조하게 됨.