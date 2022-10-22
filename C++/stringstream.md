# stringstream
- ```#include <sstream>```에 정의되어 있다
- 문자열에서 원하는 자료형의 데이터를 추출할 때 사용

## string 에서 int 추출
```C
#include <iostream>
#include <string>
#include <sstream>

using namespace std;

int main() {
    int i;
    string str = "123 456 789"
    stringstream ss(str);
    //스트림을 초기화 할 수 있는 다른 방법
    //string stream ss;
    //ss.str(str);
    while (ss >> i) {
        cout << i << '\n';
    }
}
```
출력 결과

```
123 
456 
789
```

## string 에서 string 추출
```C
#include <iostream>
#include <string>
#include <sstream>

using namespace std;

int main() {
    string s;
    string str = "hello world\n456 789"
    stringstream ss(str);
    while (ss >> s) {
        cout << s << '\n';
    }
}
```
출력 결과

```
hello 
world 
456 
789
```