# BinarySearchTree(이진탐색트리)
- 이진트리 중에서도 일정한 규칙에 의해 정렬된 트리
- 이진탐색트리의 정렬 규칙은 한 노드를 기준으로 그 노드의 왼쪽에 위치한 모든 노드(서브트리)의 값은 해당 노드보다 값이 작아야한다.
- 평균적 시간복잡도가 O(logN) 이며 균형이 잡히지 않은 트리는 O(N) 까지 가능하다. 이를 해결하기 위해 자가 균형 이즌탐색트리를 사용한다. 이 트리는 자동으로 트리의 균형을 맞춰준다
## BinarySearchTree 구현 : 정렬된 배열을 균형이진검색트리로 변환
- 균형이란 모든 서브트리 간 깊이 차이가 1이하인 트리
```C
struct TreeNode {
     int val;
     TreeNode *left;
     TreeNode *right;
 };

class Solution {
public:
    TreeNode* sortedArrayToBST(vector<int>& nums) {
        TreeNode *answer = new TreeNode();
        vector<int> left;
        vector<int> right;
        
        for (int i = 0; i < nums.size()/2; i++)
            left.push_back(nums[i]);
        for (int i = nums.size()/2+1; i < nums.size(); i++)
            right.push_back(nums[i]);
        
        answer->val = nums[nums.size()/2];
        if (left.size() != 0)
            answer->left = sortedArrayToBST(left);
        if (right.size() != 0)
            answer->right = sortedArrayToBST(right);
        
        return answer;
    }
};
```