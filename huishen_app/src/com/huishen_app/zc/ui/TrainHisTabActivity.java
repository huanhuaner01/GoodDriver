package com.huishen_app.zc.ui;

import java.util.ArrayList;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.ui.fragment.BaseFragment;
import com.huishen_app.zc.ui.fragment.SubOneFragment;
import com.huishen_app.zc.ui.fragment.TrainHisFragment;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class TrainHisTabActivity extends BaseActivity {
	// ViewPager��google SDk���Դ���һ�����Ӱ���һ���࣬��������ʵ����Ļ����л���
	// android-support-v4.jar
	private ViewPager mPager;//ҳ������
	private ArrayList<Fragment> listViews; // Tabҳ���б�
	private ImageView cursor;// ����ͼƬ
	private TextView t1, t2, t3,t4;// ҳ��ͷ��
	private int offset = 0;// ����ͼƬƫ����
	private int currIndex = 1;// ��ǰҳ�����
	private int bmpW;// ����ͼƬ���
	private TextView currentTab ; //Ŀǰ����ҳ��İ�ť
	private BaseFragment f1 ,f2 ,f3 , f4;
	private ArrayList<TextView> tabs ;
	/**
	 * ��ʼ��ͷ��
	 */
	private void InitTextView() {
		t1 = (TextView)findViewById(R.id.text1);
		t2 = (TextView)findViewById(R.id.text2);
		t3 = (TextView)findViewById(R.id.text3);
		t4 = (TextView)findViewById(R.id.text4);
		
		tabs = new ArrayList<TextView>();
		tabs.add(t1);
		tabs.add(t2);
		tabs.add(t3);
		tabs.add(t4);
		
		f1 = new SubOneFragment(this); 
		f2 = new TrainHisFragment(this); 
		f3 = new SubOneFragment(this); 
		f4 = new SubOneFragment(this); 
		
		t1.setOnClickListener(new MyOnClickListener(0));
		t2.setOnClickListener(new MyOnClickListener(1));
		t3.setOnClickListener(new MyOnClickListener(2));
		t4.setOnClickListener(new MyOnClickListener(3));
	
	}

	/**
	 * ��ʼ��ViewPager
	 */
	private void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.vPager);
		listViews = new ArrayList<Fragment>();
		listViews.add(f1);
		listViews.add(f2);
		listViews.add(f3);
		listViews.add(f4);
		mPager.setOffscreenPageLimit(2);
		mPager.setAdapter(new MyPagerAdapter(this.getSupportFragmentManager(),listViews));
		mPager.setCurrentItem(1);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * ��ʼ������
	 */
	private void InitImageView() {
		cursor = (ImageView)findViewById(R.id.cursor);
	
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// ��ȡ�ֱ��ʿ��
		bmpW = screenW / 4 ;// ��ȡͼƬ���
		offset = screenW / 8;// ����ƫ����
		Matrix matrix = new Matrix();
		matrix.postTranslate(0,0);
		
	       LinearLayout.LayoutParams imagebtn_params = new LinearLayout.LayoutParams(
	              LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	              imagebtn_params.height = 5;
	              imagebtn_params.width = dm.widthPixels / 4;
	              cursor.setLayoutParams(imagebtn_params);
	             
	             
		cursor.setImageMatrix(matrix);// ���ö�����ʼλ��
		this.currentTab = t2 ;
		t2.setTextColor(getResources().getColor(R.color.main_title_background));
	    Animation animation = null;         
		cursor.setImageMatrix(matrix);// ���ö�����ʼλ��
		animation = new TranslateAnimation(0, offset*2, 0, 0);
		animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
		animation.setDuration(300);
		cursor.startAnimation(animation);
	}
	/**
	 * ViewPager������
	 */
	public class MyPagerAdapter extends FragmentPagerAdapter {
		private ArrayList<Fragment> fragmentsList;
		public MyPagerAdapter(FragmentManager fm ,ArrayList<Fragment> list) {
			super(fm);
			this.fragmentsList = list ;
		}

		

	    public MyPagerAdapter(FragmentManager fm) {
	        super(fm);
	    }

	    @Override
	    public int getCount() {
	        return fragmentsList.size();
	    }

	    @Override
	    public Fragment getItem(int arg0) {
	        return fragmentsList.get(arg0);
	    }

	    @Override
	    public int getItemPosition(Object object) {
	        return super.getItemPosition(object);
	    }

	}

	/**
	 * ͷ��������
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;
		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};

	/**
	 * ҳ���л�����
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 ;// ҳ��1 -> ҳ��2 ƫ����
		int two = one * 2;// ҳ��1 -> ҳ��3 ƫ����

		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);
			currIndex = arg0;
			animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
			animation.setDuration(300);
			cursor.startAnimation(animation);
			tabs.get(currIndex).setTextColor(getResources().getColor(R.color.main_title_background));
			currentTab.setTextColor(getResources().getColor(R.color.book_imitate_textcolornew));
			currentTab = tabs.get(currIndex);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	@Override
	protected void findViewById_Init() {
		this.setContentView(R.layout.train_his_tab_lay);
		InitTextView();
		InitImageView();		
		InitViewPager();
	}

	@Override
	protected void initView() {
		
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 
        if (keyCode == KeyEvent.KEYCODE_BACK
                 && event.getRepeatCount() == 0) {
             finish();
             return true;
         }
         return super.onKeyDown(keyCode, event);
     }
}
