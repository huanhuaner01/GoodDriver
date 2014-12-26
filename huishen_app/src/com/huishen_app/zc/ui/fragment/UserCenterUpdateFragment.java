package com.huishen_app.zc.ui.fragment;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huishen_app.all.alarmservice.HanderListObject;
import com.huishen_app.zc.operate_thread.GetHttpResultThread;
import com.huishen_app.zc.ui.Main_fragment_ui;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.ui.dialog.LoadingDialog_ui;
import com.huishen_app.zc.util.MyUtil;

@SuppressLint("ValidFragment")
public class UserCenterUpdateFragment extends BaseFragment {
	private String TAG = "UserCenterUpdateFragment";
	private EditText edit;// 输入框，
	private Button updateBtn; // 修改确认按钮
	private ImageView cancel; // 取消图片
	private TextView title;// 标题
	private int tag; // 标志，0表示电话号码修改，1表示地址修改
	private LoadingDialog_ui loading ; //加载框

	public UserCenterUpdateFragment(BaseActivity father, int tag) {
		super(father);
		this.tag = tag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = null;
		try {
			this.inflater = inflater;
			view = inflater.inflate(R.layout.user_center_update, null);

			regist(view);

			init(view);
		} catch (Exception e) {
		}
		return view;
	}

	private void init(View view) {
		JSONObject json;
		try {
			json = new JSONObject(father.readString("login_result"));
			if(tag == 0){
				edit.setText(json.getString("phone"));
			}else{
			edit.setText(json.getString("address"));
			}
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		updateBtn.setText("确定");
		if (tag == 0) {
			edit.setInputType(InputType.TYPE_CLASS_PHONE);
			
			updateBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					updatePhone();
				}
			});
			title.setText("电话号码修改");
		} else {
			updateBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					updateAddr();
				}
			});
			title.setText("地址修改");
		}
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				UserCenterUpdateFragment.this.father.switchcenter(0);
			}

		});
	}

	/**
	 * 修改电话号码
	 */
	private void updatePhone() {
		String newphone = edit.getText().toString();
		if (!MyUtil.isTel(newphone)) {
			Toast.makeText(father, "电话号码不符合规范", Toast.LENGTH_SHORT).show();
			return;
		}
//		String operurl = "http://192.168.0.100:8080/Test/TestServlet";
		String operurl = father.getOperateURL(R.string.webbaseurl, R.string.url_user_update);
		// 登录10s超时 且只收一条消息
		HanderListObject loginhander = new HanderListObject(5, true) {
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==13){

					// 保存对象
					Main_fragment_ui.setLoginhttpclient(null);
					Toast.makeText(father, "对不起，您已下线",
							Toast.LENGTH_SHORT).show();
				}else
				if (msg.what == 11) {
					// 登录信息
					JSONObject json;
					
					try {
						
						json = new JSONObject(msg.obj.toString());
						Log.i(TAG,json.toString());
						if(json.getInt("state")!= 1){
							if(json.getInt("error") != 1){
								// 保存对象
								Toast.makeText(father, "你已下线", Toast.LENGTH_SHORT).show();
							
							}else{
								Toast.makeText(father, "其他异常", Toast.LENGTH_SHORT).show();
							}
							return ;
						}
						father.saveData(json.getJSONObject("data"));
						father.switchcenter(3);
				
					} catch (JSONException e) {
						
						e.printStackTrace();
						Toast.makeText(father, "网络异常，修改失败",
								Toast.LENGTH_SHORT).show();
					}
					// {"addr":"电子科大","id":1,"licence":"C1","name":"杨俊",
					// "path":"/attchment/images/IMG_2014092216410302242395.jpg",
					// "phone":"13403150491","teacherId":"00000001"}
					// 保存用户ID

	

				} else {
					Toast.makeText(father, "修改失败",
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
        param.put("id", father.readString("user_id"));
        param.put("phone",edit.getText().toString());
        param.put("mobileFlag", father.readString("mobileFlag"));
        Log.i("wq54tre3trete", "mobileFlag:"+father.readString("mobileFlag"));
		param.put("operateurl", operurl);
		param.put("encoding", father.getStringValue(R.string.encoding));
		// 新建操作对象
		GetHttpResultThread geter = new GetHttpResultThread(loginhander, param);
        
		// 注册监听
		loginhander.addtolisttask(geter);

		loading = new LoadingDialog_ui(this.father, R.style.loadingstyle,
				R.layout.dialog_loading_lay);
		loading.setCancelable(false);
		loading.show();
        }catch(Exception e){
        	e.printStackTrace();
        }

	}

	/**
	 * 修改地址
	 */
	private void updateAddr() {
		String newphone = edit.getText().toString();
//		String operurl = "http://192.168.0.100:8080/Test/TestServlet";
		String operurl = father.getOperateURL(R.string.webbaseurl, R.string.url_user_update);
		// 登录10s超时 且只收一条消息
		HanderListObject loginhander = new HanderListObject(5, true) {
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==13){

					// 保存对象
					Main_fragment_ui.setLoginhttpclient(null);
					Toast.makeText(father, "对不起，您已下线",
							Toast.LENGTH_SHORT).show();
				}else
				if (msg.what == 11) {
					// 登录信息
					JSONObject json;
					
					try {
						
						json = new JSONObject(msg.obj.toString());
						Log.i(TAG,json.toString());
						if(json.getInt("state")!= 1){
							if(json.getInt("error") != 1){
								// 保存对象
								Main_fragment_ui.setLoginhttpclient(null);
								Toast.makeText(father, "你已下线", Toast.LENGTH_SHORT).show();
							
							}else{
								Toast.makeText(father, "其他异常", Toast.LENGTH_SHORT).show();
							}
							return ;
						}
						father.saveData(json.getJSONObject("data"));
						father.switchcenter(3);
				
					} catch (JSONException e) {
						
						e.printStackTrace();
						Toast.makeText(father, "网络异常，修改失败",
								Toast.LENGTH_SHORT).show();
					}
					// {"addr":"电子科大","id":1,"licence":"C1","name":"杨俊",
					// "path":"/attchment/images/IMG_2014092216410302242395.jpg",
					// "phone":"13403150491","teacherId":"00000001"}
					// 保存用户ID

	

				} else {
					Toast.makeText(father, "修改失败",
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
        param.put("id", father.readString("user_id"));
        param.put("address",edit.getText().toString());
        param.put("mobileFlag", father.readString("mobileFlag"));
        Log.i("wq54tre3trete", "mobileFlag:"+father.readString("mobileFlag"));
		param.put("operateurl", operurl);
		param.put("encoding", father.getStringValue(R.string.encoding));
		// 新建操作对象
		GetHttpResultThread geter = new GetHttpResultThread(loginhander, param);
        
		// 注册监听
		loginhander.addtolisttask(geter);

		loading = new LoadingDialog_ui(this.father, R.style.loadingstyle,
				R.layout.dialog_loading_lay);
		loading.setCancelable(false);
		loading.show();
        }catch(Exception e){
        	e.printStackTrace();
        }

	}

	/**
	 * 注册组件
	 * 
	 * @param view
	 */
	private void regist(View view) {
		edit = (EditText) view.findViewById(R.id.user_update_edit);
		updateBtn = (Button) view.findViewById(R.id.user_update_ok);
		title = (TextView) view.findViewById(R.id.header_cancel_title);
		cancel = (ImageView) view.findViewById(R.id.header_cancel_cancel);
	}

}
