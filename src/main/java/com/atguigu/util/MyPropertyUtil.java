package com.atguigu.util;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

public class MyPropertyUtil {

	public static String getProperty(String url, String key) {
		Properties props = new Properties();
		
		try {
			props.load(MyPropertyUtil.class.getClassLoader().getResourceAsStream(url));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return (String)props.getProperty(key);
	}
	
}
