package com.huishen_app.zh.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateStruct {
	/* Ĭ�ϵĸ�ʽʱ�� */
	public static String defaultdatestruct;
	/* ʶ��ĸ�ʽʱ�� */
	public static List<String> datestruct;
	/* �Ƿ��Ѿ���ʼ�� */
	public static boolean init = false;
	/* Ĭ�Ϻ�ʶ���ʱ���ʽ */
	private static SimpleDateFormat recongise;
	private static DateFormat defaults;

	// ��ʼ�����в���
	private static void init() {
		// Ĭ��ʱ���ʽ
		defaultdatestruct = "yyyy-MM-dd";
		// ʶ��ʱ���ʽ
		datestruct = new ArrayList<String>();
		datestruct.add("yyyy-M-d");
		datestruct.add("yyyy-MM-dd");
		datestruct.add("yyyy:M:d");
		datestruct.add("yyyy:MM:dd");
		datestruct.add("yyyy��MM��dd��");
		datestruct.add("yyyy��M��d��");
		datestruct.add("yyyy/MM/dd");
		datestruct.add("yyyy/M/d");

		defaults = new SimpleDateFormat(defaultdatestruct);

		// �Ѿ���ʼ��
		init = true;
	}

	/**
	 * ��ʶ���ʱ���ʽ�� �Ƚ�����ʱ��� -1:��ʾС�� 0:��ʾ���� 1:��ʾ���� 2 ��ʾ�쳣 begin:��endǰ����-1 ���ں�����1
	 * 
	 * @param timebegin
	 *            ��ʼʱ��
	 * @param timeend
	 *            ����ʱ��
	 * @return -1,1,1,2
	 */
	public static int comp_time(String timebegin, String timeend) {
		int result = 2;
		if (init == false)
			init();
		try {
			String tb, te;
			tb = recon_date(timebegin);
			te = recon_date(timeend);
			// �쳣 ��ʶ��
			if (tb == null || te == null)
				return 2;
			// �����׼��
			result = tb.compareToIgnoreCase(te);
			result = result < 0 ? -1 : result;
			result = result > 0 ? 1 : result;
		} catch (Exception e) {
			return 2;
		}
		return result;
	}

	/**
	 * ʶ��һ��ʱ�䣬����Ĭ�ϸ�ʽ��ʱ�� ���ʶ�𲻳ɹ� ����null
	 * 
	 * @param datestr
	 * @return
	 */
	public static String recon_date(String datestr) {
		String default_date = null;
		if (init == false)
			init();
		// ����ʶ��ʱ���ʽ
		Date date = null;
		for (String temp : datestruct) {
			try {
				// ʶ��һ��ָ����ʱ��
				recongise = new SimpleDateFormat(temp);
				date = recongise.parse(datestr);
				default_date = defaults.format(date);
			} catch (Exception e) {
			}
		}
		return default_date;
	}

	/**
	 * ʶ��һ��ʱ�䣬����Ĭ�ϸ�ʽ��ʱ�� ���ʶ�𲻳ɹ� ����null
	 * 
	 * @param datestr
	 * @return
	 */
	public static String recon_date(Date d) {
		String default_date = null;
		if (init == false)
			init();
		// ����ʶ��ʱ���ʽ
		try {
			// ʶ��һ��ָ����ʱ��
			default_date = defaults.format(d);
		} catch (Exception e) {
		}
		return default_date;
	}

}
