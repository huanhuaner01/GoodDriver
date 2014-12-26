package com.huishen_app.zc.ui.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huishen_app.all.alarmservice.HanderListObject;
import com.huishen_app.zc.operate_thread.GetHttpResultThread;
import com.huishen_app.zc.operate_thread.GetHttpResultThreadMore;
import com.huishen_app.zc.ui.JudgeActivity;
import com.huishen_app.zc.ui.Main_fragment_ui;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.TrainHisDetailActivity;
import com.huishen_app.zc.ui.adapter.Book_TrainHistory_Adapter;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.ui.dialog.LoadingDialog_ui;
import com.huishen_app.zh.ui.Chart.ChartBean;
import com.huishen_app.zh.ui.Chart.ChartLineBean;
import com.huishen_app.zh.ui.Chart.ZhChart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint({ "ValidFragment", "ResourceAsColor", "SimpleDateFormat", "Instantiatable" })
public class TrainHisFragment extends BaseFragment implements
		OnItemClickListener {

	public TrainHisFragment(BaseActivity father) {
		super(father);
	}

	private String TAG = "TrainHisFragment";
	private View this_lay;

	private ListView train_his_listview;

	private List<Map<String, Object>> listview_date;

	private Book_TrainHistory_Adapter adapter;

	private LoadingDialog_ui loading; // ���ؿ�
	private ZhChart c; // ����ͼ
    
	
	@Override
	public void onResume() {
		initDate();
		super.onResume();
	}

	@SuppressLint("InflateParams")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try {
			this_lay = inflater.inflate(R.layout.book_train_lay, null);

			train_his_listview = (ListView) this_lay
					.findViewById(R.id.train_his_listview);

			draw_pic();

		} catch (Exception e) {
		}
		return this_lay;
	}

	private void initDate() {
		listview_date = new ArrayList<Map<String, Object>>();
		//
		initAllData();
		// ������Դ1

	}

	public void draw_pic() {

		c = new ZhChart(this.father);
		LinearLayout layout = (LinearLayout) this_lay.findViewById(R.id.layout);
		layout.addView(c);

		LinearLayout.LayoutParams imglayout = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				father.getHeight() * 2 / 5);
		// ����ͼƬ��С
		c.setLayoutParams(imglayout);

	}

	/**
	 * �����б�����
	 */
	private void setListDate(JSONArray array) {
		// {"beginTime":"2014-11-12","coachName":"���ڷ�","count":1,"endTime":"2014-11-12",
		//   "id":52,"lessonCode":"11411110012","status":2,"subject":1}
		// ������Դ1
		String[] from = { "date", "kemu", "teacher" };
		int[] to = { R.id.book_yuyue_his_date, R.id.book_yuyue_his_kemu,
				R.id.book_yuyue_his_jl };
        
		
		listview_date.clear();
		for (int i = 0; i < array.size(); i++) {
			JSONObject json;
				json = array.getJSONObject(i);
		
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("date", json.getString("beginTime"));
			map.put("kemu", father.lessons[json.getInteger("subject")]);
			map.put("teacher", json.getString("coachName"));
			map.put("status", json.getInteger("status"));
			map.put("id", json.getInteger("id"));
			map.put("count", json.getInteger("count"));
			map.put("lessonCode", json.getString("lessonCode"));
			
			listview_date.add(map);
			
		}
		adapter = new Book_TrainHistory_Adapter(father, listview_date,
				R.layout.train_his_item, from, to);

		train_his_listview.setAdapter(adapter);


//		HashMap<String, Object> map;
//		for (int index = 0; index < 3; ++index) {
//			map = new HashMap<String, Object>();
//			map.put("id", index);
//			listview_date.add(map);
//		}
		adapter.notifyDataSetChanged();

		train_his_listview.setOnItemClickListener(this);
	}

	/**
	 * ������������
	 */
	private void initAllData() {
		/*
		 * ����ͼ���ݷ��ʵ�ַ
		 * http://192.168.0.101:8080/drivingSchool/mobile/queryTrainStatistics
		 * ?mobileFlag=123456 ����ͼ���ݸ�ʽ
		 * {"series":[{"data":[0,0,0,0,0,0],"name":0,"pointer"
		 * :4},{"data":[0,0,0, 0
		 * ,0,0],"name":1,"pointer":4},{"data":[100,0,0,100
		 * ,0,0],"name":2,"pointer" :
		 * 4},{"data":[50,0,0,100,0,0],"name":3,"pointer"
		 * :4},{"data":[100,0,0,100 ,
		 * 0,0],"name":4,"pointer":4},{"data":[100,0,0
		 * ,100,0,0],"name":5,"pointer"
		 * :4},{"data":[100,0,0,100,0,0],"name":6,"pointer":4}],"times":[
		 * "2014-11-20 07:00:00"
		 * ,"2014-11-19 07:00:00","2014-11-18 07:00:00","2014-11-17 07:00:00"
		 * ,"2014-11-16 07:00:00","2014-11-15 07:00:00"]}
		 */
		// ����ͼurl
		String operurl = father.getOperateURL(R.string.webbaseurl,
				R.string.get_sub2trainurl);
		// ��ѵxiangq
		String operurl_n = father.getOperateURL(R.string.webbaseurl,
				R.string.get_straindetail);
		// ��¼10s��ʱ ��ֻ��һ����Ϣ
		HanderListObject loginhander = new HanderListObject(5, true) {
			public void handleMessage(Message msg) {
				if (msg.what == 13) {

					// �������
					Main_fragment_ui.setLoginhttpclient(null);
					Toast.makeText(father, "�Բ�����������", Toast.LENGTH_SHORT)
							.show();
				} else if (msg.what == 11) {

					try {
						Map<String, StringBuffer> map = (Map<String, StringBuffer>) msg.obj;
						// Log.i(TAG,"1:"+map.toString());
						JSONObject json = JSON.parseObject(map.get("1")
								.toString());
						Log.i(TAG, "1:" + json.toString());
						setChart(json);
//						Log.i(TAG, "2:" + map.get("2").toString());
						JSONArray array = JSONArray.parseArray(map.get("2")
								.toString());
						Log.i(TAG, "2:" + array.toString());
						setListDate(array);
					} catch (Exception e) {

						e.printStackTrace();
						Toast.makeText(father, "�����쳣������ʧ��", Toast.LENGTH_SHORT)
								.show();
					}

				} else {
					Toast.makeText(father, "����ʧ��", Toast.LENGTH_SHORT).show();
				}

				// �ر�
				if (loading != null) {
					loading.dismiss();
					loading = null;
				}
			}
		};
		try {
			// ����ͼ��ʼ������
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("subject", 1);
			param.put("mobileFlag", father.readString("mobileFlag"));
			param.put("operateurl", operurl);
			param.put("encoding", father.getStringValue(R.string.encoding));
			// �½���������
			// GetHttpResultThread geter = new GetHttpResultThread(loginhander,
			// param);
			GetHttpResultThreadMore geter = new GetHttpResultThreadMore(
					loginhander, param, operurl, param, operurl_n);
			// ע�����
			loginhander.addtolisttask(geter);

			loading = new LoadingDialog_ui(this.father, R.style.loadingstyle,
					R.layout.dialog_loading_lay);
			loading.setCancelable(false);
			loading.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void setChart(JSONObject json) {
		Log.i(TAG, "setChart Json date is " + json.toString());
		// ��ȡX��ֵ
		JSONArray array1 = json.getJSONArray("times");
		JSONArray array2 = json.getJSONArray("series");	
		//����ͼ����
		ChartBean chartb = new ChartBean();
		chartb.setBgColor(Color.WHITE);
		chartb.setTitle("����ߴ���ѵ����ͼ");
		chartb.setTitleColor(getResources().getColor(
				R.color.book_imitate_textcolornew));
		chartb.setXYpointColor(getResources().getColor(R.color.Grey_line));
		chartb.setTableColor(getResources().getColor(R.color.Grey));
		chartb.setXYLabelColor(getResources().getColor(
				R.color.book_imitate_textcolornew));

		chartb.setYLabel(new int[] { 0, 20, 40, 60, 80, 100 });
		ArrayList<ChartLineBean> lines = new ArrayList<ChartLineBean>();
		//����0����Ҫ��ͼ
		if(array1.size() != 0){

		String[] xlabels = new String[array1.size()];
        
		for (int i = 0; i < array1.size(); i++) {
			Date data = array1.getDate(i);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
			String dateString = formatter.format(data);
			xlabels[i] = dateString;
			Log.i(TAG, dateString);
		}


		chartb.setXLabel(xlabels);

		String[] str = new String[] { "������ѵ", "�������", "�µ�ͣ������", "�෽λͣ��",
				"ֱ��ת��", "������ʻ", "������" };
		int[] colors = new int[] { R.color.green_light, R.color.orange_dark,
				R.color.purple_dark, R.color.blue_dark, R.color.green_ldark,
				R.color.blue_light, R.color.green_Dark };


		for (int i = 0; i < array2.size(); i++) {
			JSONObject js = array2.getJSONObject(i);
			JSONArray ja = js.getJSONArray("data");
			int[] data = new int[ja.size()];
			for (int j = 0; j < ja.size(); j++) {
				data[j] = ja.getIntValue(j);
			}
			ChartLineBean line = new ChartLineBean(getResources().getColor(
					colors[js.getIntValue("name")]), 1,
					str[js.getIntValue("name")], data);
			lines.add(line);
		}
		}
		c.upDateDraw(chartb, lines);

	}

	/** ���listview ����Ӧ�¼� */
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		try {
			 ListView listView = (ListView) parent;
			 HashMap<String, Object> map = (HashMap<String, Object>) listView
			 .getItemAtPosition(position);
			 String trainCode = map.get("id").toString();
			 Log.i(TAG, "trainCode is "+trainCode);
			 if((Integer)view.getTag() == 0){ //����
				         Intent i = new Intent(this.getActivity(),
				        		 JudgeActivity.class); 
				         i.putExtra("trainCode", trainCode);
				         i.putExtra("kemu", map.get("kemu").toString());
				         i.putExtra("teacher", map.get("teacher").toString());
				         i.putExtra("lessonCode", map.get("lessonCode").toString());
				         this.getActivity().startActivity(i);
			 }else{
			    //�鿴
                
			    Intent i = new Intent(this.getActivity(),
				TrainHisDetailActivity.class);
			    i.putExtra("trainCode", trainCode);
			    this.getActivity().startActivity(i);
			 }
		} catch (Exception e) {
		}
	}
}
