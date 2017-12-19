package com.atguigu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.bean.DETAIL_T_MALL_SKU;
import com.atguigu.bean.MODEL_T_MALL_SKU_ATTR_VALUE;
import com.atguigu.bean.OBJECT_T_MALL_ATTR;
import com.atguigu.bean.OBJECT_T_MALL_SKU;
import com.atguigu.bean.OBJECT_T_MALL_SKU_KEYWORDS;
import com.atguigu.bean.T_MALL_SKU;
import com.atguigu.bean.T_MALL_SKU_ATTR_VALUE;
import com.atguigu.service.AttrService;
import com.atguigu.service.SearchService;
import com.atguigu.util.MyCacheUtil;
import com.atguigu.util.MyHttpGetUtil;
import com.atguigu.util.MyJsonUtil;
import com.atguigu.util.MyPropertyUtil;

@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	@Autowired
	private AttrService attrService;

	/**
	 * 关键字查询，从solr中全文检索商品集合
	 * 
	 * @param keywords
	 * @param model
	 * @return
	 */
	@RequestMapping("/keywords_search")
	public String search_keywords(String keywords, Model model) {
		List<OBJECT_T_MALL_SKU_KEYWORDS> list_sku = new ArrayList<>();
		// 远程http调用keywords关键字搜索系统，抓获第三方关键字搜索系统的异常
		String string = MyPropertyUtil.getProperty("keywords.properties", "k_url");
		try {
			String doGet = MyHttpGetUtil.doGet(string+ keywords);
			list_sku = MyJsonUtil.json_to_list(doGet, OBJECT_T_MALL_SKU_KEYWORDS.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("sku_list", list_sku);
		model.addAttribute("keywords", keywords);
		return "sale_search_keywords";
	}

	/*
	 * 去往商品详情页面
	 */
	@RequestMapping("goto_sku_detail")
	public String goto_sku_detail(int spu_id, int sku_id, ModelMap map) {
		// 1，查询商品sku信息t_mall_sku
		// 1，查询商品spu信息t_mall_product
		// 多，查询商品属性（规格）列表用OBJECT_T_MALL_SKU_ATTR_VALUE_NAME封装
		// t_mall_sku_attr_value,t_mall_attr,t_mall_value
		// 多，查询商品图片信息t_mall_product_image
		DETAIL_T_MALL_SKU obj_sku = searchService.get_sku_detail_by_spu_sku(spu_id, sku_id);

		// 查询商品sku列表，即京东购物网站上商品详情页面中的颜色分类，如魅蓝note6手机，颜色分类为颜色，不同配置
		List<T_MALL_SKU> list_sku = searchService.get_sku_list_by_spu(spu_id);
		map.put("list_sku", list_sku);
		map.put("obj_sku", obj_sku);
		return "sale_search_sku_detail";
	}

	// list_av封装了属性id和属性值id所组成的list集合，(改进：redis交叉查询，多个属性id和属性值id的商品skuid集合的交集)
	@RequestMapping("search_sku_by_attr")
	public String search_sku_by_attr(String class_2_id, String order, MODEL_T_MALL_SKU_ATTR_VALUE list_av,
			ModelMap map) {
		List<OBJECT_T_MALL_SKU> sku_list = new ArrayList<>();
		// 交叉查询
		String[] keys = new String[list_av.getList_av().size()];
		List<T_MALL_SKU_ATTR_VALUE> list_attr = list_av.getList_av();
		for (int i = 0; i < list_attr.size(); i++) {
			T_MALL_SKU_ATTR_VALUE av = list_attr.get(i);
			int shxm_id = av.getShxm_id();
			int shxzh_id = av.getShxzh_id();
			String key = "attr_" + class_2_id + "_" + shxm_id + "_" + shxzh_id;
			keys[i] = key;
		}
		// 下面2行代码封装为工具类中的方法
		// Jedis jedis = JedisPoolUtils.getJedis();
		// jedis.zinterstore("dstkey", keys);
		// 交叉查询，这一步是该请求的关键
		String dstkey = MyCacheUtil.key_inter("dstkey", keys);
		// 先从redis缓存中查询
		sku_list = MyCacheUtil.get_redis_key(dstkey, OBJECT_T_MALL_SKU.class);

		// 若redis中查不到，通过mysql
		// 通过属性检索查询sku商品
		if (sku_list == null || sku_list.size() == 0) {
			sku_list = searchService.search_sku_by_attr(class_2_id, order, list_av.getList_av());
		}

		map.put("sku_list", sku_list);
		return "sale_search_sku_list";
	}

	@RequestMapping("goto_search_class")
	public String goto_search_class(String class_2_id, String class_2_name, ModelMap map) {

		// 查询class_2_id对应的商品分类属性集合
		List<OBJECT_T_MALL_ATTR> attr_list = attrService.select_attr(class_2_id);

		// 调用属性检索的业务层
		List<OBJECT_T_MALL_SKU> sku_list = new ArrayList<>();

		String key = "class_2_" + class_2_id;

		sku_list = MyCacheUtil.get_redis_key(key, OBJECT_T_MALL_SKU.class);
		// 查询mysql

		// 查询class_2_id对应的商品集合(先从redis中查询，若查询不到，则从mysql中查询)
		if (sku_list == null || sku_list.size() == 0) {
			// 从mysql查询，并且同步redis
			sku_list = searchService.select_sku_by_class_2(class_2_id);
			MyCacheUtil.refreshRedisKey(key, sku_list);
		}
		map.put("sku_list", sku_list);
		map.put("attr_list", attr_list);
		map.put("class_2_id", class_2_id);
		map.put("class_2_name", class_2_name);
		return "sale_search";
	}
}
