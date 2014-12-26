package com.huishen_app.zh.ui.Chart;

import android.graphics.Color;

/**
 * ��̬�����������ݽṹ
 * ���а�����������ɫ����ϸ������ͼ�����ƣ���������
 * @author zhanghuan
 *
 */
public class ChartLineBean {

	private int lineColor = Color.BLACK ;
	private int lineSize = 1 ;
	private String linename = "����" ;
	private int[] data = {1,2,3,4,5,6,7,8,9};
	
	
	
	public ChartLineBean() {
		super();
	}

	public ChartLineBean(int lineColor, int lineSize, String linename,
			int[] data) {
		super();
		this.lineColor = lineColor;
		this.lineSize = lineSize;
		this.linename = linename;
		this.data = data;
	}
	
	public int getLineColor() {
		return lineColor;
	}
	public void setLineColor(int lineColor) {
		this.lineColor = lineColor;
	}
	public int getLineSize() {
		return lineSize;
	}
	public void setLineSize(int lineSize) {
		this.lineSize = lineSize;
	}
	public String getLinename() {
		return linename;
	}
	public void setLinename(String linename) {
		this.linename = linename;
	}
	public int[] getData() {
		return data;
	}
	public void setData(int[] data) {
		this.data = data;
	
	}
	
	
}
