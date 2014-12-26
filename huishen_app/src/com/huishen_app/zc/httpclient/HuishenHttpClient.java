package com.huishen_app.zc.httpclient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.util.Log;

public class HuishenHttpClient {

	// 最大的读取长度
	private final static int MAX_READ_LEN = 1000;

	// 单例模式的HTTPClient
	private static HttpClient httpclient;

	// 单例模式的HttpClient
	public static synchronized HttpClient getHttpClient() {
		if (null == httpclient) {
			httpclient = new DefaultHttpClient();
		}
		return httpclient;
	}

	/**
	 * HttpClient post 提交数据方法
	 * 
	 * @throws Exception
	 */
	public static final StringBuffer Post(String url,
			Map<String, String> param, String utf_gbk) {

		// 定义相关变量
		HttpPost httppost = null;
		HttpResponse response = null;
		StringBuffer result = new StringBuffer();
         
		// 有效性检查
		if (url == null){
			Log.i("Http", "url is null");
			return result;}
		if (param == null){
			Log.i("Http", "param is null");
			param = new HashMap<String, String>();}
		if (utf_gbk == null){
			Log.i("Http", "utf_gbk is null");
			utf_gbk = HTTP.UTF_8;}
		try {
			// 新建一个可以关闭的HTTPClient
			httpclient = getHttpClient();

			// 新建HTTPOST
			httppost = new HttpPost(url);

			// 新建参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			// 构造参数
			Iterator<String> keyset = param.keySet().iterator();
			String key, value;
			while (keyset.hasNext()) {
				key = keyset.next();
				value = param.get(key);
				// 添加参数
				if (key != null && key.length() > 0 && value != null
						&& value.length() > 0)
					nvps.add(new BasicNameValuePair(key, value));
			}
            Log.i("Huishen", nvps.toString());
			// 设置参数
			httppost.setEntity(new UrlEncodedFormEntity(nvps, utf_gbk));

			// 打开处理连接并返回
			response = httpclient.execute(httppost);

			// 判断是否请求成功
			if (response.getStatusLine().getStatusCode() >= 200
					&& response.getStatusLine().getStatusCode() < 300) {
				result = ReadHtmlInfo(response);
			}
			if(response.getStatusLine().getStatusCode() == 320){
				result = new StringBuffer("1");
				Log.i("噶哈哈哈", result.toString().length()+"");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("网络操作失败", e.getMessage());
		}

		// 依次关闭相关连接
		try {
			if (httppost != null)
				httppost.abort();
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 读取网页内容
	 * 
	 * @param response
	 * @throws Exception
	 */
	public static StringBuffer ReadHtmlInfo(HttpResponse response)
			throws Exception {
		// 新建一个保存数据对象
		StringBuffer s_b = new StringBuffer();
		// 获取具体内容
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			// 获取链接输入流
			InputStream instream = entity.getContent();

			// 读取第一个段数据
			byte[] buffer = new byte[MAX_READ_LEN + 2];
			int read_len = instream.read(buffer, 0, MAX_READ_LEN);

			String temp;
			while (read_len > 0) {
				temp = new String(buffer, 0, read_len);
				s_b.append(temp);
				// 连续读取数据
				read_len = instream.read(buffer, 0, MAX_READ_LEN);
			}
			instream.close();
		}
		return s_b;
	}

}
