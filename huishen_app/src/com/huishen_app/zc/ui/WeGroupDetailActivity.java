package com.huishen_app.zc.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zh.util.AndroidUtil;
import com.huishen_app.zh.util.TextStyleUtil;

public class WeGroupDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
	private static final int REFRESH_COMPLETE = 0X110;
	private SwipeRefreshLayout mSwipeLayout;
	private ImageView desimg , proimg ,headerimg;
	private TextView price , overleaf ,title;
	private Button submmit ;
	@Override
	protected void findViewById_Init() {
		setContentView(R.layout.activity_we_group_detail);
		this.mSwipeLayout = (SwipeRefreshLayout)findViewById(R.id.id_swipe_ly);
		this.desimg = (ImageView)findViewById(R.id.wegroup_detail_img_des);
		this.headerimg  = (ImageView)findViewById(R.id.wegroup_detail_img_header);
		this.price = (TextView) findViewById(R.id.wegroup_detail_price);
		this.overleaf = (TextView) findViewById(R.id.wegroup_detail_tv_overleaf);
		this.title = (TextView) findViewById(R.id.header_title);
		this.submmit = (Button) findViewById(R.id.wegroup_detail_submmit);
	}

	@Override
	protected void initView() {
		this.title.setText("团购详情");
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(android.R.color.holo_green_dark, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);
		//重修设置各种图片的大小
		if(AndroidUtil.getImageScaleParams(this, R.drawable.wegroup_info )!= null){
		this.desimg.setLayoutParams(AndroidUtil.getImageScaleParams(this, R.drawable.wegroup_info));
		}
		if(AndroidUtil.getImageScaleParams(this, R.drawable.wegroup_info )!= null){
		this.headerimg.setLayoutParams(AndroidUtil.getImageScaleParams(this, R.drawable.wegroup_bg));
		}
		
//		String pricemsg = "价格：￥ <font color='red'><strong>5999 </strong></font>&nbsp;&nbsp;&nbsp;<del>￥7999</del>";
		TextStyleUtil.setTextAppearanceSpan(this,this.price ,"5999 ");
		TextStyleUtil.addStrikeSpan(this.price ,"￥7888");
		
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
		String des = "<font color='"+this.getResources().getColor(R.color.wegroup_item_buttomtv_color)
				+"'>"+key[i]+"</font>"+value[i];
		 this.overleaf.append(Html.fromHtml(des));
		}
	
	}
	
	@Override
	protected void initData() {
		
	}
	/**
	 * 通用返回按钮
	 * 
	 * @param v
	 */
	public void backIntent(View v) {
		finish();
	}
	/**
	 * 确认订单按钮响应事件
	 * 
	 * @param v
	 */
	public void weGroupSubmmit(View v) {
		Intent i = new Intent(WeGroupDetailActivity.this,WegroupSActivity.class);
		this.startActivity(i);
	}
	
	//监听返回键
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 
        if (keyCode == KeyEvent.KEYCODE_BACK
                 && event.getRepeatCount() == 0) {
             finish();
             return true;
         }
         return super.onKeyDown(keyCode, event);
     }

	@Override
	public void onRefresh() {
		mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
	}
	
	private Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
			case REFRESH_COMPLETE:
//				mDatas.addAll(Arrays.asList("Lucene", "Canvas", "Bitmap"));
				
//				adapter.notifyDataSetChanged();
				mSwipeLayout.setRefreshing(false);
				break;

			}
		};
	};
}
