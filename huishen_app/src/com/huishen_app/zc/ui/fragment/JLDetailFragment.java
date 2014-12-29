package com.huishen_app.zc.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.huishen_app.all.mywidget.NoScrollListView;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.util.TextStyleUtil;
/**
 * 教练详情界面
 * @author zhanghuan
 *
 */
public class JLDetailFragment extends BaseFragment {
    private View RootView ;  //根组件
    private NoScrollListView jugeList  , trainList; //评价列表，培训场地列表
    private ArrayList<Map<String ,Object>> judgeListData ,trainareaListDate ; //评价数据  ，培训场地数据
    private SimpleAdapter judgeAdapter , trainareaAdapter ;  //评价列表适配器，培训场地适配器
	public JLDetailFragment(BaseActivity father) {
		super(father);
	}

	public JLDetailFragment(BaseActivity father, Object tag) {
		super(father, tag);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		try {
			RootView = inflater.inflate(R.layout.fragment_jldetail, null);
			regsitView();
			initView();
		  } catch (Exception e) {
		}
		return RootView;
	}
   
	/**
	 * 注册组件
	 */
	private void regsitView() {
		jugeList = (NoScrollListView)RootView.findViewById(R.id.judge_list);
		trainList = (NoScrollListView)RootView.findViewById(R.id.trainarea_list);
	}
 
	
	/**
	 * 初始化数据和事件监听
	 */
	private void initView() {
		//评价添加数据
		judgeListData = new ArrayList<Map<String ,Object>>();
		String[] jfrom = new String[]{"star","stuname" ,"content"};
		int[] jto = new int[]{R.id.judge_listitem_star ,R.id.judge_listitem_stuname ,R.id.judge_listitem_content};
		for(int i = 0 ; i<4 ; i++){
			HashMap<String , Object> map = new HashMap<String , Object>();
			map.put("star",R.drawable.star_four);
			map.put("stuname","(XXX学员)");
			map.put("content","老师教的不错");
			judgeListData.add(map);
		}
		judgeAdapter = new SimpleAdapter(this.father,judgeListData ,R.layout.judge_list_item ,jfrom , jto);
		jugeList.setAdapter(judgeAdapter);
		
		//设置培训场地列表和按钮
		trainareaListDate = new ArrayList<Map<String ,Object>>();
		String[] tfrom = new String[]{"area","addr" ,"tel"};
		int[] tto = new int[]{R.id.trainarea_listitem_area ,R.id.trainarea_listitem_addr ,R.id.trainarea_listitem_tel};
		for(int i = 0 ; i<4 ; i++){
			HashMap<String , Object> map = new HashMap<String , Object>();
			map.put("area",TextStyleUtil.setTextAppearanceSpan(father, "蜀娟驾校", "(郫县校区)"));
			map.put("addr","地址：成都市");
			map.put("tel","联系电话：13888888888");
			trainareaListDate.add(map);
		}
		trainareaAdapter = new SimpleAdapter(this.father,trainareaListDate ,R.layout.trainarea_list_item ,tfrom , tto);
		trainList.setAdapter(trainareaAdapter);
	}

}
