package com.atguigu.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 * URLDecoder和URLEncoder它的作用主要是用于普通字符串和application/x-www-form-rulencoded MIME字符串之间的转换，
当URL地址里包含非西欧字符的字符串时,系统会将这些非西欧转换成如图所示的特殊字符串
,那么编码过程中可能涉及将普通字符串和这种特殊字符串的相关转换,这就是需要使用
URLDecoder和URLEncoder类URLDecoder类包含一个decode(String s,String charcter)静态方法,它可以将看上
去乱码的特殊字符串转换成普通字符串 URLEncoder类包含一个encode(String s,String charcter)静态方法,它可以将普通
字符串转换成application/x-www-form-urlencoded MIME字符串。
 * @author NeoSoul
 */
public class MyJsonUtil {

	public static void main(String[] args) throws UnsupportedEncodingException {
		List<T_MALL_SHOPPINGCAR> list_car = new ArrayList<T_MALL_SHOPPINGCAR>();

		for (int i = 0; i < 3; i++) {
			T_MALL_SHOPPINGCAR t_MALL_SHOPPINGCAR = new T_MALL_SHOPPINGCAR();
			t_MALL_SHOPPINGCAR.setSku_mch("商品" + i);
			t_MALL_SHOPPINGCAR.setSku_jg(i * 1000);
			list_car.add(t_MALL_SHOPPINGCAR);
		}
		
		list_to_json(list_car);
	}
	/**
	 * 将对象，变成普通字符串，再编码变成json字符串
	 * @param t
	 * @return
	 */
	public static <T> String object_to_json(T t) {

		String json = JSON.toJSONString(t);
		try {
			json = URLEncoder.encode(json, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return json;
	}
	/**
	 * 将json解码变成普通字符串，再变成对象
	 * @param json
	 * @param t
	 * @return
	 */
	public static <T> T json_to_object(String json, Class<T> t) {
		try {
			json = URLDecoder.decode(json, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		T fromJson = JSON.parseObject(json, t);

		return fromJson;
	}
	/**
	 * 将json字符串解码变成普通字符串，再变成list集合
	 * @param json
	 * @param t
	 * @return
	 */
	public static <T> List<T> json_to_list(String json, Class<T> t) {
		try {
			json = URLDecoder.decode(json, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<T> parseArray = JSON.parseArray(json, t);
		return parseArray;
	}
	/**
	 *将list变成普通字符串，再编码变成json字符串 
	 * @param list
	 * @return
	 */
	public static <T> String list_to_json(List<T> list) {

		String json = JSON.toJSONString(list);
		System.out.println(json);
		try {
			json = URLEncoder.encode(json, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(json);
		return json;
	}

}
