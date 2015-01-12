package com.huishen_app.zc.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.base.BaseActivity;

/**
 * 带有标题的listFragment
 * @author zhanghuan
 *
 */
public class TitleListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
	//下拉刷新组件
	private SwipeRefreshLayout mSwipeLayout;
	/** 返回上一个fragment */
	public int BACK_FRAGMENT = 0 ; 
	/** 返回上一个activity **/
	public int BACK_ACTIVITY =1 ; 
	private View RootView ;  //根组件
	private TextView title ,note; //标题,位于右边的文字
	private ListView list ; //列表
	private Button back ; //返回键
    private String titlestr ;
    private String url ;
    private String data ; //数据列表
    private TitleListInterface listinterface ; 
    private int backmodel ;
    
	public TitleListFragment(BaseActivity father,String titlestr , String url ,int backmodel) {
		super(father);
		this.titlestr = titlestr ;
		this.url = url ;
		this.backmodel = backmodel ;
	}

	public TitleListFragment(BaseActivity father, Object tag,String titlestr , String url ,int bakmodel) {
		super(father, tag);
		this.titlestr = titlestr ;
		this.url = url ;
		this.backmodel = backmodel ;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		    
		try{
			RootView = inflater.inflate(R.layout.fragment_titlelist, null);
			registView();
			initView();
		  } catch (Exception e) {
		}
		return RootView;
	}

	private void registView() {
		this.title = (TextView)RootView.findViewById(R.id.header_title) ;
		this.note = (TextView)RootView.findViewById(R.id.header_note) ;
		this.list = (ListView)RootView.findViewById(R.id.titlelist_list) ;
		this.back = (Button)RootView.findViewById(R.id.header_back) ;
		this.mSwipeLayout = (SwipeRefreshLayout)RootView.findViewById(R.id.swipe_ly);
	}

	private void initView() {
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(R.color.color_refresh_1, R.color.color_refresh_2,
				R.color.color_refresh_3, R.color.color_refresh_4);
		//设置标题
		if(titlestr != null){
			this.title.setText(titlestr) ;
		}
		//获取网络数据
		getWebData();
		
		//设置返回键监听
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				 FragmentManager fm = getFragmentManager();  
			     fm.popBackStack();
			}
			
		}) ;
	}
	
	/**
	 * 访问网络，获取网络数据
	 */
	private void getWebData(){
		//如果url为空或者list接口为空则不访问网络，直接返回
//		if(url == null || url.equals("")||listinterface == null){
//			return ;
//		}
		listinterface.setList(data, list);
	}
	
	/***
	 * 设置接口
	 * @param listinterface
	 */
	public void setTitleList(TitleListInterface listinterface){
		this.listinterface = listinterface ;
	}
	/**
	 * 设置标题栏备注备注
	 * @param isShow
	 * @param listener
	 */
	public void setNote(boolean isShow ,OnClickListener listener){
		this.note.setVisibility(View.VISIBLE) ;
		this.note.setOnClickListener(listener);
	}
	/**
	 * 用于列表项的接口
	 * @author zhanghuan
	 * 
	 */
	public interface TitleListInterface {
		public void setList(String data , ListView list);
	}
	@Override
	public void onRefresh() {
		getWebData() ;
	}
	
}
