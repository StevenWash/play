package com.toulezu.test.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configurable
@EnableScheduling // 标注启动定时任务
public class SpringSchedule {

	@Scheduled(fixedRate = 1000 * 30) // 30秒执行一次
	public void reportCurrentTime() {
		System.out.println("Scheduling Tasks Examples reportCurrentTime: The time is now " + dateFormat ().format (new Date ()));
	}
	
	@Scheduled(cron="*/10 * * * * ?") // 每10秒执行一次
	public void reportCurrentTimeByCron() {
		System.out.println("Scheduling Tasks Examples reportCurrentTimeByCron: The time is now " + dateFormat ().format (new Date ()));
	}

	private SimpleDateFormat dateFormat() {
		return new SimpleDateFormat("HH:mm:ss");
	}
}

/*cron Expression 表达式。

这一部分是摘抄的：

字段		允许值				允许的特殊字符
秒		0-59				, - * /
分		0-59				, - * /
小时		0-23				, - * /
日期		1-31				, - * / L W C
月份		1-12或者 JAN-DEC		, - * /
星期		1-7 或者 SUN-SAT		, - * / L C #
年（可选）	留空, 1970-2099	, - * /

如上面的表达式所示:

“*”字符被用来指定所有的值。如：”*“在分钟的字段域里表示“每分钟”。

“-”字符被用来指定一个范围。如：“10-12”在小时域意味着“10点、11点、12点”。

“,”字符被用来指定另外的值。如：“MON,WED,FRI”在星期域里表示”星期一、星期三、星期五”.

“?”字符只在日期域和星期域中使用。它被用来指定“非明确的值”。当你需要通过在这两个域中的一个来指定一些东西的时候，它是有用的。看下面的例子你就会明白。

“L”字符指定在月或者星期中的某天（最后一天）。即“Last ”的缩写。但是在星期和月中“Ｌ”表示不同的意思，如：在月子段中“L”指月份的最后一天-1月31日，2月28日，如果在星期字段中则简单的表示为“7”或者“SAT”。如果在星期字段中在某个value值得后面，则表示“某月的最后一个星期value”,如“6L”表示某月的最后一个星期五。

“W”字符只能用在月份字段中，该字段指定了离指定日期最近的那个星期日。

“#”字符只能用在星期字段，该字段指定了第几个星期value在某月中

每一个元素都可以显式地规定一个值（如6），一个区间（如9-12），一个列表（如9，11，13）或一个通配符（如*）。“月份中的日期”和“星期中的日期”这两个元素是互斥的，因此应该通过设置一个问号（？）来表明你不想设置的那个字段。表7.1中显示了一些cron表达式的例子和它们的意义：

表达式

意义
"0 0 12 * * ?"		每天中午12点触发
"0 15 10 ? * *"		每天上午10:15触发
"0 15 10 * * ?"		每天上午10:15触发
"0 15 10 * * ? *"		每天上午10:15触发
"0 15 10 * * ? 2005"		2005年的每天上午10:15 触发
"0 * 14 * * ?"		在每天下午2点到下午2:59期间的每1分钟触发
"0 0/5 14 * * ?"		在每天下午2点到下午2:55期间的每5分钟触发
"0 0/5 14,18 * * ?"		在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
"0 0-5 14 * * ?"		在每天下午2点到下午2:05期间的每1分钟触发
"0 10,44 14 ? 3 WED"		每年三月的星期三的下午2:10和2:44触发
"0 15 10 ? * MON-FRI"		周一至周五的上午10:15触发
"0 15 10 15 * ?"		每月15日上午10:15触发
"0 15 10 L * ?"		每月最后一日的上午10:15触发
"0 15 10 ? * 6L"		每月的最后一个星期五上午10:15触发
"0 15 10 ? * 6L 2002-2005"		2002年至2005年的每月的最后一个星期五上午10:15触发
"0 15 10 ? * 6#3"		每月的第三个星期五上午10:15触发
每天早上6点  0 6 * * * 
**/

//每两个小时  0 */ 2 * * *  

//晚上11点到早上8点之间每两个小时，早上八点  0 23-7/2，8 * * *  

// 每个月的4号和每个礼拜的礼拜一到礼拜三的早上11点  0 11 4 * 1-3  

// 1月1日早上4点  0 4 1 1 

