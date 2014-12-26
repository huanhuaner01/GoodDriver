package com.huishen_app.zc.util;

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
	//设置价格外观
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
	
	//设置价格外观
	public static SpannableString setTextAppearanceSpan(Context context,String price) { 
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
	
	//设置价格外观
	public static SpannableString setTextAppearanceSpan(Context context,String price ,float f) { 
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
	
	//设置文字删除线
	public static void addStrikeSpan(TextView tv ,String str) {  
	    SpannableString spanString = new SpannableString(str);  
	    StrikethroughSpan span = new StrikethroughSpan();  
	    spanString.setSpan(span, 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
	    tv.append(spanString);  
	}
}
