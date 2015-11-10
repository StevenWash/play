package getluckynumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Number {

	public static String getNumber(Integer range, Integer count, Boolean haszero) {
		List<Integer> temp = new ArrayList<Integer>();
		StringBuilder str = new StringBuilder();
		Random ran = new Random();
		Integer num = 0;
		while (true) {
			num = ran.nextInt(range);
			if (!haszero && num == 0) {
				continue;
			}
			if (!temp.contains(new Integer(num))) {
				if (temp.size() < count) {
					temp.add(new Integer(num));
				} else {
					break;
				}
			}
		}
		Integer[] intarr= temp.toArray(new Integer[count]);
		Integer t = 0;
		for (int i = 0; i < intarr.length; i++) {
			for (int j = 1 + i; j < intarr.length; j++) {
				if (intarr[i] > intarr[j]) {
					t = intarr[i];
					intarr[i] = intarr[j];
					intarr[j] = t;
				}
			}
		}
		String intstr = null;
		for (Integer integer : intarr) {
			intstr = integer.toString();
			if (intstr.length() == 1) {
				intstr = "0".concat(intstr);
			}
			str.append(intstr).append(" ");
		}
		return str.toString();
	}
	
	public static void main(String[] args) {
		int cou = 1;
		for (int i = 1; i <= cou; i++) {
			System.out.println(i + " : " + getNumber(34, 6, false) + " - " + getNumber(17, 1, false));
		}
	}
	
}