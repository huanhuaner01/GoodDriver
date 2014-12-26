package com.huishen_app.zc.ui.fragment;

import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.base.BaseActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("ValidFragment")
public class HeadFragment extends BaseFragment {

	public HeadFragment(BaseActivity father) {
		super(father);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View buttom = null;
		try {
			buttom = inflater.inflate(R.layout.main_lay_title, null);
		} catch (Exception e) {
		}
		return buttom;
	}


}
