package com.huishen_app.zc.ui.adapter;

import java.util.List;
import java.util.Map;
import com.huishen_app.zc.ui.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;

public class RattingBarListAdapter extends SimpleAdapter{
	private List<? extends Map<String, ?>> data ;
	public RattingBarListAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		this.data = data ;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		RatingBar ratingbar = (RatingBar)view.findViewById(R.id.ratingbar);
		if (data.get(position).get("numstar").toString()!= null && !data.get(position).get("numstar").toString().equals("")) {
			
			ratingbar.setRating(Float.parseFloat(data.get(position).get("numstar").toString()));
		}
		return view ;
	}

}
