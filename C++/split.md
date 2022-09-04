# C++ split function 구현
```C
vector<string> split(string input, char delimiter) {
    vector<string> answer;
    stringstream ss(input);
    string temp;
 
    while (getline(ss, temp, delimiter))
        answer.push_back(temp);
 
    return answer;
}
```