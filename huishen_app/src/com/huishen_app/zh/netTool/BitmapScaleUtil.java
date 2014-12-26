package com.huishen_app.zh.netTool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * ����ͼƬ�Ĺ����ࡣ
 * @author Muyangmin
 * @create 2014-3-25
 * @version 2.0
 */
public final class BitmapScaleUtil {
	/**
	 * ����ͼƬ��ָ���ߴ磬�÷���������R.XXX��
	 * @param context ��������Ϣ
	 * @param res Ҫ���ŵ�ͼƬԴID
	 * @param desw Ŀ����
	 * @param desh Ŀ��߶�
	 * @return
	 * @lastModify 2014-3-23 by Muyangmin
	 * @description (optional)
	 */
	public static Bitmap scaleBitmap(Context context, int res, int desw, int desh){
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), res);
		return scaleBitmap(context, bitmap, desw, desh);
	}
	/**
	 * ����ͼƬ��ָ���ߴ磬�÷����������ֳɵ�Bitmap��
	 * @param context ��������Ϣ
	 * @param bmp Ҫ���ŵ�ͼƬ
	 * @param desw Ŀ����
	 * @param desh Ŀ��߶�
	 * @return
	 * @lastModify 2014-3-25 by Muyangmin
	 * @description (optional)
	 */
	public static Bitmap scaleBitmap(Context context, Bitmap bmp, int desw, int desh){
		//���Դ�ߴ�
		int srcw = bmp.getWidth();
		int srch = bmp.getHeight();
		//�������ű�
		float scalew = (float)desw/srcw;
		float scaleh = (float)desh/srch;
		//����Matrix����
		Matrix matrix = new Matrix();
		matrix.postScale(scalew, scaleh);
		//������ͼƬ
		Bitmap nb = Bitmap.createBitmap(bmp, 0, 0, srcw, srch, matrix, true);
		return nb;
	}
}
