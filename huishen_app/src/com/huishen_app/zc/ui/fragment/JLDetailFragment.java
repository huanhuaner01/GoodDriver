package com.huishen_app.zc.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.base.BaseActivity;

public class JLDetailFragment extends BaseFragment {
    private View RootView ;
	public JLDetailFragment(BaseActivity father) {
		super(father);
	}

	public JLDetailFragment(BaseActivity father, Object tag) {
		super(father, tag);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		try {
			RootView = inflater.inflate(R.layout.fragment_jldetail, null);
			regsitView();
			initView();
		  } catch (Exception e) {
		}
		return RootView;
	}

	private void regsitView() {
	}

	private void initView() {
		
	}

}
