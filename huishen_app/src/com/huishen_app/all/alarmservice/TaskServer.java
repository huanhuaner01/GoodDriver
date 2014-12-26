package com.huishen_app.all.alarmservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class TaskServer extends Service {

	// 路径设置和一些固定信息
	public static final String ACTION = "com.huishen_app.all.alarmservice";
	private final IBinder mbinder = new Mybinder();

	// 计数器
	private static long count = 0;

	/**
	 * 统计当前的cout时间是否为一个 指定的时间间隔
	 * 
	 * @return 是否是一个固定的时间
	 */
	private static boolean isinteger(long countnow, int timesize) {
		// 即 这种情况每次都执行
		if (timesize <= 1)
			return true;
		// 即为指定的时间间隔
		if (Math.floor(countnow / (timesize + 0.0)) == Math.ceil(countnow
				/ (timesize + 0.0)))
			return true;
		return false;

	}

	/**
	 * 消息发送
	 * 
	 * @param type
	 * @param messinfo
	 * @param handler
	 */
	public static void sendmessage(int type, Object messinfo, Handler handler) {
		try {
			Message mess = new Message();
			// 消息机制：返回执行次数
			mess.what = type;
			mess.obj = messinfo.toString();
			if (handler != null)
				handler.sendMessage(mess);
		} catch (Exception e) {
		}
	}

	/**
	 * 开始任务
	 * 
	 * @param temp
	 */
	private static void task_work() {
		HanderListObject newtask = HanderListObject.getTask();
		if (newtask != null && newtask.getAutotask() != null) {
			newtask.getAutotask().start();
			Log.i("任务调度", "任务ID:" + newtask.getIdhandler());
		}

	}

	/** service 轮训方法 */
	public static void finishtask() {
		try {
			// 检查前台任务
			task_work();

			// 1秒钟检查一次
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
		// 调用后台信息
		try {
			count++;
			// 检查是否有新的任务 如果有就 新建线程处理

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

	// 定义接口 返回服务对象
	private class Mybinder extends Binder {
	}

}
