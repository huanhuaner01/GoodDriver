package com.huishen_app.zh.calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.huishen_app.zc.ui.R;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;

/**
 * �����ؼ�
 * @author zhanghuan
 *
 */
public class CalendarActivity extends FragmentActivity {
	public static String BEGIN_DATE = "beginDate" ,END_DATE = "endDate" ,
			SECTION_STATUS="isSection" ,RESULT_DATA = "selectDate";
	private String TAG = "MainActivity" ;
    private TextView title ;
    private Button back ;
    private ViewPager mPager ;
    private String beginDate , endDate ; //�����ؼ��Ŀ�ѡ���� 
    private boolean isSection = false ; //�Ƿ���ѡ��״̬
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Bundle bundle = this.getIntent().getExtras(); 
        isSection = bundle.getBoolean(SECTION_STATUS) ;
        if(isSection){
        	this.beginDate = bundle.getString(this.BEGIN_DATE) ;
        	this.endDate = bundle.getString(this.END_DATE) ;
        }
        registView();
        init();
        
  
    }
    
    /**
     * ע�����
     */
    private void registView() {
    	 mPager = (ViewPager)this.findViewById(R.id.pager) ;
         title = (TextView)this.findViewById(R.id.header_title) ;
         this.back = (Button)this.findViewById(R.id.header_back) ;
	}
    
    /**
     * ��ʼ���¼�
     */
	private void init() {
	      CalendarPagerAdapter mPagerAdapter = new CalendarPagerAdapter(getSupportFragmentManager());
	        
	        /**************ѡ����������*********************/
	       if(this.isSection){
	        mPagerAdapter.setSection(beginDate, endDate) ;
	       }
	        /**************ѡ���������ý���******************/
	        
			mPager.setAdapter(mPagerAdapter);
			mPager.setOnPageChangeListener(new OnPageChangeListener(){

				@Override
				public void onPageScrollStateChanged(int arg0) {
					
				}
	            
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					
				}

				@Override
				public void onPageSelected(int index) {
					Date date = CalendarUtil.getDate(index) ;
					SimpleDateFormat df = new SimpleDateFormat("yyyy/MM");
					String d = df.format(date) ;
					title.setText(d) ;
				}
				
			});
			setToDay();
			this.back.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					CalendarActivity.this.finish() ;
				}
				
			}) ;
	}
	
	/**
	 * ���ý���
	 */
	private void setToDay(){
    	Calendar cal=Calendar.getInstance();
    	int year = cal.get(Calendar.YEAR) ;
    	int mouth = cal.get(Calendar.MONTH) ;
    	int day = cal.get(Calendar.DATE) ;
    	int index = (year - 1970)*12+mouth;
    	
//    	Log.i(TAG, "index :"+index+" ����ʱ����"+year+"��"+mouth+"��"+day+"��"+" cal.getttime():"+cal.getTime().toString()) ;
    	if(this.isSection){
    		int begin  = CalendarUtil.getMouthsForDate(this.beginDate) ;
        	int end = CalendarUtil.getMouthsForDate(endDate) ;
    	if(index >= begin && index <= end){
    	this.mPager.setCurrentItem(index) ;
    	}else{
    		this.mPager.setCurrentItem(begin) ;
    	}
    	}else{
    		this.mPager.setCurrentItem(index) ;
    	}
    }
}
