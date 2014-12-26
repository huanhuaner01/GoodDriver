package com.huishen_app.all.calendar;

import static android.view.View.MeasureSpec.AT_MOST;
import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.makeMeasureSpec;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * TableRow that draws a divider between each cell. To be used with
 * {@link CalendarGridView}.
 */
public class CalendarRowView extends ViewGroup implements View.OnClickListener {
	private boolean isHeaderRow;
	// private WeekView.Listener listener;
	private int cellSize;

	public CalendarRowView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params) {
		child.setOnClickListener(this);
		super.addView(child, index, params);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// long start = System.currentTimeMillis();
		final int totalWidth = MeasureSpec.getSize(widthMeasureSpec);
		cellSize = totalWidth / 7;
		int cellWidthSpec = makeMeasureSpec(cellSize, EXACTLY);
		int cellHeightSpec = isHeaderRow ? makeMeasureSpec(cellSize, AT_MOST)
				: cellWidthSpec;
		int rowHeight = 0;
		for (int c = 0, numChildren = getChildCount(); c < numChildren; c++) {
			final View child = getChildAt(c);
			child.measure(cellWidthSpec, cellHeightSpec);
			// The row height is the height of the tallest cell.
			if (child.getMeasuredHeight() > rowHeight) {
				rowHeight = child.getMeasuredHeight();
			}
		}
		final int widthWithPadding = totalWidth + getPaddingLeft()
				+ getPaddingRight();
		final int heightWithPadding = rowHeight + getPaddingTop()
				+ getPaddingBottom();
		setMeasuredDimension(widthWithPadding, heightWithPadding);
		// Logr.d("Row.onMeasure %d ms", System.currentTimeMillis() - start);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		int cellHeight = bottom - top;
		for (int c = 0, numChildren = getChildCount(); c < numChildren; c++) {
			final View child = getChildAt(c);
			child.layout(c * cellSize, 0, (c + 1) * cellSize, cellHeight);
		}
		// Logr.d("Row.onLayout %d ms", System.currentTimeMillis() - start);
	}

	public void setIsHeaderRow(boolean isHeaderRow) {
		this.isHeaderRow = isHeaderRow;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * @Override public void onClick(View v) { // Header rows don't have a click
	 * listener if (listener != null) {
	 * listener.handleClick((MonthCellDescriptor) v.getTag()); } }
	 * 
	 * public void setListener(WeekView.Listener listener) { this.listener =
	 * listener; }
	 */
}
