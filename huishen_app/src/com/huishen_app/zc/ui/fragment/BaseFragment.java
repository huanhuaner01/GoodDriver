package com.huishen_app.zc.ui.fragment;

import com.huishen_app.zc.ui.base.BaseActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

	public LayoutInflater inflater;

	public BaseActivity father;
	
	public Object tag ; //±Í«©

	public BaseFragment(BaseActivity father) {
		super();
		this.father = father;
	}
	public BaseFragment(BaseActivity father ,Object tag) {
		super();
		this.father = father;
		this.tag = tag ;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

}
