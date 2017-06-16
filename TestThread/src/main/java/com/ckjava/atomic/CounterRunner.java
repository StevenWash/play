package com.ckjava.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class CounterRunner implements Runnable {

	private static AtomicInteger ai = new AtomicInteger();
	
	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			System.out.println(Thread.currentThread().getName() + ":" + ai.addAndGet(1));
		}
	}

}
