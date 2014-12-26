package com.huishen_app.all.databaseoperate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 数据库操作工具类
 * 
 * @author 郑川
 */
public class HuishenDatabaseOperate extends SQLiteOpenHelper {
	
	private static final String TAG = "数据操作标志：";// 调试标签
	public static final String DATABASE_NAME = "huishen_db";// 数据库名

	// 单例模式的 操作对象
	public static HuishenDatabaseOperate dboper = null;

	// 只执行一次的方法
	public void onCreate(SQLiteDatabase db) {
		// 创建表
		createtableversion();
	}

	/** 构造函数 */
	private HuishenDatabaseOperate(Context context) {
		super(context, DATABASE_NAME, null, 1);

	}

	/**
	 * 初始化操作
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
	 * 检查表是否存在
	 * 
	 * @param tablename
	 *            :表名
	 * @return
	 */
	public boolean isexit(String tablename) {
		try {
			// 检查表是否存在
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
	 * 返回sql 语句个数
	 * 
	 * @param sql
	 * @return
	 */
	public int getcount(String sql) {
		int count = 0;
		Cursor cur = null;
		// 查询数据
		try {
			cur = getReadableDatabase().rawQuery(sql, null);
			// 获取总个数
			if (cur != null)
				count = cur.getCount();
		} catch (Exception e) {
		} finally {
			if (cur != null)
				cur.close();
		}
		return count;
	}

	/** 存放对应的Version 下拉菜单 该表只保存一个值 */
	public void createtableversion() {
		// 如果表存在就不新建
		if (isexit("version"))
			return;
		try {
			getWritableDatabase().execSQL(
					"create table version ( id INTEGER PRIMARY KEY autoincrement,"
							+ "info TEXT ,type TEXT);");
			Log.v(TAG, "新建version成功");
		} catch (Exception e) {
			Log.v(TAG, "新建version失败" + e.getMessage());
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
