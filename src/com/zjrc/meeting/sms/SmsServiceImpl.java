package com.zjrc.meeting.sms;

import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.chinamobile.openmas.entity.MmsMessage;
import com.chinamobile.openmas.entity.SmsMessage;
import com.zjrc.meeting.common.MmsProvider;
import com.zjrc.meeting.common.SmsProvider;
import com.zjrc.meeting.domain.DeliveryReport;

@WebService(endpointInterface = "com.zjrc.meeting.sms.SmsService", targetNamespace = "http://openmas.chinamobile.com/pulgin")
public class SmsServiceImpl implements SmsService {
	protected static final Log log = LogFactory.getLog(SmsServiceImpl.class);

	public String getSystemTime(javax.xml.datatype.XMLGregorianCalendar d) {
		System.out.println("Executing operation getSystemTime");
		System.out.println(d);
		try {
			String _return = "";
			return _return;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	//彩信回复信息
	public void notifyMms(String messageId) {
		MmsMessage mmsMessage = MmsProvider.getMmsMessage(messageId);
		if (mmsMessage != null) {
			System.out.println("*****MessageId:" + mmsMessage.getId());
			System.out.println("*****ApplicationId:" + mmsMessage.getApplicationId());
			System.out.println("*****From:" + mmsMessage.getFrom());
			System.out.println("*****To:" + mmsMessage.getTo());
			System.out.println("*****ExtendCode:" + mmsMessage.getExtendCode());
			System.out.println("*****Content:" + mmsMessage.getContent());
			System.out.println("*****ReceivedTime:" + mmsMessage.getReceivedTime());
		} else {
			System.out.println("null");
		}
	}

	//彩信发送后的状态信息
	public void notifyMmsDeliveryReport(DeliveryReport deliveryReport) {
		System.out.println("**********MessageId:" + deliveryReport.getMessageId().getValue());
		System.out.println("**********MessageDeliveryStatus:" + deliveryReport.getMessageDeliveryStatus().getValue());
		System.out.println("**********ReceivedAddress:" + deliveryReport.getReceivedAddress().getValue());
		System.out.println("**********StatusCode:" + deliveryReport.getStatusCode().getValue());
		System.out.println("**********SendAddress:" + deliveryReport.getSendAddress().getValue());
	}

	//短信回复信息
	public void notifySms(String messageId) {
		SmsMessage smsMessage = SmsProvider.getMmsMessage(messageId);
		if (smsMessage != null) {
			System.out.println("*****MessageId:" + smsMessage.getId());
			System.out.println("*****ApplicationId:" + smsMessage.getApplicationId());
			System.out.println("*****From:" + smsMessage.getFrom());
			System.out.println("*****To:" + smsMessage.getTo());
			System.out.println("*****ExtendCode:" + smsMessage.getExtendCode());
			System.out.println("*****Content:" + smsMessage.getContent());
			System.out.println("*****ReceivedTime:" + smsMessage.getReceivedTime());
		} else {
			System.out.println("null");
		}
	}

	//短信发送后的状态信息
	public void notifySmsDeliveryReport(DeliveryReport deliveryReport) {
		System.out.println("**********MessageId:" + deliveryReport.getMessageId().getValue());
		System.out.println("**********MessageDeliveryStatus:" + deliveryReport.getMessageDeliveryStatus().getValue());
		System.out.println("**********ReceivedAddress:" + deliveryReport.getReceivedAddress().getValue());
		System.out.println("**********StatusCode:" + deliveryReport.getStatusCode().getValue());
		System.out.println("**********SendAddress:" + deliveryReport.getSendAddress().getValue());
	}

}
