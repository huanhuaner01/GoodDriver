package com.huishen_app.zc.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.util.AndroidUtil;
import com.huishen_app.zc.util.TextStyleUtil;

public class JXDetailFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

	public JXDetailFragment(BaseActivity father) {
		super(father);
	}
	
		private static final int REFRESH_COMPLETE = 0X110;
		private SwipeRefreshLayout mSwipeLayout;
		private TextView title , overleaf ,headerdes ,price ;
		private ImageView jximg,infoimg ,proimg ;
		private Button btn ;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_jxdetail,
					container, false);
			registView(rootView);
			initView();
			return rootView;
		}
		/**
		 * ���ע��
		 * @param view
		 */
		private void registView(View rootView){
			mSwipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.id_swipe_ly);
			title = (TextView)rootView.findViewById(R.id.header_title);
			overleaf = (TextView)rootView.findViewById(R.id.wegroup_detail_tv_overleaf) ;
			headerdes = (TextView)rootView.findViewById(R.id.wegroup_item_tv_imgdes) ;
			price = (TextView)rootView.findViewById(R.id.wegroup_detail_price) ;
			jximg = (ImageView)rootView.findViewById(R.id.wegroup_detail_img_header) ;
			infoimg = (ImageView)rootView.findViewById(R.id.wegroup_detail_img_des) ;
			proimg = (ImageView)rootView.findViewById(R.id.wegroup_detail_img_procedure) ;
			btn = (Button)rootView.findViewById(R.id.wegroup_detail_submmit) ;
		}
		/**
		 * ��ʼ�����
		 */
		private void initView(){
			title.setText("��У����");
			
			mSwipeLayout.setOnRefreshListener(this);
			mSwipeLayout.setColorScheme(android.R.color.holo_green_dark, android.R.color.holo_green_light,
					android.R.color.holo_orange_light, android.R.color.holo_red_light);
			//�������ø���ͼƬ�Ĵ�С
			//�޸ļ�У���ͼƬ�Ĵ�С
			if(AndroidUtil.getImageScaleParams(this.father, R.drawable.wegroup_info )!= null){
			this.infoimg.setLayoutParams(AndroidUtil.getImageScaleParams(this.father, R.drawable.wegroup_info));
			}
			//�޸�ѧ������ͼƬ�Ĵ�С
			if(AndroidUtil.getImageScaleParams(this.father, R.drawable.wegroup_info )!= null){
			this.proimg.setLayoutParams(AndroidUtil.getImageScaleParams(this.father, R.drawable.wegroup_pro));
			}
			//�޸ļ�УͼƬ�Ĵ�С
			if(AndroidUtil.getImageScaleParams(this.father, R.drawable.wegroup_info )!= null){
			this.jximg.setBackgroundResource( R.drawable.jx_detail);
			this.jximg.setLayoutParams(AndroidUtil.getImageScaleParams(this.father, R.drawable.jx_detail));
			}
			//���ü�У����
			this.headerdes.setText("����У");
			//���ü۸���Ϣ
			TextStyleUtil.setTextAppearanceSpan(this.father,this.price ,"3999 ");
			this.price.append("��");
			
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
			/**
			 * 1����������18-65��δѧ����ʿ��
               2����ѧ�������ݴɣ���ɵȿ��Գ��ͣ�
               3������ѧʱ��ÿ������Լ�����Σ�ÿ����������ѧʱ��45����/ ѧʱ�����ر����ѣ���Ҫ��ǰһ��绰ԤԼ��
               4��������ʽ��ÿ���������һ��������
               5���ػݼ۸��г��ۣ�4880Ԫ���ּ�4500Ԫ��һ�Ѱ��ɣ���;���������ѣ��N����ѧ�����ṩȫ�̵�����
               6��ȫ��һ���ƣ�������������ѡ�ICָ�ƿ��ѡ����۽̲ķѡ�����ѧϰ�����ۿ��ԡ�������ѵ�����ؿ��ԡ���·��ѵ��·������ȫ�������ԡ�ʵϰ�ơ����ա�ÿ����Ŀ��������һ����ѵĲ������᣻
               7����·��ѵ����400���
               8�����ð�������ѵ��/�鱾��/400����·ѵ/���Խ��ͷ�
               9�����ѧԱ����Ѱ����ס֤��ѧ��֤��
               10����֤ʱ�䣺����������60�졣����ѧԱƽ��3-4���£����ѧԱƽ��4-5���£�ѧԱ����ʱ��ֱ��Ӱ��ѧԱ��֤ʱ�䣻
               11��������ַ�����μ�У�岨���أ����μ�У����ӻ��أ�
               12�����ͷ���ԤԼ����������ͣ�������ѽ��ͣ�
               13�������ύ��ѧԱ����ͨ��ͬ�ǿ�ݼ��ͣ�֧�ֵ������N����ѧ����֧��������š�
			 */
			//���ñ�����֪
			for(int i = 0 ;i<13 ; i++){
			String des = "<font color='"+this.father.getResources().getColor(R.color.wegroup_item_buttomtv_color)
					+"'>"+key[i]+"</font>"+value[i];
			 this.overleaf.append(Html.fromHtml(des));
			}
			btn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					weGroupSubmmit(arg0);
					
				}
				
			});
			
		}
		
		/**
		 * ȷ�϶�����ť��Ӧ�¼�
		 * 
		 * @param v
		 */
		public void weGroupSubmmit(View v) {
            //����ϵͳ�Ĳ��ŷ���ʵ�ֵ绰������
            //��װһ������绰��intent�����ҽ��绰�����װ��һ��Uri������
			//��1��ACTION_CALL��ֱ�Ӳ��ţ�
			//��2��ACTION_DIAL�����ò��ų����ֹ�������
            Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:13558657902"));
            this.father.startActivity(intent);//�ڲ���
		}
		
		
		
		
		/*************************************************************************/
		
		@Override
		public void onRefresh()
		{
			// Log.e("xxx", Thread.currentThread().getName());
			// UI Thread

			mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);

		}
		
		private Handler mHandler = new Handler()
		{
			@Override
			public void handleMessage(android.os.Message msg)
			{
				switch (msg.what)
				{
				case REFRESH_COMPLETE:
//					adapter.notifyDataSetChanged();
					mSwipeLayout.setRefreshing(false);
					break;

				}
			};
		};
	}
