package com.atguigu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bean.OBJECT_T_MALL_FLOW;
import com.atguigu.bean.OBJECT_T_MALL_ORDER;
import com.atguigu.bean.T_MALL_ADDRESS;
import com.atguigu.bean.T_MALL_ORDER_INFO;
import com.atguigu.bean.T_MALL_SKU;
import com.atguigu.exception.MyOverSaleException;
import com.atguigu.mapper.OrderMapper;
import com.atguigu.service.OrderService;
import com.atguigu.util.MyDataUtil;
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderMapper orderMapper;
	
	@Override
	public List<Integer> save_order(double sum, T_MALL_ADDRESS address, OBJECT_T_MALL_ORDER order) {
		//购物车id集合，作用是将订单中的所有购物车的id添加进来，然后，返回return list_gwc_id;
		//在控制器中同步session，将session中的购物车中删除掉已经购买的购物车
		List<Integer> list_gwc_id = new ArrayList<>();
		//保存订单order，生成订单id，订单号，主键生成策略
		Map<String,Object> map = new HashMap<>();
		order.setJdh(1);//更改订单的进度号为订单已提交
		order.setDzh_mch(address.getYh_dz());//提交订单后，进度号为1时，订单的用户地址。
		map.put("order", order);
		map.put("address", address);//dzh_id,dzh_mch
		orderMapper.insert_order(map);
		//保存订单物流flow，生成物流号id，主键生成策略。循环保存，因为要返回主键id
		List<OBJECT_T_MALL_FLOW> list_flow = order.getList_flow();
		for (int i = 0; i < list_flow.size(); i++) {
			OBJECT_T_MALL_FLOW flow = list_flow.get(i);
			flow.setMdd(address.getYh_dz());
			Map<String,Object> map2 = new HashMap<>();
			map2.put("flow", flow);
			map2.put("order_id", order.getId());//dd_id
			orderMapper.insert_flow(map2);
			
			//保存orderinf，订单信息表批量保存
			List<T_MALL_ORDER_INFO> list_info = flow.getList_info();
			Map<String,Object> map3 = new HashMap<>();
			map3.put("list_info", list_info);
			map3.put("flow_id", flow.getId());//flow_id
			map3.put("order_id", order.getId());
			orderMapper.insert_order_info(map3);
			
			for (int j = 0; j < list_info.size(); j++) {
				//将订单中的购物车id添加到list_gwc_id
				T_MALL_ORDER_INFO info = list_info.get(j);
				
				list_gwc_id.add(info.getGwch_id());
			}
		}
		
		//从db中把购物车表中的购物车数据，和保存的订单数据比较，一样的，删除掉购物车数据，返回删除的购物车id
		orderMapper.delete_shoppingcarlist_by_gwcid(list_gwc_id);
		return list_gwc_id;
	}
	/**
	 * 真实情况时先处理库存，再处理订单，
	 * 这里为了演示事务回滚，先处理了订单，后处理了库存
	 */
	@Override
	public void save_order_after_pay(OBJECT_T_MALL_ORDER order) throws MyOverSaleException {
		//修改订单状态，此处的order对象是为了携带修改的数据，所以该order中的有些数据为空，，但是数据库中的该数据存在，不为空。
		order.setYjsdshj(MyDataUtil.getMyDate(3));
		order.setJdh(2);//订单已支付
		orderMapper.update_order(order);
		List<OBJECT_T_MALL_FLOW> list_flow = order.getList_flow();
		for (int i = 0; i < list_flow.size(); i++) {
			OBJECT_T_MALL_FLOW flow = list_flow.get(i);
			flow.setPsshj(MyDataUtil.getMyDate(1));
			flow.setMdd(order.getDzh_mch());
			flow.setMqdd("商品已出库");
			flow.setPsmsh("商品正在出库");
			flow.setYh_id(order.getYh_id());
			flow.setYwy("佟刚");
			flow.setLxfsh("110110110");
			//修改物流信息
			orderMapper.update_flow(flow);
			
			List<T_MALL_ORDER_INFO> list_info = flow.getList_info();
			for (int j = 0; j < list_info.size(); j++) {
				T_MALL_ORDER_INFO info = list_info.get(j);
				//加事务select .. from .. for udpate，查询库存数量
				int kc = orderMapper.select_sku_kc_by_skuid(info.getSku_id());
				int sku_shl = info.getSku_shl();
				//库存数量是否小于购买数量
				if(kc<sku_shl) {
					// 返回订单失败状态，回滚库存业务的事务
					//超卖
					throw new MyOverSaleException("超卖，over sale");
				}else {
					//加事务update
					Map<String,Integer> map = new HashMap<String,Integer>();
					map.put("sku_shl", sku_shl);
//					map.put("kc", kc);
					map.put("sku_id", info.getSku_id());
					orderMapper.update_kc(map);
				}
			}
		}
		
		
	}

	@Override
	public OBJECT_T_MALL_ORDER select_order_by_id(Integer id) {
		return orderMapper.select_order_by_id(id);
	}

}
