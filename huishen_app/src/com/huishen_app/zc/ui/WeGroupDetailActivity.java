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
		this.title.setText("�Ź�����");
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(android.R.color.holo_green_dark, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);
		//�������ø���ͼƬ�Ĵ�С
		if(AndroidUtil.getImageScaleParams(this, R.drawable.wegroup_info )!= null){
		this.desimg.setLayoutParams(AndroidUtil.getImageScaleParams(this, R.drawable.wegroup_info));
		}
		if(AndroidUtil.getImageScaleParams(this, R.drawable.wegroup_info )!= null){
		this.headerimg.setLayoutParams(AndroidUtil.getImageScaleParams(this, R.drawable.wegroup_bg));
		}
		
//		String pricemsg = "�۸񣺣� <font color='red'><strong>5999 </strong></font>&nbsp;&nbsp;&nbsp;<del>��7999</del>";
		TextStyleUtil.setTextAppearanceSpan(this,this.price ,"5999 ");
		TextStyleUtil.addStrikeSpan(this.price ,"��7888");
		
		String[] key = new String[]{"1����������: ","2����ѧ������","3������ѧʱ��","4��������ʽ��","5���ػݼ۸�",
				"6��ȫ��һ���ƣ�","7����·��","8�����ð�����","9�����ѧԱ��","10����֤ʱ�䣺","11��������ַ��","12�����ͷ���","13�������ύ��"};
		String[] value = new String[]{"18-65��δѧ����ʿ��<br />",
				     "�ݴɣ���ɵȿ��Գ��ͣ�<br />",
				     "ÿ������Լ�����Σ�ÿ����������ѧʱ��45����/ ѧʱ�����ر����ѣ���Ҫ��ǰһ��绰ԤԼ��<br />",
				     "ÿ���������һ��������<br />",
				     "�г��ۣ�4880Ԫ���ּ�4500Ԫ��һ�Ѱ��ɣ���;���������ѣ��N����ѧ�����ṩȫ�̵�����<br />",
				     "������������ѡ�ICָ�ƿ��ѡ����۽̲ķѡ�����ѧϰ�����ۿ��ԡ�������ѵ�����ؿ��ԡ���·��ѵ��·������ȫ�������ԡ�ʵϰ�ơ����ա�ÿ����Ŀ��������һ����ѵĲ������᣻<br />",
				     "ѵ����400���<br />",
				     "��ѵ��/�鱾��/400����·ѵ/���Խ��ͷ�;<br />",
				     "��Ѱ����ס֤��ѧ��֤��<br />",
				     "����������60�졣����ѧԱƽ��3-4���£����ѧԱƽ��4-5���£�ѧԱ����ʱ��ֱ��Ӱ��ѧԱ��֤ʱ�䣻<br />",
				     "������ַ�����μ�У�岨���أ����μ�У����ӻ��أ�<br />",
				     "ԤԼ����������ͣ�������ѽ��ͣ�<br />",
				     "ѧԱ����ͨ��ͬ�ǿ�ݼ��ͣ�֧�ֵ������N����ѧ����֧��������š�<br />"};
		/**
		 * 1����������18-65��δѧ����ʿ��
           2����ѧ�������ݴɣ���ɵȿ��Գ��ͣ�
           3������ѧʱ��ÿ������Լ�����Σ�ÿ����������ѧʱ��45����/ ѧʱ�����ر����ѣ���Ҫ��ǰһ��绰ԤԼ��
           4��������ʽ��ÿ���������һ��������
           5���ػݼ۸��г��ۣ�4880Ԫ���ּ�4500Ԫ��һ�Ѱ��ɣ���;���������ѣ��N����ѧ�����ṩȫ�̵�����
           6��ȫ��һ���ƣ�������������ѡ�ICָ�ƿ��ѡ����۽̲ķѡ�����ѧϰ�����ۿ��ԡ�������ѵ�����ؿ��ԡ���·��ѵ��·������ȫ�������ԡ�ʵϰ�ơ����ա�ÿ����Ŀ��������һ����ѵĲ������᣻
           7����·��ѵ����400���
           8�����ð�������ѵ��/�鱾��/400����·ѵ/���Խ��ͷ�
           9�����ѧԱ����Ѱ����ס֤��ѧ��֤��
           10����֤ʱ�䣺����������60�졣����ѧԱƽ��3-4���£����ѧԱƽ��4-5���£�ѧԱ����ʱ��ֱ��Ӱ��ѧԱ��֤ʱ�䣻
           11��������ַ�����μ�У�岨���أ����μ�У����ӻ��أ�
           12�����ͷ���ԤԼ����������ͣ�������ѽ��ͣ�
           13�������ύ��ѧԱ����ͨ��ͬ�ǿ�ݼ��ͣ�֧�ֵ������N����ѧ����֧��������š�
		 */
		//���ñ�����֪
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
	 * ͨ�÷��ذ�ť
	 * 
	 * @param v
	 */
	public void backIntent(View v) {
		finish();
	}
	/**
	 * ȷ�϶�����ť��Ӧ�¼�
	 * 
	 * @param v
	 */
	public void weGroupSubmmit(View v) {
		Intent i = new Intent(WeGroupDetailActivity.this,WegroupSActivity.class);
		this.startActivity(i);
	}
	
	//�������ؼ�
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
