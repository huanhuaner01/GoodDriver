package com.huishen_app.zh.netTool;


import android.graphics.Bitmap;

/**
 * �����ȡͼƬ�����ڸ��½���Ľӿڣ����������߳�
 * @author jfids
 *
 */
public interface ImageTUpdate {
	/**
	 * ���½���
	 * @param data
	 */
	public void update(Bitmap data);
	/**
	 * ����������ʾ
	 */
	public void setProgressStart();
	/**
	 * �رս�����ʾ
	 */
	public void setProgressEnd();
}
