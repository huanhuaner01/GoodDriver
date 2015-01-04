package com.huishen_app.zc.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.huishen_app.all.mywidget.NoScrollListView;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.util.TextStyleUtil;
/**
 * �����������
 * @author zhanghuan
 *
 */
public class JLDetailFragment extends BaseFragment {
    private View RootView ;  //�����
    private TextView title ,jlname , price ,drivingYear ,score ,judgenum ,jldes ; //���⣬�������֣������۸񣬽��䣬���ֳɼ�����������
    private NoScrollListView jugeList  , trainList; //�����б���ѵ�����б�
    private ArrayList<Map<String ,Object>> judgeListData ,trainareaListDate ; //��������  ����ѵ��������
    private SimpleAdapter judgeAdapter , trainareaAdapter ;  //�����б�����������ѵ����������
	public JLDetailFragment(BaseActivity father) {
		super(father);
	}

	public JLDetailFragment(BaseActivity father, Object tag) {
		super(father, tag);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		try {
			RootView = inflater.inflate(R.layout.fragment_jldetail, null);
			regsitView();
			initView();
		  } catch (Exception e) {
		}
		return RootView;
	}
   
	/**
	 * ע�����
	 */
	private void regsitView() {
		title = (TextView)RootView.findViewById(R.id.header_title);
		jlname = (TextView)RootView.findViewById(R.id.jl_detail_tv_name) ;
		price = (TextView)RootView.findViewById(R.id.jl_detail_tv_price);
		score = (TextView)RootView.findViewById(R.id.jl_detail_tv_year) ;
		judgenum = (TextView)RootView.findViewById(R.id.jl_detail_judgenum);
		jldes = (TextView)RootView.findViewById(R.id.jl_detail_tv_overleaf);
		jugeList = (NoScrollListView)RootView.findViewById(R.id.judge_list);
		trainList = (NoScrollListView)RootView.findViewById(R.id.trainarea_list);
	}
 
	
	/**
	 * ��ʼ�����ݺ��¼�����
	 */
	private void initView() {
		this.title.setText(this.father.getResources().getString(R.string.jl_detail));
		//���ý������˻�����Ϣ
		setBaseInfo();
		//�����������
		judgeListData = new ArrayList<Map<String ,Object>>();
		String[] jfrom = new String[]{"star","stuname" ,"content"};
		int[] jto = new int[]{R.id.judge_listitem_star ,R.id.judge_listitem_stuname ,R.id.judge_listitem_content};
		for(int i = 0 ; i<4 ; i++){
			HashMap<String , Object> map = new HashMap<String , Object>();
			map.put("star",R.drawable.star_four);
			map.put("stuname","(XXXѧԱ)");
			map.put("content","��ʦ�̵Ĳ���");
			judgeListData.add(map);
		}
		judgeAdapter = new SimpleAdapter(this.father,judgeListData ,R.layout.judge_list_item ,jfrom , jto);
		jugeList.setAdapter(judgeAdapter);
		
		//������ѵ�����б�Ͱ�ť
		trainareaListDate = new ArrayList<Map<String ,Object>>();
		String[] tfrom = new String[]{"area","addr" ,"tel"};
		int[] tto = new int[]{R.id.trainarea_listitem_area ,R.id.trainarea_listitem_addr ,R.id.trainarea_listitem_tel};
		for(int i = 0 ; i<4 ; i++){
			HashMap<String , Object> map = new HashMap<String , Object>();
			map.put("area",TextStyleUtil.getTextAppearanceSpan(father, "����У", "(ۯ��У��)"));
			map.put("addr","��ַ���ɶ���");
			map.put("tel","��ϵ�绰��13888888888");
			trainareaListDate.add(map);
		}
		trainareaAdapter = new SimpleAdapter(this.father,trainareaListDate ,R.layout.trainarea_list_item ,tfrom , tto);
		trainList.setAdapter(trainareaAdapter);
	}
	
	
	//���ý������˻�����Ϣ
	private void setBaseInfo(){
		this.jlname.setText(TextStyleUtil.getTextAppearanceSpan(this.father, "����ʯ","(����У)")) ;
		this.price.append(TextStyleUtil.getTextAppearanceSpan(this.father, "5588"));
		//�������
		jldes.setText(TextStyleUtil.getTextAppearanceSpan(this.father ,"��        �䣺","7��" ,R.color.book_imitate_textcolornew));
		jldes.append("\n") ;
		jldes.append(TextStyleUtil.getTextAppearanceSpan(this.father ,"��        �䣺","10��" ,R.color.book_imitate_textcolornew));
		jldes.append("\n") ;
		jldes.append(TextStyleUtil.getTextAppearanceSpan(this.father ,"����֤�ţ�","��00268" ,R.color.book_imitate_textcolornew));
		jldes.append("\n") ;
		jldes.append(TextStyleUtil.getTextAppearanceSpan(this.father ,"�������ܣ�\n","  �����ǳ���" ,R.color.book_imitate_textcolornew));
	
	}
	

}
