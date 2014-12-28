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
    /** ���ضԻ��� */
	private LoadingDialog_ui loading;
	
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
		this.title.setText("�ҵ��н�");
		this.note.setText("�콱����");
		this.note.setVisibility(View.VISIBLE);
		this.note.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				String[] str = new String[]{"1.�ô���ȯ���޺�˾�����º�����Уʹ�á�","2.�ô���ȯ���޿�Ŀ������Ŀ��ѡ��ʹ�á�","3.�ô���ȯ���Զһ��ֽ�","4.�ô���ȯʹ����Ч��Ϊ2014��12��30����2015��12��31��","5.��˾��ѧ���������Դ˴λ�����н���Ȩ��"};
				ArrayList<HashMap<String ,String>> array= new ArrayList<HashMap<String ,String>>();
				for(int i = 0 ; i<str.length;i++){
				HashMap<String ,String> map = new HashMap<String , String>();
				map.put("value", str[i]);
				array.add(map);

				}
				dialog = new MessageDialog_ui(PrizeListActivity.this,"�콱����" ,1 ,array); 
				dialog.show();
			}
			
		});
		//list����
      String[] from = new String[]{"prize","time"};
      int[] to = new int[]{R.id.prizelist_prize,R.id.prizelist_time};
      data = new ArrayList<HashMap<String ,Object>>();
      adapter = new PrizeListAdapter(this,data ,R.layout.prizelist_item ,from , to);
      Log.i("prizeList", "��֪��");
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
	/** ��ȡ��Ʒ */
	public void lucklist() {

		/**
		 * {"DrivingLicenceType":"B2","Testcount":0,
		 * "address":"����","coachId":12, "coachname":"��ƽ·",
		 * "lessonInfocount":0,"licenceCode":"510824199210207794",
		 * "path":"/drivingSchool/attachment/head/2014110715530401412670.jpg",
		 * "phone":"123456","school":"����ѵ����",
		 * "sex":true,"stuId":11,"stuname":"����"}
		 */
		
		String operurl = this
				.getOperateURL(R.string.webbaseurl,
				R.string.url_getlucklist);
		// ��¼10s��ʱ ��ֻ��һ����Ϣ
		HanderListObject loginhander = new HanderListObject(10, true) {
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==13){

					// �������
					Main_fragment_ui.setLoginhttpclient(null);
					Toast.makeText(PrizeListActivity.this, "�Բ�����������",
							Toast.LENGTH_SHORT).show();
				}else
				if (msg.what == 11) {
					// ��¼��Ϣ
					JSONArray jobj;
					try {
						
						jobj = new JSONArray(msg.obj.toString());
						Log.i("luck", jobj.toString());
						data.clear() ;
						//[{"prizeName":"�Ż�Ƿ��100","createTime":"2014-12-26"}]
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
					      map.put("time", "�齱ʱ�䣺"+jobj.getJSONObject(i).getString("createTime"));
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
				// �ر�
				if (loading != null) {
					loading.dismiss();
					loading = null;
				}
				
			}
		};

		// ��ʼ������
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("mobileFlag",readString("mobileFlag"));
        
		param.put("operateurl", operurl);
		param.put("encoding", getStringValue(R.string.encoding));
		// �½���������
		GetHttpResultThread login = new GetHttpResultThread(loginhander, param);

		// ע�����
		loginhander.addtolisttask(login);

	}
	
	@Override
	public void onRefresh() {
		lucklist();
	}
}
