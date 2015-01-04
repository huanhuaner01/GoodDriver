package com.huishen_app.zc.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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
    private TextView title ,jlname , price ,drivingYear ,score ,judgenum ,jldes ; //标题，教练名字，报名价格，教龄，评分成绩，评价数量
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
		title = (TextView)RootView.findViewById(R.id.header_title);
		jlname = (TextView)RootView.findViewById(R.id.jl_detail_tv_name) ;
		price = (TextView)RootView.findViewById(R.id.jl_detail_tv_price);
		score = (TextView)RootView.findViewById(R.id.jl_detail_tv_year) ;
		judgenum = (TextView)RootView.findViewById(R.id.jl_detail_judgenum);
		jldes = (TextView)RootView.findViewById(R.id.jl_detail_tv_overleaf);
		jugeList = (NoScrollListView)RootView.findViewById(R.id.judge_list);
		trainList = (NoScrollListView)RootView.findViewById(R.id.trainarea_list);
	}
 
	
	/**
	 * 初始化数据和事件监听
	 */
	private void initView() {
		this.title.setText(this.father.getResources().getString(R.string.jl_detail));
		//设置教练个人基本信息
		setBaseInfo();
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
			map.put("area",TextStyleUtil.getTextAppearanceSpan(father, "蜀娟驾校", "(郫县校区)"));
			map.put("addr","地址：成都市");
			map.put("tel","联系电话：13888888888");
			trainareaListDate.add(map);
		}
		trainareaAdapter = new SimpleAdapter(this.father,trainareaListDate ,R.layout.trainarea_list_item ,tfrom , tto);
		trainList.setAdapter(trainareaAdapter);
	}
	
	
	//设置教练个人基本信息
	private void setBaseInfo(){
		this.jlname.setText(TextStyleUtil.getTextAppearanceSpan(this.father, "王安石","(蜀娟驾校)")) ;
		this.price.append(TextStyleUtil.getTextAppearanceSpan(this.father, "5588"));
		//教练简介
		jldes.setText(TextStyleUtil.getTextAppearanceSpan(this.father ,"教        龄：","7年" ,R.color.book_imitate_textcolornew));
		jldes.append("\n") ;
		jldes.append(TextStyleUtil.getTextAppearanceSpan(this.father ,"驾        龄：","10年" ,R.color.book_imitate_textcolornew));
		jldes.append("\n") ;
		jldes.append(TextStyleUtil.getTextAppearanceSpan(this.father ,"教练证号：","川00268" ,R.color.book_imitate_textcolornew));
		jldes.append("\n") ;
		jldes.append(TextStyleUtil.getTextAppearanceSpan(this.father ,"教练介绍：\n","  教练非常好" ,R.color.book_imitate_textcolornew));
	
	}
	

}
