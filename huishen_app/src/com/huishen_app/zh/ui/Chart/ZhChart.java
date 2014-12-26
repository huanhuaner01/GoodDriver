package com.huishen_app.zh.ui.Chart;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

@SuppressLint("DrawAllocation")
public class ZhChart extends View {
	// 原点坐标
	private int Xpoint;
	private int Ypoint;
	
	//XY轴的单位长度
	private int Xscale ;
	private int Yscale ;
	//图例个数
	private int legendSum ;
	private int legendlinenum = 4;
	private int legendlines ;
	//基础属性
	private ChartBean chart = new ChartBean();
	private ArrayList<ChartLineBean> lines = new ArrayList<ChartLineBean>();
	
	public ZhChart(Context context,ChartBean chart ,ArrayList<ChartLineBean> lines) {
		super(context);
		this.chart = chart ;
		if(chart.getMargin()==40){
			chart.setMargin(this.getHeight()/15);
		}
		this.lines = lines ;
	}
	
	public void upDateDraw(ChartBean chart ,ArrayList<ChartLineBean> lines){
		this.chart = chart ;
		this.lines = lines ;
		if(chart.getMargin()==40){
			chart.setMargin(this.getHeight()/15);
		}
		this.invalidate();
	}
	public ZhChart(Context context) {
		super(context);
		ChartLineBean line = new ChartLineBean();
		if(chart.getMargin()==40){
			chart.setMargin(this.getHeight()/15);
		}
		lines.add(line);
	}

	// 初始化数据值
	public void init() {
		legendSum = lines.size() ;
		Xpoint =3*this.chart.getMargin()/2;
		//图例行数
		legendlines =(int)( Math.ceil(((double)legendSum)/legendlinenum)) ;
		Ypoint = this.getHeight() - (legendlines+1)*this.chart.getMargin();
		Xscale = (this.getWidth() -  this.chart.getMargin()-Xpoint) / (this.chart.getXLabel().length - 1);
		Yscale = (this.Ypoint-this.Xpoint)/ (this.chart.getYLabel().length - 1);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(this.chart.getBgColor());
		Paint p1 = new Paint();
		p1.setStyle(Paint.Style.STROKE);
		p1.setAntiAlias(true);
		p1.setColor(this.chart.getXYpointColor());
		p1.setStrokeWidth(2);
		init();
		this.drawXLine(canvas, p1);
		this.drawYLine(canvas, p1);
		this.drawTable(canvas);
		this.drawData(canvas);
		this.drawLegend(canvas) ;
	}
	// 画表格
	private void drawTable(Canvas canvas) {
		Paint paint = new Paint();
		Paint tpaint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(this.chart.getTableColor());
		
		tpaint.setStyle(Paint.Style.FILL);
		tpaint.setTextAlign(Paint.Align.CENTER);
		tpaint.setColor(this.chart.getXYLabelColor());
		tpaint.setTextSize(this.chart.getMargin()/2);
		Path path = new Path();
		PathEffect effects = new DashPathEffect(new float[] { 5, 5, 5, 5 }, 1);
		paint.setPathEffect(effects);
		// 纵向线
		for (int i = 1; i * Xscale <= (this.getWidth() - this.chart.getMargin()); i++) {
			int startX = Xpoint + i * Xscale;
			int startY = Ypoint;
			int stopY = Ypoint - (this.chart.getYLabel().length - 1) * Yscale;
			path.moveTo(startX, startY);
			path.lineTo(startX, stopY);
			canvas.drawPath(path, paint);
			try{
			canvas.drawText(this.chart.getXLabel()[i], startX - this.chart.getMargin() / 4,
					Ypoint+this.chart.getMargin()/2,tpaint);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		 //原点值
		canvas.drawText(this.chart.getXLabel()[0], Xpoint - this.chart.getMargin() / 4,
				Ypoint+this.chart.getMargin()/2,tpaint);
	
		tpaint.setTextAlign(Paint.Align.LEFT);
		canvas.drawText(this.chart.getYLabel()[0]+"",this.chart.getMargin()*2/3,
				Ypoint+ this.chart.getMargin() / 4,tpaint);
		// 横向线
		for (int i = 1; (Ypoint - i * Yscale) >= Xpoint; i++) {
			int startX = Xpoint;
			int startY = Ypoint - i * Yscale;
			int stopX = Xpoint + (this.chart.getXLabel().length - 1) * Xscale;
			path.moveTo(startX, startY);
			path.lineTo(stopX, startY);
			canvas.drawPath(path, paint);
			canvas.drawText(this.chart.getYLabel()[i]+"",this.chart.getMargin()*2/3, startY
					+ this.chart.getMargin() / 4, tpaint);
		}
		
		
		//画标题
		 tpaint.setTextSize(this.chart.getMargin()*2/3);
		 tpaint.setColor(this.chart.getTitleColor());
		 canvas.drawText(this.chart.getTitle(),Xpoint-this.chart.getMargin()/2,Xpoint-this.chart.getMargin()/2, tpaint);
	}

	// 画横纵轴
	private void drawXLine(Canvas canvas, Paint p) {
		canvas.drawLine(Xpoint, Ypoint, Xpoint, Xpoint, p);
//		canvas.drawLine(Xpoint, this.Margin, Xpoint - Xpoint / 3, this.Margin
//				+ this.Margin / 3, p);
//		canvas.drawLine(Xpoint, this.Margin, Xpoint + Xpoint / 3, this.Margin
//				+ this.Margin / 3, p);
	}

	private void drawYLine(Canvas canvas, Paint p) {
		canvas.drawLine(Xpoint, Ypoint, this.getWidth() - this.chart.getMargin(), Ypoint,
				p);
//		canvas.drawLine(this.getWidth() - this.Margin, Ypoint, this.getWidth()
//				- this.Margin - this.Margin / 3, Ypoint - this.Margin / 3, p);
//		canvas.drawLine(this.getWidth() - this.Margin, Ypoint, this.getWidth()
//				- this.Margin - this.Margin / 3, Ypoint + this.Margin / 3, p);
	}

	// 画数据
	private void drawData(Canvas canvas) {
		Paint p = new Paint();
		p.setAntiAlias(true);
		for(ChartLineBean line : lines){
		p.setColor(line.getLineColor());
		// 纵向线
		for (int i = 1; i * Xscale <= (this.getWidth() - this.chart.getMargin()); i++) {
			int startX = Xpoint + i * Xscale;
//			//值为-1表示此处没有数据，为空心圆，将数据置为0
//			if(line.getData()[i]==0){
//				p.setStyle(Paint.Style.STROKE);
//			}
			canvas.drawCircle(startX, calY(line.getData()[i]), 4, p);
//			p.setStyle(Style.FILL);
			canvas.drawLine(Xpoint+(i-1)*Xscale, calY(line.getData()[i-1]), startX, calY(line.getData()[i]), p);
			
		}
		}
	}

	//画图例
	private void drawLegend(Canvas canvas){
		Paint p = new Paint();
		p.setAntiAlias(true);
	    p.setStyle(Style.FILL);//实心矩形框  
	   for(int i = 0 ;i<lines.size() ; i++){
	    p.setColor(lines.get(i).getLineColor());  
	    int size = this.chart.getMargin()*3/5;
	    p.setTextSize(size);
	    //图例所在的行列
	    int h = (int)( Math.ceil(((double)(i+1))/legendlinenum)) ;
	    int l = (i+1)%legendlinenum ;
	    if(l == 0){l = legendlinenum;}
	    int xtemp = (this.getWidth() - this.chart.getMargin() )/legendlinenum;
	
	    int x0  = this.chart.getMargin() + xtemp*(l-1);
	    int y0 = Ypoint+this.chart.getMargin()*h;
	    int x = x0+size ;
	    int y = y0+size ;
	    Log.i("weqwrqw", "("+x0+","+y0+","+x+","+y+")");
	    canvas.drawRect(new RectF(x0,y0,x,y), p);
	    canvas.drawText(lines.get(i).getLinename(),x+5,y, p);
	    }
	}
	
	/**
	 * 
	 * @param y  
	 * @return
	 */
	private int calY(int y){ 
		int y0 = 0 ;
		int y1 = 0 ;
		if(y>this.chart.getYLabel()[this.chart.getYLabel().length - 1]){
			y = this.chart.getYLabel()[this.chart.getYLabel().length - 1];
		}
	//	Log.i("zzzz", "y:"+y);
		try{
			y0 =this.chart.getYLabel()[0];
	//		Log.i("zzzz", "y0"+y0);
			y1 = this.chart.getYLabel()[1];
	//		Log.i("zzzz","y1"+y1);
		}catch(Exception e){
	//		Log.i("zzzz", "string changed is err");
			return 0;
		}
		try{
	//		Log.i("zzzz", "返回数据"+(Ypoint-(y-y0)*Yscale/(y1-y0)) );
			return Ypoint-((y-y0)*Yscale/(y1-y0)) ;
		}catch(Exception e){
		//	Log.i("zzzz", "return is err");
			return 0;
		}
	}

}
