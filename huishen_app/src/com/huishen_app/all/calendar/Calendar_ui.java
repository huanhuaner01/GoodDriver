package com.huishen_app.all.calendar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huishen_app.all.calendar.PoupSimpleList.OnPoupItemClickListener;
import com.huishen_app.zc.ui.Book_yuyue_first_ui;
import com.huishen_app.zc.ui.R;

public class Calendar_ui extends Activity {
	
	private static final String LOG_TAG = "Calendar_ui";
	
	private Calendar_Adapter calAdp;
	private ListView calListView;
	private TextView activeMonthTextView;
	private TextView selectedDateTextView;
	private static final DateFormat format1 = new SimpleDateFormat(
			Book_yuyue_first_ui.DATASTRUCT, Locale.CHINA);
	private LinearLayout headRowView;
	private LinearLayout headTitle;
	private RelativeLayout centerView;
	// private TextView dateTextView;
	private Button current_yearButton;
	private Button todaybButton;
	// private Button confirmButton;
	private String[] weekName = { "日", "一", "二", "三", "四", "五", "六" };
	private String selectDate;
	// ----
	private Calendar reCalendar;
	// ----
	public static final int REFRESH_SELECTED_DATE = 1;
	public static final int REFRESH_ACTIVE_MONTH = 2;
	private List<String> year = new ArrayList<String>();
	
	private OnTodaySelectedListener todayListener;
	
	protected final void setOnTodaySelectedListener(OnTodaySelectedListener listener){
		todayListener = listener;
	}
	protected static interface OnTodaySelectedListener{
		void onTodaySelected();
	}
	
	protected static final int MSG_RETURN = 0x1100;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Calendar date = (Calendar) msg.obj;
			selectDate = date.get(Calendar.YEAR) + "/"
					+ (date.get(Calendar.MONTH) + 1) + "/"
					+ date.get(Calendar.DAY_OF_MONTH);
			reCalendar = (Calendar) date.clone();
			switch (msg.what) {
			case MSG_RETURN:
				setResultAndReturn();
				break;
			case REFRESH_SELECTED_DATE:
				selectedDateTextView.setText(format1.format(date.getTime()));
				refreshYearMonthView();
				break;
			}
		}
		
		private void setResultAndReturn(){
			Intent intent = new Intent();
			if (selectDate != null){
				
				if(compare_date(selectDate)){
					intent.putExtra("selectDate", selectDate);
				}else{
					Toast.makeText(Calendar_ui.this, "对不起，不能选中之前的时间", Toast.LENGTH_SHORT).show();
					return ;
				}
				
			}
			if (reCalendar != null)
				intent.putExtra("reCalendar", reCalendar);
			
			Calendar_ui.this.setResult(RESULT_OK, intent);
			// 当调用finsh()方法时，这个intent就会传递回调用这个Activity的源Activity的onActivityResult()方法里
			Calendar_ui.this.finish();
		}
	};

	public int preWeeks = 100 * 365;
	public int bacWeeks = 100 * 365;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar_lay);
		calAdp = new Calendar_Adapter(this, mHandler, bacWeeks, preWeeks);
		// confirmButton = (Button) findViewById(R.id.calendar_ok_btn);
		setOnTodaySelectedListener(calAdp);
		activeMonthTextView = (TextView) findViewById(R.id.active_month);
		selectedDateTextView = (TextView) findViewById(R.id.title);
		headRowView = (LinearLayout) findViewById(R.id.headrow);
		headTitle = (LinearLayout) findViewById(R.id.header);
		centerView = (RelativeLayout) findViewById(R.id.center);
		current_yearButton = (Button) findViewById(R.id.current_year_btn);
		todaybButton = (Button) findViewById(R.id.today_btn);
		calListView = (ListView) findViewById(R.id.calListView);
		Calendar today = Calendar.getInstance(Locale.getDefault());

		selectDate = today.get(Calendar.YEAR) + "年"
				+ (today.get(Calendar.MONTH) + 1) + "月"
				+ today.get(Calendar.DAY_OF_MONTH) + "日";
		// SimpleDateFormat monthNameFormat = new
		// SimpleDateFormat(getString(R.string.month_name_format),
		// Locale.getDefault());
		// title.setText(monthNameFormat.format(today.getTime()));
		// headGridView=(CalendarRowView) layout.getChildAt(1);
		// calendarPickerView=(CalendarPickerView) layout.getChildAt(2);
		// CalendarRowView headRowView=(CalendarRowView)
		// headGridView.getChildAt(0);
		int firstDayOfWeek = today.getFirstDayOfWeek();
		// SimpleDateFormat weekdayNameFormat = new
		// SimpleDateFormat(getString(R.string.day_name_format),
		// Locale.getDefault());
		for (int offset = 0; offset < 7; offset++) {
			today.set(Calendar.DAY_OF_WEEK, firstDayOfWeek + offset);
			final TextView textView = (TextView) headRowView.getChildAt(offset);
			// headGridView.removeView(textView);
			// textView.setText(weekdayNameFormat.format(today.getTime()));
			textView.setText(weekName[offset]);
		}
		for (int i = 1970; i <= 2036; i++) {
			year.add(i + "");
		}
		current_yearButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Calendar can = Calendar.getInstance();
				final PoupSimpleList poup = new PoupSimpleList(
						Calendar_ui.this, headTitle.getWidth(), centerView
								.getHeight());
				poup.setOnPoupItemClickListener(new OnPoupItemClickListener() {
					public void onPoupItemClick() {
						Calendar can = Calendar.getInstance();
						int cyear = Integer.parseInt(poup.getSelectionItem());
						can.set(Calendar.YEAR, cyear);
						calListView.setSelection(calAdp.setSelectedDate(can) - 3);
					}
				});
				poup.setData(year);
				poup.setSelection(can.get(Calendar.YEAR) - 1970);
				poup.show(headTitle, 0, 0);
			}
		});
		calListView.setAdapter(calAdp);
		// calAdp.selectedPosition = preWeeks;
		Calendar can = null;
		String calString = getIntent().getStringExtra("default_cal");
		if (calString != null && calString.length() > 0) {
			Date date = null;
			try {
				date = format1.parse(calString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			can = Calendar.getInstance();
			can.setTime(date);
		} else {
			can = Calendar.getInstance();
		}

		// can.add(Calendar.DATE, Calendar);

		calListView.setSelection(calAdp.setSelectedDate(can) - 3);// calAdp.selectedPosition-2);
		current_yearButton.setText(can.get(Calendar.YEAR) + "");
		// calAdp.notifyDataSetChanged();
		/*
		 * confirmButton.setOnClickListener(new OnClickListener() {
		 * 
		 * public void onClick(View v) { Intent intent = new Intent(); if
		 * (selectDate != null) intent.putExtra("selectDate", selectDate); if
		 * (reCalendar != null) intent.putExtra("reCalendar", reCalendar);
		 * CalendarActivity.this.setResult(RESULT_OK, intent); //
		 * 当调用finsh()方法时，这个intent就会传递回调用这个Activity的源Activity的onActivityResult
		 * ()方法里 CalendarActivity.this.finish();
		 * 
		 * } });
		 */
		todaybButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Calendar can = Calendar.getInstance();
				// can.add(Calendar.MONTH, -3);
				calListView.setSelection(calAdp.setSelectedDate(can) - 3);// calAdp.selectedPosition-2);
				// calListView.setSelection(calAdp.setSelectedDate(can)-3);//calAdp.selectedPosition-2);
				current_yearButton.setText(can.get(Calendar.YEAR) + "");
				// calAdp.notifyDataSetChanged();
				if (todayListener != null){
					todayListener.onTodaySelected();
				}
			}
		});
		calListView.setOnScrollListener(new OnScrollListener() {

			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				Calendar lastSelect = (Calendar) calAdp.activeMonth.clone();
				calAdp.setActiveMonth(firstVisibleItem + visibleItemCount / 2);
				if (calAdp.activeMonth.get(Calendar.MONTH) != lastSelect
						.get(Calendar.MONTH)
						|| calAdp.activeMonth.get(Calendar.YEAR) != lastSelect
								.get(Calendar.YEAR)) {
					refreshYearMonthView();
					calAdp.notifyDataSetChanged();
				}
			}
		});
	}

	// 返回按钮
	public void backIntent(View source) {
		finish();
	}

	private void refreshYearMonthView() {
		// 年份
		int year = calAdp.activeMonth.get(Calendar.YEAR);
		activeMonthTextView
				.setText((calAdp.activeMonth.get(Calendar.MONTH) + 1) + "月");
		current_yearButton.setText(year + "");
	}

	public boolean compare_date(String time) {
		DateFormat daf = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
		//skip today compare
		String today = daf.format(new Date());
		Log.d(LOG_TAG, "today="+today+", param time="+time);
		if (today.equals(time)){
			Log.d(LOG_TAG, "today is selected.");
			return true;
		}
		
		try {
			Date dt = new Date();
			String str = daf.format(dt);
			dt = daf.parse(str);
			Date date = daf.parse(time);
			if (dt.compareTo(date) > 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}

}
