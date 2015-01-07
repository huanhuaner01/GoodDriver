package com.huishen_app.zc.ui.dialog;

import java.util.ArrayList;
import java.util.HashMap;

import com.huishen_app.zc.ui.R;
import com.huishen_app.zh.util.AndroidUtil;
import com.huishen_app.zh.util.TextStyleUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MessageDialog_ui extends Dialog implements View.OnClickListener{
	//对话框拥有的组件
	private TextView title ; //标题 
	private ImageView cancel ;   //取消按钮
	private TextView message ; //消息内容
	public Activity activity;  //父组件所在的Activity
	private String titlename ,content;
	private boolean isluck ;
	private int type = 0; //为1表示显示消息列表
	private LinearLayout lay ;
	private ArrayList<HashMap<String ,String>> array ;
	
	public MessageDialog_ui(Activity activity ,String titlename ,boolean isluck ,String content) {
		super(activity,R.style.dataselectstyle);
		this.activity = activity ;
		this.titlename = titlename ;
		this.isluck = isluck ;
		this.content = content ;
		
	}
	public MessageDialog_ui(Activity activity ,String titlename ,int type,ArrayList<HashMap<String ,String>> array) {
		super(activity,R.style.dataselectstyle);
		this.activity = activity ;
		this.titlename = titlename ;
		this.isluck = false ;
		this.array = array ;
		this.type = 1 ;
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_message_lay);

		// 获取组件
		title = (TextView) findViewById(R.id.dialog_message_title);
		cancel = (ImageView) findViewById(R.id.dialog_message_cancel);
		message = (TextView) findViewById(R.id.dialog_message_tv);
		lay = (LinearLayout) findViewById(R.id.dialog_message_lay);
        init();
//		setCancelable(false);//取消back键的监听
	}
	
	private void init() {
		this.title.setText(this.titlename) ;
		this.cancel.setOnClickListener(this);
		switch(type){
		
		case 0 :
		if(isluck){ 
			message.setText("恭喜您获得");
			message.append(TextStyleUtil.getTextAppearanceSpan(this.activity,this.content, 1.0f));
			message.append("奖励") ;
		}else{
			message.setText(this.content);
		}
		break ;
		case 1:
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			this.lay.setLayoutParams(lp);
			for(int i = 0 ; i<array.size() ; i++){
			message.append(array.get(i).get("value"));
			message.append("\n");
			}
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
