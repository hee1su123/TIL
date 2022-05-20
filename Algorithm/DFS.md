# DFS(깊이 우선 탐색)
- 해당 정점을 방문하지 않은 인접 정점에 대해 재귀적으로 방문하거나 Stack을 이용.
- 처음엔 visit 이라는 주어진 그래프와 똑같이 생긴 그래프를 만들어서 방문 여부를 판단했지만, map 이나 set 같은 중복을 허용하지 않는 자료구조를 사용하여 해당 노드를 방문 했는지 안 했는지 찾는게 더 빠른 것 같다.(정확하진 않음. 상황에 따라 다를지도..?)

## DFS 구현 : 조합 nCk 반환

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