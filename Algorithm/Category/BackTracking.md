# BackTracking
- DFS, BFS 를 더 효율적으로 만들어 주는 기법
- 필요 없는 경우의 수를 가지치기 하여 시간 복잡도를 줄이는 방법
- 탐색 중 오답을 만나면 이전 분기점으로 돌아가며 오답을 만난 분기점 이후로는 탐색을 실행하지 않는다
- 대표적으로 N-Queen 문제로 설명 가능

## N-Queen 문제를 이용한 BackTracking 구현
- 구현 방법 중 가장 신기한 점은 2차원 배열을 사용하지 않았다는 점이다. 처음 구현을 시도 했을때 당연히 체스판을 만들기 위해 이차원 배열을 이용하여 체스판을 만든 이후에 퀸을 배치하는 방식을 사용하였다. 구현 후 풀이를 봤을 때 사용한 1차원 배열을 이용한 백트래킹 구현은 매우 신선한 풀이였다.
- check 함수를 뺄셈을 이용 후 절댓값 함수 abs 를 이용해 비교한 점도 내가 한 풀이와 달랐다.

```C
#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

vector<int> col;
int N = 0;
int answer = 0;

bool check(int level) {
    for(int i = 0; i < level; i++)
        if(col[i] == col[level] || abs(col[level] - col[i]) == level - i)
            return false;
    return true;
}

void backtracking(int idx) {
    if(idx == N)
        answer++;
    else {
        for(int i = 0; i < N; i++) {
            col[idx] = i;
            if(check(idx))
                backtracking(idx+1);
        }
    }
}

int main() {
    cin >> N;
    col.assign(N, 0);
    backtracking(0);
    cout << answer;
}
```