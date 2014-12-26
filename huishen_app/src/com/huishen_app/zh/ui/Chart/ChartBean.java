package com.huishen_app.zh.ui.Chart;

import android.graphics.Color;

/**
 * ��̬����ͼ�Ļ�������
 * ���а��������Ҽ�࣬���⣬������ɫ�������ǩ��ɫ�����������ɫ��������ɫ��X���ǩ�飬Y���ǩ��
 * @author zhanghuan
 *
 */
public class ChartBean {
	//���ߺͱ�����ɫ
	private int bgColor = Color.WHITE ;
	private int XYpointColor = Color.BLACK ;
	private int TitleColor = Color.BLACK;
	private int XYLabelColor = Color.BLACK;
	private int TableColor = Color.GRAY ;
	// �����ı�
	private String Title = "����ߴ���ѵ����ͼ";
	// Ĭ�ϱ߾�
	private int Margin = 40;
	//X,Y��ǩ
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
