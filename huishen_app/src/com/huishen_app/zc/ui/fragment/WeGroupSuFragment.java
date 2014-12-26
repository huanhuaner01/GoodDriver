package com.huishen_app.zc.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.text.Html;
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
import com.huishen_app.zc.util.TextStyleUtil;

public class WeGroupSuFragment extends BaseFragment implements OnClickListener{
	public WeGroupSuFragment(BaseActivity father ,Object tag ) {
		super(father ,tag);
		
	}
        private ListGridView gv ; //选择人数的gridView
        private View rootView ;  //父节点
        private TextView num , title ,total; //
        private Button btn ; //确认按钮
        
        
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.fragment_wegroups,
					container, false);
			registView();
			init();
			return rootView;
		}
		
		/**
		 * 注册所有组件
		 */
		private void registView(){
			this.gv = (ListGridView)rootView.findViewById(R.id.wegroup_s_gv);
		    this.title = (TextView)rootView.findViewById(R.id.header_title) ;
		    this.btn = (Button)rootView.findViewById(R.id.wegroup_order_btn);
		    this.total = (TextView) rootView.findViewById(R.id.wegroup_s_total);
		    this.num = (TextView) rootView.findViewById(R.id.wegroup_s_num);
		}
		
		private void init(){
			//设置标题
			this.title.setText("团购信息");
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
			//设置参团人数
			this.num.setText(Html.fromHtml("<font color='"+getResources().getColor(R.color.main_title_background)+"'>300</font>/1000"));
			//设置总金额文字
			this.total.setText("￥");
			TextStyleUtil.setTextAppearanceSpan(this.father ,this.total ,"5588 ");
			this.total.append("(每人节约800元)");
			
			/***********************按钮事件******************************/
			this.btn.setOnClickListener(this);
			
		}
		 
		

	    @Override  
	    public void onClick(View v)  
	    {  
	    	
	    	this.father.switchcenter(tag,null);  
	        
	    }
		
	
}
