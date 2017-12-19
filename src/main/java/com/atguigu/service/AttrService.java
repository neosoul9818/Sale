package com.atguigu.service;

import java.util.List;

import com.atguigu.bean.OBJECT_T_MALL_ATTR;
import com.atguigu.bean.T_MALL_VALUE;

public interface AttrService {

	List<OBJECT_T_MALL_ATTR> select_attr(String id);

	void save_attr(String flbh2, List<OBJECT_T_MALL_ATTR> list_attr);

	int del_value(String value_id);

}
