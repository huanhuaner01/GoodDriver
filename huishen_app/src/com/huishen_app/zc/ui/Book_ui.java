package com.huishen_app.zc.ui;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.huishen_app.zc.ui.base.BaseActivity;

public class Book_ui extends BaseActivity {

	private ImageView book_conten_img;

	private ImageButton train_bt, imitate_bt;

	protected void findViewById_Init() {
		setContentView(R.layout.book_lay);

		// 初始化组件变量
		book_conten_img = (ImageView) findViewById(R.id.book_content_img);

		train_bt = (ImageButton) findViewById(R.id.book_menu_train);
		imitate_bt = (ImageButton) findViewById(R.id.book_menu_imitate);

	}

	protected void initView() {

		// 行布局
		int width = getWidth() > 100 ? getWidth() : 200;
		LinearLayout.LayoutParams imglayout = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, (int) (width * 58 / 72) + 1);
		// 设置图片大小
		book_conten_img.setLayoutParams(imglayout);

		// menu 大小
		LinearLayout.LayoutParams menulayout = new LinearLayout.LayoutParams(
				(int) (width * 0.3), (int) (width * 0.3));

		// 设置图片大小
		train_bt.setLayoutParams(menulayout);
		imitate_bt.setLayoutParams(menulayout);

	}

	protected void initData() {

	}

	public void test1(View s) {
		Intent intent = new Intent(this, Book_yuyue_first_ui.class);
		startActivity(intent);

	}
	
	public void test2(View s) {
		Intent intent = new Intent(this, Book_imitate_ui.class);
		startActivity(intent);

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
