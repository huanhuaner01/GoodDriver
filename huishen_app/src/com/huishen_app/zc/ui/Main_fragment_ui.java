package com.huishen_app.zc.ui;

import org.apache.http.client.HttpClient;

import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.ui.fragment.BookFragment;
import com.huishen_app.zc.ui.fragment.ButtomFragment;
import com.huishen_app.zc.ui.fragment.CenterFragment;
import com.huishen_app.zc.ui.fragment.UserCenterFragment;
import com.huishen_app.zc.ui.fragment.WeGroupFragment;
import com.huishen_app.zh.netTool.AppController;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;

public class Main_fragment_ui extends BaseActivity {

	/** 登录标志位 */
	private static HttpClient loginhttpclient = null;
	
    private int backindex = 0 ;
	/** 三个框架 */
	private Fragment header, center, buttom;

	/** 管理器 */
	private FragmentTransaction frag_tran;

	protected void findViewById_Init() {
		// 启动服务
		startservice();
		
		setContentView(R.layout.main_fragment_lay);
		frag_tran = getSupportFragmentManager().beginTransaction();

		buttom = new ButtomFragment(this);
		frag_tran.add(R.id.buttom_fragment, buttom);

		center = new CenterFragment(this);
		frag_tran.add(R.id.center_fragment, center);

		// 提交显示
		frag_tran.commit();
	}
	protected void initView() {
        
	}

	protected void initData() {

	}

	public void finish() {
		stopservice();
		loginhttpclient = null;
		super.finish();
	}

	/**
	 * 切换中间区域
	 */
	public void switchcenter(int homeTabBook) {
		Fragment new_center = null;
		if (homeTabBook == R.id.home_tab_main) {
			new_center = new CenterFragment(this);
			switchcontent(homeTabBook, new_center);
		} else if (homeTabBook == R.id.home_tab_book) {
			if(readString("roleId").equals("6")){
				new_center = new BookFragment(this);
				switchcontent(homeTabBook, new_center);
			}else{
				DisPlay("对不起，您身份未激活，请联系驾校激活!", true);
			}
			
		} else if (homeTabBook == R.id.home_tab_usercenter) {
			new_center = new UserCenterFragment(this);
			switchcontent(homeTabBook, new_center);
		} else if (homeTabBook == R.id.home_tab_djt) {
//			DisPlay("敬请期待!" + homeTabBook, true);
			new_center = new WeGroupFragment(this);
			switchcontent(homeTabBook, new_center);
			
		} else if (homeTabBook == R.id.main_menu_zjx) {
			((ButtomFragment) buttom).clearcheck();
//			DisPlay("敬请期待!" + homeTabBook, true);
			// 打开找驾校
				Intent i = new Intent(Main_fragment_ui.this ,JXListActivity.class);
				startActivity(i);

		} else if (homeTabBook == R.id.main_menu_zjl) {
			((ButtomFragment) buttom).clearcheck();
//			DisPlay("敬请期待!" + homeTabBook, true);
			// 打开找教练 
				Intent i = new Intent(Main_fragment_ui.this ,JLListActivity.class);
				startActivity(i);
				
		} else if (homeTabBook == R.id.main_menu_zxyy) {
			if (Main_fragment_ui.getLoginhttpclient() == null) {
				switchActivity();
				
			} else {
				if(readString("roleId").equals("6")){
				((ButtomFragment) buttom).setcheck(R.id.home_tab_book);
				}else{
					DisPlay("对不起，您身份未激活，请联系驾校激活!", true);
				}
			}
		} else if (homeTabBook == R.id.main_menu_xclc) {
			Intent i = new Intent(Main_fragment_ui.this ,ProcessActivity.class);
			startActivity(i);
		} else if (homeTabBook == R.id.main_menu_jfsc) {
			if (Main_fragment_ui.getLoginhttpclient() == null) {
				switchActivity();
				
			} else {
				
					Intent i = new Intent(Main_fragment_ui.this ,JFSCActivity.class);
					startActivity(i);
			}
			
		} else if (homeTabBook == R.id.main_menu_djt) {
			DisPlay("敬请期待!" + homeTabBook, true);
//			((ButtomFragment) buttom).setcheck(R.id.home_tab_djt);
		}else {
			DisPlay("敬请期待!" + homeTabBook, true);
		}
	}

	/**
	 * 切换中心区域
	 * 
	 * @param homeTabBook
	 * @param new_center
	 */
	private void switchcontent(int homeTabBook, Fragment new_center) {
		/* 如果新加载的不是需要更新的 */
		frag_tran = getSupportFragmentManager().beginTransaction();
		frag_tran.replace(R.id.center_fragment, new_center);
		// 提交显示
		frag_tran.commitAllowingStateLoss();
	}

	/** 登录状态改变 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			if (Login_ui.LOGINCODE == resultCode) {
				boolean islogin = data.getExtras().getBoolean(
						Login_ui.LoginstateName);
				ButtomFragment br = (ButtomFragment) buttom;
				Log.i("Main", "islogin:"+islogin);
				if (islogin == true) {
					br.setcheck(R.id.home_tab_usercenter);
					switchcenter(R.id.home_tab_usercenter);
				} else {
					br.setcheck(R.id.home_tab_main);
					switchcenter(R.id.home_tab_main);
				}
			}
			super.onActivityResult(requestCode, resultCode, data);
		} catch (Exception e) {
		}
	}

	public static HttpClient getLoginhttpclient() {
		return loginhttpclient;
	}

	public static void setLoginhttpclient(HttpClient loginhttpclient) {
		Main_fragment_ui.loginhttpclient = loginhttpclient;	
		AppController.getInstance().getRequestQueue().getCache().clear();
	}

	@Override
	public void switchActivity() {
		ComponentName componentName = new ComponentName(this,
				Login_ui.class);
		Intent intent = new Intent();
		intent.setComponent(componentName);
		startActivityForResult(intent, Login_ui.LOGINCODE);
		Log.i("Main", "intent to login ");
		super.switchActivity();
	}

	@Override
	protected void onStop() {
		AppController.getInstance().getRequestQueue().cancelAll(getApplicationContext());
		AppController.getInstance().getRequestQueue().getCache().clear();
		super.onStop();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 
        if (keyCode == KeyEvent.KEYCODE_BACK
                 && event.getRepeatCount() == 0) {
             if(backindex == 0){
            	 DisPlay("再按一次退出好司机!", true);
            	 backindex++;
            	 return false ;
             }
             finish();
             return true;
         }
         return super.onKeyDown(keyCode, event);
     }

}
