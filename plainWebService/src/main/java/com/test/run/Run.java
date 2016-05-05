package com.test.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.test.run.Run;

@Configuration
@ComponentScan(basePackages = {"com.test"})
@EnableAutoConfiguration // ��֪BootҪ����һ���ض��ķ�ʽ����Ӧ�ý������á����ַ����Ὣ��������ʽ�����þ�����Ϊ���Ĭ�ϵ�Լ��������ܹ��۽�����ξ����ʹӦ��׼�������Ա�����������
public class Run {
	public static void main(String[] args) {
		SpringApplication.run(Run.class, args);
	}
}