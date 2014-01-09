package com.henlo.process;
import nds.query.QueryEngine;
//import nds.util.Tools;
import java.util.*;
import java.security.MessageDigest;

public class GetData {

	
	/*int uid=userweb.getUserId();
	Map m=new HashMap();

	Object paramV= QueryEngine.getInstance().doQueryOne("select value from ad_param where name='webpos.datagenerationUrl'");
	String uriPre="";
	if(null!=paramV&&!"localhost".equals(paramV)) 
	uriPre=(String)paramV;

	String pw=String.valueOf(QueryEngine.getInstance().doQueryOne("select passwordhash from users where id="+uid));
	String s=pw+uid;
	String r="";
	MessageDigest md = MessageDigest.getInstance("MD5"); 
	md.update(s.getBytes());
	byte[] b=md.digest();  
	int i;
	StringBuffer buf = new StringBuffer("");
	for (int offset = 0; offset < b.length; offset++) 
		{   i = b[offset];   
		if(i<0) i+= 256;   
		if(i<16)   buf.append("0");   
		buf.append(Integer.toHexString(i)); 
		}
	r=buf.toString();
	String uri=uriPre+"/bpos/datageneration.jsp?uid="+uid+"&datetype=all&scope=all&vf="+r;
	m.put("code",4);
	m.put("message",uri);
	m*/
}
