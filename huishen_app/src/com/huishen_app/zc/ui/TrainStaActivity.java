package com.huishen_app.zc.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.huishen_app.all.alarmservice.HanderListObject;
import com.huishen_app.all.mywidget.RoundProgressBar;
import com.huishen_app.zc.operate_thread.GetHttpResultThread;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.ui.dialog.LoadingDialog_ui;
import com.huishen_app.zh.netTool.AppController;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class TrainStaActivity extends BaseActivity {

    private String TAG = "TrainStaActivity";
	private RoundProgressBar train_sta_roundbar;
	private TextView perc ,title ;  //百分比,标题
	private TextView train_sub1  ;  //科目一完成比
	private TextView train_sub2  ;  //科目二完成比
	private TextView train_sub3  ;  //科目三完成比
	private TextView train_sub4  ;  //科目四完成比
	private TextView train_time ;  //统计时间
	private LinearLayout bg ; //背景图片
	private LinearLayout lay_sub1,lay_sub2,lay_sub3,lay_sub4; //四个科目布局
	private int sum = 0 ,num = 0 ; //sum总学时,num已修学时
	private int percnum = 0 ; //百分比
    private String url ; //数据访问lujin
	/** 加载对话框 */
	private LoadingDialog_ui loading;
	private ArrayList<TextView> tvs = null ;
	int[] scount = new int[]{4,13,20,4};
	@Override
	protected void findViewById_Init() {
		this.setContentView(R.layout.train_sta_lay);
		train_sta_roundbar = (RoundProgressBar)findViewById(R.id.train_sta_roundbar);
        perc = (TextView)findViewById(R.id.perc_t);
        train_sub1 = (TextView)findViewById(R.id.train_sub1_t);
        train_sub2 = (TextView)findViewById(R.id.train_sub2_t);
        train_sub3 = (TextView)findViewById(R.id.train_sub3_t);
        train_sub4 = (TextView)findViewById(R.id.train_sub4_t);
        train_time = (TextView)findViewById(R.id.train_time);
        title = (TextView)findViewById(R.id.header_title);
        bg = (LinearLayout)findViewById(R.id.train_sta_bg);
        lay_sub1 = (LinearLayout)findViewById(R.id.train_sta_sub1);
        lay_sub2 = (LinearLayout)findViewById(R.id.train_sta_sub2);
        lay_sub3 = (LinearLayout)findViewById(R.id.train_sta_sub3);
        lay_sub4 = (LinearLayout)findViewById(R.id.train_sta_sub4);
        tvs = new ArrayList<TextView>();
        tvs.add(train_sub1);
        tvs.add(train_sub2);
        tvs.add(train_sub3);
        tvs.add(train_sub4);
	}

	@Override
	protected void initView() {
       title.setText("培训统计");
       LinearLayout.LayoutParams r_params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, (int) (this.getWidth() * 512/720));
  	   bg.setLayoutParams(r_params);
  	 lay_sub2.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View arg0) {
		 Intent i = new Intent(TrainStaActivity.this,TrainHisTabActivity.class);
		 startActivity(i);
		}
  		 
  	 });
	}

	@Override
	protected void initData() {
		url = getOperateURL(R.string.webbaseurl, R.string.get_staurl);
       //使用Volley进行网络请求
		HanderListObject loginhander = new HanderListObject(5, true) {
			public void handleMessage(Message msg) {
				if(msg.what==13){

					// 保存对象
					Main_fragment_ui.setLoginhttpclient(null);
					Toast.makeText(TrainStaActivity.this, "对不起，您已下线",
							Toast.LENGTH_SHORT).show();
				}else
				if (msg.what == 11) {
					// 登录信息
					JSONObject json;
					
					try {
						
						json = new JSONObject(msg.obj.toString());
						Log.i(TAG,json.toString());
						setData(json);
				
					} catch (JSONException e) {
						
						e.printStackTrace();
						Toast.makeText(TrainStaActivity.this, "网络异常",
								Toast.LENGTH_SHORT).show();
					}
	

				} else {
					Toast.makeText(TrainStaActivity.this, "访问失败",
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
        param.put("id", readString("user_id"));
        param.put("mobileFlag", readString("mobileFlag"));
		param.put("operateurl", url);
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
	private void setData(JSONObject result) {
		
		try {
			Log.i("TrainSta", result.toString());
			JSONArray jsons = result.getJSONArray("data");
			for(int i = 0 ; i<jsons.length(); i++){
				JSONObject json = jsons.getJSONObject(i);
//				Log.i("TrainSta", json.getString("scount"));
				sum = sum+scount[i];
				num = num+json.getInt("total");
				setSubText(json);
				Log.i("TrainSta", "sum"+sum);
			}
            percnum =(num*100)/sum ;
            Log.i("TrainSta","percnum"+percnum);
            perc.setText(percnum+"%"); //设置百分比文字显示
            train_sta_roundbar.setProgress(percnum); //设置百分比进度条显示
            train_time.setText("统计截止"+result.getString("time"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 在ui上面填数据
	 * @param lesson
	 * @param content
	 */
	 private void setSubText(JSONObject json){
		 try {
			tvs.get(json.getInt("subject")).setText(Html.fromHtml("<font color=\"red\">"
		           +json.getInt("total")+"</font>"+"/"+scount[json.getInt("subject")]));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 } 
	/**
	 * 通用返回按钮
	 * 
	 * @param v
	 */
	public void backIntent(View v) {
		AppController.getInstance().getRequestQueue().cancelAll(TAG);
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