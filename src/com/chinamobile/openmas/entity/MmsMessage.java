/**
 * 
 */
package com.chinamobile.openmas.entity;

import java.util.Date;

/**
 * @author OpenMas
 *
 */
public class MmsMessage {
	
	private String Id;
	private String ApplicationId;
	private String From;
	private String To;
	private String Subject;
	private String Content;
	private int Priority;
	private String ExtendCode;
	private Date ReceivedTime;
	
	public MmsMessage(){}
	
	/**
	 * @return id 彩信标识。
	 */
	public String getId()
	{
		return Id;
	}
	/**
	 * @param id 彩信标识。
	 */
	public void setId(String id)
	{
		this.Id = id;
	}
	/**
	 * @return applicationId 彩信接收方应用标识
	 */
	public String getApplicationId()
	{
		return ApplicationId;
	}
	/**
	 * @param applicationId 彩信接收方应用标识
	 */
	public void setApplicationId(String applicationId)
	{
		ApplicationId = applicationId;
	}
	
	/**
	 * @return content 彩信内容
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
	 * @return extendCode 彩信接收方的扩展号码
	 */
	public String getExtendCode() {
		return ExtendCode;
	}
	/**
	 * @param extendCode 彩信接收方的扩展号码
	 */
	public void setExtendCode(String extendCode) {
		ExtendCode = extendCode;
	}
	/**
	 * @return from 彩信发送方
	 */
	public String getFrom() {
		return From;
	}
	/**
	 * @param from 彩信发送方
	 */
	public void setFrom(String from) {
		From = from;
	}
	/**
	 * @return receivedTime 彩信接收时间
	 */
	public Date getReceivedTime() {
		return ReceivedTime;
	}
	/**
	 * @param receivedTime 彩信接收时间
	 */
	public void setReceivedTime(Date receivedTime) {
		ReceivedTime = receivedTime;
	}
	/**
	 * @return to 彩信接收方
	 */
	public String getTo() {
		return To;
	}
	/**
	 * @param to 彩信接收方
	 */
	public void setTo(String to) {
		To = to;
	}

	/**
	 * @return priority 优先级
	 * @see Priority
	 */
	public int getPriority() {
		return Priority;
	}

	/**
	 * @param priority 优先级
	 */
	public void setPriority(int priority) {
		Priority = priority;
	}

	/**
	 * @return subject 彩信标题
	 */
	public String getSubject() {
		return Subject;
	}

	/**
	 * @param subject 彩信标题
	 */
	public void setSubject(String subject) {
		Subject = subject;
	}
	public String toString()
	{
		return "MessageType:MMS;"
        + "From: " + From + ";"
        + "To:" + To + ";"
        + "Priority:" + Priority + ";"
        + "Subject:" + Subject + ";"
        + "Content:" + Content + ";";
	}

}
