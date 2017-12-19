package com.atguigu.server;

import java.util.List;

import javax.jws.WebService;

import com.atguigu.bean.T_MALL_ADDRESS;
import com.atguigu.bean.T_MALL_USER_ACCOUNT;
@WebService
public interface AddressServer {

	void insert_address(T_MALL_ADDRESS address);

	List<T_MALL_ADDRESS> select_addresses_by_user_id(T_MALL_USER_ACCOUNT user);

	T_MALL_ADDRESS select_addresses_by_id(int address_id);

}
