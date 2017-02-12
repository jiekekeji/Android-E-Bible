package com.papa.bible.utils;

import android.app.Activity;

public class ConfigureUtils {

	/**
	 * 序号的大小
	 */
	private static int NUM_FONT_SIZE = -1;
	/**
	 * 文本字体大小
	 */
	private static int CONTENT_FONT_SIZE = -1;

	/**
	 * 设置序号的字体大小
	 * 
	 * @param activity
	 * @param size
	 */
	public static void setNumFontSize(Activity activity, int size) {
		NUM_FONT_SIZE = size;
		SPUtils.put(activity, "num_font_size", size);
	}

	/**
	 * 设置文本的字体大小
	 * 
	 * @param activity
	 * @param size
	 */
	public static void setContentFontSize(Activity activity, int size) {
		CONTENT_FONT_SIZE = size;
		SPUtils.put(activity, "content_font_size", size);
	}

	/**
	 * 获取序号的字体大小
	 * 
	 * @param activity
	 * @param size
	 * @return
	 */
	public static int getNumFontSize(Activity activity) {
		if (NUM_FONT_SIZE == -1) {
			NUM_FONT_SIZE = (Integer) SPUtils.get(activity, "num_font_size", 30);
		}
		return NUM_FONT_SIZE;
	}

	/**
	 * 获取内容的字体大小
	 * 
	 * @param activity
	 * @param size
	 * @return
	 */
	public static int getContentFontSize(Activity activity) {
		if (CONTENT_FONT_SIZE == -1) {
			CONTENT_FONT_SIZE = (Integer) SPUtils.get(activity, "content_font_size", 24);
		}
		return CONTENT_FONT_SIZE;
	}

}
