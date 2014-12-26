package com.huishen_app.zc.ui.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

public class JL_Search_Adapter extends SimpleAdapter {
    private List<Map<String, Object>> data ;
	public JL_Search_Adapter(Context context, List<Map<String, Object>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		this.data = data ;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		convertView.setTag(data.get(position));
		return super.getView(position, convertView, parent);
	}

}
