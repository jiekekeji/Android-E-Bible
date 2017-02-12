package com.papa.bible;

import android.app.Service;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.papa.bible.R;
import com.papa.bible.fr.BookMarkFragment;
import com.papa.bible.fr.HighLinghtsFragment;
import com.papa.bible.fr.NoteFragment;
import com.papa.bible.fr.ReaderFragment;

/**
 * 阅读列表
 * 
 * @author Administrator
 * 
 */
public class ContentActivity extends BaseActivity implements OnClickListener {
	private DrawerLayout mDrawerLayout;
	public ReaderFragment readerFragment;
	private String pagePosition = null;
	// 三个tab
	private View mynote_ll;
	private TextView mynote_tv;
	private View mynote_vw;
	private View highlights_ll;
	private TextView highlights_tv;
	private View highlights_vv;
	private View bookmark_ll;
	private TextView bookmark_tv;
	private View bookmark_vw;
	// fragment的父布局
	private RelativeLayout noteFragment_rl;
	private RelativeLayout highLinghtsFragment_rl;
	private RelativeLayout bookMarkFragment_rl;
	// 切换fragment
	private NoteFragment noteFragment;
	private HighLinghtsFragment highLinghtsFragment;
	private BookMarkFragment bookMarkFragment;
	private AudioManager audioManager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_layout);
		initData();
		initDrawerLayout();
		initFragments();
		initView();

	}

	private void initData() {
		pagePosition = getIntent().getStringExtra("pagePosition");
		audioManager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
	}

	private void initFragments() {
		FragmentManager manager = getSupportFragmentManager();
		readerFragment = (ReaderFragment) manager.findFragmentByTag("ReaderFragment");
		readerFragment.initPager = pagePosition;
		// 侧滑菜单
		noteFragment = (NoteFragment) manager.findFragmentByTag("NoteFragment");
		highLinghtsFragment = (HighLinghtsFragment) manager.findFragmentByTag("HighLinghtsFragment");
		bookMarkFragment = (BookMarkFragment) manager.findFragmentByTag("BookMarkFragment");
	}

	private void initDrawerLayout() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		mDrawerLayout.setScrimColor(Color.TRANSPARENT);
		findViewById(R.id.drawer_menu).setOnClickListener(this);
	}

	private void initView() {
		mynote_ll = findViewById(R.id.mynote_ll);
		highlights_ll = findViewById(R.id.highlights_ll);
		bookmark_ll = findViewById(R.id.bookmark_ll);
		mynote_ll.setOnClickListener(this);
		highlights_ll.setOnClickListener(this);
		bookmark_ll.setOnClickListener(this);
		mynote_tv = (TextView) findViewById(R.id.mynote_tv);
		mynote_vw = findViewById(R.id.mynote_vw);
		highlights_tv = (TextView) findViewById(R.id.highlights_tv);
		highlights_vv = findViewById(R.id.highlights_vv);
		bookmark_tv = (TextView) findViewById(R.id.bookmark_tv);
		bookmark_vw = findViewById(R.id.bookmark_vw);
		//
		noteFragment_rl = (RelativeLayout) findViewById(R.id.noteFragment_rl);
		highLinghtsFragment_rl = (RelativeLayout) findViewById(R.id.highLinghtsFragment_rl);
		bookMarkFragment_rl = (RelativeLayout) findViewById(R.id.bookMarkFragment_rl);
		resetView();
		setCheckView(highlights_tv, highlights_vv, highLinghtsFragment_rl);
	}

	private void resetView() {
		mynote_tv.setTextSize(14);
		mynote_tv.setTextColor(R.color.black);
		mynote_vw.setVisibility(View.GONE);
		// 重置mynote的tab
		highlights_tv.setTextSize(14);
		highlights_tv.setTextColor(R.color.black);
		highlights_vv.setVisibility(View.GONE);
		// 重置bookmark的tab
		bookmark_tv.setTextSize(14);
		bookmark_tv.setTextColor(R.color.black);
		bookmark_vw.setVisibility(View.GONE);
		// 隐藏fragment
		noteFragment_rl.setVisibility(View.GONE);
		highLinghtsFragment_rl.setVisibility(View.GONE);
		bookMarkFragment_rl.setVisibility(View.GONE);
	}

	private void setCheckView(TextView tv, View view, RelativeLayout fragmentView) {
		tv.setTextSize(16);
		tv.setTextColor(R.color.verse_red);
		view.setVisibility(View.VISIBLE);
		fragmentView.setVisibility(View.VISIBLE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mynote_ll:
			resetView();
			setCheckView(mynote_tv, mynote_vw, noteFragment_rl);
			break;
		case R.id.highlights_ll:
			resetView();
			setCheckView(highlights_tv, highlights_vv, highLinghtsFragment_rl);
			break;
		case R.id.bookmark_ll:
			resetView();
			setCheckView(bookmark_tv, bookmark_vw, bookMarkFragment_rl);
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP:
			audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE,
					AudioManager.FX_FOCUS_NAVIGATION_UP);
			break;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER,
					AudioManager.FX_FOCUS_NAVIGATION_UP);
			break;
		case KeyEvent.KEYCODE_BACK:
			finish();
			break;
		}
		return true;
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
	}
}
