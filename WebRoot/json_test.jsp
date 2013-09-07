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
	    
	    JSONObject result =new JSONObject();
		result.put("results",length);
		result.put("rows",json);

		rs.close();
		stmt.close();
		conn.close();  
}%>   
<% 			 
	
	JSONObject data =new JSONObject();
	
	JSONObject jsonResult = new JSONObject();
	
	JSONObject jsonResult_data = new JSONObject();
	List data_m_product = new ArrayList();	
	
	JSONObject data_m_product_child = new JSONObject();
	List m_product_color = new ArrayList();
	JSONObject m_product_color_child = new JSONObject();
	JSONObject color_c_store = new JSONObject();
	JSONObject c_store_w = new JSONObject();
	JSONObject w_docno = new JSONObject();	
	JSONObject docno_q = new JSONObject();
	JSONObject q_array = new JSONObject();
	List array_tag_c = new ArrayList();
	JSONObject array_tag_c_child = new JSONObject();
	
	//开始初始化数据
	array_tag_c_child.put("content", "34");
	array_tag_c_child.put("QTYREM", "9");
	array_tag_c_child.put("m_product_alias_id", "816706");
	array_tag_c_child.put("QTY_SO", "0");	
	array_tag_c_child.put("QTYCAN", "2");
	array_tag_c_child.put("QTY_ALLOT", "0");
	array_tag_c_child.put("DESTQTY", "9");
	
	array_tag_c.add(array_tag_c_child);
	q_array.put("tag_c", array_tag_c);
	
	docno_q.put("xmlns", "1");
	docno_q.put("array",q_array);

	w_docno.put("billdate", "20130807");
	w_docno.put("q", docno_q);
	w_docno.put("sotype", "FWD");
	w_docno.put("xmlns", "SO1308070000007");
	w_docno.put("b_so_id", "1747");
	
	c_store_w.put("docno", w_docno);
	c_store_w.put("xmlns", "1");

	color_c_store.put("w", c_store_w);
	color_c_store.put("xmlns", "兰州外购成品仓");
	
	m_product_color_child.put("xmlns", "63");
	m_product_color_child.put("c_store", color_c_store);
	
	m_product_color.add(m_product_color_child);

	data_m_product_child.put("color", m_product_color);
	data_m_product_child.put("M_PRODUCT_LIST", "104453");
	data_m_product_child.put("value", "浅口单鞋");
	data_m_product_child.put("xmlns", "121118");
	
	data_m_product.add(data_m_product_child);
		
	jsonResult_data.put("m_product", data_m_product);
	
	
	jsonResult.put("Billdateend","20130808");
	jsonResult.put("Product_Filter","");
	jsonResult.put("saletypeid","");
	jsonResult.put("status","1");
	jsonResult.put("Billdatebeg","20130808");
	jsonResult.put("data",jsonResult_data);
	jsonResult.put("isarray", "Y");
	jsonResult.put("searchord", "包含(SO1308080000005,SO1308080000004,SO1308080000003,SO1308080000002,SO1308080000001,SO1308070000010,SO1308070000009,SO1308070000007,SO1308070000006,SO1308070000005)");
	jsonResult.put("p_status", "-1");
	jsonResult.put("p_display", "0");
	jsonResult.put("DEST_FILTER", "");
	jsonResult.put("description", "tlw");
	jsonResult.put("m_allot_id","61");
	jsonResult.put("feeallot","0");
	jsonResult.put("distdate","20130808");
	jsonResult.put("ISAUTOSUBSAL","Y");
	jsonResult.put("notes","");
	jsonResult.put("C_ORIG","总部仓库");
	
	data.put("jsonResult", jsonResult.toJSONString());	
%>
<html>
	<script type="text/javascript">
		function at(){
			var result = <%=data%>
			alert(result);			
			
			var a = -2&&-1;
			if(0)
				alert(a)
		}
	</script>
	
	
	
	<body>
		<input type="button" onclick="at()" value="Json Test">
	
	</body>


</html>