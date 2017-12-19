package com.atguigu.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bean.DETAIL_T_MALL_SKU;
import com.atguigu.bean.MODEL_T_MALL_SKU_ATTR_VALUE;
import com.atguigu.bean.OBJECT_T_MALL_SKU;
import com.atguigu.bean.T_MALL_SKU;
import com.atguigu.bean.T_MALL_SKU_ATTR_VALUE;
import com.atguigu.mapper.SearchMapper;
import com.atguigu.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	private SearchMapper searchMapper;

	@Override
	public List<OBJECT_T_MALL_SKU> select_sku_by_class_2(String class_2_id) {
		return searchMapper.select_sku_by_class_2(class_2_id);
	}

	@Override
	public List<OBJECT_T_MALL_SKU> search_sku_by_attr(String class_2_id,String order, List<T_MALL_SKU_ATTR_VALUE> list_av) {
		StringBuffer sql = new StringBuffer();
		/**
		 * And sku.id in (select sku0.sku_id from (select sku_id from
		 * t_mall_sku_attr_value where shxm_id=1 and shxzh_id=2) Sku0,x (select sku_id
		 * from t_mall_sku_attr_value where shxm_id=1 and shxzh_id=2) Sku1, (select
		 * sku_id from t_mall_sku_attr_value where shxm_id=1 and shxzh_id=2) Sku2 Where
		 * sku0.sku_id = sku1.sku_id and sku1.sku_id = sku2.sku_id)
		 */
		if (list_av!=null && list_av.size() != 0) {
			sql.append("And sku.id in (select sku0.sku_id from ");
			for (int i = 0; i < list_av.size(); i++) {
				sql.append(" (select sku_id from t_mall_sku_attr_value where shxm_id=" + list_av.get(i).getShxm_id()
						+ " and shxzh_id=" + list_av.get(i).getShxzh_id() + ") Sku" + i + " ");
				// 当i不是最后一个t_mall_sku_attr_value表时
				if (i < (list_av.size() - 1)) {
					sql.append(" , ");
				}
				// 如果是则不加逗号，且跳出循环
			}
			//当有多个t_mall_sku_attr_value表，这几个同名的表进行条件关联
			if (list_av.size() > 1) {
				sql.append(" where ");
				
				for (int i = 0; i < list_av.size(); i++) {
					
					if (i < (list_av.size() - 1)) {
						sql.append(" sku" + i + ".sku_id = sku" + (i + 1) + ".sku_id ");
						//当多个t_mall_sku_attr_value表，且个数大于2才有可能有多个关联条件。
						if (list_av.size() > 2 && i < (list_av.size() - 2)) {
							sql.append(" and ");
						}
					}
				}
			}
			
			sql.append(" )");
		}
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("sql", sql.toString());
		map.put("class_2_id",class_2_id);
		map.put("order", order);
		return searchMapper.select_sku_by_attr(map);
	}

	@Override
	public List<T_MALL_SKU> get_sku_list_by_spu(int spu_id) {
		
		return searchMapper.select_sku_list_by_spu(spu_id);
	}

	@Override
	public DETAIL_T_MALL_SKU get_sku_detail_by_spu_sku(int spu_id, int sku_id) {
		HashMap<Object,Object> map = new HashMap<Object, Object>();
		map.put("spu_id", spu_id);
		map.put("sku_id",sku_id);
		return searchMapper.select_sku_detail_by_spu_sku(map);
	}

}
