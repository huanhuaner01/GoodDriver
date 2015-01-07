package com.huishen_app.zh.util;

import java.io.File;

import android.os.Environment;

public class SDPathCheck {

	/**
	 * ���·���Ƿ���� �����ھʹ���
	 * 
	 * @param path
	 * @return
	 */
	public static String checkpath(String path) {
		String filepath = "";

		if (path == null || path.trim().length() == 0)
			return filepath;
		try {
			File sd = Environment.getExternalStorageDirectory();
			// �������·��
			filepath = sd.getPath() + "/" + path;
			File file = new File(filepath);
			if (!file.exists())
				file.mkdirs();
		} catch (Exception e) {
		}
		return filepath;
	}

	/**
	 * �����ļ�
	 * 
	 * @param filename
	 * @return
	 */
	public static boolean createfile(String filename) {
		if (filename == null || filename.trim().length() == 0)
			return false;
		try {
			File file = new File(filename);
			if (!file.exists())
				file.createNewFile();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * ����֮ǰ��ɾ���ļ�
	 * 
	 * @param filename
	 * @return
	 */
	public static boolean createfile_deletebefore(String filename) {
		if (filename == null || filename.trim().length() == 0)
			return false;
		try {
			File file = new File(filename);
			if (!file.exists())
				file.createNewFile();
			else {
				file.delete();
				file.createNewFile();
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
