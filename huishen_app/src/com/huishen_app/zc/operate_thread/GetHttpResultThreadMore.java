package com.huishen_app.zc.operate_thread;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.util.Log;

import com.huishen_app.all.alarmservice.AutoTask;
import com.huishen_app.all.alarmservice.HanderListObject;
import com.huishen_app.zc.httpclient.HuishenHttpClient;

public class GetHttpResultThreadMore extends AutoTask {

	private Map<String, String> map_f, map_n;

	private String url_f, url_n;

	public GetHttpResultThreadMore(HanderListObject temp,
			Map<String, Object> p_f, String url_f, Map<String, Object> p_n,
			String url_n) {
		super(temp, p_f);
		this.url_f = url_f;
		this.url_n = url_n;

		map_f = new HashMap<String, String>();

		// 构造参数
		Iterator<String> keyset = p_f.keySet().iterator();
		String key, value;
		while (keyset.hasNext()) {
			key = keyset.next();
			value = p_f.get(key).toString();
			// 添加参数
			if (key != null && key.length() > 0 && value != null
					&& value.length() > 0)
				map_f.put(key, value);
		}
	   Log.i("GetHttp", map_f.toString());

		map_n = new HashMap<String, String>();
		// 构造参数
				Iterator<String> keyset_n = p_n.keySet().iterator();
				while (keyset_n.hasNext()) {
					key = keyset_n.next();
					value = p_f.get(key).toString();
					// 添加参数
					if (key != null && key.length() > 0 && value != null
							&& value.length() > 0)
						map_n.put(key, value);
				}
			   Log.i("GetHttp", map_n.toString());

	}

	@Override
	public void run() {
		StringBuffer result = new StringBuffer("");
		StringBuffer result_n = new StringBuffer("");
		Map<String, StringBuffer> r_map = new HashMap<String, StringBuffer>();
		try {
//
			result = HuishenHttpClient.Post(url_f, map_f, getEncoding());
			r_map.put("1", result);
			if (result.length() > 0) {
				if(result.length()==1){
					finishtask(13, r_map);
					return ;
				}
				
			}
			result_n = HuishenHttpClient.Post(url_n, map_n, getEncoding());
			r_map.put("2", result_n);
			if (result.length() > 0) {
				if(result.length()==1){
					finishtask(13, r_map);
					return ;
				}
				
			}
			Log.i("获取详情信息", result_n.toString());
		} catch (Exception e) {
			Log.i("错误信息", e.getMessage());
		}

		if (result.length() > 0&&result_n.length()>0) {
			finishtask(11, r_map);
			return;
		}
		finishtask(12, r_map);
	}

}
