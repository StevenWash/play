package com.ckjava.atomic;

public class CounterRunner2 implements Runnable {

	private int i = 0;
	
	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			setI(i);
			System.out.println(Thread.currentThread().getName() + ":" + getI());	
		}
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i += i;
	}

}
