package com.huishen_app.zc.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.adapter.KeyValueAdapter;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.util.TextStyleUtil;
/**
 * ����ȷ�Ͻ���ʱ��
 * @author zhanghuan
 *
 */
public class ConfirmOrderFragment extends BaseFragment {
	private View rootView ;  //���ڵ�
	private TextView title  , total; //����
	private Button back , submmit ; //���� ���ύ
	private ListView list ; 
	private ArrayList<HashMap<String ,Object>> data ;
	public ConfirmOrderFragment(BaseActivity father ,Object tag ) {
		super(father , tag); 
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_confirmorder,
				container, false);
		regsitView();
		initView();
		return rootView;
	}
	private void regsitView() {
		this.title = (TextView)rootView.findViewById(R.id.header_title);
		this.back = (Button)rootView.findViewById(R.id.header_back);
		this.submmit = (Button)rootView.findViewById(R.id.submmit);
		this.list = (ListView)rootView.findViewById(R.id.list);
		this.total = (TextView)rootView.findViewById(R.id.price);
		
	}
	private void initView() {
		//�޸ı���
		this.title.setText(this.father.getResources().getString(R.string.confirm_order));
		//�������ؼ�
		this.back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
		        FragmentManager fm = getFragmentManager();  
		        fm.popBackStack();
			}
			
		});
		//�б��������
		data = new ArrayList<HashMap<String , Object>>();
		String[] key = new String[]{"��Ŀ","ģʽ","ѧ������","ԭ����","�ֵ���"};
		String[] value = new String[]{"VIP�Ź���һ��һ����","��ģʽһ����","C1","4666","2541"};
		for(int i = 0 ; i<key.length ; i++){
			HashMap<String ,Object> map = new HashMap<String ,Object>();
			map.put("key",key[i]);
			map.put("value",value[i]);
			map.put("islight" ,i==4?true:false);
			data.add(map);
		}
		String[] from = new String[]{"key","value"};
		int[] to = new int[]{R.id.item_key,R.id.item_value};
		KeyValueAdapter adapter = new KeyValueAdapter(this.father ,data ,R.layout.confirm_order_listitem ,from ,to );
		list.setAdapter(adapter);
		//�����ܼ�
		this.total.append(TextStyleUtil.getTextAppearanceSpan(this.father ,"2541"));
		this.total.append("��ʡ��2000��");
		this.submmit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				ConfirmOrderFragment.this.father.switchcenter(tag,null);
			}
			
		}) ;
	}
	

}
