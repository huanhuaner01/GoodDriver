package com.huishen_app.zc.ui;

import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zh.util.AndroidUtil;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProcessActivity extends BaseActivity {
    private TextView title ;
    private ImageView process ;
	
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

	@Override
	protected void findViewById_Init() {
		setContentView(R.layout.activity_process);
		this.title = (TextView)findViewById(R.id.header_title);
		this.process = (ImageView)findViewById(R.id.process_img);
	}

	@Override
	protected void initView() {
		this.title.setText("学车流程");
		this.process.setLayoutParams(AndroidUtil.getImageScaleParams(this, R.drawable.process)) ;
	}

	@Override
	protected void initData() {
	
	}
}
