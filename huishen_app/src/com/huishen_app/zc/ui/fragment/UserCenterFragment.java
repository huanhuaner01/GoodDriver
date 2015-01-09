package com.huishen_app.zc.ui.fragment;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.huishen_app.all.mywidget.RoundImageView;
import com.huishen_app.zc.ui.Book_mokao_ui;
import com.huishen_app.zc.ui.Book_yuyue_ui;
import com.huishen_app.zc.ui.LocationActivity;
import com.huishen_app.zc.ui.PrizeListActivity;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.TrainHisTabActivity;
import com.huishen_app.zc.ui.TrainStaActivity;
import com.huishen_app.zc.ui.UserCenterDetailActivity;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.ui.dialog.LoadingDialog_ui;
import com.huishen_app.zh.netTool.AppController;
import com.huishen_app.zh.netTool.NetUtil;

@SuppressLint("ValidFragment")
public class UserCenterFragment extends BaseFragment implements OnClickListener {
    private String TAG = "UserCenterFragment";
	private LinearLayout uc_lay;
	private View this_lay;

	private Button his, sta, user_center_yuyue, user_center_mokao ,prize;
	private TextView city , title ;
	private TextView username , userlicense , userschool ,tip1,tip2;//用户姓名，驾照类型，驾校名称,预约学车数据提示，预约模拟数据提示
	private RoundImageView img ; //用户头像
	
	private LoadingDialog_ui dialog ;//加载框
	private boolean showstate = false ;
	public UserCenterFragment(BaseActivity father) {
		super(father);
	}
	
	@Override
	public void onResume() {

	    AppController.getInstance().getRequestQueue().getCache().clear();
			if(father.readString("city")!= null &&!father.readString("city").equals("")){
				city.setText(father.readString("city").toString());
			}
		
		if(showstate){
		initView();	
		}
		showstate = true ;
		super.onResume();
	}



	@SuppressLint("InflateParams")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try {
			this_lay = inflater.inflate(R.layout.user_center, null);
			findViewById_Init();
			initView();
             

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this_lay;
	}

	protected void findViewById_Init() {
		// 初始化组件变量
		uc_lay = (LinearLayout) this_lay
				.findViewById(R.id.usercenter_center_info_bg);

		user_center_yuyue = (Button) this_lay
				.findViewById(R.id.user_center_yuyue);
		user_center_mokao = (Button) this_lay
				.findViewById(R.id.user_center_mokao);
		prize = (Button)this_lay.findViewById(R.id.user_center_prize);

		his = (Button) this_lay.findViewById(R.id.user_center_train_his);
		sta = (Button) this_lay.findViewById(R.id.user_center_train_sta);
		city = (TextView) this_lay.findViewById(R.id.header_city);
		title = (TextView) this_lay.findViewById(R.id.header_title);
		
		
		username = (TextView) this_lay.findViewById(R.id.user_name);
		userlicense = (TextView) this_lay.findViewById(R.id.user_license);
		userschool = (TextView) this_lay.findViewById(R.id.user_school);
		img = (RoundImageView)this_lay.findViewById(R.id.user_center_infobg);
	    tip1 = (TextView) this_lay.findViewById(R.id.user_center_tip1);
	    tip2 = (TextView) this_lay.findViewById(R.id.user_center_tip2);
	}

	protected void initView() {
		if(father.readString("city")!= null &&!father.readString("city").equals("")){
			city.setText(father.readString("city").toString());
		}
		title.setText("个人中心");
		// 行布局
		int width = father.getWidth() > 100 ? father.getWidth() : 200;
		LinearLayout.LayoutParams imglayout = new LinearLayout.LayoutParams(
				width, father.getHeight() / 3);
		// 设置图片大小
		uc_lay.setLayoutParams(imglayout);

		his.setOnClickListener(this);
		sta.setOnClickListener(this);

		user_center_yuyue.setOnClickListener(this);
		user_center_mokao.setOnClickListener(this);
		prize.setOnClickListener(this);
		uc_lay.setOnClickListener(this);
		try{
		JSONObject json = new JSONObject(father.readString("login_result"));
		Log.i(TAG, json.toString());
		username.setText(json.optString("stuname","暂无"));
		userlicense.setText("驾照类型："+json.optString("DrivingLicenceType","暂无"));
		userschool.setText("驾校："+json.optString("school","暂无"));
//		设置预约的数目
		if(father.readString("yuyuenum").equals("0")){
			tip1.setVisibility(View.GONE);
		}
		if(father.readString("testnum").equals("0")){
			tip2.setVisibility(View.GONE);
		}
		tip1.setText(father.readString("yuyuenum")); 
		tip2.setText(father.readString("testnum")); 
		getUserPhoto(this.father.readString("path"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

     
	public void onClick(View v) {
		if (v.getId() == R.id.user_center_prize) {
			Intent i = new Intent(father, PrizeListActivity.class);
			startActivity(i);
		}else
		if (v.getId() == R.id.user_center_yuyue) {
			if(!this.father.readString("roleId").equals("6")){
				this.father.DisPlay("对不起，您身份未激活，请联系驾校激活!", true);
				return ;
			}
			Intent i = new Intent(father, Book_yuyue_ui.class);
			i.putExtra("opentype", "user_center");
			startActivity(i);
		} else if (v.getId() == R.id.user_center_mokao) {
			if(!this.father.readString("roleId").equals("6")){
				this.father.DisPlay("对不起，您身份未激活，请联系驾校激活!", true);
				return ;
			}
			Intent i = new Intent(father, Book_mokao_ui.class);
			i.putExtra("opentype", "user_center");
			startActivity(i);
		} else if (v.getId() == R.id.usercenter_center_info_bg) {
			
			Intent i = new Intent(father, UserCenterDetailActivity.class);
			 startActivityForResult(i, 0);
		}else if (v.getId() == R.id.user_center_train_his) {
			if(!this.father.readString("roleId").equals("6")){
				this.father.DisPlay("对不起，您身份未激活，请联系驾校激活!", true);
				return ;
			}
			Intent i = new Intent(father, TrainHisTabActivity.class);
			startActivity(i);
		}else if (v.getId() == R.id.user_center_train_sta) {
			if(!this.father.readString("roleId").equals("6")){
				this.father.DisPlay("对不起，您身份未激活，请联系驾校激活!", true);
				return ;
			}
			Intent i = new Intent(father, TrainStaActivity.class);
			startActivity(i);
		} else {
			father.switchcenter(v.getId());
		}

	}
	public void getUserPhoto(String path){
//		String url = "http://huishen.wicp.net/jxsys/attchment/images//IMG_201411051128290188114.jpg";
		String url = getString(R.string.webbaseurl)+path;
		if(path.equals("")){
			Log.i(TAG, "头像为空");
			return ;
			
		}
		if(!NetUtil.isNetworkConnected(this.father)){
			Toast.makeText(this.father, "网络未连接", Toast.LENGTH_SHORT).show();
			return ;
		}
		Log.i(TAG, "url："+url);
		ImageLoader imageLoader = AppController.getInstance().getImageLoader();
		dialog = new LoadingDialog_ui(this.father, R.style.loadingstyle,
				R.layout.dialog_loading_lay);
		dialog.setCancelable(true);
		dialog.show();
		// If you are using normal ImageView
		imageLoader.get(url, new ImageListener() {

		    @Override
		    public void onErrorResponse(VolleyError error) {
		    	dialog.dismiss();
//		        Toast.makeText(father, "错误信息："+error.getMessage(), Toast.LENGTH_SHORT).show();
		    }

		    @Override
		    public void onResponse(ImageContainer response, boolean arg1) {
		        if (response.getBitmap() != null) {
		            // load image into imageview
		            img.setImageBitmap(response.getBitmap());
		            dialog.dismiss();
		        }
		    }
		});
	}
	
}
