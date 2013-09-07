package test.controls;

import java.util.*;

import com.chinamobile.openmas.client.*;
import com.chinamobile.openmas.entity.*;

public class OpenMasSmsTestTemp
{
	  public static void main(String[] args)
	  {
		  try
		  {
		  // 短信
		  Sms sms = new Sms("http://211.140.163.220:9080/OpenMasService");
		  String[] destinationAddresses = new String[]{"18857846128"};
		  String message="短信测试";
		  String extendCode = "2"; //自定义扩展代码（模块）
		  String ApplicationID= "8006001";
		  String Password = "QbYLgU1TpuD5";
		  String GateWayid = "";
		  
		  GateWayid = sms.SendMessage(destinationAddresses, message, extendCode, ApplicationID, Password);
		  System.out.println(GateWayid);
		  
		//获取上行短信 方法一
		  SmsMessage mo = sms.GetMessage(GateWayid);
		  if(mo !=null)
			  System.out.println(mo.toString());
		  
		  }catch(Exception ex)
		  {
			  System.err.println(ex);
		  }
		  
	  }

}
