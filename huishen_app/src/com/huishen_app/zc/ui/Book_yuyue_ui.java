package com.huishen_app.zc.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.huishen_app.all.alarmservice.HanderListObject;
import com.huishen_app.zc.operate_thread.DeleteXuankeThread;
import com.huishen_app.zc.operate_thread.GetHttpResultThread;
import com.huishen_app.zc.ui.adapter.Book_YuYue_Adapter;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.ui.dialog.LoadingDialog_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class Book_yuyue_ui extends BaseActivity {

	private TextView head_title;

	private ListView book_xuyue_selected;

	private List<Map<String, Object>> listview_date;

	private Book_YuYue_Adapter adapter;

	private JSONArray his_book;

	private List<String> select_id;

	private boolean opentype = false;
    
	private String[] lessons = new String[]{"科目一","科目二","科目三","科目四"};
	
	@Override
	protected void findViewById_Init() {
		setContentView(R.layout.book_yuyue_lay);

		head_title = (TextView) findViewById(R.id.header_title);
		book_xuyue_selected = (ListView) findViewById(R.id.book_yuyue_selected);

	}

	protected void initView() {
		select_id = new ArrayList<String>();
		// 设置标题
		head_title.setText(getString(R.string.book_head_xuece));
		//***************************初始化列表*******************************************//
		
		//设置选择模式为多选模式
		book_xuyue_selected.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		// 定义资源
		listview_date = new ArrayList<Map<String, Object>>();
		String[] from = { "date", "time", "kemu", "jl","price",
				"status" };
		int[] to = { R.id.book_yuyue_his_date,
				R.id.book_yuyue_his_time, R.id.book_yuyue_his_kemu,
				 R.id.book_yuyue_his_jl,R.id.book_yuyue_his_price,
				R.id.book_yuyue_his_states };

		adapter = new Book_YuYue_Adapter(Book_yuyue_ui.this,
				listview_date,
				R.layout.book_dateselect_xuanke_item, from, to);
        
		book_xuyue_selected.setAdapter(adapter);
		book_xuyue_selected.setOnItemClickListener(new OnItemClickListener(){    
            @Override    
            public void onItemClick(AdapterView<?> parent, View view,    
                    int position, long id) {
            	//刷新列表
             	adapter.notifyDataSetChanged();
            	
            	String viewid = view.getTag() == null ? "" : view.getTag()
        				.toString();
        		if (book_xuyue_selected.isItemChecked(position)) {// 添加
        			select_id.add(viewid);
        			
        		} else {// 删除
        			for (int index = 0; index < select_id.size(); ++index) {
        				if (select_id.get(index).equalsIgnoreCase(viewid)) {
        					select_id.remove(index);
        					--index;
        				}
        			}
        		}   
            }    
        });
		
	  //***************************初始化列表结束*******************************************//
	    
	    
		Object obj = getParams("opentype");
		if (obj == null || obj.toString().trim().length() == 0)
			opentype = false;
		else
			opentype = true;
	}

	protected void initData() {
	   //清空选中列表
		book_xuyue_selected.clearChoices();
		load_web_data_resume();

	}

	public void addyuyue(View view) {
		if (opentype) {
			// 打开 选课 关闭当前的
			Intent i = new Intent(this, Book_yuyue_first_ui.class);
			startActivity(i);
		}
		finish();
	}

	public void delete_xuanke(View view) {
		//清空选中列表
		book_xuyue_selected.clearChoices();
		if (select_id == null || select_id.size() == 0) {
			DisPlay("没有预约的课程", true);
			return;
		}
		Log.i("删除预约",select_id.toString());
		load_web_data(select_id);
	}

	private void load_web_data(List<String> idlist) {
		String operurl = getOperateURL(R.string.webbaseurl,
				R.string.get_cancelplan);
		// 登录10s超时 且只收一条消息
		HanderListObject loginhander = new HanderListObject(12, true) {
			public void handleMessage(Message msg) {
				if(msg.what == 13){
					Toast.makeText(Book_yuyue_ui.this,"对不起，您已下线，请重新登录", Toast.LENGTH_SHORT).show();
					Main_fragment_ui.setLoginhttpclient(null);
				}else
				if (msg.what == 11) {
					Toast.makeText(Book_yuyue_ui.this, "删除成功", Toast.LENGTH_SHORT).show();
					
					load_web_data_resume();
					
				} else {
					if (loading != null) {
						loading.dismiss();
						loading = null;
					}
				}
			}
		};

		// 初始化参数 stuId=1&date=2014/10/03&page=1&rows=2&dutyType=0
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("idstr", idlist);
		param.put("operateurl", operurl);
		param.put("encoding", getStringValue(R.string.encoding));
		param.put("mobileFlag", readString("mobileFlag"));
		// 新建操作对象
		DeleteXuankeThread geter = new DeleteXuankeThread(loginhander, param);

		// 注册监听
		loginhander.addtolisttask(geter);
		loading = new LoadingDialog_ui(this, R.style.loadingstyle,
				R.layout.dialog_loading_lay);
		loading.setCancelable(false);
		loading.show();
	}

	private void load_web_data_resume() {
        //清空数据
		book_xuyue_selected.clearChoices();
		select_id.clear();
		listview_date.clear();
		
		String operurl = getOperateURL(R.string.webbaseurl, R.string.get_yuyue);
		// 登录10s超时 且只收一条消息
		HanderListObject loginhander = new HanderListObject(6, true) {

			public void handleMessage(Message msg) {
				if(msg.what == 13){
					Toast.makeText(Book_yuyue_ui.this,"对不起，您已下线，请重新登录", Toast.LENGTH_SHORT).show();
					Main_fragment_ui.setLoginhttpclient(null);
				}else
				if (msg.what == 11) {
					
					his_book = JSONArray
							.fromObject(msg.obj.toString());
                   Log.i("还好",his_book.toString()); 
                   
                   int yuyuenum = Integer.parseInt(readString("yuyuenum"));
                   if(his_book.size()!=yuyuenum){
                	   saveString("yuyuenum",his_book.size()+"");
                   }
					
					HashMap<String, Object> map;
					for (int index = 0; index < his_book.size(); ++index) {
						map = new HashMap<String, Object>();
						JSONObject	j_obj = his_book.getJSONObject(index);
//                        
                      String date = ToDate(j_obj.getString("studyDate"));
//                      String time = ToTime(j_obj.getString("beginTime"),j_obj.getString("endTime"));
						map.put("date",date);
						map.put("yuyue_id", j_obj.get("id"));

						map.put("time",j_obj.getString("studyTime"));
						map.put("kemu",lessons[j_obj.getInt("subject")]);
						map.put("jl", j_obj.get("name"));
                        map.put("price","￥"+(j_obj.get("price")==null?"0":j_obj.get("price")));
//						map.put("status_id", j_obj.get("status").toString());
//						map.put("status", j_obj.get("status").toString()
//								.equalsIgnoreCase("0") ? "带分配" : Html.fromHtml("<font color=\"red\">已分配</font>"));
						listview_date.add(map);
					}
					//通知列表适配器更新数据
					adapter.notifyDataSetChanged();
				    
				} else {
                    
				}
				// 关闭
				if (loading != null) {
					loading.dismiss();
					loading = null;
				}
			}
		};

		// 初始化参数 stuId=1&date=2014/10/03&page=1&rows=2&dutyType=0
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("id", readString("user_id"));
		param.put("mobileFlag", readString("mobileFlag"));

		param.put("operateurl", operurl);
		param.put("encoding", getStringValue(R.string.encoding));
		// 新建操作对象
		GetHttpResultThread geter = new GetHttpResultThread(loginhander, param);

		// 注册监听
		loginhander.addtolisttask(geter);

		if (loading == null) {
			loading = new LoadingDialog_ui(this, R.style.loadingstyle,
					R.layout.dialog_loading_lay);
			loading.setCancelable(false);
			loading.show();
		}

	}
	
	/**
	  * 将短时间格式字符串转换为时间 yyyy-MM-dd 
	  * 
	  * @param strDate
	  * @return
	  */
	public String ToDate(String strDate) {
		Log.i("woyaofengle", strDate);
		String result  = null ;
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	  ParsePosition pos = new ParsePosition(0);
	  Date strtodate = null;
	try {
		strtodate = formatter.parse(strDate);
		formatter = new  SimpleDateFormat("yyyy-MM-dd");
		result = formatter.format(strtodate);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  return result;
	}
	/**
	  * 将短时间格式字符串转换为时间 段
	  * 
	  * @param strDate
	  * @return
	  */
	public String ToTime(String begin,String end) {
		String result  = null ;
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  Date begintime = null;
	  Date endTime = null;
	try {
		begintime = format.parse(begin);
		endTime = format.parse(end);
		format = new SimpleDateFormat("HH:mm");
		result = format.format(begintime)+"-"+format.format(endTime);
	} catch (ParseException e) {
		e.printStackTrace();
	}
	 
	  return result;
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
