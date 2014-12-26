package com.huishen_app.zc.ui.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.huishen_app.zc.ui.R;

import android.content.Context;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Book_YuYue_Adapter extends SimpleAdapter {

	private List<Map<String, Object>> data;

	public Book_YuYue_Adapter(Context context, List<Map<String, Object>> data,
			int resource, String[] from, int[] to ) {
		super(context, data, resource, from, to);
		if(data == null){
			data = new ArrayList<Map<String, Object>>();
		}
		this.data = data;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		CheckBox book_yuyue_icon_status;
		TextView price ;
		try {
			//设置标志位
            view.setTag(data.get(position).get("yuyue_id"));
            
			book_yuyue_icon_status = (CheckBox) view
					.findViewById(R.id.book_yuyue_icon_status);
//			book_yuyue_icon_status.setTag(data.get(position).get("yuyue_id"));
			price = (TextView) view
					.findViewById(R.id.book_yuyue_his_price);
			//设置选中状态
			  if (((ListView) parent).isItemChecked(position)) {
				  book_yuyue_icon_status.setChecked(true);
		        } else {
		          book_yuyue_icon_status.setChecked(false);
		        }
			  
			TextPaint tp = price.getPaint(); 
			tp.setFakeBoldText(true);
			
				book_yuyue_icon_status
						.setBackgroundResource(R.drawable.all_select_icon_selector);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	} 
	
	  public final class ViewHolder {   
	        public CheckBox cBox;    
	    }   
}
