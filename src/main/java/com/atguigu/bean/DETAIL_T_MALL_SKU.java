package com.atguigu.bean;

import java.util.List;
/**
 * 封装商品详情页面的商品spu信息和商品sku信息和商品属性（规格列表list_av_name）信息和商品图片信息list_image
 * @author NeoSoul
 */
public class DETAIL_T_MALL_SKU extends T_MALL_SKU {
	// 查询商品sku信息t_mall_sku
	// 查询商品spu信息t_mall_product
	// 查询商品属性（规格）列表OBJECT_T_MALL_SKU_ATTR_VALUE_NAME t_mall_sku_attr_value,t_mall_attr,t_mall_value
	// 查询商品图片信息t_mall_product_image

	private T_MALL_PRODUCT spu;
	private List<T_MALL_PRODUCT_IMAGE> list_image;
	private List<OBJECT_T_MALL_SKU_ATTR_VALUE_NAME> list_av_name;

	public T_MALL_PRODUCT getSpu() {
		return spu;
	}

	public void setSpu(T_MALL_PRODUCT spu) {
		this.spu = spu;
	}

	public List<T_MALL_PRODUCT_IMAGE> getList_image() {
		return list_image;
	}

	public void setList_image(List<T_MALL_PRODUCT_IMAGE> list_image) {
		this.list_image = list_image;
	}

	public List<OBJECT_T_MALL_SKU_ATTR_VALUE_NAME> getList_av_name() {
		return list_av_name;
	}

	public void setList_av_name(List<OBJECT_T_MALL_SKU_ATTR_VALUE_NAME> list_av_name) {
		this.list_av_name = list_av_name;
	}

}
