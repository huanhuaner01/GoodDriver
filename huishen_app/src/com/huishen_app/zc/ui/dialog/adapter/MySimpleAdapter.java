package com.huishen_app.zc.ui.dialog.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

public class MySimpleAdapter extends SimpleAdapter {

	// ������Ϣ
	private List<Map<String, String>> datacopy;

	/**
	 * ���캯��
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 * @param from
	 * @param to
	 */
	public MySimpleAdapter(Context context, List<Map<String, String>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		datacopy = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View viewnew = super.getView(position, convertView, parent);
		try {

		} catch (Exception e) {
		}
		return viewnew;
	}

	/**
	 * ��ȡֵ
	 * 
	 * @param p
	 * @param key
	 * @return
	 */
	public String getvalue(int p, String key) {
		String str = null;
		try {
			str = datacopy.get(p).get(key);
		} catch (Exception e) {
		}
		return str;
	}

	public List<Map<String, String>> getDatacopy() {
		return datacopy;
	}
}
