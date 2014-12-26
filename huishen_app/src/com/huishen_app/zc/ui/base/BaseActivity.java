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
	public static String[] lessons = new String[]{"��Ŀһ","��Ŀ��","��Ŀ��","��Ŀ��"};
	public static String[] status = new String[]{"δ����","�ѷ���","�����","��ѵ�쳣"};
	// ��ȡ������
	public static final String TAG = BaseActivity.class.getSimpleName();

	// �������
	protected Handler mHandler = null;

	// ������
	protected ProgressDialog progressDialog = null;

	// ��Ļ�Ĵ�С
	private int width = 0, height = 0;

	/** ���ضԻ��� */
	public LoadingDialog_ui loading;

	// LayoutInflater mLayoutInflater = getLayoutInflater();
	private LayoutInflater mLayoutInflater;

	/** ��ȡ�����ĵط� */
	private Bundle params;

	/**
	 * ���ݳ�ʼ��
	 */
	private void beforeinit() {
		try {
			mLayoutInflater = getLayoutInflater();

			DisplayMetrics metric = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metric);

			width = metric.widthPixels; // ��ȣ�PX��
			height = metric.heightPixels; // �߶ȣ�PX��

		} catch (Exception e) {
		}
	}

	// �ع��ĸ��෽��
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		params = savedInstanceState;

		// getSize() �Ȼ�ȡ��Ļ��С
		beforeinit();

		// ��ʼ����������
		findViewById_Init();

		// ��ʼ������
		initView();

		// ��ʼ������
		initData();

	}

	// �ָ�����
	protected void onResume() {
		super.onResume();
	}

	/** �������� */
	public void saveString(String name, String obj) {
		try {
			SharedPreferences.Editor sharedata = getSharedPreferences("data", 0)
					.edit();
			sharedata.putString(name, obj);
			sharedata.commit();
		} catch (Exception e) {
		}
	}

	/** ������� */
	public String readString(String name) {
		String value = "";
		try {
			SharedPreferences sharedata = getSharedPreferences("data", 0);
			value = sharedata.getString(name, "");
		} catch (Exception e) {
		}
		return value;
	}

	/** ������� */
	public void saveObject(String name, Object obj) {
		SharedPreferences preferences = getSharedPreferences("base64",
				MODE_PRIVATE);
		// �����ֽ������
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// �������������������װ�ֽ���
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			// ������д���ֽ���
			oos.writeObject(obj);
			// ���ֽ��������base64���ַ���
			String oAuth_Base64 = new String(Base64.encodeBase64(baos
					.toByteArray()));
			Editor editor = preferences.edit();
			editor.putString(name, oAuth_Base64);

			editor.commit();
		} catch (Exception e) {
		}
	}

	/** ��ȡһ������ */
	public Object readObject(String name) {
		Object obj = null;
		try {
			SharedPreferences preferences = getSharedPreferences("base64",
					MODE_PRIVATE);
			String productBase64 = preferences.getString(name, null);
			if (productBase64 == null)
				return obj;
			// ��ȡ�ֽ�
			byte[] base64 = Base64.decodeBase64(productBase64.getBytes());

			// ��װ���ֽ���
			ByteArrayInputStream bais = new ByteArrayInputStream(base64);
			try {
				// �ٴη�װ
				ObjectInputStream bis = new ObjectInputStream(bais);
				try {
					// ��ȡ����
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

	/** ��������ֵ ���û����ʾ��Ϣ�����ҷ���������ֵ ��ʾ��Ϣ �������tag ������ */
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

	/** ��������ֵ ���û����ʾ��Ϣ�����ҷ���������ֵ ��ʾ��Ϣ �������tag ������ */
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

	// ���ٷ���
	protected void onDestroy() {
		super.onDestroy();
	}

	// ���¼��ط���
	protected void onPause() {
		super.onPause();
	}

	// ��������
	protected void onRestart() {
		super.onRestart();
	}

	// ��������
	protected void onStart() {
		super.onStart();
	}

	// ֹͣ����
	protected void onStop() {
		
		super.onStop();
	}

	/*** �󶨿ؼ�id */
	protected abstract void findViewById_Init();

	/*** ��ʼ���ؼ� */
	protected abstract void initView();

	/*** ��ʼ������ */
	protected abstract void initData();
	

	/**
	 * �л� ����λ��
	 * 
	 * @param homeTabBook
	 */
	public void switchcenter(int homeTabBook) {

	}
	/**
	 * ҳ����ת
	 * 
	 * @param homeTabBook
	 */
	public void switchActivity() {

	}
	/**
	 * �л� ����λ��
	 * 
	 * @param tag2
	 */
	public void switchcenter(Object tag2, Map<String, Object> param) {
             
	}

	/**
	 * ��ʾ�ı���Ϣ
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

	/** ���ؽ����� */
	public void showProgressDialog() {

		progressDialog = new ProgressDialog(this);

		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(true);
		progressDialog.setMessage("���Ժ�����Ŭ�����ء���");
		progressDialog.show();
	}

	// ��õ�ǰ����汾��Ϣ
	protected String getVersionName() {
		try {
			// ��ȡpackagemanager��ʵ��
			PackageManager packageManager = getPackageManager();
			// getPackageName()���㵱ǰ��İ�����0�����ǻ�ȡ�汾��Ϣ
			PackageInfo packInfo = packageManager.getPackageInfo(
					getPackageName(), 0);
			return packInfo.versionName;
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * ��ȡ����URL
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
	 * �� ����ѡ��ؼ�
	 * 
	 * @param view
	 */
	public void openCalendar(View view) {
		SimpleDateFormat df = new SimpleDateFormat(DATASTRUCT);// �������ڸ�ʽ
		String datenow = df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
		ComponentName componentName = new ComponentName(this, Calendar_ui.class);
		Intent intent = new Intent();
		intent.setComponent(componentName);
		intent.putExtra("default_cal", datenow);
		startActivityForResult(intent, open_result);
	}

	/**
	 * ��ȡ��������URL
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
	 * ��������Ƿ�����
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

	/** �������� */
	public void startservice() {
		try {
			PollingUtils.startPollingService(this, 1, TaskServer.class,
					TaskServer.ACTION);
		} catch (Exception e) {
		}
	}

	/** �������� */
	public void stopservice() {
		try {
			PollingUtils.stopPollingService(this, TaskServer.class,
					TaskServer.ACTION);
		} catch (Exception e) {
		}
	}

	/**
	 * ��ȡ��������
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
	 * �����ڸ�ʽ2014/11/28ת��2014-11-28
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
     * ���ַ���ʱ��ת��Date
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
