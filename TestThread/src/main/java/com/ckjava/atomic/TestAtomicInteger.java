package com.ckjava.atomic;

public class TestAtomicInteger {
	public static void main(String[] args) throws InterruptedException {
		/**
		 * 线程不安全的调用,每个线程使用了单独的共享的 CounterRunner 对象,不明白为什么最后没有出现同步的问题
		 */
		//invoke1();
		
		/**
		 * 线程安全的调用,每个线程尽管使用了共享的 CounterRunner 对象
		 * 但是通过 AtomicInteger, 不会出现多个线程并行访问和操作同一个对象的问题
		 */
		//invoke2();
		
		/**
		 * 线程安全的调用,每个线程使用了单独的 CounterRunner2 对象, 不会出现多个线程并行访问和操作同一个对象的问题
		 * 
		 *  Thread-0:0
			Thread-1:0
			Thread-0:1
			Thread-1:1
			Thread-0:3
			Thread-2:0
			Thread-1:3
			Thread-2:1
			Thread-2:3
		 */
		//invoke3();
		
		/**
		 * 线程不安全调用,3个线程使用了同一个 CounterRunner2 对象, 导致加出来了同样的数字
		 * 
		 *  Thread-0:0
			Thread-0:1
			Thread-0:3
			Thread-1:0
			Thread-1:4
			Thread-2:3
			Thread-2:7
			Thread-2:9
			Thread-1:6
		 */
		//invoke4();
		
		/**
		 * 不推荐的用法
		 */
		//invoke5();
		
		/**
		 * 推荐的用法, 多个线程并行访问同一个对象
		 */
		invoke6();
	}

	public static void invoke1() {
		Thread t1 = new Thread(new CounterRunner());
		t1.start();
		Thread t2 = new Thread(new CounterRunner());
		t2.start();
		Thread t3 = new Thread(new CounterRunner());
		t3.start();
	}
	
	public static void invoke2() {
		CounterRunner runner = new CounterRunner();
		Thread t1 = new Thread(runner);
		t1.start();
		Thread t2 = new Thread(runner);
		t2.start();
		Thread t3 = new Thread(runner);
		t3.start();
	}
	
	public static void invoke3() {
		Thread t1 = new Thread(new CounterRunner2());
		t1.start();
		Thread t2 = new Thread(new CounterRunner2());
		t2.start();
		Thread t3 = new Thread(new CounterRunner2());
		t3.start();
	}
	
	public static void invoke4() {
		CounterRunner2 runner = new CounterRunner2();
		Thread t1 = new Thread(runner);
		t1.start();
		Thread t2 = new Thread(runner);
		t2.start();
		Thread t3 = new Thread(runner);
		t3.start();
	}
	
	public static void invoke5() {
		Thread t1 = new Thread(new CounterRunner3());
		t1.start();
		Thread t2 = new Thread(new CounterRunner3());
		t2.start();
		Thread t3 = new Thread(new CounterRunner3());
		t3.start();
	}
	
	public static void invoke6() {
		CounterRunner3 runner = new CounterRunner3();
		Thread t1 = new Thread(runner);
		t1.start();
		Thread t2 = new Thread(runner);
		t2.start();
		Thread t3 = new Thread(runner);
		t3.start();
	}
}
