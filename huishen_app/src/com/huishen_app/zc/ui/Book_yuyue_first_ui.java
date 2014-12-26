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

	/** ����ԤԼ��3������ */
	private EditText book_date, book_kemu;

	/** ���ѡ��Ի��� */
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
		title.setText("ԤԼ��ѵ");
	}

	@Override
	protected void initData() {

	}

	/**
	 * ��һ��
	 * 
	 * @param s
	 */
	public void nextstep(View s) {

		String date, kumu;
		date = checkValue(book_date, "��ѡ��ʱ��");
		if (date.length() == 0)
			return;

		kumu = checkValue(book_kemu, "��ѡ���Ŀ");
		if (kumu.length() == 0)
			return;

		try {
			Intent intent = new Intent();
			intent.setClass(this, Book_dateselect_ui.class);
			intent.putExtra("book_date", date);
			intent.putExtra("book_kemu", kumu);
			intent.putExtra("book_kemu_id", kumu.equalsIgnoreCase("��Ŀ��") ? "1"
					: "2");
        
			startActivity(intent);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void opendataselect(View view) {
			List<String> value = new ArrayList<String>();
			value.add("��Ŀ��");
			value.add("��Ŀ��");

			kemu = new SelectDialog_ui(view, this, "ԤԼ��Ŀ", value, this);
			kemu.show();
		
	}

	// ʱ���ȡ����
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
			book_kemu.setTag(value.toString().equalsIgnoreCase("��Ŀ��") ? "1"
					: "2");
	}
	/**
	 * ͨ�÷��ذ�ť
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
