package com.huishen_app.zc.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.huishen_app.all.mywidget.NoScrollListView;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.ShowMapActivity;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zh.util.TextStyleUtil;
/**
 * �����������
 * @author zhanghuan
 *
 */
public class JLDetailFragment extends BaseFragment implements View.OnClickListener{
    private View RootView ;  //�����
    private String TAG = "JLDetailFragment" ;
    /**���⣬�������֣������۸񣬽��䣬���ֳɼ����������� ,������飬ѧ������*/
    private TextView title ,jlname , price ,drivingYear ,score ,judgenum ,jldes ,overleaf ; 
    private NoScrollListView jugeList  , trainList; //�����б���ѵ�����б�
    /** ����ͼ������鿴���� �� ѵ�����ز鿴���� �����۲鿴���� */
    private LinearLayout desMore ,trainareaMore ,judgeMore ; 
    
    /** ����������ť����Ҫ��ѯ��ť  */
    private Button signUp , consult ;
    
    private ArrayList<Map<String ,Object>> judgeListData ,trainareaListDate ; //��������  ����ѵ��������
    private SimpleAdapter judgeAdapter , trainareaAdapter ;  //�����б�����������ѵ����������
    
    /** ��ʾlistFragment(������Ϊ����fragment) */
    private ListFragment fragment ; 
    
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
		overleaf = (TextView)RootView.findViewById(R.id.wegroup_detail_tv_overleaf);
		jugeList = (NoScrollListView)RootView.findViewById(R.id.judge_list);
		trainList = (NoScrollListView)RootView.findViewById(R.id.trainarea_list);
		desMore = (LinearLayout)RootView.findViewById(R.id.jl_detail_des_more) ;
		trainareaMore = (LinearLayout)RootView.findViewById(R.id.trainarea_seemore);
		judgeMore = (LinearLayout)RootView.findViewById(R.id.judge_seemore) ;
		signUp = (Button)RootView.findViewById(R.id.jl_detail_btn_signup);
		consult = (Button)RootView.findViewById(R.id.jl_detail_btn_consult) ;
		
	}
 
	
	/**
	 * ��ʼ�����ݺ��¼�����
	 */
	private void initView() {
		this.title.setText(this.father.getResources().getString(R.string.jl_detail));
		//���ý������˻�����Ϣ
		setJlBaseInfo();
		//���ý�����֪
		setOverleaf() ;
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
		// listViewע��һ��Ԫ�ص���¼�������
		trainList.setOnItemClickListener(new OnItemClickListener() {
	
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i = new Intent(father ,ShowMapActivity.class);
				father.startActivity(i);
			}
		});
		//������������Ҫ��ѯ�����¼�
		signUp.setOnClickListener(this);
		consult.setOnClickListener(this);
		//���ý���ͼ������鿴��������¼�
		desMore.setOnClickListener(this);
		//����ѵ�����ز鿴��������¼�
		trainareaMore.setOnClickListener(this);
		//�������۲鿴��������¼�
		judgeMore.setOnClickListener(this) ;
		
	}
	
	
	//���ý������˻�����Ϣ
	private void setJlBaseInfo(){
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
	
	/**
	 * ���ý�����֪
	 */
	private void setOverleaf(){
		String[] key = new String[]{"1����������: ","2����ѧ������","3������ѧʱ��","4��������ʽ��","5���ػݼ۸�",
				"6��ȫ��һ���ƣ�","7����·��","8�����ð�����","9�����ѧԱ��","10����֤ʱ�䣺","11��������ַ��","12�����ͷ���","13�������ύ��"};
		String[] value = new String[]{"18-65��δѧ����ʿ��<br />",
				     "�ݴɣ���ɵȿ��Գ��ͣ�<br />",
				     "ÿ������Լ�����Σ�ÿ����������ѧʱ��45����/ ѧʱ�����ر����ѣ���Ҫ��ǰһ��绰ԤԼ��<br />",
				     "ÿ���������һ��������<br />",
				     "�г��ۣ�4880Ԫ���ּ�4500Ԫ��һ�Ѱ��ɣ���;���������ѣ��N����ѧ�����ṩȫ�̵�����<br />",
				     "������������ѡ�ICָ�ƿ��ѡ����۽̲ķѡ�����ѧϰ�����ۿ��ԡ�������ѵ�����ؿ��ԡ���·��ѵ��·������ȫ�������ԡ�ʵϰ�ơ����ա�ÿ����Ŀ��������һ����ѵĲ������᣻<br />",
				     "ѵ����400���<br />",
				     "��ѵ��/�鱾��/400����·ѵ/���Խ��ͷ�;<br />",
				     "��Ѱ����ס֤��ѧ��֤��<br />",
				     "����������60�졣����ѧԱƽ��3-4���£����ѧԱƽ��4-5���£�ѧԱ����ʱ��ֱ��Ӱ��ѧԱ��֤ʱ�䣻<br />",
				     "������ַ�����μ�У�岨���أ����μ�У����ӻ��أ�<br />",
				     "ԤԼ����������ͣ�������ѽ��ͣ�<br />",
				     "ѧԱ����ͨ��ͬ�ǿ�ݼ��ͣ�֧�ֵ������N����ѧ����֧��������š�<br />"};

		//���ñ�����֪
		for(int i = 0 ;i<13 ; i++){
		 String des = "<font color='"+this.father.getResources().getColor(R.color.wegroup_item_buttomtv_color)
		 		+"'>"+key[i]+"</font>"+value[i];
		 this.overleaf.append(Html.fromHtml(des));
		}
	}

	@Override
	public void onClick(View v) {

        FragmentManager fm = getFragmentManager();  
        FragmentTransaction tx = fm.beginTransaction();
		switch(v.getId()){
		//�л�������ͼ������ҳ��
		case R.id.jl_detail_des_more: 
			fragment = new ListFragment(this.father ,"������" ,"",0 ,new ListFragment.ListFragmentAdapter(){

				@Override
				public void setDes(String result, TextView tv) {
					//�������
					tv.setText(TextStyleUtil.getTextAppearanceSpan(father ,"��        �䣺","7��" ,R.color.book_imitate_textcolornew));
					tv.append("\n") ;
					tv.append(TextStyleUtil.getTextAppearanceSpan(father ,"��        �䣺","10��" ,R.color.book_imitate_textcolornew));
					tv.append("\n") ;
					tv.append(TextStyleUtil.getTextAppearanceSpan(father ,"����֤�ţ�","��00268" ,R.color.book_imitate_textcolornew));
					tv.append("\n") ;
					tv.append(TextStyleUtil.getTextAppearanceSpan(father ,"�������ܣ�\n","  �����ǳ���" ,R.color.book_imitate_textcolornew));
				} 

				@Override
				public void setList(String result, NoScrollListView list) {
					
				}
				
			});
	        tx.hide(this);   
	        tx.add(R.id.container,fragment , "jldes");
	        tx.addToBackStack(null);  
	          
			break ;
			//�л���ѵ�������б�ҳ��
		case R.id.trainarea_seemore:
			fragment = new ListFragment(this.father ,"ѵ������" ,"",0 ,new ListFragment.ListFragmentAdapter(){

				@Override
				public void setDes(String result, TextView tv) {
					tv.setVisibility(View.GONE);
				} 

				@Override
				public void setList(String result, NoScrollListView list) {
					String[] tfrom = new String[]{"area","addr" ,"tel"};
					int[] tto = new int[]{R.id.trainarea_listitem_area ,R.id.trainarea_listitem_addr ,R.id.trainarea_listitem_tel};
				
					trainareaAdapter = new SimpleAdapter(father,trainareaListDate ,R.layout.trainarea_list_item ,tfrom , tto);
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
				
			});
	        tx.hide(this);   
	        tx.add(R.id.container,fragment , "jltrainarea");
	        tx.addToBackStack(null); 
			break ;
			//�л��������б�ҳ��
		case R.id.judge_seemore:
			fragment = new ListFragment(this.father ,"ѵ������" ,"",0,new ListFragment.ListFragmentAdapter(){

				@Override
				public void setDes(String result, TextView tv) {
					tv.setVisibility(View.GONE);
				} 

				@Override
				public void setList(String result, NoScrollListView list) {
					list.setAdapter(judgeAdapter);
				}
				
			});
	        tx.hide(this);   
	        tx.add(R.id.container,fragment , "jltrainarea");
	        tx.addToBackStack(null);
			break ;
			//��������ҳ��
		case R.id.jl_detail_btn_signup:
			break ;
			//��Ҫ��ѯ����
		case R.id.jl_detail_btn_consult:
			break ;
		}
		tx.commit();
	}

}
