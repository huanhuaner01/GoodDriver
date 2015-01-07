package com.huishen_app.zc.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huishen_app.all.alarmservice.HanderListObject;
import com.huishen_app.zc.operate_thread.GetHttpResultThread;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.ui.dialog.LoadingDialog_ui;
import com.huishen_app.zc.ui.dialog.SelectDialog_ui;
import com.huishen_app.zc.ui.dialog.adapter.DialogItemSelectInterface;
import com.huishen_app.zh.netTool.NetUtil;
import com.huishen_app.zh.util.DateStruct;

@SuppressLint("SimpleDateFormat")
public class Book_imitate_ui extends BaseActivity implements
		DialogItemSelectInterface {

	private EditText date, kemu;

	private TextView head_title,name,price,idnum,tel;

	private List<String> jz_type;

	private SelectDialog_ui dialog;

	private JSONArray j_array;

	protected void findViewById_Init() {
		setContentView(R.layout.book_imitate_lay);
		head_title = (TextView) findViewById(R.id.header_title);
		name=(TextView)findViewById(R.id.book_imitate_name);
		price = (TextView)findViewById(R.id.book_imitate_price) ;
		idnum = (TextView)findViewById(R.id.book_imitate_idnum);
		tel = (TextView)findViewById(R.id.book_imitate_tel);
		jz_type = new ArrayList<String>();
        
		date = (EditText) findViewById(R.id.book_imitate_txtdate);
		kemu = (EditText) findViewById(R.id.book_imitate_kemu);
	}

	protected void initView() {
		// ���ñ���
		head_title.setText(getString(R.string.book_head_mokao));
		try{
	        JSONObject json = new JSONObject(this.readString("login_result"));
	        name.setText(json.getString("stuname"));
	        idnum.setText(json.getString("licenceCode"));
	        tel.setText(json.getString("phone"));
	        price.setText("����");
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}

	protected void initData() {
         
	}

	public void opendataselect(View view) {
			List<String> value = new ArrayList<String>();
			value.add("��Ŀ��");
			value.add("��Ŀ��");

			dialog = new SelectDialog_ui(view, this, "ԤԼ��Ŀ", value, this);
			dialog.show();
	
	}

	public void save_imitate(View s) {

		String book_date = checkValue(date, "��ѡ��ʱ��");
		if (book_date.length() == 0)
			return;

		String book_kemu = checkValue(kemu, "��ѡ���Ŀ");
		if (book_kemu.length() == 0)
			return;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("studyDate", DateStruct.recon_date(book_date));
			param.put("subject", book_kemu.equalsIgnoreCase("��Ŀ��") ? "1" : "2");
			param.put("id", readString("user_id"));
			add_imitate(param);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private void add_imitate(Map<String, Object> param) {
		if(!NetUtil.isNetworkConnected(this)){
			Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
			return ;
		}
		String operurl = getOperateURL(R.string.webbaseurl,
				R.string.get_addmokao);
		// ��¼10s��ʱ ��ֻ��һ����Ϣ
		HanderListObject loginhander = new HanderListObject(6, true) {
			public void handleMessage(Message msg) {
				if (msg.what == 11) {
					// 0��"ԤԼ�ɹ���"��1��"����ǰһ��ԤԼ��"��2��"ԤԼ���� ��ģ������ԤԼ��"
					// ������"���տ���ԤԼ�����������޷�ԤԼ���볢���������ڣ�"
					try{
					JSONObject json = new JSONObject(msg.obj.toString());
					switch (json.getInt("state")) {
					case 0:
						DisPlay("ԤԼ�ɹ�", true);
						break;
					case 1:
						DisPlay("����ǰһ��ԤԼ��", true);
						break;
					case 2:
						DisPlay("ԤԼ���� ��ģ������ԤԼ��", true);
						break;

					default:
						DisPlay("���տ���ԤԼ�����������޷�ԤԼ���볢���������ڣ�", true);
						break;
					}
					}catch(Exception e){
						e.printStackTrace();
					}
				} else {
				}
			
				if (loading != null) {
					loading.dismiss();
					loading = null;
				}
				Intent intent = new Intent();
				intent.setClass(Book_imitate_ui.this, Book_mokao_ui.class);
				startActivity(intent);
				finish();
			}
		};

		param.put("operateurl", operurl);
		param.put("encoding", getStringValue(R.string.encoding));
		param.put("mobileFlag", readString("mobileFlag"));
		// �½���������
		GetHttpResultThread geter = new GetHttpResultThread(loginhander, param);

		// ע�����
		loginhander.addtolisttask(geter);
		loading = new LoadingDialog_ui(this, R.style.loadingstyle,
				R.layout.dialog_loading_lay);
		loading.setCancelable(false);
		loading.show();
	}

	@Override
	public void finish_select(View view, int postion, Object value) {
		if (view.getId() == R.id.book_imitate_kemu) {
			kemu.setText(value.toString());
			getPrice();
		} 
	

	}
	
	public void getPrice(){
		if(!NetUtil.isNetworkConnected(this)){
			Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
			return ;
		}
		if((!date.getText().toString().equals(""))&&(!kemu.getText().toString().equals(""))){
			String operurl = getOperateURL(R.string.webbaseurl, R.string.get_imitateprice);
			// ��¼10s��ʱ ��ֻ��һ����Ϣ
			HanderListObject loginhander = new HanderListObject(5, true) {
				public void handleMessage(Message msg) {
					if (msg.what == 11) {
			// ��¼��Ϣ
						JSONObject json;
						
						try {
							
							json = new JSONObject(msg.obj.toString());
							Log.i(TAG,json.toString());
							if(compare_date(date.getText().toString(),json.getString("beginDate"),json.getString("endDate"))){
								price.setText(json.getString("price"));
							}else{
								DisPlay("��ԤԼʱ��Ϊ��"+json.getString("beginDate")+"~"+json.getString("endDate"), true);
								date.setText("");
							}
					        
						} catch (JSONException e) {
							
							e.printStackTrace();
							Toast.makeText(Book_imitate_ui.this, "�����쳣����ȡ�۸�ʧ��",
									Toast.LENGTH_SHORT).show();
						}
		

					} else {
						Toast.makeText(Book_imitate_ui.this, "��ȡ�۸�ʧ��",
								Toast.LENGTH_SHORT).show();
					}

					// �ر�
					if (loading != null) {
						loading.dismiss();
						loading = null;
					}
				}
			};
	        try{
			// ��ʼ������
			Map<String, Object> param = new HashMap<String, Object>();
	        param.put("id",readString("user_id"));
	        param.put("date", date.getText().toString());
	        param.put("subject", kemu.getText().toString().equals("��Ŀ��")?1:3);
	        param.put("mobileFlag", readString("mobileFlag"));
			param.put("operateurl", operurl);
			param.put("encoding", getStringValue(R.string.encoding));
			// �½���������
			GetHttpResultThread geter = new GetHttpResultThread(loginhander, param);
	        
			// ע�����
			loginhander.addtolisttask(geter);

			loading = new LoadingDialog_ui(this, R.style.loadingstyle,
					R.layout.dialog_loading_lay);
			loading.setCancelable(false);
			loading.show();
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
		}
	}
    public boolean compare_date(String time,String begintime ,String endtime) {
        
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat daf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date dt1 = df.parse(begintime);
            Date dt2 = df.parse(endtime);
            Date date = daf.parse(time);
            if ((date.getTime()>=dt1.getTime())&&(date.getTime()<=dt2.getTime())) {
                return true ;
            } else {
            	return false ;
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        
		return false;
            }
    
	// ʱ���ȡ����
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		try {
			if (requestCode == open_result) {
				if (resultCode == RESULT_OK) {
					String structdate = data.getExtras()
							.getString("selectDate");
					if (structdate != null && structdate.trim().length() > 0)
						{
						  date.setText(structdate);
						  getPrice();
						}
				}
			}
		} catch (Exception e) {
		}
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
