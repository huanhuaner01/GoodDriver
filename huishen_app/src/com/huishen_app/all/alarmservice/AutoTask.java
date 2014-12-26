package com.huishen_app.all.alarmservice;

import java.util.Map;

public abstract class AutoTask extends Thread {

	// ���巵�ز���
	private HanderListObject temp;
	private Map<String, Object> param;

	/** ����url ���뷽ʽ */
	private String operateurl, encoding;

	public AutoTask(HanderListObject temp, Map<String, Object> param) {
		if (temp == null)
			return;
		this.temp = temp;
		this.param = param;

		if (param != null) {
			operateurl = getParams("operateurl");
			encoding = getParams("encoding");
		}
	}

	// �������
	public final void finishtask(int what, Object info) {
		if (temp != null)
			temp.finishtask(temp, what, info);
	}

	public abstract void run();

	// ���ض���
	public HanderListObject gethandler() {
		return temp;
	}

	// ��ȡ��Ϣ
	public String getParams(String paramname) {
		if (paramname == null || param == null)
			return "";
		String params = (String) param.get(paramname.trim());
		return params == null ? "" : params;
	}

	// ��ȡ��Ϣ
	public Object getObject(String paramname) {
		if (paramname == null || param == null)
			return "";
		return param.get(paramname.trim());
	}

	public String getOperateurl() {
		return operateurl;
	}

	public String getEncoding() {
		return encoding;
	}

}
