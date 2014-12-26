package com.huishen_app.zc.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.huishen_app.all.mywidget.GuaGuaKa;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.util.AndroidUtil;

public class GuaGuaKaFragment extends BaseFragment {
	   private View headerimg ;
	   private TextView title ;
	   private View RootView ;
	   private GuaGuaKa guaguaka ; //¹Î¹Î¿¨×é¼þ
	   private Button back ; //·µ»Ø¼ü
	   
	public GuaGuaKaFragment(BaseActivity father) {
		super(father);
	}

	public GuaGuaKaFragment(BaseActivity father, Object tag) {
		super(father, tag);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		try {
			RootView = inflater.inflate(R.layout.fragment_guaguaka, null);
			regsitView();
			initView();
		  } catch (Exception e) {
		}
		return RootView;
	}


	private void regsitView() {
		headerimg = RootView.findViewById(R.id.gua_header_img);
		title = (TextView)RootView.findViewById(R.id.header_title) ;
		back = (Button)RootView.findViewById(R.id.header_back);
		this.guaguaka = (GuaGuaKa)RootView.findViewById(R.id.guaguaka_v);
	}
	

	private void initView() {
		headerimg.setLayoutParams(AndroidUtil.getImageScaleParams(this.father, R.drawable.guaguaka_header));
		this.title.setText(this.father.getResources().getString(R.string.guaguakafragment_title));
//		//¼àÌý·µ»Ø¼ü
//		this.back.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View arg0) {
//		        FragmentManager fm = getFragmentManager();  
//		        fm.popBackStack();
//			}
//			
//		});
	}
	
	
}
