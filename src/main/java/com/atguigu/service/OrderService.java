package com.atguigu.service;

import java.util.List;

import com.atguigu.bean.OBJECT_T_MALL_ORDER;
import com.atguigu.bean.T_MALL_ADDRESS;
import com.atguigu.exception.MyOverSaleException;

public interface OrderService {
	
	List<Integer> save_order(double sum, T_MALL_ADDRESS address, OBJECT_T_MALL_ORDER order);

	void save_order_after_pay(OBJECT_T_MALL_ORDER order) throws MyOverSaleException;

	OBJECT_T_MALL_ORDER select_order_by_id(Integer id);

}
