package test.controls;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import com.alipay.config.AlipayConfig;
import com.alipay.util.httpClient.HttpProtocolHandler;
import com.alipay.util.httpClient.HttpRequest;
import com.alipay.util.httpClient.HttpResponse;
import com.alipay.util.httpClient.HttpResultType;
import com.authority.common.utils.XmlOperateUtil;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

public class Test {
	
	private static final String BASE_URL = "http://111.1.15.134/stardy/";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String string = "温州";
		
		String[] str = string.split("\\|");
		System.out.println(str.length);
		System.out.println(str[0]);
		
		String Path = "E:\\BCKF\\Project\\java\\Workspaces\\JavawebappGithub\\src\\config\\others\\MerPrk.key";
		Path="/usr/local/tomcat/webapps/Javawebapp/WEB-INF/classes/config/others/MerPrk.key";
		
		
		File file = new File(Path);
		
		if(file.exists()){
			System.out.println("MerPrk.key exists");
		}else{
			System.out.println("MerPrk.key not exists");
		}
		
		
		// java -cp druid-1.0.0.jar com.alibaba.druid.filter.config.ConfigTools you_password
		// %E6%B8%A9%E5%B7%9E
		//WNRMsp637PiROfSUR7s0jP2iN5mJb4FFvTjwnF45qVrMMP8gZVYFcqnsAEVO7J0EkOSRsl/tWRRh/skUX7qMDw==
		/*try {
			String encode = URLEncoder.encode(string,"utf-8");
			System.out.println("encode:"+encode);
			
			String  decode = URLDecoder.decode(encode,"utf-8");
			System.out.println("decode:"+decode);
			
		
		
			File downloadfile = new File("D:\\2011.txt");
			FileOutputStream fos=null; 
			fos =  new FileOutputStream(downloadfile);
			
			for(int i=0;i<=2;i++){
				String line = i+"测试\r\n";
				fos.write(line.getBytes("GBK"));
			}
			fos.close();
			
		
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		
		/*try {
			HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
			HttpRequest request = new HttpRequest(HttpResultType.BYTES);
			//设置编码集
	        request.setCharset(AlipayConfig.input_charset);
	        
	        String to = "18857846128";
	        String yzm = String.valueOf(Integer.parseInt(to.substring(to.length()-4))*3+3206);
	        String tjpc = "1";
	        
	        Map<String, String> paramTemp = new HashMap<String, String>();
	        paramTemp.put("usr", "wz255774");
	        paramTemp.put("pwd", "xg");
	        paramTemp.put("to", to);
	        paramTemp.put("content", "短信内容测试");
	        paramTemp.put("yzm", yzm);
	        paramTemp.put("zihao", "");
	        paramTemp.put("tjpc", tjpc);
	        paramTemp.put("ds", "");
	        
	        request.setParameters(generatNameValuePair(paramTemp));
	        request.setUrl(BASE_URL+"sendmsg_xuege.jsp");
	        HttpResponse response;
			response = httpProtocolHandler.execute(request,"","");
			String strResult ="";
	        if (response == null) {
	        	System.out.println("response is null");
	        }else{
	        	strResult = response.getStringResult();
	        	System.out.println("strResult:"+strResult);
	        }
		} catch (Exception e) {
			
			// TODO: handle exception
		}*/
		/*
		
		
		if(1==1)
			return;
		
		try {
			HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
			HttpRequest request = new HttpRequest(HttpResultType.BYTES);
			//设置编码集
	        request.setCharset(AlipayConfig.input_charset);
	        
	        String to = "18857846128";
	        String yzm = String.valueOf(Integer.parseInt(to.substring(to.length()-4))*3+3206);
	        String tjpc = "1";
	        
	        Map<String, String> paramTemp = new HashMap<String, String>();
	        paramTemp.put("usr", "wz255774");
	        paramTemp.put("pwd", "xg");
	        paramTemp.put("to", to);
	        paramTemp.put("content", "短信内容测试");
	        paramTemp.put("yzm", yzm);
	        paramTemp.put("zihao", "");
	        paramTemp.put("tjpc", tjpc);
	        paramTemp.put("ds", "");
	        
	        request.setParameters(generatNameValuePair(paramTemp));
	        request.setUrl(BASE_URL+"sendmsg_xuege.jsp");
	        HttpResponse response;
			response = httpProtocolHandler.execute(request,"","");
			String strResult ="";
	        if (response == null) {
	        	System.out.println("response is null");
	        }else{
	        	strResult = response.getStringResult();
	        	System.out.println("strResult:"+strResult);
	        }
		} catch (Exception e) {
			
			// TODO: handle exception
		}
		
				
		String username ="fd";
		String password ="fd";
		password = DigestUtils.md5Hex(password);
		
		System.out.println("password:"+password);
		
		password = DigestUtils.md5Hex(password+"{"+username+"}");
				
		System.out.println("password:"+password);
		
		//Do xml 
		String text = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+ 
				  "<infos>"+ 
				   "<info>"+ 
				    "<msg_id><![CDATA[-1]]></msg_id>"+  
				    "<password><![CDATA[18963]]></password>"+ 
				    "<src_tele_num><![CDATA[106573066104]]></src_tele_num>"+ 
				    "<dest_tele_num><![CDATA[18857846128]]></dest_tele_num>"+ 
				    "<msg><![CDATA[你好]]></msg>"+  
				   "</info>"+ 
				   "<info>"+ 
				    "<msg_id><![CDATA[-1]]></msg_id>"+  
				    "<password><![CDATA[18963]]></password>"+ 
				    "<src_tele_num><![CDATA[106573066104]]></src_tele_num>"+ 
				    "<dest_tele_num><![CDATA[18857846128]]></dest_tele_num>"+ 
				    "<msg><![CDATA[你好]]></msg>"+  
				   "</info>"+ 
				  "</infos>";  
		
		XmlOperateUtil xmlOperateUtil = new XmlOperateUtil();
		
		Document document = xmlOperateUtil.generateDocumentByString(text);
		
		List list = document.selectNodes("//infos//info");
		Iterator iter_list = list.iterator();
        if (iter_list.hasNext()) {
           Element element = (Element) iter_list.next();
           for (Iterator ieson = element.elementIterator(); ieson.hasNext();) {
               Element elementSon = (Element) ieson.next();
               System.out.println(elementSon.getName() + ":"
                      + elementSon.getText());
           }
           
           
           Iterator iter_element = element.elementIterator("msg_id");
           while (iter_element.hasNext()) {
               Element nameElement = (Element) iter_element.next();
               System.out.println("msg_id:"+nameElement.getText());
           }
           
           
        }

		if(1==1)
			return ;
		
		try {
			java.util.Random r=new java.util.Random(); 
			//String random = String.valueOf(Integer.parseInt(r.nextDouble()*10000/1));
			Double db = r.nextDouble()*10000 ;
			String str = String.valueOf(db.intValue());
			str = StringUtils.leftPad(str, 4, "0");
			System.out.println(str);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		try{
			String time ="08:00-23:00";
			String[] setime= time.split("-");			
			String  starttime[] = setime[0].split(":");
			int startminute = Integer.parseInt(starttime[0])*60+Integer.parseInt(starttime[1]);
			String  endtime[] = setime[1].split(":");
			int endminute = Integer.parseInt(endtime[0])*60+Integer.parseInt(endtime[1]);
			
			Calendar cal=Calendar.getInstance();
			cal.setTime(new Date());
			int nowminute = cal.get(Calendar.HOUR_OF_DAY)*60+cal.get(Calendar.MINUTE);
			
			System.out.println(nowminute);
			
			System.out.println(startminute+":"+endminute+":"+nowminute);
			
		} catch (Exception e){
			System.out.println(e);
		}
		
		
		
		
		System.out.println("==============================");
		
		String[] standard = {"true","0","0"};
		
		System.out.println("standard:"+standard[0]+","+standard[1]);
		
		
		int id = Integer.parseInt("12"+"13");
		
		System.out.println(id);
		
		
		// TODO Auto-generated method stub
		String html="Tomcat服务器监控1.3版 for Win,Linux(原创)";		
		
		int start=html.indexOf(">");
		int end  =html.indexOf("<", start);
		System.out.println("1:"+start);
		System.out.println("2:"+end);
		if(start>-1&&end>-1)
			html=html.substring(start+1, end);
		
		System.out.println(html);
		
		Evaluator eval = new Evaluator(); 
		
        try {
			System.out.println(eval.evaluate("2*3-5/(3-1)"));		
	        System.out.println(eval.evaluate("7 / 2")); 
	        System.out.println(eval.evaluate("7 % 2")); 
	        System.out.println(eval.evaluate("((4 + 3) * -2) * 3")); 
	        System.out.println(eval.evaluate("((4 + 3) * -2) * 3 + sqrt(30)")); 
	        System.out.println(eval.evaluate("((4 + 3) * -2) * 3 + sin(45)")); 
        
        } catch (EvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		
	}
	
	/**
     * MAP类型数组转换成NameValuePair类型
     * @param properties  MAP类型数组
     * @return NameValuePair类型数组
     */
    private static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }

        return nameValuePair;
    }

}
