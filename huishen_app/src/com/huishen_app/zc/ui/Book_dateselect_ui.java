package com.huishen_app.zc.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.huishen_app.all.alarmservice.HanderListObject;
import com.huishen_app.zc.operate_thread.ADDXuankeThread;
import com.huishen_app.zc.operate_thread.GetHttpResultThread;
import com.huishen_app.zc.operate_thread.GetHttpResultThreadMore;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.ui.dialog.LoadingDialog_ui;
import com.huishen_app.zh.netTool.NetUtil;
import com.huishen_app.zh.util.AndroidUtil;
import com.huishen_app.zh.util.DateStruct;

public class Book_dateselect_ui extends BaseActivity implements
		OnTouchListener, OnCheckedChangeListener, OnClickListener {

	/** ��������ҳ������� */
	private String date, kemu;

	private TextView head_title;

	private LinearLayout dateselect_right_title, book_dateselect_selected;

	private TextView s_date, s_kemu, s_ready;
	// ������ͷ
	private RadioGroup book_yuyue_jlgrouup;

	private String price = "0";
	// ����Ԫ ���� �� �Ѿ�ԤԼ��"
	private JSONArray jl_rows;
	private JSONObject selected_obj;

	// ����ѡ���ʱ��
	private Object[] bookdates;

	// ѡ������ſγ�
	private List<LinearLayout> selected;
	private int max_selected = 3;
	// �����Ѿ�ѡ�˵Ŀγ�
	private int allready = 0;

	protected void findViewById_Init() {
		// ��ʼ�����
		setContentView(R.layout.book_dateselect_lay);
		selected = new ArrayList<LinearLayout>();

		head_title = (TextView) findViewById(R.id.header_title);

		date = getParams("book_date").toString();
		kemu = getParams("book_kemu").toString();

		// ��ʼ������
		dateselect_right_title = (LinearLayout) findViewById(R.id.dateselect_right_title);
		book_dateselect_selected = (LinearLayout) findViewById(R.id.book_dateselect_selected);
		book_yuyue_jlgrouup = (RadioGroup) findViewById(R.id.book_yuyue_jlgrouup);

		s_date = (TextView) findViewById(R.id.book_dateselect_s_date);
		s_kemu = (TextView) findViewById(R.id.book_dateselect_s_kemu);
		s_ready = (TextView) findViewById(R.id.book_dateselect_allready);

	}

	protected void initView() {
		// ���ñ���
		head_title.setText(getString(R.string.book_head_xuece));

		s_date.setText(date);
		s_kemu.setText(kemu);

		book_yuyue_jlgrouup.setOnCheckedChangeListener(this);

	}

	protected void initData() {
		allready = Integer.parseInt(readString("yuyuenum"));
		s_ready.setText("��ѡ��" + allready);
		// ��ȡ����
		load_web_data();

	}

	private void load_web_data() {
		if(!NetUtil.isNetworkConnected(this)){
			Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
			return ;
		}
		String operurl = getOperateURL(R.string.webbaseurl,
				R.string.get_jz_timeinfo);
		// ��¼10s��ʱ ��ֻ��һ����Ϣ
		HanderListObject loginhander = new HanderListObject(5, true) {
			public void handleMessage(Message msg) {
				if (msg.what == 13) {

					// �������
					Main_fragment_ui.setLoginhttpclient(null);
					Toast.makeText(Book_dateselect_ui.this, "�Բ�����������",
							Toast.LENGTH_SHORT).show();
				} else if (msg.what == 11) {
					Log.i("Book", msg.obj.toString());
					jl_rows = JSONArray.fromObject(msg.obj.toString());
					addjl();
					if (book_yuyue_jlgrouup != null
							&& book_yuyue_jlgrouup.getChildCount() > 0) {
						RadioButton rb = (RadioButton) book_yuyue_jlgrouup
								.getChildAt(0);
						book_yuyue_jlgrouup.check(rb.getId());
					}
				} else {

				}
				// �ر�
				if (loading != null) {
					loading.dismiss();
					loading = null;
				}
			}
		};

		// ��ʼ������ teaId=00000001&date=2014/10/20&page=1&rows=100&dutyType=0
		Map<String, Object> param = new HashMap<String, Object>();
		String teacherId = readString("coachId");
		if (teacherId != null && teacherId.trim().length() > 0) {
			param.put("coachId", teacherId);
		}

		if (kemu.equalsIgnoreCase("��Ŀ��"))
			param.put("subject", "1");
		else
			param.put("subject", "2");

		param.put("studyDate", switchDate(date));
		param.put("mobileFlag", readString("mobileFlag"));
		param.put("operateurl", operurl);
		param.put("encoding", getStringValue(R.string.encoding));

		// ��ȡ�û���ѡ�γ�
		// Map<String, Object> param_n = new HashMap<String, Object>();
		// �½���������
		GetHttpResultThread geter = new GetHttpResultThread(loginhander, param);
		// GetHttpResultThreadMore geter = new
		// GetHttpResultThreadMore(loginhander, param,operurl,);

		// ע�����
		loginhander.addtolisttask(geter);

		loading = new LoadingDialog_ui(this, R.style.loadingstyle,
				R.layout.dialog_loading_lay);
		loading.setCancelable(false);
		loading.show();

	}

	/**
	 * ��ӽ���
	 */
	private void addjl() {
		// �����߱�ͷ
		LinearLayout.LayoutParams title_time = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		title_time.setMargins(0, 0, 0, 1);

		LinearLayout.LayoutParams img_lay = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 1);
		img_lay.setMargins(0, 0, 0, 0);

		// ��ӽ���
		book_yuyue_jlgrouup.removeAllViews();
		for (int i = 0; i < jl_rows.size(); ++i) {
			RadioButton rb = (RadioButton) getmLayoutInflater().inflate(
					R.layout.book_yuyue_jl_item, null);
			JSONObject jobj = jl_rows.getJSONObject(i);
			rb.setText(jobj.getString("coachName"));
			rb.setTag(jobj.getString("coachId"));

			book_yuyue_jlgrouup.addView(rb);
			rb.setLayoutParams(title_time);
			// ��ӷָ���
			ImageView img = new ImageView(this);
			book_yuyue_jlgrouup.addView(img);
			img.setBackgroundResource(R.color.book_dateselect_jl_sp);
			img.setLayoutParams(img_lay);
		}
	}

	/** �����¼� */
	public boolean onTouch(View v, MotionEvent event) {
		try {
			// ��ȡ�������
			String jl, jl_time;
			String values = v.getTag().toString();

			// ��ȡ�����͡�ʱ��
			jl = values.split("~")[0];
			jl_time = values.split("~")[1];

			GradientDrawable gradientDrawable;
			View img = v.findViewById(R.id.book_yuyue_timeicon);

			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				if (img.getVisibility() == View.GONE) {
					img.setVisibility(View.VISIBLE);
					// ���� ��Բ��img
					v.setBackgroundResource(R.drawable.book_yuyue_dateselect_bg);
				} else {
					img.setVisibility(View.GONE);
					// ���� ��Բ��img
					v.setBackgroundResource(R.color.white);
				}
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				if (img.getVisibility() == View.VISIBLE) {
					Log.i("book_date", date+" "+v.getTag().toString().split("~")[1]);
					if(!compareDate(date+" "+v.getTag().toString().split("~")[1])){  //��ѡʱ��Ϊ��ȥ�¼�
						img.setVisibility(View.GONE);
						// ���� ��Բ��img
						v.setBackgroundResource(R.color.white);
						DisPlay("��ѡ��δ����ʱ��",false);
					}else 
						if (selected.size() < max_selected - allready) {
						add_xuanke(v);
					} else {
						img.setVisibility(View.GONE);
						// ���� ��Բ��img
						v.setBackgroundResource(R.color.white);
						DisPlay("���Ѿ�ԤԼ��" + allready + "�ſγ̣�������ԤԼ"
								+ (max_selected - allready) + "�ſγ̣��ܹ�����ԤԼ3�ſγ̡�",
								false);
					}
				} else
					delete_xuanke(v);

			} else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
				if (img.getVisibility() == View.GONE) {
					img.setVisibility(View.VISIBLE);
					// ���� ��Բ��img
					v.setBackgroundResource(R.drawable.book_yuyue_dateselect_bg);
				} else {
					img.setVisibility(View.GONE);
					// ���� ��Բ��img
					v.setBackgroundResource(R.color.white);
				}
			}
		} catch (Exception e) {
		}

		return false;
	}

	/**
	 * ɾ��ѡ��
	 * 
	 * @param view
	 */
	public void delete_xuanke(View view) {
		try {
			for (int index = 0; index < selected.size(); ++index) {
				if (view.getTag()
						.toString()
						.equalsIgnoreCase(
								selected.get(index).getTag().toString())) {
					delete_xuanke_view(view.getTag().toString());
					selected.remove(index);
					--index;
				}
			}
		} catch (Exception e) {
		}
	}

	/**
	 * ɾ��ѡ�� ָ��tag book_dateselect_selected.removeView(selected.get(index));
	 * 
	 * @param view
	 */
	private void delete_xuanke_view(String tag) {
		for (int i = 0; i < book_dateselect_selected.getChildCount(); i++) {
			View view = book_dateselect_selected.getChildAt(i);
			if (view != null && view.getTag().toString().equalsIgnoreCase(tag)) {
				book_dateselect_selected.removeViewAt(i);
				break;
			}
		}
	}

	public void add_xuanke(View view) {
		if(!NetUtil.isNetworkConnected(this)){
			Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
			return ;
		}
		// ��ȡ����Ĵ�С
		LinearLayout.LayoutParams c_params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		c_params.setMargins(0, 0, 0, 0);

		View child = getmLayoutInflater().inflate(
				R.layout.book_dateselect_xuanke, null);


		TextView tv = (TextView) child
				.findViewById(R.id.book_dateselect_selected_datetime);
		tv.setText(getTimeSlot(view.getTag().toString().split("~")[1]));

		tv = (TextView) child.findViewById(R.id.book_dateselect_selected_jl);
		tv.setText(view.getTag().toString().split("~")[2]);

		final TextView pt = (TextView) child
				.findViewById(R.id.book_dateselect_selected_price);
		pt.setText("��" + price);
		// ��ȡ�۸�����
		String operurl = getOperateURL(R.string.webbaseurl,
				R.string.get_jltime_price);
		// ��¼10s��ʱ ��ֻ��һ����Ϣ
		HanderListObject loginhander = new HanderListObject(5, true) {

			public void handleMessage(Message msg) {

				if (msg.what == 13) {

					// �������
					Main_fragment_ui.setLoginhttpclient(null);
					Toast.makeText(Book_dateselect_ui.this, "�Բ�����������",
							Toast.LENGTH_SHORT).show();

				} else if (msg.what == 11) {
					JSONObject json = JSONObject.fromObject(msg.obj.toString());
					Log.i("Book", json.toString());
					price = json.getInt("price") + "";
					pt.setText("��" + price);
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

		param.put("coachId", view.getTag().toString().split("~")[0]);
		param.put("studyDate", switchDate(date));
		param.put("time", Integer
				.parseInt(view.getTag().toString().split("~")[1]
						.substring(0, 2)));
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
		// ***********************��ȡ�۸����ݽ���
		book_dateselect_selected.addView(child);
		child.setLayoutParams(c_params);

		ImageButton ib = (ImageButton) child
				.findViewById(R.id.book_dateselect_deletexuanke_ib);
		ib.setTag(view.getTag());
		ib.setOnClickListener(this);

		// ���ѡ��
		selected.add((LinearLayout) child);

		// view.gettag()
		child.setTag(view.getTag());
	}

	/**
	 * ���ѡ��
	 * 
	 * @param s
	 */
	public void next_step(View s) {
		date = DateStruct.recon_date(date);
		// jsonStr="[{"studyDate":"2014-11-23","coachId":"1","studyTime":"12:00-13:00","subject","1","payWay":"1"}]"
		// view.setTag(jl_id + "~" +bookdates[i * 3 + j].toString()+ "~"
		// + jl_name);
		JSONArray array = new JSONArray();
		JSONObject obj;
		for (View view : selected) {
			Log.i(TAG, date);
			String info = view.getTag().toString();
			String[] ars = info.split("~");
			obj = new JSONObject();
			obj.put("coachId", ars[0]);
			obj.put("studyDate", date);
			obj.put("studyTime", getTimeSlot(ars[1]));
			obj.put("subject", kemu.equalsIgnoreCase("��Ŀ��") ? "2" : "1");
			obj.put("planWay", "1");
			array.add(obj);
		}
		load_web_data_add(array);
	}

	private void load_web_data_add(JSONArray ja) {
		if (ja == null || ja.size() == 0) {
			// �����ʼ������
			Intent intent = new Intent(Book_dateselect_ui.this,
					Book_yuyue_ui.class);
			startActivity(intent);
			return;
		}
		if(!NetUtil.isNetworkConnected(this)){
			Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
			return ;
		}
		String operurl = getOperateURL(R.string.webbaseurl,
				R.string.get_addplan);
		// ��¼10s��ʱ ��ֻ��һ����Ϣ
		HanderListObject loginhander = new HanderListObject(6, true) {

			public void handleMessage(Message msg) {

				// DisPlay(msg.obj.toString(), true);
				if (msg.what == 13) {

					// �������
					Main_fragment_ui.setLoginhttpclient(null);
					Toast.makeText(Book_dateselect_ui.this, "�Բ�����������",
							Toast.LENGTH_SHORT).show();
				} else if (msg.what == 11) {
					// System.out.println(msg.obj.toString());
					Log.i("book_dateselect_ui", "���ԤԼ�ɹ�");
					DisPlay("ԤԼ�ɹ�", true);
					// ԤԼ��Ĵ���
				} else {
					DisPlay(msg.obj.toString(), true);
				}
				// �����ʼ������
				Intent intent = new Intent(Book_dateselect_ui.this,
						Book_yuyue_ui.class);
				startActivity(intent);
				finish();
				// �ر�
				if (loading != null) {
					loading.dismiss();
					loading = null;
				}
			}
		};

		Map<String, Object> param = new HashMap<String, Object>();

		param.put("jsonStr", ja.toString());
		param.put("mobileFlag", readString("mobileFlag"));
		param.put("operateurl", operurl);
		param.put("encoding", getStringValue(R.string.encoding));
		// �½���������
		ADDXuankeThread geter = new ADDXuankeThread(loginhander, param);

		// ע�����
		loginhander.addtolisttask(geter);

		loading = new LoadingDialog_ui(this, R.style.loadingstyle,
				R.layout.dialog_loading_lay);
		loading.setCancelable(false);
		loading.show();

	}

	/**
	 * ѡ���������
	 */
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		RadioButton rb = (RadioButton) group.findViewById(checkedId);
		String jl_name = rb.getText().toString(), jl_id = rb.getTag()
				.toString();
		// ��ӽ���ʱ��
		addjl_time(jl_name, jl_id);

	}

	/**
	 * ��ӽ������е�ʱ��
	 * 
	 * @param jl_id
	 * @param jl_name
	 */
	private void addjl_time(String jl_name, String jl_id) {

		Map<String, Integer> data = analysis_jl(jl_id, jl_name);
		bookdates = data.keySet().toArray();

		// ��Ϊ��ߵĽ�����ȥ100
		int width = getWidth() - AndroidUtil.dip2px(this, 80);
		// ��3��ʱ��ѡ�����
		LinearLayout.LayoutParams c_params = new LinearLayout.LayoutParams(
				width / 3, width / 3);
		c_params.setMargins(0, 0, 0, 0);

		// ������
		LinearLayout.LayoutParams r_params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		r_params.setMargins(0, 0, 0, 0);

		// ����м����
		View view;
		TextView book_yuyue_timetext;
		TextView state;
		LinearLayout liner;
		dateselect_right_title.removeAllViews();

		for (int i = 0; i <= bookdates.length / 3; ++i) {
			liner = new LinearLayout(this);
			liner.setOrientation(LinearLayout.HORIZONTAL);

			for (int j = 0; j < 3; j++) {
				// �������Ѿ��ﵽ������ӵ�е�ʱ������
				if (i * 3 + j >= bookdates.length)
					break;
				view = getmLayoutInflater().inflate(
						R.layout.book_yuyue_jl_time_item, null);

				liner.addView(view);
				// ��¼��λ��
				view.setLayoutParams(c_params);

				book_yuyue_timetext = (TextView) view
						.findViewById(R.id.book_yuyue_timetext);
				state = (TextView) view.findViewById(R.id.book_yuyue_statetext);

				book_yuyue_timetext.setText(bookdates[i * 3 + j].toString());
				// ״̬����
				if (data.get(bookdates[i * 3 + j]) == 0) {
					state.setVisibility(View.GONE);
				}
				Log.i("���ڲ���",
						"i:" + i + " j:" + j + " time:"
								+ bookdates[i * 3 + j].toString() + " state:"
								+ data.get(bookdates[i * 3 + j]));
				// ��ȡ���Ե����view ����һ��view
				view = view.findViewById(R.id.dateselect_item_layout);

				view.setTag(jl_id + "~" + bookdates[i * 3 + j].toString() + "~"
						+ jl_name);

				// ����Ƿ����
				if (data.get(bookdates[i * 3 + j]) == 0) {
					view.setOnTouchListener(this);
					try {
						for (int index = 0; index < selected.size(); ++index) {
							View v = selected.get(index);
							if (v.getTag().toString()
									.equalsIgnoreCase(view.getTag().toString())) {
								// ����ѡ��״̬
								View img = view
										.findViewById(R.id.book_yuyue_timeicon);
								img.setVisibility(View.VISIBLE);
								// ���� ��Բ��img
								view.setBackgroundResource(R.drawable.book_yuyue_dateselect_bg);

								selected.set(index, (LinearLayout) view);
							}
						}
					} catch (Exception e) {
					}
				} else {
					view.setBackgroundResource(R.color.book_dateselect_unselected_color);
				}

			}
			if (i * 3 >= bookdates.length) {
				break;
			}
			dateselect_right_title.addView(liner);
			liner.setLayoutParams(r_params);
		}
	}

	/**
	 * ����������ʱ��
	 */
	// TODO
	private Map<String, Integer> analysis_jl(String jl_id, String jl_name) {
		Map<String, Integer> map = new LinkedHashMap<String, Integer>();
		String match = "^[ ]*[tT][0-2][0-9]$[ ]*";
		try {
			JSONObject jobj;
			for (int i = 0; i < jl_rows.size(); ++i) {
				try {
					jobj = jl_rows.getJSONObject(i);
					if (jobj.getString("coachId").toString()
							.equalsIgnoreCase(jl_id)
							&& jobj.getString("coachName").toString()
									.equalsIgnoreCase(jl_name)) {
						Iterator iter = jobj.keySet().iterator();

						while (iter.hasNext()) {
							String obj = iter.next().toString();
							// �����ʱ��
							if (obj.matches(match)) {
								map.put(obj.trim().substring(1,
										obj.trim().length())
										+ ":00", jobj.getInt(obj));
							}
						}
						break;
					}
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
		}
		return map;
	}

	private long resumecount = 0;

	protected void onResume() {
		if (resumecount > 0) {
			findViewById_Init();
			initView();
			initData();
		}
		resumecount++;
		super.onResume();
	}

	/**
	 * ��ʱ��ת��ʱ���
	 * 
	 * @param time
	 * @return
	 */
	public String getTimeSlot(String time) {
		int t = Integer.parseInt(time.substring(0, 2));
		int t2 = t + 1;
		if (t2 < 10) {
			return time + "-" + "0" + t2 + ":00";
		}
		return time + "-" + t2 + ":00";
	}

	/**
	 * ɾ��ѡ��
	 */
	public void onClick(View v) {
		delete_xuanke(v);
		try {
			String value = v.getTag().toString();
			String[] values = value.split("~");

			// ѡ��Ҫ�����Ľ���
			r_b_g_checked(values[0], values[2]);

		} catch (Exception e) {
		}

	}

	/** ѡ�� ָ��id�� �ڵ� */
	public void r_b_g_checked(String jl_id, String jl_name) {
		try {
			// ��ȡ��ǰ��ѡ��
			int rb_now = book_yuyue_jlgrouup.getCheckedRadioButtonId();
			RadioButton rb = (RadioButton) book_yuyue_jlgrouup
					.findViewById(rb_now);

			String now_id = rb.getTag() == null ? "" : rb.getTag().toString()
					.trim();
			// ����ǲ��� ��ǰ�Ľ��� ����� ��ɾ�� �ý���ѡ�� �������ֻɾ������
			if (now_id.length() > 0 && now_id.equalsIgnoreCase(jl_id)) {
				// ��ӽ���ʱ��
				addjl_time(jl_name, jl_id);
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

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	   /**
	    * �Ƚ��ַ���ʱ��͵�ǰʱ��
	    * @param strDate
	    * @return true�ַ���ʱ��ȵ�ǰʱ���false �ȵ�ǰʱ��С
	    */
		private boolean compareDate(String strDate){
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	    	boolean temp = false ;
	    	Date date = null;
			try {
				date = sdf.parse(strDate);
		    	if(date.compareTo(new Date())>0){
		    		temp = true ; 
		    	}
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			return temp;
	    	
	    }
}
