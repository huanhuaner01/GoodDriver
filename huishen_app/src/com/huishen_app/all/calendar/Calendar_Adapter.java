package com.huishen_app.all.calendar;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

import java.util.Calendar;
import java.util.Locale;

import com.huishen_app.all.calendar.Calendar_ui.OnTodaySelectedListener;
import com.huishen_app.zc.ui.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Calendar_Adapter extends BaseAdapter implements
		OnTodaySelectedListener {
    public Calendar startDate;
    public Calendar activeMonth;

    public int selectedPosition;
    public int selectedDay;

    private Context mContext;
    private Handler mHandler;
    public int count;
    private TextView[] week = new TextView[7];
    private int[] dayId = {
            R.id.sun, R.id.mon, R.id.tue, R.id.wed, R.id.thu,
            R.id.fri, R.id.sat
    };

    public Calendar_Adapter(Context mContext, Handler mHandler, int start, int end) {
        super();
        this.mContext = mContext;
        this.mHandler = mHandler;
        startDate = Calendar.getInstance();
        setMidnight(startDate);
        activeMonth = Calendar.getInstance();
        setMidnight(activeMonth);
        selectedDay = startDate.get(Calendar.DAY_OF_WEEK) - 1;
        int offset = selectedDay + start * 7;
        startDate.add(Calendar.DATE, -offset);
        this.count = start + end;
    }

    public void setActiveMonth(int position) {
        activeMonth = (Calendar) startDate.clone();
        activeMonth.add(Calendar.DATE, position * 7);
    }

    public int getCount() {
        return count;
    }

    public Object getItem(int arg0) {
        Calendar itemDate = (Calendar) startDate.clone();
        itemDate.add(Calendar.DATE, arg0 * 7);
        return itemDate;
    }

    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("deprecation")
	public View getView(int position, View convertView, ViewGroup parent) {
        Calendar itemDate = (Calendar) startDate.clone();
        itemDate.add(Calendar.DATE, position * 7);

        View currentView = convertView;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            currentView = vi.inflate(R.layout.calendar_list_item, null);
        }
        for (int i = 0; i < 7; i++) {
            week[i] = (TextView) currentView.findViewById(dayId[i]);
            week[i].setText(itemDate.get(Calendar.DATE) + "");

            week[i].setTag(position);
            week[i].setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    selectedPosition = (Integer) v.getTag();
                    Calendar itemDate = (Calendar) startDate.clone();

                    for (int i = 0; i < 7; i++) {
                        if (v.getId() == dayId[i]) {
                            itemDate.add(Calendar.DATE, selectedPosition * 7 + i);
                            selectedDay = i;
                            Calendar_Adapter.this.notifyDataSetChanged();
                            break;
                        }
                    }
//                    msg.what = type;
//                    msg.obj = date;
//                    sendRefreshMsg(itemDate, Calendar_ui.REFRESH_SELECTED_DATE);
                    mHandler.sendMessage(Message.obtain(mHandler, Calendar_ui.REFRESH_SELECTED_DATE, itemDate));
                    mHandler.sendMessage(Message.obtain(mHandler, Calendar_ui.MSG_RETURN, itemDate));
//                    sendRefreshMsg(itemDate, 0x1100);
                }
            });
            if (selectedPosition == position && i == selectedDay) {// 已选择的日期
                week[i].setBackgroundColor(mContext.getResources().getColor(
                        R.color.calendar_selected_date_bg));
                week[i].setTextColor(mContext.getResources().getColor(
                        R.color.calendar_selected_date_text));
            } else {
                if (activeMonth.get(Calendar.MONTH) == itemDate
                        .get(Calendar.MONTH)
                        && activeMonth.get(Calendar.YEAR) == itemDate
                                .get(Calendar.YEAR)) {// 当前月
                    week[i].setBackgroundDrawable(mContext.getResources().getDrawable(
                            R.drawable.calendar_cell_bg));
                    week[i].setTextColor(mContext.getResources().getColor(
                            R.color.calendar_current_month_text));
                } else {
                    week[i].setBackgroundDrawable(mContext.getResources().getDrawable(
                            R.drawable.calendar_cell_bg));
                    week[i].setTextColor(mContext.getResources().getColor(
                            R.color.calendar_other_month_text));
                }
            }

            itemDate.add(Calendar.DATE, 1);
        }
        return currentView;
    }

//    private void sendRefreshMsg(Calendar date, int type) {
//        Message msg = new Message();
//        msg.what = type;
//        msg.obj = date;
//        mHandler.sendMessage(msg);
//    }

    public int setSelectedDate(Calendar date) {
        long start = startDate.getTimeInMillis();
        long target = date.getTimeInMillis();
        long ms = target - start;
        int day = (int) ((ms) / (3600 * 24 * 1000));
        selectedPosition = day / 7;
        selectedDay = day % 7;
        setActiveMonth(selectedPosition);
        this.notifyDataSetChanged();
        mHandler.sendMessage(Message.obtain(mHandler, Calendar_ui.REFRESH_SELECTED_DATE, date));
//        sendRefreshMsg(date, Calendar_ui.REFRESH_SELECTED_DATE);
        return selectedPosition;
    }

    private void setMidnight(Calendar cal) {
        cal.set(HOUR_OF_DAY, 0);
        cal.set(MINUTE, 0);
        cal.set(SECOND, 0);
        cal.set(MILLISECOND, 0);
    }

	@Override
	public void onTodaySelected() {
		Calendar today = Calendar.getInstance(Locale.CHINA);
		mHandler.sendMessage(Message.obtain(mHandler, Calendar_ui.REFRESH_SELECTED_DATE, today));
		mHandler.sendMessage(Message.obtain(mHandler, Calendar_ui.MSG_RETURN, today));
	}

}
