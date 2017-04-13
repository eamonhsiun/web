package com.wemeow.web.util.state;


public class Status {
	/**
	 * 状态值
	 */
	private boolean result;
	
	/**
	 * 状态码
	 */
	private int statusCode;
	
	/**
	 * 需要让用户知道的数据
	 */
	private Object data;
	
	/**
	 * 加密验证数据（保护内部逻辑）
	 */
	private Object extra;


	public Status(boolean result, int statusCode, Object data,Object extra) {
		super();
		this.result = result;
		this.statusCode = statusCode;
		this.data = data;
		this.extra = extra;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public Object getExtra() {
		return extra;
	}

	public void setExtra(Object extra) {
		this.extra = extra;
	}
	


	
}
