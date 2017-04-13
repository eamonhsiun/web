package com.wemeow.web.util.state;


public class StatusException extends Exception{
	private static final long serialVersionUID = 6922891378191357989L;

	private int statusCode;

	public StatusException(int statusCode) {
		super();
		this.setStatusCode(statusCode);
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public static Status procExcp(Exception e){
		if(e instanceof StatusException)
			return new Status(false, ((StatusException)e).getStatusCode(), 0, 0);
		e.printStackTrace();
		return new Status(false, StatusCode.FAILED, 0, 0);
	}
}
