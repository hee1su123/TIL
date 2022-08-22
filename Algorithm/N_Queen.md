# N-Queen 문제

## 이차원 배열 풀이 방법
- N*N 이차원 배열 생성 후 백트래킹 진행
    - 한 행에 하나의 퀸만 넣으므로 같은 행에서는 검사 하지 않음
    - 같은 열에서 퀸 유무 검색
    - 대각선에서 퀸 유무 검색
## 일차원 배열 풀이 방법 (이것 때문에 TIL 작성)
- 크기 N 의 배열 한개
    - 퀸이 존재하는 위치의 index 는 다 달라야 하는 것이므로 하나의 배열에서 유무를 판단 가능
- 크기 2*N-1 배열 두개
    - 좌측 상단에서 우측 하단으로 가는 대각선 && 우측 상단에서 좌측 하단으로 가는 대각선 을 의미하는 배열 2개
    - 같은 대각선 상에 하나의 퀸만 존재하므로 각 대각선 방향마다 하나의 배열로 유무를 판단 가능
    - 대각선 배열의 index 를 이해하는 것이 핵심인 것 같음. 처음 보면 이해하기 힘듬...
        - 대각선 index 는 N*N 행렬이 있다는 가정하에 행과 열의 index 조합으로 만들어짐

```C
using namespace std;

int N;
int visit1[16] = { 0 };
int visit2[31] = { 0 };
int visit3[31] = { 0 };
int cnt = 0;
int result = 0;

void queen(int a, int b) {
    if (a == N) {
        result++;
        return;
    }

    for (int i = 1; i <= N; i++) {
        if (visit1[i] == 0 && visit2[a + i] == 0 && visit3[a - i + N] == 0) {
            visit1[i] = 1;
            visit2[a + i] = 1;
            visit3[a - i + N] = 1;
            queen(a + 1, i);
            visit1[i] = 0;
            visit2[a + i] = 0;
            visit3[a - i + N] = 0;
        }
    }
}

int main() {
    cin.tie(NULL);
    ios::sync_with_stdio(false);

    cin >> N;
    queen(0, 0);
    cout << result << '\n';

    return 0;
}
```