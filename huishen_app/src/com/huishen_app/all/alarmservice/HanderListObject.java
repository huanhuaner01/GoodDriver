package com.huishen_app.all.alarmservice;

import java.util.ArrayList;
import java.util.Date;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class HanderListObject extends Handler {

	// ����߳����� ��Ĭ�ϳ�ʱʱ��
	private static final int MAXTASK = 3, TIMEOUT_LEN = 6;

	// ��ʱ��what
	public static final int TIMEOUT = 1, CANCELTASK = 2;
	// ��̬����Ϣ����
	private static ArrayList<HanderListObject> handlerlist;
	// �����id
	private long idhandler;
	// �Ƿ��Ѿ���ִ��
	private boolean isrun;
	// ʲôʱ��ʼ��
	private Date begindate;
	// ����СС
	private Message msg;

	// timeout��ʱʱ��
	private int timeout;
	// ���崦��Ľӿ�
	private AutoTask autotask;

	// �Ƿ�Ҫ��ֻ��Ӧһ����Ϣ // �Ƿ��Ѿ����͵�һ����Ϣ
	private boolean siglemsg, issend;

	/**
	 * ���峬ʱ ��ʲô
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
		// ������һ��idhand
		long idhand = 1;
		for (int index = 0; index < handlerlist.size(); ++index) {
			HanderListObject temp = handlerlist.get(index);
			if (temp != null) {
				// ����нϴ��idhand
				if (idhand <= temp.getIdhandler())
					idhand = temp.getIdhandler() + 1;
			}
		}
		this.idhandler = idhand;
		// ��ӵ�������Ϣ
		siglemsg = false;
		issend = false;
	}

	/**
	 * ���峬ʱ ��ʲô
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
		// ������һ��idhand
		long idhand = 1;
		for (int index = 0; index < handlerlist.size(); ++index) {
			HanderListObject temp = handlerlist.get(index);
			if (temp != null) {
				// ����нϴ��idhand
				if (idhand <= temp.getIdhandler())
					idhand = temp.getIdhandler() + 1;
			}
		}
		this.idhandler = idhand;
		// ��ӵ�������Ϣ
		this.siglemsg = siglemsg;
		issend = false;
	}

	// ��ʼ��״̬
	public void initstate() {
		// ��ʼʱ��
		this.begindate = new Date();
		this.isrun = false;
	}

	// �������
	public void addtolisttask(AutoTask task) {
		if (task == null) {
			finishtask(this, 3, "û��ָ������");
			return;
		}
		if (task.gethandler() == null) {
			finishtask(this, 4, "ָ���������ָ�����ؾ����HanderListObject");
			return;
		}
		// ��ʼ��״̬
		initstate();
		setAutotask(task);
		Log.i("�������", "����ID:" + idhandler);

		if (handlerlist == null)
			handlerlist = new ArrayList<HanderListObject>();
		// ������ӳɹ�
		handlerlist.add(this);
	}

	/**
	 * �Ƴ�ĳ����Ϣ ͨ���ƶ���id
	 * 
	 * @param idhander
	 */
	public final void removehandler(long idhander) {
		if (handlerlist == null)
			handlerlist = new ArrayList<HanderListObject>();
		// �Ƴ�ĳ����Ϣ���
		for (HanderListObject temp : handlerlist)
			if (temp.getIdhandler() == idhander) {
				handlerlist.remove(temp);
				Log.i("�������", "����ID��" + idhander);
			}

	}

	/**
	 * ȡ������
	 * 
	 * @param handler
	 * @param what
	 * @param info
	 */
	public final void canceltask(HanderListObject handler) {
		if (handler == null)
			return;
		// �رն���
		try {
			long idhand = handler.getIdhandler();
			removehandler(idhand);
		} catch (Exception e) {
		}
		// ������Ϣ���������
		try {
			// �����ֻ������һ����Ϣ ���� ���û�з�����Ϣ
			if (siglemsg == false || issend == false) {
				Message msg = setmessage(HanderListObject.CANCELTASK, "ȡ������");
				if (msg != null)
					sendMessage(msg);
			}
		} catch (Exception e) {
		}
		// �Ѿ����͹�һ����Ϣ
		issend = true;
	}

	/**
	 * ������� Ĭ��what 10���� 10����Ϊϵͳ��Ϣ
	 * 
	 * @param handler
	 * @param what
	 * @param info
	 */
	public final void finishtask(HanderListObject handler, int what, Object info) {
		if (handler == null)
			return;
		// �رն���
		try {
			long idhand = handler.getIdhandler();
			removehandler(idhand);
		} catch (Exception e) {
		}
		// ������Ϣ���������
		try {
			// �����ֻ������һ����Ϣ ���� ���û�з�����Ϣ
			if (siglemsg == false || issend == false) {
				Message msg = setmessage(what, info);
				if (msg != null)
					sendMessage(msg);
			}
		} catch (Exception e) {
		}
		// �Ѿ����͹�һ����Ϣ
		issend = true;
	}

	// ������Ϣ
	private synchronized Message setmessage(int what, Object info) {
		msg = new Message();
		msg.what = what;
		msg.obj = info;
		return msg;
	}

	/** ����Ƿ�ʱ */
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

	/** �Ƿ�������� */
	public static final synchronized boolean hasTask() {
		if (handlerlist == null)
			handlerlist = new ArrayList<HanderListObject>();
		// ��鵱ǰ�ܹ����е�
		for (int index = 0; index < handlerlist.size(); ++index) {
			if (handlerlist.get(index).isrun == false)
				return true;
		}
		return false;
	}

	// ��ȡ��һ������
	public static final synchronized HanderListObject getTask() {
		if (handlerlist == null)
			handlerlist = new ArrayList<HanderListObject>();

		for (int index = 0, count = 0; handlerlist != null
				&& index < handlerlist.size(); ++index) {
			// ��Ч�Լ��ͳ�ʱ���
			if (handlerlist.get(index) == null
					|| handlerlist.get(index).istimeout()) {
				handlerlist.get(index).finishtask(handlerlist.get(index),
						TIMEOUT, "����ʱ");
				--index;
				continue;
			}
			// ���̲߳��ܳ���
			if (handlerlist.get(index).isrun == true) {
				++count;
				if (count > MAXTASK)
					return null;
			}
		}
		// ��鵱ǰ�ܹ����е�
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

	// �����Ƿ���
	public void resetisend() {
		issend = false;
	}

}
