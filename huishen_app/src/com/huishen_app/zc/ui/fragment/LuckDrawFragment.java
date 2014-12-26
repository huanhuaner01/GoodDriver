package com.huishen_app.zc.ui.fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.huishen_app.all.alarmservice.HanderListObject;
import com.huishen_app.zc.httpclient.HuishenHttpClient;
import com.huishen_app.zc.operate_thread.GetHttpResultThread;
import com.huishen_app.zc.operate_thread.LoginThread;
import com.huishen_app.zc.ui.Main_fragment_ui;
import com.huishen_app.zc.ui.PrizeListActivity;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.ui.dialog.LoadingDialog_ui;
import com.huishen_app.zc.ui.dialog.MessageDialog_ui;
import com.huishen_app.zc.util.AndroidUtil;
import com.huishen_app.zc.util.MyUtil;
/**
 * ���ֳ齱
 * @author zhanghuan
 *
 */
public class LuckDrawFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
	private SwipeRefreshLayout mSwipeLayout;
	private static final String LOG_TAG = "LuckDrawFragment";
	   private View headerimg ,lucktitleimg ,topimg ,luckcenterbg ,rolev ,listv;
	   private TextView title ,scoretv , roletv ,note;
	   private View RootView ;
	   private Button back ; //���ؼ�
	   private LinearLayout luckly ,luck_listbg; //�齱����
	   private ListView lucklist ; //�н�����
	   private int score = 300 ;
	   private MessageDialog_ui dialog ; //�н���Ϣ������
	   private int isplay = 0; //�Ƿ������
	   private int lotteryInfoId = 0 ;
	   /** ���ضԻ��� */
		private LoadingDialog_ui loading;
//	   
		/**
		 * ����ת���е�Item���id�б���Ҫע�������Щid��˳ʱ�뷽�����У���12369874��
		 */
		private int[] lotteryItemIds = new int[] { 
				R.id.lottery_item_1, R.id.lottery_item_2, R.id.lottery_item_3, 
														R.id.lottery_item_6,
														R.id.lottery_item_9,
									R.id.lottery_item_8,
				R.id.lottery_item_7,
				R.id.lottery_item_4};
		
		private ImageView lotteryBtn;
	   
	public LuckDrawFragment(BaseActivity father) {
		super(father);
	}

	public LuckDrawFragment(BaseActivity father, Object tag) {
		super(father, tag);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		try {
			RootView = inflater.inflate(R.layout.fragment_luckdraw, null);
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
		this.mSwipeLayout = (SwipeRefreshLayout)RootView.findViewById(R.id.id_swipe_ly);
		headerimg = RootView.findViewById(R.id.luck_header_img);
		title = (TextView)RootView.findViewById(R.id.header_title) ;
		note = (TextView)RootView.findViewById(R.id.header_note);
		back = (Button)RootView.findViewById(R.id.header_back);
		lotteryBtn = (ImageView) RootView.findViewById(R.id.lottery_item_5);
//		lucktitleimg = RootView.findViewById(R.id.luck_title_img);
		topimg = RootView.findViewById(R.id.luck_view_top);
		luckly = (LinearLayout)RootView.findViewById(R.id.luck_ly_center) ;
		luckcenterbg = RootView.findViewById(R.id.luck_center_bg);
		rolev = RootView.findViewById(R.id.luck_v_role);
		listv = RootView.findViewById(R.id.luck_v_list) ;
		roletv = (TextView)RootView.findViewById(R.id.luck_tv_role);
		scoretv = (TextView)RootView.findViewById(R.id.luck_tv_score);
		luck_listbg = (LinearLayout)RootView.findViewById(R.id.luck_listbg);
		lucklist = (ListView)RootView.findViewById(R.id.luck_list);
		
		
	}
    
	/**
	 * ��ʼ�����
	 */
	private void initView() {
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(R.color.color_refresh_1, R.color.color_refresh_2,
				R.color.color_refresh_3, R.color.color_refresh_4);
		headerimg.setLayoutParams(AndroidUtil.getImageScaleParams(this.father, R.drawable.luckdraw_headerimg));
		luck_listbg.setLayoutParams(AndroidUtil.getImageScaleParams(this.father, R.drawable.luck_list_bg)) ;
		this.title.setText(this.father.getResources().getString(R.string.luckdrawfragment_title));
		this.note.setText("�ҵ��н�");
		this.note.setVisibility(View.VISIBLE);
		this.note.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(father ,PrizeListActivity.class);
				father.startActivity(i);
			}
			
		});
		//���ó齱ת�̵�ͼƬ������С
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(this.father.getWidth()/3,
				(int) ((this.father.getWidth()/3)*AndroidUtil.getImageScale(this.father ,R.drawable.luckdraw_title_img)));
		lp.setMargins(this.father.getWidth()/8, 0, 0, 0);
//		lucktitleimg.setLayoutParams(lp);
		
		lp = new LinearLayout.LayoutParams(this.father.getWidth()*9/10 ,
				(int) ((this.father.getWidth()*9/10)*AndroidUtil.getImageScale(this.father ,R.drawable.luckdraw_topimg)));
		lp.setMargins(this.father.getWidth()*1/20, 0, 0, 0);
		topimg.setLayoutParams(lp);
		lp = new LinearLayout.LayoutParams(this.father.getWidth()*4/5 ,this.father.getWidth()*4/5 );
		lp.setMargins(this.father.getWidth()*1/10, 0, 0, 0);
		luckly.setLayoutParams(lp);
		
		FrameLayout.LayoutParams fp = new FrameLayout.LayoutParams(this.father.getWidth()*9/11 ,LayoutParams.MATCH_PARENT);
		fp.setMargins(this.father.getWidth()*1/11, 0, 0, 0);
		luckcenterbg.setLayoutParams(fp);
		//���ó齱��������Ĳ��ִ�С
		lp = new LinearLayout.LayoutParams(this.father.getWidth()*2/9,
				(int) ((this.father.getWidth()*2/9)*AndroidUtil.getImageScale(this.father ,R.drawable.luckdraw_title_img)));
		lp.setMargins(this.father.getWidth()/11, 0, 0, 0);
		rolev.setLayoutParams(lp);
		//�����н�����������Ĳ��ִ�С
		listv.setLayoutParams(lp);
		//���õ�ǰ����
//		setScoreText();
		//���ó齱��ť�ļ����¼�
		lotteryBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v){
				switch(isplay){
				case 0:

					dialog = new MessageDialog_ui(LuckDrawFragment.this.father ,"��Ϣ��ʾ",false,"�Բ���,Ŀǰû���κλ");		
					dialog.show();
				break;
				case 1:
					lotteryBtn.setEnabled(false);	//���ð�ť�����ظ����
					handler.sendEmptyMessage(LotteryHandler.MSG_START_LOTTERY);
					break;
				case 2:
					dialog = new MessageDialog_ui(LuckDrawFragment.this.father ,"��Ϣ��ʾ",false,"�Բ������Ļ����Ѿ�����");		
					dialog.show();
				}
			}
		});
		
		//���ó齱����
//		List<HashMap<String ,Object>> listdata = new ArrayList<HashMap<String ,Object>>();
//		listdata.add(new HashMap<String ,Object>());
//		listdata.add(new HashMap<String ,Object>());
//		listdata.add(new HashMap<String ,Object>());
//		listdata.add(new HashMap<String ,Object>());
//		listdata.add(new HashMap<String ,Object>());
//		SimpleAdapter adapter =new SimpleAdapter(this.father ,listdata ,R.layout.lucklist_item ,new String[]{} ,new int[]{});
		ArrayList<LuckyGuy<CharSequence, Object>> prizelist = new ArrayList<LuckDrawFragment.LuckyGuy<CharSequence,Object>>();
		prizelist.add(new LuckyGuy<CharSequence, Object>("9*****@qq.com", "�����800Ԫ"));
		prizelist.add(new LuckyGuy<CharSequence, Object>("23****", "�����100Ԫ"));
		prizelist.add(new LuckyGuy<CharSequence, Object>("132****@qq.com", "�����100Ԫ"));
		prizelist.add(new LuckyGuy<CharSequence, Object>("51******", "�����500Ԫ"));
		prizelist.add(new LuckyGuy<CharSequence, Object>("87******", "�����100Ԫ"));
		prizelist.add(new LuckyGuy<CharSequence, Object>("55******", "�����100Ԫ"));
		prizelist.add(new LuckyGuy<CharSequence, Object>("76******@163.com", "�����500Ԫ"));
		LuckyListAdapter adapter = new LuckyListAdapter(prizelist);
		lucklist.setAdapter(adapter);
		listHandler.sendEmptyMessage(LuckyListHandler.MSG_SCROLL_LUCKY_LIST);
		getLotteryChances();
		loading = new LoadingDialog_ui(this.father, R.style.loadingstyle,
				R.layout.dialog_loading_lay);
		loading.setCancelable(false);
		loading.show();
	} 
	
//	private void setScoreText(){
//		scoretv.setText("�ҵĻ��֣�");
//		scoretv.append(TextStyleUtil.setTextAppearanceSpan(this.father, this.score+" ",1.0f));
//	}
	
	/**
	 * ����ʵ��һ���齱�б���������ܷ����ԭ���ṩ��һ����̬��������{@link #getEmptyInstance()}��
	 * �÷������ڻ��һ�����ɱ�Ŀ�ʵ�����Ҷ�{@link #getPrize()}�� {@link #getUsername()}
	 * ���������˷�װ����֧����������ơ����ǣ�����Ŀǰϵͳ�Ľṹ�����Bean�಻����ŵ��������ڣ��ͻ��ˣ���outer
	 * class���������ֱ�ӷ��ʵ���ĳ�Ա����˴���Ǳ�ڵİ�ȫ������
	 * <p>�ͻ�����Ҫע����Ӹ������ƣ����������¼��㣺
	 * <ul>
	 * <li>��Ҫ�����޲ι��캯����</li>
	 * <li>��Ҫֱ�ӵ��ö�����򣬶�Ӧ��ʹ��getter������</li>
	 * <li>����getter����֮ǰʼ�յ��� {@link #isEmptyInstance()}�����жϡ�</li>
	 * </ul></p>
	 * @author Muyangmin
	 * @create 2014-12-24
	 * @param <K> �н��˺�
	 * @param <V> �н���Ʒ
	 */
	private static class LuckyGuy<K extends CharSequence, V extends Object> {

		private K username;
		private V prize;
		
		private static final LuckyGuy<CharSequence, Object> INSTANCE_GUY = 
				new LuckyGuy<CharSequence, Object>();

		/**
		 * ����һ���յĶ���ʵ����ע�⣬ͨ���÷�����õ�ʵ���ǲ��ɱ�ģ����Ҳ������������ {@link #getPrize()}��
		 * {@link #getUsername()}��������ͼ��������������Щ�������׳�һ�� {@link SecurityException}��
		 * �ͻ��˿���ʹ�� {@link #isEmptyInstance()}���ж��ض������Ƿ�Ϊ�����ʵ����
		 */
		protected static final LuckyGuy<CharSequence, Object> getEmptyInstance(){
			return INSTANCE_GUY;
		}
		
		protected boolean isEmptyInstance(){
			return this.username==null; // skip prize field
		}
		
		protected K getUsername(){
			if (isEmptyInstance()){
				throw new SecurityException("you should never call this method due to it is an null object.");
			}
			return username;
		}
		protected V getPrize() {
			if (isEmptyInstance()){
				throw new SecurityException("you should never call this method due to it is an null object.");
			}
			return prize;
		}

		private  LuckyGuy() {}
		/**
		 * create a non-null object.
		 * @param username
		 * @param prize
		 */
		public LuckyGuy(K username, V prize) {
			if (username==null || prize==null){
				throw new IllegalArgumentException(
						"To create null instance, you should call getInstance() method instead this constructor.");
			}
			this.username = username;
			this.prize = prize;
		}

	}

	/**
	 * Ϊ��ʹ�÷��㣬��Adapter��δ���Ϊ���ͻ��ģ����ǣ����Ĺ��캯������һ�������б����б������ֵ�Ѿ����趨���ˡ�
	 * @author Muyangmin
	 * @create 2014-12-24
	 */
	private class LuckyListAdapter extends BaseAdapter{
		private ArrayList<LuckyGuy<CharSequence, Object>> list;
		
		private LuckyListAdapter(ArrayList<LuckyGuy<CharSequence, Object>> prizelist){
			this.list = prizelist;
		}
		
		protected final void scrollData(){
			LuckyGuy<CharSequence, Object> firstItem = list.get(0);
			list.remove(0);
			list.add(firstItem);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return list.size();
		}
        
		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView==null){
				holder = new ViewHolder();
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.lucklist_item, null);
				holder.tvuser = (TextView) convertView.findViewById(R.id.luck_user);
				holder.tvprize= (TextView) convertView.findViewById(R.id.luck_prize);
				convertView.setTag(holder);
			}
			else{
				holder = (ViewHolder) convertView.getTag();
			}
			@SuppressWarnings("unchecked")//we know this is safe
			LuckyGuy<CharSequence, Object> guy = (LuckyGuy<CharSequence, Object>) getItem(position);
			if (!guy.isEmptyInstance()){
				holder.tvuser.setText(guy.getUsername());
				holder.tvprize.setText(guy.getPrize().toString());
			}
			else{//show empty line
				holder.tvuser.setText("");
				holder.tvprize.setText("");
			}
			return convertView;
		}
		private class ViewHolder{
			protected TextView tvuser;
			protected TextView tvprize;
		}
	}

	private LuckyListHandler listHandler = new LuckyListHandler(new WeakReference<LuckDrawFragment>(this));

	private static final class LuckyListHandler extends Handler{
		private static final int MSG_SCROLL_LUCKY_LIST = 3;
		private WeakReference<LuckDrawFragment> weakReference;
		protected LuckyListHandler(WeakReference<LuckDrawFragment> wk){
			weakReference = wk;
		}
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			LuckDrawFragment framgent = weakReference.get();
			if (framgent==null){
				Log.w(LOG_TAG, "activity has gone.do nothing.");
				return ;
			}
			switch (msg.what) {
			case MSG_SCROLL_LUCKY_LIST:
				if (framgent.lucklist==null){
					return ;
				}
				LuckyListAdapter adapter = (LuckyListAdapter) framgent.lucklist.getAdapter();
				if (adapter != null){
					adapter.scrollData();
				}
				framgent.listHandler.sendEmptyMessageDelayed(MSG_SCROLL_LUCKY_LIST, 1000);
				break;

			default:
				break;
			}
		}
	}
	
	/**
	 * ����ת���¼���Listener��
	 * @author Muyangmin
	 * @create 2014-12-19
	 */
	protected interface OnLotteryStateChangedListener{
		/**
		 * ת��ת��ʱ���á�������������ǿ��ǵ����ܻ�ִ�в��������Ȳ�����
		 * ���ڲ����С��±ꡱ�ĺ��壬�μ�{@link #onLotteryFinished(int)}.
		 * @param before ֮ǰ�ĸ����±�
		 * @param current ��ǰ�����±�
		 */
		void onLotteryRunning(int before, int current);
		/**
		 * ת��ֹͣʱ�����á�
		 * @param index ���ֹͣ��λ�ã����λ����ָ�� {@link MainActivity#lotteryItemIds}����±ꡣ
		 */
		void onLotteryFinished(int index);
	}

	private OnLotteryStateChangedListener listener = new OnLotteryStateChangedListener() {

		@Override
		public void onLotteryRunning(int before, int current) {
			// do nothing now
		}

		@Override
		public void onLotteryFinished(int index) {
//			Toast.makeText(LuckDrawFragment.this.father, "��Ǹû�н�������һ����",
//					Toast.LENGTH_SHORT).show();
			// ��Ҫ���ǵ�������ָ��齱��ť
			lotteryBtn.setEnabled(true);
		}
	};
	private LotteryHandler handler = new LotteryHandler(new WeakReference<LuckDrawFragment>(this));
	@SuppressWarnings("unused")
	private static final class LotteryHandler extends Handler{
		private WeakReference<LuckDrawFragment> weakReference;
		/** ����ѡ�õĲ������͡� */
		private final LotteryDelayPolicy delayPolicy;
		
		protected LotteryHandler(WeakReference<LuckDrawFragment> wk){
			weakReference = wk;
			delayPolicy = POLICY_CONSTANT;	//�������޸���Ҫѡ�õĲ���
		}
		
		private static final int MSG_START_LOTTERY = 1;		//����ת��
		private static final int MSG_UPDATE_LOTTERY = 2;	//����ת��
		private static final int MSG_STOP_LOTTERY = 3;	//STOPת��
//		private static final int MSG_FINISH_LOTTERY = 4;	//finishת��
		private static final int FIRST_DELAY_MILLIS = 100;
		
//		private int totalTimes;	//�����ܴ��������ֶ�Ӧ�ý�����ֵһ�Σ�������java ���Թ淶��������ʹ��final�����޶�
		private int times;
		private int delay;
		private int index;
		

		/**
		 * �����ṩһ�����Խӿڣ��ԸýӿڵĲ�ͬʵ�ֿ�����Ч�ı�ת�̵�ת���ٶȡ�
		 * �ͻ��˴���Ӧ�����ǳ���ʵ�ָýӿ����ṩ���Եı����������ʹ������ǩ���ķ�����
		 * @author Muyangmin
		 * @create 2014-12-24
		 */
		private static interface LotteryDelayPolicy{
			/**
			 * ���ת���´�ת�����ӳ�ʱ�䡣��ͬ��ʵ�ֿ�����ѡ��غ��Բ��ֲ��������磬ʵ��һ���������Ĳ��ԣ���
			 * @param totalTimes ת��Ԥ��ת�����ܴ���
			 * @param serial ���ε���ʱ�Ĵ�����ţ�����x��ת����
			 * @param currentDelay ��ǰ�ӳ�ʱ�䣬�� ms Ϊ��λ��
			 * @return ���������ھ�����Ե�ʱ��ֵ���� ms Ϊ��λ��
			 */
			int emulateLotteryDelay(int totalTimes, int serial, int currentDelay);
		}
		/**
		 * ʹ�ó���ֵ�ṩת�̵��ӳ�ֵ���ò��Կ��Ժܺõ�Ӧ��ת��������Χ�仯�ܴ�������
		 * ��UI���ۿ��ǣ�ʹ�øò���ʱ������ת�̴���������2*8�Σ�������ת����Ȧ��
		 */
		private static final LotteryDelayPolicy POLICY_CONSTANT = new LotteryDelayPolicy() {
			@Override
			public int emulateLotteryDelay(int totalTimes, int serial, int currentDelay) {
				return 100;	//100ms ������Ӿ�����ֵ
			}
		};
		
		/**
		 * ʹ�ý��������Ҳ��ܿص��㷨�ṩ�ӳ�ֵ�����㷨������ת���������ߵ���������鲻����40�Σ���
		 */
		private static final LotteryDelayPolicy POLICY_LAZY_LINEAR = new LotteryDelayPolicy() {
			
			@Override
			public int emulateLotteryDelay(int totalTimes, int serial, int currentDelay) {
				if (totalTimes <0 || serial < 0){
					throw new IllegalArgumentException("must be positive!");
				}
				if (serial==0 || currentDelay<100){//avoid parameter attack
					return 100;	//initial value
				}
				return currentDelay+15;
			}
		};
		
		/**
		 * ʹ���������Ľ��������㷨�ṩ�ӳ�ֵ�����㷨������ʵ���ȿ������ת��Ч����
		 */
		private static final LotteryDelayPolicy POLICY_QUALIFIED_LINEAR = new LotteryDelayPolicy() {
			
			@Override
			public int emulateLotteryDelay(int totalTimes, int serial, int currentDelay) {
				if (currentDelay <200){
					currentDelay+=10;
				}
				else if (currentDelay < 400){
					currentDelay+=20;
				}
				else if (currentDelay < 600){
					currentDelay+=30;
				}
				else if (currentDelay<600){
					currentDelay+=40;
				}
				else if (currentDelay<1000){
					currentDelay+=60;
				}
				else if (currentDelay<1100){
					currentDelay += 10;
				}
				return currentDelay;
			}
		};

	
		@Override
		public void handleMessage(Message msg) {
		    boolean isStop = false ;
			super.handleMessage(msg);
			LuckDrawFragment framgent = weakReference.get();
			if (framgent==null){
				Log.w(LOG_TAG, "activity has gone.do nothing.");
				return ;
			}
			switch (msg.what) {
			case MSG_START_LOTTERY:
				//��ʼ����������
				times = 0;
//				totalTimes = times;
				index = 0;
				delay = FIRST_DELAY_MILLIS;
				Log.d(LOG_TAG, "begin lottery:times="+times+", delay="+delay);
				//���õ�һ��Ϊtrue
				((ImageView)framgent.getView().findViewById(framgent.lotteryItemIds[0])).setSelected(false);
				framgent.handler.sendEmptyMessageDelayed(MSG_UPDATE_LOTTERY,delay);
				framgent.getPrizeResult();
				break;
				
			case MSG_UPDATE_LOTTERY:
				//�������Ϊ�ص�������׼��
				final int beforeIndex = index;
				//ִ��UI�л�
				View fragmentView = framgent.getView();
				if (fragmentView==null){
					return ;
				}
				((ImageView)fragmentView.findViewById(framgent.lotteryItemIds[index])).setSelected(false);
				index = (index+1)%framgent.lotteryItemIds.length;
				((ImageView)fragmentView.findViewById(framgent.lotteryItemIds[index])).setSelected(true);
				

				//����͸��²���
//				times++;
//				if (isStop){
//					//������һ����selected״̬
//					((ImageView)framgent.getView().findViewById(framgent.lotteryItemIds[index])).setSelected(false);
//					framgent.listener.onLotteryFinished(index);
//					framgent.handler.removeMessages(MSG_UPDATE_LOTTERY);
//					return ;
//				}
				if(framgent == null){
					framgent.handler.removeMessages(MSG_UPDATE_LOTTERY);
				}
				framgent.listener.onLotteryRunning(beforeIndex, index);
//				Log.d(LOG_TAG, "update lottery:times="+times+", delay="+delay);
//				delay = delayPolicy.emulateLotteryDelay(totalTimes, totalTimes-times, delay);
				framgent.handler.sendEmptyMessageDelayed(MSG_UPDATE_LOTTERY,delay);
				break;
			case MSG_STOP_LOTTERY:
				Log.d(LOG_TAG, "lottery finished.");
				//������һ����selected״̬
				((ImageView)framgent.getView().findViewById(framgent.lotteryItemIds[index])).setSelected(false);
				framgent.listener.onLotteryFinished(index);
				framgent.handler.removeMessages(MSG_UPDATE_LOTTERY);
//				isStop = true ;
				break ;
//			case MSG_FINISH_LOTTERY:
//				Log.d(LOG_TAG, "lottery finished.");
//				//������һ����selected״̬
//				((ImageView)framgent.getView().findViewById(framgent.lotteryItemIds[index])).setSelected(false);
//				framgent.handler.removeMessages(MSG_UPDATE_LOTTERY);
			default:
				break;
			}
		}
		/**
		 * ����һ���������Ҳ����ת��ת���Ĵ�����
		 * @param range ������Χ������100��
		 * @param minNum ��Сֵ
		 * @return ������Сֵ����һ��[0,range)֮�����ֵ��
		 */
		private int generateRandomNumber(int range, int minNum){
			if (range <=0 || minNum <0){
				throw new IllegalArgumentException("null");
			}
			int a = ((int)(Math.random()*10000))%range;
			return minNum + a;
		}
		
	}
	/** ���ʳ齱��Ϣ*/
	public void getLotteryChances() {

		/**
		 * {"DrivingLicenceType":"B2","Testcount":0,
		 * "address":"����","coachId":12, "coachname":"��ƽ·",
		 * "lessonInfocount":0,"licenceCode":"510824199210207794",
		 * "path":"/drivingSchool/attachment/head/2014110715530401412670.jpg",
		 * "phone":"123456","school":"����ѵ����",
		 * "sex":true,"stuId":11,"stuname":"����"}
		 */
		
		String operurl = LuckDrawFragment.this.father.getOperateURL(R.string.webbaseurl,
				R.string.url_getluckinfo);
		// ��¼10s��ʱ ��ֻ��һ����Ϣ
		HanderListObject loginhander = new HanderListObject(10, true) {
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==13){

					// �������
					Main_fragment_ui.setLoginhttpclient(null);
					Toast.makeText(father, "�Բ�����������",
							Toast.LENGTH_SHORT).show();
				}else
				if (msg.what == 11) {
					// ��¼��Ϣ
					JSONObject jobj;
					try {
						
						jobj = new JSONObject(msg.obj.toString());
						Log.i("luck", jobj.toString());
						//���ó齱״̬
						isplay = jobj.getInt("status");
						switch(isplay){
						case 0:

							dialog = new MessageDialog_ui(LuckDrawFragment.this.father ,"��Ϣ��ʾ",false,"�Բ���,Ŀǰû���κλ");		
							dialog.show();
						break;
						case 1:
							lotteryInfoId = jobj.getInt("lotteryInfoId");
							break ;
						case 2:
							dialog = new MessageDialog_ui(LuckDrawFragment.this.father ,"��Ϣ��ʾ",false,"�Բ������Ļ����Ѿ�����");		
							dialog.show();
						}
						

					} catch (JSONException e) {
						e.printStackTrace();
					}
					// {"addr":"���ӿƴ�","id":1,"licence":"C1","name":"�",
					// "path":"/attchment/images/IMG_2014092216410302242395.jpg",
					// "phone":"13403150491","teacherId":"00000001"}
					// �����û�ID

	

				} else {
					Toast.makeText(father, msg.obj + "",
							Toast.LENGTH_SHORT).show();
				}
				if(mSwipeLayout.isRefreshing()){
					mSwipeLayout.setRefreshing(false);
				}
				// �ر�
				if (loading != null) {
					loading.dismiss();
					loading = null;
				}
			}
		};

		// ��ʼ������
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("mobileFlag",father.readString("mobileFlag"));
        
		param.put("operateurl", operurl);
		param.put("encoding", father.getStringValue(R.string.encoding));
		// �½���������
		GetHttpResultThread login = new GetHttpResultThread(loginhander, param);

		// ע�����
		loginhander.addtolisttask(login);



	}
	/** ��ȡ��Ʒ */
	private void getPrizeResult() {
		
		String operurl = LuckDrawFragment.this.father.getOperateURL(R.string.webbaseurl,
				R.string.url_getluckresult);
		// ��¼10s��ʱ ��ֻ��һ����Ϣ
		HanderListObject loginhander = new HanderListObject(10, true) {
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==13){

					// �������
					Main_fragment_ui.setLoginhttpclient(null);
					Toast.makeText(father, "�Բ�����������",
							Toast.LENGTH_SHORT).show();
				}else
				if (msg.what == 11) {
					// ��¼��Ϣ
					JSONObject jobj;
					try {
						
						jobj = new JSONObject(msg.obj.toString());
						Log.i("luck", jobj.toString());
						switch(jobj.getInt("status")){
						//status����1��ʾ�齱�ɹ� ���ҷ��ؽ�Ʒ��Ϣ��2��ʾ�齱��Ϣ�Ƿ���3��ʾ�齱δ��ʼ��4��ʾ�齱����
						case 1:
							isplay = 2 ;
							String prize = jobj.getJSONObject("prizeInfo").getString("prizeName");
							dialog = new MessageDialog_ui(LuckDrawFragment.this.father ,"�н����" , true ,prize);		
							dialog.show();
							break;
						case 2:
							dialog = new MessageDialog_ui(LuckDrawFragment.this.father ,"�н����" , true ,"�齱��Ϣ�Ƿ�");		
							dialog.show();
							break;
						case 3:
							dialog = new MessageDialog_ui(LuckDrawFragment.this.father ,"�н����" , true ,"�齱δ��ʼ");		
							dialog.show();
							break;
						case 4:
							dialog = new MessageDialog_ui(LuckDrawFragment.this.father ,"�н����" , true ,"�齱�ѽ���");		
							dialog.show();
							break;
						}
                        handler.sendEmptyMessageDelayed(LotteryHandler.MSG_STOP_LOTTERY,0);
						// �������
                        
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// {"addr":"���ӿƴ�","id":1,"licence":"C1","name":"�",
					// "path":"/attchment/images/IMG_2014092216410302242395.jpg",
					// "phone":"13403150491","teacherId":"00000001"}
					// �����û�ID

	

				} else {
//					Toast.makeText(father, msg.obj + "",
//							Toast.LENGTH_SHORT).show();
				}
               
			}
		};

		// ��ʼ������
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lotteryInfoId", lotteryInfoId);

		param.put("mobileFlag",MyUtil.getUuid());
        
		param.put("operateurl", operurl);
		param.put("encoding", father.getStringValue(R.string.encoding));
		// �½���������
		GetHttpResultThread login = new GetHttpResultThread(loginhander, param);

		// ע�����
		loginhander.addtolisttask(login);

//		loading = new LoadingDialog_ui(this.father, R.style.loadingstyle,
//				R.layout.dialog_loading_lay);
//		loading.setCancelable(false);
//		loading.show();

	}
	@Override
	public void onRefresh() {
		getLotteryChances();
	}
	
	
}
