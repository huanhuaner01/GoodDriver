package com.huishen_app.zc.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.huishen_app.all.mywidget.NoScrollListView;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.ShowMapActivity;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zh.util.TextStyleUtil;
/**
 * 教练详情界面
 * @author zhanghuan
 *
 */
public class JLDetailFragment extends BaseFragment implements View.OnClickListener{
    private View RootView ;  //根组件
    private String TAG = "JLDetailFragment" ;
    /**标题，教练名字，报名价格，教龄，评分成绩，评价数量 ,教练简介，学车流程*/
    private TextView title ,jlname , price ,drivingYear ,score ,judgenum ,jldes ,overleaf ; 
    private NoScrollListView jugeList  , trainList; //评价列表，培训场地列表
    /** 教练图文详情查看更多 ， 训练场地查看更多 ，评价查看更多 */
    private LinearLayout desMore ,trainareaMore ,judgeMore ; 
    
    /** 立即报名按钮，我要咨询按钮  */
    private Button signUp , consult ;
    
    private ArrayList<Map<String ,Object>> judgeListData ,trainareaListDate ; //评价数据  ，培训场地数据
    private SimpleAdapter judgeAdapter , trainareaAdapter ;  //评价列表适配器，培训场地适配器
    
    /** 显示listFragment(这里作为二级fragment) */
    private ListFragment fragment ; 
    
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
		overleaf = (TextView)RootView.findViewById(R.id.wegroup_detail_tv_overleaf);
		jugeList = (NoScrollListView)RootView.findViewById(R.id.judge_list);
		trainList = (NoScrollListView)RootView.findViewById(R.id.trainarea_list);
		desMore = (LinearLayout)RootView.findViewById(R.id.jl_detail_des_more) ;
		trainareaMore = (LinearLayout)RootView.findViewById(R.id.trainarea_seemore);
		judgeMore = (LinearLayout)RootView.findViewById(R.id.judge_seemore) ;
		signUp = (Button)RootView.findViewById(R.id.jl_detail_btn_signup);
		consult = (Button)RootView.findViewById(R.id.jl_detail_btn_consult) ;
		
	}
 
	
	/**
	 * 初始化数据和事件监听
	 */
	private void initView() {
		this.title.setText(this.father.getResources().getString(R.string.jl_detail));
		//设置教练个人基本信息
		setJlBaseInfo();
		//设置教练须知
		setOverleaf() ;
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
		// listView注册一个元素点击事件监听器
		trainList.setOnItemClickListener(new OnItemClickListener() {
	
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i = new Intent(father ,ShowMapActivity.class);
				father.startActivity(i);
			}
		});
		//立即报名，我要咨询监听事件
		signUp.setOnClickListener(this);
		consult.setOnClickListener(this);
		//设置教练图文详情查看更多监听事件
		desMore.setOnClickListener(this);
		//设置训练场地查看更多监听事件
		trainareaMore.setOnClickListener(this);
		//设置评价查看更多监听事件
		judgeMore.setOnClickListener(this) ;
		
	}
	
	
	//设置教练个人基本信息
	private void setJlBaseInfo(){
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
	
	/**
	 * 设置教练须知
	 */
	private void setOverleaf(){
		String[] key = new String[]{"1、报名对象: ","2、教学车辆：","3、练车学时：","4、练车方式：","5、特惠价格：",
				"6、全程一费制：","7、道路：","8、费用包含：","9、外地学员：","10、拿证时间：","11、练车地址：","12、接送服务：","13、资料提交："};
		String[] value = new String[]{"18-65岁未学车人士。<br />",
				     "捷达、桑塔纳等考试车型；<br />",
				     "每周至少约车两次，每次至少两个学时（45分钟/ 学时）。特别提醒：需要提前一天电话预约；<br />",
				     "每次最多四人一起练车；<br />",
				     "市场价：4880元，现价4500元，一费包干，中途无隐性消费，嘚儿驾学车网提供全程担保；<br />",
				     "包括报名服务费、IC指纹卡费、理论教材费、理论学习、理论考试、场地培训、场地考试、道路培训、路考、安全文明考试、实习牌、驾照。每个科目当场均有一次免费的补考机会；<br />",
				     "训练包400公里；<br />",
				     "培训费/书本费/400公里路训/考试接送费;<br />",
				     "免费办理居住证、学生证；<br />",
				     "本地外地最快60天。本地学员平均3-4个月，外地学员平均4-5个月，学员练车时间直接影响学员拿证时间；<br />",
				     "练车地址：成鑫驾校清波基地，成鑫驾校黄田坝基地；<br />",
				     "预约练车定点接送，考试免费接送；<br />",
				     "学员可以通过同城快递寄送，支持到付；嘚儿驾学车网支持免费上门。<br />"};

		//设置报名须知
		for(int i = 0 ;i<13 ; i++){
		 String des = "<font color='"+this.father.getResources().getColor(R.color.wegroup_item_buttomtv_color)
		 		+"'>"+key[i]+"</font>"+value[i];
		 this.overleaf.append(Html.fromHtml(des));
		}
	}

	@Override
	public void onClick(View v) {

        FragmentManager fm = getFragmentManager();  
        FragmentTransaction tx = fm.beginTransaction();
		switch(v.getId()){
		//切换到教练图文详情页面
		case R.id.jl_detail_des_more: 
			fragment = new ListFragment(this.father ,"王教练" ,"",0 ,new ListFragment.ListFragmentAdapter(){

				@Override
				public void setDes(String result, TextView tv) {
					//教练简介
					tv.setText(TextStyleUtil.getTextAppearanceSpan(father ,"教        龄：","7年" ,R.color.book_imitate_textcolornew));
					tv.append("\n") ;
					tv.append(TextStyleUtil.getTextAppearanceSpan(father ,"驾        龄：","10年" ,R.color.book_imitate_textcolornew));
					tv.append("\n") ;
					tv.append(TextStyleUtil.getTextAppearanceSpan(father ,"教练证号：","川00268" ,R.color.book_imitate_textcolornew));
					tv.append("\n") ;
					tv.append(TextStyleUtil.getTextAppearanceSpan(father ,"教练介绍：\n","  教练非常好" ,R.color.book_imitate_textcolornew));
				} 

				@Override
				public void setList(String result, NoScrollListView list) {
					
				}
				
			});
	        tx.hide(this);   
	        tx.add(R.id.container,fragment , "jldes");
	        tx.addToBackStack(null);  
	          
			break ;
			//切换到训练场地列表页面
		case R.id.trainarea_seemore:
			fragment = new ListFragment(this.father ,"训练场地" ,"",0 ,new ListFragment.ListFragmentAdapter(){

				@Override
				public void setDes(String result, TextView tv) {
					tv.setVisibility(View.GONE);
				} 

				@Override
				public void setList(String result, NoScrollListView list) {
					String[] tfrom = new String[]{"area","addr" ,"tel"};
					int[] tto = new int[]{R.id.trainarea_listitem_area ,R.id.trainarea_listitem_addr ,R.id.trainarea_listitem_tel};
				
					trainareaAdapter = new SimpleAdapter(father,trainareaListDate ,R.layout.trainarea_list_item ,tfrom , tto);
					list.setAdapter(trainareaAdapter);
					// listView注册一个元素点击事件监听器
					list.setOnItemClickListener(new OnItemClickListener() {
				
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
								long arg3) {
							Intent i = new Intent(father ,ShowMapActivity.class);
							father.startActivity(i);
						}
					});
				}
				
			});
	        tx.hide(this);   
	        tx.add(R.id.container,fragment , "jltrainarea");
	        tx.addToBackStack(null); 
			break ;
			//切换到评价列表页面
		case R.id.judge_seemore:
			fragment = new ListFragment(this.father ,"训练场地" ,"",0,new ListFragment.ListFragmentAdapter(){

				@Override
				public void setDes(String result, TextView tv) {
					tv.setVisibility(View.GONE);
				} 

				@Override
				public void setList(String result, NoScrollListView list) {
					list.setAdapter(judgeAdapter);
				}
				
			});
	        tx.hide(this);   
	        tx.add(R.id.container,fragment , "jltrainarea");
	        tx.addToBackStack(null);
			break ;
			//立即报名页面
		case R.id.jl_detail_btn_signup:
			break ;
			//我要咨询界面
		case R.id.jl_detail_btn_consult:
			break ;
		}
		tx.commit();
	}

}
