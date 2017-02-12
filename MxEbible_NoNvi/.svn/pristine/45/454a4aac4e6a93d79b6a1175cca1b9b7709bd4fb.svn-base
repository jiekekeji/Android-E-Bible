package com.papa.bible.utils;

import android.app.Activity;
import android.app.ProgressDialog;

public class MyProgressDialog {

	private static ProgressDialog dialog;

	/**
	 * 显示进度条
	 */
	public static void showDialog(Activity activity) {
		if (null == dialog) {
			dialog = new ProgressDialog(activity);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		}
		dialog.show();
	}

	/**
	 * 关闭
	 * 
	 * @param activity
	 */
	public static void closeDialog() {
		if (null != dialog) {
			dialog.dismiss();
			dialog = null;
		}
	}

}
