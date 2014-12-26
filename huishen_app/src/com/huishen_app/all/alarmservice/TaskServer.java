package com.huishen_app.all.alarmservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class TaskServer extends Service {

	// ·�����ú�һЩ�̶���Ϣ
	public static final String ACTION = "com.huishen_app.all.alarmservice";
	private final IBinder mbinder = new Mybinder();

	// ������
	private static long count = 0;

	/**
	 * ͳ�Ƶ�ǰ��coutʱ���Ƿ�Ϊһ�� ָ����ʱ����
	 * 
	 * @return �Ƿ���һ���̶���ʱ��
	 */
	private static boolean isinteger(long countnow, int timesize) {
		// �� �������ÿ�ζ�ִ��
		if (timesize <= 1)
			return true;
		// ��Ϊָ����ʱ����
		if (Math.floor(countnow / (timesize + 0.0)) == Math.ceil(countnow
				/ (timesize + 0.0)))
			return true;
		return false;

	}

	/**
	 * ��Ϣ����
	 * 
	 * @param type
	 * @param messinfo
	 * @param handler
	 */
	public static void sendmessage(int type, Object messinfo, Handler handler) {
		try {
			Message mess = new Message();
			// ��Ϣ���ƣ�����ִ�д���
			mess.what = type;
			mess.obj = messinfo.toString();
			if (handler != null)
				handler.sendMessage(mess);
		} catch (Exception e) {
		}
	}

	/**
	 * ��ʼ����
	 * 
	 * @param temp
	 */
	private static void task_work() {
		HanderListObject newtask = HanderListObject.getTask();
		if (newtask != null && newtask.getAutotask() != null) {
			newtask.getAutotask().start();
			Log.i("�������", "����ID:" + newtask.getIdhandler());
		}

	}

	/** service ��ѵ���� */
	public static void finishtask() {
		try {
			// ���ǰ̨����
			task_work();

			// 1���Ӽ��һ��
			if (isinteger(count, 1 * PollingUtils.seconds
					/ PollingUtils.sizetime)) {
			}
		} catch (Exception e) {
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mbinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		count = 0;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// ���ú�̨��Ϣ
		try {
			count++;
			// ����Ƿ����µ����� ����о� �½��̴߳���

			if (HanderListObject.hasTask()) {
				Thread clockThread = new Thread(new Runnable() {
					public void run() {
						try {
							finishtask();
						} catch (Exception e) {
						}
					}
				});
				clockThread.start();
			}
		} catch (Exception e) {
		}
	}

	// ����ӿ� ���ط������
	private class Mybinder extends Binder {
	}

}
