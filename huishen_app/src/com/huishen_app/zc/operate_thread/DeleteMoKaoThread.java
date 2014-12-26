package com.huishen_app.zc.operate_thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.huishen_app.all.alarmservice.AutoTask;
import com.huishen_app.all.alarmservice.HanderListObject;
import com.huishen_app.zc.httpclient.HuishenHttpClient;

@SuppressWarnings("unchecked")
public class DeleteMoKaoThread extends AutoTask {
	private List<String> idlist;
	Map<String, String> map;
    String mobileFlag = null ;
	public DeleteMoKaoThread(HanderListObject temp, Map<String, Object> param) {

		super(temp, param);
		idlist = (List<String>) param.get("idlist");
		mobileFlag = (String)param.get("mobileFlag") ;
	}

	public void run() {
		StringBuffer info;
		StringBuffer id = new StringBuffer() ;
		try {
			id.append(idlist.get(0));
			for (int i = 1; i < idlist.size(); ++i) {
				id.append(","+idlist.get(i));
			}
			Log.i("DeleteMoKaoThread", id.toString());
			map = new HashMap<String,String>();
			map.put("id", id.toString());
			map.put("mobileFlag", mobileFlag);
			info = HuishenHttpClient.Post(getOperateurl(), map,
					getEncoding());
		} catch (Exception e) {
			Log.i("¥ÌŒÛ–≈œ¢", e.getMessage());
			finishtask(12, "");
			return;
		}
		if(info.length() == 1){
			finishtask(13, null);
		}else{
		finishtask(11, info);
		}
	}

}
