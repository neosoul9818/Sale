package com.atguigu.mapper;

import java.util.List;
import java.util.Map;

import com.atguigu.bean.OBJECT_T_MALL_FLOW;
import com.atguigu.bean.OBJECT_T_MALL_ORDER;

public interface OrderMapper {
	/**
	 * // 保存订单表，返回主键
		// shhr
		// zje
		// jdh
		// yh_id
		// dzh_id
		// dzh_mch
	 * @param map
	 */
	void insert_order(Map<String, Object> map);
	/**
	 * // 保存物流表 ,返回主键
			// psfsh
			// yh_id
			// dd_id
			// mqdd
			// mdd
	 * @param map2
	 */
	void insert_flow(Map<String, Object> map2);
	/**
	 * list_info,dd_id,flow_id
	 * @param map3
	 */
	void insert_order_info(Map<String, Object> map3);
	
	void delete_shoppingcarlist_by_gwcid(List<Integer> list_gwc_id);
	/**
	 * 更新进度号和yjsdshj
	 * @param order
	 */
	void update_order(OBJECT_T_MALL_ORDER order);
	/**
	 * 数据库生成物流flow，没有psshj配送时间，psmsh配送描述，yhid，mdd，ywy业务员，lxfsh业务员联系方式
	 * @param flow
	 */
	void update_flow(OBJECT_T_MALL_FLOW flow);
	/**
	 * select for update
	 * @param sku_id
	 * @return
	 */
	int select_sku_kc_by_skuid(int sku_id);
	/**
	 * kc = kc - sku_shl,
	 * sku_xl = sku_xl -sku_shl,
	 * 减库存
	 * @param sku_shl
	 * @param sku_id
	 */
	void update_kc(Map<String, Integer> map);
	
	OBJECT_T_MALL_ORDER select_order_by_id(Integer id);

}
