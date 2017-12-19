package com.atguigu.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.atguigu.bean.T_MALL_ADDRESS;
import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.bean.T_MALL_USER_ACCOUNT;
import com.atguigu.server.AddressServer;
import com.atguigu.server.UserServer;
import com.atguigu.service.LoginService;
import com.atguigu.service.ShoppingCartService;
import com.atguigu.util.MyJsonUtil;
import com.atguigu.util.MyUploadUtil;
import com.atguigu.util.NumberUtil;

@Controller
public class LoginController {
	@Autowired
	private LoginService loginService;
	@Autowired
	UserServer userServer;
	@Autowired
	AddressServer addressServer;
	// private UserServer userServer = MyWSFactoryUtil
	// .getWs(MyPropertiesUtil.getMyProperty("ws.properties", "ws_user"),
	// UserServer.class);

	@Autowired
	private ShoppingCartService shoppingCarService;

	@RequestMapping("/goto_login")
	public String goto_login() {

		return "sale_login";
	}

	// 表单异步提交
	@RequestMapping(value = "login2", produces = "text/html; charset=UTF-8")
	@ResponseBody
	public String login_serialize(T_MALL_USER_ACCOUNT user_account) {

		return "中文乱码";
	}

	@RequestMapping("login")
	public String login(String dataSource_type,@CookieValue("list_cart_cookie") String list_cart_cookie, T_MALL_USER_ACCOUNT user_account,
			HttpSession session, HttpServletResponse response) {
		// 表单数据右user_account的用哪个名称和密码
		// T_MALL_USER_ACCOUNT dbUser = loginService.getUser_Account(user_account);
		
		
		T_MALL_USER_ACCOUNT dbUser=null;
		try {
			//因为此处的user是通过webservice得到的，webservice远程服务器如果没有启动，此处会发生connection refuse异常
//			dbUser = userServer.login(user_account);
			//第一种：我的方式
//			dbUser = userServer.login_test(dataSource_type, user_account);
			//第二种：老师的方式，体现了高并发线程安全问题
			if("1".equals(dataSource_type)) {
				dbUser = userServer.login(user_account);
			}else {
				dbUser = userServer.login2(user_account);
			}
			List<T_MALL_ADDRESS> addresses_by_user_id = addressServer.select_addresses_by_user_id(dbUser);
			System.out.println(addresses_by_user_id);
			
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e1.printStackTrace();
			return "redirect:/goto_login.do";
		}
		if (dbUser == null) {
			return "redirect:/goto_login.do";
		} else {
			session.setAttribute("user", dbUser);
			Cookie cookie=null;
			try {
				cookie = new Cookie("yh_nch", URLEncoder.encode(dbUser.getYh_nch(), "utf-8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			cookie.setMaxAge(60 * 60 * 24);
			response.addCookie(cookie);

			try {
				Cookie cookie2 = new Cookie("test", URLEncoder.encode("cookie中的中文乱码", "utf-8"));
				cookie2.setMaxAge(60 * 60 * 24 * 3);
				response.addCookie(cookie2);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			// cookie.setPath("/");

			// 合并购物车
			// 用户id、cookie购物车、response、session
			List<T_MALL_SHOPPINGCAR> list_cart_db = shoppingCarService.get_cart_list_by_userid(dbUser);
			merge_cart(list_cart_cookie, list_cart_db, response, session);

			return "redirect:/index.do";

		}
	}

	/**
	 * 合并购物车（在用户登录的情况下，将cookie中的购物车数据同步到数据库db中，并且同步session，删除cookie）
	 * 
	 * @param list_cart_cookie
	 * @param list_cart_db
	 * @param response
	 *            用于返回浏览器一个新的空值的cookie为了覆盖
	 * @param session
	 *            从session中取得用户user，将cookie中的购物车添加进db时，没有yh_id，所以需要session，而且合并购物车中，要从db中同步session
	 */
	private void merge_cart(String list_cart_cookie, List<T_MALL_SHOPPINGCAR> list_cart_db,
			HttpServletResponse response, HttpSession session) {

		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
		List<T_MALL_SHOPPINGCAR> list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);

		if (list_cart_db == null || list_cart_db.size() == 0) {// db中没有，则直接将cookie数据添加add进db

			if (StringUtils.isBlank(list_cart_cookie)) {
				// db和cookie都为空，结束，不操作
			} else {// db没有，cookie中有数据，直接添加add进db,注意cookie中的cart没有yh_id
				// List<T_MALL_SHOPPINGCAR> list_cart =
				// MyJsonUtil.json_to_list(list_cart_cookie,
				// T_MALL_SHOPPINGCAR.class);
				for (int i = 0; i < list_cart.size(); i++) {

					list_cart.get(i).setYh_id(user.getId());// 注意cookie中的cart没有yh_id,所以要加入用户id
					shoppingCarService.saveCart(list_cart.get(i));// 保存db
				}
				// session.setAttribute("list_cart_session", list_cart);// 同步session
				// Cookie cookie = new Cookie("list_cart_cookie", "");//
				// 删除cookie，用空值cookie覆盖旧的有值的cookie
				// cookie.setMaxAge(60 * 60 * 24);
				// response.addCookie(cookie);
			}
		} else {// db中有数据，分2中情况
			if (StringUtils.isBlank(list_cart_cookie)) {// cookie中没有，
				// 不操作，结束
			} else {// cookie中有数据
				// List<T_MALL_SHOPPINGCAR> list_cart =
				// MyJsonUtil.json_to_list(list_cart_cookie,
				// T_MALL_SHOPPINGCAR.class);
				// 分2中情况更新或添加

				for (int i = 0; i < list_cart.size(); i++) {

					boolean b = is_new_car(list_cart_db, list_cart.get(i));

					list_cart.get(i).setYh_id(user.getId());// 注意cookie中的数据么有用户id
					if (b) {// 如果true，则是新的，添加db中,同步session
						shoppingCarService.saveCart(list_cart.get(i));
					} else {// 如果是false，则更新update。
						updateCart(list_cart.get(i), list_cart_db);
					}
				}
				// List<T_MALL_SHOPPINGCAR> list =
				// shoppingCarService.get_cart_list_by_userid(user);
				// session.setAttribute("list_cart_session", list);
			}
		}

		Cookie cookie = new Cookie("list_cart_cookie", "");// 删除cookie，用空值cookie覆盖旧的有值的cookie
		cookie.setMaxAge(60 * 60 * 24);
		response.addCookie(cookie);

		List<T_MALL_SHOPPINGCAR> list = shoppingCarService.get_cart_list_by_userid(user);
		session.setAttribute("list_cart_session", list);
	}

	@RequestMapping("regist")
	public String regist(T_MALL_USER_ACCOUNT user_account, @RequestParam("files") MultipartFile[] files) {
		// 上传文件
		List<String> list_image = MyUploadUtil.upload_image(files);
		user_account.setYh_tx(list_image.get(0));
		// 保存用户
		loginService.regist(user_account);

		// 注册成功
		return "redirect:/index.do";
		// 注册失败
	}

	@RequestMapping("to_regist")
	public String to_regist() {

		return "to_regist";
	}

	@RequestMapping("goto_out")
	public String goto_out(HttpSession session) {
		session.removeAttribute("user");
		return "sale_index";
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
				if (cart.getYh_id() != 0) {// 用户已经登录，则cart中有yh_id,则更新db，同步session
					shoppingCarService.updateCart(list_car.get(i));
				}
				System.out.println(list_car.size());
				// break;
			}
		}
	}

}
