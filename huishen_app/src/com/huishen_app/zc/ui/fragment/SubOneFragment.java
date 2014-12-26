package com.huishen_app.zc.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.base.BaseActivity;

@SuppressLint("ValidFragment")
public class SubOneFragment extends BaseFragment{

	public SubOneFragment(BaseActivity father) {
		super(father);
	}
	private View this_lay;
	
	@SuppressLint("InflateParams")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try {
			this_lay = inflater.inflate(R.layout.fragment_null, null);

		} catch (Exception e) {
		}
		return this_lay;
	}
}
