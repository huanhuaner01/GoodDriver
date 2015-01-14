package com.huishen_app.zc.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.ShowMapActivity;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zh.util.TextStyleUtil;

public class TainareaListFragment extends TitleListFragment {

	public TainareaListFragment(BaseActivity father, String titlestr, String url) {
		super(father, titlestr, url);
	}

	public TainareaListFragment(BaseActivity father, Object tag,
			String titlestr, String url) {
		super(father, tag, titlestr, url);
	}

	@Override
	public void setList(String data, ListView list) {
		//������ѵ�����б�Ͱ�ť
		ArrayList<Map<String ,Object>> trainareaListDate = new ArrayList<Map<String ,Object>>();
		String[] tfrom = new String[]{"area","addr" ,"tel"};
		int[] tto = new int[]{R.id.trainarea_listitem_area ,R.id.trainarea_listitem_addr ,R.id.trainarea_listitem_tel};
		for(int i = 0 ; i<4 ; i++){
			HashMap<String , Object> map = new HashMap<String , Object>();
			map.put("area",TextStyleUtil.getTextAppearanceSpan(father, "����У", "(ۯ��У��)"));
			map.put("addr","��ַ���ɶ���");
			map.put("tel","��ϵ�绰��13888888888");
			trainareaListDate.add(map);
		}
		SimpleAdapter trainareaAdapter = new SimpleAdapter(this.father,trainareaListDate ,R.layout.trainarea_list_item ,tfrom , tto);
		list.setAdapter(trainareaAdapter);
		// listViewע��һ��Ԫ�ص���¼�������
		list.setOnItemClickListener(new OnItemClickListener() {
	
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i = new Intent(father ,ShowMapActivity.class);
				father.startActivity(i);
			}
		});
	}

	@Override
	public void setNote(TextView note) {
	   	
	}
}
