package com.atguigu.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bean.OBJECT_T_MALL_ATTR;
import com.atguigu.bean.T_MALL_VALUE;
import com.atguigu.mapper.AttrMapper;
import com.atguigu.service.AttrService;

@Service
public class AttrServiceImpl implements AttrService {

	@Autowired
	private AttrMapper attrMapper;

	@Override
	public List<OBJECT_T_MALL_ATTR> select_attr(String id) {
		return attrMapper.select_attr(id);
	}

	@Override
	public void save_attr(String flbh2, List<OBJECT_T_MALL_ATTR> list_attr) {
			for (int i = 0; i < list_attr.size(); i++) {
				//属性名表
				Map<Object, Object> map = new  HashMap<Object, Object>();
				map.put("flbh2", flbh2);
//				map.put("shxm_mch",list_attr.get(i).getShxm_mch());
				map.put("attr", list_attr.get(i));
				attrMapper.insert_attr(map);
				//属性值表
				List<T_MALL_VALUE> list_value = list_attr.get(i).getList_value();
				Map<Object, Object> map2= new HashMap<Object, Object>();
				int id = list_attr.get(i).getId();
				map2.put("shxm_id",id );
				map2.put("list_value", list_value);
				
				attrMapper.insert_values(map2);
				
			}
	}

	@Override
	public int del_value(String value_id) {
		
		return attrMapper.del_value(value_id);
	}

}
