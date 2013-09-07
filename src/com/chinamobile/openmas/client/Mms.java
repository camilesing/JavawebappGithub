/**
 * 
 */
package com.chinamobile.openmas.client;

import org.apache.axis2.AxisFault;
import org.tempuri.MmsImplementationStub;
import com.chinamobile.openmas.entity.*;
import java.rmi.RemoteException;

/**
 * @author OpenMas
 *
 */
public class Mms {
	
	private MmsImplementationStub stub = null;
	public Mms(String serviceUrl) throws AxisFault 
	{
		stub = new MmsImplementationStub(serviceUrl);
	}
	/**
	 * 发送彩信
	 * @param destinationAddresses 接收方地址列表(可一次发送给多人)
	 * @param subject 主题
	 * @param content 彩信内容（符合彩信规范的xml文档）
	 * @return 返回彩信发送唯一标识
	 */
	public String SendMessage(String[] destinationAddresses, String subject, String content) 
	{
		MmsImplementationStub.SendMessage1 sendMessage = new MmsImplementationStub.SendMessage1();
		sendMessage.setContent(content);
		sendMessage.setSubject(subject);
		MmsImplementationStub.ArrayOfstring array=new MmsImplementationStub.ArrayOfstring();
		for(int i=0;i<destinationAddresses.length;i++)
		{
			array.addString(destinationAddresses[i]);
		}
		sendMessage.setDestinationAddresses(array);
		try
		{
			return stub.SendMessage1(sendMessage).getSendMessage1Result();
		}catch(RemoteException rem)
		{
			System.err.println(rem);
			return null;
		}
	}
	
	/**
	 * 发送彩信
	 * @param destinationAddresses 接收方地址列表(可一次发送给多人)
	 * @param subject 主题
	 * @param content 彩信内容（符合彩信规范的xml文档）
	 * @param extendCode 扩展号码
	 * @return 返回彩信发送唯一标识
	 */
	public String SendMessage(String[] destinationAddresses, String subject, String content,String extendCode)
	{
		MmsImplementationStub.SendMessage2 sendMessage = new MmsImplementationStub.SendMessage2();
		sendMessage.setContent(content);
		sendMessage.setSubject(subject);
		sendMessage.setExtendCode(extendCode);
		MmsImplementationStub.ArrayOfstring array=new MmsImplementationStub.ArrayOfstring();
		for(int i=0;i<destinationAddresses.length;i++)
		{
			array.addString(destinationAddresses[i]);
		}
		sendMessage.setDestinationAddresses(array);
		try
		{
			return stub.SendMessage2(sendMessage).getSendMessage2Result();
		}catch(RemoteException rem)
		{
			System.err.println(rem);
			return null;
		}
	}
	
	/**
	 * 发送彩信
	 * @param destinationAddresses 接收方地址列表(可一次发送给多人)
	 * @param subject 主题
	 * @param content 彩信内容（符合彩信规范的xml文档）
	 * @param extendCode 扩展号码
	 * @param applicationId 应用ID
	 * @param password 应用密码
	 * @return 返回彩信发送唯一标识
	 */
	public String SendMessage(String[] destinationAddresses, String subject, String content,String extendCode,String applicationId,String password) 
	{
		MmsImplementationStub.SendMessage3 sendMessage = new MmsImplementationStub.SendMessage3();
		sendMessage.setContent(content);
		sendMessage.setSubject(subject);
		sendMessage.setExtendCode(extendCode);
		sendMessage.setApplicationId(applicationId);
		sendMessage.setPassword(password);
		MmsImplementationStub.ArrayOfstring array=new MmsImplementationStub.ArrayOfstring();
		for(int i=0;i<destinationAddresses.length;i++)
		{
			array.addString(destinationAddresses[i]);
		}
		sendMessage.setDestinationAddresses(array);
		try
		{
			return stub.SendMessage3(sendMessage).getSendMessage3Result();
		}catch(Exception rem)
		{
			System.err.println(rem);
			return null;
		}
	}
	
	/**
	 * 发送彩信
	 * @param destinationAddresses 接收方地址列表(可一次发送给多人)
	 * @param subject 主题
	 * @param content 彩信内容（符合彩信规范的xml文档）
	 * @param extendCode 扩展号码
	 * @param applicationId 应用ID
	 * @param password 应用密码
	 * @param expectSendTime 期望发送时间
	 * @return 返回彩信发送唯一标识
	 */
	public String SendMessage(String[] destinationAddresses, String subject, String content,String extendCode,String applicationId,String password,java.util.Calendar expectSendTime)
	{
		MmsImplementationStub.SendMessage4 sendMessage = new MmsImplementationStub.SendMessage4();
		sendMessage.setContent(content);
		sendMessage.setSubject(subject);
		sendMessage.setExtendCode(extendCode);
		sendMessage.setApplicationId(applicationId);
		sendMessage.setPassword(password);
		sendMessage.setExpectSendTime(expectSendTime);
		MmsImplementationStub.ArrayOfstring array=new MmsImplementationStub.ArrayOfstring();
		for(int i=0;i<destinationAddresses.length;i++)
		{
			array.addString(destinationAddresses[i]);
		}
		sendMessage.setDestinationAddresses(array);
		try
		{
			return stub.SendMessage4(sendMessage).getSendMessage4Result();
		}catch(RemoteException rem)
		{
			System.err.println(rem);
			return null;
		}
	}
	
	/**
	 * 获取彩信发送状态
	 * @param messageId 彩信ID
	 * @return 返回彩信发送状态
	 */
	public DeliveryReport[]  GetDeliveryReport(String messageId)
	{
		MmsImplementationStub.GetDeliveryReport1 request = new MmsImplementationStub.GetDeliveryReport1();
		request.setMessageId(messageId);
		MmsImplementationStub.GetDeliveryReport1Response response;
		try
		{
			response=stub.GetDeliveryReport1(request);
			if(response == null)
				return null;
		}catch(RemoteException rem)
		{
			System.err.println(rem);
			return null;
		}
		MmsImplementationStub.DeliveryReport[] reports = response.getGetDeliveryReport1Result().getDeliveryReport();
		if(reports == null)
			return null;
		DeliveryReport[]  deliveryReports = new DeliveryReport[reports.length];
		for(int i=0;i<reports.length;i++)
		{
			DeliveryReport deliveryReport = new DeliveryReport();
			deliveryReport.setMessageId(reports[i].get_x003C_MessageId_x003E_k__BackingField());
			deliveryReport.setMessageDeliveryStatus(reports[i].get_x003C_MessageDeliveryStatus_x003E_k__BackingField().getValue());
			deliveryReport.setReceivedAddress(reports[i].get_x003C_SendAddress_x003E_k__BackingField());
			deliveryReport.setSendAddress(reports[i].get_x003C_SendAddress_x003E_k__BackingField());
			deliveryReport.setStatusCode(reports[i].get_x003C_StatusCode_x003E_k__BackingField());
			deliveryReports[i]=deliveryReport;
		}
		return deliveryReports;
	}
	
	/**
	 * 获取彩信发送状态
	 * @param messageId 彩信ID
	 * @param applicationId 应用ID
	 * @param password 应用密码
	 * @return 返回彩信发送状态
	 */
	public DeliveryReport[]  GetDeliveryReport(String messageId,String applicationId, String password)
	{
		MmsImplementationStub.GetDeliveryReport2 request = new MmsImplementationStub.GetDeliveryReport2();
		request.setMessageId(messageId);
		request.setApplicationId(applicationId);
		request.setPassword(password);
		MmsImplementationStub.GetDeliveryReport2Response response;
		try{
			response=stub.GetDeliveryReport2(request);
			if(response == null)
				return null;
		}catch(RemoteException rem)
		{
			System.err.println(rem);
			return null;
		}
		MmsImplementationStub.DeliveryReport[] reports = response.getGetDeliveryReport2Result().getDeliveryReport();
		if(reports == null)
			return null;
		DeliveryReport[]  deliveryReports = new DeliveryReport[reports.length];
		for(int i=0;i<reports.length;i++)
		{
			DeliveryReport deliveryReport = new DeliveryReport();
			deliveryReport.setMessageId(reports[i].get_x003C_MessageId_x003E_k__BackingField());
			deliveryReport.setMessageDeliveryStatus(reports[i].get_x003C_MessageDeliveryStatus_x003E_k__BackingField().getValue());
			deliveryReport.setReceivedAddress(reports[i].get_x003C_SendAddress_x003E_k__BackingField());
			deliveryReport.setSendAddress(reports[i].get_x003C_SendAddress_x003E_k__BackingField());
			deliveryReport.setStatusCode(reports[i].get_x003C_StatusCode_x003E_k__BackingField());
			deliveryReports[i]=deliveryReport;
		}
		return deliveryReports;
	}
	
	/**
	 * 接收上行彩信
	 * @param messageId 彩信ID
	 * @return 返回上行彩信
	 */
	public MmsMessage GetMessage(String messageId) 
	{
		MmsImplementationStub.GetMessage request = new MmsImplementationStub.GetMessage();
		request.setMessageId(messageId);
		MmsImplementationStub.GetMessageResponse response;
		try
		{
			response=stub.GetMessage(request);
			if(response == null)
				return null;
		}catch(RemoteException rem)
		{
			System.err.println(rem);
			return null;
		}
		MmsImplementationStub.MmsMessage message = response.getGetMessageResult();
		if(message == null)
			return null;
		MmsMessage mmsMessage = new MmsMessage();
		mmsMessage.setApplicationId(message.get_x003C_ApplicationId_x003E_k__BackingField());
		mmsMessage.setContent(message.get_x003C_Content_x003E_k__BackingField());
		mmsMessage.setExtendCode(message.get_x003C_ExtendCode_x003E_k__BackingField());
		mmsMessage.setFrom(message.get_x003C_From_x003E_k__BackingField());
		mmsMessage.setId(message.get_x003C_Id_x003E_k__BackingField());
		mmsMessage.setReceivedTime(message.get_x003C_ReceivedTime_x003E_k__BackingField().getTime());
		mmsMessage.setTo(message.get_x003C_To_x003E_k__BackingField());
		mmsMessage.setSubject(message.get_x003C_Subject_x003E_k__BackingField());
		if(message.get_x003C_Priority_x003E_k__BackingField().getValue().equalsIgnoreCase(MmsImplementationStub.Priority._Low))
			mmsMessage.setPriority(Priority.Low);
		else if(message.get_x003C_Priority_x003E_k__BackingField().getValue().equalsIgnoreCase(MmsImplementationStub.Priority._High))
			mmsMessage.setPriority(Priority.High);
		else 
			mmsMessage.setPriority(Priority.Normal);	
		return mmsMessage;
	}
	/**
	 * 接收上行彩信
	 * @param applicationId 应用ID
	 * @param password 应用密码
	 * @return 返回上行彩信
	 */
	public MmsMessage[] GetMessages(String applicationId, String password)
	{
		MmsImplementationStub.GetMessages request = new MmsImplementationStub.GetMessages();
		request.setApplicationId(applicationId);
		request.setPassword(password);
		MmsImplementationStub.GetMessagesResponse response;
		try
		{
			response =stub.GetMessages(request);
			if(response == null)
				return null;
		}catch(RemoteException rem)
		{
			System.err.println(rem);
			return null;
		}
		MmsImplementationStub.MmsMessage[] messages =response.getGetMessagesResult().getMmsMessage();
		if(messages == null)
			return null;
		MmsMessage[] mmsMessages = new MmsMessage[messages.length];
		for(int i=0;i<messages.length;i++)
		{
			MmsMessage mmsMessage = new MmsMessage();
			mmsMessage.setApplicationId(messages[i].get_x003C_ApplicationId_x003E_k__BackingField());
			mmsMessage.setContent(messages[i].get_x003C_Content_x003E_k__BackingField());
			mmsMessage.setExtendCode(messages[i].get_x003C_ExtendCode_x003E_k__BackingField());
			mmsMessage.setFrom(messages[i].get_x003C_From_x003E_k__BackingField());
			mmsMessage.setId(messages[i].get_x003C_Id_x003E_k__BackingField());
			mmsMessage.setReceivedTime(messages[i].get_x003C_ReceivedTime_x003E_k__BackingField().getTime());
			mmsMessage.setTo(messages[i].get_x003C_To_x003E_k__BackingField());
			mmsMessage.setSubject(messages[i].get_x003C_Subject_x003E_k__BackingField());
			if(messages[i].get_x003C_Priority_x003E_k__BackingField().getValue().equalsIgnoreCase(MmsImplementationStub.Priority._Low))
				mmsMessage.setPriority(Priority.Low);
			else if(messages[i].get_x003C_Priority_x003E_k__BackingField().getValue().equalsIgnoreCase(MmsImplementationStub.Priority._High))
				mmsMessage.setPriority(Priority.High);
			else 
				mmsMessage.setPriority(Priority.Normal);
			mmsMessages[i] = mmsMessage;
		}
		return mmsMessages;
	}
	
	/**
	 * 新增彩信发送任务
	 * @param periodType 任务周期（年、月、周、日）
	 * @param periodValue 周期值
	 * @param periodTime 任务时间（时分秒）
	 * @param destinationAddresses 接收方地址列表(可一次发送给多人)
	 * @param subject 主题
	 * @param content 彩信内容（符合彩信规范的xml文档）
	 * @return 返回任务唯一标识
	 * @throws MessageException
	 */
	public String AddTask(int periodType, PeriodValue periodValue,PeriodTime periodTime, String[] destinationAddresses,String subject, String content) throws MessageException
	{
		if(periodType >=0 && periodType <=3)
		{}
		else
		{
			throw new MessageException("类型(periodType)必须在0~3之间。");
		}
		MmsImplementationStub.AddTask1 request = new MmsImplementationStub.AddTask1();
		MmsImplementationStub.ArrayOfstring array=new MmsImplementationStub.ArrayOfstring();
		for(int i=0;i<destinationAddresses.length;i++)
		{
			array.addString(destinationAddresses[i]);
		}
		request.setDestinationAddresses(array);
		request.setSubject(subject);
		request.setContent(content);
		switch(periodType)
		{
			case PeriodType.Day: request.setPeriodType(MmsImplementationStub.PeriodType.Day);break;
			case PeriodType.Month:request.setPeriodType(MmsImplementationStub.PeriodType.Month);break;
			case PeriodType.Week:request.setPeriodType(MmsImplementationStub.PeriodType.Week);break;
			case PeriodType.Year:request.setPeriodType(MmsImplementationStub.PeriodType.Year);break;
		}
		MmsImplementationStub.PeriodValue vPeriodValue = new MmsImplementationStub.PeriodValue();
		vPeriodValue.setDay(periodValue.getDay());
		vPeriodValue.setMonth(periodValue.getMonth());
		switch(periodValue.getWeek())
		{
			case DayOfWeek.SUNDAY :vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Sunday);break;
			case DayOfWeek.MONDAY:vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Monday);break;
			case DayOfWeek.TUESDAY:vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Thursday);break;
			case DayOfWeek.WEDNESDAY:vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Wednesday);break;
			case DayOfWeek.THURSDAY:vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Thursday);break;
			case DayOfWeek.FRIDAY:vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Friday);break;
			case DayOfWeek.SATURDAY:vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Saturday);break;
		}
		request.setPeriodValue(vPeriodValue);
		MmsImplementationStub.PeriodTime vperiodTime = new MmsImplementationStub.PeriodTime();
		vperiodTime.setHour(periodTime.getHour());
		vperiodTime.setMinute(periodTime.getMinute());
		vperiodTime.setSecond(periodTime.getSecond());
		request.setPeriodTime(vperiodTime);
		try
		{
			return stub.AddTask1(request).getAddTask1Result();
		}catch(RemoteException rem)
		{
			System.err.println(rem);
			return null;
		}
	}
	
	/**
	 * 新增彩信发送任务
	 * @param periodType 任务周期（年、月、周、日）
	 * @param periodValue 周期值
	 * @param periodTime 任务时间（时分秒）
	 * @param destinationAddresses 接收方地址列表(可一次发送给多人)
	 * @param subject 主题
	 * @param content 彩信内容（符合彩信规范的xml文档）
	 * @param extendCode 扩展号码
	 * @return 返回任务唯一标识
	 * @throws MessageException
	 */
	public String AddTask(int periodType, PeriodValue periodValue,PeriodTime periodTime, String[] destinationAddresses,String subject, String content,String extendCode) throws MessageException
	{
		if(periodType >=0 && periodType <=3)
		{}
		else
		{
			throw new MessageException("类型(periodType)必须在0~3之间。");
		}
		MmsImplementationStub.AddTask2 request = new MmsImplementationStub.AddTask2();
		MmsImplementationStub.ArrayOfstring array=new MmsImplementationStub.ArrayOfstring();
		for(int i=0;i<destinationAddresses.length;i++)
		{
			array.addString(destinationAddresses[i]);
		}
		request.setDestinationAddresses(array);
		request.setSubject(subject);
		request.setContent(content);
		switch(periodType)
		{
			case PeriodType.Day: request.setPeriodType(MmsImplementationStub.PeriodType.Day);break;
			case PeriodType.Month:request.setPeriodType(MmsImplementationStub.PeriodType.Month);break;
			case PeriodType.Week:request.setPeriodType(MmsImplementationStub.PeriodType.Week);break;
			case PeriodType.Year:request.setPeriodType(MmsImplementationStub.PeriodType.Year);break;
		}
		MmsImplementationStub.PeriodValue vPeriodValue = new MmsImplementationStub.PeriodValue();
		vPeriodValue.setDay(periodValue.getDay());
		vPeriodValue.setMonth(periodValue.getMonth());
		switch(periodValue.getWeek())
		{
			case DayOfWeek.SUNDAY :vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Sunday);break;
			case DayOfWeek.MONDAY:vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Monday);break;
			case DayOfWeek.TUESDAY:vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Thursday);break;
			case DayOfWeek.WEDNESDAY:vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Wednesday);break;
			case DayOfWeek.THURSDAY:vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Thursday);break;
			case DayOfWeek.FRIDAY:vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Friday);break;
			case DayOfWeek.SATURDAY:vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Saturday);break;
		}
		request.setPeriodValue(vPeriodValue);
		MmsImplementationStub.PeriodTime vperiodTime = new MmsImplementationStub.PeriodTime();
		vperiodTime.setHour(periodTime.getHour());
		vperiodTime.setMinute(periodTime.getMinute());
		vperiodTime.setSecond(periodTime.getSecond());
		request.setPeriodTime(vperiodTime);
		request.setExtendCode(extendCode);
		try
		{
			return stub.AddTask2(request).getAddTask2Result();
		}catch(RemoteException rem)
		{
			System.err.println(rem);
			return null;
		}
	}
	
	/**
	 * 新增彩信发送任务
	 * @param periodType 任务周期（年、月、周、日）
	 * @param periodValue 周期值
	 * @param periodTime 任务时间（时分秒）
	 * @param destinationAddresses 接收方地址列表(可一次发送给多人)
	 * @param subject 主题
	 * @param content 彩信内容（符合彩信规范的xml文档）
	 * @param extendCode 扩展号码
	 * @param applicationId 应用ID
	 * @param password  应用密码
	 * @return 返回任务唯一标识
	 * @throws MessageException
	 */
	public String AddTask(int periodType, PeriodValue periodValue,PeriodTime periodTime, String[] destinationAddresses,String subject, String content,String extendCode,String applicationId,String password) throws MessageException
	{
		if(periodType >=0 && periodType <=3)
		{}
		else
		{
			throw new MessageException("类型(periodType)必须在0~3之间。");
		}
		MmsImplementationStub.AddTask3 request = new MmsImplementationStub.AddTask3();
		MmsImplementationStub.ArrayOfstring array=new MmsImplementationStub.ArrayOfstring();
		for(int i=0;i<destinationAddresses.length;i++)
		{
			array.addString(destinationAddresses[i]);
		}
		request.setDestinationAddresses(array);
		request.setSubject(subject);
		request.setContent(content);
		switch(periodType)
		{
			case PeriodType.Day: request.setPeriodType(MmsImplementationStub.PeriodType.Day);break;
			case PeriodType.Month:request.setPeriodType(MmsImplementationStub.PeriodType.Month);break;
			case PeriodType.Week:request.setPeriodType(MmsImplementationStub.PeriodType.Week);break;
			case PeriodType.Year:request.setPeriodType(MmsImplementationStub.PeriodType.Year);break;
		}
		MmsImplementationStub.PeriodValue vPeriodValue = new MmsImplementationStub.PeriodValue();
		vPeriodValue.setDay(periodValue.getDay());
		vPeriodValue.setMonth(periodValue.getMonth());
		switch(periodValue.getWeek())
		{
			case DayOfWeek.SUNDAY :vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Sunday);break;
			case DayOfWeek.MONDAY:vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Monday);break;
			case DayOfWeek.TUESDAY:vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Thursday);break;
			case DayOfWeek.WEDNESDAY:vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Wednesday);break;
			case DayOfWeek.THURSDAY:vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Thursday);break;
			case DayOfWeek.FRIDAY:vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Friday);break;
			case DayOfWeek.SATURDAY:vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Saturday);break;
		}
		request.setPeriodValue(vPeriodValue);
		MmsImplementationStub.PeriodTime vperiodTime = new MmsImplementationStub.PeriodTime();
		vperiodTime.setHour(periodTime.getHour());
		vperiodTime.setMinute(periodTime.getMinute());
		vperiodTime.setSecond(periodTime.getSecond());
		request.setPeriodTime(vperiodTime);
		request.setExtendCode(extendCode);
		request.setApplicationId(applicationId);
		request.setPassword(password);
		try
		{
			return stub.AddTask3(request).getAddTask3Result();
		}catch(RemoteException rem)
		{
			System.err.println(rem);
			return null;
		}
	}
	
	/**
	 * 新增彩信发送任务
	 * @param periodType 任务周期（年、月、周、日）
	 * @param periodValue 周期值
	 * @param periodTime 任务时间（时分秒）
	 * @param destinationAddresses 接收方地址列表(可一次发送给多人)
	 * @param subject 主题
	 * @param content 彩信内容（符合彩信规范的xml文档）
	 * @param extendCode 扩展号码
	 * @param applicationId 应用ID
	 * @param password  应用密码
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @return 返回任务唯一标识
	 * @throws MessageException
	 */
	public String AddTask(int periodType, PeriodValue periodValue,PeriodTime periodTime, String[] destinationAddresses,String subject, String content,String extendCode,String applicationId,String password,java.util.Calendar beginTime,java.util.Calendar endTime) throws MessageException
	{
		if(periodType >=0 && periodType <=3)
		{}
		else
		{
			throw new MessageException("类型(periodType)必须在0~3之间。");
		}
		MmsImplementationStub.AddTask4 request = new MmsImplementationStub.AddTask4();
		MmsImplementationStub.ArrayOfstring array=new MmsImplementationStub.ArrayOfstring();
		for(int i=0;i<destinationAddresses.length;i++)
		{
			array.addString(destinationAddresses[i]);
		}
		request.setDestinationAddresses(array);
		request.setSubject(subject);
		request.setContent(content);
		switch(periodType)
		{
			case PeriodType.Day: request.setPeriodType(MmsImplementationStub.PeriodType.Day);break;
			case PeriodType.Month:request.setPeriodType(MmsImplementationStub.PeriodType.Month);break;
			case PeriodType.Week:request.setPeriodType(MmsImplementationStub.PeriodType.Week);break;
			case PeriodType.Year:request.setPeriodType(MmsImplementationStub.PeriodType.Year);break;
		}
		MmsImplementationStub.PeriodValue vPeriodValue = new MmsImplementationStub.PeriodValue();
		vPeriodValue.setDay(periodValue.getDay());
		vPeriodValue.setMonth(periodValue.getMonth());
		switch(periodValue.getWeek())
		{
			case DayOfWeek.SUNDAY :vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Sunday);break;
			case DayOfWeek.MONDAY:vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Monday);break;
			case DayOfWeek.TUESDAY:vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Thursday);break;
			case DayOfWeek.WEDNESDAY:vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Wednesday);break;
			case DayOfWeek.THURSDAY:vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Thursday);break;
			case DayOfWeek.FRIDAY:vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Friday);break;
			case DayOfWeek.SATURDAY:vPeriodValue.setWeek(MmsImplementationStub.DayOfWeek.Saturday);break;
		}
		request.setPeriodValue(vPeriodValue);
		MmsImplementationStub.PeriodTime vperiodTime = new MmsImplementationStub.PeriodTime();
		vperiodTime.setHour(periodTime.getHour());
		vperiodTime.setMinute(periodTime.getMinute());
		vperiodTime.setSecond(periodTime.getSecond());
		request.setPeriodTime(vperiodTime);
		request.setExtendCode(extendCode);
		request.setApplicationId(applicationId);
		request.setPassword(password);
		request.setBeginTime(beginTime);
		request.setEndTime(endTime);
		try
		{
			return stub.AddTask4(request).getAddTask4Result();
		}catch(RemoteException rem)
		{
			System.err.println(rem);
			return null;
		}
	}
	/**
	 * 任务移除
	 * @param taskId 任务唯一标识
	 */
	public void RemoveTask(String taskId) 
	{
		MmsImplementationStub.RemoveTask request = new MmsImplementationStub.RemoveTask();
		request.setTaskId(taskId);
		try
		{
			stub.RemoveTask(request);
		}catch(RemoteException rem)
		{
			System.err.println(rem);
		}
	}
	
	/**
	 * 任务移除
	 * @param taskId 任务唯一标识
	 * @param password 应用密码
	 */
	public void RemoveTask(String taskId,String password) 
	{
		MmsImplementationStub.RemoveTask2 request = new MmsImplementationStub.RemoveTask2();
		request.setTaskId(taskId);
		request.setPassword(password);
		try
		{
			stub.RemoveTask2(request);
		}catch(RemoteException rem)
		{
			System.err.println(rem);
		}
	}
}
