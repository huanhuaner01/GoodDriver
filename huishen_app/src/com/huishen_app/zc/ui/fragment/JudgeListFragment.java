package com.huishen_app.zc.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.widget.ListView;
import android.widget.TextView;

import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.adapter.JudgeListAdapter;
import com.huishen_app.zc.ui.adapter.RattingBarListAdapter;
import com.huishen_app.zc.ui.base.BaseActivity;

public class JudgeListFragment extends TitleListFragment {

	public JudgeListFragment(BaseActivity father, String titlestr, String url) {
		super(father, titlestr, url);
	}

	public JudgeListFragment(BaseActivity father, Object tag, String titlestr,
			String url) {
		super(father, tag, titlestr, url);
		
	}

	@Override
	public void setList(String data, ListView list) {
		//评价添加数据
		ArrayList<Map<String ,Object>> judgeListData = new ArrayList<Map<String ,Object>>();
		HashMap<String , Object> map = new HashMap<String , Object>();
		map.put("name","王教练");
		map.put("rating",4.6);
		map.put("score",4.6);
		judgeListData.add(map);
		for(int i = 1 ; i<5 ; i++){
			map = new HashMap<String , Object>();
			map.put("rating",2.7);
			map.put("stuname","(XXX学员)");
			map.put("content","老师教的不错");
			judgeListData.add(map);
		}
		JudgeListAdapter judgeAdapter = new JudgeListAdapter(this.father,judgeListData);
		list.setAdapter(judgeAdapter);
	}

	@Override
	public void setNote(TextView note) {
		
	}

}
