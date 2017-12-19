package com.atguigu.service;

import com.atguigu.bean.T_MALL_USER_ACCOUNT;

public interface LoginService {

	void regist(T_MALL_USER_ACCOUNT user_account);

	T_MALL_USER_ACCOUNT getUser_Account(T_MALL_USER_ACCOUNT user_account);

}
