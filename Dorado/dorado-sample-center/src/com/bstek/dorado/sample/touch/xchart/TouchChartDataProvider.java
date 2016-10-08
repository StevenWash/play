package com.bstek.dorado.sample.touch.xchart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;

@Component
public class TouchChartDataProvider {

	@DataProvider
	public Collection<Map<String, Object>> getLineData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 8; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("label", (2004 + i) + "年");
			map.put("salesDept1", 120 - (10 * i) + (int) (Math.random() * 100));
			map.put("salesDept2", 120 + (30 * i) + (int) (Math.random() * 80));
			map.put("salesDept3", 60 + (10 * i) + (int) (Math.random() * 60));
			list.add(map);
		}
		return list;
	}

	@DataProvider
	public Collection<Map<String, Object>> getColumnData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 1; i < 5; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("label", "第" + i + "季度");
			map.put("salesDept1", 80 + (20 * i) + (int) (Math.random() * 100));
			map.put("salesDept2", 120 + (30 * i) + (int) (Math.random() * 80));
			map.put("salesDept3", 60 + (20 * i) + (int) (Math.random() * 60));
			list.add(map);
		}
		return list;
	}

	@DataProvider
	public Collection<Map<String, Object>> getBarData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 6; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("label", (2006 + i) + "年");
			map.put("salesDept1", 80 + (20 * i) + (int) (Math.random() * 100));
			map.put("salesDept2", 120 + (30 * i) + (int) (Math.random() * 80));
			map.put("salesDept3", 60 + (20 * i) + (int) (Math.random() * 60));
			list.add(map);
		}
		return list;
	}

	@DataProvider
	public Collection<Map<String, Object>> getPieData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;

		double values[] = new double[] { 19289, 23897, 31852, 58168, 73933 };
		String labels[] = new String[] { "苹果", "香蕉", "草莓", "梨", "橘子" };

		for (int i = 0, j = values.length; i < j; i++) {
			map = new HashMap<String, Object>();
			map.put("value", values[i]);
			map.put("label", labels[i]);
			list.add(map);
		}

		return list;
	}

	@DataProvider
	public Collection<Map<String, Object>> getAreaData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 8; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("label", (2004 + i) + "年");
			map.put("value", 80 + (20 * i) + (int) (Math.random() * 120));
			list.add(map);
		}
		return list;
	}

	@DataProvider
	public Collection<Map<String, Number>> getScatterData1() {
		List<Map<String, Number>> list = new ArrayList<Map<String, Number>>();
		Map<String, Number> map = null;

		double xs[] = new double[] { 9.9, 4.9, 8.8, 3.2, 8.1, 4.8, 5.8, 5.5,
				2.9, 3.8, 8.9, 0.9, 5.3, 1.4, 8.1, 9.8, 8.8, 3.5, 4.9, 6.5,
				5.8, 3 };
		double ys[] = new double[] { 287.1, 49, 140.8, 150.4, 39.6, 172.8,
				278.4, 52.5, 84.1, 18.4, 26.7, 27, 148.4, 22.4, 137.7, 401.8,
				114.4, 28, 117.6, 195, 76.8, 48 };

		for (int i = 0, j = xs.length; i < j; i++) {
			map = new HashMap<String, Number>();
			map.put("x", xs[i]);
			map.put("y", ys[i]);
			list.add(map);
		}

		return list;
	}

	@DataProvider
	public Collection<Map<String, Number>> getScatterData2() {
		List<Map<String, Number>> list = new ArrayList<Map<String, Number>>();
		Map<String, Number> map = null;

		double xs[] = new double[] { 8.9, 5, 2, 8.5, 6.9, 7.3, 7.1, 4.3, 6.4,
				5, 9.9, 3.9, 1.3, 5.9, 5.9, 0.7, 4, 9.8, 8.3 };
		double ys[] = new double[] { 160.2, 24, 94, 399.5, 289.8, 15.6, 333.7,
				98.9, 84.8, 230, 445.5, 70.2, 5.4, 271.4, 177, 15.4, 24, 431.2,
				132.8 };

		for (int i = 0, j = xs.length; i < j; i++) {
			map = new HashMap<String, Number>();
			map.put("x", xs[i]);
			map.put("y", ys[i]);
			list.add(map);
		}

		return list;
	}
}
