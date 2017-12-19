package com.atguigu.mapper;

import java.util.List;
import java.util.Map;

import com.atguigu.bean.OBJECT_T_MALL_ATTR;

public interface AttrMapper {

	List<OBJECT_T_MALL_ATTR> select_attr(String id);


	void insert_attr(Map<Object, Object> map);

	void insert_values(Map<Object, Object> map2);


	int del_value(String value_id);

}
