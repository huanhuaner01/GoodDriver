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
	//�Ի���ӵ�е����
	private TextView title ; //���� 
	private ImageView cancel ;   //ȡ����ť
	private TextView message ; //��Ϣ����
	public Activity activity;  //��������ڵ�Activity
	private String titlename ,content;
	private boolean isluck ;
	private int type = 0; //Ϊ1��ʾ��ʾ��Ϣ�б�
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

		// ��ȡ���
		title = (TextView) findViewById(R.id.dialog_message_title);
		cancel = (ImageView) findViewById(R.id.dialog_message_cancel);
		message = (TextView) findViewById(R.id.dialog_message_tv);
		lay = (LinearLayout) findViewById(R.id.dialog_message_lay);
        init();
//		setCancelable(false);//ȡ��back���ļ���
	}
	
	private void init() {
		this.title.setText(this.titlename) ;
		this.cancel.setOnClickListener(this);
		switch(type){
		
		case 0 :
		if(isluck){ 
			message.setText("��ϲ�����");
			message.append(TextStyleUtil.getTextAppearanceSpan(this.activity,this.content, 1.0f));
			message.append("����") ;
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
