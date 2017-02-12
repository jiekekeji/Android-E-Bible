package com.papa.bible.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.papa.bible.bean.ChapterInfo;
import com.papa.bible.bean.VerseInfo;

/**
 * 
 * @author Administrator
 * 
 */
public class TBBible {

	/**
	 * 两个译本：表名分别为:niv、nkjv.
	 */

	private static final String TAG = TBBible.class.getSimpleName();

	/**
	 * 译本niv的表名
	 */
	public static final String TB_NIV = "niv";

	/**
	 * 译本nkjv的表名
	 */
	public static final String TB_NKJV = "nkjv";

	/**
	 * 查询所有的章 Select distinct bookID,bookName,chapter from niv;
	 * 
	 * @param db
	 * @param tbName
	 * @return
	 */
	public static List<ChapterInfo> queryChapters(SQLiteDatabase db, String tbName) {
		List<ChapterInfo> chapters = new ArrayList<ChapterInfo>();
		String[] columns = { "bookID,bookName,chapter,media" };
		Cursor cursor = db.query(true, tbName, columns, null, null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			ChapterInfo info = new ChapterInfo();
			info.setBookID(cursor.getInt(0));
			info.setBookName(cursor.getString(1));
			info.setChapter(cursor.getString(2));
			info.setMedia(cursor.getString(3));
			chapters.add(info);
		}
		cursor.close();
		return chapters;
	}

	/**
	 * 根据bookID,chapter查询该chapter下的所有诗句
	 * 
	 * @param db
	 * @param tbName
	 * @return
	 */
	public static List<VerseInfo> queryRecordInChapter(SQLiteDatabase db, String tbName, String bookID, String chapter) {
		List<VerseInfo> chapters = new ArrayList<VerseInfo>();
		String[] columns = { "bookID,bookName,chapter,section,verse,bgcolor,underline,reserve01,reserve02,"
				+ "reserve03,reserve04,reserve05,reserve06,reserve07,media" };
		String selection = "bookID=? AND chapter=?";
		String[] selectionArgs = { bookID, chapter };
		Cursor cursor = db.query(true, tbName, columns, selection, selectionArgs, null, null, null, null, null);
		cursor2List(chapters, cursor);
		cursor.close();
		return chapters;
	}

	/**
	 * 添加或更新笔记内容
	 * 
	 * @param db
	 * @param bookID
	 * @param chapter
	 * @param section
	 * @param note
	 * @return
	 */
	public static boolean updateNote(SQLiteDatabase db, String tbName, String bookID, String chapter, String section,
			String note) {
		ContentValues values = new ContentValues();
		values.put("reserve02", note);
		values.put("reserve01", "1");
		values.put("reserve03", System.currentTimeMillis());
		String whereClause = "bookID=? AND chapter=? AND section=?";
		String[] whereArgs = { bookID, chapter, section };
		int code = db.update(tbName, values, whereClause, whereArgs);
		if (code > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 删除笔记
	 * 
	 * @param db
	 * @param tbName
	 * @param bookID
	 * @param chapter
	 * @param section
	 * @param note
	 * @return
	 */
	public static boolean deleteNote(SQLiteDatabase db, String tbName, String bookID, String chapter, String section) {
		ContentValues values = new ContentValues();
		values.put("reserve02", "");
		values.put("reserve01", "0");
		values.put("reserve03", "");
		String whereClause = "bookID=? AND chapter=? AND section=?";
		String[] whereArgs = { bookID, chapter, section };
		int code = db.update(tbName, values, whereClause, whereArgs);
		if (code > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 查询笔记
	 * 
	 * @return
	 */
	public static List<VerseInfo> queryNotes(SQLiteDatabase db, String tbName, Integer page, Integer rows) {
		List<VerseInfo> verseInfos = new ArrayList<VerseInfo>();
		String[] columns = { "bookID,bookName,chapter,section,verse,bgcolor,underline,reserve01,reserve02,"
				+ "reserve03,reserve04,reserve05,reserve06,reserve07,media" };
		String selection = "reserve01=1";// reserve01为1，该句子有笔记
		String limit = page + "," + rows;
		String orderBy = "reserve03 desc";//
		Cursor cursor = db.query(true, tbName, columns, selection, null, null, null, orderBy, limit, null);
		cursor2List(verseInfos, cursor);
		cursor.close();
		return verseInfos;
	}

	/**
	 * 添加书签
	 * 
	 * @param db
	 * @param tbName
	 * @param bookID
	 * @param chapter
	 * @param section
	 * @param note
	 * @return
	 */
	public static boolean addBookMark(SQLiteDatabase db, String tbName, String bookID, String chapter, String section) {
		ContentValues values = new ContentValues();
		values.put("reserve04", "1");
		values.put("reserve05", System.currentTimeMillis());
		String whereClause = "bookID=? AND chapter=? AND section=?";
		String[] whereArgs = { bookID, chapter, section };
		int code = db.update(tbName, values, whereClause, whereArgs);
		if (code > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 删除书签
	 * 
	 * @param db
	 * @param tbName
	 * @param bookID
	 * @param chapter
	 * @param section
	 * @param note
	 * @return
	 */
	public static boolean deleteBookMark(SQLiteDatabase db, String tbName, String bookID, String chapter, String section) {
		ContentValues values = new ContentValues();
		values.put("reserve04", "");
		values.put("reserve05", "");
		String whereClause = "bookID=? AND chapter=? AND section=?";
		String[] whereArgs = { bookID, chapter, section };
		int code = db.update(tbName, values, whereClause, whereArgs);
		if (code > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 查询书签
	 * 
	 * @param db
	 * @param tbName
	 * @return
	 */
	public static List<VerseInfo> queryBookMark(SQLiteDatabase db, String tbName, Integer page, Integer rows) {
		List<VerseInfo> verseInfos = new ArrayList<VerseInfo>();
		String[] columns = { "bookID,bookName,chapter,section,verse,bgcolor,underline,reserve01,reserve02,"
				+ "reserve03,reserve04,reserve05,reserve06,reserve07,media" };
		String selection = "reserve04=1";// reserve04为1，该句子已加入书签
		String limit = page + "," + rows;
		String orderBy = "reserve05 desc";//
		Cursor cursor = db.query(true, tbName, columns, selection, null, null, null, orderBy, limit, null);
		cursor2List(verseInfos, cursor);
		cursor.close();
		return verseInfos;
	}

	/**
	 * 添加或更新背景色句子
	 * 
	 * @param db
	 * @param tbName
	 * @param bookID
	 * @param chapter
	 * @param section
	 * @return
	 */
	public static boolean updateHighlights(SQLiteDatabase db, String tbName, String bookID, String chapter,
			String section, String color) {
		ContentValues values = new ContentValues();
		values.put("bgcolor", color);
		values.put("reserve07", System.currentTimeMillis());// 高亮的时间
		String whereClause = "bookID=? AND chapter=? AND section=?";
		String[] whereArgs = { bookID, chapter, section };
		int code = db.update(tbName, values, whereClause, whereArgs);
		if (code > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 删除背景色
	 * 
	 * @param db
	 * @param tbName
	 * @param bookID
	 * @param chapter
	 * @param section
	 * @param note
	 * @return
	 */
	public static boolean deleteHighlights(SQLiteDatabase db, String tbName, String bookID, String chapter,
			String section) {
		ContentValues values = new ContentValues();
		values.put("bgcolor", "");
		values.put("reserve07", "");// 高亮的时间
		String whereClause = "bookID=? AND chapter=? AND section=?";
		String[] whereArgs = { bookID, chapter, section };
		int code = db.update(tbName, values, whereClause, whereArgs);
		if (code > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 删除颜色和下划线
	 * 
	 * @param db
	 * @param tbName
	 * @param bookID
	 * @param chapter
	 * @param section
	 * @return
	 */
	public static boolean deleteHighlightsAndUnderline(SQLiteDatabase db, String tbName, String bookID, String chapter,
			String section) {
		ContentValues values = new ContentValues();
		values.put("bgcolor", "");
		values.put("reserve07", "");// 高亮的时间
		values.put("underline", "");
		values.put("reserve06", "");
		String whereClause = "bookID=? AND chapter=? AND section=?";
		String[] whereArgs = { bookID, chapter, section };
		int code = db.update(tbName, values, whereClause, whereArgs);
		if (code > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 按照背景色查询句子
	 * 
	 * @param db
	 * @param tbName
	 * @param bookID
	 * @param chapter
	 * @param section
	 * @param color
	 * @return
	 */
	public static List<VerseInfo> queryHighlights(SQLiteDatabase db, String tbName, String color, Integer page,
			Integer rows) {
		List<VerseInfo> verseInfos = new ArrayList<VerseInfo>();
		String[] columns = { "bookID,bookName,chapter,section,verse,bgcolor,underline,reserve01,reserve02,"
				+ "reserve03,reserve04,reserve05,reserve06,reserve07,media" };
		String selection = "bgcolor=?";// reserve01为1，该句子有笔记
		String limit = page + "," + rows;
		String[] selectionArgs = { color };
		String orderBy = "reserve07 desc";//
		Cursor cursor = db.query(true, tbName, columns, selection, selectionArgs, null, null, orderBy, limit, null);
		cursor2List(verseInfos, cursor);
		cursor.close();
		return verseInfos;
	}

	/**
	 * 将查询到的数据放进List
	 * 
	 * @param chapters
	 * @param cursor
	 */
	private static void cursor2List(List<VerseInfo> chapters, Cursor cursor) {
		while (cursor.moveToNext()) {
			VerseInfo info = new VerseInfo();
			info.setBookID(cursor.getInt(0));
			info.setBookName(cursor.getString(1));
			info.setChapter(cursor.getString(2));
			info.setSection(cursor.getString(3));
			info.setVerse(cursor.getString(4));
			String bgcolor = cursor.getString(5);
			info.setBgcolor(bgcolor);
			if (!TextUtils.isEmpty(bgcolor)) {
				info.setReserve07(toDate(cursor.getString(13)));
			}

			info.setUnderline(cursor.getString(6));
			info.setReserve01(cursor.getString(7));
			info.setReserve02(cursor.getString(8));
			String noteTime = cursor.getString(9);
			if (!TextUtils.isEmpty(noteTime)) {
				info.setReserve03(toDate(noteTime));
			}

			info.setReserve04(cursor.getString(10));
			String bookMarkTime = cursor.getString(11);
			if (!TextUtils.isEmpty(bookMarkTime)) {
				info.setReserve05(toDate(bookMarkTime));
			}

			String underLineTime = cursor.getString(12);
			if (!TextUtils.isEmpty(underLineTime)) {
				info.setReserve06(toDate(underLineTime));
			}
			info.setMedia(cursor.getString(13));
			chapters.add(info);
		}
	}

	/**
	 * 添加下划线
	 * 
	 * @param db
	 * @param tbName
	 * @param bookID
	 * @param chapter
	 * @param section
	 * @return
	 */
	public static boolean updateUnderLine(SQLiteDatabase db, String tbName, String bookID, String chapter,
			String section) {
		ContentValues values = new ContentValues();
		values.put("underline", "1");
		values.put("reserve06", System.currentTimeMillis());// 添加下划线的时间
		String whereClause = "bookID=? AND chapter=? AND section=?";
		String[] whereArgs = { bookID, chapter, section };
		int code = db.update(tbName, values, whereClause, whereArgs);
		if (code > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 删除下划线
	 * 
	 * @param db
	 * @param tbName
	 * @param bookID
	 * @param chapter
	 * @param section
	 * @param note
	 * @return
	 */
	public static boolean deleteUnderLine(SQLiteDatabase db, String tbName, String bookID, String chapter,
			String section) {
		ContentValues values = new ContentValues();
		values.put("underline", "");
		values.put("reserve06", "");
		String whereClause = "bookID=? AND chapter=? AND section=?";
		String[] whereArgs = { bookID, chapter, section };
		int code = db.update(tbName, values, whereClause, whereArgs);
		if (code > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 查询下划线句子
	 * 
	 * @param db
	 * @param tbName
	 * @param bookID
	 * @param chapter
	 * @param section
	 * @param color
	 * @return
	 */
	public static List<VerseInfo> queryUnderline(SQLiteDatabase db, String tbName, Integer page, Integer rows) {
		List<VerseInfo> verseInfos = new ArrayList<VerseInfo>();
		String[] columns = { "bookID,bookName,chapter,section,verse,bgcolor,underline,reserve01,reserve02,"
				+ "reserve03,reserve04,reserve05,reserve06,reserve07,media" };
		String selection = "underline=1";// underline为1，该句子下划线
		String limit = page + "," + rows;
		String orderBy = "reserve06 desc";//
		Cursor cursor = db.query(true, tbName, columns, selection, null, null, null, orderBy, limit, null);
		cursor2List(verseInfos, cursor);
		cursor.close();
		return verseInfos;
	}

	/**
	 * 搜索诗句 select * from niv where verse like "%af%";
	 * 
	 * @param db
	 * @param tbName
	 * @return
	 */
	public static List<VerseInfo> searchVerse(SQLiteDatabase db, String tbName, String keyword, Integer page,
			Integer rows) {
		List<VerseInfo> verseInfos = new ArrayList<VerseInfo>();
		String[] columns = { "bookID,bookName,chapter,section,verse,bgcolor,underline,reserve01,reserve02,"
				+ "reserve03,reserve04,reserve05,reserve06,reserve07,media" };
		String selection = "verse like " + "'%" + keyword + "%'" + " limit " + page + "," + rows;
		Cursor cursor = db.query(tbName, columns, selection, null, null, null, null);
		cursor2List(verseInfos, cursor);
		cursor.close();
		return verseInfos;
	}

	/**
	 * 根据章节标题搜索诗句
	 * 
	 * @param db
	 * @param tbName
	 * @param keyword
	 * @param page
	 * @param rows
	 * @return
	 */
	public static List<VerseInfo> searchChapter(SQLiteDatabase db, String tbName, String bookName, String chapter,
			String section, Integer page, Integer rows) {
		List<VerseInfo> verseInfos = new ArrayList<VerseInfo>();
		String[] columns = { "bookID,bookName,chapter,section,verse,bgcolor,underline,reserve01,reserve02,"
				+ "reserve03,reserve04,reserve05,reserve06,reserve07,media" };
		String selection = "";
		bookName = bookName.replace(" ", "");
		chapter = chapter.replace(" ", "");
		section = section.replace(" ", "");

		if (TextUtils.isEmpty(chapter) && TextUtils.isEmpty(section)) {
			selection = "bookName like " + "'%" + bookName + "%'" + " limit " + page + "," + rows;
		} else if (!TextUtils.isEmpty(chapter) && !TextUtils.isEmpty(section)) {
			selection = "bookName like " + "'%" + bookName + "%'" + " AND chapter=" + chapter + " AND section="
					+ section + " limit " + page + "," + rows;
		} else if (!TextUtils.isEmpty(chapter) && TextUtils.isEmpty(section)) {
			selection = "bookName like " + "'%" + bookName + "%'" + " AND chapter=" + chapter + " limit " + page + ","
					+ rows;
		}
		Cursor cursor = db.query(tbName, columns, selection, null, null, null, null);
		cursor2List(verseInfos, cursor);
		cursor.close();
		return verseInfos;
	}

	/**
	 * 时间转换
	 */
	private static SimpleDateFormat format;

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static final String toDate(String millis) {
		if (null == format) {
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		return format.format(new Date(Long.valueOf(millis)));
	}
}
