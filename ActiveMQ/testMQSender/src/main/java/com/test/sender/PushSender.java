package com.test.sender;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 发送者
 *
 */

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.horizon.app.push.bean.PushConfig;
import com.horizon.app.push.bean.PushMessage;

public class PushSender {
	
	/**
	 * mobile-push 的队列名称
	 */
	private static final String QUEUE_NAME = "mobile_push_queue";
	/**
	 * 在bsi中的消息类型
	 */
	private static final String MESSAGE_TYPE = "mobile_push";
	
	/**
	 * MQ 用户名，根据不同的环境配置
	 */
	private static final String USERNAME = "admin";
	
	/**
	 * MQ 密码，根据不同的环境配置
	 */
	private static final String PASSWORD = "admin";
	
	/**
	 * MQ url，根据不同的环境配置
	 */
	private static final String BROKERURL = "tcp://192.168.37.12:61616";
	
	
	private static final int SEND_NUMBER = 1;

	public static void main(String[] args) {
		// ConnectionFactory ：连接工厂，JMS 用它创建连接
		ConnectionFactory connectionFactory;
		// Connection ：JMS 客户端到JMS Provider 的连接
		Connection connection = null;
		// Session： 一个发送或接收消息的线程
		Session session;
		// Destination ：消息的目的地;消息发送给谁.
		Destination destination;
		// MessageProducer：消息发送者
		MessageProducer producer;
		// TextMessage message;
		// 构造ConnectionFactory实例对象，此处采用ActiveMq的实现jar
		connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);
		try {
			// 构造从工厂得到连接对象
			connection = connectionFactory.createConnection();
			// 启动
			connection.start();
			// 获取操作连接
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			// 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
			destination = session.createQueue(QUEUE_NAME);
			// 得到消息生成者【发送者】
			producer = session.createProducer(destination);
			// 设置不持久化，此处学习，实际根据项目决定
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			// 构造消息，此处写死，项目就是参数，或者方法获取
			sendObjectMessage(session, producer);
			//sendTextMessage(session, producer);
			session.commit();
			
			System.out.println("发送完毕！");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != connection)
					connection.close();
			} catch (Throwable ignore) {
			}
		}
	}

	/**
	 * 发送 PushMessage 对象
	 * @param session
	 * @param producer
	 * @throws Exception
	 */
	public static void sendObjectMessage(Session session, MessageProducer producer) throws Exception {
		for (int i = 1; i <= SEND_NUMBER; i++) {
			ObjectMessage objMsg = session.createObjectMessage();
			// 类型
			objMsg.setStringProperty("type", MESSAGE_TYPE);
			
			// push发送配置
			PushConfig config = new PushConfig();
			//config.setMobile(""); // 按手机号
			// config.setUuid(""); // uuid 按设备唯一标示
			config.setUid(662281); // 按uid
			config.setBadge(1); // 显示在设备右上角的通知数
			
			/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				config.setPush_time(sdf.parse("2016-01-27 14:57:00")); // 设置定期推送时间
				//config.setExpiration_interval(86400L); // 消息过期的相对时间，从调用 API 的时间开始算起，单位是「秒」
				config.setExpiration_time(sdf.parse("2016-01-28 14:35:00"));// 消息过期的绝对日期时间
			} catch (Exception e) {
				e.printStackTrace();
			}*/
			
			PushMessage msg = new PushMessage();
			//msg.setTitle("push title");
			msg.setAlert("通过mq发送"); // 如果只是通知消息，设置alert即可
			//msg.setUrl("push url");
			config.setPushMessage(msg);
			
			objMsg.setObject(config);
			producer.send(objMsg);
		}
	}
	
	/**
	 * 直接根据uid进行发送
	 * @param session
	 * @param producer
	 * @throws Exception
	 */
	public static void sendTextMessage(Session session, MessageProducer producer) throws Exception {
		for (int i = 1; i <= SEND_NUMBER; i++) {
			TextMessage textMsg = session.createTextMessage();
			// 类型
			textMsg.setStringProperty("type", MESSAGE_TYPE);
			textMsg.setIntProperty("uid", 661519);
			textMsg.setIntProperty("badge", 1);
			textMsg.setStringProperty("text", "通过MQ发送");
			producer.send(textMsg);
		}
	}
}
