package com.huishen_app.zc.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.huishen_app.all.alarmservice.HanderListObject;
import com.huishen_app.zc.operate_thread.GetHttpResultThread;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.adapter.Book_TrainHistory_Adapter;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.ui.dialog.ContentDialog_ui;
import com.huishen_app.zc.ui.dialog.LoadingDialog_ui;
import com.huishen_app.zh.netTool.NetUtil;

import android.annotation.SuppressLint;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class TrainHisDetailActivity extends BaseActivity implements OnItemClickListener{

	private ListView train_his_listview;

	private List<Map<String, Object>> listview_date;

	private SimpleAdapter adapter;
	/** ���ضԻ��� */
	private LoadingDialog_ui loading;
	
	private ContentDialog_ui hisdialog ; //��ʷ��¼������
	
	private TextView title ,teacher , license_type,train_sub_t,train_state_t ,
	                train_trainingtime ,train_time ,train_car ,train_money; //����
    private String trainCode ; 
	@Override
	protected void findViewById_Init() {
		this.setContentView(R.layout.train_his_detail_lay);
        trainCode =  getParams("trainCode").toString();
        Log.i(TAG, trainCode+"" );
		train_his_listview = (ListView)findViewById(R.id.train_his_detail_listview);
		title = (TextView) findViewById(R.id.header_title);
		teacher = (TextView) findViewById(R.id.train_instructor);
		license_type = (TextView) findViewById(R.id.train_license_type);
		train_sub_t = (TextView) findViewById(R.id.train_sub_t);
		train_state_t = (TextView) findViewById(R.id.train_state_t);
		train_trainingtime = (TextView) findViewById(R.id.train_trainingtime);
		train_time = (TextView) findViewById(R.id.train_time);
		train_car = (TextView) findViewById(R.id.train_car);
		train_money = (TextView) findViewById(R.id.train_money);
		
		String[] from = {"detailType","campusCode","detailCount","beginTime","score"};
		int[] to = {R.id.train_detail_date,R.id.train_detail_location,R.id.train_detail_count,R.id.train_detail_time,R.id.train_detail_score};

		listview_date = new ArrayList<Map<String, Object>>();
		adapter = new SimpleAdapter(this,listview_date,
				R.layout.train_his_detail_item, from, to);

		train_his_listview.setAdapter(adapter);
		train_his_listview.setOnItemClickListener(this);
	}
    
	@Override
	protected void initView() {
		title.setText("��ѵ����");
	}

	@Override
	protected void initData() {
		if(!NetUtil.isNetworkConnected(this)){
			Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
			return ;
		}
		String operurl = getOperateURL(R.string.webbaseurl, R.string.get_todetailurl);
		// ��¼10s��ʱ ��ֻ��һ����Ϣ
		HanderListObject loginhander = new HanderListObject(6, true) {
			public void handleMessage(Message msg) {
				if(msg.what==13){

					// �������
					Main_fragment_ui.setLoginhttpclient(null);
					Toast.makeText(TrainHisDetailActivity.this, "�Բ�����������",
							Toast.LENGTH_SHORT).show();
				}else
				if (msg.what == 11) {
					// ��¼��Ϣ
					JSONObject json;
					
					try {
						
						json = JSON.parseObject(msg.obj.toString());
						setInfo(json.getJSONObject("info"));
				        setList(json.getJSONObject("dataPageBean"));
					} catch (JSONException e) {
						
						e.printStackTrace();
						Toast.makeText(TrainHisDetailActivity.this, "�����쳣����ȡ��Ϣʧ��",
								Toast.LENGTH_SHORT).show();
					}

				} else {
					Toast.makeText(TrainHisDetailActivity.this, "��ȡ��Ϣʧ��",
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
        param.put("mobileFlag", readString("mobileFlag"));
		param.put("operateurl", operurl);
		param.put("trainCode", trainCode);
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
     
	private void setList(JSONObject json){
		/**
		 * ,{"beginTime":"2014-11-04","campusCode":"004",
		 * "campusId":2,"detailCount":1,"detailType":"�෽λͣ��",
		 * "drivingLength":9,"endTime":"2014-11-04",
		 * "id":302,"lessonId":63,"score":90}
		 * {"beginTime":"2014-11-04","campusCode":"003",
		 * "campusId":1,"detailCount":2,"detailType":"�෽λͣ��",
		 * "drivingLength":9,"endTime":"2014-11-04",
		 * "id":307,"lessonId":63,"score":50}
		 * {"beginTime":"2014-11-04","campusCode":"003",
		 * "campusId":1,"detailCount":1,"detailType":"������",
		 * "drivingLength":31,"endTime":"2014-11-04",
		 * "id":305,"lessonId":63,"score":90}
		 */
		JSONArray array = json.getJSONArray("rows");
		Log.i(TAG, array.toString());
		listview_date.clear();
		HashMap<String, Object> map;
		for (int i = 0; i < array.size(); i++) {
			JSONObject jo = array.getJSONObject(i);
			map = new HashMap<String, Object>();
			map.put("id", jo.getInteger("id"));
			map.put("lessonId", jo.getInteger("lessonId"));
			map.put("detailType", jo.getString("detailType"));
			map.put("detailCount", jo.getInteger("detailCount"));
			map.put("beginTime", jo.getString("beginTime"));
			map.put("score", jo.getInteger("score"));
			map.put("campusCode", jo.getString("campusCode"));
			listview_date.add(map);
		}
		//TODO ʱ������������������
		// ������Դ1
		adapter.notifyDataSetChanged();

	}
	/**
	 * ���û�����Ϣ
	 * @param json
	 */
	private void setInfo(JSONObject json){
		teacher.setText(json.getString("coachName"));
		license_type.setText(json.getString("title"));
		train_sub_t.setText(lessons[json.getInteger("subject")]);
		train_state_t.setText(status[json.getInteger("status")]);
		train_trainingtime.setText(json.getString("realBeginTime"));
		train_time.setText(json.getString("beginTime")+"~"+json.getString("endTime"));
		//���ݿ�û��
		train_car.setText(json.getString("licenceCode"));
		train_money.setText(json.getString("cash"));		
	}
	/** 
	 * ���list����Ӧ�¼�
	 */
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if(!NetUtil.isNetworkConnected(this)){
			Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
			return ;
		}
		String operurl = getOperateURL(R.string.webbaseurl, R.string.get_todeductioninfo);
		// ��¼10s��ʱ ��ֻ��һ����Ϣ
		HanderListObject loginhander = new HanderListObject(5, true) {
			public void handleMessage(Message msg) {
				if(msg.what==13){

					// �������
					Main_fragment_ui.setLoginhttpclient(null);
					Toast.makeText(TrainHisDetailActivity.this, "�Բ�����������",
							Toast.LENGTH_SHORT).show();
				}else
				if (msg.what == 11) {
					// ��Ϣ
					JSONObject ja ;
					try {
						
						ja = JSON.parseObject(msg.obj.toString());
//						Toast.makeText(TrainHisDetailActivity.this, msg.obj.toString(),
//								Toast.LENGTH_SHORT).show();
				        setDialog(ja.getJSONArray("rows"));
					} catch (JSONException e) {
						
						e.printStackTrace();
						Toast.makeText(TrainHisDetailActivity.this, "�����쳣����ȡ��Ϣʧ��",
								Toast.LENGTH_SHORT).show();
					}

				} else {
					Toast.makeText(TrainHisDetailActivity.this, "��ȡ��Ϣʧ��",
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
        param.put("mobileFlag", readString("mobileFlag"));
		param.put("operateurl", operurl);
		param.put("deducted_code", listview_date.get(position).get("id"));
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
	/**
	 * ���õ�����
	 * @param array
	 */
	private void setDialog(JSONArray array ){
        String[] types = new String[]{"��ѵ��Ŀ ","������ѵ", "������� ","�µ�ͣ�� ","�� ","�෽λͣ�� ","ֱ��ת�� ","������ʻ ","������"};
		String[] from = {"type","content","pointValue"};
		int[] to = {R.id.dialog_type,R.id.dialog_content,R.id.dialog_value};
		ArrayList<Map<String, Object>> listview_date = new ArrayList<Map<String, Object>>();
//		listview_date.clear();
		HashMap<String, Object> map;
		for (int index = 0; index < array.size(); ++index) {
			JSONObject json = array.getJSONObject(index);
			map = new HashMap<String, Object>();
			map.put("content", json.getString("content"));
			map.put("type", types[json.getInteger("detailType")]);
			map.put("pointCode", json.getInteger("pointCode"));
			map.put("id", json.getInteger("id"));
			map.put("pointValue", json.getInteger("pointValue"));
			listview_date.add(map);
		}
		try{
		SimpleAdapter sadapter = new SimpleAdapter(TrainHisDetailActivity.this,listview_date,
				R.layout.train_his_detail_dia_item, from, to);
		hisdialog = new ContentDialog_ui(TrainHisDetailActivity.this,"�۷���ϸ",sadapter);
		hisdialog.show();
		}catch(Exception e){
//			e.printStackTrace() ;
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
