<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map" %>
<%@ page import="org.apache.commons.httpclient.NameValuePair" %>
<%@ page import="com.alipay.util.httpClient.HttpProtocolHandler" %>
<%@ page import="com.alipay.util.httpClient.HttpRequest" %>
<%@ page import="com.alipay.util.httpClient.HttpResponse" %>
<%@ page import="com.alipay.util.httpClient.HttpResultType" %>

<%!
	String BASE_URL ="http://111.1.15.134/stardy/";

	private static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
	    NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
	    int i = 0;
	    for (Map.Entry<String, String> entry : properties.entrySet()) {
	        nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
	    }
	
	    return nameValuePair;
	}
%>
<%
	
	try {
		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
		HttpRequest req = new HttpRequest(HttpResultType.STRING);
		HttpResponse rep;
		String strResult ="";
		Map<String, String> paramTemp = new HashMap<String, String>();
		
		//设置编码集
	    req.setCharset("gbk");
		
		String to = request.getParameter("to");
		String yzm = request.getParameter("yzm");
		String tjpc= request.getParameter("tjpc");
		String content = request.getParameter("content");
		if(!request.getMethod().equalsIgnoreCase("POST"))
			content = new String(content.getBytes("ISO-8859-1"),"utf-8");
		
		/*
	  	Enumeration e = request.getHeaderNames();
        while (e.hasMoreElements()) {
            String name = (String)e.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + " = " + value);
        }
	  	
        
	  	System.out.println("content:"+content);
	  	*/
	  	
	    paramTemp.put("usr", "wz255774");
	    paramTemp.put("pwd", "xg");
	    paramTemp.put("to", to);
	    paramTemp.put("content", content);
	    paramTemp.put("yzm", yzm);
	    paramTemp.put("tjpc", tjpc);
	    
	    
	    req.setParameters(generatNameValuePair(paramTemp));
	    req.setUrl(BASE_URL+"sendmsg_xuege.jsp");
	    req.setMethod(HttpRequest.METHOD_POST);
	    rep = httpProtocolHandler.execute(req,"","");
		
	    if (rep == null) {
	    	System.out.println("response is null");
	    }else{
	    	strResult = rep.getStringResult();
	    	System.out.println("SmsSendResult:"+strResult);
	    }
	    
	  	out.print(strResult);
	  	out.flush();
	  	
	    
	} catch (Exception e) {
		
		// TODO: handle exception
	}

%>