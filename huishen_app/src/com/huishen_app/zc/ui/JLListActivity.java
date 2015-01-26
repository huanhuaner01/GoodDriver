package com.huishen_app.zc.ui;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.adapter.JL_Search_Adapter;
import com.huishen_app.zc.ui.base.BaseActivity;

@SuppressLint("ValidFragment")
public class JLListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

	private static final int REFRESH_COMPLETE = 0X110;
	private SwipeRefreshLayout mSwipeLayout;
	private TextView title ;
	private PopupWindow conditionPopWindow;// 条件搜索浮动窗口
	private GridView popGrid ; 
	private ArrayList<HashMap<String , Object>> popdata ; //popwindow的数据
	private 
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
		map.put("photo","http://picture.51efu.cn/images/201104/thumb_img/2507_thumb_P_1304045401776.jpg");
		listview_date.add(map);
		map = new HashMap<String, Object>();
		map.put("photo","http://img5.imgtn.bdimg.com/it/u=3793805207,3272353858&fm=21&gp=0.jpg");
		listview_date.add(map);
		map = new HashMap<String, Object>();
		map.put("photo","http://pic1.win4000.com/pic/1/ac/1fb0489694.jpg");
		listview_date.add(map);
		map = new HashMap<String, Object>();
		map.put("photo","http://www.tbwfy.com/tufyL3R1ZnlpbWcwMi50YW9iYW9jZG4uY29tL2Jhby91cGxvYWRlZC9pMi8xNzcwNDAyOTEyMDc2ODEwNC9UMTNVd0JYbFJiWFhYWFhYWFhfISEwLWl0ZW1fcGljLmpwZw==.jpg");
		listview_date.add(map);
		map = new HashMap<String, Object>();
		map.put("photo","http://preview.quanjing.com/mf025/mf700-00609701.jpg");
		listview_date.add(map);

		adapter = new JL_Search_Adapter(this, listview_date,
				R.layout.jl_select_item, from, to);

		jx_search_listview.setAdapter(adapter);
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(R.color.color_refresh_1, R.color.color_refresh_2,
				R.color.color_refresh_3, R.color.color_refresh_4);
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
	
	/**
	 * 条件搜索
	 * @param v
	 */
	public void conditionSearch(View v){

	
		switch(v.getId()){
		case R.id.jl_list_condition_location:
		
			conditionPopWindow.showAsDropDown(v);
			break ;
		case R.id.jl_list_condition_year:
			conditionPopWindow.showAsDropDown(v);
			break ;
		case R.id.jl_list_condition_sex :
			break ;
		case R.id.jl_list_condition_more:
			break ;
	    default :
				if(conditionPopWindow.isShowing()){
				conditionPopWindow.dismiss() ;
				}
		}
	}
	/**
	 * 初始化
	 */
	private void setPopWindow(){
		View layout = LayoutInflater.from(this).inflate(R.layout.popwindow_ly, null); 
		GridView grid = (GridView)layout.findViewById(R.id.grid);
		popdata = new ArrayList<HashMap<String ,Object>>();
		for(int i = 0 ;i< 10 ;i++){
			HashMap<String , Object> map = new HashMap<String , Object>();
			popdata.add(map) ;
		}
		adapter = new SimpleAdapter(this,popdata ,R.layout.popwindow_item ,new String[]{} ,new int[]{});
		grid.setAdapter(adapter) ;
		conditionPopWindow = new PopupWindow(layout,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		conditionPopWindow.setFocusable(true);// 加上这个popupwindow中的ListView才可以接收点击事件

		// 控制popupwindow点击屏幕其他地方消失
		conditionPopWindow.setBackgroundDrawable(new BitmapDrawable());// 设置背景图片，不能在布局中设置，要通过代码来设置
		conditionPopWindow.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功，如上
	}
}
