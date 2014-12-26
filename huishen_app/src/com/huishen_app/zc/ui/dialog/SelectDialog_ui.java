package com.huishen_app.zc.ui.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.dialog.adapter.DialogItemSelectInterface;
import com.huishen_app.zc.ui.dialog.adapter.MySimpleAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class SelectDialog_ui extends Dialog implements OnItemClickListener {

	public final static String textname = "itemvalue";
	// 两个组件
	private TextView title;
	private ListView listview;

	// value
	private String titlename = "";
	private List<String> listvalue;

	// 数据信息
	private List<Map<String, String>> list;

	//
	public Activity activity;
	public View fatherview;

	// 选择后的回调函数
	private DialogItemSelectInterface select;

	public SelectDialog_ui(View fatherview, Activity activity,
			String titlename, List<String> listvalue,
			DialogItemSelectInterface select) {
		super(activity, R.style.dataselectstyle);
		this.listvalue = listvalue;
		this.select = select;
		this.activity = activity;
		this.fatherview = fatherview;
		this.titlename = titlename;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_select_lay);

		// 获取组件
		title = (TextView) findViewById(R.id.dialog_select_title);
		listview = (ListView) findViewById(R.id.dialog_select_listview);
		title.setText(titlename);
		// 初始化数据
		initdata();
		//设置单选模式
		listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		// 注册监听器
		listview.setOnItemClickListener(this);

//		setCancelable(false);
	}

	private void initdata() {
		// 数据对象
		list = new ArrayList<Map<String, String>>();

		// 定义资源
		String[] from = { textname };
		int[] to = { R.id.dialog_dataselect_text };

		// 循环添加信息
		HashMap<String, String> map = null;
		for (int index = 0; listvalue != null && index < listvalue.size(); ++index) {
			map = new HashMap<String, String>();
			map.put(textname, listvalue.get(index));
			list.add(map);
		}

		MySimpleAdapter simpleAdapter = new MySimpleAdapter(getContext(), list,
				R.layout.dialog_dataselect_item, from, to);
		listview.setAdapter(simpleAdapter);
	}

	@Override
	public void onBackPressed() {
		try {
			activity.onBackPressed();
		} catch (Exception e) {
		}
		try {
			dismiss();
		} catch (Exception e) {
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ListView listView = (ListView) parent;

		HashMap<String, Object> map = (HashMap<String, Object>) listView
				.getItemAtPosition(position);
		Object type = map.get(textname);

		// 完成选择
		select.finish_select(fatherview, position, type);
		dismiss();
	}

}