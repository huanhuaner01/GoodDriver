package com.huishen_app.all.alarmservice;

import java.util.Map;

public abstract class AutoTask extends Thread {

	// 定义返回操作
	private HanderListObject temp;
	private Map<String, Object> param;

	/** 操作url 编码方式 */
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

	// 完成任务
	public final void finishtask(int what, Object info) {
		if (temp != null)
			temp.finishtask(temp, what, info);
	}

	public abstract void run();

	// 返回对象
	public HanderListObject gethandler() {
		return temp;
	}

	// 获取信息
	public String getParams(String paramname) {
		if (paramname == null || param == null)
			return "";
		String params = (String) param.get(paramname.trim());
		return params == null ? "" : params;
	}

	// 获取信息
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
