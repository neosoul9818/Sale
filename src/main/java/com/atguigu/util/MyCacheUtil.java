package com.atguigu.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.atguigu.bean.OBJECT_T_MALL_SKU;

import redis.clients.jedis.Jedis;

/**
 * redis缓存工具类
 * 
 * @author NeoSoul
 *
 */
public class MyCacheUtil {
	/**
	 * 刷新redis中的指定key对应的value
	 * 
	 * @param key
	 * @param list
	 */
	public static <T> int refreshRedisKey(String key, List<T> list) {
		Jedis jedis=null;
		try {
			jedis = JedisPoolUtils.getJedis();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		jedis.del(key);

		for (int i = 0; i < list.size(); i++) {
			jedis.zadd(key, i, MyJsonUtil.object_to_json(list.get(i)));
		}
		return list.size();
	}
	/**
	 * 从redis缓存中得到指定key的value
	 * @param key
	 * @param t
	 * @return
	 */
	public  static <T> List<T>  get_redis_key(String key, Class<T> t) {
		List<T> list = new ArrayList<>();

		Jedis jedis=null;
		try {
			jedis = JedisPoolUtils.getJedis();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		Set<String> zrange = jedis.zrange(key, 0, -1);
		//扩展1
//		jedis.zrevrangeByScore(key, 100, 0);
		//扩展2
//		Set<String> zrange = jedis.zrangeByScore(key, 0, 100);
		
		Iterator<String> iterator = zrange.iterator();
		while (iterator.hasNext()) {
			String next = iterator.next();
			list.add(MyJsonUtil.json_to_object(next, t));
		}
		return list;
	}
	/**
	 * 交叉查询
	 * @param dstkey
	 * @param keys
	 */
	public static String key_inter(String dstkey, String[] keys) {
		Jedis jedis=null;
		try {
			jedis = JedisPoolUtils.getJedis();
			
		 Boolean exists = jedis.exists(dstkey);
		 /**
		  * 生成新的交叉查询dstkey（原因：当高并发访问时，当多个用户同时通过属性查询商品集合时，
		  * 各自选择不同属性名id和属性值id，若dstkey一样的话，检索的结果会被覆盖。所以）
		  */
		 for (int i = 0; i < keys.length; i++) {
			dstkey = dstkey +"_"+keys[i];
		}
		 
		 if(!exists) {
			 jedis.zinterstore(dstkey, keys);
		 }
		 
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return dstkey;
	}

}
