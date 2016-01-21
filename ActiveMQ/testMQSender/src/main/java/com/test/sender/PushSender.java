package com.test.sender;

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
	
	private static final String QUEUE_NAME = "mobile_push_queue";
	private static final String MESSAGE_TYPE = "mobile_push";
	
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
		connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://192.168.37.12:61616");
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

	public static void sendObjectMessage(Session session, MessageProducer producer) throws Exception {
		for (int i = 1; i <= SEND_NUMBER; i++) {
			ObjectMessage objMsg = session.createObjectMessage();
			// 类型
			objMsg.setStringProperty("type", MESSAGE_TYPE);
			
			PushConfig config = new PushConfig();
			//config.setMobile("18616849667");
			//config.setInstallation_id("");
			config.setUid(661519);
			
			/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date push_time = sdf.parse("2015-12-11 18:00:00");
				config.setPush_time(push_time);
				config.setExpiration_interval(86400L);
			} catch (Exception e) {
				e.printStackTrace();
			}*/
			
			PushMessage msg = new PushMessage();
			msg.setAlert("通过mq发送");
			config.setPushMessage(msg);
			
			objMsg.setObject(config);
			producer.send(objMsg);
		}
	}
	
	public static void sendTextMessage(Session session, MessageProducer producer) throws Exception {
		for (int i = 1; i <= SEND_NUMBER; i++) {
			TextMessage textMsg = session.createTextMessage();
			// 类型
			textMsg.setStringProperty("type", MESSAGE_TYPE);
			textMsg.setIntProperty("uid", 661519);
			textMsg.setIntProperty("badge", 0);
			textMsg.setStringProperty("text", "通过MQ发送");
			producer.send(textMsg);
		}
	}
}
