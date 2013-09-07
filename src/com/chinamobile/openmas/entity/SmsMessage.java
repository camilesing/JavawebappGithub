/**
 * 
 */
package com.chinamobile.openmas.entity;

import java.util.Date;
import java.text.SimpleDateFormat;
/**
 * @author OpenMas
 *
 */
public class SmsMessage
{
	private String Id;
	private String ApplicationId;
	private String From;
	private String To;
	private String Content;
	private String ExtendCode;
	private Date ReceivedTime;
	
	public SmsMessage()
	{}
	public SmsMessage(String meesageId, String applicationId, String content, String from, String to, String extendCode, Date receivedTime)
    {
        this.Id = meesageId;
        this.ApplicationId = applicationId;
        this.From = from;
        this.To = to;
        this.Content = content;
        this.ExtendCode = extendCode;
        this.ReceivedTime = receivedTime;
    }
	/**
	 * @return id 短信标识。
	 */
	public String getId()
	{
		return Id;
	}
	/**
	 * @param id 短信标识。
	 */
	public void setId(String id)
	{
		this.Id = id;
	}
	/**
	 * @return applicationId 短信接收方应用标识
	 */
	public String getApplicationId()
	{
		return ApplicationId;
	}
	/**
	 * @param applicationId 短信接收方应用标识
	 */
	public void setApplicationId(String applicationId)
	{
		ApplicationId = applicationId;
	}
	
	/**
	 * @return content 短信内容
	 */
	public String getContent() {
		return Content;
	}
	/**
	 * @param content 短信内容
	 */
	public void setContent(String content) {
		Content = content;
	}
	/**
	 * @return extendCode 短信接收方的扩展号码
	 */
	public String getExtendCode() {
		return ExtendCode;
	}
	/**
	 * @param extendCode 短信接收方的扩展号码
	 */
	public void setExtendCode(String extendCode) {
		ExtendCode = extendCode;
	}
	/**
	 * @return from 短信发送方
	 */
	public String getFrom() {
		return From;
	}
	/**
	 * @param from 短信发送方
	 */
	public void setFrom(String from) {
		From = from;
	}
	/**
	 * @return receivedTime 短信接收时间
	 */
	public Date getReceivedTime() {
		return ReceivedTime;
	}
	/**
	 * @param receivedTime 短信接收时间
	 */
	public void setReceivedTime(Date receivedTime) {
		ReceivedTime = receivedTime;
	}
	/**
	 * @return to 短信接收方
	 */
	public String getTo() {
		return To;
	}
	/**
	 * @param to 短信接收方
	 */
	public void setTo(String to) {
		To = to;
	}
	public String toString()
	{
		 return "MessageType:SMS;"
         + "From: " + From + ";"
         + "To:" + To + ";"
         + "Content:" + Content + ";"
         + "ExtendCode:" + ExtendCode + ";"
         + "ReceivedTime:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ReceivedTime);
	}
	
}
