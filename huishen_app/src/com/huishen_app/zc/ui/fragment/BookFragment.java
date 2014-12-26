package com.huishen_app.zc.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huishen_app.zc.ui.Book_imitate_ui;
import com.huishen_app.zc.ui.Book_yuyue_first_ui;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.base.BaseActivity;

@SuppressLint("ValidFragment")
public class BookFragment extends BaseFragment implements OnClickListener {

	/** 图标对象 */
	private ImageView book_conten_img;

	/** 两个按钮 */
	private ImageButton train_bt, imitate_bt;

	/** 父类对象 */
	private BaseActivity father;
	private TextView city , title ;

	public BookFragment(BaseActivity father) {
		super(father);
		this.father = father;
	}
	@Override
	public void onResume() {
		if(father.readString("city")!= null &&!father.readString("city").equals("")){
			city.setText(father.readString("city").toString());
		}
		super.onResume();
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View book = null;
		try {
			book = inflater.inflate(R.layout.book_lay, null);
			findViewById_Init(book);
			initView(book);
		  } catch (Exception e) {
		}
		return book;
	}

	protected void findViewById_Init(View view) {
		// 初始化组件变量
		book_conten_img = (ImageView) view.findViewById(R.id.book_content_img);

		train_bt = (ImageButton) view.findViewById(R.id.book_menu_train);
		imitate_bt = (ImageButton) view.findViewById(R.id.book_menu_imitate);
		city = (TextView)view.findViewById(R.id.header_city);
		title = (TextView)view.findViewById(R.id.header_title);
		
		train_bt.setOnClickListener(this);
		imitate_bt.setOnClickListener(this);
	}

	protected void initView(View view) {
		if(father.readString("city")!= null &&!father.readString("city").equals("")){
			city.setText(father.readString("city").toString());
		}
//		city.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View arg0) {
//				Intent i = new Intent(father ,LocationActivity.class);
//				father.startActivity(i);
//			}
//			
//		});
		title.setText("在线预约");
		// 行布局
		int width = father.getWidth() > 100 ? father.getWidth() : 200;
		LinearLayout.LayoutParams imglayout = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, width * 58 / 72 + 1);
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

	@Override
	public void onClick(View v) {
		if (v == train_bt) {
			Intent intent = new Intent(father, Book_yuyue_first_ui.class);
			startActivity(intent);
		} else if (v == imitate_bt) {
			Intent intent = new Intent(father, Book_imitate_ui.class);
			startActivity(intent);
		}

	}
}
