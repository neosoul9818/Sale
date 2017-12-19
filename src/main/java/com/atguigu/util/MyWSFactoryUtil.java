package com.atguigu.util;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.atguigu.bean.T_MALL_USER_ACCOUNT;
import com.atguigu.server.UserServer;

public class MyWSFactoryUtil {
	
	public static  <T> T getWs(String url, Class<T> t) {
		JaxWsProxyFactoryBean jaxws = new JaxWsProxyFactoryBean();
		jaxws.setAddress(url);
		jaxws.setServiceClass(t);
		
		T create = (T) jaxws.create();
		/*T_MALL_USER_ACCOUNT user = new T_MALL_USER_ACCOUNT();
		user.setYh_mch("lilei");
		user.setYh_mm("123");*/
		/*T_MALL_USER_ACCOUNT login = create.login(user);*/
		return create;
	}
}
