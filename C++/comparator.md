```C++
#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;

bool comp_func (int i,int j) {
    return (i<j);
}

struct myclass {
    bool operator() (int i,int j) {
        return (i<j);
    }
} com_obj;

int main () {
  vector<int> vec {32,71,12,45,26,80,53,33};

  // default (operator <):
  sort(vec.begin(), vec.end());

  // function
  sort(vec.begin(), vec.end(), comp_func);

  // object
  sort(vec.begin(), vec.end(), com_obj);

  return 0;
}
```

## Reference
[https://cplusplus.com/reference](https://cplusplus.com/reference/algorithm/sort/?kw=sort)