package com.huishen_app.zc.ui.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class ViewPageAdapter extends PagerAdapter {

	// ��Ҫ���ص�ҳ��
	private List<View> mViewList;

	// ��ʼ������
	public ViewPageAdapter(List<View> mListViews) {
		this.mViewList = mListViews;
	}

	@Override
	public int getCount() {
		// if (mViewList != null)
			// return mViewList.size();
		// return 0;
		return Integer.MAX_VALUE;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		position %= mViewList.size();
		if (position<0){
			position = mViewList.size()+position;
		}
		View view = mViewList.get(position);
		//���View�Ѿ���֮ǰ��ӵ���һ����������������remove��������׳�IllegalStateException��
		ViewParent vp = view.getParent();
		if (vp!=null){
			ViewGroup parent = (ViewGroup)vp;
			parent.removeView(view);
		}
        container.addView(view);  
        //add listeners here if necessary
         return view;  
		// ((ViewPager) view).addView(mViewList.get(index), 0);
		// return mViewList.get(index);
	}

	@Override
	public void destroyItem(View view, int position, Object arg2) {
		// ((ViewPager) view).removeView(mViewList.get(position));
	}
	
	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return (view == obj);
	}
}
