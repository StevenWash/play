package com.test.aop.interfaces;

import com.test.exception.BusinessLogicException;

public interface IBusinessLogic {
	public void foo();

	public void bar() throws BusinessLogicException;

	public long time();
}
