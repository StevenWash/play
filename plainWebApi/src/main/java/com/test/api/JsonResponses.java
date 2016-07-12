package com.test.api;

import java.io.Serializable;

public class JsonResponses implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 返回提示
	 */
	private String message;			
	/**
	 * 返回状态(200为成功,400为失败)
	 */
	private int code;
	/**
	 * 返回数据(Object)
	 */
	private Object data;

		public JsonResponses() {
		this.code = ResultCode.Success.getCode();
		this.message = ResultCode.Success.getMessage();
	}
	
	/**
	 * @param code 状态编码
	 * @param message 提示信息
	 * @param data 返回数据
	 */
	public JsonResponses(int code,String message,Object data){
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	/**
	 * @param code 状态编码
	 * @param message 提示信息
	 */
	public JsonResponses(int code,String message){
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
