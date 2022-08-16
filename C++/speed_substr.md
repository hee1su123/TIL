## substr 함수 속도

[백준 11478번 서로 다른 부분 문자열의 개수](https://www.acmicpc.net/problem/11478)

```C
    for (int i = 1; i <= S.size(); i++) {
        for (int j = 0; j <= S.size() - i; j++) {
            st.insert(S.substr(j, i));
        }
    }
```

```C
string make_str(string& S, int start, int size) {
    string answer;
    for (int i = 0; i < size; i++) {
        answer.push_back(S[start + i]);
    }
    return answer;
}

    for (int i = 1; i <= S.size(); i++) {
        for (int j = 0; j <= S.size() - i; j++) {
            st.insert(make_str(S, j, i));
        }
    }
```

위의 코드는 통과한 코드  
아래 코드는 시간 초과 코드  
차이는 substr 과 make_str -> substr 함수는 그럼 어떻게 구현되어 있는거지??