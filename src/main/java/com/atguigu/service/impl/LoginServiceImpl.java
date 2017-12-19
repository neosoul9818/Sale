package com.atguigu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bean.T_MALL_USER_ACCOUNT;
import com.atguigu.mapper.LoginMapper;
import com.atguigu.service.LoginService;
@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private LoginMapper loginMapper;
	
	@Override
	public void regist(T_MALL_USER_ACCOUNT user_account) {
		
		loginMapper.regist(user_account);
	}

	@Override
	public T_MALL_USER_ACCOUNT getUser_Account(T_MALL_USER_ACCOUNT user_account) {
		return loginMapper.getUser_Account(user_account);
	}

}
