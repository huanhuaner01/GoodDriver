package com.huishen_app.all.databaseoperate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * ���ݿ����������
 * 
 * @author ֣��
 */
public class HuishenDatabaseOperate extends SQLiteOpenHelper {
	
	private static final String TAG = "���ݲ�����־��";// ���Ա�ǩ
	public static final String DATABASE_NAME = "huishen_db";// ���ݿ���

	// ����ģʽ�� ��������
	public static HuishenDatabaseOperate dboper = null;

	// ִֻ��һ�εķ���
	public void onCreate(SQLiteDatabase db) {
		// ������
		createtableversion();
	}

	/** ���캯�� */
	private HuishenDatabaseOperate(Context context) {
		super(context, DATABASE_NAME, null, 1);

	}

	/**
	 * ��ʼ������
	 * 
	 * @param context
	 * @return
	 */
	public static HuishenDatabaseOperate getInstanse(Context context) {
		try {
			if (dboper == null)
				dboper = new HuishenDatabaseOperate(context);
			else if (dboper.getWritableDatabase() == null)
				dboper = new HuishenDatabaseOperate(context);
		} catch (Exception e) {
			dboper = new HuishenDatabaseOperate(context);
		}
		return dboper;
	}

	/**
	 * �����Ƿ����
	 * 
	 * @param tablename
	 *            :����
	 * @return
	 */
	public boolean isexit(String tablename) {
		try {
			// �����Ƿ����
			String[] args = { tablename };
			Cursor cur = getReadableDatabase().query("sqlite_master", null,
					" type='table' AND name=? ", args, null, null, null);

			int count = cur.getCount();
			cur.close();
			if (count == 0)
				return false;
			else
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * ����sql ������
	 * 
	 * @param sql
	 * @return
	 */
	public int getcount(String sql) {
		int count = 0;
		Cursor cur = null;
		// ��ѯ����
		try {
			cur = getReadableDatabase().rawQuery(sql, null);
			// ��ȡ�ܸ���
			if (cur != null)
				count = cur.getCount();
		} catch (Exception e) {
		} finally {
			if (cur != null)
				cur.close();
		}
		return count;
	}

	/** ��Ŷ�Ӧ��Version �����˵� �ñ�ֻ����һ��ֵ */
	public void createtableversion() {
		// �������ھͲ��½�
		if (isexit("version"))
			return;
		try {
			getWritableDatabase().execSQL(
					"create table version ( id INTEGER PRIMARY KEY autoincrement,"
							+ "info TEXT ,type TEXT);");
			Log.v(TAG, "�½�version�ɹ�");
		} catch (Exception e) {
			Log.v(TAG, "�½�versionʧ��" + e.getMessage());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

	@Override
	public synchronized void close() {
	}

}
