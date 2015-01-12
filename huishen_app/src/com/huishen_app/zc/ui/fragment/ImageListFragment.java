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
	 * ���������ı�
	 */
	private void setDes(){
		
	}
	
	/**
	 * ���������б�
	 */
	private void setList(){
		
	}
    
}
