package com.huishen_app.zc.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.huishen_app.all.mywidget.NoScrollListView;
import com.huishen_app.zc.ui.JLListActivity;
import com.huishen_app.zc.ui.OrderActivity;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.ShowMapActivity;
import com.huishen_app.zc.ui.adapter.RattingBarListAdapter;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zh.util.AndroidUtil;
import com.huishen_app.zh.util.TextStyleUtil;

public class JXDetailFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

	public JXDetailFragment(BaseActivity father) {
		super(father);
	}
	
		private static final int REFRESH_COMPLETE = 0X110;
		private SwipeRefreshLayout mSwipeLayout;
		private TextView title , overleaf ,headerdes ,price , note;
		private ImageView jximg,infoimg ,proimg ;
		private Button btn ;
		private NoScrollListView jugeList  , trainList; //评价列表，培训场地列表
		private ArrayList<Map<String ,Object>> judgeListData ,trainareaListDate ; //评价数据  ，培训场地数据
		   /** 训练场地查看更多 ，评价查看更多 */
	    private LinearLayout trainareaMore ,judgeMore ; 
		/** 培训场地适配器  */
	    private SimpleAdapter  trainareaAdapter ;  
	    /** 评价列表适配器  */
	    private RattingBarListAdapter judgeAdapter ;
	    /** 二级fragment */
	    private TitleListFragment fragment ;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_jxdetail,
					container, false);
			registView(rootView);
			initView();
			return rootView;
		}
		/**
		 * 组件注册
		 * @param view
		 */
		private void registView(View rootView){
			mSwipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.id_swipe_ly);
			title = (TextView)rootView.findViewById(R.id.header_title);
			overleaf = (TextView)rootView.findViewById(R.id.wegroup_detail_tv_overleaf) ;
			headerdes = (TextView)rootView.findViewById(R.id.wegroup_item_tv_imgdes) ;
			price = (TextView)rootView.findViewById(R.id.wegroup_detail_price) ;
			jximg = (ImageView)rootView.findViewById(R.id.wegroup_detail_img_header) ;
			infoimg = (ImageView)rootView.findViewById(R.id.wegroup_detail_img_des) ;
			btn = (Button)rootView.findViewById(R.id.wegroup_detail_submmit) ;
			note = (TextView)rootView.findViewById(R.id.header_note) ;
			jugeList = (NoScrollListView)rootView.findViewById(R.id.judge_list);
			trainList = (NoScrollListView)rootView.findViewById(R.id.trainarea_list);
			trainareaMore = (LinearLayout)rootView.findViewById(R.id.trainarea_seemore);
			judgeMore = (LinearLayout)rootView.findViewById(R.id.judge_seemore) ;
		}
		/**
		 * 初始化组件
		 */
		private void initView(){
			title.setText("驾校详情");
			
			mSwipeLayout.setOnRefreshListener(this);
			mSwipeLayout.setColorScheme(R.color.color_refresh_1, R.color.color_refresh_2,
					R.color.color_refresh_3, R.color.color_refresh_4);
			//重修设置各种图片的大小
			//修改驾校简介图片的大小
			if(AndroidUtil.getImageScaleParams(this.father, R.drawable.wegroup_info )!= null){
			this.infoimg.setLayoutParams(AndroidUtil.getImageScaleParams(this.father, R.drawable.wegroup_info));
			}
			//修改驾校图片的大小
			if(AndroidUtil.getImageScaleParams(this.father, R.drawable.wegroup_info )!= null){
			this.jximg.setBackgroundResource( R.drawable.jx_detail);
			this.jximg.setLayoutParams(AndroidUtil.getImageScaleParams(this.father, R.drawable.jx_detail));
			}
			//设置驾校名称
			this.headerdes.setText("蜀鹃驾校");
			//设置价格信息
			TextStyleUtil.setTextAppearanceSpan(this.father,this.price ,"3999 ");
			this.price.append("起");
			
			String[] key = new String[]{"1、报名对象: ","2、教学车辆：","3、练车学时：","4、练车方式：","5、特惠价格：",
					"6、全程一费制：","7、道路：","8、费用包含：","9、外地学员：","10、拿证时间：","11、练车地址：","12、接送服务：","13、资料提交："};
			String[] value = new String[]{"18-65岁未学车人士。<br />",
					     "捷达、桑塔纳等考试车型；<br />",
					     "每周至少约车两次，每次至少两个学时（45分钟/ 学时）。特别提醒：需要提前一天电话预约；<br />",
					     "每次最多四人一起练车；<br />",
					     "市场价：4880元，现价4500元，一费包干，中途无隐性消费，好司机提供全程担保；<br />",
					     "包括报名服务费、IC指纹卡费、理论教材费、理论学习、理论考试、场地培训、场地考试、道路培训、路考、安全文明考试、实习牌、驾照。每个科目当场均有一次免费的补考机会；<br />",
					     "训练包400公里；<br />",
					     "培训费/书本费/400公里路训/考试接送费;<br />",
					     "免费办理居住证、学生证；<br />",
					     "本地外地最快60天。本地学员平均3-4个月，外地学员平均4-5个月，学员练车时间直接影响学员拿证时间；<br />",
					     "成都",
					     "预约练车定点接送，考试免费接送；<br />",
					     "学员可以通过同城快递寄送，支持到付；好司机支持免费上门。<br />"};
			/**
			 * 1、报名对象：18-65岁未学车人士。
               2、教学车辆：捷达、桑塔纳等考试车型；
               3、练车学时：每周至少约车两次，每次至少两个学时（45分钟/ 学时）。特别提醒：需要提前一天电话预约；
               4、练车方式：每次最多四人一起练车；
               5、特惠价格：市场价：4880元，现价4500元，一费包干，中途无隐性消费，嘚儿驾学车网提供全程担保；
               6、全程一费制：包括报名服务费、IC指纹卡费、理论教材费、理论学习、理论考试、场地培训、场地考试、道路培训、路考、安全文明考试、实习牌、驾照。每个科目当场均有一次免费的补考机会；
               7、道路：训练包400公里；
               8、费用包含：培训费/书本费/400公里路训/考试接送费
               9、外地学员：免费办理居住证、学生证；
               10、拿证时间：本地外地最快60天。本地学员平均3-4个月，外地学员平均4-5个月，学员练车时间直接影响学员拿证时间；
               11、练车地址：成鑫驾校清波基地，成鑫驾校黄田坝基地；
               12、接送服务：预约练车定点接送，考试免费接送；
               13、资料提交：学员可以通过同城快递寄送，支持到付；嘚儿驾学车网支持免费上门。
			 */
			//设置报名须知
			for(int i = 0 ;i<13 ; i++){
			String des = "<font color='"+this.father.getResources().getColor(R.color.wegroup_item_buttomtv_color)
					+"'>"+key[i]+"</font>"+value[i];
			 this.overleaf.append(Html.fromHtml(des));
			}
			btn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					weGroupSubmmit(arg0);
					
				}
				
			});
			setTrainAreaList() ;
			setJudgeList() ;
			setNote();
			
		}
		
		/**
		 * 设置训练场地列表
		 */
		private void setTrainAreaList(){
	
			
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
			trainareaMore.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					OnViewClick(v) ;
				}
				
			});
		}
		
		/**
		 * 设置评价列表
		 */
		private void setJudgeList(){
			//评价添加数据
			judgeListData = new ArrayList<Map<String ,Object>>();
			String[] jfrom = new String[]{"stuname" ,"content"};
			int[] jto = new int[]{R.id.judge_listitem_stuname ,R.id.judge_listitem_content};
			for(int i = 0 ; i<4 ; i++){
				HashMap<String , Object> map = new HashMap<String , Object>();
				map.put("rating",2.7);
				map.put("stuname","(XXX学员)");
				map.put("content","老师教的不错");
				judgeListData.add(map);
			}
			judgeAdapter = new RattingBarListAdapter(this.father,judgeListData ,R.layout.judge_list_item ,jfrom , jto);
			jugeList.setAdapter(judgeAdapter);
			judgeMore.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					OnViewClick(v) ;
				}
				
			});
		}
		
		/**
		 * 设置标题栏中的note组件（这里将note设置为可见，作为所有教练的入口）
		 */
		private void setNote() {
			this.note.setVisibility(View.VISIBLE);
			this.note.setText("所有教练");
			this.note.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
			     Intent i = new Intent(father ,JLListActivity.class);
			     father.startActivity(i);
				}
				
			});
		}
		/**
		 * 部分按钮的响应事件
		 * @param v
		 */
		public void OnViewClick(View v) {

	        FragmentManager fm = getFragmentManager();  
	        FragmentTransaction tx = fm.beginTransaction();
			switch(v.getId()){
				//切换到训练场地列表页面
			case R.id.trainarea_seemore:
				fragment = new TainareaListFragment(this.father ,"训练场地" ,"");
		        tx.hide(this);   
		        tx.add(R.id.container,fragment , "jltrainarea");
		        tx.addToBackStack(null); 
				break ;
				//切换到评价列表页面
			case R.id.judge_seemore:
				fragment = new JudgeListFragment(this.father ,"评价" ,"");
		        tx.hide(this);   
		        tx.add(R.id.container,fragment , "jltrainarea");
		        tx.addToBackStack(null);
				break ;
			}     
			tx.commit();
		}
		/**
		 * 确认订单按钮响应事件
		 * 
		 * @param v
		 */
		public void weGroupSubmmit(View v) {
            //调用系统的拨号服务实现电话拨打功能
            //封装一个拨打电话的intent，并且将电话号码包装成一个Uri对象传入
			//（1）ACTION_CALL：直接拨号；
			//（2）ACTION_DIAL：调用拨号程序，手工拨出。
            Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:02887877236"));
            this.father.startActivity(intent);//内部类
		}
		
		
		
		
		/*************************************************************************/
		
		@Override
		public void onRefresh()
		{
			// Log.e("xxx", Thread.currentThread().getName());
			// UI Thread

			mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);

		}
		
		private Handler mHandler = new Handler()
		{
			@Override
			public void handleMessage(android.os.Message msg)
			{
				switch (msg.what)
				{
				case REFRESH_COMPLETE:
//					adapter.notifyDataSetChanged();
					mSwipeLayout.setRefreshing(false);
					break;

				}
			};
		};
	}
