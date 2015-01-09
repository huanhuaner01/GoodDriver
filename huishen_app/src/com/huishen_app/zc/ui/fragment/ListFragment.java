package com.huishen_app.zc.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.huishen_app.all.mywidget.NoScrollListView;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.ui.fragment.ListFragment.ListFragmentAdapter;

/**
 * �������֣��б��ͱ����Fragment(ͨ��)
 * @author zhanghuan
 *
 */
@SuppressLint("InflateParams") public class ListFragment extends BaseFragment {
	private View RootView ;  //�����
	private TextView title ; //����
	private TextView des ; //���
	private Button back  ; //���ؼ�
	private NoScrollListView list ; //�б�
	private ListFragmentAdapter fragmentAdapter ; //fragment������
	private String result  ,titleStr;
	private int url ;
	
	
	public ListFragment(BaseActivity father ,String title, int url ,ListFragmentAdapter fragmentApdater) {
		super(father);
		this.fragmentAdapter = fragmentApdater;
		this.titleStr = title ;
		this.url = url ;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		    
		try{
			RootView = inflater.inflate(R.layout.fragment_list, null);
			registView();
			initView();
		  } catch (Exception e) {
		}
		return RootView;
	}

	private void registView() {
		title = (TextView)RootView.findViewById(R.id.header_title) ;
		des = (TextView)RootView.findViewById(R.id.f_list_content) ;
		list = (NoScrollListView)RootView.findViewById(R.id.f_list_list);
		back = (Button)RootView.findViewById(R.id.head_back) ;
	}
    
	private void initView() {
	   this.title.setText(titleStr) ;
	   getWebData();
	   fragmentAdapter.setDes(result, des);
	   fragmentAdapter.setList(result, list);
	}
	
	private void getWebData(){
		
	}
	
	public void setBack(View.OnClickListener listener){
		if(listener != null){
		this.back.setOnClickListener(listener);
		}
	}
	/**
	 * listFragment �Ľӿں���
	 * @author zhanghuan
	 * 
	 */
	public interface ListFragmentAdapter{
		void setDes(String result ,TextView tv);
		void setList(String result ,NoScrollListView list);
	}
    
}