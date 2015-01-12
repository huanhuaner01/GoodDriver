package com.huishen_app.zc.ui.fragment;

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
import com.huishen_app.zc.ui.base.BaseActivity;

/**
 * 带图片的文本列表
 * @author zhanghuan
 *
 */
@SuppressLint("InflateParams") 
public class ImageListFragment extends BaseFragment {
	private View RootView ;  //根组件
	private TextView title ; //标题
	private TextView des ; //简介
	private Button back  ; //返回键
	private NoScrollListView list ; //列表
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
			RootView = inflater.inflate(R.layout.fragment_list, null);
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
		
	}
	
	/**
	 * 设置内容文本
	 */
	private void setDes(){
		
	}
	
	/**
	 * 设置内容列表
	 */
	private void setList(){
		
	}
    
}
