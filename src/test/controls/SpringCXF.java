package test.controls;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.authority.service.SmsService;
import com.zjrc.meeting.domain.DeliveryReport;

public class SpringCXF {
	
	public static void main(String[] args) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(SmsService.class);
		factory.setAddress("http://192.168.80.68:9000/Javawebapp/Openmas_Webservice/SmsService");
		SmsService sms = (SmsService) factory.create();
		DeliveryReport report = new DeliveryReport();
		JAXBElement<String> messageId = new JAXBElement<String>(new QName("http://entity.openmas.chinamobile.com/xsd", "messageId"), String.class, "206a63d6-f82d-4e46-bd13-25e8e05b802e");
		JAXBElement<String> messageDeliveryStatus = new JAXBElement<String>(new QName("http://entity.openmas.chinamobile.com/xsd", "messageDeliveryStatus"), String.class, "0");
		JAXBElement<String> receivedAddress = new JAXBElement<String>(new QName("http://entity.openmas.chinamobile.com/xsd", "receivedAddress"), String.class, "1");
		JAXBElement<String> statusCode = new JAXBElement<String>(new QName("http://entity.openmas.chinamobile.com/xsd", "statusCode"), String.class, "DELIVRD");
		JAXBElement<String> sendAddress = new JAXBElement<String>(new QName("http://entity.openmas.chinamobile.com/xsd", "sendAddress"), String.class, "18857846128");
				
		report.setMessageId(messageId);
		report.setMessageDeliveryStatus(messageDeliveryStatus);
		report.setReceivedAddress(receivedAddress);
		report.setStatusCode(statusCode);
		report.setSendAddress(sendAddress);
		
		
		sms.notifySmsDeliveryReport(report);
	}
}
