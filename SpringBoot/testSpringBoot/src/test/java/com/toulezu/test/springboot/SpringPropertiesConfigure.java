package com.toulezu.test.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * 通过该类可以代替在xml中如下的配置
 * 
 * 
 *	
  	  <bean class="org.springframework.context.support.PropertySourcesPlaceholderConfigurer">
        <property name="ignoreUnresolvablePlaceholders" value="true"/>
        <property name="locations">
          <list>
            <value>classpath:jdbc.properties</value>
          </list>
        </property>
      </bean>
 * @author chen_k
 *
 */
@Configuration
@PropertySource(value = { "classpath:jdbc.properties" })
public class SpringPropertiesConfigure {

	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
