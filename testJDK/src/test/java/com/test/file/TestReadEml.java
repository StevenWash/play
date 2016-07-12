package com.test.file;

import java.io.InputStream;

import javax.mail.BodyPart;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class TestReadEml {
	public static void main(String args[]) throws Exception {
		display();
	}

	public static void display() throws Exception {
		Session mailSession = Session.getDefaultInstance(System.getProperties(), null);
		InputStream source = TestReadEml.class.getClassLoader().getResourceAsStream("test.eml");
		MimeMessage message = new MimeMessage(mailSession, source);

		System.out.println("Subject : " + message.getSubject());
		System.out.println("From : " + message.getFrom());
		System.out.println("--------------");
		System.out.println("Body : " + message.getContent());
		System.out.println("ContentType : " + message.getContentType());
		if (message.getContentType().equals("text/html")) {
			String content = (String) message.getContent();
			System.out.println(content);
		} else if (message.getContentType().equals("multipart/related")) {
			MimeMultipart part = (MimeMultipart) message.getContent();
			System.out.println(part);
		}
		MimeMultipart part = (MimeMultipart) message.getContent();
		System.out.println(part.getCount());
		System.out.println("邮件内容如下--------------------------------------------");
		for (int i = 0; i < part.getCount(); i ++) {
			BodyPart bodyPart = part.getBodyPart(i);
			
			String content = MailHelper.getContent(bodyPart);
			System.out.println(content);
			//byte[] data = Base64.decodeBase64(part.getBodyPart(i).getContent().toString().getBytes());
			//String str = new String(data, "ISO8859-1");
			//System.out.println(str);
		}
	}
}
