package com.authority.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.zjrc.meeting.domain.DeliveryReport;

@WebService(targetNamespace = "http://openmas.chinamobile.com/pulgin", name = "SmsServicePortType")
public interface SmsService {

	@WebMethod(operationName = "NotifyMmsDeliveryReport", action = "urn:NotifyMmsDeliveryReport")
	public void notifyMmsDeliveryReport(
			@WebParam(name = "deliveryReport", targetNamespace = "http://openmas.chinamobile.com/pulgin")
			DeliveryReport deliveryReport);

	@WebMethod(operationName = "NotifyMms", action = "urn:NotifyMms")
	public void notifyMms(@WebParam(name = "messageId", targetNamespace = "http://openmas.chinamobile.com/pulgin")
	String messageId);

	@WebMethod(action = "urn:getSystemTime")
	public String getSystemTime(@WebParam(name = "d", targetNamespace = "http://openmas.chinamobile.com/pulgin")
	javax.xml.datatype.XMLGregorianCalendar d);

	@WebMethod(operationName = "NotifySmsDeliveryReport", action = "urn:NotifySmsDeliveryReport")
	public void notifySmsDeliveryReport(
			@WebParam(name = "deliveryReport", targetNamespace = "http://openmas.chinamobile.com/pulgin")
			DeliveryReport deliveryReport);

	@WebMethod(operationName = "NotifySms", action = "urn:NotifySms")
	public void notifySms(@WebParam(name = "messageId", targetNamespace = "http://openmas.chinamobile.com/pulgin")
	String messageId);
}
