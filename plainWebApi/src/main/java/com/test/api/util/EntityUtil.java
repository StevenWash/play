package com.test.api.util;

import java.beans.PropertyDescriptor;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

public class EntityUtil {

	/**
	 * 将实体类中的属性值放入Map中
	 * 
	 * @param bean
	 * @return
	 */
	public static void add2Map(Map<String, Object> reMap, Object bean) throws Exception {
		if (bean == null || reMap == null) {
			return;
		}
		PropertyDescriptor[] propDescs = PropertyUtils.getPropertyDescriptors(bean);
		for (PropertyDescriptor propDesc : propDescs) {
			String propertyName = propDesc.getName();
			if ("class".equals(propertyName)) {
				continue;
			}
			reMap.put(propertyName, PropertyUtils.getProperty(bean, propertyName));
		}
	}
	
}
