package com.huishen_app.zc.operate_thread;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.util.Log;

import com.huishen_app.all.alarmservice.AutoTask;
import com.huishen_app.all.alarmservice.HanderListObject;
import com.huishen_app.zc.httpclient.HuishenHttpClient;

public class ADDXuankeThread extends AutoTask {
	private Map<String, String> map;

	public ADDXuankeThread(HanderListObject temp, Map<String, Object> param) {

		super(temp, param);

		map = new HashMap<String, String>();
        
		// 构造参数
		Iterator<String> keyset = param.keySet().iterator();
		String key, value;
		while (keyset.hasNext()) {
			key = keyset.next();
			value = param.get(key).toString();
			// 添加参数
			if (key != null && key.length() > 0 && value != null
					&& value.length() > 0)
				map.put(key, value);
		}
        Log.i("AddXuanke", map.toString());
	}

	public void run() {
		StringBuffer result = new StringBuffer("");
		try {
			result = HuishenHttpClient
					.Post(getOperateurl(), map, getEncoding());

		} catch (Exception e) {
			Log.i("错误信息", e.getMessage());
		}

		if (result.length() > 0) {
			if(result.length() == 1){
				finishtask(13, "登录失败");
			}
			finishtask(11, result);
			return;
		}
		finishtask(12, "选课失败");
	}

}
