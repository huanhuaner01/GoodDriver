package com.huishen_app.zc.ui.adapter;

import java.util.List;
import java.util.Map;

import com.huishen_app.zc.ui.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class JlListPopGridAdapter extends SimpleAdapter {
    private List<Map<String ,Object>> data ;
	public JlListPopGridAdapter(Context context,
			List<Map<String ,Object>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		this.data = data ;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		TextView tv= (TextView)view.findViewById(R.id.jl_pop_itemtv) ;
		
		if(Integer.parseInt(data.get(position).get("status").toString()) == 1){
			tv.setSelected(true) ;
		}else{
			tv.setSelected(false) ;
		}
		view.setTag(data.get(position).get("value").toString());
		return view ;
	}
    
}
