package com.atguigu.util;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.ext.WSPasswordCallback;

public class MyPasswordCallBack implements CallbackHandler{

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		//callbacks[0],此处是因为，callbacks数组中只有一个数据。
		//public class WSPasswordCallback implements Callback {
		WSPasswordCallback pwdcb = (WSPasswordCallback)callbacks[0];
		
		
		pwdcb.setIdentifier("a");
		//此处只是为了测试，实际生产中不能这样，不安全
//		pwdcb.setPassword("1");
		//真实环境中防止别人窃取令牌密码，可以+时间+，再用md5加密
		pwdcb.setPassword(MD5Util.md5("1"+MyDataUtil.getMyDateString()));
		
	}

}
