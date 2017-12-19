package com.atguigu.mapper;

import com.atguigu.bean.T_MALL_USER_ACCOUNT;

public interface LoginMapper {

	public void regist(T_MALL_USER_ACCOUNT user_account);

	public T_MALL_USER_ACCOUNT getUser_Account(T_MALL_USER_ACCOUNT user_account);

}
