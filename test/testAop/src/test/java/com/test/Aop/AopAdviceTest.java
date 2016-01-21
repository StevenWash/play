package com.test.Aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.test.aop.interfaces.IBusinessLogic;
import com.test.exception.BusinessLogicException;

public class AopAdviceTest {
    private static ApplicationContext ac;

	public static void main(String[] args) {
        ac = new ClassPathXmlApplicationContext("advice-config.xml");
        IBusinessLogic ibl = (IBusinessLogic) ac.getBean("businessLogicBean");
        ibl.foo();
        try {
            ibl.bar();
        } catch (BusinessLogicException e) {
            System.out.println("Caught BusinessLogicException");
        }
        ibl.time();
    }
}

