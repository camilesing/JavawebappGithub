<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();   
	String url="jdbc:oracle:thin:@localhost:1521:xe";   
	//orcl为你的数据库的SID   
	String user="poe";   
	String password="314159";   
	Connection conn= DriverManager.getConnection(url,user,password);   
	Statement stmt=conn.createStatement  
	(ResultSet.TYPE_SCROLL_SENSITIVE,  
	ResultSet.CONCUR_UPDATABLE);   
	String sql="select * from BASE_FIELDS where rownum<3";
	JSONArray json = new JSONArray();
	
	ResultSet rs=stmt.executeQuery(sql);   
	int length=0;
	while(rs.next()) {
		length++;
		JSONObject obj=new JSONObject();
		String FIELD_ID=rs.getString("FIELD_ID");
		obj.put("FIELD_ID",FIELD_ID);
	    obj.put("FIELD_NAME",rs.getString("FIELD_NAME"));
	    obj.put("VALUE_FIELD",rs.getString("VALUE_FIELD"));
	    json.add(obj);	   	  		
}%>   
<% 		
	JSONObject result =new JSONObject();
	result.put("results",length);
	result.put("rows",json);
	
	out.print(result);
	out.flush();
	rs.close();
	stmt.close();
	conn.close();   
%>
