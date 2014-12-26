package com.huishen_app.zc.operate_thread;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.huishen_app.all.alarmservice.AutoTask;
import com.huishen_app.all.alarmservice.HanderListObject;
import com.huishen_app.zc.httpclient.HuishenHttpClient;

public class LoginThread extends AutoTask {

	/** 参数 */
	private Map<String, String> map;

	public LoginThread(HanderListObject temp, Map<String, Object> param) {
		super(temp, param);
		Log.i("denglu", param.toString());
	}

	public void run() {
		init();
		StringBuffer result = new StringBuffer();
		try {
			result = HuishenHttpClient
					.Post(getOperateurl(), map, getEncoding());

		} catch (Exception e) {
			Log.i("错误信息", e.getMessage());
		}
        
		if (result.toString().length() > 0)
		{
			finishtask(11, result);
		}
		finishtask(12, "登录失败");
	}

	public void init() {
		map = new HashMap<String, String>();
		map.put("username", getParams("username"));
		map.put("password", getParams("password"));
		map.put("mobileFlag", getParams("mobileFlag"));
		
	}
	
	
}
