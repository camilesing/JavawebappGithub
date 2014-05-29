<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%> 
<html>
<body>
<%Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
String url="jdbc:oracle:thin:@127.0.0.1:1521:XE"; 

String user="neands3"; 
String password="abc123"; 
Connection conn= DriverManager.getConnection(url,user,password);
Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
String sql="select * from BASE_USERS";
ResultSet rs=stmt.executeQuery(sql);
while(rs.next()) {%>   
您的第一个字段内容为：<%=rs.getString(1)%>   
您的第二个字段内容为：<%=rs.getString(2)%>   
<%}%>   
<%out.print("数据库操作成功，恭喜你");%>   
<%rs.close();
stmt.close();
conn.close();
%>   
</body>
</html>
