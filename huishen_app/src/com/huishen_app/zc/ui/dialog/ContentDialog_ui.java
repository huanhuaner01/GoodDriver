package com.huishen_app.zc.ui.dialog;

import com.huishen_app.zc.ui.R;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * 自定义内容对话框
 * @author zhanghuan
 *
 */
public class ContentDialog_ui extends Dialog implements View.OnClickListener {
	//对话框拥有的组件
	private TextView title; //标题
	private ImageView cancel ;   //取消按钮
	private ListView list ; //内容列表
	private LinearLayout head ; //列表表头
	private SimpleAdapter listAdapter ;  //列表适配器
	
	private String titlename = "标题" ; //默认标题
	
	//
	public Activity activity;  //父组件所在的Activity
//	public View fatherview;  //父组件

	public ContentDialog_ui(Activity activity,
			String titlename, SimpleAdapter listAdapter) {
		super(activity, R.style.dataselectstyle);
		this.listAdapter = listAdapter ;
		this.titlename = titlename;
		this.activity = activity ;
//		this.fatherview = fatherview ;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_content_lay);

		// 获取组件
		title = (TextView) findViewById(R.id.dialog_content_title);
		cancel = (ImageView) findViewById(R.id.dialog_content_cancel);
		list = (ListView) findViewById(R.id.dialog_content_list);
////		head = (LinearLayout) findViewById(R.id.list_head);
		title.setText(titlename);
//		// 初始化数据
		initdata();

//		setCancelable(false);//取消back键的监听
	}
    
	/**
	 * 设置表头的隐藏
	 * @param hide true隐藏，false取消隐藏
	 */
//	public void hideHead(boolean hide){
//		if(hide){
//			head.setVisibility(View.GONE);
//			return ;
//		}
//		head.setVisibility(View.VISIBLE);
//	}
	
	private void initdata() {
		cancel.setOnClickListener(this);
		addAdapter();
	}
    
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
	
	/**添加内容列表**/
	private void addAdapter(){
       if(this.listAdapter == null){
    	   return ;
       }
       try{
       list.setAdapter(listAdapter);
       }catch(Exception e){
    	   e.printStackTrace();
       }
       
	}

@Override
public void onClick(View v) {
	try {
		dismiss();
	} catch (Exception e) {
	}
}

}
