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
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	public static int getImageWidth(Context context ,int id){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), id);/* 这里返回的bmp是null */
           int width=options.outWidth;
//           int height=options.outHeight;
		return width;
		
	}
	
	public static int getImageHeight(Context context ,int id){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), id);/* 这里返回的bmp是null */
//           int width=options.outWidth;
           int height=options.outHeight;
		return height;	
	}
	/**
	 * 获得图片的比例（高/宽）
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
		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), id,options);/* 这里返回的bmp是null */
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
	 * 获取图片缩放后的大小（宽为活动窗口的宽）
	 * @param context 父类baseActivity
	 * @param id 图片id (图片在资源文件中)
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
		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), id,options);/* 这里返回的bmp是null */
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