package com.huishen_app.zc.ui.adapter;

import java.util.List;
import java.util.Map;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zh.netTool.AppController;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

public class JL_Search_Adapter extends SimpleAdapter {
    private List<Map<String, Object>> data ;
    private ImageLoader imageLoader ;
	public JL_Search_Adapter(Context context, List<Map<String, Object>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		this.data = data ;
		imageLoader = AppController.getInstance().getImageLoader();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		NetworkImageView img = (NetworkImageView)view.findViewById(R.id.jl_item_photo);
		if (data.get(position).get("photo")!= null && !data.get(position).get("photo").equals("")) {
			
			img.setDefaultImageResId(R.drawable.jl_test_icon);
			img.setErrorImageResId(R.drawable.ic_launcher);
			img.setImageUrl(data.get(position).get("photo").toString(), imageLoader);
		}
		return view ;
	}

}
