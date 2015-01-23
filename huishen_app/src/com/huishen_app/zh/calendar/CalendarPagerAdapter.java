package com.huishen_app.zh.calendar;


import java.util.Date;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 * 日历fragment适配器
 * @author zhanghuan
 *
 */
public class CalendarPagerAdapter extends FragmentStatePagerAdapter {
    private String TAG = "CalendarPagerAdapter" ;
    private String beginDate , endDate ; //日历控件的可选区间 
    private boolean isSection  = false ;
	public CalendarPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	/**
	 * 设置可选区间
	 */
    public void setSection(String beginDate ,String endDate){
    	this.beginDate = beginDate ;
    	this.endDate = endDate ;
    	this.isSection = true ;
    } 
    
	@Override
	public Fragment getItem(int position) {
		CalendarPagerFragment fragment = CalendarPagerFragment.create(position) ;
		if(this.isSection){
			return CalendarPagerFragment.create(position ,this.beginDate ,this.endDate) ;
		}
		return CalendarPagerFragment.create(position);
	}

	@Override
	public int getCount() {
//		Log.i(TAG, CalendarUtil.getAllMonth()+"") ;
		return CalendarUtil.getAllMonth();
//		return 10;
	}

}
