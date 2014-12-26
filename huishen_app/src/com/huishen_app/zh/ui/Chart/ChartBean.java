package com.huishen_app.zh.ui.Chart;

import android.graphics.Color;

/**
 * 静态折线图的基本属性
 * 其中包括：左右间距，标题，坐标颜色，坐标标签颜色，背景表格颜色，标题颜色，X轴标签组，Y轴标签组
 * @author zhanghuan
 *
 */
public class ChartBean {
	//曲线和背景颜色
	private int bgColor = Color.WHITE ;
	private int XYpointColor = Color.BLACK ;
	private int TitleColor = Color.BLACK;
	private int XYLabelColor = Color.BLACK;
	private int TableColor = Color.GRAY ;
	// 标题文本
	private String Title = "最近七次培训走势图";
	// 默认边距
	private int Margin = 40;
	//X,Y标签
	private int[] YLabel = {0,1,2,3,4,5,6,7,8};
	private String[] XLabel = {"0", "1", "2", "3", "4", "5", "6", "7", "8"} ;
	
	public ChartBean() {
		super();
	}
	public ChartBean(int bgColor, int xYpointColor, int titleColor,
			int xYLabelColor, int tableColor, String title, int margin,
			int[] yLabel, String[] xLabel) {
		super();
		this.bgColor = bgColor;
		XYpointColor = xYpointColor;
		TitleColor = titleColor;
		XYLabelColor = xYLabelColor;
		TableColor = tableColor;
		Title = title;
		Margin = margin;
		YLabel = yLabel;
		XLabel = xLabel;
	}
	public int[] getYLabel() {
		return YLabel;
	}
	public void setYLabel(int[] yLabel) {
		YLabel = yLabel;
	}
	public String[] getXLabel() {
		return XLabel;
	}
	public void setXLabel(String[] xLabel) {
		XLabel = xLabel;
	}
	public int getBgColor() {
		return bgColor;
	}
	public void setBgColor(int bgColor) {
		this.bgColor = bgColor;
	}
	public int getXYpointColor() {
		return XYpointColor;
	}
	public void setXYpointColor(int xYpointColor) {
		XYpointColor = xYpointColor;
	}
	public int getTitleColor() {
		return TitleColor;
	}
	public void setTitleColor(int titleColor) {
		TitleColor = titleColor;
	}
	public int getXYLabelColor() {
		return XYLabelColor;
	}
	public void setXYLabelColor(int xYLabelColor) {
		XYLabelColor = xYLabelColor;
	}
	public int getTableColor() {
		return TableColor;
	}
	public void setTableColor(int tableColor) {
		TableColor = tableColor;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public int getMargin() {
		return Margin;
	}
	public void setMargin(int margin) {
		Margin = margin;
	}
	
}
