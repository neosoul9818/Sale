package com.atguigu.service;

import java.util.List;

import com.atguigu.bean.DETAIL_T_MALL_SKU;
import com.atguigu.bean.OBJECT_T_MALL_SKU;
import com.atguigu.bean.T_MALL_SKU;
import com.atguigu.bean.T_MALL_SKU_ATTR_VALUE;

public interface SearchService {

	List<OBJECT_T_MALL_SKU> select_sku_by_class_2(String class_2_id);

	List<OBJECT_T_MALL_SKU> search_sku_by_attr(String class_2_id,String order, List<T_MALL_SKU_ATTR_VALUE> list_av);

	List<T_MALL_SKU> get_sku_list_by_spu(int spu_id);

	DETAIL_T_MALL_SKU get_sku_detail_by_spu_sku(int spu_id, int sku_id);

}
