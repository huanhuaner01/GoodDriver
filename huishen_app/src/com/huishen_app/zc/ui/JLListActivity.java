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
import com.huishen_app.zc.ui.adapter.JL_Search_Adapter;
import com.huishen_app.zc.ui.adapter.JX_Search_Adapter;
import com.huishen_app.zc.ui.base.BaseActivity;

@SuppressLint("ValidFragment")
public class JLListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

	private static final int REFRESH_COMPLETE = 0X110;
	private SwipeRefreshLayout mSwipeLayout;
	private TextView title ;
	
	private List<Map<String, Object>> listview_date;
	private JL_Search_Adapter adapter;

	private ListView jx_search_listview;

	private Map<String, Object> param;

	private Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
			case REFRESH_COMPLETE:
//				mDatas.addAll(Arrays.asList("Lucene", "Canvas", "Bitmap"));
				adapter.notifyDataSetChanged();
				mSwipeLayout.setRefreshing(false);
				break;

			}
		};
	};
	
	private void init_data() {
		// 定义资源1
		String[] from = {};
		int[] to = {};
		listview_date = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("photo","http://preview.quanjing.com/mf025/mf700-00609711.jpg");
		listview_date.add(map);
		map = new HashMap<String, Object>();
		map.put("photo","http://img5.imgtn.bdimg.com/it/u=3793805207,3272353858&fm=21&gp=0.jpg");
		listview_date.add(map);
		map = new HashMap<String, Object>();
		map.put("photo","http://pic1.win4000.com/pic/1/ac/1fb0489694.jpg");
		listview_date.add(map);
		map = new HashMap<String, Object>();
		map.put("photo","http://preview.quanjing.com/mf025/mf700-00609710.jpg");
		listview_date.add(map);
		map = new HashMap<String, Object>();
		map.put("photo","http://preview.quanjing.com/mf025/mf700-00609701.jpg");
		listview_date.add(map);

		adapter = new JL_Search_Adapter(this, listview_date,
				R.layout.jl_select_item, from, to);

		jx_search_listview.setAdapter(adapter);
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(android.R.color.holo_green_dark, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);
		jx_search_listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i = new Intent(JLListActivity.this ,JLDetailActivity.class);
				startActivity(i);
			}
			
		});
	}

	@Override
	protected void findViewById_Init() {
		setContentView(R.layout.jllist_lay);
		jx_search_listview = (ListView) findViewById(R.id.jl_list);
		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
		title = (TextView)findViewById(R.id.header_title);
		init_data();
	}

	@Override
	protected void initView() {
		title.setText("找教练");
	}

	@Override
	protected void initData() {
		
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
		
		mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);

	}
}
