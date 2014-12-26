package com.huishen_app.zc.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zh.sortlistTool.CharacterParser;
import com.huishen_app.zh.sortlistTool.ClearEditText;
import com.huishen_app.zh.sortlistTool.PinyinComparator;
import com.huishen_app.zh.sortlistTool.SideBar;
import com.huishen_app.zh.sortlistTool.SideBar.OnTouchingLetterChangedListener;
import com.huishen_app.zh.sortlistTool.SortAdapter;
import com.huishen_app.zh.sortlistTool.SortModel;
import com.huishen_app.zh.sortlistTool.SortSearchAdapter;

public class LocationActivity extends BaseActivity {
	private ListView sortListView,seachlist;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private SortSearchAdapter sadapter ;
	private ClearEditText mClearEditText;
	private TextView title ;
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;
	
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		initViews();
	}
    
	private void initViews() {
		//实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		
		pinyinComparator = new PinyinComparator();
		
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		title = (TextView)findViewById(R.id.header_title);
		title.setText("选择所在地"+"("+readString("city")+")");
		sideBar.setTextView(dialog);
		
		//设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			
			@Override
			public void onTouchingLetterChanged(String s) {
				//该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if(position != -1){
					sortListView.setSelection(position+2);
				}
				
			}
		});
		
		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		seachlist = (ListView) findViewById(R.id.country_search);
		initSearchList();
		initList();
	}

    private void initSearchList(){
    	sadapter = new SortSearchAdapter(this, null);
    	seachlist.setAdapter(sadapter);
    }
    private void initList(){
    	sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//这里要利用adapter.getItem(position)来获取当前position所对应的对象
				if(position>=2){
//				Toast.makeText(getApplication(), ((SortModel)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
				saveString("city", ((SortModel)adapter.getItem(position)).getName());
				finish();
				}
			}
		});
		
		SourceDateList = filledData(getResources().getStringArray(R.array.date));
		
		// 根据a-z进行排序源数据
		Collections.sort(SourceDateList, pinyinComparator);
		//热门城市数据
		ArrayList<HashMap<String,String>> listdata = new ArrayList<HashMap<String,String>>();
		for(int i= 0 ; i<10 ; i++){
			HashMap<String ,String> map = new HashMap<String,String>();
			map.put("gridview_tv", "成都");
			listdata.add(map);
		}
		adapter = new SortAdapter(this, SourceDateList,listdata);
		adapter.setGpsItemListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				TextView city = (TextView)v ;
				saveString("city",city.getText().toString());
				finish();
			}
			
		});
		adapter.setGridViewItemListener(new  OnItemClickListener() 
	  {  
	public void onItemClick(AdapterView<?> arg0,//The AdapterView where the click happened   
	                                  View arg1,//The view within the AdapterView that was clicked  
	                                  int arg2,//The position of the view in the adapter  
	                                  long arg3//The row id of the item that was clicked  
	                                  ) {  
	    //在本例中arg2=arg3  
	    HashMap<String, Object> item=(HashMap<String, Object>) arg0.getItemAtPosition(arg2);  
	    //显示所选Item的ItemText  
//		Toast.makeText(LocationActivity.this,(String)item.get("gridview_tv"), Toast.LENGTH_SHORT).show();
		saveString("city",(String)item.get("gridview_tv"));
		finish();
	}
	  } );
		sortListView.setAdapter(adapter);
		
		
		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
		
		//根据输入框输入值的改变来过滤搜索
		mClearEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
    }
	/**
	 * 为ListView填充数据
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String [] date){
		List<SortModel> mSortList = new ArrayList<SortModel>();
		
		for(int i=0; i<date.length; i++){
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			//汉字转换成拼音
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			// 正则表达式，判断首字母是否是英文字母
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			
			mSortList.add(sortModel);
		}
		return mSortList;
		
	}
	
	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * @param filterStr
	 */
	private void filterData(String filterStr){
		List<SortModel> filterDateList = new ArrayList<SortModel>();
		
		if(TextUtils.isEmpty(filterStr)){
			sortListView.setVisibility(View.VISIBLE);
			seachlist.setVisibility(View.GONE);
			filterDateList = SourceDateList;
			// 根据a-z进行排序
			Collections.sort(filterDateList, pinyinComparator);
			adapter.updateListView(filterDateList);
		}else{
			sortListView.setVisibility(View.GONE);
			seachlist.setVisibility(View.VISIBLE);
			filterDateList.clear();
			for(SortModel sortModel : SourceDateList){
				String name = sortModel.getName();
				if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
					filterDateList.add(sortModel);
				}
			}
			sadapter.updateListView(filterDateList);
		}
	}

	@Override
	protected void onStop() {
		adapter.stopGPS();
		super.onStop();
	}
	/**
	 * 通用返回按钮
	 * 
	 * @param v
	 */
	public void backIntent(View v) {
		finish();
	}

	@Override
	protected void findViewById_Init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 
        if (keyCode == KeyEvent.KEYCODE_BACK
                 && event.getRepeatCount() == 0) {
             finish();
             return true;
         }
         return super.onKeyDown(keyCode, event);
     }
	
}
