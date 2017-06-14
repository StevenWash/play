package com.test.api.bean;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "对应Map的Entry实体")
public class KVBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "key", required = false)
	private String key;
	
	@ApiModelProperty(value = "value", required = false)
	private String value;
	
	@ApiModelProperty(value = "1对1 持有另一方的应用，类似链表中的指针", required = false)
	private KVBean next;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public KVBean getNext() {
		return next;
	}

	public void setNext(KVBean next) {
		this.next = next;
	}

	public KVBean() {
	}

	public KVBean(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	
}
