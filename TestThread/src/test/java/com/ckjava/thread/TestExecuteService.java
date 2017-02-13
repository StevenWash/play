package com.ckjava.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 *  通过 ExecuteService 来控制执行的线程数，线程池的实现
 *  
 *  通过submit来提交任务，当有空闲的线程时候才真正开始执行任务
 * 
 *  通过将 ExecutorService 对象作为 Controller（单例模式） 中一个属性，确保 ExecutorService 对象也是单例模式
 *  
 */
public class TestExecuteService {
	
	// 假设有10个任务，每个任务花费的时间如下
	public static List<Map<String, Long>> taskList = new ArrayList<>();
	private static long[] spendTime = new long[] { 3000, 3000, 3000, 3000, 3000, 100, 10000, 9000, 1000, 6000 };
	
	// 当前机器线程数
	private static final int pros = Runtime.getRuntime().availableProcessors();
	
	// 线程池大小
	private static final int poolSize = 5;
	public static ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
	
	public static void main(String[] args) {
		
		
		System.out.println("处理器线程数为: " + pros);
		
		for (int i = 0; i < spendTime.length; i++) {
			Map<String, Long> taskMap = new HashMap<String, Long>();
			taskMap.put("task"+i, spendTime[i]);
			taskList.add(taskMap);
		}
		
		for (Map<String, Long> task : taskList) {
			executorService.submit(new TaskThread(task));
		}
		
	}
	
	private static class TaskThread implements Runnable {

		private Map<String, Long> task;

		public TaskThread(Map<String, Long> task) {
			this.task = task;
		}

		@SuppressWarnings("static-access")
		@Override
		public void run() {
			try {
				String taskname = task.entrySet().iterator().next().getKey();
				long processTime = task.entrySet().iterator().next().getValue();
				System.out.println(Thread.currentThread().getName() + ": start execute task " + taskname);
				Thread.currentThread().sleep(processTime);
				System.out.println(Thread.currentThread().getName() + ": end execute task "+taskname+", spend: " + processTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
