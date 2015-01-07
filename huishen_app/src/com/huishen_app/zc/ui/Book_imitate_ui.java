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
		// 设置标题
		head_title.setText(getString(R.string.book_head_mokao));
		try{
	        JSONObject json = new JSONObject(this.readString("login_result"));
	        name.setText(json.getString("stuname"));
	        idnum.setText(json.getString("licenceCode"));
	        tel.setText(json.getString("phone"));
	        price.setText("暂无");
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}

	protected void initData() {
         
	}

	public void opendataselect(View view) {
			List<String> value = new ArrayList<String>();
			value.add("科目二");
			value.add("科目三");

			dialog = new SelectDialog_ui(view, this, "预约科目", value, this);
			dialog.show();
	
	}

	public void save_imitate(View s) {

		String book_date = checkValue(date, "请选择时间");
		if (book_date.length() == 0)
			return;

		String book_kemu = checkValue(kemu, "请选择科目");
		if (book_kemu.length() == 0)
			return;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("studyDate", DateStruct.recon_date(book_date));
			param.put("subject", book_kemu.equalsIgnoreCase("科目二") ? "1" : "2");
			param.put("id", readString("user_id"));
			add_imitate(param);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private void add_imitate(Map<String, Object> param) {
		if(!NetUtil.isNetworkConnected(this)){
			Toast.makeText(this, "网络未连接", Toast.LENGTH_SHORT).show();
			return ;
		}
		String operurl = getOperateURL(R.string.webbaseurl,
				R.string.get_addmokao);
		// 登录10s超时 且只收一条消息
		HanderListObject loginhander = new HanderListObject(6, true) {
			public void handleMessage(Message msg) {
				if (msg.what == 11) {
					// 0："预约成功！"，1："须提前一天预约！"，2："预约已满 请模考后再预约！"
					// 其他："当日考试预约名额已满，无法预约！请尝试其他日期！"
					try{
					JSONObject json = new JSONObject(msg.obj.toString());
					switch (json.getInt("state")) {
					case 0:
						DisPlay("预约成功", true);
						break;
					case 1:
						DisPlay("须提前一天预约！", true);
						break;
					case 2:
						DisPlay("预约已满 请模考后再预约！", true);
						break;

					default:
						DisPlay("当日考试预约名额已满，无法预约！请尝试其他日期！", true);
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
		// 新建操作对象
		GetHttpResultThread geter = new GetHttpResultThread(loginhander, param);

		// 注册监听
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
			Toast.makeText(this, "网络未连接", Toast.LENGTH_SHORT).show();
			return ;
		}
		if((!date.getText().toString().equals(""))&&(!kemu.getText().toString().equals(""))){
			String operurl = getOperateURL(R.string.webbaseurl, R.string.get_imitateprice);
			// 登录10s超时 且只收一条消息
			HanderListObject loginhander = new HanderListObject(5, true) {
				public void handleMessage(Message msg) {
					if (msg.what == 11) {
			// 登录信息
						JSONObject json;
						
						try {
							
							json = new JSONObject(msg.obj.toString());
							Log.i(TAG,json.toString());
							if(compare_date(date.getText().toString(),json.getString("beginDate"),json.getString("endDate"))){
								price.setText(json.getString("price"));
							}else{
								DisPlay("可预约时间为："+json.getString("beginDate")+"~"+json.getString("endDate"), true);
								date.setText("");
							}
					        
						} catch (JSONException e) {
							
							e.printStackTrace();
							Toast.makeText(Book_imitate_ui.this, "网络异常，获取价格失败",
									Toast.LENGTH_SHORT).show();
						}
		

					} else {
						Toast.makeText(Book_imitate_ui.this, "获取价格失败",
								Toast.LENGTH_SHORT).show();
					}

					// 关闭
					if (loading != null) {
						loading.dismiss();
						loading = null;
					}
				}
			};
	        try{
			// 初始化参数
			Map<String, Object> param = new HashMap<String, Object>();
	        param.put("id",readString("user_id"));
	        param.put("date", date.getText().toString());
	        param.put("subject", kemu.getText().toString().equals("科目二")?1:3);
	        param.put("mobileFlag", readString("mobileFlag"));
			param.put("operateurl", operurl);
			param.put("encoding", getStringValue(R.string.encoding));
			// 新建操作对象
			GetHttpResultThread geter = new GetHttpResultThread(loginhander, param);
	        
			// 注册监听
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
    
	// 时间获取函数
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
	 * 通用返回按钮
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
