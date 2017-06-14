package com.test.api.bean;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "基本信息")
public class BasicInfoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ua", required = false)
	private String ua;
	
	@ApiModelProperty(value = "api 版本号", required = false)
	private String apiversion;
	
	@ApiModelProperty(value = "app 版本号", required = false)
	private String appver;
	
	@ApiModelProperty(value = "", required = false)
	private String src;
	
	@ApiModelProperty(value = "设备id", required = false)
	private String uuid;
	
	@ApiModelProperty(value = "用户id", required = false)
	private Integer uid;
	
	@ApiModelProperty(value = "", required = false)
	private String salt;
	
	@ApiModelProperty(value = "", required = false)
	private String token;
	
	@ApiModelProperty(value = "", required = false)
	private String test;
	
	public String getUa() {
		return ua;
	}
	public void setUa(String ua) {
		this.ua = ua;
	}
	public String getApiversion() {
		return apiversion;
	}
	public void setApiversion(String apiversion) {
		this.apiversion = apiversion;
	}
	public String getAppver() {
		return appver;
	}
	public void setAppver(String appver) {
		this.appver = appver;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public BasicInfoBean(String ua, String apiversion, String appver, String src, String uuid, Integer uid, String salt,
			String token, String test) {
		super();
		this.ua = ua;
		this.apiversion = apiversion;
		this.appver = appver;
		this.src = src;
		this.uuid = uuid;
		this.uid = uid;
		this.salt = salt;
		this.token = token;
		this.test = test;
	}
	public BasicInfoBean() {
		super();
	}
	@Override
	public String toString() {
		return "BasicInfoBean [ua=" + ua + ", apiversion=" + apiversion + ", appver=" + appver + ", src=" + src
				+ ", uuid=" + uuid + ", uid=" + uid + ", salt=" + salt + ", token=" + token + ", test=" + test + "]";
	}
}
