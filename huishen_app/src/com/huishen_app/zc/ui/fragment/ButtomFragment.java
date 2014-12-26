package com.huishen_app.zc.ui.fragment;

import com.huishen_app.zc.ui.Main_fragment_ui;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.base.BaseActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

@SuppressLint("ValidFragment")
public class ButtomFragment extends BaseFragment implements
		OnCheckedChangeListener {
	
	//provide a empty constructor to avoid runtime fragment exception
	public ButtomFragment() {
		super(null);
	}
	public ButtomFragment(BaseActivity father) {
		super(father);
	}

	private RadioGroup home_radio_button_group;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View buttom = null;
		try {
			buttom = inflater.inflate(R.layout.main_lay_buttom, null);

			// 获取 菜单列表的group
			home_radio_button_group = (RadioGroup) buttom
					.findViewById(R.id.home_radio_button_group);

			// 设置监听事件
			home_radio_button_group.setOnCheckedChangeListener(this);

		} catch (Exception e) {
		}
		return buttom;
	}

	/**
	 * 设置选中
	 * 
	 * @param rs
	 */
	public void setcheck(int rs) {
		home_radio_button_group.check(rs);
	}

	/**
	 * 清空选项
	 */
	public void clearcheck() {
		home_radio_button_group.clearCheck();
	}

	/**
	 * 菜单选项
	 */
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		RadioButton rb = (RadioButton)group.findViewById(checkedId);
		if(rb != null && rb.isChecked()){
			
		if (checkedId == R.id.home_tab_usercenter) {
			if (Main_fragment_ui.getLoginhttpclient() == null) {
				 home_radio_button_group.clearCheck();
				 this.father.switchcenter(R.id.main_menu_zxyy);
			} else {
				father.switchcenter(R.id.home_tab_usercenter);
			}
		} else if (checkedId == R.id.home_tab_book) {
			if (Main_fragment_ui.getLoginhttpclient() == null) {
			   	 home_radio_button_group.clearCheck();
			     this.father.switchActivity();
			} else {
				father.switchcenter(R.id.home_tab_book);
			}
		} else if (checkedId == R.id.home_tab_main) {
			father.switchcenter(R.id.home_tab_main);
		} else if (checkedId == R.id.home_tab_djt) {
			father.switchcenter(R.id.home_tab_djt);
		}
		}
	}
}
