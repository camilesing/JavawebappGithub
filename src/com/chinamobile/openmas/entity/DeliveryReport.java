/**
 * 
 */
package com.chinamobile.openmas.entity;

/**
 * @author Openmas
 *
 */
public class DeliveryReport 
{
	private String MessageId;
	
	private String ReceivedAddress;
//	<summary>
//    ÐÐÒµÍø¹Ø·µ»ØµÄ×´Ì¬ºÅÂë
//    </summary>
	private String StatusCode;
	
	private String MessageDeliveryStatus;
	
	private String SendAddress;
	
	public DeliveryReport(){}
	
	public DeliveryReport(String messageId, String receivedAddress, String sendAddress,String  messageDeliveryStatus, String statusCode)
    {
        this.MessageId = messageId;
        this.ReceivedAddress = receivedAddress;
        this.StatusCode = statusCode;
        this.MessageDeliveryStatus = messageDeliveryStatus;
        this.SendAddress = sendAddress;
    }
	
	public String getMessageId()
    {
       return MessageId;
    }
	
	public void setMessageId(String messageid)
	{
		this.MessageId = messageid;
	}
	
	public String getReceivedAddress()
	{
		return ReceivedAddress;
	}
	public void setReceivedAddress(String receivedAddress)
	{
		this.ReceivedAddress = receivedAddress;
	}
	
	public String getStatusCode()
	{
		return StatusCode;
	}
	
	public void setStatusCode(String statusCode)
	{
		this.StatusCode = statusCode;
	}
	/**
	 * 
	 * @return ×´Ì¬
	 * @see DeliveryStatus
	 */
	public String getMessageDeliveryStatus()
    {
        return MessageDeliveryStatus;
    }
	public void setMessageDeliveryStatus(String messageDeliveryStatus)
	{
		this.MessageDeliveryStatus = messageDeliveryStatus;
		
	}
	
	public String getSendAddress()
    {
        return SendAddress;
    }
	public void setSendAddress(String sendAddress)
	{
		this.SendAddress = sendAddress;
	}
	
	public String toString()
	{
		return "MessageId:"+MessageId+",ReceivedAddress:" + ReceivedAddress + ",SendAddress:"+SendAddress+",DeliveryStatusEnum:" + MessageDeliveryStatus + "," + "StatusCode:" + StatusCode;
	}
	
}
