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
 * ���б����listFragment
 * @author zhanghuan
 *
 */
public class TitleListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
	//����ˢ�����
	private SwipeRefreshLayout mSwipeLayout;
	/** ������һ��fragment */
	public int BACK_FRAGMENT = 0 ; 
	/** ������һ��activity **/
	public int BACK_ACTIVITY =1 ; 
	private View RootView ;  //�����
	private TextView title ,note; //����,λ���ұߵ�����
	private ListView list ; //�б�
	private Button back ; //���ؼ�
    private String titlestr ;
    private String url ;
    private String data ; //�����б�
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
		//���ñ���
		if(titlestr != null){
			this.title.setText(titlestr) ;
		}
		//��ȡ��������
		getWebData();
		
		//���÷��ؼ�����
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				 FragmentManager fm = getFragmentManager();  
			     fm.popBackStack();
			}
			
		}) ;
	}
	
	/**
	 * �������磬��ȡ��������
	 */
	private void getWebData(){
		//���urlΪ�ջ���list�ӿ�Ϊ���򲻷������磬ֱ�ӷ���
//		if(url == null || url.equals("")||listinterface == null){
//			return ;
//		}
		listinterface.setList(data, list);
	}
	
	/***
	 * ���ýӿ�
	 * @param listinterface
	 */
	public void setTitleList(TitleListInterface listinterface){
		this.listinterface = listinterface ;
	}
	/**
	 * ���ñ�������ע��ע
	 * @param isShow
	 * @param listener
	 */
	public void setNote(boolean isShow ,OnClickListener listener){
		this.note.setVisibility(View.VISIBLE) ;
		this.note.setOnClickListener(listener);
	}
	/**
	 * �����б���Ľӿ�
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
