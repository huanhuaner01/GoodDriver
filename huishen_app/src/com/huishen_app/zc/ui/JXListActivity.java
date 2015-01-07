package com.huishen_app.zc.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.adapter.JX_Search_Adapter;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zh.util.TextStyleUtil;

@SuppressLint("ValidFragment")
public class JXListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{


	private static final int REFRESH_COMPLETE = 0X110;
	private SwipeRefreshLayout mSwipeLayout;
	private TextView title ;
	
	private List<Map<String, Object>> listview_date;
	private JX_Search_Adapter adapter;

	private ListView jx_search_listview;

	private Map<String, Object> param;


	private void init_data() {
		// 定义资源1
		String[] from = {"jxname" , "addr"};
		int[] to = {R.id.jx_select_name ,R.id.jx_select_addr };
		listview_date = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jxname", "蜀鹃驾校");
		map.put("addr", "郫县红光镇川交厂内     >2km");
		map.put("price", TextStyleUtil.getTextAppearanceSpan(this, "3999"));
		listview_date.add(map);
        
		adapter = new JX_Search_Adapter(this, listview_date,
				R.layout.jx_select_item, from, to);

		jx_search_listview.setAdapter(adapter);
		jx_search_listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i = new Intent(JXListActivity.this ,JXDetailActivity.class);
				startActivity(i);
			}
			
		});
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(android.R.color.holo_green_dark, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);
	}

	@Override
	protected void findViewById_Init() {
		setContentView(R.layout.jxlist_lay);
		jx_search_listview = (ListView) findViewById(R.id.jx_list);
		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
		title = (TextView)findViewById(R.id.header_title);
		init_data();
	}

	@Override
	protected void initView() {
		title.setText("找驾校");
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
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
	
	public void onRefresh()
	{
		// Log.e("xxx", Thread.currentThread().getName());
		// UI Thread

		mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);

	}
	
	private Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{ 
			switch (msg.what)
			{
			case REFRESH_COMPLETE:
				adapter.notifyDataSetChanged();
				mSwipeLayout.setRefreshing(false);
				break;

			}
		};
	};
}
