package test.controls;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import com.authority.common.utils.XmlOperateUtil;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
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
           
           
           /*Iterator iter_element = element.elementIterator("msg_id");
           while (iter_element.hasNext()) {
               Element nameElement = (Element) iter_element.next();
               System.out.println("msg_id:"+nameElement.getText());
           }*/
           
           
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
		} 
		
	}

}
