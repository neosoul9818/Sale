package com.atguigu.util;

import java.math.BigDecimal;

public class TestBigDecimal {

	public static void main(String[] args) {
		// 初始化问题 , 用字符串构造器
		BigDecimal b1 = new BigDecimal(0.001f);
		BigDecimal b2 = new BigDecimal("0.001");
		System.out.println(b1);
		System.out.println(b2);

		// 比较问题
		int compareTo = b1.compareTo(b2);// -1 0 1
		System.out.println(compareTo);

		// 运算，加减乘除
		BigDecimal b3 = new BigDecimal("6.454545");
		BigDecimal b4 = new BigDecimal("7.232313");

		BigDecimal add = b3.add(b3);
		System.out.println(add);
		BigDecimal subtract = b3.subtract(b4);
		System.out.println(subtract);
		BigDecimal multiply = b3.multiply(b4);

		// 刻度，保留数位
		BigDecimal setScale = multiply.setScale(3, BigDecimal.ROUND_HALF_DOWN);
		System.out.println(setScale);

		// 除不尽，保留数位
		BigDecimal divide = b3.divide(b4, 2, BigDecimal.ROUND_HALF_DOWN);
		System.out.println(divide);

	}

}
