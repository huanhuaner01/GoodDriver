package com.huishen_app.zh.netTool;


import android.graphics.Bitmap;

/**
 * 网络获取图片后用于更新界面的接口，运行于主线程
 * @author jfids
 *
 */
public interface ImageTUpdate {
	/**
	 * 更新界面
	 * @param data
	 */
	public void update(Bitmap data);
	/**
	 * 开启进度提示
	 */
	public void setProgressStart();
	/**
	 * 关闭进度提示
	 */
	public void setProgressEnd();
}
