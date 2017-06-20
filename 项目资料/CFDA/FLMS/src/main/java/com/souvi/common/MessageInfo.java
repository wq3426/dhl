package com.souvi.common;

/**
 * 接口返回消息
 * 
 * @author sunjitao
 *
 */
public class MessageInfo {

	private boolean isSuccess;
	private String message;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
