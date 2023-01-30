# JDBC / Java Database Connectivity
- API for Java Language, which defines how a client access a database
- 데이터베이스에 접근하기 위한 Java 의 API
- JDBC의 클래스와 인터페이스들은 애플리케이션이 특정 데이터베이스로 유저들의 요청을 전송할 수 있게 해준다

## 역사
- JDBC 이전 ODBC API 를 이용하여 데이터베이스에 접근했지만, ODBC 는 C언어로 작성되어 있어 Java 언어로 된 JDBC 가 탄생하게 되었다

## 연결 과정
- Import package
- Load Driver : 2가지 방법 中 택 1
```Java
Class.forName("oracle.jdbc.driver.OracleDriver");
```
or
```Java
DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
```
- Establish connection
```Java
Connection con = DriverManager.getConnection(url, user, pwd);
```
- Create Statement
```Java
Statement st = con.createStatement();
```
- Execute query
```Java
String query = "SELECT * FROM TABLE1"
st.executeQuery(query);
```
- Close connection
```Java
con.close();
```

## Reference
- [Wikipedia - 'JDBC'](https://en.wikipedia.org/wiki/Java_Database_Connectivity)
- [위키백과 - 'JDBC'](https://ko.wikipedia.org/wiki/JDBC)
- [javatpoint - 'JDBC'](https://www.javatpoint.com/java-jdbc)
- [geeksforgeeks - 'Intoduction to JDBC'](https://www.geeksforgeeks.org/introduction-to-jdbc/)
- [geeksforgeeks - 'Establishing jdbc connection](https://www.geeksforgeeks.org/establishing-jdbc-connection-in-java/)