package com.ckjava.proxy.interfaces;

import com.ckjava.proxy.annotations.NotEmpty;
import com.ckjava.proxy.annotations.NotNull;

public interface IConnection {
	
	public void open();
	
	public void close();
	
	public void get(@NotNull @NotEmpty String key);
	
	public void get(@NotNull String key, @NotNull String value);
	
	public void create();
	
}
