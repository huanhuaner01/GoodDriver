package com.huishen_app.zc.ui.adapter;

import java.util.List;
import java.util.Map;

import com.huishen_app.zc.ui.R;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Book_TrainHistory_Adapter extends SimpleAdapter {
    private String TAG = "Book_TrainHistory_Adapter";
	private List<Map<String, Object>> data;

	public Book_TrainHistory_Adapter(Context context,
			List<Map<String, Object>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		this.data = data;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
	    TextView status = (TextView) view.findViewById(R.id.book_yuyue_his_status);
		try {
			TextView judge = (TextView)view.findViewById(R.id.book_yuyue_his_judge);
			ImageView see = (ImageView)view.findViewById(R.id.book_yuyue_his_see);
			
			Log.i(TAG,"count:"+(data.get(position).get("count")) );
			if((Integer)data.get(position).get("count")== 0){
				 //未评价
				judge.setVisibility(View.VISIBLE);
				see.setVisibility(View.GONE);
			}else{
				//评价了
				judge.setVisibility(View.GONE);
				see.setVisibility(View.VISIBLE);
			}
			switch((Integer)data.get(position).get("status")){
			 case 0: //未分配车辆
				 status.setText("未分配");
				 break ;
			 case 1: //已分配车辆
				 status.setText("已分配");
				 break;
			 case 2: //培训完成
				 status.setText("已完成");
				 break ;
			 case 4: //培训异常
				 status.setText("培训异常");
				 break ;
			}
//			data.equals(null);
//			//将状态赋予这个item
			view.setTag(data.get(position).get("count"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return view;
	}
}
