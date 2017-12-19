package com.atguigu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.bean.T_MALL_USER_ACCOUNT;
import com.atguigu.service.ShoppingCartService;
import com.atguigu.util.MyJsonUtil;
import com.atguigu.util.NumberUtil;

@Controller
public class ShoppingCarController {
	@Autowired
	private ShoppingCartService shoppingCarService;

	/**
	 * 当从cookie中删除，则通过cart的sku_id删除，因为cookie中的cart没有用户id和cart的id
	 * 当从db中删除，则通过cart的id删除
	 * 
	 * @param cart
	 * @param list_cart_cookie
	 * @param session
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("delete_cart")
	public String delete_cart(T_MALL_SHOPPINGCAR cart,
			@CookieValue(value = "list_cart_cookie", required = false) String list_cart_cookie, HttpSession session,
			HttpServletResponse response, Model model) {
		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<>();
		// 删除指定购物车
		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
		if (user == null) {// 删除cookie中的
			list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
			if (list_cart != null && list_cart.size() > 0) {
				for (int i = 0; i < list_cart.size(); i++) {
					if (list_cart.get(i).getSku_id() == cart.getSku_id()) {
						list_cart.remove(i);
					}
				}
			}
			//覆盖cookie
			Cookie cookie = new Cookie("list_cart_cookie", MyJsonUtil.list_to_json(list_cart));
			cookie.setMaxAge(60 * 60 * 24);
			response.addCookie(cookie);

		} else {// 删除数据库中的，同步session,此处的list_car一定不为空，一定有数据
			list_cart = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");
			for (int i = 0; i < list_cart.size(); i++) {
				if(list_cart.get(i).getId()==cart.getId()) {
					shoppingCarService.delete_cart_by_id(list_cart.get(i).getId());
					//同步session
					list_cart = shoppingCarService.get_cart_list_by_userid(user);
					session.setAttribute("list_cart_session", list_cart);
				}
			}
		}

		
		model.addAttribute("list_cart", list_cart);
		model.addAttribute("size", get_size(list_cart));
		model.addAttribute("sum", get_sum(list_cart));
		return "sale_cart_list_inner";
	}

	/**
	 * /**
	 * 1 sku_id 2数量 3价格 4选中状态 5改变类型（数量，选中）
	 * 
	 * @param change_type
	 * @param cart
	 * @param model
	 * @param response
	 * @param session
	 * @param list_cart_cookie
	 * @return
	 */
	@RequestMapping("change_item")
	public String change_item(String change_type, T_MALL_SHOPPINGCAR cart, Model model, HttpServletResponse response,
			HttpSession session, @CookieValue(value = "list_cart_cookie", required = false) String list_cart_cookie) {
		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<>();
		if (user == null) {
			list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
		} else {
			list_cart = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");
		}
		for (int i = 0; i < list_cart.size(); i++) {

			if (list_cart.get(i).getSku_id() == cart.getSku_id()) {
				if (change_type.endsWith("uncheck")) {
					// 更改选中状态
					list_cart.get(i).setShfxz(cart.getShfxz());
				} else {
					// 更数量
					list_cart.get(i).setTjshl(cart.getTjshl());
					list_cart.get(i).setShfxz(cart.getShfxz());
					double multiply = NumberUtil.doubleMultiply(cart.getTjshl(), cart.getSku_jg());
					list_cart.get(i).setHj(multiply);
				}

				if (user == null) {
					Cookie cookie = new Cookie("list_cart_cookie", MyJsonUtil.list_to_json(list_cart));
					cookie.setMaxAge(60 * 60 * 24);
					response.addCookie(cookie);
				} else {
					shoppingCarService.updateCart(list_cart.get(i));
				}

			}
		}

		model.addAttribute("list_cart", list_cart);
		model.addAttribute("size", get_size(list_cart));
		model.addAttribute("sum", get_sum(list_cart));
		return "sale_cart_list_inner";
	}

	/**
	 * 求购物车的总金额（必须是选中的商品）
	 * 
	 * @param list_cart
	 * @return
	 */
	private double get_sum(List<T_MALL_SHOPPINGCAR> list_cart) {
		double sum = 0;
		for (int i = 0; i < list_cart.size(); i++) {
			if (list_cart.get(i).getShfxz().equals("1")) {
				sum += NumberUtil.doubleAdd(
						NumberUtil.doubleMultiply(list_cart.get(i).getSku_jg(), list_cart.get(i).getTjshl()));
			}
		}

		return sum;
	}

	/**
	 * 求购物车的总商品个数（必须是选中的商品）
	 * 
	 * @param list_cart
	 * @return
	 */
	private int get_size(List<T_MALL_SHOPPINGCAR> list_cart) {
		int size = 0;
		for (int i = 0; i < list_cart.size(); i++) {
			System.out.println(list_cart.get(i).getShfxz());
			if (list_cart.get(i).getShfxz().equals("1")) {
				size += list_cart.get(i).getTjshl();
			}
		}

		return size;
	}

	@RequestMapping("goto_cart_list")
	public String goto_cart_list(HttpServletResponse response, HttpSession session,
			@CookieValue(value = "list_cart_cookie", required = false) String list_cart_cookie, Model model) {
		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<>();
		if (user == null) {// 未登录，查询cookie中的数据
			list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
			// model.addAttribute("list_cart", list_cart);
		} else {// 登录，查询数据库中的数据
			// list_cart = shoppingCarService.get_cart_list_by_userid(user);
			list_cart = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");
			// model.addAttribute("list_cart", list_cart);
		}
		// 将购物车数据存入域中

		if (list_cart != null) {
			model.addAttribute("list_cart", list_cart);
			model.addAttribute("size", get_size(list_cart));
			model.addAttribute("sum", get_sum(list_cart));
		}else {
			
			System.out.println("购物车为空");
		}

		return "sale_cart_list";
	}

	@RequestMapping("show_mini_cart")
	public String mini_cart_list(HttpSession session,
			@CookieValue(value = "list_cart_cookie", required = false) String list_cart_cookie, Model model) {
		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<>();
		if (user == null) {// 未登录，查询cookie中的数据
			list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
			// model.addAttribute("list_cart", list_cart);
		} else {// 登录，查询数据库中的数据
			// list_cart = shoppingCarService.get_cart_list_by_userid(user);
			list_cart = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");
			// model.addAttribute("list_cart", list_cart);
		}
		// 将购物车数据存入域中
		model.addAttribute("list_cart", list_cart);
		if (list_cart != null) {

			for (int i = 0; i < list_cart.size(); i++) {
				double sum = NumberUtil.doubleAdd(list_cart.get(i).getHj());
				model.addAttribute("size", list_cart.size());
				model.addAttribute("sum", sum);
			}
		}
		return "sale_cart_mini_list";
	}

	@RequestMapping("add_cart")
	public String add_cart(HttpServletResponse response, HttpSession session,
			@CookieValue(value = "list_cart_cookie", required = false) String list_cart_cookie, T_MALL_SHOPPINGCAR cart,
			Model model) {

		List<T_MALL_SHOPPINGCAR> list_car = new ArrayList<T_MALL_SHOPPINGCAR>();//
		// 判断用户是否存在添加购物车判断用户登录状态
		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");

		if (user == null) {// 用户没有登陆，操作cookie

			if (StringUtils.isBlank(list_cart_cookie)) {// cookie中没有list_cart_cookie，购物车不存在
				// 添加一个新的购物车
				list_car.add(cart);
			} else {// 如果cookie存在购物车list_cart_cookie，list_car代表的是
				list_car = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
				boolean b = is_new_car(list_car, cart);// 判断要添加的cart是否已经在cookie中的购物车集合中
				if (b) {// 是新的购物车，添加insert
					list_car.add(cart);
				} else {// 是已经存在，更新update
					updateCart(cart, list_car);
				}
			}
			// 不管之前cookie中有没有list_cart_cookie，都new一个，没有，则添加，有，则覆盖掉原来的cookie
			Cookie cookie = new Cookie("list_cart_cookie", MyJsonUtil.list_to_json(list_car));
			cookie.setMaxAge(60 * 60 * 24);// cookie要设置过期时间
			response.addCookie(cookie);
		} else {// 用户已经登陆,操作db数据库//用户登录，则cart中封装了user_id
			// 更新session（此时session中的数据是来自数据库中的数据的备份，所以和数据库一致）
			list_car = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");

			// 判断session中是否存在该list_cart_session
			if (list_car == null || list_car.size() == 0) {// session中不存在list_cart_session

				shoppingCarService.saveCart(cart);

				list_car = shoppingCarService.get_cart_list_by_userid(user);
				session.setAttribute("list_cart_session", list_car);
			} else {// session中存在list_cart_session
				list_car.add(cart);
				// 操作db
				// 判断数据库中是否存在该购物车
				int b = shoppingCarService.if_cart_exist(cart.getSku_id(), user.getId());
				if (b == 1) {// 存在
					// 存在,update
					// 更新db
					// 同步session
					updateCart(cart, list_car);
				} else {// 不存在
						// 不存在，insert
					shoppingCarService.saveCart(cart);
				}
			}
		}
		return "redirect:/goto_cart_success.do";
	}

	/**
	 * 购物车cart的添加数量+1，合计， 判断如果当期购物车中有用户id则，db更新指定购物车数据
	 * 
	 * @param cart
	 * @param list_car
	 */
	private void updateCart(T_MALL_SHOPPINGCAR cart, List<T_MALL_SHOPPINGCAR> list_car) {

		for (int i = 0; i < list_car.size(); i++) {
			if (list_car.get(i).getSku_id() == cart.getSku_id()) {// 存在返回，false
				list_car.get(i).setTjshl(list_car.get(i).getTjshl() + 1);// 商品合计
				// list_car.get(i).setHj(list_car.get(i).getTjshl() *
				// list_car.get(i).getSku_jg());
				double multiply = NumberUtil.doubleMultiply(list_car.get(i).getTjshl(), list_car.get(i).getSku_jg());
				list_car.get(i).setHj(multiply);
				if (cart.getYh_id() != 0) {// 用户已经登录,则更新db，同步session
					shoppingCarService.updateCart(list_car.get(i));
				}
				System.out.println(list_car.size());
				// break;
			}
		}
	}

	/**
	 * 判断要添加的购物车是否已经存在购物车集合中
	 * 
	 * @param list_car
	 * @param cart
	 * @return true 是新的;false 已经存在;
	 */
	private boolean is_new_car(List<T_MALL_SHOPPINGCAR> list_car, T_MALL_SHOPPINGCAR cart) {
		boolean b = true;
		for (int i = 0; i < list_car.size(); i++) {
			if (list_car.get(i).getSku_id() == cart.getSku_id()) {// 存在返回，false
				b = false;
				break;
			}
		}

		return b;
	}

	/**
	 * 添加购物车成功后，去往成功页面
	 * 
	 * @param cart
	 * @param model
	 * @return
	 */
	@RequestMapping("goto_cart_success")
	public String goto_cart_success(T_MALL_SHOPPINGCAR cart, Model model) {

		return "sale_cart_success";
	}
}
