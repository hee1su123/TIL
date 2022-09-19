# C++ binary_search(), lower_bound(), upper_bound()
- <algorithm> 헤더 파일
- 이진탐색을 구현해 놓은 함수
- 오름차순으로 정렬되어 있는 경우에만 사용 가능하다
- 시간복잡도 O(logN)
## binary_search(arr_begin,arr_end,find_value)
- arr_begin: 시작 iterator
- arr_end: 끝 iterator
- find_value: 찾는 값
- 값이 존재하면 true, 아니면 false 를 반환
## lower_bound(arr_begin,arr_end,find_value), upper_bound(arr_begin,arr_end,find_value)
- lower: 주어진 값보다 크거나 같은 값 중 가장 작은 값의 이터레이터 반환
- upper: 주어진 값보다 큰 값 중 가장 작은 값의 이터레이터 반환