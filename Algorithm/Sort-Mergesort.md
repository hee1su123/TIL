# Mergesort(병합정렬)
- 분할 정복을 통해 배열을 정렬하는 알고리즘
- 비교 기반 정렬 알고리즘

## Mergesort 이해하기
- 리스트의 길이가 1 이하이면 이미 정렬된 것으로 본다
- 분할(divide): 정렬되지 않은 리스트를 절반으로 잘라 비슷한 크기의 두 부분 리스트로 나눈다(크기가 1 이하가 될 때까지 반복)
- 정복(conquer): 각 부분 리스트를 재귀적으로 합병 정렬을 이용해 정렬한다.
- 결합(combine): 두 부분 리스트를 다시 하나의 정렬된 리스트로 합병한다. 이때 정렬 결과가 임시배열에 저장된다.
- 복사(copy): 임시 배열에 저장된 결과를 원래 배열에 복사한다.

```
[1, 2, 3, 5] : 정렬된 배열 A
[4, 6, 7, 8] : 정렬된 배열 B
[] : 두 집합을 합칠 빈 배열 C


        ↓
1단계 : [1, 2, 3, 5] 
        ↓
       [4, 6, 7, 8] 
        1과 4를 비교
        1 < 4 이므로 1을 C 에 입력
     C:[1]

           ↓
2단계 : [1, 2, 3, 5] 
        ↓
       [4, 6, 7, 8] 
        2와 4를 비교
        2 < 4 이므로 2를 C 에 입력
     C:[1, 2]


              ↓
3단계 : [1, 2, 3, 5] 
        ↓
       [4, 6, 7, 8] 
        3과 4를 비교
        3 < 4 이므로 3을 C 에 입력
     C:[1, 2, 3]

                 ↓
3단계 : [1, 2, 3, 5] 
        ↓
       [4, 6, 7, 8] 
        5와 4를 비교
        5 > 4 이므로 4을 C 에 입력
     C:[1, 2, 3, 4]

                 ↓
3단계 : [1, 2, 3, 5] 
           ↓
       [4, 6, 7, 8] 
        5와 6을 비교
        5 < 6 이므로 5을 C 에 입력
     C:[1, 2, 3, 4, 5]


       [6, 7, 8] 은 하나씩 C 에 추가
     C:[1, 2, 3, 4, 5, 6, 7, 8]

```

## Mergesort 구현
```C
vector<int> merge(vector<int> arr1, vector<int> arr2) {
    vector<int> answer;
    int i = 0;
    int j = 0;
    
    while (i < arr1.size() && j < arr2.size()) {
        if (arr1[i] < arr2[j]) {
            answer.push_back(arr1[i]);
            i++;
        }
        else {
            answer.push_back(arr2[j]);
            j++;
        }
    }

    while (i < arr1.size())
        answer.push_back(arr1[i++]);
    
    while (j < arr2.size())
        answer.push_back(arr2[j++]);

    return answer;
}

vector<int> mergeSort(vector<int> arr) {
    if (arr.size() <= 1)
        return arr;
    
    int center = arr.size() / 2;
    vector<int> arr1;
    vector<int> arr2;

    for (int i = 0; i < center; i++)
        arr1.push_back(arr[i]);
    for (int i = center; i < arr.size(); i++)
        arr2.push_back(arr[i]);
    
    return merge(mergeSort(arr1), mergeSort(arr2));
}
```