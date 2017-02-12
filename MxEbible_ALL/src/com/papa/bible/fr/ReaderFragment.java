package com.papa.bible.fr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import razerdp.basepopup.BasePopupWindow.OnDismissListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.papa.bible.R;
import com.papa.bible.adapter.ReadPagerAdapter;
import com.papa.bible.bean.ChapterInfo;
import com.papa.bible.common.OptionType;
import com.papa.bible.db.AssetsDatabaseManager;
import com.papa.bible.db.TBBible;
import com.papa.bible.utils.ConfigureUtils;
import com.papa.bible.utils.MyUtils;
import com.papa.bible.utils.SPUtils;
import com.papa.bible.utils.ToastUtils;
import com.papa.bible.widget.BrightnessPopup;
import com.papa.bible.widget.ChapterSelPopup;
import com.papa.bible.widget.ChapterSelPopup.OnChapterSelectedListener;
import com.papa.bible.widget.FontSizePopup;
import com.papa.bible.widget.FontSizePopup.OnFontSizeSelected;

public class ReaderFragment extends BaseFragment implements OnClickListener, OnPageChangeListener {

	private static final String TAG = ReaderFragment.class.getSimpleName();
	public static final String BROADCAST_ACTION = "com.mx.bile.fr.ReaderFragment";
	private TextView chapter_title_tv;
	private TextView version_name_tv;
	private ViewPager viewpager;
	private ReadPagerAdapter mAdapter;
	private List<ChapterInfo> chapters;
	private UpdateVerseReceiver mReceiver;
	// 标志位
	private int current_pager = 0;
	// initPager的格式bookID#chapter;
	public String initPager = null;
	// 快进和快退的幅度 3秒
	private static int seekPositon = 3000;

	// 试图
	private ImageButton font_size_iv;
	private ImageButton pre_chapter_btn;
	private ImageButton next_chapter_btn;
	private ImageButton back_home_btn;
	private ImageButton brightness_ibtn;
	// 字体大小调整popup
	private FontSizePopup sizePopup;
	// 音频播放相关
	private MediaPlayer mPlayer;
	private ImageButton back_play_ibtn;
	private ImageButton start_play_ibtn;
	private ImageButton front_play_ibtn;
	private Integer playPositon = 0;// 音频播放的位置

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mReceiver = new UpdateVerseReceiver();
		IntentFilter filter = new IntentFilter(BROADCAST_ACTION);
		activity.registerReceiver(mReceiver, filter);
		initPlayer();
	}

	@Override
	protected int getFragmentResLayout() {
		return R.layout.fr_content_reader;
	}

	@Override
	protected void initView() {
		chapter_title_tv = (TextView) findViewById(R.id.chapter_title_tv);
		chapter_title_tv.setOnClickListener(this);
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		chapters = new ArrayList<ChapterInfo>();
		mAdapter = new ReadPagerAdapter(activity, chapters);
		viewpager.setAdapter(mAdapter);
		viewpager.setOnPageChangeListener(this);

		font_size_iv = (ImageButton) findViewById(R.id.font_size_iv);
		font_size_iv.setOnClickListener(this);
		pre_chapter_btn = (ImageButton) findViewById(R.id.pre_chapter_btn);
		pre_chapter_btn.setOnClickListener(this);
		next_chapter_btn = (ImageButton) findViewById(R.id.next_chapter_btn);
		next_chapter_btn.setOnClickListener(this);
		back_home_btn = (ImageButton) findViewById(R.id.back_home_btn);
		back_home_btn.setOnClickListener(this);
		version_name_tv = (TextView) findViewById(R.id.version_name_tv);

		back_play_ibtn = (ImageButton) findViewById(R.id.back_play_ibtn);
		back_play_ibtn.setOnClickListener(this);
		start_play_ibtn = (ImageButton) findViewById(R.id.start_play_ibtn);
		start_play_ibtn.setOnClickListener(this);
		front_play_ibtn = (ImageButton) findViewById(R.id.front_play_ibtn);
		front_play_ibtn.setOnClickListener(this);
		findViewById(R.id.brightness_ibtn).setOnClickListener(this);

	}

	@Override
	protected void initData() {
		if (OptionType.CURRENT_TABLE.equals(OptionType.NIV_TABLE)) {
			version_name_tv.setText("NIV");
		} else if (OptionType.CURRENT_TABLE.equals(OptionType.NVI_TABLE)) {
			version_name_tv.setText("NVI");
		} else {
			version_name_tv.setText("NKJV");
		}
		new LoadDataTask().execute(OptionType.CURRENT_TABLE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.chapter_title_tv:
			showChapterSelPopup();

			break;
		case R.id.font_size_iv:
			showFontSizePopup();
			break;
		case R.id.pre_chapter_btn:
			if (current_pager == 0) {
				return;
			}
			current_pager = current_pager - 1;
			viewpager.setCurrentItem(current_pager);
			break;
		case R.id.next_chapter_btn:
			if (current_pager == chapters.size() - 1) {
				return;
			}
			current_pager = current_pager + 1;
			viewpager.setCurrentItem(current_pager);
			break;
		case R.id.back_home_btn:
			activity.finish();
			break;
		case R.id.back_play_ibtn:
			if (mPlayer.isPlaying()) {
				if (0 > mPlayer.getCurrentPosition() - seekPositon) {
					// 快退小于零
					return;
				}
				mPlayer.seekTo(mPlayer.getCurrentPosition() - seekPositon);
			}
			break;
		case R.id.start_play_ibtn:
			play(MyUtils.getMediaPath() + chapters.get(current_pager).getMedia());
			break;
		case R.id.front_play_ibtn:
			if (mPlayer.isPlaying()) {
				if (mPlayer.getDuration() < mPlayer.getCurrentPosition() + seekPositon) {
					// 如果超出了播放的最大长度
					return;
				}
				mPlayer.seekTo(mPlayer.getCurrentPosition() + seekPositon);
			}
			break;
		case R.id.brightness_ibtn:
			new BrightnessPopup(activity).showPopupWindow();
			break;
		}
	}

	/**
	 * 显示章节选择对话框
	 */
	private void showChapterSelPopup() {
		ChapterSelPopup chapterSelPopup = new ChapterSelPopup(activity, chapters);
		chapterSelPopup.setOnChapterSelectedListener(new OnChapterSelectedListener() {

			@Override
			public void onChapterSelected(String initPager) {
				ReaderFragment.this.initPager = initPager;
				setInitPager(chapters);
				viewpager.setCurrentItem(current_pager);
			}
		});
		chapterSelPopup.showPopupWindow();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		activity.unregisterReceiver(mReceiver);
		saveLastReadPager();
		destroyMediaPlayer();
	}

	/**
	 * 保存最后阅读的页面
	 */
	private void saveLastReadPager() {
		int position = viewpager.getCurrentItem();
		ChapterInfo info = chapters.get(position);
		// bookID#chapter
		SPUtils.put(activity, "lastPager", info.getBookID() + "#" + info.getChapter());
		SPUtils.put(activity, "lastVersion", OptionType.CURRENT_TABLE);
		// 保存当前版本的当前阅读进度
		SPUtils.put(activity, OptionType.CURRENT_TABLE, info.getBookID() + "#" + info.getChapter());
	}

	/**
	 * 刷新阅读列表
	 * 
	 * @author Administrator
	 * 
	 */
	class UpdateVerseReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (BROADCAST_ACTION.equals(intent.getAction())) {
				mAdapter.notifyDataSetChanged();
			}
		}
	}

	/**
	 * 显示字体代销调整
	 */
	private void showFontSizePopup() {
		if (null == sizePopup) {
			sizePopup = new FontSizePopup(activity);
		}
		sizePopup.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {

			}
		});
		sizePopup.setFontSizeSelected(new OnFontSizeSelected() {

			@Override
			public void onFontSizeSelected(int size) {
				ConfigureUtils.setContentFontSize(activity, size);
				mAdapter.notifyDataSetChanged();
			}
		});
		sizePopup.showPopupWindow();
	}

	/*** start --pager滑动的监听 ***/
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		Log.i(TAG, "被选中的pager=" + position);
		current_pager = position;
		ChapterInfo info = chapters.get(position);
		chapter_title_tv.setText(info.getBookName() + " " + info.getChapter());
		// 播放选中页面的音频
		if (mPlayer.isPlaying()) {
			mPlayer.pause();
			playPositon = 0;
			play(MyUtils.getMediaPath() + chapters.get(current_pager).getMedia());
		}
	}

	/*** end --pager滑动的监听 ***/

	// start 与音频播放相关
	private void play(String path) {
		try {
			if (mPlayer.isPlaying()) {// 暂停
				playPositon = mPlayer.getCurrentPosition();
				mPlayer.pause();
				start_play_ibtn.setImageResource(R.drawable.start_play);
			} else if (playPositon != 0) {// 继续播放
				mPlayer.seekTo(playPositon);
				start_play_ibtn.setImageResource(R.drawable.start_pause);
			} else {// 重新播放
				mPlayer.reset();
				mPlayer.setDataSource(path);
				mPlayer.prepareAsync();
				start_play_ibtn.setImageResource(R.drawable.start_pause);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化播放器
	 */
	private void initPlayer() {
		mPlayer = new MediaPlayer();
		mPlayer.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer player) {
				// 准备完成，开始播放
				player.start();
			}
		});
		mPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer player) {
				Log.i(TAG, "onCompletion");
				if (current_pager == chapters.size() - 1) {
					start_play_ibtn.setImageResource(R.drawable.start_play);
				} else {// 切换到下一个
					current_pager = current_pager + 1;
					viewpager.setCurrentItem(current_pager);
					if (mPlayer.isPlaying()) {
						mPlayer.pause();
						playPositon = 0;
						play(MyUtils.getMediaPath() + chapters.get(current_pager).getMedia());
					} else {
						playPositon = 0;
						play(MyUtils.getMediaPath() + chapters.get(current_pager).getMedia());
					}
				}

			}
		});
		mPlayer.setOnErrorListener(new OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer player, int arg1, int arg2) {
				Log.i(TAG, "onError " + "arg1=" + arg1 + " arg2=" + arg2);
				start_play_ibtn.setImageResource(R.drawable.start_play);
				ToastUtils.showToast(activity, "Play Error");
				return false;
			}

		});
		mPlayer.setOnSeekCompleteListener(new OnSeekCompleteListener() {

			@Override
			public void onSeekComplete(MediaPlayer player) {
				Log.i(TAG, "onSeekComplete");
				player.start();
			}

		});
	}

	/**
	 * 销毁MediaPlayer
	 */
	private void destroyMediaPlayer() {
		if (null == mPlayer) {
			return;
		}
		mPlayer.stop();
		mPlayer.release();
		mPlayer = null;
	}

	// end 与音频播放相关

	// 加载所有的章节
	class LoadDataTask extends AsyncTask<String, Void, List<ChapterInfo>> {

		@Override
		protected List<ChapterInfo> doInBackground(String... params) {
			AssetsDatabaseManager.initManager(activity);
			AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
			SQLiteDatabase db = mg.getDatabase("mxbible.db");
			List<ChapterInfo> list = TBBible.queryChapters(db, params[0]);
			setInitPager(list);
			mg.closeAllDatabase();
			return list;
		}

		@Override
		protected void onPostExecute(List<ChapterInfo> result) {
			super.onPostExecute(result);
			chapters.addAll(result);
			mAdapter.notifyDataSetChanged();
			viewpager.setCurrentItem(current_pager);

		}

	}

	// 设置初始显示的页面
	private void setInitPager(List<ChapterInfo> list) {
		if (TextUtils.isEmpty(initPager)) {
			return;
		}
		// initPager的格式bookID#chapter;
		String[] pagers = initPager.split("#");
		for (int i = 0; i < list.size(); i++) {
			ChapterInfo info = list.get(i);
			if (Integer.valueOf(info.getBookID()) == Integer.valueOf((pagers[0]))
					&& Integer.valueOf(info.getChapter()) == Integer.valueOf((pagers[1]))) {
				current_pager = i;
				return;
			}
		}
	}

}
