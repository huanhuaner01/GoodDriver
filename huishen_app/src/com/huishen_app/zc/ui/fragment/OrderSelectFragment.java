package com.huishen_app.zc.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.huishen_app.all.mywidget.ListGridView;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zh.util.TextStyleUtil;

public class OrderSelectFragment extends BaseFragment implements OnClickListener{
    private ListGridView gv ; //ѡ��������gridView
    private View rootView ;  //���ڵ�
    private TextView title ,total; //
    private Button btn ; //ȷ�ϰ�ť
	public OrderSelectFragment(BaseActivity father) {
		super(father);
	}

	public OrderSelectFragment(BaseActivity father, Object tag) {
		super(father, tag);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_order_select,
				container, false);
		registView();
		init();
		return rootView;
	}

	private void registView() {
		this.gv = (ListGridView)rootView.findViewById(R.id.order_select_cartype);
	    this.title = (TextView)rootView.findViewById(R.id.header_title) ;
	    this.btn = (Button)rootView.findViewById(R.id.order_select_btn);
	    this.total = (TextView) rootView.findViewById(R.id.order_select_total);
	}

	private void init() {
		//���ñ���
		this.title.setText("ѡ�񶩵���Ϣ");
		//���ó������͵����ݺ��¼�
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
		String[] array = new String[]{"C1","C2","D1","D2"};
		for(int i = 0 ; i<array.length ; i++)
		{
			HashMap<String, Object> map = new HashMap<String,Object>();
			map.put("plan", array[i]);
			data.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(father, data, R.layout.wegroup_detail_s_gridviewitem, 
				new String[]{"plan"}, new int[]{R.id.gridview_tv});
		this.gv.setAdapter(adapter);
		//�����ܽ������
		this.total.setText("��");
		TextStyleUtil.setTextAppearanceSpan(this.father ,this.total ,"5588 ");
		this.total.append("(��Լ800Ԫ)");
		
		/***********************��ť�¼�******************************/
		this.btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		this.father.switchcenter(tag,null);
	}

}
