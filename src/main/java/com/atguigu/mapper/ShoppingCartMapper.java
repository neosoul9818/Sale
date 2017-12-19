package com.atguigu.mapper;

import java.util.List;
import java.util.Map;

import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.bean.T_MALL_USER_ACCOUNT;

public interface ShoppingCartMapper {

	int if_cart_exist(Map<Object, Object> map);

	void update_cart(T_MALL_SHOPPINGCAR cart);

	void insert_cart(T_MALL_SHOPPINGCAR cart);

	List<T_MALL_SHOPPINGCAR> select_cart_list_by_userid(T_MALL_USER_ACCOUNT user);

	void delete_cart_by_id(int id);

//	int if_cart_exist(int sku_id, int yh_id);

}
