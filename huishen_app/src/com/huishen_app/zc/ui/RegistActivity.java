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
		title.setText("新用户注册");
	}

	@Override
	protected void initData() {
		
	}
	/**
	 * 通用返回按钮
	 * 
	 * @param v
	 */
	public void backIntent(View v) {
		finish();
	}
	/**
	 * 提交
	 * 
	 * @param v
	 */
	public void regist(View v) {
		if(realname.getText().toString().equals("")){
			DisPlay("真实姓名不能为空",true);
			return ;
		}
		if(name.getText().toString().equals("")){
			DisPlay("用户名不能为空",true);
			return ;
		}
		if(idnumber.getText().toString().equals("")){
			DisPlay("身份证号不能为空",true);
			return ;
		}
		if(email.getText().toString().equals("")){
			DisPlay("邮箱不能为空",true);
			return ;
		}
		if(tel.getText().toString().equals("")){
			DisPlay("电话号码不能为空",true);
			return ;
		}
		if(password.getText().toString().equals("")){
			DisPlay("密码不能为空",true);
			return ;
		}
		if(!password.getText().toString().equals(passwordcheck.getText().toString())){
			DisPlay("两次密码不同",true);
			return ;
		}
		if(!MyUtil.isRealName(realname.getText().toString())){
			DisPlay("真实姓名格式不对，请输入2~8个中文字符",true);
			return ;
		}
		if(!MyUtil.isUserName(name.getText().toString())){
			DisPlay("用户名格式不对，请输入4~20个字符",true);
			return ;
		}
		if(!VerifyIdCard.verify(idnumber.getText().toString())){
			DisPlay("身份证号格式不对",true);
			return ;
		}
		if(!MyUtil.isEmail(email.getText().toString())){
			DisPlay("邮箱格式不正确",true);
			return ;
		}
		if(!MyUtil.isTel(tel.getText().toString())){
			DisPlay("电话号码格式不正确",true);
			return ;
		}
		//hhah
		CheckCommit();
	}
	
	
	public void CheckCommit(){
		if(!NetUtil.isNetworkConnected(this)){
			Toast.makeText(this, "网络未连接", Toast.LENGTH_SHORT).show();
			return ;
		}
		// 登录6s超时 且只收一条消息
		HanderListObject loginhander = new HanderListObject(6, true) {
			public void handleMessage(Message msg) {
			
			if (msg.what == 11) {
					
						Log.i(TAG,msg.obj.toString());
						RegistActivity.this.DisPlay("注册成功", true);
						finish();
				} else {
					Toast.makeText(RegistActivity.this, "注册失败"+ msg.obj,
							Toast.LENGTH_SHORT).show();
				}
                
				// 关闭
				if (loading != null) {
					loading.dismiss();
					loading = null;
				}
			}
		};
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 初始化参数
        //用户名
		Map<String, Object> nameparam = new HashMap<String, Object>();
		nameparam.put("username", name.getText().toString());
		nameparam.put("operateurl", getOperateURL(R.string.webbaseurl,
				R.string.regist_url_checkusername));
		nameparam.put("encoding", getStringValue(R.string.encoding));
//		param.put("mobileFlag",MyUtil.getUuid());
		//身份证号
		Map<String, Object> idparam = new HashMap<String, Object>();
		idparam.put("licenceCode", idnumber.getText().toString());
		idparam.put("operateurl", getOperateURL(R.string.webbaseurl,
				R.string.regist_url_checkidnumber));
		idparam.put("encoding", getStringValue(R.string.encoding));
//		param.put("mobileFlag",MyUtil.getUuid());
		//邮箱
		Map<String, Object> emailparam = new HashMap<String, Object>();
		emailparam.put("email", email.getText().toString());
		emailparam.put("operateurl", getOperateURL(R.string.webbaseurl,
				R.string.regist_url_checkemail));
		emailparam.put("encoding", getStringValue(R.string.encoding));
//		param.put("mobileFlag",MyUtil.getUuid());
		//提交
		Map<String, Object> commitparam = new HashMap<String, Object>();
		/**
		 * （email,password,(name真实姓名),licenceCode身份证号,sex=(1(男),2(女)),phone,address,username(用户名)）
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
		//添加进入列表
		list.add(nameparam);
		list.add(idparam);
		list.add(emailparam);
		list.add(commitparam);
		// 新建操作对象
		RegistThread login = new RegistThread(loginhander, list);

		// 注册监听
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
