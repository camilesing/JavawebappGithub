package test.controls;

import java.util.*;

import com.authority.common.utils.WebUtils;
import com.chinamobile.openmas.client.*;
import com.chinamobile.openmas.entity.*;

public class OpenMasSms
{
	  public static void main(String[] args)
	  {
		  try
		  {
		  // 短信
		  
		  WebUtils web = new WebUtils();
		  String webservice = web.readValue("config/others/config.properties","openmas.sms");
		  String extendCode = web.readValue("config/others/config.properties","openmas.extendCode");
		  String ApplicationID= web.readValue("config/others/config.properties","openmas.ApplicationID");
		  String Password = web.readValue("config/others/config.properties","openmas.Password");
		  			  
		  Sms sms = new Sms(webservice);
		  String[] destinationAddresses = new String[]{"18857846128","15967417271"};
		  
		  String message="短信测试....";	
		  String GateWayid = "";
		  
		  GateWayid = sms.SendMessage(destinationAddresses, message, extendCode, ApplicationID, Password);
		  System.out.println(GateWayid);
				  
		  }catch(Exception ex)
		  {
			  System.err.println(ex);
		  }
		  
	  }

}
