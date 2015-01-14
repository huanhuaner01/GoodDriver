package com.huishen_app.zc.ui;


import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.ui.fragment.JXDetailFragment;

import android.view.KeyEvent;
import android.view.View;
/**
 * 教练详情
 * @author zhanghuan
 *
 */
public class JXDetailActivity extends BaseActivity {
    

	@Override
	protected void findViewById_Init() {
		setContentView(R.layout.activity_jxdetail);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new JXDetailFragment(this)).commit();
	
	}

	@Override
	protected void initView() {	
	}

	@Override
	protected void initData() {	
	}
	/**
	 * 通用返回按钮
	 * 
	 * @param v
	 */
	public void backIntent(View v) {
		finish();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 
        if (keyCode == KeyEvent.KEYCODE_BACK
                 && event.getRepeatCount() == 0) {
             finish();
             return true;
         }
         return super.onKeyDown(keyCode, event);
     }




}
