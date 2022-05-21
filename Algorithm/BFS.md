# BFS(넓이 우선 탐색)
- 해당 정점에 인접한 모든 정점을 방문한 후에 다음 정점으로 진행하며, Queue를 이용하여 구현한다

## BFS 구현
- 처음 Queue 에 첫 노드를 입력
- Queue의 맨 앞 노드가 방문되지 않은 경우에 인접한 노드들을 Queue 뒤에 입력
- 해당 노드를 방문했다고 표기 후 Queue 에서 pop
- 반복
```C
Queue<> que;
vector<bool> visit;

void bfs(vector<vector<int>> graph) {
    que.push(Node);
    while (que.first() is not visited) {
        while (!que.emtpy()) {
            que.push(NextNodes);
        }
        visit(Node) = true;
        que.pop(Node);
    }
}
```