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
 * 积分抽奖
 * @author zhanghuan
 *
 */
public class LuckDrawFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
	private SwipeRefreshLayout mSwipeLayout;
	private static final String LOG_TAG = "LuckDrawFragment";
	   private View headerimg ,lucktitleimg ,topimg ,luckcenterbg ,rolev ,listv;
	   private TextView title ,scoretv , roletv ,note;
	   private View RootView ;
	   private Button back ; //返回键
	   private LinearLayout luckly ,luck_listbg; //抽奖布局
	   private ListView lucklist ; //中奖名单
	   private int score = 300 ;
	   private MessageDialog_ui dialog ; //中奖消息弹出框
	   private int isplay = 0; //是否可以玩
	   private int lotteryInfoId = 0 ;
	   /** 加载对话框 */
		private LoadingDialog_ui loading;
//	   
		/**
		 * 这是转盘中的Item组件id列表，需要注意的是这些id按顺时针方向排列，即12369874。
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
	 * 注册组件
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
	 * 初始化组件
	 */
	private void initView() {
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(R.color.color_refresh_1, R.color.color_refresh_2,
				R.color.color_refresh_3, R.color.color_refresh_4);
		headerimg.setLayoutParams(AndroidUtil.getImageScaleParams(this.father, R.drawable.luckdraw_headerimg));
		luck_listbg.setLayoutParams(AndroidUtil.getImageScaleParams(this.father, R.drawable.luck_list_bg)) ;
		this.title.setText(this.father.getResources().getString(R.string.luckdrawfragment_title));
		this.note.setText("我的中奖");
		this.note.setVisibility(View.VISIBLE);
		this.note.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(father ,PrizeListActivity.class);
				father.startActivity(i);
			}
			
		});
		//设置抽奖转盘的图片比例大小
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
		//设置抽奖规则字体的布局大小
		lp = new LinearLayout.LayoutParams(this.father.getWidth()*2/9,
				(int) ((this.father.getWidth()*2/9)*AndroidUtil.getImageScale(this.father ,R.drawable.luckdraw_title_img)));
		lp.setMargins(this.father.getWidth()/11, 0, 0, 0);
		rolev.setLayoutParams(lp);
		//设置中奖名名单字体的布局大小
		listv.setLayoutParams(lp);
		//设置当前积分
//		setScoreText();
		//设置抽奖按钮的监听事件
		lotteryBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v){
				switch(isplay){
				case 0:

					dialog = new MessageDialog_ui(LuckDrawFragment.this.father ,"消息提示",false,"对不起,目前没有任何活动");		
					dialog.show();
				break;
				case 1:
					lotteryBtn.setEnabled(false);	//禁用按钮避免重复点击
					handler.sendEmptyMessage(LotteryHandler.MSG_START_LOTTERY);
					break;
				case 2:
					dialog = new MessageDialog_ui(LuckDrawFragment.this.father ,"消息提示",false,"对不起，您的机会已经用完");		
					dialog.show();
				}
			}
		});
		
		//设置抽奖名单
//		List<HashMap<String ,Object>> listdata = new ArrayList<HashMap<String ,Object>>();
//		listdata.add(new HashMap<String ,Object>());
//		listdata.add(new HashMap<String ,Object>());
//		listdata.add(new HashMap<String ,Object>());
//		listdata.add(new HashMap<String ,Object>());
//		listdata.add(new HashMap<String ,Object>());
//		SimpleAdapter adapter =new SimpleAdapter(this.father ,listdata ,R.layout.lucklist_item ,new String[]{} ,new int[]{});
		ArrayList<LuckyGuy<CharSequence, Object>> prizelist = new ArrayList<LuckDrawFragment.LuckyGuy<CharSequence,Object>>();
		prizelist.add(new LuckyGuy<CharSequence, Object>("9*****@qq.com", "代金卷800元"));
		prizelist.add(new LuckyGuy<CharSequence, Object>("23****", "代金卷100元"));
		prizelist.add(new LuckyGuy<CharSequence, Object>("132****@qq.com", "代金卷100元"));
		prizelist.add(new LuckyGuy<CharSequence, Object>("51******", "代金卷500元"));
		prizelist.add(new LuckyGuy<CharSequence, Object>("87******", "代金卷100元"));
		prizelist.add(new LuckyGuy<CharSequence, Object>("55******", "代金卷100元"));
		prizelist.add(new LuckyGuy<CharSequence, Object>("76******@163.com", "代金卷500元"));
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
//		scoretv.setText("我的积分：");
//		scoretv.append(TextStyleUtil.setTextAppearanceSpan(this.father, this.score+" ",1.0f));
//	}
	
	/**
	 * 该类实现一个抽奖列表项。出于性能方面的原因，提供了一个静态工厂方法{@link #getEmptyInstance()}，
	 * 该方法用于获得一个不可变的空实例，且对{@link #getPrize()}和 {@link #getUsername()}
	 * 方法进行了封装，以支持这样的设计。但是，由于目前系统的结构，这个Bean类不方便放到其他包内，客户端（即outer
	 * class）代码可以直接访问到类的成员，因此存在潜在的安全隐患。
	 * <p>客户端需要注意遵从该类的设计，尤其是以下几点：
	 * <ul>
	 * <li>不要调用无参构造函数。</li>
	 * <li>不要直接调用对象的域，而应该使用getter方法。</li>
	 * <li>调用getter方法之前始终调用 {@link #isEmptyInstance()}进行判断。</li>
	 * </ul></p>
	 * @author Muyangmin
	 * @create 2014-12-24
	 * @param <K> 中奖账号
	 * @param <V> 中奖奖品
	 */
	private static class LuckyGuy<K extends CharSequence, V extends Object> {

		private K username;
		private V prize;
		
		private static final LuckyGuy<CharSequence, Object> INSTANCE_GUY = 
				new LuckyGuy<CharSequence, Object>();

		/**
		 * 返回一个空的对象实例。注意，通过该方法获得的实例是不可变的，并且不允许调用它的 {@link #getPrize()}和
		 * {@link #getUsername()}方法。试图从这个对象调用这些方法将抛出一个 {@link SecurityException}。
		 * 客户端可以使用 {@link #isEmptyInstance()}来判断特定对象是否为这个空实例。
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
	 * 为了使用方便，该Adapter并未设计为泛型化的，但是，它的构造函数接受一个泛型列表，该列表的上限值已经被设定好了。
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
	 * 监听转盘事件的Listener。
	 * @author Muyangmin
	 * @create 2014-12-19
	 */
	protected interface OnLotteryStateChangedListener{
		/**
		 * 转盘转动时调用。设置这个方法是考虑到可能会执行播放声音等操作。
		 * 关于参数中“下标”的含义，参见{@link #onLotteryFinished(int)}.
		 * @param before 之前的高亮下标
		 * @param current 当前高亮下标
		 */
		void onLotteryRunning(int before, int current);
		/**
		 * 转盘停止时被调用。
		 * @param index 最后停止的位置，这个位置是指在 {@link MainActivity#lotteryItemIds}里的下标。
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
//			Toast.makeText(LuckDrawFragment.this.father, "抱歉没中奖，再来一发？",
//					Toast.LENGTH_SHORT).show();
			// 重要！记得在这里恢复抽奖按钮
			lotteryBtn.setEnabled(true);
		}
	};
	private LotteryHandler handler = new LotteryHandler(new WeakReference<LuckDrawFragment>(this));
	@SuppressWarnings("unused")
	private static final class LotteryHandler extends Handler{
		private WeakReference<LuckDrawFragment> weakReference;
		/** 具体选用的策略类型。 */
		private final LotteryDelayPolicy delayPolicy;
		
		protected LotteryHandler(WeakReference<LuckDrawFragment> wk){
			weakReference = wk;
			delayPolicy = POLICY_CONSTANT;	//在这里修改需要选用的策略
		}
		
		private static final int MSG_START_LOTTERY = 1;		//启动转盘
		private static final int MSG_UPDATE_LOTTERY = 2;	//更新转盘
		private static final int MSG_STOP_LOTTERY = 3;	//STOP转盘
//		private static final int MSG_FINISH_LOTTERY = 4;	//finish转盘
		private static final int FIRST_DELAY_MILLIS = 100;
		
//		private int totalTimes;	//保存总次数，该字段应该仅被赋值一次，但限于java 语言规范，并不能使用final进行限定
		private int times;
		private int delay;
		private int index;
		

		/**
		 * 这里提供一个策略接口，对该接口的不同实现可以有效改变转盘的转动速度。
		 * 客户端代码应该总是尝试实现该接口以提供策略的变更，而不是使用其他签名的方法。
		 * @author Muyangmin
		 * @create 2014-12-24
		 */
		private static interface LotteryDelayPolicy{
			/**
			 * 获得转盘下次转动的延迟时间。不同的实现可以有选择地忽略部分参数（例如，实现一个常量级的策略）。
			 * @param totalTimes 转盘预计转动的总次数
			 * @param serial 本次调用时的次数序号（即第x次转动）
			 * @param currentDelay 当前延迟时间，以 ms 为单位。
			 * @return 返回依赖于具体策略的时间值，以 ms 为单位。
			 */
			int emulateLotteryDelay(int totalTimes, int serial, int currentDelay);
		}
		/**
		 * 使用常量值提供转盘的延迟值。该策略可以很好地应对转动次数范围变化很大的情况。
		 * 从UI美观考虑，使用该策略时，建议转盘次数不少于2*8次，即至少转动两圈。
		 */
		private static final LotteryDelayPolicy POLICY_CONSTANT = new LotteryDelayPolicy() {
			@Override
			public int emulateLotteryDelay(int totalTimes, int serial, int currentDelay) {
				return 100;	//100ms 是最低视觉暂留值
			}
		};
		
		/**
		 * 使用渐进增长且不受控的算法提供延迟值，该算法适用于转动次数不高的情况（建议不超过40次）。
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
		 * 使用修正过的渐进增长算法提供延迟值，该算法适用于实现先快后慢的转盘效果。
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
				//初始化各个参数
				times = 0;
//				totalTimes = times;
				index = 0;
				delay = FIRST_DELAY_MILLIS;
				Log.d(LOG_TAG, "begin lottery:times="+times+", delay="+delay);
				//设置第一个为true
				((ImageView)framgent.getView().findViewById(framgent.lotteryItemIds[0])).setSelected(false);
				framgent.handler.sendEmptyMessageDelayed(MSG_UPDATE_LOTTERY,delay);
				framgent.getPrizeResult();
				break;
				
			case MSG_UPDATE_LOTTERY:
				//保存参数为回调方法做准备
				final int beforeIndex = index;
				//执行UI切换
				View fragmentView = framgent.getView();
				if (fragmentView==null){
					return ;
				}
				((ImageView)fragmentView.findViewById(framgent.lotteryItemIds[index])).setSelected(false);
				index = (index+1)%framgent.lotteryItemIds.length;
				((ImageView)fragmentView.findViewById(framgent.lotteryItemIds[index])).setSelected(true);
				

				//计算和更新参数
//				times++;
//				if (isStop){
//					//解除最后一个的selected状态
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
				//解除最后一个的selected状态
				((ImageView)framgent.getView().findViewById(framgent.lotteryItemIds[index])).setSelected(false);
				framgent.listener.onLotteryFinished(index);
				framgent.handler.removeMessages(MSG_UPDATE_LOTTERY);
//				isStop = true ;
				break ;
//			case MSG_FINISH_LOTTERY:
//				Log.d(LOG_TAG, "lottery finished.");
//				//解除最后一个的selected状态
//				((ImageView)framgent.getView().findViewById(framgent.lotteryItemIds[index])).setSelected(false);
//				framgent.handler.removeMessages(MSG_UPDATE_LOTTERY);
			default:
				break;
			}
		}
		/**
		 * 生成一个随机数，也就是转盘转动的次数。
		 * @param range 波动范围，比如100。
		 * @param minNum 最小值
		 * @return 返回最小值加上一个[0,range)之间的数值。
		 */
		private int generateRandomNumber(int range, int minNum){
			if (range <=0 || minNum <0){
				throw new IllegalArgumentException("null");
			}
			int a = ((int)(Math.random()*10000))%range;
			return minNum + a;
		}
		
	}
	/** 访问抽奖信息*/
	public void getLotteryChances() {

		/**
		 * {"DrivingLicenceType":"B2","Testcount":0,
		 * "address":"北京","coachId":12, "coachname":"河平路",
		 * "lessonInfocount":0,"licenceCode":"510824199210207794",
		 * "path":"/drivingSchool/attachment/head/2014110715530401412670.jpg",
		 * "phone":"123456","school":"长安训练场",
		 * "sex":true,"stuId":11,"stuname":"张三"}
		 */
		
		String operurl = LuckDrawFragment.this.father.getOperateURL(R.string.webbaseurl,
				R.string.url_getluckinfo);
		// 登录10s超时 且只收一条消息
		HanderListObject loginhander = new HanderListObject(10, true) {
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==13){

					// 保存对象
					Main_fragment_ui.setLoginhttpclient(null);
					Toast.makeText(father, "对不起，您已下线",
							Toast.LENGTH_SHORT).show();
				}else
				if (msg.what == 11) {
					// 登录信息
					JSONObject jobj;
					try {
						
						jobj = new JSONObject(msg.obj.toString());
						Log.i("luck", jobj.toString());
						//设置抽奖状态
						isplay = jobj.getInt("status");
						switch(isplay){
						case 0:

							dialog = new MessageDialog_ui(LuckDrawFragment.this.father ,"消息提示",false,"对不起,目前没有任何活动");		
							dialog.show();
						break;
						case 1:
							lotteryInfoId = jobj.getInt("lotteryInfoId");
							break ;
						case 2:
							dialog = new MessageDialog_ui(LuckDrawFragment.this.father ,"消息提示",false,"对不起，您的机会已经用完");		
							dialog.show();
						}
						

					} catch (JSONException e) {
						e.printStackTrace();
					}
					// {"addr":"电子科大","id":1,"licence":"C1","name":"杨俊",
					// "path":"/attchment/images/IMG_2014092216410302242395.jpg",
					// "phone":"13403150491","teacherId":"00000001"}
					// 保存用户ID

	

				} else {
					Toast.makeText(father, msg.obj + "",
							Toast.LENGTH_SHORT).show();
				}
				if(mSwipeLayout.isRefreshing()){
					mSwipeLayout.setRefreshing(false);
				}
				// 关闭
				if (loading != null) {
					loading.dismiss();
					loading = null;
				}
			}
		};

		// 初始化参数
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("mobileFlag",father.readString("mobileFlag"));
        
		param.put("operateurl", operurl);
		param.put("encoding", father.getStringValue(R.string.encoding));
		// 新建操作对象
		GetHttpResultThread login = new GetHttpResultThread(loginhander, param);

		// 注册监听
		loginhander.addtolisttask(login);



	}
	/** 获取奖品 */
	private void getPrizeResult() {
		
		String operurl = LuckDrawFragment.this.father.getOperateURL(R.string.webbaseurl,
				R.string.url_getluckresult);
		// 登录10s超时 且只收一条消息
		HanderListObject loginhander = new HanderListObject(10, true) {
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==13){

					// 保存对象
					Main_fragment_ui.setLoginhttpclient(null);
					Toast.makeText(father, "对不起，您已下线",
							Toast.LENGTH_SHORT).show();
				}else
				if (msg.what == 11) {
					// 登录信息
					JSONObject jobj;
					try {
						
						jobj = new JSONObject(msg.obj.toString());
						Log.i("luck", jobj.toString());
						switch(jobj.getInt("status")){
						//status返回1表示抽奖成功 并且返回奖品信息，2表示抽奖信息非法，3表示抽奖未开始，4表示抽奖结束
						case 1:
							isplay = 2 ;
							String prize = jobj.getJSONObject("prizeInfo").getString("prizeName");
							dialog = new MessageDialog_ui(LuckDrawFragment.this.father ,"中奖结果" , true ,prize);		
							dialog.show();
							break;
						case 2:
							dialog = new MessageDialog_ui(LuckDrawFragment.this.father ,"中奖结果" , true ,"抽奖信息非法");		
							dialog.show();
							break;
						case 3:
							dialog = new MessageDialog_ui(LuckDrawFragment.this.father ,"中奖结果" , true ,"抽奖未开始");		
							dialog.show();
							break;
						case 4:
							dialog = new MessageDialog_ui(LuckDrawFragment.this.father ,"中奖结果" , true ,"抽奖已结束");		
							dialog.show();
							break;
						}
                        handler.sendEmptyMessageDelayed(LotteryHandler.MSG_STOP_LOTTERY,0);
						// 保存对象
                        
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// {"addr":"电子科大","id":1,"licence":"C1","name":"杨俊",
					// "path":"/attchment/images/IMG_2014092216410302242395.jpg",
					// "phone":"13403150491","teacherId":"00000001"}
					// 保存用户ID

	

				} else {
//					Toast.makeText(father, msg.obj + "",
//							Toast.LENGTH_SHORT).show();
				}
               
			}
		};

		// 初始化参数
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lotteryInfoId", lotteryInfoId);

		param.put("mobileFlag",MyUtil.getUuid());
        
		param.put("operateurl", operurl);
		param.put("encoding", father.getStringValue(R.string.encoding));
		// 新建操作对象
		GetHttpResultThread login = new GetHttpResultThread(loginhander, param);

		// 注册监听
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
