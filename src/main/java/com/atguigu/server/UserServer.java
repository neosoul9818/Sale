package com.atguigu.server;

import javax.jws.WebService;

import com.atguigu.bean.T_MALL_USER_ACCOUNT;
@WebService
public interface UserServer {
	
	public T_MALL_USER_ACCOUNT login(T_MALL_USER_ACCOUNT user);

	public String login_REST_FUL(T_MALL_USER_ACCOUNT user);
	
	public T_MALL_USER_ACCOUNT login_test(String dataSource_type,T_MALL_USER_ACCOUNT user);
	
	public T_MALL_USER_ACCOUNT login2(T_MALL_USER_ACCOUNT user);
}
