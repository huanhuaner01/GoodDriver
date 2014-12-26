package com.huishen_app.zc.operate_thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import android.util.Log;
import com.huishen_app.all.alarmservice.AutoTask;
import com.huishen_app.all.alarmservice.HanderListObject;
import com.huishen_app.zc.httpclient.HuishenHttpClient;

public class RegistThread extends AutoTask{
	private ArrayList<Map<String ,String>> maps  = new  ArrayList<Map<String ,String>>();
	public RegistThread(HanderListObject temp,ArrayList<Map<String ,Object>> params) {
		super(temp, params.get(0));
		 Log.i("RegistThread",params.toString());
		 Log.i("RegistThread",params.size()+"");
        for(int i =0;i<params.size();i++){
        	Map<String ,String> map = new HashMap<String ,String>();
        	// 构造参数
    		Iterator<String> keyset = params.get(i).keySet().iterator();
    		String key, value;
    		while (keyset.hasNext()) {
    			key = keyset.next();
    			value = params.get(i).get(key).toString();
    			// 添加参数
    			if (key != null && key.length() > 0 && value != null
    					&& value.length() > 0)
    				map.put(key, value);
    		}
    		  maps.add(map);
    		  Log.i("RegistThread",map.toString());
        }
        
	}

	public void run() {
		StringBuffer result = new StringBuffer("");
		int tag = 11 ; //返回码标志位 ，为11正常处理数据，12错误信息
		int index = 1; //-1提交失败 ，i为arraylist列表的第几项成功
		try {

			for(int i = 0 ;i<maps.size()-1 ; i++){
				result = HuishenHttpClient.Post(maps.get(i).get("operateurl"),
						maps.get(i), maps.get(i).get("encoding"));
				Log.i("12334",result.toString());
				if (result.length()<1) {
					tag= 12;
					break ;
				}else
				{
				  JSONObject json = JSONObject.fromObject(result.toString());
				  if(json.getInt("validate") == 0){
					index=i+1 ;
					result = new StringBuffer(index+"");
				 }else{
					 break ;
				 }
				}
			}
			if(index == maps.size()-1){
				result = HuishenHttpClient.Post(maps.get(maps.size()-1).get("operateurl"),
						maps.get(maps.size()-1), getEncoding());
				if (result.length()<1) {
					tag= 12;
				}else{
				JSONObject json = JSONObject.fromObject(result.toString());
				if(json.getInt("count") == 0){
					index=-1 ;
					result = new StringBuffer(index+"");
				}else{
					index++;
					result = new StringBuffer(index+"");
				}
				}
			}
		} catch (Exception e) {
			Log.i("错误信息", e.getMessage());
		}
		finishtask(tag, result);
	}
}
