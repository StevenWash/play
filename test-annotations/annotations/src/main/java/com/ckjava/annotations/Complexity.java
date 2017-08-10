package com.ckjava.annotations;

public @interface Complexity {

	ComplexityLevel value() default ComplexityLevel.MEDIUM;
}
