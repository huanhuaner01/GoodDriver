package com.huishen_app.zc.ui.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.huishen_app.zc.ui.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
/**
 * 预约模考的适配器
 * @author zhanghuan
 *
 */
public class Book_Mokao_Adapter extends SimpleAdapter {

	private List<Map<String, Object>> data;

	public Book_Mokao_Adapter(Context context, List<Map<String, Object>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		this.data = data;
		if(data == null){
			data = new ArrayList<Map<String, Object>>();
		}
		this.data = data;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		CheckBox book_mokao_icon_status;
		TextView book_mokao_his_states;
		try {

			book_mokao_icon_status = (CheckBox) view
					.findViewById(R.id.book_mokao_icon_status);
			book_mokao_his_states = (TextView) view
					.findViewById(R.id.book_mokao_his_states);
			String status = data.get(position).get("status_id").toString();
			//设置选中状态
			  if (((ListView) parent).isItemChecked(position)) {
				  book_mokao_icon_status.setChecked(true);
		        } else {
		        	book_mokao_icon_status.setChecked(false);
		        }
			  view.setTag(data.get(position).get("mokao_id"));
			if (status.equalsIgnoreCase("0")) {// 待分配状态 可以操作
				book_mokao_icon_status
						.setBackgroundResource(R.drawable.all_select_icon_selector);
				book_mokao_his_states.setTextColor(Color.rgb(255, 102, 3));
				book_mokao_his_states.setText(data.get(position).get("status")
						.toString());
			} else if (status.equalsIgnoreCase("3")) {// 异常
				book_mokao_icon_status
						.setBackgroundResource(R.drawable.all_unoperate_icon);
				book_mokao_his_states.setTextColor(Color.rgb(255, 1, 1));
				book_mokao_his_states.setText(data.get(position).get("status")
						.toString());
			} else {// 已分配 已完成
				book_mokao_icon_status
						.setBackgroundResource(R.drawable.all_unoperate_icon);
				book_mokao_his_states.setTextColor(Color.rgb(51, 178, 109));
				book_mokao_his_states.setText(data.get(position).get("status")
						.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	}

}
