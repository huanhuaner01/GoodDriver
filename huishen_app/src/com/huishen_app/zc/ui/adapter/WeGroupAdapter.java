package com.huishen_app.zc.ui.adapter;

import java.util.List;
import java.util.Map;

import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.base.BaseActivity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 
 * @author zhanghuan
 *
 */
public class WeGroupAdapter extends BaseAdapter {
	private List<Map<String, Object>> data;
	private BaseActivity context ;
	private OnClickListener listener ;
	private LayoutInflater mInflater;
    private final int TYPE_COUNT=2;  
    private final int HEADER_TYPE=0;  
    private final int DEFAULT_TYPE=1;

	public WeGroupAdapter(BaseActivity context ,List<Map<String, Object>> data,OnClickListener listener) {
		super();
		this.data = data ;
		this.context = context ;
		mInflater = LayoutInflater.from(context);
		this.listener = listener ;
	}
	

	@Override
	public int getItemViewType(int position) {
		if(position == 0){
			return HEADER_TYPE ;
		}
		return DEFAULT_TYPE;
	}

	
	@Override
	public int getViewTypeCount() {
		return TYPE_COUNT;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder_HeaderView h0 = null ;
		ViewHolder_default h1 = null;
		int type = getItemViewType(position);
		if(convertView == null){
			switch (type) {
			case HEADER_TYPE:
				convertView = mInflater.inflate(R.layout.wegroup_item_header,
        				null); 
				h0 = new ViewHolder_HeaderView();
				h0.img = (View)convertView.findViewById(R.id.wegroup_item_header_bg);
		
				convertView.setTag(h0); 
				break ;
			default:
				convertView = mInflater.inflate(R.layout.wegroup_item,
        				null);	
				h1 = new ViewHolder_default();
				h1.ic = (ImageView)convertView.findViewById(R.id.wegroup_item_ic) ;
				h1.item_class = (TextView)convertView.findViewById(R.id.wegroup_item_class);
				h1.des = (TextView)convertView.findViewById(R.id.wegroup_item_des);
				h1.price = (TextView)convertView.findViewById(R.id.wegroup_item_price);
				h1.remainingtime = (TextView)convertView.findViewById(R.id.wegroup_item_remainingtime);
				h1.numofpeople = (TextView)convertView.findViewById(R.id.wegroup_item_numofpeople);
				convertView.setTag(h1);
			}
			
		} else {  
            switch (type) {  
            case DEFAULT_TYPE:  
                h1 = (ViewHolder_default) convertView.getTag();  
                break;  
            case HEADER_TYPE:  
                h0 = (ViewHolder_HeaderView) convertView.getTag();  
                break;  
            }  
        }  
        // …Ë÷√◊ ‘¥  
        switch (type) {  
        case DEFAULT_TYPE:  
        	initDefaultView(h1,position);
        	convertView.setOnClickListener(listener);
            break;  
        case HEADER_TYPE:
        	headerView(h0,position);
        	
            break; 
        }  
  
		return convertView;
	}
	private void headerView(ViewHolder_HeaderView h0,int position) {
		LinearLayout.LayoutParams imglayout = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, (int) (context.getWidth()* 360 / 720));
		h0.img.setLayoutParams(imglayout);
	}


	private void initDefaultView(ViewHolder_default h1, int position) {
		h1.item_class.setText(data.get(position).get("class").toString());
		
	}


	@Override
	public int getCount() {	
		return data.size();
	}
	@Override
	public Object getItem(int position) {
		
		return data.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	
	final static class ViewHolder_default {
		TextView item_class;
		TextView des;
		TextView price ;
		ImageView ic ;
		TextView remainingtime ;
		TextView numofpeople ;
	}
    final static class ViewHolder_HeaderView{
		View img;
    }
   
}
