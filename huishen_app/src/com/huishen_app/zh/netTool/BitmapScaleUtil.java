package com.huishen_app.zh.netTool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * 缩放图片的工具类。
 * @author Muyangmin
 * @create 2014-3-25
 * @version 2.0
 */
public final class BitmapScaleUtil {
	/**
	 * 缩放图片到指定尺寸，该方法适用于R.XXX。
	 * @param context 上下文信息
	 * @param res 要缩放的图片源ID
	 * @param desw 目标宽度
	 * @param desh 目标高度
	 * @return
	 * @lastModify 2014-3-23 by Muyangmin
	 * @description (optional)
	 */
	public static Bitmap scaleBitmap(Context context, int res, int desw, int desh){
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), res);
		return scaleBitmap(context, bitmap, desw, desh);
	}
	/**
	 * 缩放图片到指定尺寸，该方法适用于现成的Bitmap。
	 * @param context 上下文信息
	 * @param bmp 要缩放的图片
	 * @param desw 目标宽度
	 * @param desh 目标高度
	 * @return
	 * @lastModify 2014-3-25 by Muyangmin
	 * @description (optional)
	 */
	public static Bitmap scaleBitmap(Context context, Bitmap bmp, int desw, int desh){
		//获得源尺寸
		int srcw = bmp.getWidth();
		int srch = bmp.getHeight();
		//计算缩放比
		float scalew = (float)desw/srcw;
		float scaleh = (float)desh/srch;
		//创建Matrix对象
		Matrix matrix = new Matrix();
		matrix.postScale(scalew, scaleh);
		//生成新图片
		Bitmap nb = Bitmap.createBitmap(bmp, 0, 0, srcw, srch, matrix, true);
		return nb;
	}
}
