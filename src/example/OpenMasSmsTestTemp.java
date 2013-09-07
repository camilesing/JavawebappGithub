package example;

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
		  Sms sms = new Sms("http://192.168.0.4:8080/openmasservice");
		  String[] destinationAddresses = new String[]{"13735865265"};
		  String message="短信测试";
		  String extendCode = "0101"; //自定义扩展代码（模块）
		  String ApplicationID= "default";
		  String Password = "default";
		  //发送短信 方法一
		  String GateWayid =sms.SendMessage(destinationAddresses, message);
		  System.out.println(GateWayid);
		  //发送短信 方法二
		  GateWayid = sms.SendMessage(destinationAddresses, message, extendCode);
		  System.out.println(GateWayid);
		  //发送短信 方法三
		  GateWayid = sms.SendMessage(destinationAddresses, message, extendCode, ApplicationID, Password);
		  System.out.println(GateWayid);
		  //发送短信 方法四
		  //设置定时时间
		  Calendar expectSendTime = Calendar.getInstance();
		  expectSendTime.add(Calendar.MINUTE, 5);
		  GateWayid = sms.SendMessage(destinationAddresses, message, extendCode, ApplicationID, Password, expectSendTime);
		  System.out.println(GateWayid);
		  
		  
		  //开始时间01-01 - 结束时间12-31
		  Calendar beginTime = Calendar.getInstance();
		  beginTime.set(Calendar.MONTH, 0);
		  beginTime.set(Calendar.DAY_OF_MONTH,1);
		  Calendar endTime = Calendar.getInstance();
		  endTime.set(Calendar.MONTH, 11);
		  endTime.set(Calendar.DAY_OF_MONTH,31);
		  //定时 每月的7号 10:12:11
		  PeriodValue  periodValue = new PeriodValue();
		  periodValue.setDay(7);
		  PeriodTime periodTime = new PeriodTime();
		  periodTime.setHour(10);
		  periodTime.setMinute(12);
		  periodTime.setSecond(11);
		  
		  //定时任务 方法一
		  GateWayid=sms.AddTask(PeriodType.Month,periodValue,periodTime,destinationAddresses, message);
		  System.out.println(GateWayid);
		  //定时任务 方法二
		  GateWayid=sms.AddTask(PeriodType.Month, periodValue, periodTime, destinationAddresses, message, extendCode, ApplicationID, Password, beginTime, endTime);
		  System.out.println(GateWayid);
		  //定时任务 方法三
		  GateWayid = sms.AddTask(PeriodType.Month, periodValue, periodTime, destinationAddresses, message, extendCode);
		  System.out.println(GateWayid);
		  //定时任务 方法四
		  GateWayid = sms.AddTask(PeriodType.Month, periodValue, periodTime, destinationAddresses, message, extendCode, ApplicationID, Password);
		  System.out.println(GateWayid);
		  //删除定时 方法一
		  sms.RemoveTask(GateWayid);
		  //删除定时 方法二
		  sms.RemoveTask(GateWayid, Password);
		  
		  //获取上行短信 方法一
		  SmsMessage mo = sms.GetMessage("02e2021b-e544-45df-87f0-192c96083826");
		  if(mo !=null)
			  System.out.println(mo.toString());
		  //获取上行短信 方法二
		  SmsMessage[] messages = sms.GetMessages(ApplicationID, Password);
		  if(messages != null)
		  {
			  for(int i=0;i<messages.length;i++)
				  System.out.println(messages[i].toString());
		  }
		  }catch(Exception ex)
		  {
			  System.err.println(ex);
		  }
		  System.exit(0);
	  }

}
