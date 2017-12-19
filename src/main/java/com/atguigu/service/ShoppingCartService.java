package com.atguigu.service;

import java.util.List;

import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.bean.T_MALL_USER_ACCOUNT;

public interface ShoppingCartService {
	/**
	 * 根据用户id和sku的id来确定cart购物车是否存在
	 * @param sku_id
	 * @param yh_id
	 * @return 返回1则存在，返回0则不存在
	 */
	int if_cart_exist(int sku_id, int yh_id);
	/**
	 * 更新yh_id的指定的cart的数量
	 * @param cart
	 */
	void updateCart(T_MALL_SHOPPINGCAR cart);
	/**
	 * 保存指定用户yh_id的cart的数量
	 * @param cart
	 */
	void saveCart(T_MALL_SHOPPINGCAR cart);
	/**
	 * 通过用户的yh_id得到该用户的购物车集合
	 * @param user
	 * @return
	 */
	List<T_MALL_SHOPPINGCAR> get_cart_list_by_userid(T_MALL_USER_ACCOUNT user);
	void delete_cart_by_id(int id);

}
