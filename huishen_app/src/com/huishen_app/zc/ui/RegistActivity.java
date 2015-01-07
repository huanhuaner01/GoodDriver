package com.huishen_app.zc.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.huishen_app.all.alarmservice.HanderListObject;
import com.huishen_app.zc.operate_thread.RegistThread;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.ui.dialog.LoadingDialog_ui;
import com.huishen_app.zh.netTool.NetUtil;
import com.huishen_app.zh.util.MyUtil;
import com.huishen_app.zh.util.VerifyIdCard;

import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RegistActivity extends BaseActivity {
   private EditText realname , name,idnumber ,email ,tel ,addr ,password, passwordcheck ;
   private RadioGroup sex ;
   private TextView title ;
	@Override
	protected void findViewById_Init() {
		 setContentView(R.layout.activity_regist);
		 realname = (EditText) findViewById(R.id.regist_edit_realname);
		 name = (EditText) findViewById(R.id.regist_edit_name);
		 idnumber = (EditText) findViewById(R.id.regist_edit_id);
		 email = (EditText) findViewById(R.id.regist_edit_email);
		 tel = (EditText) findViewById(R.id.regist_edit_tel);
		 addr = (EditText) findViewById(R.id.regist_edit_address);
		 password = (EditText) findViewById(R.id.regist_edit_password);
		 passwordcheck = (EditText) findViewById(R.id.regist_edit_psswordcheck);
		 realname = (EditText) findViewById(R.id.regist_edit_realname);
		 sex = (RadioGroup) findViewById(R.id.regist_sex_rdgroup);
		 title = (TextView) findViewById(R.id.header_title);
	}

	@Override
	protected void initView() {
		sex.check(R.id.regist_sex_boy);
		title.setText("���û�ע��");
	}

	@Override
	protected void initData() {
		
	}
	/**
	 * ͨ�÷��ذ�ť
	 * 
	 * @param v
	 */
	public void backIntent(View v) {
		finish();
	}
	/**
	 * �ύ
	 * 
	 * @param v
	 */
	public void regist(View v) {
		if(realname.getText().toString().equals("")){
			DisPlay("��ʵ��������Ϊ��",true);
			return ;
		}
		if(name.getText().toString().equals("")){
			DisPlay("�û�������Ϊ��",true);
			return ;
		}
		if(idnumber.getText().toString().equals("")){
			DisPlay("���֤�Ų���Ϊ��",true);
			return ;
		}
		if(email.getText().toString().equals("")){
			DisPlay("���䲻��Ϊ��",true);
			return ;
		}
		if(tel.getText().toString().equals("")){
			DisPlay("�绰���벻��Ϊ��",true);
			return ;
		}
		if(password.getText().toString().equals("")){
			DisPlay("���벻��Ϊ��",true);
			return ;
		}
		if(!password.getText().toString().equals(passwordcheck.getText().toString())){
			DisPlay("�������벻ͬ",true);
			return ;
		}
		if(!MyUtil.isRealName(realname.getText().toString())){
			DisPlay("��ʵ������ʽ���ԣ�������2~8�������ַ�",true);
			return ;
		}
		if(!MyUtil.isUserName(name.getText().toString())){
			DisPlay("�û�����ʽ���ԣ�������4~20���ַ�",true);
			return ;
		}
		if(!VerifyIdCard.verify(idnumber.getText().toString())){
			DisPlay("���֤�Ÿ�ʽ����",true);
			return ;
		}
		if(!MyUtil.isEmail(email.getText().toString())){
			DisPlay("�����ʽ����ȷ",true);
			return ;
		}
		if(!MyUtil.isTel(tel.getText().toString())){
			DisPlay("�绰�����ʽ����ȷ",true);
			return ;
		}
		//hhah
		CheckCommit();
	}
	
	
	public void CheckCommit(){
		if(!NetUtil.isNetworkConnected(this)){
			Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
			return ;
		}
		// ��¼6s��ʱ ��ֻ��һ����Ϣ
		HanderListObject loginhander = new HanderListObject(6, true) {
			public void handleMessage(Message msg) {
			
			if (msg.what == 11) {
					
						Log.i(TAG,msg.obj.toString());
						RegistActivity.this.DisPlay("ע��ɹ�", true);
						finish();
				} else {
					Toast.makeText(RegistActivity.this, "ע��ʧ��"+ msg.obj,
							Toast.LENGTH_SHORT).show();
				}
                
				// �ر�
				if (loading != null) {
					loading.dismiss();
					loading = null;
				}
			}
		};
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// ��ʼ������
        //�û���
		Map<String, Object> nameparam = new HashMap<String, Object>();
		nameparam.put("username", name.getText().toString());
		nameparam.put("operateurl", getOperateURL(R.string.webbaseurl,
				R.string.regist_url_checkusername));
		nameparam.put("encoding", getStringValue(R.string.encoding));
//		param.put("mobileFlag",MyUtil.getUuid());
		//���֤��
		Map<String, Object> idparam = new HashMap<String, Object>();
		idparam.put("licenceCode", idnumber.getText().toString());
		idparam.put("operateurl", getOperateURL(R.string.webbaseurl,
				R.string.regist_url_checkidnumber));
		idparam.put("encoding", getStringValue(R.string.encoding));
//		param.put("mobileFlag",MyUtil.getUuid());
		//����
		Map<String, Object> emailparam = new HashMap<String, Object>();
		emailparam.put("email", email.getText().toString());
		emailparam.put("operateurl", getOperateURL(R.string.webbaseurl,
				R.string.regist_url_checkemail));
		emailparam.put("encoding", getStringValue(R.string.encoding));
//		param.put("mobileFlag",MyUtil.getUuid());
		//�ύ
		Map<String, Object> commitparam = new HashMap<String, Object>();
		/**
		 * ��email,password,(name��ʵ����),licenceCode���֤��,sex=(1(��),2(Ů)),phone,address,username(�û���)��
		 */
		commitparam.put("email", email.getText().toString());
		commitparam.put("password", password.getText().toString());
		commitparam.put("name", realname.getText().toString());
		commitparam.put("licenceCode", idnumber.getText().toString());
		commitparam.put("sex", sex.getCheckedRadioButtonId() == R.id.regist_sex_boy ?1:0);
		commitparam.put("phone", tel.getText().toString());
		commitparam.put("address", addr.getText().toString());
		commitparam.put("username", name.getText().toString());
		commitparam.put("operateurl", getOperateURL(R.string.webbaseurl,
				R.string.regist_url_submit));
		commitparam.put("encoding", getStringValue(R.string.encoding));
//		param.put("mobileFlag",MyUtil.getUuid());
		//��ӽ����б�
		list.add(nameparam);
		list.add(idparam);
		list.add(emailparam);
		list.add(commitparam);
		// �½���������
		RegistThread login = new RegistThread(loginhander, list);

		// ע�����
		loginhander.addtolisttask(login);

		loading = new LoadingDialog_ui(this, R.style.loadingstyle,
				R.layout.dialog_loading_lay);
		loading.setCancelable(false);
		loading.show();
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
