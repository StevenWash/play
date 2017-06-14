package com.ckjava.thread;

/**
 * Thread.join() 等待当前线程终止执行
 * 当多个线程并行执行的时候，如果想让某个线程执行完毕后其他线程再执行，那么调用该线程的 join 方法即可。
 * 
 * @author chen_k
 * @see <a href="http://www.open-open.com/lib/view/open1371741636171.html">Java Thread.join()详解</a>
 *
 */
public class TestThreadJoin {
	
	public static void main(String[] args) {
		TestThreadJoin join = new TestThreadJoin();
		join.testJoinMethod();
	}
	
	public void testJoinMethod() {
		Thread t1 = new Thread(new Runner1(2000));
		t1.start();
		
		Thread t2 = new Thread(new Runner2(1000));
		t2.start();
		try {
			t2.join(); // t2 一定比t3先执行完毕
		} catch (Exception e) {
		}
		
		Thread t3 = new Thread(new Runner3(500));
		t3.start();
	}
	
	public static class Runner1 implements Runnable {

		private long waitTime;
		
		public Runner1(long waitTime) {
			super();
			this.waitTime = waitTime;
		}

		@Override
		public void run() {
			
			try {
				Thread.sleep(waitTime);
				
				System.out.println("Thread1 end run");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static class Runner2 implements Runnable {

		private long waitTime;
		
		public Runner2(long waitTime) {
			super();
			this.waitTime = waitTime;
		}
		
		@Override
		public void run() {
			
			try {
				Thread.sleep(waitTime);
				
				System.out.println("Thread2 end run");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static class Runner3 implements Runnable {
		
		private long waitTime;
		
		public Runner3(long waitTime) {
			super();
			this.waitTime = waitTime;
		}
		
		@Override
		public void run() {
			
			try {
				Thread.sleep(waitTime);
				
				System.out.println("Thread3 end run");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
