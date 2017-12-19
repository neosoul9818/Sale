package com.atguigu.util;

import java.util.HashMap;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.beans.factory.FactoryBean;
/**
 * 得到服务
 * 该类会注入到spring的ioc容器中，
 * 好处：是可以直接通过autowire注解绑定到t
 * t是服务接口
 * @author NeoSoul
 *
 * @param <T>
 */
public class MyWSFactoryBean<T> implements FactoryBean<T> {

	private Class<T> t;
	private String url;

	public Class<T> getT() {
		return t;
	}

	public void setT(Class<T> t) {
		this.t = t;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public T getObject() throws Exception {
		JaxWsProxyFactoryBean jaxws = new JaxWsProxyFactoryBean();
		jaxws.setAddress(url);
		jaxws.setServiceClass(t);
		
		//可以选择性的加入拦截,也可以去掉这里的if判断，去掉的话，
		//t指定的所有的服务接口都需要安全拦截，不去掉的话，只有UserServer服务接口需要安全拦截
		if(t.getSimpleName().equals("UserServer")) {
			
			//加入客户端安全拦截代理
			//ws安全机制，token安全令牌，必须要在创建ws之前。
			HashMap<String,Object> map = new HashMap<String, Object>();
			map.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
			map.put(WSHandlerConstants.PASSWORD_TYPE,"PasswordText");
			//MyPasswordCallBack.class.getName(),是得到该类的全类名
			map.put(WSHandlerConstants.PW_CALLBACK_CLASS,MyPasswordCallBack.class.getName());
			
			//注意：此处要额外配置用户，服务器端不用配置该用户
			map.put(WSHandlerConstants.USER, "username");
			WSS4JOutInterceptor outInterceptor = new WSS4JOutInterceptor(map);
			jaxws.getOutInterceptors().add(outInterceptor);
		}

		T create = (T) jaxws.create();
		return create;
	}

	@Override
	public Class<?> getObjectType() {
		return this.t;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
