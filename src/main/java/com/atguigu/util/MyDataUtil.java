package com.atguigu.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDataUtil {

	public static void main(String[] args) {
		Date date = MyDataUtil.getMyDate(0);
		System.out.println(date);
	}

	public static Date getMyDate(int i) {
		Calendar c = Calendar.getInstance();

		c.add(Calendar.DATE, i);

		return c.getTime();
	}

	public static String getMyDateString() {
		//注意此处的时间是定义到分，么有秒，所以，用于token时，过期时间是一分钟
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");

		String format = sdf.format(new Date());
		return format;
	}

}
