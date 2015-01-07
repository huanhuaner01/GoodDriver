package com.huishen_app.zc.ui.adapter;

import java.util.List;
import java.util.Map;

import com.huishen_app.zc.ui.R;
import com.huishen_app.zh.util.TextStyleUtil;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class JX_Search_Adapter extends SimpleAdapter {
    private Context context ;
    private List<Map<String, Object>> data ;
	public JX_Search_Adapter(Context context, List<Map<String, Object>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		this.context = context ;
		this.data = data ;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
	    TextView tv = (TextView)view.findViewById(R.id.jx_select_price) ;
	    tv.setText(TextStyleUtil.getTextAppearanceSpan(context, data.get(position).get("price").toString()));
	    tv.append("Æð");
		return view ;
	}

}
