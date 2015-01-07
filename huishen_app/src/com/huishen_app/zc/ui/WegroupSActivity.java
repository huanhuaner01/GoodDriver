package com.huishen_app.zc.ui;

import java.util.Map;

import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.ui.fragment.ConfirmInfoFragment;
import com.huishen_app.zc.ui.fragment.ConfirmOrderFragment;
import com.huishen_app.zc.ui.fragment.TipFragment;
import com.huishen_app.zc.ui.fragment.WeGroupSuFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

public class WegroupSActivity extends BaseActivity {
	private WeGroupSuFragment wesu ;
	private ConfirmInfoFragment confirminfo ;
	private ConfirmOrderFragment confirmorder ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wegroup_s);
		if (savedInstanceState == null) {
			    wesu = new WeGroupSuFragment(this ,0) ;
			    FragmentManager fm = this.getSupportFragmentManager();  
		        FragmentTransaction tx = fm.beginTransaction();  
		        tx.add(R.id.container, wesu,"wesu");  
		        tx.commit();
		}
	}
	@Override
	protected void findViewById_Init() {
		
	}

	@Override
	protected void initView() {
		
	}

	@Override
	protected void initData() {
		
	}
	
	
	@Override
	public void switchcenter(Object tag ,Map<String, Object> param) {
		FragmentManager fm = this.getSupportFragmentManager();  
        FragmentTransaction tx = fm.beginTransaction();
		   switch((Integer)tag){
		   case 0:

			   confirminfo = new ConfirmInfoFragment(this ,1);
		    	tx.setTransition(FragmentTransaction.TRANSIT_UNSET);
		        tx.hide(wesu);  
		        tx.add(R.id.container, confirminfo, "TWO");
		        tx.addToBackStack(null);  
		        
	        break ;
		   case 1:
				confirmorder = new ConfirmOrderFragment(this,2);
		        tx.setTransition(FragmentTransaction.TRANSIT_UNSET);
		        tx.hide(confirminfo);   
		        tx.add(R.id.container, confirmorder, "confirmorder");
		        tx.addToBackStack(null); 
			   break ;
		   case 2:
			   tx.hide(confirmorder);
			   tx.add(R.id.container, new TipFragment(this),"tipfragment"); 
			   tx.addToBackStack(null);
			   break ;
			   
		   }
		   tx.commit();
	}
	/**
	 * 通用返回按钮
	 * 
	 * @param v
	 */
	public void backIntent(View v) {
		finish();
	}
}
