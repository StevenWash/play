package com.ckjava.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class CounterRunner3 implements Runnable {

	private static AtomicReference<Integer> value = new AtomicReference<Integer>(0);
	
	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			System.out.println(Thread.currentThread().getName() + ":" + addAndGet(1));	
			//System.out.println(Thread.currentThread().getName() + ":" + value.getAndSet(1));
		}
	}
	
	public int addAndGet(int i) {
		int current = value.get();
		value.lazySet(current += i);
		return value.get();
	}

}
