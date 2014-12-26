package com.huishen_app.all.alarmservice;

import java.util.ArrayList;
import java.util.Date;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class HanderListObject extends Handler {

	// 最大线程数量 和默认超时时间
	private static final int MAXTASK = 3, TIMEOUT_LEN = 6;

	// 超时的what
	public static final int TIMEOUT = 1, CANCELTASK = 2;
	// 静态的消息队列
	private static ArrayList<HanderListObject> handlerlist;
	// 分配的id
	private long idhandler;
	// 是否已经在执行
	private boolean isrun;
	// 什么时候开始的
	private Date begindate;
	// 集成小小
	private Message msg;

	// timeout超时时间
	private int timeout;
	// 定义处理的接口
	private AutoTask autotask;

	// 是否要求只响应一次消息 // 是否已经发送第一条消息
	private boolean siglemsg, issend;

	/**
	 * 定义超时 做什么
	 * 
	 * @param timeout
	 * @param dowhat
	 */
	public HanderListObject(int timeout) {
		super();
		if (handlerlist == null)
			handlerlist = new ArrayList<HanderListObject>();
		this.timeout = timeout <= 1 ? TIMEOUT_LEN : timeout;
		this.isrun = false;
		// 计算下一个idhand
		long idhand = 1;
		for (int index = 0; index < handlerlist.size(); ++index) {
			HanderListObject temp = handlerlist.get(index);
			if (temp != null) {
				// 如果有较大的idhand
				if (idhand <= temp.getIdhandler())
					idhand = temp.getIdhandler() + 1;
			}
		}
		this.idhandler = idhand;
		// 添加到最后的消息
		siglemsg = false;
		issend = false;
	}

	/**
	 * 定义超时 做什么
	 * 
	 * @param timeout
	 * @param dowhat
	 */
	public HanderListObject(int timeout, boolean siglemsg) {
		super();
		if (handlerlist == null)
			handlerlist = new ArrayList<HanderListObject>();
		this.timeout = timeout <= 1 ? TIMEOUT_LEN : timeout;
		this.isrun = false;
		// 计算下一个idhand
		long idhand = 1;
		for (int index = 0; index < handlerlist.size(); ++index) {
			HanderListObject temp = handlerlist.get(index);
			if (temp != null) {
				// 如果有较大的idhand
				if (idhand <= temp.getIdhandler())
					idhand = temp.getIdhandler() + 1;
			}
		}
		this.idhandler = idhand;
		// 添加到最后的消息
		this.siglemsg = siglemsg;
		issend = false;
	}

	// 初始化状态
	public void initstate() {
		// 开始时间
		this.begindate = new Date();
		this.isrun = false;
	}

	// 添加任务
	public void addtolisttask(AutoTask task) {
		if (task == null) {
			finishtask(this, 3, "没有指定任务");
			return;
		}
		if (task.gethandler() == null) {
			finishtask(this, 4, "指定任务必须指定返回句柄：HanderListObject");
			return;
		}
		// 初始化状态
		initstate();
		setAutotask(task);
		Log.i("任务添加", "任务ID:" + idhandler);

		if (handlerlist == null)
			handlerlist = new ArrayList<HanderListObject>();
		// 任务添加成功
		handlerlist.add(this);
	}

	/**
	 * 移除某条消息 通过制定的id
	 * 
	 * @param idhander
	 */
	public final void removehandler(long idhander) {
		if (handlerlist == null)
			handlerlist = new ArrayList<HanderListObject>();
		// 移除某条消息句柄
		for (HanderListObject temp : handlerlist)
			if (temp.getIdhandler() == idhander) {
				handlerlist.remove(temp);
				Log.i("任务完成", "任务ID：" + idhander);
			}

	}

	/**
	 * 取消任务
	 * 
	 * @param handler
	 * @param what
	 * @param info
	 */
	public final void canceltask(HanderListObject handler) {
		if (handler == null)
			return;
		// 关闭队列
		try {
			long idhand = handler.getIdhandler();
			removehandler(idhand);
		} catch (Exception e) {
		}
		// 发送消息给请求对象
		try {
			// 如果是只允许发送一条消息 或者 如果没有发送消息
			if (siglemsg == false || issend == false) {
				Message msg = setmessage(HanderListObject.CANCELTASK, "取消任务");
				if (msg != null)
					sendMessage(msg);
			}
		} catch (Exception e) {
		}
		// 已经发送过一条消息
		issend = true;
	}

	/**
	 * 任务完成 默认what 10以上 10以下为系统消息
	 * 
	 * @param handler
	 * @param what
	 * @param info
	 */
	public final void finishtask(HanderListObject handler, int what, Object info) {
		if (handler == null)
			return;
		// 关闭队列
		try {
			long idhand = handler.getIdhandler();
			removehandler(idhand);
		} catch (Exception e) {
		}
		// 发送消息给请求对象
		try {
			// 如果是只允许发送一条消息 或者 如果没有发送消息
			if (siglemsg == false || issend == false) {
				Message msg = setmessage(what, info);
				if (msg != null)
					sendMessage(msg);
			}
		} catch (Exception e) {
		}
		// 已经发送过一条消息
		issend = true;
	}

	// 设置消息
	private synchronized Message setmessage(int what, Object info) {
		msg = new Message();
		msg.what = what;
		msg.obj = info;
		return msg;
	}

	/** 检查是否超时 */
	private boolean istimeout() {
		if (begindate == null)
			return true;
		try {
			Date now = new Date();
			long timetemp = now.getTime() - begindate.getTime();
			if (timetemp >= timeout * 1000)
				return true;
		} catch (Exception e) {
		}
		return false;
	}

	/** 是否具有任务 */
	public static final synchronized boolean hasTask() {
		if (handlerlist == null)
			handlerlist = new ArrayList<HanderListObject>();
		// 检查当前总共运行的
		for (int index = 0; index < handlerlist.size(); ++index) {
			if (handlerlist.get(index).isrun == false)
				return true;
		}
		return false;
	}

	// 获取下一个任务
	public static final synchronized HanderListObject getTask() {
		if (handlerlist == null)
			handlerlist = new ArrayList<HanderListObject>();

		for (int index = 0, count = 0; handlerlist != null
				&& index < handlerlist.size(); ++index) {
			// 有效性检查和超时检查
			if (handlerlist.get(index) == null
					|| handlerlist.get(index).istimeout()) {
				handlerlist.get(index).finishtask(handlerlist.get(index),
						TIMEOUT, "任务超时");
				--index;
				continue;
			}
			// 总线程不能超过
			if (handlerlist.get(index).isrun == true) {
				++count;
				if (count > MAXTASK)
					return null;
			}
		}
		// 检查当前总共运行的
		for (int index = 0; handlerlist != null && index < handlerlist.size(); ++index) {
			if (handlerlist.get(index).isrun == false) {
				handlerlist.get(index).isrun = true;
				return handlerlist.get(index);
			}
		}
		return null;
	}

	public long getIdhandler() {
		return idhandler;
	}

	public AutoTask getAutotask() {
		return autotask;
	}

	public void setAutotask(AutoTask autotask) {
		this.autotask = autotask;
	}

	// 重置是否发送
	public void resetisend() {
		issend = false;
	}

}
