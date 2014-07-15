package com.authority.pojo;

public class PdaReturn {
	
	private String status;
	
	private String message;
	
	private String other ;
	
	public PdaReturn(){
		
		
	}
	
	public PdaReturn(String status,String message){
		this.status=status;
		this.message=message;
	}
	
	public PdaReturn(String status,String message,String other){
		this.status=status;
		this.message=message;
		this.other = other;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
	
	
	
}
