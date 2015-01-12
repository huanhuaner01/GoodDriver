package com.huishen_app.zc.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;

import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.ui.fragment.JLDetailFragment;


public class JLDetailActivity extends BaseActivity {
   private JLDetailFragment f_jldetail ;

	@Override
	protected void findViewById_Init() {
		setContentView(R.layout.activity_jldetail);
		f_jldetail = new JLDetailFragment(this ,0) ;
		    FragmentManager fm = this.getSupportFragmentManager();  
	        FragmentTransaction tx = fm.beginTransaction();  
	        tx.add(R.id.container, f_jldetail,"f_jldetail");  
	        tx.commit();
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * 通用返回按钮
	 * 
	 * @param v
	 */
	public void backIntent(View v) {
		finish();
	}
	
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		 
//        if (keyCode == KeyEvent.KEYCODE_BACK
//                 && event.getRepeatCount() == 0) {
//             finish();
//             return true;
//         }
//         return super.onKeyDown(keyCode, event);
//     }
}
