package com.huishen_app.zc.ui.dialog;

import com.huishen_app.zc.ui.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class LoadingDialog_ui extends Dialog {

	public Activity activity;
	public Context context;
	// 定义资源
	public int resource;

	public LoadingDialog_ui(Context context, int theme, int resource) {
		super(context, theme);
		this.context = context;
		this.resource = resource;
	}

	public LoadingDialog_ui(Activity activity, int theme, int resource) {
		super(activity, theme);
		this.activity = activity;
		this.resource = resource;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(resource);
	}

	public static LoadingDialog_ui showdialog(Activity father) {
		LoadingDialog_ui loading = new LoadingDialog_ui(father,
				R.style.loadingstyle, R.layout.dialog_loading_lay);
		loading.show();
		return loading;
	}

	@Override
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