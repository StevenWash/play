package com.test.api;

public enum ResultCode {
	Success(200, "success"), UnChanged(304, "对象有效仍可用"), ParamError(400, "参数错误"), NotSupport(401, "不支持的方法"), Rejected(
			403, ""), MethodNotFind(404, "未找到该方法"), System_Error(500, "系统繁忙，请稍候重试!"), BizError(1001, "业务逻辑错误");
	ResultCode(int code, String desc) {
		this.code = code;
		this.message = desc;
	}

	private int code;
	private String message;

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
