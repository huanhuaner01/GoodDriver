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
 * �Զ������ݶԻ���
 * @author zhanghuan
 *
 */
public class ContentDialog_ui extends Dialog implements View.OnClickListener {
	//�Ի���ӵ�е����
	private TextView title; //����
	private ImageView cancel ;   //ȡ����ť
	private ListView list ; //�����б�
	private LinearLayout head ; //�б��ͷ
	private SimpleAdapter listAdapter ;  //�б�������
	
	private String titlename = "����" ; //Ĭ�ϱ���
	
	//
	public Activity activity;  //��������ڵ�Activity
//	public View fatherview;  //�����

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

		// ��ȡ���
		title = (TextView) findViewById(R.id.dialog_content_title);
		cancel = (ImageView) findViewById(R.id.dialog_content_cancel);
		list = (ListView) findViewById(R.id.dialog_content_list);
////		head = (LinearLayout) findViewById(R.id.list_head);
		title.setText(titlename);
//		// ��ʼ������
		initdata();

//		setCancelable(false);//ȡ��back���ļ���
	}
    
	/**
	 * ���ñ�ͷ������
	 * @param hide true���أ�falseȡ������
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
	
	/**��������б�**/
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
