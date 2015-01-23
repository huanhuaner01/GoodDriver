package com.huishen_app.zh.calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import com.huishen_app.zc.ui.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class CalendarPagerFragment extends Fragment {

	public static final String INDEX = "index", DATE_BEGIN = "begin",
			DATE_END = "end";
	private String TAG = "CalendarPagerFragment";
	private int mMonthIndex;
	private CalendarGridViewAdapter adapter;
	private Date beginDate, endDate; // �����ؼ��Ŀ�ѡ����
	private boolean isSection = false;

	public static CalendarPagerFragment create(int monthIndex) {
		CalendarPagerFragment fragment = new CalendarPagerFragment();
		Bundle args = new Bundle();
		args.putInt(INDEX, monthIndex);
		fragment.setArguments(args);
		return fragment;
	}

	public static CalendarPagerFragment create(int monthIndex,
			String beginDate, String endDate) {
		CalendarPagerFragment fragment = new CalendarPagerFragment();
		Bundle args = new Bundle();
		args.putInt(INDEX, monthIndex);
		args.putString(DATE_BEGIN, beginDate);
		args.putString(DATE_END, endDate);
		fragment.setArguments(args);
		return fragment;
	}

	/**
	 * ���ÿ�ѡ����
	 */
	public void setSection(String beginDate, String endDate) {

		this.beginDate = CalendarUtil.getDate(beginDate);
		this.endDate = CalendarUtil.getDate(endDate);
		if (this.beginDate == null) {
			this.beginDate = new GregorianCalendar(CalendarUtil.MIN_YEAR, 0, 1)
					.getTime();
		}
		if (this.endDate == null) {
			this.endDate = new GregorianCalendar(CalendarUtil.MAX_YEAR, 11, 31)
					.getTime();
		}
		Log.i(TAG, "endDate is " + this.endDate);
		this.isSection = true;
	}

	/**
	 * �ж������Ƿ��ڿ�ѡ����
	 */
	private boolean dayisSection(Date date) {
		Log.i(TAG, "begindate is :" + beginDate.toString());
		int i = date.compareTo(beginDate);
		int j = date.compareTo(endDate);
		if (i >= 0 && j <= 0) {
			return true;
		}
		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mMonthIndex = getArguments().getInt(INDEX);
		if (getArguments().getString(DATE_BEGIN) == null
				&& getArguments().getString(DATE_END) == null) {
			return;

		}
		setSection(getArguments().getString(DATE_BEGIN), getArguments()
				.getString(DATE_END));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = null;
		view = inflater.inflate(R.layout.fragment_calendar, container, false);
		GridView grid = (GridView) view.findViewById(R.id.gridview);
		adapter = new CalendarGridViewAdapter(container.getContext(),
				getDays(), R.layout.calendar_day, new String[] { "day" },
				new int[] { R.id.day });
		grid.setAdapter(adapter);
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {

				HashMap<String, Object> map = (HashMap<String, Object>) view
						.getTag();
				Log.i(TAG, map.toString());
				if (Integer.parseInt(map.get("status").toString()) < 0) {
					return;
				}
				adapter.selectOption(position);
				Date date = CalendarUtil.getDate(mMonthIndex);
				SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/");
				String d = df.format(date);
				Intent intent = new Intent(); 
				intent.putExtra(CalendarActivity.RESULT_DATA, d + map.get("day"));
				CalendarPagerFragment.this.getActivity().setResult(CalendarPagerFragment.this.getActivity().RESULT_OK, intent); 
				CalendarPagerFragment.this.getActivity().finish();
			}

		});
		return view;

	}

	private List<HashMap<String, Object>> getDays() {
		int daynum = CalendarUtil.getDayNum(mMonthIndex);
		int dayweek = CalendarUtil.getDayWeek(mMonthIndex);
		Date today = CalendarUtil.getDate(CalendarUtil.getCurrentMouth(),
				CalendarUtil.getCurrentDay());
		List<HashMap<String, Object>> days = new ArrayList<HashMap<String, Object>>();
		for (int i = 1; i < dayweek; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("day", "");
			map.put("status", -1);
			days.add(map);
		}
		for (int i = 1; i <= daynum; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("day", i);
			if (this.isSection) { // ����ѡ��
				    map.put("status", -1);
/*****************************��һ���жϷ���************************************************/
				    Date newDay = CalendarUtil.getDate(this.mMonthIndex, i);
				    if(dayisSection(newDay)){
				    	map.put("status", 0);
				    	if(this.mMonthIndex == CalendarUtil.getCurrentMouth()&&dayisSection(today)){
				    			if(i == CalendarUtil.getCurrentDay()){
				    				map.put("status", 2);
				    				}
				    	}else{
				    		if(this.mMonthIndex == CalendarUtil.getMouthsForDate(this.beginDate)){
			    				if(i == CalendarUtil.getDayForDate(this.beginDate)){
			    					map.put("status", 1);
			    				}
			    			}else{
			    				if(i==1){
			    					map.put("status", 1);
			    				}
			    			}
				    	}
				    }else{
						if (this.mMonthIndex == CalendarUtil.getCurrentMouth()
						&& i == CalendarUtil.getCurrentDay()) { // �ж��Ƿ��ǽ���
							map.put("status", -2);
						}
				    }
/*****************************��һ���жϷ�������************************************************/	
				    
/*****************************�ڶ����жϷ���***************************************************/				    
//					if (this.mMonthIndex == CalendarUtil.getCurrentMouth()
//							&& i == CalendarUtil.getCurrentDay()) { // �ж��Ƿ��ǽ���
//						if (dayisSection(today)) {
//							// ��������ն��ͽ���������ͬ������Ϊ����״̬
//							map.put("status", 2);
//						} else {
//							// ��������ն��ͽ���������ͬ������Ϊ���첻��ѡ״̬
//							map.put("status", -2);
//						}
//					} else { //���ǽ���
//						//�ж��Ƿ���ѡ����
//						Date newDay = CalendarUtil.getDate(this.mMonthIndex, i);
//						if(dayisSection(newDay)){
//							map.put("status", 0);
//							if(CalendarUtil.getMouthsForDate(this.beginDate) == CalendarUtil.getCurrentMouth()){
//								if(!dayisSection(today) && i == CalendarUtil.getDayForDate(this.beginDate) 
//										&& this.mMonthIndex ==CalendarUtil.getMouthsForDate(this.beginDate)  ){
//									map.put("status", 1);
//								}
//							}else{
//								if(this.mMonthIndex == CalendarUtil.getMouthsForDate(this.beginDate)){
//									if(i == CalendarUtil.getDayForDate(this.beginDate)){
//										map.put("status", 1);
//									}
//								}else{
//									if(this.mMonthIndex !=CalendarUtil.getCurrentMouth() && i == 1){
//										map.put("status", 1);
//									}
//								}
//							}
//						}
//						}
/*****************************�ڶ����жϷ�������***************************************************/					    
			} else {
				map.put("status", 0);
				if (CalendarUtil.getCurrentDay() > daynum) { // �������ڴ���Ŀǰ�·ݵ�����
					// ����ǵ�һ�죬����Ϊѡ��״̬
					if (i == 1)
						map.put("status", 1);

				} else { // ����������첻���ڱ��µ�����

					if (this.mMonthIndex == CalendarUtil.getCurrentMouth()
							&& i == CalendarUtil.getCurrentDay()) { // ��������ն��ͽ���������ͬ������Ϊ����״̬
						map.put("status", 2);
					} else if (i == CalendarUtil.getCurrentDay()) { // ֻ�����ںͽ���������ͬ������Ϊѡ��״̬
						map.put("status", 1);
					}
				}
			}
			Log.i(TAG, map.toString());
			days.add(map);
		}
		return days;
	}
}
