package com.huishen_app.zc.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.huishen_app.all.mywidget.NoScrollListView;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.adapter.ImageListAdapter;
import com.huishen_app.zc.ui.adapter.JL_Search_Adapter;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zh.util.TextStyleUtil;

/**
 * ��ͼƬ���ı��б�
 * @author zhanghuan
 *
 */
@SuppressLint("InflateParams") 
public class ImageListFragment extends BaseFragment {
	private View RootView ;  //�����
	private TextView title ; //����
	private TextView des ; //���
	private Button back  ; //���ؼ�
	private NoScrollListView list ; //�б�
	private List<Map<String, Object>> listview_date;
	private ImageListAdapter adapter;
	private String result  ,titleStr;
	private String url ;
	public int BACK_FRAGMENT = 0 ;
	public int BACK_ACTIVITY =1 ;
	private int backmodel = 0;
	
	public ImageListFragment(BaseActivity father ,String title,String url ,int backmodel) {
		super(father);
		this.titleStr = title ;
		this.url = url ;
		this.backmodel = backmodel ;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		    
		try{
			RootView = inflater.inflate(R.layout.fragment_imagelist, null);
			registView();
			initView();
		  } catch (Exception e) {
		}
		return RootView;
	}

	private void registView() {
		title = (TextView)RootView.findViewById(R.id.header_title) ;
		des = (TextView)RootView.findViewById(R.id.f_list_content) ;
		list = (NoScrollListView)RootView.findViewById(R.id.f_list_list);
		back = (Button)RootView.findViewById(R.id.header_back) ;
	}
    
	private void initView() {
	   this.title.setText(titleStr) ;

	   getWebData();
	   this.back.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			if(backmodel== BACK_FRAGMENT){
				 FragmentManager fm = getFragmentManager();  
			     fm.popBackStack();
			}
		}
		   
	   });
	}
	
	private void getWebData(){
		setDes();
		setList();
	}
	
	/**
	 * ���������ı�
	 */
	private void setDes(){
		//�������
		des.setText(TextStyleUtil.getTextAppearanceSpan(this.father ,"��        �䣺","7��" ,R.color.book_imitate_textcolornew));
		des.append("\n") ;
		des.append(TextStyleUtil.getTextAppearanceSpan(this.father ,"��        �䣺","10��" ,R.color.book_imitate_textcolornew));
		des.append("\n") ;
		des.append(TextStyleUtil.getTextAppearanceSpan(this.father ,"����֤�ţ�","��00268" ,R.color.book_imitate_textcolornew));
		des.append("\n") ;
		des.append(TextStyleUtil.getTextAppearanceSpan(this.father ,"�������ܣ�\n","  �����ǳ���" ,R.color.book_imitate_textcolornew));
	}
	
	/**
	 * ���������б�
	 */
	private void setList(){
		// ������Դ1
		String[] from = {};
		int[] to = {};
		listview_date = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("photo","http://preview.quanjing.com/mf025/mf700-00609711.jpg");
		listview_date.add(map);
		map = new HashMap<String, Object>();
		map.put("photo","http://img5.imgtn.bdimg.com/it/u=3793805207,3272353858&fm=21&gp=0.jpg");
		listview_date.add(map);
		map = new HashMap<String, Object>();
		map.put("photo","http://pic1.win4000.com/pic/1/ac/1fb0489694.jpg");
		listview_date.add(map);
		map = new HashMap<String, Object>();
		map.put("photo","http://preview.quanjing.com/mf025/mf700-00609710.jpg");
		listview_date.add(map);
		map = new HashMap<String, Object>();
		map.put("photo","http://preview.quanjing.com/mf025/mf700-00609701.jpg");
		listview_date.add(map);

		adapter = new ImageListAdapter(this.father, listview_date,
				R.layout.list_image_item, from, to);

		list.setAdapter(adapter);     
	}
    
}
