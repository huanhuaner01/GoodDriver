package com.huishen_app.zh.util;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.huishen_app.zc.ui.R;

public class TextStyleUtil {
	//���ü۸����
	public static void setTextAppearanceSpan(Context context ,TextView tv ,String price) { 
		if(price.equals("")){
			return ;
		}
	    SpannableString spanString = new SpannableString(price);  
	    StyleSpan boldspan = new StyleSpan(Typeface.BOLD);
	    ForegroundColorSpan colorspan = new ForegroundColorSpan(context.getResources().getColor(R.color.main_title_background));
	    
	    spanString.setSpan(boldspan, 0, price.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
	    spanString.setSpan(colorspan, 0, price.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    spanString.setSpan(new RelativeSizeSpan(2.0f), 0, price.length(),Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
	    tv.append(spanString);  
	} 
	
	//���ü۸����
	public static SpannableString getTextAppearanceSpan(Context context,String price) { 
		if(price.equals("")){
			return null;
		}
	    SpannableString spanString = new SpannableString(price);  
	    StyleSpan boldspan = new StyleSpan(Typeface.BOLD);
	    ForegroundColorSpan colorspan = new ForegroundColorSpan(context.getResources().getColor(R.color.main_title_background));
	    
	    spanString.setSpan(boldspan, 0, price.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
	    spanString.setSpan(colorspan, 0, price.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    spanString.setSpan(new RelativeSizeSpan(2.0f), 0, price.length(),Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
	    return spanString;  
	} 
	
	//����������ۣ��Ӵ֣�����ɫ�������Զ���С������
	public static SpannableString getTextAppearanceSpan(Context context,String price ,float f) { 
		if(price.equals("")){
			return null;
		}
	    SpannableString spanString = new SpannableString(price);  
	    StyleSpan boldspan = new StyleSpan(Typeface.BOLD);
	    ForegroundColorSpan colorspan = new ForegroundColorSpan(context.getResources().getColor(R.color.main_title_background));
	    
	    spanString.setSpan(boldspan, 0, price.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
	    spanString.setSpan(colorspan, 0, price.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    spanString.setSpan(new RelativeSizeSpan(f), 0, price.length(),Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
	    return spanString;  
	} 
	
	//str1������ʾ ��str2�Ӵ֣�����ɫ
	public static SpannableString getTextAppearanceSpan(Context context,String str1 ,String str2) { 
		if(str1.equals("")||str2.equals("")){
			return null;
		}
	    SpannableString spanString = new SpannableString(str1+str2);  
//	    StyleSpan boldspan = new StyleSpan(Typeface.BOLD);
	    ForegroundColorSpan colorspan = new ForegroundColorSpan(context.getResources().getColor(R.color.main_title_background));
	    
//	    spanString.setSpan(boldspan, str1.length(), str1.length()+str2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
	    spanString.setSpan(colorspan, str1.length(), str1.length()+str2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    return spanString;  
	}
	
	/**
	 * ����������͵�����
	 * @param context
	 * @param str1
	 * @param str2
	 * @param color
	 * @return
	 */
	public static SpannableString getTextAppearanceSpan(Context context,String str1 ,String str2 ,int color) { 
		if(str1.equals("")||str2.equals("")){
			return null;
		}
	    SpannableString spanString = new SpannableString(str1+str2);  
//	    StyleSpan boldspan = new StyleSpan(Typeface.BOLD);
	    ForegroundColorSpan colorspan = new ForegroundColorSpan(context.getResources().getColor(color));
	    
//	    spanString.setSpan(boldspan, str1.length(), str1.length()+str2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
	    spanString.setSpan(colorspan, str1.length(), str1.length()+str2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    return spanString;  
	}
	//��������ɾ����
	public static void addStrikeSpan(TextView tv ,String str) {  
	    SpannableString spanString = new SpannableString(str);  
	    StrikethroughSpan span = new StrikethroughSpan();  
	    spanString.setSpan(span, 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
	    tv.append(spanString);  
	}
}