package com.huishen_app.zc.ui.fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.adapter.ViewPageAdapter;
import com.huishen_app.zc.ui.base.BaseActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class CenterFragment extends BaseFragment implements
		android.view.View.OnClickListener, OnPageChangeListener {

	private TextView city ;
	public CenterFragment(BaseActivity father) {
		super(father);
	}

	private LayoutInflater inflater;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View buttom = null;
		try {
			this.inflater = inflater;
			buttom = inflater.inflate(R.layout.main_lay_center, null);

			findViewById_Init(buttom);

			initView(buttom);

			initData(buttom);
		} catch (Exception e) {
		}
		return buttom;
	}

	// ����ҳ��
	private ViewPager mViewPager;
	// ҳ��������
	private ViewPageAdapter mPageAdapter;
	// ͼƬ��ͼ�б�
	private List<View> mListViews;

	// ͼƬ��Դ
	private List<Integer> imgresourse;

	// ��������ť ��ͼ���б�
	private View[] mImageViews;
	// �б����
	private int mViewCount;
	// ��ǰѡ�е�����
	private int mCurSel;
	//����ͼƬ��handler
	private ImageHandler handler = new ImageHandler(new WeakReference<CenterFragment>(this));
	// ���Ĳ���
	private LinearLayout maincenter_view;

	@Override
	public void onResume() {
		if(father.readString("city")!= null &&!father.readString("city").equals("")){
			city.setText(father.readString("city").toString());
		}
		super.onResume();
	}
	
	private void init() {
		imgresourse = new ArrayList<Integer>();
		// ��ȡ����
		imgresourse.add(R.drawable.head_pic_car1);
//		imgresourse.add(R.drawable.head_pic_car2);
		imgresourse.add(R.drawable.head_pic_car4);
		imgresourse.add(R.drawable.head_pic_car5);
		// ��ʼ������
		mViewCount = imgresourse.size();
		// ��Ӷ����ҳ
		for (int i = 0; i < mViewCount; i++) {
			View view = inflater.inflate(R.layout.main_center_viewpage_item,
					null);
			// ��ʼ��ͼƬ
			view.setBackgroundResource(imgresourse.get(i));
			
			mListViews.add(view);
		}
	}

	protected void findViewById_Init(View f_view) {

		// ��ʼ��
		maincenter_view = (LinearLayout) f_view
				.findViewById(R.id.main_center_lay);
		city = (TextView) f_view.findViewById(R.id.header_city);
		imgresourse = new ArrayList<Integer>();
		mListViews = new ArrayList<View>();
		
	}

	protected void initView(View f_view) {
		// ��ʼ���ݳ�ʼ��
		init();
//		city.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View arg0) {
//				Intent i = new Intent(father ,LocationActivity.class);
//				father.startActivity(i);
//			}
//			
//		});
		// ��ȡ��С
		View viewpager = inflater.inflate(R.layout.main_center_viewpage_lay,
				null);

		// �в���
		int width = father.getWidth() > 100 ? father.getWidth() : 200;
		LinearLayout.LayoutParams r_params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, width * 385/720);
		// ���� margin
		// r_params.setMargins(col_space, paddingtop, col_space, paddingbuttom);
		maincenter_view.addView(viewpager, r_params);

		// ��ȡ��С
		LinearLayout menu = (LinearLayout) inflater.inflate(
				R.layout.main_center_menu, null);

		// ��ȡ����Ĵ�С 3����ť 0.7 ��������0.1 �м� 0.2
		LinearLayout.LayoutParams c_params = new LinearLayout.LayoutParams(
				(int) (width * 0.7 / 3), (int) (width * 0.7 / 3));
		c_params.setMargins((int) (width * 0.05), 0, (int) (width * 0.05), 0);

		maincenter_view.addView(menu);
		// ע���Ⱥ�˳�� ����Ӳ˵������ô�С
		init_imgbt(menu, R.id.main_menu_zjx, c_params);
		init_imgbt(menu, R.id.main_menu_zjl, c_params);
		init_imgbt(menu, R.id.main_menu_zxyy, c_params);
		init_imgbt(menu, R.id.main_menu_xclc, c_params);
		init_imgbt(menu, R.id.main_menu_jfsc, c_params);
		init_imgbt(menu, R.id.main_menu_djt, c_params);

		// ��������
		mPageAdapter = new ViewPageAdapter(mListViews);
		mViewPager = (ViewPager) f_view.findViewById(R.id.viewpager);
		mViewPager.setAdapter(mPageAdapter);
		mViewPager.setOnPageChangeListener(this);
		handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
	}

	/**
	 * ��ʼ��imgbt
	 * 
	 * @param menu
	 * @param rs
	 * @param c_params
	 */
	private void init_imgbt(LinearLayout menu, int rs,
			LinearLayout.LayoutParams c_params) {
		ImageButton imgv = (ImageButton) menu.findViewById(rs);
		imgv.setLayoutParams(c_params);
		imgv.setOnClickListener(this);
	}

	protected void initData(View f_view) {
		// ��ȡviewpager�еĲ���
		LinearLayout pic_select_view = (LinearLayout) maincenter_view
				.findViewById(R.id.llayout);

		mImageViews = new View[mViewCount];
		// ���������С
		LinearLayout.LayoutParams paramimg = new LinearLayout.LayoutParams(20,
				20);
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(10, 1);
		for (int i = 0; i < mViewCount; i++) {
			View view = inflater.inflate(
					R.layout.main_center_viewpage_click_img, null);
			view.setEnabled(true);
			view.setOnClickListener(this);
			view.setTag(i);
			pic_select_view.addView(view, paramimg);
			mImageViews[i] = view;

			// ��ӷָ��հ�
			if (i < mViewCount - 1) {
				view = inflater.inflate(
						R.layout.main_center_viewpage_click_img_black, null);
				pic_select_view.addView(view, param);
			}
		}
		mCurSel = 0;
		if (mImageViews.length > 0)
			mImageViews[mCurSel].setEnabled(false);
	}

	/**
	 * ����ĳҳ��
	 * 
	 * @param pos
	 */
	private void setCurView(int pos) {
		if (pos < 0 || pos >= mViewCount) {
			return;
		}
		mViewPager.setCurrentItem(pos);
	}

	/**
	 * ���������С����
	 * 
	 * @param index
	 */
	private void setCurPoint(int index) {
		if (index < 0 || index > mViewCount  || mCurSel == index) {
			return;
		}
		mImageViews[mCurSel].setEnabled(true);
		mImageViews[index].setEnabled(false);

		mCurSel = index;
	}

	/**
	 * imgview click �¼�
	 */
	@Override
	public void onClick(View v) {
		if (v.getTag() != null) {
			int pos = (Integer) v.getTag();
			setCurView(pos);
			setCurPoint(pos);
		} else {
			father.switchcenter(v.getId());
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		switch (arg0) {
		//�û�������������ͣ�ֲ�
		case ViewPager.SCROLL_STATE_DRAGGING:
			handler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
			break;
		//������ɣ����������ֲ�
		case ViewPager.SCROLL_STATE_IDLE:
			handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
			break;
		default:
			break;
		}
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		handler.sendMessage(Message.obtain(handler, 
				ImageHandler.MSG_PAGE_CHANGED, position, 0));
		position %= mViewCount;
		if (position<0){
			position = mViewCount+position;
		}
		setCurPoint(position);
		
	}

	@Override
	public void onDestroy() {
		try {
			for (int i = 0; i < mListViews.size(); ++i) {
				View view = mListViews.get(i);
				closebitmap(view);
			}
		} catch (Exception e) {
		}
		// �رշ���
		super.onDestroy();
	}

	private void closebitmap(View father) {
		if (father == null)
			return;
		try {

			ImageView img = (ImageView) father;

			if (img != null) {
				// ���ÿ�ʹ��
				img.setDrawingCacheEnabled(true);
				Bitmap obmp = Bitmap.createBitmap(img.getDrawingCache());
				img.setDrawingCacheEnabled(false);

				if (obmp != null) {
					obmp.recycle();
				}
			}
		} catch (Exception e) {
		}
	}
	
	private static class ImageHandler extends Handler{
		
		/**
		 * ���������ʾ��View��
		 */
		protected static final int MSG_UPDATE_IMAGE	 = 1;
		/**
		 * ������ͣ�ֲ���
		 */
		protected static final int MSG_KEEP_SILENT	 = 2;
		/**
		 * ����ָ��ֲ���
		 */
		protected static final int MSG_BREAK_SILENT	 = 3;
		/**
		 * ��¼���µ�ҳ�ţ����û��ֶ�����ʱ��Ҫ��¼��ҳ�ţ������ʹ�ֲ���ҳ�����
		 * ����
		 */
		protected static final int MSG_PAGE_CHANGED	 = 4;
		
		//�ֲ����ʱ��
		protected static final long MSG_DELAY = 3000;
		
		//ʹ�������ñ���Handlerй¶
		private WeakReference<CenterFragment> weakReference;
		private int currentItem = 0;
		
		protected ImageHandler(WeakReference<CenterFragment> wk){
			weakReference = wk;
		}
		
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
//			Log.d(LOG_TAG, "receive message " + msg.what);
			CenterFragment fragment = weakReference.get();
			if (fragment==null){
				//Activity�Ѿ����գ������ٴ���UI��
				return ;
			}
			//�����Ϣ���в��Ƴ�δ���͵���Ϣ������Ҫ�Ǳ����ڸ��ӻ�������Ϣ�����ظ������⡣
			if (fragment.handler.hasMessages(MSG_UPDATE_IMAGE)){
				fragment.handler.removeMessages(MSG_UPDATE_IMAGE);
			}
			switch (msg.what) {
			case MSG_UPDATE_IMAGE:
				currentItem++;
				fragment.mViewPager.setCurrentItem(currentItem);
				//׼���´β���
				fragment.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
				break;
			case MSG_KEEP_SILENT:
				//ֻҪ��������Ϣ����ͣ��
				break;
			case MSG_BREAK_SILENT:
				fragment.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
				break;
			case MSG_PAGE_CHANGED:
				//��¼��ǰ��ҳ�ţ����ⲥ�ŵ�ʱ��ҳ����ʾ����ȷ��
				currentItem = msg.arg1;
				break;
			default:
				break;
			} 
		}
	}

}
