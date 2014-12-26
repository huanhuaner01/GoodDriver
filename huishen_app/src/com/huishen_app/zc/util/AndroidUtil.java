package com.huishen_app.zc.util;

import com.huishen_app.zc.ui.base.BaseActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class AndroidUtil {

	/**
	 * �����ֻ��ķֱ��ʴ� dp �ĵ�λ ת��Ϊ px(����)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * �����ֻ��ķֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	public static int getImageWidth(Context context ,int id){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), id);/* ���ﷵ�ص�bmp��null */
           int width=options.outWidth;
//           int height=options.outHeight;
		return width;
		
	}
	
	public static int getImageHeight(Context context ,int id){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), id);/* ���ﷵ�ص�bmp��null */
//           int width=options.outWidth;
           int height=options.outHeight;
		return height;	
	}
	/**
	 * ���ͼƬ�ı�������/��
	 * @param context
	 * @param id
	 * @return
	 */
	public static float getImageScale(Context context ,int id){
		if(context == null || id == 0){
			return 0 ;
		}
		float width = 1 ;
		float height = 0 ;
		try{
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inSampleSize = 1;
		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), id,options);/* ���ﷵ�ص�bmp��null */
        width=options.outWidth;
        height=options.outHeight;
           Log.i("ImageSize", width+" "+height);
		}catch(Exception e){
			e.printStackTrace() ;
			return 0 ;
		}
		Log.i("ImageSize", "height/width:"+height/width);
		return height/width;	
	}
	/**
	 * ��ȡͼƬ���ź�Ĵ�С����Ϊ����ڵĿ�
	 * @param context ����baseActivity
	 * @param id ͼƬid (ͼƬ����Դ�ļ���)
	 * @return LinearLayout.LayoutParams
	 */
	public static LinearLayout.LayoutParams getImageScaleParams(BaseActivity context ,int id){
		if(context == null || id == 0){
			return null ;
		}
		LinearLayout.LayoutParams r_params = null ;
		try{
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inSampleSize = 1;
		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), id,options);/* ���ﷵ�ص�bmp��null */
           int width=options.outWidth;
           int height=options.outHeight;
           Log.i("ImageSize", width+" "+height);
        r_params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, context.getWidth() * height/width);
		}catch(Exception e){
			e.printStackTrace() ;
			return null ;
		}
		return r_params;	
	}
}