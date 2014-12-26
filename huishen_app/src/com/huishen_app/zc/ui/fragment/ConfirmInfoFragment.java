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
 * ������Ϣȷ�Ͻ��棬�����û�����
 * ������Ϣ����������
 * @author zhanghuan
 *
 */
public class ConfirmInfoFragment extends BaseFragment implements OnClickListener{
	private View rootView ;  //���ڵ�
	private TextView title ; //����
	private Button back , submmit ; //���� ���ύ
	private EditText  realnanme ,username ,idnum ,email ,tel ,addr ; //��ʵ�������û����� ���֤�ţ��绰��סַ
	private RadioGroup sex ; //�Ա�
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
     * ע�����
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
	 * ��ʼ�����
	 */
	private void initView() {
		//�޸ı���
		this.title.setText(this.father.getResources().getString(R.string.confirm_info));
		//�������ؼ�
		this.back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
		        FragmentManager fm = getFragmentManager();  
		        fm.popBackStack();
			}
			
		});
		//�����ύ��
		this.submmit.setText(this.father.getResources().getString(R.string.submission));
		this.submmit.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View arg0) {
		this.father.switchcenter(tag,null);
	}  

}
