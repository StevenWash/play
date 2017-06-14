package com.ckjava.thread;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestThreadWork {
	
	/**
	 * 在一台双核四线程的机器上设置并行执行线程数为3,程序执行后可以在 Windows 任务管理器中发现 javaw.exe 进程最高占用了 75% 的CPU, 也就是说CPU中有3个线程在执行任务
	 */
	private static final int THREAD_NUMBER = 3;
	
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUMBER);
		
		// 这里虽然添加了4个任务,但是最多3个任务并行执行,还有一个处于等待状态
		List<CalculateWorker> workerList = new ArrayList<>();
		workerList.add(new CalculateWorker());
		workerList.add(new CalculateWorker());
		workerList.add(new CalculateWorker());
		workerList.add(new CalculateWorker());
	
		try {
			List<Future<String>> result = executorService.invokeAll(workerList);
			for (Future<String> future : result) {
				future.get();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	private static class CalculateWorker implements Callable<String> {

		@Override
		public String call() throws Exception {
			BigDecimal a = new BigDecimal("200000000.5");
			BigDecimal b = new BigDecimal("10000000000.25");
			for (int i = 0; i < 1000000000; i++) {
				a.multiply(b);
			}
			for (int i = 0; i < 1000000000; i++) {
				a.multiply(b);
			}
			for (int i = 0; i < 1000000000; i++) {
				a.multiply(b);
			}
			for (int i = 0; i < 1000000000; i++) {
				a.multiply(b);
			}
			for (int i = 0; i < 1000000000; i++) {
				a.multiply(b);
			}
			for (int i = 0; i < 1000000000; i++) {
				a.multiply(b);
			}
			for (int i = 0; i < 1000000000; i++) {
				a.multiply(b);
			}
			for (int i = 0; i < 1000000000; i++) {
				a.multiply(b);
			}
			System.out.println("done");
			return null;
		}
		
	}
}
