package com.atguigu.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.bean.T_MALL_USER_ACCOUNT;
import com.atguigu.mapper.ShoppingCartMapper;
import com.atguigu.service.ShoppingCartService;
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
	
	@Autowired
	private ShoppingCartMapper shoppingCarMapper;
	
	@Override
	public int if_cart_exist(int sku_id, int yh_id) {
		HashMap<Object,Object> map = new  HashMap<Object, Object>();
		map.put("sku_id", sku_id);
		map.put("yh_id",yh_id );
		return shoppingCarMapper.if_cart_exist(map);
	}

	@Override
	public void updateCart(T_MALL_SHOPPINGCAR cart) {
		
		shoppingCarMapper.update_cart(cart);
		
	}

	@Override
	public void saveCart(T_MALL_SHOPPINGCAR cart) {
		shoppingCarMapper.insert_cart(cart);
	}

	@Override
	public List<T_MALL_SHOPPINGCAR> get_cart_list_by_userid(T_MALL_USER_ACCOUNT user) {

		return shoppingCarMapper.select_cart_list_by_userid(user);
	}

	@Override
	public void delete_cart_by_id(int id) {
		shoppingCarMapper.delete_cart_by_id(id);
	}

}
