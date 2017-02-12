package com.papa.bible.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.papa.bible.common.OptionType;

public class MyUtils {

	/**
	 * 隐藏输入法
	 */
	public static void hideInputMethod(Activity activity) {
		((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity
				.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

	}

	/**
	 * 获取8位的十六进制颜色
	 * 
	 * @param color
	 * @return
	 */
	public static int getResColorID(String color) {
		if (color.equals("#f4c600")) {
			return 0xfff4c600;
		}
		if (color.equals("#90f297")) {
			return 0xff90f297;
		}
		if (color.equals("#e63bd9")) {
			return 0xffe63bd9;
		}
		if (color.equals("#44c5f5")) {
			return 0xff44c5f5;
		}
		if (color.equals("#356bb9")) {
			return 0xff356bb9;
		}
		return 0;
	}

	/**
	 * 将诗句转为HTML格式
	 * 
	 * @param num
	 *            序号
	 * @param numSize
	 *            序号的字体大小
	 * @param numColor
	 *            序号的颜色
	 * @param contentColor
	 *            内容的颜色
	 * @param contentSize
	 *            内容的大小
	 * @param contentU
	 *            内容的是否有下划线，为空没有下划线
	 * @param content
	 *            内容
	 * @return
	 */
	public static String toHtml(int num, int numSize, String numColor, String contentColor, int contentSize,
			String contentU, String content) {
		StringBuilder sb = new StringBuilder();
		// 序号部分
		sb.append("<span style=").append(
				"'font-size:" + numSize + ";text-align:center;color:" + numColor + ";font-weight:bold;'><b >" + num
						+ "</b></span>");
		// 内容部分
		sb.append("<span style=").append("'background:" + contentColor + ";font-size: " + contentSize + "'>");
		if (null != contentU) {
			sb.append("<u>").append(content).append("</u>");
			sb.append("</span>");
		} else {
			sb.append(content);
		}
		sb.append("</span>");
		return sb.toString();
	}

	/**
	 * 获取音频文件路径
	 * 
	 * @return
	 */
	public static String getMediaPath() {
		if (OptionType.CURRENT_TABLE.equals(OptionType.NIV_TABLE)) {
			return OptionType.NIV_MEDIA_FODER;
		} else if (OptionType.CURRENT_TABLE.equals(OptionType.NVI_TABLE)) {
			return OptionType.NVI_MEDIA_FODER;
		} else {
			return OptionType.NKJV_MEDIA_FODER;
		}
	}

	/**
	 * 设置当前应用屏幕亮度 0-255
	 * 
	 * @param paramInt
	 * @param activity
	 */
	public static void setScreenBrightness(int paramInt, Activity activity) {
		Window localWindow = activity.getWindow();
		WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
		float f = paramInt / 255.0F;
		localLayoutParams.screenBrightness = f;
		localWindow.setAttributes(localLayoutParams);
		Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, paramInt);
	}

	/**
	 * 获取当前应用屏幕亮度
	 * 
	 * @param activity
	 * @return
	 */
	public static int getScreenBrightness(Activity activity) {
		int value = 0;
		ContentResolver cr = activity.getContentResolver();
		try {
			value = Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS);
		} catch (SettingNotFoundException e) {
		}
		return value;
	}

	/**
	 * 判断输入的字符串是否符合规则
	 * 
	 * @param keyword
	 * @return
	 */
	public static boolean isStringOk(String keyword) {
		if (!keyword.contains(" ")) {
			return false;
		}
		if (!keyword.contains(":")) {
			return false;
		}
		if (keyword.lastIndexOf(" ") > keyword.indexOf(":")) {
			return false;
		}
		return true;
	}

	/**
	 * 获取关键字的书名
	 * 
	 * @return
	 */
	public static String getKeyWordBookName(String keyword) {
		String name = "";
		if (keyword.contains(" ")&&keyword.indexOf(" ")>0) {
			name = keyword.substring(0, keyword.indexOf(" "));
		} else {
			name = keyword;
		}
		return name;
	}

	/**
	 * 获取章
	 * 
	 * @param keyword
	 * @return
	 */
	public static String getKeyWordChapter(String keyword) {
		String chapter = "";
		if (keyword.contains(" ") && keyword.contains(":")&&keyword.indexOf(" ")<keyword.indexOf(":")) {
			chapter = keyword.substring(keyword.indexOf(" ") + 1, keyword.indexOf(":"));
		} else if (keyword.contains(" ")) {
			chapter = keyword.substring(keyword.indexOf(" "), keyword.length());
		}
		return chapter;
	}

	/**
	 * 获取节
	 * 
	 * @param keyword
	 * @return
	 */
	public static String getKeyWordSection(String keyword) {
		String section = "";
		if (keyword.contains(":")) {
			section = keyword.substring(keyword.indexOf(":") + 1, keyword.length());
		}
		return section;
	}

}
