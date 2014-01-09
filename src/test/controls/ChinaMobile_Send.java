package test.controls;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import javax.xml.namespace.QName;

public class ChinaMobile_Send {
	public static void main(String[] args) {
		try {
			
		/*String message="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+ 
		  "<infos>"+ 
		   "<info>"+ 
		    "<msg_id><![CDATA[123]]></msg_id>"+  
		    "<password><![CDATA[7869]]></password>"+ 
		    "<src_tele_num><![CDATA[106573066104]]></src_tele_num>"+ 
		    "<dest_tele_num><![CDATA[1358602249]]></dest_tele_num>"+ 
		    "<msg><![CDATA[你好1]]></msg>"+  
		   "</info>"+  
		  "</infos>";  */
		String message="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+ 
		  "<infos>"+ 
		   "<info>"+ 
		    "<msg_id><![CDATA[1122]]></msg_id>"+  
		    "<password><![CDATA[7326]]></password>"+ 
		    "<src_tele_num><![CDATA[106573066104]]></src_tele_num>"+ 
		    "<dest_tele_num><![CDATA[18857846128]]></dest_tele_num>"+ 
		    "<msg><![CDATA[你好]]></msg>"+  
		   "</info>"+ 
		  "</infos>";  

		String ret =send_msg( "120.193.39.85", "wz999999", message);//短信发送
		System.out.println("return value is " + ret); 
		 ret =receipt_all("wz999999");//该方法为：批量获取回执信息，每条消息只获取一次，单次最多返回300条回执，不同回执间以"；"隔开
		System.out.println("return value is " + ret); 
		 ret =rt_receipt("wz999999","123");//获取指定消息的回执信息
		System.out.println("return value is " + ret); 
		} catch (Exception ex) {
		ex.printStackTrace();
		} 
		}

		public static String send_msg(String wbip,String dbuser,String message) throws Exception {

				////System.setProperty("http.proxyHost", "10.70.237.88");
		       // //System.setProperty("http.proxyPort", "8080");
		String endpoint = "http://120.193.39.85/webservice/services/sendmsg";
		Service service = new Service();
		Call call = null;

		call = (Call) service.createCall();

		call.setOperationName(new QName(
		"http://120.193.39.85/webservice/services/sendmsg", "sendmsg"));
		call.setTargetEndpointAddress(new java.net.URL(endpoint)); 
		String ret = (String) call.invoke(new Object[] {dbuser,message});
		//System.out.println("return value is " + ret); 
		return ret;
		}

		public static String send_msg_st(String wbip,String dbuser,String message) throws Exception {

				//System.setProperty("http.proxyHost", "10.70.237.88");
		        //System.setProperty("http.proxyPort", "8080");
			String endpoint = "http://120.193.39.85/webservice/services/sendmsg";
		Service service = new Service();
		Call call = null;

		call = (Call) service.createCall();

		call.setOperationName(new QName(
		"http://120.193.39.85/webservice/services/sendmsg", "sendmsg_st"));
		call.setTargetEndpointAddress(new java.net.URL(endpoint)); 
		String ret = (String) call.invoke(new Object[] {dbuser,message});
		//System.out.println("return value is " + ret); 
		return ret;
		} 

		public static String deliver(String wbip,String dbuser) throws Exception {

				////System.setProperty("http.proxyHost", "10.70.237.88");
		       // //System.setProperty("http.proxyPort", "8080");
			String endpoint = "http://120.193.39.85/webservice/services/deliver";
		Service service = new Service();
		Call call = null;

		call = (Call) service.createCall();

		call.setOperationName(new QName(
		"http://120.193.39.85/webservice/services/deliver", "deliver"));
		call.setTargetEndpointAddress(new java.net.URL(endpoint)); 
		String ret = (String) call.invoke(new Object[] {dbuser});
		//System.out.println("return value is " + ret); 
		return ret;
		}


		public static String receipt_all(String dbuser) throws Exception {

				////System.setProperty("http.proxyHost", "10.70.237.88");
		       // //System.setProperty("http.proxyPort", "8080");
			String endpoint = "http://120.193.39.85/webservice/services/rt_receipt_all";
		Service service = new Service();
		Call call = null;

		call = (Call) service.createCall();

		call.setOperationName(new QName(
		"http://120.193.39.85/webservice/services/rt_receipt_all", "receipt"));
		call.setTargetEndpointAddress(new java.net.URL(endpoint)); 
		String ret = (String) call.invoke(new Object[] {dbuser});
		//System.out.println("return value is " + ret); 
		return ret;
		}



		public static String rt_receipt(String dbuser,String msg_id) throws Exception {

				////System.setProperty("http.proxyHost", "10.70.237.88");
		       // //System.setProperty("http.proxyPort", "8080");
			String endpoint = "http://120.193.39.85/webservice/services/rt_receipt";
		Service service = new Service();
		Call call = null;

		call = (Call) service.createCall();

		call.setOperationName(new QName(
		"http://120.193.39.85/webservice/services/rt_receipt", "receipt"));
		call.setTargetEndpointAddress(new java.net.URL(endpoint)); 
		String ret = (String) call.invoke(new Object[] {dbuser,msg_id});
		//System.out.println("return value is " + ret); 
		return ret;
		}



		public static String add_msg_t() throws Exception {
		String message="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+ 
		  "<infos>"; 
		return message;
		}



		public static String add_msg_w() throws Exception {
		String message="</infos>"; 
		return message;
		}


		public static String add_msg_b(String msg_id,String password,String src_tele_num,String dest_tele_num,String msg) throws Exception {
		String message="<info>"+ 
		    "<msg_id><![CDATA["+msg_id+"]]></msg_id>"+  
		    "<password><![CDATA["+password+"]]></password>"+ 
		    "<src_tele_num><![CDATA["+src_tele_num+"]]></src_tele_num>"+ 
		    "<dest_tele_num><![CDATA["+dest_tele_num+"]]></dest_tele_num>"+ 
		    "<msg><![CDATA["+msg+"]]></msg>"+  
		   "</info>";
		return message;
		}
}
