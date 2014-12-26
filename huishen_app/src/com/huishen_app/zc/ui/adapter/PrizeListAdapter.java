package com.huishen_app.zc.ui.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huishen_app.zc.ui.R;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

public class PrizeListAdapter extends SimpleAdapter {
	   private List<? extends Map<String, ?>> data ;
	    private Context context ;
	    
	public PrizeListAdapter(Context context,
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
		Log.i("prizeListAdapter", "ÔÚ»æÖÆÑ½");
		try{
		ImageView icon = (ImageView)view.findViewById(R.id.prizelist_item_ic);
	    icon.setBackgroundResource((Integer) data.get(position).get("icon"));
		}catch(Exception e){
			
		}
		return view ;
	}

}
