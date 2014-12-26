package com.huishen_app.all.calendar;

import java.util.ArrayList;
import java.util.List;

import com.huishen_app.zc.ui.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Simple_Adapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<String> mData;
	private int position = 0;

	public Simple_Adapter(Context context) {
		this.mInflater = LayoutInflater.from(context);
		mData = new ArrayList<String>();
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return this.position;
	}

	public void setData(List<String> mData) {
		this.mData = mData;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		this.position = position;
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.calendar_year_list_item,
					null);
			convertView.setId(position);
			holder.val = (TextView) convertView.findViewById(R.id.val);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.val.setText(mData.get(position));
		return convertView;
	}

	/**
	 *  ”Õº––øÿ÷∆
	 * */
	static class ViewHolder {
		public TextView val;
	}
}
