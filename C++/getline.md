# getline 함수를 사용해  입력값 받기
- cin 의 경우 공백, 개행 입력 시 공백 이전까지의 값만 받아들이며, 띄어 쓰기 혹은 개행이 포함된 문장을 입력 받기 위해서는 getline() 함수를 사용해야함

## istream 라이브러리의 cin.getline() vs string 라이브러리의 getline()
- 두개인 이유 : C++ 의 경우 문자열이 '\0' 으로 끝나는 char* 형식의 cstring 과 std::string 을 따르는 string 두개가 존재하므로, 문자열 처리 방법 또한 두개가 필요.

- cin.getline() : '\0' 이 붙는 char* 형식의 문자열 입력 방법

- getline() : std::string 문자열 입력 방법

## cin.getline()
```C
istream& getline(char* s, streamsize n);
istream& getline(char* s, streamsize n, char delim);
```
- s : C 형식의 문자열을 저장할 배열 포인터
- n : 저장할 문자의 최대 개수
- delim : 제한자로 이 문자에 도달 시 추출 중단

## std::getline()
```C
istream& getline(istream& is, string& str);
istream& getline(istream& is, string& str, char delim);
```
- s : 입력스트림 오브젝트 ex) cin
- str : 입력받은 문자열을 저장할 string 객체
- delim : 제한자로 이 문자에 도달시 추출 중단

## std::getline 에서 cin.ignore() 필요성
[입력값]
```
1
hello
```
### Case1
```C++
int main() {
    int n;
    string str;
    cin >> n;
    getline(cin, str);
    cout << n << ' ' << str;
}
```
[출력값]  
```
1
```  
hello 를 쓰기 전에 1이 출력되면서 프로그램이 종료된다

### Case2
```C++
int main() {
    int n; 
    string str;
    cin >> n;
    cin.ignore();
    getline(cin, str);
    cout << n << ' ' << str;
}
```
[출력값]  
```
1 hello
```
정상적으로 출력된다

## Reference
[Cpp reference site](https://www.tutorialspoint.com/what-is-the-use-of-cin-ignore-in-cplusplus)
[getline 블로그](https://novlog.tistory.com/78)
