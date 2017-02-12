package com.papa.bible.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBHelper extends SQLiteOpenHelper {

	/**
	 * 数据库名称
	 */
	private static final String DB_NAME = "inner";

	private static final int VERSION = 1;

	// 书签表名
	public static final String TB_BookMark = "tb_bm";

	// 笔记表名
	public static final String TB_NOTE = "tb_note";

	/***
	 * SQLiteOpenHelper
	 * 
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public DBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	/**
	 * 
	 * @param context
	 * @param name
	 * @param version
	 */
	public DBHelper(Context context, String name, int version) {
		this(context, name, null, version);
	}

	/**
	 * 
	 * @param context
	 */
	public DBHelper(Context context) {
		this(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + TB_BookMark + " (id integer primary key autoincrement," + "versionName text,"
				+ "bookID integer," + "bookName text, " + "chapter text," + " section text," + "verse text,"
				+ "bgcolor text," + "underline text," + "time  text," + "reserve02 text," + "reserve03 text,"
				+ "reserve04 text," + "reserve05 text," + "reserve06 text," + "reserve07 text," + "media text)");

		db.execSQL("create table " + TB_NOTE + " (id integer primary key autoincrement," + "versionName text,"
				+ " bookID integer," + "bookName text, " + "chapter text," + " section text," + "verse text,"
				+ "bgcolor text," + "underline text," + "reserve01 text," + "reserve02 text," + "reserve03 text,"
				+ "reserve04 text," + "reserve05 text," + "reserve06 text," + "reserve07 text," + "media text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

	}
}
