package com.mg.util;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
/**
 * BeanUtil工具
 * 将Map集合中的元素映射到bean中对应的属性
 * @author JFD
 *
 */
public class CommonUtil {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T toBean( Map map,Class<T> clazz){
		/*
		 * BeanUtils的方法会用到类型转换
		 * converter 只支持一些基本类型转化，遇到像Date这种不认识的类型时会抛出异常
		 * 这个时候就需要给类型注册转换器
		 */
		T bean;
		try {
			bean = clazz.newInstance();
			ConvertUtils.register(new DateConverter(), java.util.Date.class);
			BeanUtils.populate(bean, map);
		}  catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		return bean;
	}
}
