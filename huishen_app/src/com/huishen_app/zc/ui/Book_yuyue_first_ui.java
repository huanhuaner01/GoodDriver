package com.huishen_app.zc.ui;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.ui.dialog.SelectDialog_ui;
import com.huishen_app.zc.ui.dialog.adapter.DialogItemSelectInterface;

public class Book_yuyue_first_ui extends BaseActivity implements
		DialogItemSelectInterface {

	/** 在线预约的3中类型 */
	private EditText book_date, book_kemu;

	/** 多个选择对话框 */
	private SelectDialog_ui kemu;
	private TextView title ;
	@Override
	protected void findViewById_Init() {
		setContentView(R.layout.book_yuyue_first_lay);
		book_date = (EditText) findViewById(R.id.book_yuyue_date);
		book_kemu = (EditText) findViewById(R.id.book_yuyue_kemu);
		title = (TextView) findViewById(R.id.header_title);

	}

	@Override
	protected void initView() {
		title.setText("预约培训");
	}

	@Override
	protected void initData() {

	}

	/**
	 * 下一步
	 * 
	 * @param s
	 */
	public void nextstep(View s) {

		String date, kumu;
		date = checkValue(book_date, "请选择时间");
		if (date.length() == 0)
			return;

		kumu = checkValue(book_kemu, "请选择科目");
		if (kumu.length() == 0)
			return;

		try {
			Intent intent = new Intent();
			intent.setClass(this, Book_dateselect_ui.class);
			intent.putExtra("book_date", date);
			intent.putExtra("book_kemu", kumu);
			intent.putExtra("book_kemu_id", kumu.equalsIgnoreCase("科目二") ? "1"
					: "2");
        
			startActivity(intent);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void opendataselect(View view) {
			List<String> value = new ArrayList<String>();
			value.add("科目二");
			value.add("科目三");

			kemu = new SelectDialog_ui(view, this, "预约科目", value, this);
			kemu.show();
		
	}

	// 时间获取函数
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		try {
			if (requestCode == open_result) {
				if (resultCode == RESULT_OK) {
					String structdate = data.getExtras()
							.getString("selectDate");
					if (structdate != null && structdate.trim().length() > 0)
						book_date.setText(structdate);
				}
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void finish_select(View view, int postion, Object value) {
		
			book_kemu.setText(value.toString());
			book_kemu.setTag(value.toString().equalsIgnoreCase("科目二") ? "1"
					: "2");
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
