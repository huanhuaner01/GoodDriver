package com.huishen_app.zc.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;

public class LoadingDialog_a extends Dialog {

	public Activity activity;
	public Context context;
	// 定义资源
	public int resource;

	public LoadingDialog_a(Context context, int theme, int resource) {
		super(context, theme);
		this.context = context;
		this.resource = resource;
	}

	public LoadingDialog_a(Activity activity, int theme, int resource) {
		super(activity, theme);
		this.activity = activity;
		this.resource = resource;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(resource);
	}

	public static LoadingDialog_a showdialog(Activity father) {
		LoadingDialog_a loading = new LoadingDialog_a(father,
				R.style.loadingstyle, R.layout.loading_layout);
		loading.show();
		return loading;
	}

	public void onBackPressed() {
		try {
			activity.onBackPressed();
		} catch (Exception e) {
		}
		try {
			dismiss();
		} catch (Exception e) {
		}
	}

}