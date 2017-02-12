package com.papa.bible;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.papa.bible.common.OptionType;
import com.papa.bible.utils.SPUtils;

/**
 * 译本列表
 * 
 * @author Administrator
 * 
 */
public class MainActivity extends FragmentActivity {

	private static final String TAG = MainActivity.class.getSimpleName();

	private AudioManager audioManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		audioManager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
		test();

	}

	/**
	 * 继续阅读
	 * 
	 * @param view
	 */
	public void continue_read_ll(View view) {
		OptionType.CURRENT_TABLE = (String) SPUtils.get(this, "lastVersion", "niv");
		Intent intent = new Intent(this, ContentActivity.class);
		intent.putExtra("pagePosition", (String) SPUtils.get(this, "lastPager", ""));
		startActivity(intent);
	}

	/**
	 * 我的笔记
	 */
	public void my_note_ll(View view) {
		startActivity(new Intent(this, MyNoteActivity.class));
	}

	/**
	 * 我的书签
	 */
	public void bookmark_ll(View view) {
		startActivity(new Intent(this, BookMarkActivity.class));
	}

	/**
	 * 诗句搜索
	 * 
	 * @param view
	 */
	public void verse_search_ll(View view) {
		startActivity(new Intent(this, VerseSearchActivity.class));
	}

	/**
	 * 高亮句子列表
	 * 
	 * @param view
	 */
	public void highlight_verse_ll(View view) {
		startActivity(new Intent(this, HighLightsActivity.class));
	}

	/**
	 * 关于我们
	 * 
	 * @param view
	 */
	public void about_us_ll(View view) {
		startActivity(new Intent(this, AboutUsActivity.class));
	}

	/**
	 * 打开niv版本
	 * 
	 * @param view
	 */
	public void niv_ll(View view) {
		OptionType.CURRENT_TABLE = OptionType.NIV_TABLE;
		Intent intent = new Intent(this, ContentActivity.class);
		intent.putExtra("pagePosition", (String) SPUtils.get(this, OptionType.NIV_TABLE, ""));
		startActivity(intent);
	}

	/**
	 * 打开nkjv版本
	 * 
	 * @param view
	 */
	public void nkjv_ll(View view) {
		OptionType.CURRENT_TABLE = OptionType.NKJV_TABLE;
		Intent intent = new Intent(this, ContentActivity.class);
		intent.putExtra("pagePosition", (String) SPUtils.get(this, OptionType.NKJV_TABLE, ""));
		startActivity(intent);
	}

	/**
	 * 打开nvi译本
	 * 
	 * @param view
	 */
	public void nvi_ll(View view) {
		OptionType.CURRENT_TABLE = OptionType.NVI_TABLE;
		Intent intent = new Intent(this, ContentActivity.class);
		intent.putExtra("pagePosition", (String) SPUtils.get(this, OptionType.NVI_TABLE, ""));
		startActivity(intent);
	}

	/**
	 * 打开帮助中心
	 */
	public void help_center(View view) {
		startActivity(new Intent(this, HelpCenterActivity.class));
	}

	private void test() {
		Configuration config = getResources().getConfiguration();
		int smallestScreenWidth = config.smallestScreenWidthDp;
		Log.i(TAG, "smallest width : " + smallestScreenWidth);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			break;
		case KeyEvent.KEYCODE_VOLUME_UP:
			if (audioManager.isMusicActive()) {
				audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE,
						AudioManager.FX_FOCUS_NAVIGATION_UP);
			} else if (audioManager.isSpeakerphoneOn()) {
				audioManager.adjustStreamVolume(AudioManager.STREAM_VOICE_CALL, AudioManager.ADJUST_RAISE,
						AudioManager.FX_FOCUS_NAVIGATION_UP);
			} else {
				audioManager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_RAISE,
						AudioManager.FX_FOCUS_NAVIGATION_UP);
			}
			break;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			if (audioManager.isMusicActive()) {
				audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER,
						AudioManager.FX_FOCUS_NAVIGATION_UP);
			} else if (audioManager.isSpeakerphoneOn()) {
				audioManager.adjustStreamVolume(AudioManager.STREAM_VOICE_CALL, AudioManager.ADJUST_LOWER,
						AudioManager.FX_FOCUS_NAVIGATION_UP);
			} else {
				audioManager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_LOWER,
						AudioManager.FX_FOCUS_NAVIGATION_UP);
			}
			break;
		}
		return true;
	}

}
