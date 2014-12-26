package com.huishen_app.all.mywidget;
import android.widget.GridView;

public class ListGridView extends GridView
{
 public ListGridView(android.content.Context context,
   android.util.AttributeSet attrs)
 {
  super(context, attrs);
 }

 /**
  * ÉèÖÃ²»¹ö¶¯
  */
 @Override
public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
 {
  int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
    MeasureSpec.AT_MOST);
  super.onMeasure(widthMeasureSpec, expandSpec);

 }
}
