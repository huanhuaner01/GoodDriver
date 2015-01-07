package com.huishen_app.zc.ui.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huishen_app.zc.ui.R;
import com.huishen_app.zh.util.TextStyleUtil;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class KeyValueAdapter extends SimpleAdapter {
    private List<? extends Map<String, ?>> data ;
    private Context context ;
	public KeyValueAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		if(data == null){
			data = new ArrayList<HashMap<String ,Object>>();
		}
		this.data = data ;
		this.context = context ;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		try{
	    TextView value = (TextView)view.findViewById(R.id.item_value);
	    if((Boolean) data.get(position).get("islight")){
	    	value.setText(TextStyleUtil.getTextAppearanceSpan(context, data.get(position).get("value").toString(), 1.2f));
	    }
	    else{
	    	value.setText(data.get(position).get("value").toString());
	    }
		}catch(Exception e){
			
		}
		return view ;
	}
	

}
