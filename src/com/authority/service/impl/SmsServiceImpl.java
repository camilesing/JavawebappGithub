package com.authority.service.impl;

import java.util.Date;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.authority.common.springmvc.DateConvertEditor;
import com.authority.service.SmsService;
import com.authority.web.controller.WebFilemanagerController;
import com.chinamobile.openmas.entity.MmsMessage;
import com.chinamobile.openmas.entity.SmsMessage;
import com.zjrc.meeting.common.MmsProvider;
import com.zjrc.meeting.common.SmsProvider;
import com.zjrc.meeting.domain.DeliveryReport;


@WebService(endpointInterface = "com.zjrc.meeting.sms.SmsService", targetNamespace = "http://openmas.chinamobile.com/pulgin")
public class SmsServiceImpl implements SmsService {
	private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
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

	//���Żظ���Ϣ
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

	//���ŷ��ͺ��״̬��Ϣ
	public void notifyMmsDeliveryReport(DeliveryReport deliveryReport) {
		System.out.println("**********MessageId:" + deliveryReport.getMessageId().getValue());
		System.out.println("**********MessageDeliveryStatus:" + deliveryReport.getMessageDeliveryStatus().getValue());
		System.out.println("**********ReceivedAddress:" + deliveryReport.getReceivedAddress().getValue());
		System.out.println("**********StatusCode:" + deliveryReport.getStatusCode().getValue());
		System.out.println("**********SendAddress:" + deliveryReport.getSendAddress().getValue());
	}

	//���Żظ���Ϣ
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

	//���ŷ��ͺ��״̬��Ϣ
	public void notifySmsDeliveryReport(DeliveryReport deliveryReport) {
		/*System.out.println("**********MessageId:" + deliveryReport.getMessageId().getValue());
		System.out.println("**********MessageDeliveryStatus:" + deliveryReport.getMessageDeliveryStatus().getValue());
		System.out.println("**********ReceivedAddress:" + deliveryReport.getReceivedAddress().getValue());
		System.out.println("**********StatusCode:" + deliveryReport.getStatusCode().getValue());
		System.out.println("**********SendAddress:" + deliveryReport.getSendAddress().getValue());*/
		
		//�����ܵ�����Ϣ��д���ݿ�		
		String messageid =deliveryReport.getMessageId().getValue();
		String MessageDeliveryStatus = deliveryReport.getMessageDeliveryStatus().getValue();
		String ReceivedAddress = deliveryReport.getReceivedAddress().getValue();
		String StatusCode = deliveryReport.getStatusCode().getValue();
		String SendAddress = deliveryReport.getSendAddress().getValue();
		
		logger .debug("messageid: {},MessageDeliveryStatus:{},ReceivedAddress:{},StatusCode:{},SendAddress:{}", 
				new Object[]{messageid,MessageDeliveryStatus,ReceivedAddress,StatusCode,SendAddress});
		
		messageid= messageid==null?"":messageid;
		StatusCode = StatusCode==null?"":StatusCode;
		
		String STATE ="E";
		if(StatusCode.toUpperCase().equals("DELIVRD")){ //
			STATE = "B";
		}
		//��������״̬������Ϣ��  HENLO_SMS_STORE ��
		String insert = "insert into HENLO_SMS_STORE(ID, MESSAGEID, MESSAGEDELIVERYSTATUS, RECEIVEDADDRESS, STATUSCODE, SENDADDRESS,addtime) " +
				"select sys_guid(),'"+messageid+"','"+MessageDeliveryStatus+"','"+ReceivedAddress+"','"+StatusCode+"','"+SendAddress+"',sysdate from dual";
		jdbcTemplate.update(insert);
		try {
			if(!messageid.equals("")){
				String update = "update SMS_OUTMSG a set a.STATE='"+STATE+"' where MESSAGEID ='"+messageid+"' and a.phone='"+ReceivedAddress+"'";
				String query = "";
				if(jdbcTemplate.update(update)>0){
					//������Դ��,�����еĶ���
					if(STATE.equals("B")){
						System.out.println("======��ʼ���±���U_MESSAGE_ADDR U_MESSAGE========");
						update ="update U_MESSAGE_ADDR a set a.STATE=2 " +
								"where exists(select 'x' from SMS_OUTMSG b where b.STATE='B' and a.id=b.tablerowid and b.tablename='U_MESSAGE_ADDR' and b.MESSAGEID ='"+messageid+"' and b.phone='"+ReceivedAddress+"' )";
						jdbcTemplate.update(update);
						query = "select max(tablerowid) tablerowid from SMS_OUTMSG where MESSAGEID ='"+messageid+"' and phone='"+ReceivedAddress+"'";
						String tablerowid = jdbcTemplate.queryForObject(query, String.class);
						if(tablerowid==null||tablerowid.equals(""))
							;
						else{
							query ="select U_MESSAGE_ID from U_MESSAGE_ADDR where id='"+tablerowid+"'";
							String U_MESSAGE_ID = jdbcTemplate.queryForObject(query, String.class);
							if(U_MESSAGE_ID==null||U_MESSAGE_ID.equals(""))
								;
							else{
								update ="update U_MESSAGE a set a.STATE=2 " +
										"where not exists(select 'x' from U_MESSAGE_ADDR b where a.id=b.U_MESSAGE_ID and b.STATE!='2' ) and id='"+U_MESSAGE_ID+"'";
								jdbcTemplate.update(update);
								
								update ="update U_MESSAGE a set a.SUCCESS_CNT = (select count(*) from U_MESSAGE_ADDR b where a.id=b.U_MESSAGE_ID and b.STATE='2') where id='"+U_MESSAGE_ID+"'";
								jdbcTemplate.update(update);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception:{} ", e);
			
			// TODO: handle exception
		}
		
				
	}

}
