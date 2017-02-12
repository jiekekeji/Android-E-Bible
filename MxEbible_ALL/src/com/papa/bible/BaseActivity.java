package com.papa.bible;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {

	/**
	 * 监听短按Home键
	 * 
	 * @author Administrator
	 * 
	 */
	class HomeWatcherReceiver extends BroadcastReceiver {
		private static final String SYSTEM_DIALOG_REASON_KEY = "reason";
		private static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

		@Override
		public void onReceive(Context context, Intent intent) {
			String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
			// 短按Home键
			if (SYSTEM_DIALOG_REASON_HOME_KEY.equals(reason)) {
				finish();
			}

		}

	}

	private HomeWatcherReceiver receiver;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		registerHomeWatcherReceiver();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unRegisterHomeWatcherReceiver();
	}

	/**
	 * 注册广播
	 */
	private void registerHomeWatcherReceiver() {
		IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
		receiver = new HomeWatcherReceiver();
		registerReceiver(receiver, homeFilter);
	}

	/**
	 * 取消注册广播
	 */
	private void unRegisterHomeWatcherReceiver() {
		if (null != receiver) {
			unregisterReceiver(receiver);
		}
	}
}
