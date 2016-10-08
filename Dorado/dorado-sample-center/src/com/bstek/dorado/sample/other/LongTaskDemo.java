package com.bstek.dorado.sample.other;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.annotation.TaskScheduler;
import com.bstek.dorado.data.provider.Page;
import com.bstek.dorado.sample.dao.OrderDao;
import com.bstek.dorado.sample.entity.Order;
import com.bstek.dorado.view.task.LongTask;
import com.bstek.dorado.view.task.TaskLog;
import com.bstek.dorado.view.task.TaskState;
import com.bstek.dorado.view.task.TaskStateInfo;

@Component
public class LongTaskDemo {
	@Resource
	private OrderDao orderDao;

	@Expose
	@TaskScheduler(maxRunning = 1)
	public LongTask cloneProject() {

		return new LongTask() {
			final int TOTAL_FILE_NUM = 80;
			private int copyed;

			public Object call() throws Exception {
				for (copyed = 0; copyed < TOTAL_FILE_NUM; copyed++) {
					synchronized (this) {
						if (isSuspendRequired()) {
							Map<String, Integer> data = new HashMap<String, Integer>();
							data.put("total", TOTAL_FILE_NUM);
							data.put("completed", copyed);
							setStateInfo(new TaskStateInfo(TaskState.suspended,
									"已复制" + copyed + "个文件", data));

							wait();
						}
						if (isAbortRequired()) {
							setStateInfo(new TaskStateInfo(TaskState.aborted));
							break;
						}

						Map<String, Integer> data = new HashMap<String, Integer>();
						data.put("total", TOTAL_FILE_NUM);
						data.put("completed", copyed);
						setStateInfo(new TaskStateInfo(TaskState.running, "已复制"
								+ copyed + "个文件", data));
					}

					Thread.sleep((long) (Math.random() * 200));
				}
				return null;
			}

			protected void doResume() {
				notify();
				setStateInfo(new TaskStateInfo(TaskState.running));
			}

			protected void doAbort() {
				notify();
			}
		};
	}

	@Expose
	@TaskScheduler(maxRunning = 1)
	public LongTask checkOrders() {
		Page<Order> page = new Page<Order>(100, 1);
		orderDao.getAll(page);
		final Collection<Order> orders = page.getEntities();

		return new LongTask() {
			public Object call() throws Exception {
				setStateInfo(new TaskStateInfo(TaskState.running, "正在搜索订单..."));

				int orderNum = orders.size();
				for (int i = 0; i < orderNum; i++) {
					appendLog(new TaskLog("已找到" + i + "笔订单"));
					Thread.sleep((long) (Math.random() * 100));
				}

				setStateInfo(new TaskStateInfo(TaskState.running, "开始核算订单..."));
				int i = 0;
				for (Order order : orders) {
					i++;
					appendLog(new TaskLog("核算订单\"" + order.getId() + "\"..."));
					Thread.sleep((long) (Math.random() * 200));

					setStateInfo(new TaskStateInfo(TaskState.running, "已核算" + i
							+ "笔订单，共计" + orderNum + "笔..."));
				}

				setStateInfo(new TaskStateInfo(TaskState.running, "汇总核算结果..."));
				Thread.sleep(800 + (long) (Math.random() * 1000));
				appendLog(new TaskLog("共核算\"" + orderNum + "\"笔订单。"));
				return orderNum;
			}
		};
	}

}
