package com.test.api.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户实体")
public class User {

	@ApiModelProperty(notes = "用户id", required = false)
	private String id;
	@ApiModelProperty(notes = "姓名", required = false)
	private String name;
	@ApiModelProperty(notes = "性别", required = false)
	private String sex;
	@ApiModelProperty(notes = "年龄", required = false)
	private int age;
	@ApiModelProperty(notes = "电话", required = false)
	private String phone;
	@ApiModelProperty(notes = "电子邮件", required = false)
	private String mailAddress;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public User(String id, String name, String sex, int age, String phone, String mailAddress) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.phone = phone;
		this.mailAddress = mailAddress;
	}
	public User() {
		super();
	}
	
}
