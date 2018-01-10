package com.ckjava.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

public class TestThread {
	
	public static void main(String[] args) {
		
		String outerVariable = "this outer variable";
		
		Thread testInnerClass = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println(outerVariable);
				//System.out.println("outerVariable = " + outerVariable);
			}
		});
		
		testInnerClass.start();
	}

	// 假设有10个任务，每个任务花费的时间如下
	private long[] spendTime = new long[] { 1000, 2000, 3000, 5000, 500, 100, 10, 9000, 1000, 6000 };

	/**
	 * 在单线程的情况下
	 */
	@SuppressWarnings("static-access")
	@Test
	public void TestWithoutExecuteService() {
		long begin = System.currentTimeMillis();
		for (long l : spendTime) {
			try {
				Thread.currentThread().sleep(l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("total spend : " + (System.currentTimeMillis() - begin));
	}

	/**
	 * 使用一般的多线程来完成并行任务，也就是 Thread,Runnable
	 */
	@Test
	public void TestWithMultiThread() {
		long begin = System.currentTimeMillis();
		// 通过一个同步的Map来确保所有的任务都执行完毕
		Map<String, String> result = Collections.synchronizedMap(new HashMap<String, String>());

		List<Thread> taskList = new ArrayList<Thread>();
		for (long l : spendTime) {
			Thread taskThread = new Thread(new TaskThread2(l, result));
			taskList.add(taskThread);
			taskThread.start();
		}
		while (true) { // 这部分代码可以确保所有的线程都执行完毕了
			if (result.size() == taskList.size()) {
				break;
			}
		}
		for (Iterator<String> it = result.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			System.out.println(key + result.get(key));
		}
		System.out.println("total spend : " + (System.currentTimeMillis() - begin));
	}

	/**
	 * 在使用 ExecuteService 的情况下，通过invokeAll确保所有的线程都执行完毕
	 */
	@Test
	public void TestWithExecuteService() {
		long begin = System.currentTimeMillis();

		ExecutorService executorService = Executors.newCachedThreadPool();

		List<TaskThread> taskList = new ArrayList<TaskThread>();
		for (long l : spendTime) {
			taskList.add(new TaskThread(l));
		}
		try {
			List<Future<String>> taskResult = executorService.invokeAll(taskList); // 可以确保所有的线程都执行完成
			if (taskResult != null) {
				for (Future<String> future : taskResult) {
					System.out.println(future.get()); // 获取线程的执行结果
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			executorService.shutdown();
		}

		System.out.println("total spend : " + (System.currentTimeMillis() - begin));
	}

	/**
	 * 在使用 ExecuteService 的情况下，通过自定义的方式确保所有的线程都执行完毕
	 */
	@Test
	public void TestWithExecuteService2() {
		long begin = System.currentTimeMillis();

		ExecutorService executorService = Executors.newCachedThreadPool();
		List<Future<String>> futureList = new ArrayList<Future<String>>();

		for (long l : spendTime) {
			Future<String> future = executorService.submit(new TaskThread(l));
			futureList.add(future);
		}
		try {
			List<Future<String>> isDoneFutureList = new ArrayList<Future<String>>();
			while (!futureList.isEmpty()) { // 迭代调用以确保所有的线程都执行完毕
				ensureIsDone(futureList, isDoneFutureList);
			}
			for (Future<String> future : isDoneFutureList) {
				System.out.println(future.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			executorService.shutdown();
		}

		System.out.println("total spend : " + (System.currentTimeMillis() - begin));
	}

	/**
	 * 确保所有的线程都执行完毕
	 * 
	 * @param futureList
	 * @return void
	 */
	private void ensureIsDone(List<Future<String>> futureList, List<Future<String>> isDoneFutureList) {
		if (futureList != null && !futureList.isEmpty()) {
			for (Iterator<Future<String>> iterator = futureList.listIterator(); iterator.hasNext();) {
				Future<String> future = iterator.next();
				if (future.isDone()) {
					isDoneFutureList.add(future);
					iterator.remove();
				}
			}
		}
	}

	/**
	 * 使用Callable来实现线程
	 * 
	 * @author ck
	 *
	 */
	private class TaskThread implements Callable<String> {

		private long processTime;

		public TaskThread(long processTime) {
			this.processTime = processTime;
		}

		@SuppressWarnings("static-access")
		@Override
		public String call() throws Exception {
			Thread.currentThread().sleep(processTime);
			return Thread.currentThread().getName() + ": SUCCESS, spend: " + processTime;
		}

	}

	/**
	 * 使用Runnable来实现线程
	 * 
	 * @author ck
	 *
	 */
	private class TaskThread2 implements Runnable {

		private long processTime;
		private Map<String, String> result;

		public TaskThread2(long processTime, Map<String, String> result) {
			this.processTime = processTime;
			this.result = result;
		}

		@SuppressWarnings("static-access")
		@Override
		public void run() {
			try {
				Thread.currentThread().sleep(processTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			result.put(Thread.currentThread().getName(), ": SUCCESS, spend: " + processTime);
		}

	}
}
