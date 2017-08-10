package com.ckjava.annotations;

public @interface Author {

	String name();
	String created();
	int revision() default 1;
	String[] reviewers() default {};
}
