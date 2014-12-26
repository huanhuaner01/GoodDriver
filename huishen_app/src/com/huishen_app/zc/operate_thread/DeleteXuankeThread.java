package com.huishen_app.zc.operate_thread;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.huishen_app.all.alarmservice.AutoTask;
import com.huishen_app.all.alarmservice.HanderListObject;
import com.huishen_app.zc.httpclient.HuishenHttpClient;

@SuppressWarnings("unchecked")
public class DeleteXuankeThread extends AutoTask {
	private List<String> idstr;
	private String mobileFlag;
	Map<String, String> map;

	public DeleteXuankeThread(HanderListObject temp, Map<String, Object> param) {

		super(temp, param);
		idstr = (List<String>) param.get("idstr");
		mobileFlag = (String)param.get("mobileFlag") ;
	}

	@Override
	public void run() {
		StringBuffer info;
		StringBuffer id = new StringBuffer() ;
		try {
			id.append(idstr.get(0));
			for (int i = 1; i < idstr.size(); ++i) {
				id.append(","+idstr.get(i));
			}
			Log.i("DeleteMoKaoThread", id.toString());
			map = new HashMap<String,String>();
			map.put("idStr", id.toString());
			map.put("mobileFlag", mobileFlag);
			info = HuishenHttpClient.Post(getOperateurl(), map,
					getEncoding());
			
            
		} catch (Exception e) {
			Log.i("¥ÌŒÛ–≈œ¢", e.getMessage());
			finishtask(12, "");
			return;
		}
		if(info.length() == 1){
			finishtask(13, info);
		}else{
		finishtask(11, info);
		}
	}

}
