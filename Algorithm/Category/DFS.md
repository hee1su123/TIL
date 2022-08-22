# DFS(깊이 우선 탐색)
- 해당 정점을 방문하지 않은 인접 정점에 대해 재귀적으로 방문하거나 Stack을 이용.

## DFS 구현 : 조합 nCk 반환
1. 재귀를 이용한 구현
```C
vector<vector<int>> answer;
vector<int> tmp;

void dfs(int n, int k, int num, int idx) {
    if (idx == k) {
        answer.push_back(tmp);
        return;
    }
    for (int i = num+1; i <= n; i++) {
        tmp.push_back(i);
        dfs(n, k, i, idx+1);
        tmp.pop_back();
    }
}

vector<vector<int>> nCk(int n, int k) {
    dfs(n, k, 0, 0);
    return answer;
}
```