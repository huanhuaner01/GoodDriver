package com.huishen_app.zc.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.base.BaseActivity;

public class TipFragment extends BaseFragment {
	private View rootView ;  //���ڵ�
	private TextView title ,note; //����
	private Button back ; //���� 
	public TipFragment(BaseActivity father) {
		super(father);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_tip,
				container, false);
		regsitView();
		initView();
		return rootView;
	}
	private void regsitView() {
		this.title = (TextView)rootView.findViewById(R.id.header_title);
		this.back = (Button)rootView.findViewById(R.id.header_back);
		this.note = (TextView)rootView.findViewById(R.id.header_note) ;
		
	}
	private void initView() {
		//�޸ı���
		this.title.setText(this.father.getResources().getString(R.string.confirm_info));
		//�������ؼ�
		this.back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				note.setVisibility(View.GONE);
		        FragmentManager fm = getFragmentManager();  
		        fm.popBackStack();
			}
			
		});
		//����ȡ����
		this.note.setVisibility(View.VISIBLE);
		this.note.setText(this.father.getResources().getString(R.string.close));
		this.note.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				TipFragment.this.father.finish();
			}
			
		});
	}
}
