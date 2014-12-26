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

	// ���Ķ�ȡ����
	private final static int MAX_READ_LEN = 1000;

	// ����ģʽ��HTTPClient
	private static HttpClient httpclient;

	// ����ģʽ��HttpClient
	public static synchronized HttpClient getHttpClient() {
		if (null == httpclient) {
			httpclient = new DefaultHttpClient();
		}
		return httpclient;
	}

	/**
	 * HttpClient post �ύ���ݷ���
	 * 
	 * @throws Exception
	 */
	public static final StringBuffer Post(String url,
			Map<String, String> param, String utf_gbk) {

		// ������ر���
		HttpPost httppost = null;
		HttpResponse response = null;
		StringBuffer result = new StringBuffer();
         
		// ��Ч�Լ��
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
			// �½�һ�����Թرյ�HTTPClient
			httpclient = getHttpClient();

			// �½�HTTPOST
			httppost = new HttpPost(url);

			// �½�����
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			// �������
			Iterator<String> keyset = param.keySet().iterator();
			String key, value;
			while (keyset.hasNext()) {
				key = keyset.next();
				value = param.get(key);
				// ��Ӳ���
				if (key != null && key.length() > 0 && value != null
						&& value.length() > 0)
					nvps.add(new BasicNameValuePair(key, value));
			}
            Log.i("Huishen", nvps.toString());
			// ���ò���
			httppost.setEntity(new UrlEncodedFormEntity(nvps, utf_gbk));

			// �򿪴������Ӳ�����
			response = httpclient.execute(httppost);

			// �ж��Ƿ�����ɹ�
			if (response.getStatusLine().getStatusCode() >= 200
					&& response.getStatusLine().getStatusCode() < 300) {
				result = ReadHtmlInfo(response);
			}
			if(response.getStatusLine().getStatusCode() == 320){
				result = new StringBuffer("1");
				Log.i("��������", result.toString().length()+"");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("�������ʧ��", e.getMessage());
		}

		// ���ιر��������
		try {
			if (httppost != null)
				httppost.abort();
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * ��ȡ��ҳ����
	 * 
	 * @param response
	 * @throws Exception
	 */
	public static StringBuffer ReadHtmlInfo(HttpResponse response)
			throws Exception {
		// �½�һ���������ݶ���
		StringBuffer s_b = new StringBuffer();
		// ��ȡ��������
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			// ��ȡ����������
			InputStream instream = entity.getContent();

			// ��ȡ��һ��������
			byte[] buffer = new byte[MAX_READ_LEN + 2];
			int read_len = instream.read(buffer, 0, MAX_READ_LEN);

			String temp;
			while (read_len > 0) {
				temp = new String(buffer, 0, read_len);
				s_b.append(temp);
				// ������ȡ����
				read_len = instream.read(buffer, 0, MAX_READ_LEN);
			}
			instream.close();
		}
		return s_b;
	}

}
