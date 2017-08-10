package com.ckjava.annotations.client;

import com.ckjava.annotations.Complexity;
import com.ckjava.annotations.ComplexityLevel;

@Complexity(value = ComplexityLevel.COMPLEX)
public class SimpleAnnotationsTest {
	
	@Complexity
	public void theMethod () {
		System.out.println("use on the method");
	}
}
