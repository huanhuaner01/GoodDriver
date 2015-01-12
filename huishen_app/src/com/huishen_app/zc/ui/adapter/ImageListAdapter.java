package com.huishen_app.zc.ui.adapter;

import java.util.List;
import java.util.Map;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zh.netTool.AppController;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

public class ImageListAdapter extends SimpleAdapter {
	private String TAG = "ImageListAdapter";
	private List<? extends Map<String, ?>> data;
	private ImageLoader imageLoader;

	public ImageListAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		this.data = data;
		imageLoader = AppController.getInstance().getImageLoader();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		NetworkImageView img = (NetworkImageView) view
				.findViewById(R.id.list_imageitem_image);
		if (data.get(position).get("photo") != null
				&& !data.get(position).get("photo").equals("")) {
			Log.i(TAG, "…Ë÷√Õº∆¨" + "position");
			img.setDefaultImageResId(R.drawable.jl_test_icon);
			img.setErrorImageResId(R.drawable.ic_launcher);
			img.setImageUrl(data.get(position).get("photo").toString(),
					imageLoader);
		}
		return view;
	}

}
