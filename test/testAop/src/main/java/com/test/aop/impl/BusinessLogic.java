package com.test.aop.impl;

import com.test.aop.interfaces.IBusinessLogic;
import com.test.exception.BusinessLogicException;

public class BusinessLogic implements IBusinessLogic {

	@Override
	public void foo() {
		System.out.println("foo() ... ");
	}

	@Override
	public void bar() throws BusinessLogicException {
		System.out.println("bar() ... ");
		//throw new RuntimeException(" foo() has runtime error ... ");
	}

	@Override
	public long time() {
		System.out.println("time() ... ");
		return 0;
	}

}
