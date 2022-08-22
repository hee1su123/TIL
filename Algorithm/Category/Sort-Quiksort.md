# Quicksort(퀵정렬)
- 분할 정복을 통해 배열을 정렬하는 알고리즘
- Pivot(기준점) 을 설정한 후 Pivot을 기준으로 작은값 큰값을 양쪽에 나눠서 정렬
- 양쪽으로 나뉜 두 부분에 대해 재귀적으로 반복
- 앞서 나온 버블,선택,삽입 정렬보다 평균적으로는 빠르지만(O(NlogN)) 최악의 경우 시간 복잡도가 O(N2)로 매우 커진다.

## Quicksort 이해하기
```
1. 피벗 p, 리스트 왼쪽 끝과 오른쪽 끝에서 시작한 인덱스 i, j

5 - 3 - 7 - 6 - 2 - 1 - 4 
                        p 

2. 리스트 왼쪽에 있는 i 위치의 값이 피벗 값보다 크고, 오른쪽에 있는 j 위치의 값은 피벗 값보다 작으므로 둘을 교환

5 - 3 - 7 - 6 - 2 - 1 - 4 
i                   j   p 

1 - 3 - 7 - 6 - 2 - 5 - 4 
i                   j   p 

3. j 위치의 값이 피벗 값보다 작지만, i 위치의 값도 피벗값보다 작으므로 교환하지 않는다.

1 - 3 - 7 - 6 - 2 - 5 - 4 
    i           j       p 

4. i위치를 피벗 값보다 큰 값이 나올 때까지 진행해 j 위치의 값과 교환한다.

1 - 3 - 7 - 6 - 2 - 5 - 4 
        i       j       p 
1 - 3 - 2 - 6 - 7 - 5 - 4 
        i       j       p 

5. i위치가 j 위치보다 커지면, i 위치의 값과 피벗 값을 교환한다.

1 - 3 - 2 - 6 - 7 - 5 - 4 
                        p 
1 - 3 - 2 - 4 - 7 - 5 - 6 
            p             

6. 피벗 값 좌우의 리스트에 대해 각각 퀵 정렬을 재귀적으로 수행한다.

1 - 3 - 2       7 - 5 - 6
1 - 2 - 3       5 - 6 - 7

7. 정렬 완료
1 - 2 - 3 - 4 - 5 - 6 - 7
```

## Quicksort 구현
```C
#include <algorithm>

void quickSort(int arr[], int left, int right) {
    if(left >= right) return;

    int &pivot = arr[right];
    int i = left;
    int j = right-1;

    for (; i < j; i++) {
        if (arr[i] > pivot)
            if (arr[j] < pivot)
                swap(arr[i], arr[j--]);                
    }

    swap(arr[i], pivot);

    quickSort(arr, left, i-1);
    quickSort(arr, i+1, right);
}
```