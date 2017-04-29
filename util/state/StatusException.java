package com.wemeow.web.util.state;


public class StatusException extends Exception{
	private static final long serialVersionUID = 6922891378191357989L;

	private int statusCode;
	
	private int header;

	public StatusException(int statusCode) {
		super();
		this.setStatusCode(statusCode);
	}
	
	public StatusException(int header,int statusCode) {
		super();
		this.setStatusCode(statusCode);
		this.setHeader(header);
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public static Status procExcp(Exception e){
		if(e instanceof StatusException)
			return new Status(false, ((StatusException)e).getStatusCode(), null, null);
		e.printStackTrace();
		return new Status(false, StatusCode.FAILED, null, null);
	}

	public int getHeader() {
		return header;
	}

	public void setHeader(int header) {
		this.header = header;
	}
}
