package com.huishen_app.zh.calendar;

import java.util.List;
import java.util.Map;

import com.huishen_app.zc.ui.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

@SuppressLint("NewApi") 
public class CalendarGridViewAdapter extends SimpleAdapter {
    private List<? extends Map<String,Object>> data ;
    private int currentChecked = -1 ; //��¼Ŀǰѡ�е�item���±� ��-1Ϊû��ѡ��
    private int currentpreStatus = 0 ; //��¼Ŀǰѡ�е�item֮ǰ��״̬
    private Context context ;
    public CalendarGridViewAdapter(Context context,
			List<? extends Map<String, Object>> data, int resource, String[] from,
			int[] to ) {
		super(context, data, resource, from, to);
		this.data = data ;
		this.context = context ;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view =  super.getView(position, convertView, parent);
		TextView tv = (TextView)view.findViewById(R.id.day) ;
		TextView bottomtv = (TextView)view.findViewById(R.id.day_bottomtv) ;
		setTvStatus(bottomtv ,tv ,Integer.parseInt(this.data.get(position).get("status").toString()),position );
		view.setTag(this.data.get(position));
		return view ;
	}
	
	/**
	 * ѡ������ĸ���
	 * @param position ѡ�е��±�
	 * 
	 */
	public void selectOption(int position){
		this.data.get(currentChecked).put("status",this.currentpreStatus) ;
		this.currentpreStatus = Integer.parseInt(this.data.get(position).get("status").toString()) ;
		
		this.data.get(position).put("status", 1);
		this.notifyDataSetChanged() ;
		
	}
	private void setTvStatus(TextView bottomtv,TextView tv , int status ,int position){
		
		switch(status){
		case 0:  //��ѡ
			tv.setEnabled(true)  ;
			tv.setSelected(false);
			break ;
		case 1:  //ѡ��
			tv.setEnabled(true)  ;
			tv.setSelected(true);
			this.currentChecked = position ;
			break ;
			
		case 2:  //����
			tv.setEnabled(true)  ;
			tv.setBackgroundResource(R.drawable.today_bg) ;
			bottomtv.setText("����") ;
			bottomtv.setTextColor(this.context.getResources().getColor(R.color.main_title_background)) ;
			if(this.currentChecked == -1){
				tv.setSelected(true);
				this.currentChecked = position ;
				this.currentpreStatus = 2 ;
			}else{
				tv.setSelected(false);
			}
			bottomtv.setText("����") ;
			bottomtv.setTextColor(this.context.getResources().getColor(R.color.main_title_background)) ;
			break ;
		case -1: //����ѡ
			tv.setEnabled(false) ;
			tv.setSelected(false);
			tv.setBackground(null);
			tv.setTextColor(this.context.getResources().getColor(R.color.Grey_line)) ;
			break ;
		case -2: //���첻��ѡ
			tv.setEnabled(false)  ;
			tv.setSelected(false);
			tv.setTextColor(this.context.getResources().getColor(R.color.Grey_line)) ;
			tv.setBackgroundResource(R.drawable.cricle_todat_notoption) ;
			bottomtv.setText("����") ;
			bottomtv.setTextColor(this.context.getResources().getColor(R.color.main_title_background)) ;
			break ;
			
		}
	}
    
}
