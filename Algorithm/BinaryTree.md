# BinaryTree(이진트리)
- 거꾸로 세운 나무처럼 보이는 계층형 비선형 자료 구조
- Node, Root, Level, Parent Node, Child Node, Leaf, Sibling, Depth
- 트리는 순환구조를 갖지 않는 그래프의 종류
- 이진트리는 이 중에서도 자식노드가 최대 두개인 트리

## BinaryTree Depth 찾기 구현
- BFS 를 사용하여 이진트리의 깊이를 찾는다
```C
struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;
}

int Depth(TreeNode *root) {
    queue<TreeNode*> Que;
    TreeNode *tmp;
    int depth = 0;
    int len;

    if (root == NULL)
        return 0;
    Que.push(root);
    while (!Que.empty()) {
        depth++;
        len = Que.size();
        for (int i = 0; i < len; i++) {
            tmp = Que.front();
            if (tmp->left != NULL)
                Que.push(tmp->left);
            if (tmp->right != NULL)
                Que.push(tmp->right);
            Que.pop();
        }
    }
    return depth;
}
```