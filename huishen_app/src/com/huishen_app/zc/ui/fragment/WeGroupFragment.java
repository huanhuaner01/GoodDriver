package com.huishen_app.zc.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.WeGroupDetailActivity;
import com.huishen_app.zc.ui.adapter.WeGroupAdapter;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.ui.fragment.BaseFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class WeGroupFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
	
	private static final int REFRESH_COMPLETE = 0X110;
	private SwipeRefreshLayout mSwipeLayout;
	private ListView listView;
	private WeGroupAdapter adapter;
	private TextView city , title ;
	/** 父类对象 */
	private BaseActivity father;

	public WeGroupFragment(BaseActivity father) {
		super(father);
		this.father = father ;

	}

	@Override
	public void onResume() {
		if(father.readString("city")!= null &&!father.readString("city").equals("")){
			city.setText(father.readString("city").toString());
		}
		super.onResume();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = null;
		try {
			view = inflater.inflate(R.layout.fragment_wegroup, null);
			findViewById_Init(view); //组件注册
			initView(view); //初始化
		} catch (Exception e) {
			
		}
		return view;
	}

	private void initView(View view) {
		if(father.readString("city")!= null &&!father.readString("city").equals("")){
			city.setText(father.readString("city").toString());
		}
//		city.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View arg0) {
//				Intent i = new Intent(father ,LocationActivity.class);
//				father.startActivity(i);
//			}
//			
//		});
		title.setText("大家团");
		

		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(android.R.color.holo_green_dark, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		for(int i = 0 ; i< 10 ; i++){
		  HashMap<String, Object> map = new HashMap<String , Object>();
		  map.put("class", "VIP班");
		  maps.add(map);
		}
		adapter = new WeGroupAdapter(this.father , maps,new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent(father ,WeGroupDetailActivity.class);
				father.startActivity(i);
			}
			
		});
		listView.setAdapter(adapter);
	}

	private void findViewById_Init(View view) {
		mSwipeLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.id_swipe_ly);
		listView = (ListView) view.findViewById(R.id.list_view);
		city = (TextView)view.findViewById(R.id.header_city);
		title = (TextView)view.findViewById(R.id.header_title);

	}

	@Override
	public void onRefresh() {
		mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
	}
	
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

}
