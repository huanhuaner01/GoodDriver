package com.huishen_app.zc.ui.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import com.huishen_app.all.alarmservice.PollingUtils;
import com.huishen_app.all.alarmservice.TaskServer;
import com.huishen_app.all.calendar.Calendar_ui;
import com.huishen_app.zc.ui.dialog.LoadingDialog_ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public abstract class BaseActivity extends FragmentActivity {

	public static final String DATASTRUCT = "yyyy/MM/dd";
	public static final int open_result = 10000;
	public static String[] lessons = new String[]{"科目一","科目二","科目三","科目四"};
	public static String[] status = new String[]{"未分配","已分配","已完成","培训异常"};
	// 获取基类名
	public static final String TAG = BaseActivity.class.getSimpleName();

	// 句柄对象
	protected Handler mHandler = null;

	// 进度条
	protected ProgressDialog progressDialog = null;

	// 屏幕的大小
	private int width = 0, height = 0;

	/** 加载对话框 */
	public LoadingDialog_ui loading;

	// LayoutInflater mLayoutInflater = getLayoutInflater();
	private LayoutInflater mLayoutInflater;

	/** 获取参数的地方 */
	private Bundle params;

	/**
	 * 数据初始化
	 */
	private void beforeinit() {
		try {
			mLayoutInflater = getLayoutInflater();

			DisplayMetrics metric = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metric);

			width = metric.widthPixels; // 宽度（PX）
			height = metric.heightPixels; // 高度（PX）

		} catch (Exception e) {
		}
	}

	// 重构的父类方法
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		params = savedInstanceState;

		// getSize() 先获取屏幕大小
		beforeinit();

		// 初始化基本参数
		findViewById_Init();

		// 初始化界面
		initView();

		// 初始化数据
		initData();

	}

	// 恢复方法
	protected void onResume() {
		super.onResume();
	}

	/** 保存数据 */
	public void saveString(String name, String obj) {
		try {
			SharedPreferences.Editor sharedata = getSharedPreferences("data", 0)
					.edit();
			sharedata.putString(name, obj);
			sharedata.commit();
		} catch (Exception e) {
		}
	}

	/** 保存对象 */
	public String readString(String name) {
		String value = "";
		try {
			SharedPreferences sharedata = getSharedPreferences("data", 0);
			value = sharedata.getString(name, "");
		} catch (Exception e) {
		}
		return value;
	}

	/** 保存对象 */
	public void saveObject(String name, Object obj) {
		SharedPreferences preferences = getSharedPreferences("base64",
				MODE_PRIVATE);
		// 创建字节输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 创建对象输出流，并封装字节流
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			// 将对象写入字节流
			oos.writeObject(obj);
			// 将字节流编码成base64的字符窜
			String oAuth_Base64 = new String(Base64.encodeBase64(baos
					.toByteArray()));
			Editor editor = preferences.edit();
			editor.putString(name, oAuth_Base64);

			editor.commit();
		} catch (Exception e) {
		}
	}

	/** 获取一个对象 */
	public Object readObject(String name) {
		Object obj = null;
		try {
			SharedPreferences preferences = getSharedPreferences("base64",
					MODE_PRIVATE);
			String productBase64 = preferences.getString(name, null);
			if (productBase64 == null)
				return obj;
			// 读取字节
			byte[] base64 = Base64.decodeBase64(productBase64.getBytes());

			// 封装到字节流
			ByteArrayInputStream bais = new ByteArrayInputStream(base64);
			try {
				// 再次封装
				ObjectInputStream bis = new ObjectInputStream(bais);
				try {
					// 读取对象
					obj = bis.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
			}
		} catch (Exception e) {
		}

		return obj;
	}

	/** 检查输入框值 如果没有提示信息，并且返回输入框的值 提示信息 在组件的tag 属性中 */
	public String checkValue(EditText edit) {
		String value = "";
		if (edit == null)
			return value;
		try {
			value = edit.getText().toString().trim();
			if (value.length() == 0)
				DisPlay(edit.getHint().toString(), false);
		} catch (Exception e) {
		}
		return value;
	}

	/** 检查输入框值 如果没有提示信息，并且返回输入框的值 提示信息 在组件的tag 属性中 */
	public String checkValue(EditText edit, String info) {
		String value = "";
		if (edit == null)
			return value;
		try {
			value = edit.getText().toString().trim();
			if (value.length() == 0)
				DisPlay(info, false);
		} catch (Exception e) {
		}
		return value;
	}

	// 销毁方法
	protected void onDestroy() {
		super.onDestroy();
	}

	// 重新加载方法
	protected void onPause() {
		super.onPause();
	}

	// 重启方法
	protected void onRestart() {
		super.onRestart();
	}

	// 启动方法
	protected void onStart() {
		super.onStart();
	}

	// 停止方法
	protected void onStop() {
		
		super.onStop();
	}

	/*** 绑定控件id */
	protected abstract void findViewById_Init();

	/*** 初始化控件 */
	protected abstract void initView();

	/*** 初始化数据 */
	protected abstract void initData();
	

	/**
	 * 切换 中心位置
	 * 
	 * @param homeTabBook
	 */
	public void switchcenter(int homeTabBook) {

	}
	/**
	 * 页面跳转
	 * 
	 * @param homeTabBook
	 */
	public void switchActivity() {

	}
	/**
	 * 切换 中心位置
	 * 
	 * @param tag2
	 */
	public void switchcenter(Object tag2, Map<String, Object> param) {
             
	}

	/**
	 * 显示文本信息
	 * 
	 * @param content
	 * @param islong
	 */
	public void DisPlay(String content, boolean islong) {
		int longnum = Toast.LENGTH_SHORT;
		if (islong)
			longnum = Toast.LENGTH_LONG;
		Toast.makeText(this, content, longnum).show();
	}

	/** 加载进度条 */
	public void showProgressDialog() {

		progressDialog = new ProgressDialog(this);

		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(true);
		progressDialog.setMessage("请稍候，正在努力加载。。");
		progressDialog.show();
	}

	// 获得当前程序版本信息
	protected String getVersionName() {
		try {
			// 获取packagemanager的实例
			PackageManager packageManager = getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo = packageManager.getPackageInfo(
					getPackageName(), 0);
			return packInfo.versionName;
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * 获取基础URL
	 * 
	 * @param id
	 */
	public String getStringValue(int id) {
		String value = "";
		try {
			value = getString(id);
		} catch (Exception e) {
		}
		return value;
	}

	/**
	 * 打开 日历选择控件
	 * 
	 * @param view
	 */
	public void openCalendar(View view) {
		SimpleDateFormat df = new SimpleDateFormat(DATASTRUCT);// 设置日期格式
		String datenow = df.format(new Date());// new Date()为获取当前系统时间
		ComponentName componentName = new ComponentName(this, Calendar_ui.class);
		Intent intent = new Intent();
		intent.setComponent(componentName);
		intent.putExtra("default_cal", datenow);
		startActivityForResult(intent, open_result);
	}

	/**
	 * 获取基础最后的URL
	 * 
	 * @param id
	 */
	public String getOperateURL(int baseid, int id) {
		String value = "";
		try {
			value = getStringValue(baseid).trim();
			if (value.endsWith("\\") == false && value.endsWith("/") == false)
				value += "\\";
			value += getStringValue(id);
		} catch (Exception e) {
		}
		return value;
	}

	/**
	 * 检查网络是否连接
	 * 
	 * @return
	 */
	public final boolean checkConnectInternet() {
		ConnectivityManager con = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.isConnectedOrConnecting();
		boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.isConnectedOrConnecting();
		if (wifi | internet)
			return true;
		else
			return false;
	}

	/** 启动服务 */
	public void startservice() {
		try {
			PollingUtils.startPollingService(this, 1, TaskServer.class,
					TaskServer.ACTION);
		} catch (Exception e) {
		}
	}

	/** 启动服务 */
	public void stopservice() {
		try {
			PollingUtils.stopPollingService(this, TaskServer.class,
					TaskServer.ACTION);
		} catch (Exception e) {
		}
	}

	/**
	 * 获取参数数据
	 * 
	 * @param name
	 * @return
	 */
	public Object getParams(String name) {
		Object value = null;
		try {
			value = getIntent().getExtras().getString(name);
		} catch (Exception e) {
		}
		return value;
	}

	/*** getter **/
	public Handler getmHandler() {
		return mHandler;
	}

	public ProgressDialog getProgressDialog() {
		return progressDialog;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public LayoutInflater getmLayoutInflater() {
		return mLayoutInflater;
	}
	
	public void saveData(JSONObject json){
		try {
			saveString("user_id", json.get("stuId").toString());
			saveString("login_result", json.toString());
			saveString("roleId",json.getInt("roleId")+"");
			saveString("yuyuenum",json.optInt("lessonInfocount", 0)+"");
			saveString("testnum",json.optInt("Testcount",0)+"");
				saveString("coachId", json.optInt("coachId",0)+"");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	/**
	 * 将日期格式2014/11/28转成2014-11-28
	 * @param date
	 */
	@SuppressLint("SimpleDateFormat")
	public String switchDate(String date){
		String result = null ;
	    DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df1.parse(date);
            
            result = df2.format(dt1);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result ;
	}
	

    /**
     * 将字符串时间转成Date
     * @param time
     * @return
     */
	public Date getTimeDate(String time){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = null;
		try {
			dt = df.parse(time);
			Log.i(TAG, dt.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i(TAG, dt.toString());
		return dt ;				
	}	
	
}
