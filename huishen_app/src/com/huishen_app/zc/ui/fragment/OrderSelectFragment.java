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
    private ListGridView gv ; //选择人数的gridView
    private View rootView ;  //父节点
    private TextView title ,total; //
    private Button btn ; //确认按钮
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
		//设置标题
		this.title.setText("选择订单信息");
		//设置车辆类型的数据和事件
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
		//设置总金额文字
		this.total.setText("￥");
		TextStyleUtil.setTextAppearanceSpan(this.father ,this.total ,"5588 ");
		this.total.append("(节约800元)");
		
		/***********************按钮事件******************************/
		this.btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		this.father.switchcenter(tag,null);
	}

}
