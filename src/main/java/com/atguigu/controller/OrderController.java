package com.atguigu.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.atguigu.bean.OBJECT_T_MALL_FLOW;
import com.atguigu.bean.OBJECT_T_MALL_ORDER;
import com.atguigu.bean.T_MALL_ADDRESS;
import com.atguigu.bean.T_MALL_ORDER_INFO;
import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.bean.T_MALL_USER_ACCOUNT;
import com.atguigu.exception.MyOverSaleException;
import com.atguigu.server.AddressServer;
import com.atguigu.service.OrderService;
import com.atguigu.service.impl.OrderServiceImpl;

@Controller
@SessionAttributes("order")
public class OrderController {

	@Autowired
	AddressServer addressServer;

	@Autowired
	OrderService orderService;

	@RequestMapping("/goto_order")
	public String goto_order(double sum, HttpSession session, Model model) {
		System.out.println(orderService.getClass());//没有配置事务class com.atguigu.service.impl.OrderServiceImpl
		ArrayList<T_MALL_SHOPPINGCAR> list_cart = (ArrayList<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");
		// 将购物车中的已选择的购物车进行拆单，根据购物车中商品的库存地址拆单
		OBJECT_T_MALL_ORDER order = new OBJECT_T_MALL_ORDER();
		List<OBJECT_T_MALL_FLOW> list_flow = new ArrayList<>();
		// 因为去往订单核算页面需要用户的收货地址
		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
		
		if(user==null) {
			//用户为空请登录
			return "redirect:/goto_login.do";
		}
		
		List<T_MALL_ADDRESS> list_address = addressServer.select_addresses_by_user_id(user);
		Set<String> set_cart = new HashSet<>();
		// 得到购物车的库存地址，去重
		for (int i = 0; i < list_cart.size(); i++) {
			// 必须是选中的商品
			if (list_cart.get(i).getShfxz().equals("1")) {
				set_cart.add(list_cart.get(i).getKcdz());
			}
		}
		// 一个购物车地址对应一个物流flow,一个订单order有多个包裹，一个包裹就是一个物流，一个物流flow里有多个商品order_info
		Iterator<String> iterator = set_cart.iterator();
		while (iterator.hasNext()) {
			String kcdz = iterator.next();

			OBJECT_T_MALL_FLOW flow = new OBJECT_T_MALL_FLOW();
			// 将购物商品放入物流包裹
			List<T_MALL_ORDER_INFO> list_info = new ArrayList<>();

			for (int j = 0; j < list_cart.size(); j++) {
				// 当满足同一个库存地址，则对应的若干个商品都可以添加进flow
				if (list_cart.get(j).getShfxz().equals("1") && list_cart.get(j).getKcdz().equals(kcdz)) {
					T_MALL_ORDER_INFO info = new T_MALL_ORDER_INFO();
					info.setSku_kcdz(kcdz);// 可能不需要
					info.setSku_id(list_cart.get(j).getSku_id());// 可能不需要
					info.setSku_jg(list_cart.get(j).getSku_jg());
					info.setSku_mch(list_cart.get(j).getSku_mch());
					info.setSku_shl(list_cart.get(j).getTjshl());
					info.setShp_tp(list_cart.get(j).getShp_tp());
					info.setGwch_id(list_cart.get(j).getId());// 可能不需要
					list_info.add(info);
				}
			}
			flow.setPsfsh("平西府快递");
			flow.setMqdd("商品未出库");
			
			flow.setList_info(list_info);
			list_flow.add(flow);
		}

		// order.setShhr(user.getYh_mch());
		order.setZje(new BigDecimal("" + sum));
		order.setJdh(0);// 0代表未出库
		order.setShhr(user.getYh_nch());
		order.setYh_id(user.getId());// 可能不需要
		order.setList_flow(list_flow);

		model.addAttribute("list_address", list_address);
		model.addAttribute("order", order);
		return "sale_order_confirm";
	}

	/**
	 * 目前是在结算页面，需要核算检查收货地址和总金额，提交订单
	 * 真实情况下，是提交订单重定向2次，到支付宝二维码页面
	 * 去往重定向到支付页面 提交按钮后，生成订单号
	 * address_id,sum,order,其实此处的sum是不需要的，因为结算时，拆单已经将总金额放入order中了
	 * 
	 * @return
	 */
	@RequestMapping("/check_order")
	public String check_order(double sum, int address_id, @ModelAttribute("order") OBJECT_T_MALL_ORDER order,
			HttpSession session, Model model) {
		//进度号改为1
		// 查询后台商品数据，检查订单中的商品信息和数据库中的商品信息是否一致
		T_MALL_ADDRESS address = addressServer.select_addresses_by_id(address_id);
		// 生成订单号，保存订单业务,list_gwc_id是保存订单后，删除保存订单的id
		List<Integer> list_gwc_id = orderService.save_order(sum, address, order);
		// 把已经生成订单的商品信息从购物车中删除
		// 同步session中的购物车信息// 清除session中已经提交的购物车信息
		List<T_MALL_SHOPPINGCAR> list_cart_session = (List<T_MALL_SHOPPINGCAR>) session
				.getAttribute("list_cart_session");
		Iterator<T_MALL_SHOPPINGCAR> iterator = list_cart_session.iterator();
		while (iterator.hasNext()) {
			Integer gwc_id = iterator.next().getId();
			for (int i = 0; i < list_gwc_id.size(); i++) {
				if (gwc_id.equals(list_gwc_id.get(i))) {
					iterator.remove();
				}
			}
		}
		return "redirect:/goto_order_pay.do";
	}
	
	//重定向到支付页面，防止表单重复提交
	@RequestMapping("/goto_order_pay")
	public String goto_order_pay(@ModelAttribute("order") OBJECT_T_MALL_ORDER order,Model model) {
		//进度号为1
		return "sale_order_pay";
	}
	//支付页面上点击支付按钮,(支付页面相当于支付宝有二维码的页面)，点击支付按钮，相当于扫二维码付款。
	@RequestMapping("/pay_order")
	public String pay_order(@ModelAttribute("order") OBJECT_T_MALL_ORDER order) {
		//付款成功
		//支付服务系统通知交易系统支付成功。之后交易系统开始改订单改物流改库存 
		try {
			//update order 修改订单，
			orderService.save_order_after_pay(order);
		} catch (MyOverSaleException e) {
			//更新失败
			System.out.println(e.getMessage());
			e.printStackTrace();
			return "redirect:/order_fail.do";
		}
		//更新订单
		//更新物流
		//减库存
		
		//更新成功
		return "redirect:/order_success.do";
	}

	@RequestMapping("/order_fail")
	public String order_fail() {
		
		return "order_fail";
	}
	@RequestMapping("/order_success")
	public String pay_success() {

		return "order_success";
	}

}
