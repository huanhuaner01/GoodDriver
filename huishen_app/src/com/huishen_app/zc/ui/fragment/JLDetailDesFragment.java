package com.huishen_app.zc.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.adapter.ImageTvListAdapter;
import com.huishen_app.zc.ui.adapter.JL_Search_Adapter;
import com.huishen_app.zc.ui.base.BaseActivity;

/**
 * 教练图文详情页面
 * @author zhanghuan
 *
 */
public class JLDetailDesFragment extends TitleListFragment {

	public JLDetailDesFragment(BaseActivity father, String titlestr, String url) {
		super(father, titlestr, url);
	}

	public JLDetailDesFragment(BaseActivity father, Object tag,
			String titlestr, String url) {
		super(father, tag, titlestr, url);
	}

	@Override
	public void setList(String data, ListView list) {
		list.setDivider(null);
		list.setDividerHeight(10);
		// 定义资源1
		ArrayList<Map<String, Object>> listview_date = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		listview_date.add(map);
		map = new HashMap<String, Object>();
		map.put("photo","http://preview.quanjing.com/mf025/mf700-00609711.jpg");
		listview_date.add(map);
		map = new HashMap<String, Object>();
		map.put("photo","http://img5.imgtn.bdimg.com/it/u=3793805207,3272353858&fm=21&gp=0.jpg");
		listview_date.add(map);
		map = new HashMap<String, Object>();
		map.put("photo","http://pic1.win4000.com/pic/1/ac/1fb0489694.jpg");
		listview_date.add(map);
		map = new HashMap<String, Object>();
		map.put("photo","http://preview.quanjing.com/mf025/mf700-00609710.jpg");
		listview_date.add(map);
		map = new HashMap<String, Object>();
		map.put("photo","http://preview.quanjing.com/mf025/mf700-00609701.jpg");
		listview_date.add(map);

		ImageTvListAdapter adapter = new ImageTvListAdapter(this.father,listview_date);

		list.setAdapter(adapter);
	}

	@Override
	public void setNote(TextView note) {
		
	}
	

}
