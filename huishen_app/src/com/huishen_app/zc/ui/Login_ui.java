package com.huishen_app.zc.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huishen_app.all.alarmservice.HanderListObject;
import com.huishen_app.zc.httpclient.HuishenHttpClient;
import com.huishen_app.zc.operate_thread.LoginThread;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.ui.dialog.LoadingDialog_ui;
import com.huishen_app.zc.util.MyUtil;
import com.huishen_app.zh.netTool.NetUtil;

public class Login_ui extends BaseActivity {

	public final static int LOGINCODE = 1000;
	public final static String LoginstateName = "loginState";

	/** ��¼��־ */
	private boolean logintrage = false,remenberState=true;

	/** ���ضԻ��� */
	private LoadingDialog_ui loading;

	/** �û��� ������ */
	private EditText user, password;
	/** ��������,ע���ı��� */
	private TextView forget,regist ;
	private View remenber ,login_bg; //��ס����,��¼����ͼƬ
	private String mobileFlag;//�����

	@Override
	protected void findViewById_Init() {
		setContentView(R.layout.login_lay);

		user = (EditText) findViewById(R.id.login_input_user);
		password = (EditText) findViewById(R.id.login_input_password);
		forget = (TextView) findViewById(R.id.login_forgetpass);
		regist = (TextView) findViewById(R.id.login_regist);
		remenber = findViewById(R.id.login_remember);
		login_bg = findViewById(R.id.login_bg_v) ;
	}

	@Override
	protected void initView() {
		//ȡ���ļ������״̬�����Ϊ1��ʾ���棬������ʾû�б���
		try{
		if(readString("passremenber").equals("1")){
			remenberState = true ;
		}
		}catch(Exception e){
			remenberState = false ;
		}
		if(this.remenberState){
			user.setText(readString("username"));
			password.setText(readString("password"));
			remenber.setBackgroundResource(R.drawable.all_select_icon);
		}
       remenber.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
			if(remenberState){
				remenberState = false ;
				remenber.setBackgroundResource(R.drawable.all_unselect_icon);
			}else{
				remenberState = true ;
				remenber.setBackgroundResource(R.drawable.all_select_icon);
			}
		}
    	   
       });
       regist.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			Intent i = new Intent(Login_ui.this,RegistActivity.class);
			startActivity(i);
		}
    	   
       });
  	 LinearLayout.LayoutParams r_params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, this.getWidth() * 464/720);
   	 login_bg.setLayoutParams(r_params);
	}

	@Override
	protected void initData() {
         forget.setText(Html.fromHtml("<u>�һ�����?</u>"));
 
     	 
	}

	/** �ر� */
	@Override
	public void finish() {
		Intent data = new Intent();
		data.putExtra(LoginstateName, logintrage);
		setResult(Login_ui.LOGINCODE, data);
		super.finish();
	}

	/**
	 * ͨ�÷��ذ�ť
	 * 
	 * @param v
	 */
	public void backIntent(View v) {
		finish();
	}

	/** ��¼ */
	public void logining(View l_v) {

		/**
		 * {"DrivingLicenceType":"B2","Testcount":0,
		 * "address":"����","coachId":12, "coachname":"��ƽ·",
		 * "lessonInfocount":0,"licenceCode":"510824199210207794",
		 * "path":"/drivingSchool/attachment/head/2014110715530401412670.jpg",
		 * "phone":"123456","school":"����ѵ����",
		 * "sex":true,"stuId":11,"stuname":"����"}
		 */
		if(!NetUtil.isNetworkConnected(this)){
			Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
			return ;
		}
		String operurl = getOperateURL(R.string.webbaseurl,
				R.string.loginurl_new);
		// ��¼10s��ʱ ��ֻ��һ����Ϣ
		HanderListObject loginhander = new HanderListObject(5, true) {
			@Override
			public void handleMessage(Message msg) {
				
				if(msg.what == 11) {
					// ��¼��Ϣ
					JSONObject jobj;
					try{
						
						jobj = new JSONObject(msg.obj.toString());
						Log.i(TAG, jobj.toString());
						int status = jobj.getInt("status");
						if(status == 0){
							Toast.makeText(Login_ui.this,jobj.getString("info") ,
									Toast.LENGTH_SHORT).show();
						}else{
						saveData(jobj.getJSONObject("info"));
						
						saveString("username",user.getText().toString());
						saveString("password",remenberState?password.getText().toString():"");
						saveString("remenberState",remenberState?"1":"0");
						saveString("mobileFlag",mobileFlag);
						// �������
						Main_fragment_ui.setLoginhttpclient(HuishenHttpClient
								.getHttpClient());

						logintrage = true;
						Login_ui.this.finish();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					// {"addr":"���ӿƴ�","id":1,"licence":"C1","name":"�",
					// "path":"/attchment/images/IMG_2014092216410302242395.jpg",
					// "phone":"13403150491","teacherId":"00000001"}
					// �����û�ID
				} else {
					Toast.makeText(Login_ui.this, msg.obj + "",
							Toast.LENGTH_SHORT).show();
				}

				// �ر�
				if (loading != null) {
					loading.dismiss();
					loading = null;
				}
			}
		};

		// ��Ч�Լ��
		String user_t, password_t;
		user_t = checkValue(user);
		if (user_t.length() == 0)
			return;
		password_t = checkValue(password);
		if (password_t.length() == 0)
			return;

		// ��ʼ������
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("username", user_t);
		param.put("password", password_t);
		mobileFlag= MyUtil.getUuid();
		param.put("mobileFlag", mobileFlag);
        
		param.put("operateurl", operurl);
		param.put("encoding", getStringValue(R.string.encoding));
		// �½���������
		LoginThread login = new LoginThread(loginhander, param);

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
