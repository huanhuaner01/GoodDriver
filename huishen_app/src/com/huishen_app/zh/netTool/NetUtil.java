package com.huishen_app.zh.netTool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/***
 * ������ز���
 * @author zhanghuan
 *
 */
public class NetUtil {

	/**
	 * �ж��Ƿ�����������
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {  
	    if (context != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	        if (mNetworkInfo != null) {  
	            return mNetworkInfo.isAvailable();  
	        }  
	    }  
	    return false;  
	}

}
