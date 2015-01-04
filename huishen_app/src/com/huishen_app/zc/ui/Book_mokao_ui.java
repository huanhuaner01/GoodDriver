package com.huishen_app.zc.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.huishen_app.all.alarmservice.HanderListObject;
import com.huishen_app.zc.operate_thread.DeleteMoKaoThread;
import com.huishen_app.zc.operate_thread.GetHttpResultThread;
import com.huishen_app.zc.ui.adapter.Book_Mokao_Adapter;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.ui.dialog.LoadingDialog_ui;
import com.huishen_app.zh.netTool.NetUtil;

import android.content.Intent;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Book_mokao_ui extends BaseActivity {

	private TextView head_title;

	private ListView book_mokao_selected;

	private List<Map<String, Object>> listview_date;

	private Book_Mokao_Adapter adapter;

	// ��ʷģ��
	private JSONArray his_imitate;

	private List<String> select_id;

	private boolean opentype = false;

	@Override
	protected void findViewById_Init() {
		setContentView(R.layout.book_mokao_lay);

		head_title = (TextView) findViewById(R.id.header_title);
		book_mokao_selected = (ListView) findViewById(R.id.book_mokao_selected);

		select_id = new ArrayList<String>();

	}

	protected void initView() {

		Object obj = getParams("opentype");
		if (obj == null || obj.toString().trim().length() == 0)
			opentype = false;
		else
			opentype = true;
		// ���ñ���
		head_title.setText(getString(R.string.book_head_mokao));
		
		//***************************��ʼ���б�*******************************************//
		//����ѡ��ģʽΪ��ѡģʽ
		book_mokao_selected.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		// ������Դ1
		String[] from = { "date", "kemu", "cash", "status" };
		int[] to = { R.id.book_imitate_his_date, R.id.book_imitate_his_kemu,
				  R.id.book_imitate_his_cash,
				R.id.book_mokao_his_states };
		listview_date = new ArrayList<Map<String, Object>>();

		adapter = new Book_Mokao_Adapter(this, listview_date,
				R.layout.book_dateselect_mokao_item, from, to);

		book_mokao_selected.setAdapter(adapter);
		
		book_mokao_selected.setOnItemClickListener(new OnItemClickListener(){    
            @Override    
            public void onItemClick(AdapterView<?> parent, View view,    
                    int position, long id) {
            	//ˢ���б�
             	adapter.notifyDataSetChanged();
            	
            	String viewid = view.getTag() == null ? "" : view.getTag()
        				.toString();
				if (book_mokao_selected.isItemChecked(position)
						&& listview_date.get(position).get("status_id").equals("0")) {// ���
        			select_id.add(viewid);
        			
        		} else {// ɾ��
        			for (int index = 0; index < select_id.size(); ++index) {
        				if (select_id.get(index).equalsIgnoreCase(viewid)) {
        					select_id.remove(index);
        					--index;
        				}
        			}
        		}   
            }    
        });
		//***************************��ʼ���б����*******************************************//
	}

	private void load_web_data() {
		if(!NetUtil.isNetworkConnected(this)){
			Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
			return ;
		}
		String operurl = getOperateURL(R.string.webbaseurl,
				R.string.get_imitate);

		select_id.clear();
		book_mokao_selected.clearChoices();
		// ��¼10s��ʱ ��ֻ��һ����Ϣ
		HanderListObject loginhander = new HanderListObject(6, true) {

			public void handleMessage(Message msg) {
				if(msg.what == 13){
					Toast.makeText(Book_mokao_ui.this,"�Բ����������ߣ������µ�¼", Toast.LENGTH_SHORT).show();
					Main_fragment_ui.setLoginhttpclient(null);
				}else
				if (msg.what == 11) {
					try {
						his_imitate = new JSONArray(msg.obj.toString());
				        int testnum = Integer.parseInt(readString("yuyuenum"));
		                   if(his_imitate.length()!=testnum){
		                	   saveString("testnum",his_imitate.length()+"");
		                   }
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// ��ȡ�������
//					his_imitate = j_obj.getJSONArray("rows");
					listview_date.clear();

					HashMap<String, Object> map;
					for (int index = 0; index < his_imitate.length(); ++index) {
						map = new HashMap<String, Object>();
						try{
						JSONObject json = his_imitate.getJSONObject(index);
						map.put("date", json.get("studyDate"));
						map.put("kemu",lessons[json.getInt("subject")] );
						map.put("cash", "��"+json.get("cash"));

						map.put("mokao_id", json.get("id").toString());
						map.put("status_id", json.getString("status"));
						switch (Integer
								.parseInt(json.get("status").toString())) {
						case 0:
							map.put("status", "������");
							break;
						case 1:
							map.put("status", "�ѷ���");
							break;
						case 2:
							map.put("status", "�����");
							break;
						default:
							map.put("status", "�쳣ԤԼ");
							break;
						}
						}catch(Exception e){
							e.printStackTrace() ;
						}
						listview_date.add(map);
					}
					adapter.notifyDataSetChanged();

				} else {

				}
				// �ر�
				if (loading != null) {
					loading.dismiss();
					loading = null;
				}
			}
		};

		// ��ʼ������ stuId=1&date=2014/10/03&page=1&rows=2&dutyType=0
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("stuId", readString("user_id"));
        param.put("mobileFlag", readString("mobileFlag"));
		param.put("operateurl", operurl);
		param.put("encoding", getStringValue(R.string.encoding));
		// �½���������
		GetHttpResultThread geter = new GetHttpResultThread(loginhander, param);

		// ע�����
		loginhander.addtolisttask(geter);

		if (loading == null || loading.isShowing() == false) {
			loading = new LoadingDialog_ui(this, R.style.loadingstyle,
					R.layout.dialog_loading_lay);
			loading.setCancelable(false);
			loading.show();
		}
	}

	protected void initData() {
		
		load_web_data();

	}

	public void add_mokao(View view) {
		if (opentype) {
			// �� ѡ�� �رյ�ǰ��
			Intent i = new Intent(this, Book_imitate_ui.class);
			startActivity(i);
		}
		finish();
	}

	public void delete_mokao(View view) {
		
		if (select_id == null || select_id.size() == 0) {
			DisPlay("��ѡ��Ҫɾ����ģ��", true);
			return;
		}
//		Log.i(TAG, select_id.toString())
		book_mokao_selected.clearChoices();
		load_web_data(select_id);	
	}

	private void load_web_data(List<String> idlist) {
		String operurl = getOperateURL(R.string.webbaseurl,
				R.string.get_cancelmokao);
		// ��¼10s��ʱ ��ֻ��һ����Ϣ
		HanderListObject loginhander = new HanderListObject(12, true) {
			public void handleMessage(Message msg) {
				if(msg.what == 13){
					Toast.makeText(Book_mokao_ui.this,"�Բ����������ߣ������µ�¼", Toast.LENGTH_SHORT).show();
					Main_fragment_ui.setLoginhttpclient(null);
				}else
				if (msg.what == 11) {
					try {
						his_imitate = new JSONArray(msg.obj.toString());
				        int testnum = Integer.parseInt(readString("yuyuenum"));
		                   if(his_imitate.length()!=testnum){
		                	   saveString("testnum",his_imitate.length()+"");
		                   }
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					org.json.JSONObject json;
					try {
						json = new JSONObject(msg.obj.toString());
						saveData(json);
						load_web_data();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				} else {
					if (loading != null) {
						loading.dismiss();
						loading = null;
					}
				}
			}
		};

		// ��ʼ������ stuId=1&date=2014/10/03&page=1&rows=2&dutyType=0
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("idlist", idlist);
		param.put("mobileFlag", readString("mobileFlag"));
		param.put("operateurl", operurl);
		param.put("encoding", getStringValue(R.string.encoding));

		// �½���������
		DeleteMoKaoThread geter = new DeleteMoKaoThread(loginhander, param);

		// ע�����
		loginhander.addtolisttask(geter);
		loading = new LoadingDialog_ui(this, R.style.loadingstyle,
				R.layout.dialog_loading_lay);
		loading.setCancelable(false);
		loading.show();
	}

//	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//		String id = buttonView.getTag() == null ? "" : buttonView.getTag()
//				.toString();
//		if (isChecked) {// ���
//			select_id.add(id);
//		} else {// ɾ��
//			for (int index = 0; index < select_id.size(); ++index) {
//				if (select_id.get(index).equalsIgnoreCase(id)) {
//					select_id.remove(index);
//					--index;
//				}
//			}
//		}
//
//	}
	
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
