package com.huishen_app.zc.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.base.BaseActivity;

/**
 * 个人信息确认界面，用于用户报名
 * 个人信息不包括密码
 * @author zhanghuan
 *
 */
public class ConfirmInfoFragment extends BaseFragment implements OnClickListener{
	private View rootView ;  //父节点
	private TextView title ; //标题
	private Button back , submmit ; //返回 ，提交
	private EditText  realnanme ,username ,idnum ,email ,tel ,addr ; //真实姓名，用户名， 身份证号，电话，住址
	private RadioGroup sex ; //性别
	public ConfirmInfoFragment(BaseActivity father ,Object tag) {
		super(father , tag);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_confirminfo,
				container, false);
		regsitView();
		initView();
		return rootView;
	}
    /**
     * 注册组件
     */
	private void regsitView() {
		this.title = (TextView)rootView.findViewById(R.id.header_title);
		this.back = (Button)rootView.findViewById(R.id.header_back);
		this.realnanme = (EditText)rootView.findViewById(R.id.regist_edit_realname);
		this.username = (EditText)rootView.findViewById(R.id.regist_edit_name);
		this.idnum = (EditText)rootView.findViewById(R.id.regist_edit_id);
		this.email = (EditText)rootView.findViewById(R.id.regist_edit_email);
		this.tel = (EditText)rootView.findViewById(R.id.regist_edit_tel);
		this.addr = (EditText)rootView.findViewById(R.id.regist_edit_address);
		this.submmit = (Button)rootView.findViewById(R.id.regist_edit_ok);
		this.sex = (RadioGroup)rootView.findViewById(R.id.regist_sex_rdgroup);
		
	}
    
	/**
	 * 初始化组件
	 */
	private void initView() {
		//修改标题
		this.title.setText(this.father.getResources().getString(R.string.confirm_info));
		//监听返回键
		this.back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
		        FragmentManager fm = getFragmentManager();  
		        fm.popBackStack();
			}
			
		});
		//设置提交键
		this.submmit.setText(this.father.getResources().getString(R.string.submission));
		this.submmit.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View arg0) {
		this.father.switchcenter(tag,null);
	}  

}
