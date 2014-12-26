package com.huishen_app.all.calendar;

import java.util.ArrayList;
import java.util.List;

import com.huishen_app.zc.ui.R;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

public class PoupSimpleList {
	private List<String>data=new ArrayList<String>();
	private PopupWindow popupWindow;
	private ListView yearView;
	private View view;//骞翠唤鍒楄〃瀹瑰櫒
	private String chooseItem;
	private LayoutInflater layoutInflater;
	public static int POUP_COLOS=1;
	public static int POUP_SHOW=2;
	private int poupstate=0;
	private OnPoupItemClickListener listener = null;
	
	public interface OnPoupItemClickListener {
    	public void onPoupItemClick();
    }
	
	public void setOnPoupItemClickListener(OnPoupItemClickListener p){
		this.listener=p;
	}
	/**
	 * @param width 鏄剧ず鐨勫搴�	 * @param height 鏄剧ず鐨勯珮搴�	 * */
	public PoupSimpleList(Context context,int width,int height){
		layoutInflater=LayoutInflater.from(context);
		view =layoutInflater.inflate(R.layout.calendar_simple_year_listview, null);
		yearView=(ListView)view.findViewById(R.id.yearview);
		yearView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				chooseItem=data.get(arg2);
				poupstate=1;
				popupWindow.dismiss();
				listener.onPoupItemClick();
			}
		});
		popupWindow=new PopupWindow(view,width,height);
	}
	/**
	 * 鑾峰彇褰撳墠绐楀彛鐘舵�
	 * */
	public int getPoupstate(){
		return this.poupstate;
	}
	/**
	 * 璁剧疆鏁版嵁
	 * @param data 鏁版嵁鍒楄〃
	 * */
	public void setData(List<String> data){
		Simple_Adapter adapter=new Simple_Adapter(yearView.getContext());
		adapter.setData(data);
		yearView.setAdapter(adapter);
		this.data=data;
	}
	/**
	 * 璁剧疆榛樿閫夋嫨鐨勯」
	 * @param position 閫夋嫨椤圭储寮�	 * */
	public void setSelection(int position){
		yearView.setSelection(position);
	}
	/**
	 * 鑾峰彇褰撳墠閫夋嫨椤�	 * */
	public String getSelectionItem(){
		return chooseItem;
	}
	/**
	 * 鏄剧ず寮瑰嚭鍒楄〃
	 * @param view 鍦╲iew鐨勫乏涓嬭鏄剧ず
	 * @param xoff 鏄剧ず鐨剎鍧愭爣
	 * @param yoff 鏄剧ず鐨剏鍧愭爣
	 * */
	public void show(View view,int xoff,int yoff){
		poupstate=2;
		// 浣垮叾鑱氶泦
		popupWindow.setFocusable(true);
		// 璁剧疆鍏佽鍦ㄥ鐐瑰嚮娑堝け
		popupWindow.setOutsideTouchable(true);
		// 杩欎釜鏄负浜嗙偣鍑烩�杩斿洖Back鈥濅篃鑳戒娇鍏舵秷澶憋紝骞朵笖骞朵笉浼氬奖鍝嶄綘鐨勮儗鏅�		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 鏄剧ず鐨勪綅缃负:灞忓箷鐨勫搴︾殑涓�崐-PopupWindow鐨勯珮搴︾殑涓�崐
		popupWindow.showAsDropDown(view, xoff, xoff);
	}
}
