package com.huishen_app.zc.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.huishen_app.all.alarmservice.HanderListObject;
import com.huishen_app.zc.operate_thread.GetHttpResultThread;
import com.huishen_app.zc.ui.adapter.PrizeListAdapter;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.ui.dialog.LoadingDialog_ui;
import com.huishen_app.zc.ui.dialog.MessageDialog_ui;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PrizeListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
	private SwipeRefreshLayout mSwipeLayout;
    private TextView title , note;
    private ListView list ;
    private MessageDialog_ui dialog ;
    private PrizeListAdapter adapter ;
    private ArrayList<HashMap<String ,Object>> data ;
    /** 加载对话框 */
	private LoadingDialog_ui loading;
	
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
	}

	@Override
	protected void findViewById_Init() {
		setContentView(R.layout.activity_prize_list);
		this.title = (TextView) findViewById(R.id.header_title);
		this.list = (ListView) findViewById(R.id.prize_list);
		this.note = (TextView) findViewById(R.id.header_note);
		this.mSwipeLayout = (SwipeRefreshLayout)findViewById(R.id.id_swipe_ly);
	}

	@Override
	protected void initView() {
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(R.color.color_refresh_1, R.color.color_refresh_2,
				R.color.color_refresh_3, R.color.color_refresh_4);
		this.title.setText("我的中奖");
		this.note.setText("领奖规则");
		this.note.setVisibility(View.VISIBLE);
		this.note.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				String[] str = new String[]{"1.该代金券仅限好司机旗下合作驾校使用。","2.该代金券仅限科目二、科目三选课使用。","3.该代金券不以兑换现金。","4.该代金券使用有效期为2014年12月30日至2015年12月31日","5.好司机学车网保留对此次活动的所有解释权。"};
				ArrayList<HashMap<String ,String>> array= new ArrayList<HashMap<String ,String>>();
				for(int i = 0 ; i<str.length;i++){
				HashMap<String ,String> map = new HashMap<String , String>();
				map.put("value", str[i]);
				array.add(map);

				}
				dialog = new MessageDialog_ui(PrizeListActivity.this,"领奖规则" ,1 ,array); 
				dialog.show();
			}
			
		});
		//list设置
      String[] from = new String[]{"prize","time"};
      int[] to = new int[]{R.id.prizelist_prize,R.id.prizelist_time};
      data = new ArrayList<HashMap<String ,Object>>();
      adapter = new PrizeListAdapter(this,data ,R.layout.prizelist_item ,from , to);
      Log.i("prizeList", "不知道");
	  list.setAdapter(adapter);
	}

	@Override
	protected void initData() {
		lucklist();
		loading = new LoadingDialog_ui(this, R.style.loadingstyle,
				R.layout.dialog_loading_lay);
		loading.setCancelable(false);
		loading.show();
		
	}
	/** 获取奖品 */
	public void lucklist() {

		/**
		 * {"DrivingLicenceType":"B2","Testcount":0,
		 * "address":"北京","coachId":12, "coachname":"河平路",
		 * "lessonInfocount":0,"licenceCode":"510824199210207794",
		 * "path":"/drivingSchool/attachment/head/2014110715530401412670.jpg",
		 * "phone":"123456","school":"长安训练场",
		 * "sex":true,"stuId":11,"stuname":"张三"}
		 */
		
		String operurl = this
				.getOperateURL(R.string.webbaseurl,
				R.string.url_getlucklist);
		// 登录10s超时 且只收一条消息
		HanderListObject loginhander = new HanderListObject(10, true) {
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==13){

					// 保存对象
					Main_fragment_ui.setLoginhttpclient(null);
					Toast.makeText(PrizeListActivity.this, "对不起，您已下线",
							Toast.LENGTH_SHORT).show();
				}else
				if (msg.what == 11) {
					// 登录信息
					JSONArray jobj;
					try {
						
						jobj = new JSONArray(msg.obj.toString());
						Log.i("luck", jobj.toString());
						data.clear() ;
						//[{"prizeName":"张欢欠你100","createTime":"2014-12-26"}]
						for(int i = 0 ;i<jobj.length() ;i++ ){
					      HashMap<String , Object> map = new HashMap<String ,Object>();
					      int prizeid = R.drawable.jiangpin_00;
					      switch(jobj.getJSONObject(i).getInt("id")){
					      case 9:
					    	  prizeid = R.drawable.jiangpin_01;
					    	  break ;
					      case 10:
					    	  prizeid = R.drawable.jiangpin_02;
					    	  break ;
					      case 11:
					    	  prizeid = R.drawable.jiangpin_03;
					    	  break ;
					      }
					      map.put("icon", prizeid);
					      map.put("prize", jobj.getJSONObject(i).getString("prizeName"));
					      map.put("time", "抽奖时间："+jobj.getJSONObject(i).getString("createTime"));
					      data.add(map);
						}
						adapter.notifyDataSetChanged();
					} catch (JSONException e) {
						
						e.printStackTrace();
					}
					
	

				} else {
					Toast.makeText(PrizeListActivity.this, msg.obj + "",
							Toast.LENGTH_SHORT).show();
				}
				if(mSwipeLayout.isRefreshing()){
					mSwipeLayout.setRefreshing(false);
				}
				// 关闭
				if (loading != null) {
					loading.dismiss();
					loading = null;
				}
				
			}
		};

		// 初始化参数
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("mobileFlag",readString("mobileFlag"));
        
		param.put("operateurl", operurl);
		param.put("encoding", getStringValue(R.string.encoding));
		// 新建操作对象
		GetHttpResultThread login = new GetHttpResultThread(loginhander, param);

		// 注册监听
		loginhander.addtolisttask(login);

	}
	
	@Override
	public void onRefresh() {
		lucklist();
	}
}
