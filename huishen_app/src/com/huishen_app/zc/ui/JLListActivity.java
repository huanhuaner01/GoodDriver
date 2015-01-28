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
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.adapter.JL_Search_Adapter;
import com.huishen_app.zc.ui.adapter.JlListPopGridAdapter;
import com.huishen_app.zc.ui.base.BaseActivity;
/**
 * 教练列表
 * @author zhanghuan
 *
 */
@SuppressLint("ValidFragment")
public class JLListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

	private static final int REFRESH_COMPLETE = 0X110;
	private SwipeRefreshLayout mSwipeLayout;
	
	//标题栏相关
	private TextView title ;
	private Button cancel , search ;
	private ImageButton searchimg ;
	private EditText searchEdit ;
	private RelativeLayout lay1 ,lay2 ;
	
	private PopupWindow conditionPopWindow;// 条件搜索浮动窗口
	private GridView popGrid ; 
	private List<Map<String , Object>> popdata ; //popwindow的数据
	private JlListPopGridAdapter popAdapter ;
	
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
		cancel = (Button)findViewById(R.id.header_cancel);
		searchimg = (ImageButton)findViewById(R.id.header_search);
		search = (Button) findViewById(R.id.header_btn_search);
		lay1 = (RelativeLayout) findViewById(R.id.header_lay1);
		lay2 = (RelativeLayout) findViewById(R.id.header_lay2);
	}

	@Override
	protected void initView() {
		title.setText("找教练");
		this.searchimg.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
			    lay1.setVisibility(View.GONE) ;
			    lay2.setVisibility(View.VISIBLE) ;
			}
			
		});
		this.cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				 lay1.setVisibility(View.VISIBLE) ;
				 lay2.setVisibility(View.GONE) ;
			}
			
		}) ;
		init_data();
		setPopWindow();
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
		final TextView tv = (TextView)v ;
		popdata.clear() ;
		  HashMap<String ,Object> map = new HashMap<String ,Object>();
	    	map.put("value", "不限") ;
	    	map.put("status", 1 ) ;
	    	popdata.add(map) ;
		switch(v.getId()){
		case R.id.jl_list_condition_location:
		    for(int i = 0 ; i<10 ; i++){
		    	map = new HashMap<String ,Object>();
		    	map.put("value", "双流") ;
		    	map.put("status", 0 ) ;
		    	popdata.add(map) ;
		    }
	    	popAdapter.notifyDataSetChanged();
	    	popGrid.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int arg2, long arg3) {
					conditionPopWindow.dismiss() ;
					tv.setText(view.getTag()+"") ;
				}
	    	});
			conditionPopWindow.showAsDropDown(v);
			break ;
		case R.id.jl_list_condition_year:
			 String[] years = new String[]{"5年以下","5-10年" ,"10年以上"};
			   for(int i = 0 ; i<years.length ; i++){
			    	map = new HashMap<String ,Object>();
			    	map.put("value", years[i]) ;
			    	map.put("status", 0 ) ;
			    	popdata.add(map) ;
			    }
		    	popAdapter.notifyDataSetChanged();
		    	popGrid.setOnItemClickListener(new OnItemClickListener(){
					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int arg2, long arg3) {
						conditionPopWindow.dismiss() ;
						tv.setText(view.getTag()+"") ;
					}
		    	});
				conditionPopWindow.showAsDropDown(v);
			conditionPopWindow.showAsDropDown(v);
			break ;
		case R.id.jl_list_condition_sex :
			 String[] sexs = new String[]{"男","女" };
			   for(int i = 0 ; i<sexs.length ; i++){
			    	map = new HashMap<String ,Object>();
			    	map.put("value", sexs[i]) ;
			    	map.put("status", 0 ) ;
			    	popdata.add(map) ;
			    }
		    	popAdapter.notifyDataSetChanged();
		    	popGrid.setOnItemClickListener(new OnItemClickListener(){
					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int arg2, long arg3) {
						conditionPopWindow.dismiss() ;
						tv.setText(view.getTag()+"") ;
					}
		    	});
				conditionPopWindow.showAsDropDown(v);
			conditionPopWindow.showAsDropDown(v);
			break ;
		case R.id.jl_list_condition_more:
			 String[] mores = new String[]{"评价最高","驾龄最高","教龄最高","通过率最高"};
			   for(int i = 0 ; i<mores.length ; i++){
			    	map = new HashMap<String ,Object>();
			    	map.put("value", mores[i]) ;
			    	map.put("status", 0 ) ;
			    	popdata.add(map) ;
			    }
		    	popAdapter.notifyDataSetChanged();
		    	popGrid.setOnItemClickListener(new OnItemClickListener(){
					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int arg2, long arg3) {
						conditionPopWindow.dismiss() ;
						tv.setText(view.getTag()+"") ;
					}
		    	});
				conditionPopWindow.showAsDropDown(v);
			conditionPopWindow.showAsDropDown(v);
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
		popGrid = (GridView)layout.findViewById(R.id.grid);
		popdata = new ArrayList<Map<String ,Object>>();
		popAdapter = new JlListPopGridAdapter(this,popdata ,R.layout.popwindow_item ,new String[]{"value"} ,new int[]{R.id.jl_pop_itemtv});
		popGrid.setAdapter(popAdapter) ;
		conditionPopWindow = new PopupWindow(layout,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		conditionPopWindow.setFocusable(true);// 加上这个popupwindow中的ListView才可以接收点击事件

		// 控制popupwindow点击屏幕其他地方消失
		conditionPopWindow.setBackgroundDrawable(new BitmapDrawable());// 设置背景图片，不能在布局中设置，要通过代码来设置
		conditionPopWindow.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功，如上
	}
}
