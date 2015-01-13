package com.huishen_app.zc.ui.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zh.netTool.AppController;
import com.huishen_app.zh.util.TextStyleUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * ��ʾ�ı������ͼƬ�б��������
 * @author zhanghuan
 *
 */
public class ImageTvListAdapter extends BaseAdapter {
	private List<Map<String ,Object>> data ;
	private Context context ;
	private ImageLoader imageLoader ;
	private LayoutInflater inflater;
    private final int TYPE_TEXT = 0 ;
    private final int TYPE_IMAGE = 1 ;
	public ImageTvListAdapter(Context context ,List<Map<String ,Object>> data ) {
		if(data == null){
			data = new ArrayList<Map<String ,Object>>();
		}
		this.data = data ;
		this.context = context ;
		imageLoader = AppController.getInstance().getImageLoader();
		inflater = LayoutInflater.from(context) ;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	// ÿ��convert view������ô˷�������õ�ǰ����Ҫ��view��ʽ
	@Override
	public int getItemViewType(int position) {
		int p = position;
		if (p == 0)
			return TYPE_TEXT;
		else 
			return TYPE_IMAGE ;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}
	@Override
	public View getView(int position, View view, ViewGroup viewgroup) {
		TextViewHolder tvHolder = null ;
		ImageViewHolder imageHolder = null ;
		int type = getItemViewType(position);
		if(view == null){
			// ����ǰ�������ʽ��ȷ��new�Ĳ���  
            switch (type) { 
            case TYPE_TEXT:
            	view = inflater.inflate(R.layout.list_tv_item,
        				null);
            	tvHolder = new TextViewHolder();
     
            	view.setTag(tvHolder) ;
            	break ;
            case TYPE_IMAGE:
            	view = inflater.inflate(R.layout.list_image_item,
        				null);
            	imageHolder = new ImageViewHolder();
            	imageHolder.image = (NetworkImageView)view.findViewById(R.id.list_imageitem_image);
            	view.setTag(imageHolder);
            	break ;
            }
		}
            else{
            	switch (type) {
            	case TYPE_TEXT:
            		tvHolder = (TextViewHolder)view.getTag();
            		break ;
            	case TYPE_IMAGE:
            		imageHolder = (ImageViewHolder)view.getTag() ;
            		break ;
            	}
            }
		// ������Դ
		switch(type){
		case TYPE_TEXT:
	       	tvHolder.tv = (TextView)view.findViewById(R.id.item_tv);
        	tvHolder.tv.setText(TextStyleUtil.getTextAppearanceSpan(this.context ,"��        �䣺","7��" ,R.color.book_imitate_textcolornew));
        	tvHolder.tv.append("\n") ;
        	tvHolder.tv.append(TextStyleUtil.getTextAppearanceSpan(this.context ,"��        �䣺","10��" ,R.color.book_imitate_textcolornew));
        	tvHolder.tv.append("\n") ;
        	tvHolder.tv.append(TextStyleUtil.getTextAppearanceSpan(this.context ,"����֤�ţ�","��00268" ,R.color.book_imitate_textcolornew));
        	tvHolder.tv.append("\n") ;
        	tvHolder.tv.append(TextStyleUtil.getTextAppearanceSpan(this.context ,"�������ܣ�\n","  �����ǳ���" ,R.color.book_imitate_textcolornew));
    		break ;
    	case TYPE_IMAGE:
    		imageHolder = (ImageViewHolder)view.getTag() ;
    		if(data.get(position).get("photo").toString()!= null && !data.get(position).get("photo").toString().equals("")){
    			imageHolder.image.setDefaultImageResId(R.drawable.jl_test_icon);
    			imageHolder.image.setErrorImageResId(R.drawable.ic_launcher);
    			imageHolder.image.setImageUrl(data.get(position).get("photo").toString(), imageLoader);
    		}
    		break ;
		}
		return view ;
		
	}
	/**
	 * ֻ���ı����б���
	 * @author zhanghuan
	 *
	 */
	public class TextViewHolder {
		TextView tv;
	}
	
	/**
	 * ֻ��ͼƬ���б���
	 * @author zhanghuan
	 * 
	 */
	public class ImageViewHolder {
		NetworkImageView image;
	}

}
