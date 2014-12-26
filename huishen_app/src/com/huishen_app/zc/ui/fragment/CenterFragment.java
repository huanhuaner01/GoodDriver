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

	// 滑动页面
	private ViewPager mViewPager;
	// 页面适配器
	private ViewPageAdapter mPageAdapter;
	// 图片视图列表
	private List<View> mListViews;

	// 图片资源
	private List<Integer> imgresourse;

	// 下面点击按钮 的图标列表
	private View[] mImageViews;
	// 列表个数
	private int mViewCount;
	// 当前选中的索引
	private int mCurSel;
	//播放图片的handler
	private ImageHandler handler = new ImageHandler(new WeakReference<CenterFragment>(this));
	// 中心布局
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
		// 获取参数
		imgresourse.add(R.drawable.head_pic_car1);
//		imgresourse.add(R.drawable.head_pic_car2);
		imgresourse.add(R.drawable.head_pic_car4);
		imgresourse.add(R.drawable.head_pic_car5);
		// 初始化参数
		mViewCount = imgresourse.size();
		// 添加多个分页
		for (int i = 0; i < mViewCount; i++) {
			View view = inflater.inflate(R.layout.main_center_viewpage_item,
					null);
			// 初始化图片
			view.setBackgroundResource(imgresourse.get(i));
			
			mListViews.add(view);
		}
	}

	protected void findViewById_Init(View f_view) {

		// 初始化
		maincenter_view = (LinearLayout) f_view
				.findViewById(R.id.main_center_lay);
		city = (TextView) f_view.findViewById(R.id.header_city);
		imgresourse = new ArrayList<Integer>();
		mListViews = new ArrayList<View>();
		
	}

	protected void initView(View f_view) {
		// 初始数据初始化
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
		// 获取大小
		View viewpager = inflater.inflate(R.layout.main_center_viewpage_lay,
				null);

		// 行布局
		int width = father.getWidth() > 100 ? father.getWidth() : 200;
		LinearLayout.LayoutParams r_params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, width * 385/720);
		// 设置 margin
		// r_params.setMargins(col_space, paddingtop, col_space, paddingbuttom);
		maincenter_view.addView(viewpager, r_params);

		// 获取大小
		LinearLayout menu = (LinearLayout) inflater.inflate(
				R.layout.main_center_menu, null);

		// 获取组件的大小 3个按钮 0.7 左右两边0.1 中间 0.2
		LinearLayout.LayoutParams c_params = new LinearLayout.LayoutParams(
				(int) (width * 0.7 / 3), (int) (width * 0.7 / 3));
		c_params.setMargins((int) (width * 0.05), 0, (int) (width * 0.05), 0);

		maincenter_view.addView(menu);
		// 注意先后顺序 先添加菜单在设置大小
		init_imgbt(menu, R.id.main_menu_zjx, c_params);
		init_imgbt(menu, R.id.main_menu_zjl, c_params);
		init_imgbt(menu, R.id.main_menu_zxyy, c_params);
		init_imgbt(menu, R.id.main_menu_xclc, c_params);
		init_imgbt(menu, R.id.main_menu_jfsc, c_params);
		init_imgbt(menu, R.id.main_menu_djt, c_params);

		// 加载数据
		mPageAdapter = new ViewPageAdapter(mListViews);
		mViewPager = (ViewPager) f_view.findViewById(R.id.viewpager);
		mViewPager.setAdapter(mPageAdapter);
		mViewPager.setOnPageChangeListener(this);
		handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
	}

	/**
	 * 初始化imgbt
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
		// 获取viewpager中的布局
		LinearLayout pic_select_view = (LinearLayout) maincenter_view
				.findViewById(R.id.llayout);

		mImageViews = new View[mViewCount];
		// 设置组件大小
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

			// 添加分隔空白
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
	 * 设置某页面
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
	 * 设置下面的小方块
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
	 * imgview click 事件
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
		//用户滑动，请求暂停轮播
		case ViewPager.SCROLL_STATE_DRAGGING:
			handler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
			break;
		//滑动完成，重新请求轮播
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
		// 关闭服务
		super.onDestroy();
	}

	private void closebitmap(View father) {
		if (father == null)
			return;
		try {

			ImageView img = (ImageView) father;

			if (img != null) {
				// 设置可使用
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
		 * 请求更新显示的View。
		 */
		protected static final int MSG_UPDATE_IMAGE	 = 1;
		/**
		 * 请求暂停轮播。
		 */
		protected static final int MSG_KEEP_SILENT	 = 2;
		/**
		 * 请求恢复轮播。
		 */
		protected static final int MSG_BREAK_SILENT	 = 3;
		/**
		 * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
		 * 例如
		 */
		protected static final int MSG_PAGE_CHANGED	 = 4;
		
		//轮播间隔时间
		protected static final long MSG_DELAY = 3000;
		
		//使用弱引用避免Handler泄露
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
				//Activity已经回收，无需再处理UI了
				return ;
			}
			//检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
			if (fragment.handler.hasMessages(MSG_UPDATE_IMAGE)){
				fragment.handler.removeMessages(MSG_UPDATE_IMAGE);
			}
			switch (msg.what) {
			case MSG_UPDATE_IMAGE:
				currentItem++;
				fragment.mViewPager.setCurrentItem(currentItem);
				//准备下次播放
				fragment.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
				break;
			case MSG_KEEP_SILENT:
				//只要不发送消息就暂停了
				break;
			case MSG_BREAK_SILENT:
				fragment.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
				break;
			case MSG_PAGE_CHANGED:
				//记录当前的页号，避免播放的时候页面显示不正确。
				currentItem = msg.arg1;
				break;
			default:
				break;
			} 
		}
	}

}
