package com.papa.bible.widget;

import razerdp.basepopup.BasePopupWindow;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.papa.bible.R;
import com.papa.bible.adapter.ReadPagerAdapter;
import com.papa.bible.bean.VerseInfo;
import com.papa.bible.common.OptionType;
import com.papa.bible.db.AssetsDatabaseManager;
import com.papa.bible.db.TBBible;
import com.papa.bible.fr.BookMarkFragment;
import com.papa.bible.fr.HighLinghtsFragment;

/**
 * 句子高亮，添加书签，笔记等操作
 * 
 * @author Administrator
 * 
 */
public class OptionContentPopup extends BasePopupWindow implements View.OnClickListener {

	protected static final String TAG = OptionContentPopup.class.getSimpleName();
	private Activity activity;
	private View popupView;

	// 被添加笔记等信息的诗句
	private VerseInfo info;
	// 相关试图
	private TextView title_tv;
	private Button yellow_btn;
	private Button green_btn;
	private Button red_btn;
	private Button blue_btn;
	private Button gray_btn;
	private Button Off_btn;
	private ImageView underline_iv;
	private ImageView add_bookmark_iv;
	private ImageView book_note_iv;

	public OptionContentPopup(Activity context, VerseInfo info) {
		super(context);
		this.activity = context;
		this.info = info;
		initView();

	}

	private void initView() {
		if (null == popupView) {
			return;
		}
		title_tv = (TextView) popupView.findViewById(R.id.title_tv);
		yellow_btn = (Button) popupView.findViewById(R.id.yellow_btn);
		green_btn = (Button) popupView.findViewById(R.id.green_btn);
		red_btn = (Button) popupView.findViewById(R.id.red_btn);
		blue_btn = (Button) popupView.findViewById(R.id.blue_btn);
		gray_btn = (Button) popupView.findViewById(R.id.gray_btn);
		Off_btn = (Button) popupView.findViewById(R.id.Off_btn);
		underline_iv = (ImageView) popupView.findViewById(R.id.underline_iv);
		add_bookmark_iv = (ImageView) popupView.findViewById(R.id.add_bookmark_iv);
		book_note_iv = (ImageView) popupView.findViewById(R.id.book_note_iv);
		// 设置监听
		title_tv.setOnClickListener(this);
		yellow_btn.setOnClickListener(this);
		green_btn.setOnClickListener(this);
		red_btn.setOnClickListener(this);
		blue_btn.setOnClickListener(this);
		gray_btn.setOnClickListener(this);
		Off_btn.setOnClickListener(this);
		underline_iv.setOnClickListener(this);
		add_bookmark_iv.setOnClickListener(this);
		book_note_iv.setOnClickListener(this);
		initData();
	}

	private void initData() {
		title_tv.setText(info.getBookName() + " " + info.getChapter() + ":" + info.getSection());
	}

	@Override
	protected Animation getShowAnimation() {
		return getDefaultScaleAnimation();
	}

	@Override
	protected View getClickToDismissView() {
		return popupView.findViewById(R.id.click_to_dismiss);
	}

	@Override
	public View getPopupView() {
		popupView = LayoutInflater.from(mContext).inflate(R.layout.popup_option_content, null);
		return popupView;
	}

	@Override
	public View getAnimaView() {
		return popupView.findViewById(R.id.popup_anima);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_tv:
			break;
		case R.id.yellow_btn:
			info.setBgcolor("#f4c600");
			new OptionTask().execute(1);
			break;
		case R.id.green_btn:
			info.setBgcolor("#90f297");
			new OptionTask().execute(2);
			break;
		case R.id.red_btn:
			info.setBgcolor("#e63bd9");
			new OptionTask().execute(3);
			break;
		case R.id.blue_btn:
			info.setBgcolor("#44c5f5");
			new OptionTask().execute(4);
			break;
		case R.id.gray_btn:
			info.setBgcolor("#356bb9");
			new OptionTask().execute(5);
			break;
		case R.id.underline_iv:
			info.setUnderline("1");
			new OptionTask().execute(6);
			break;
		case R.id.Off_btn:
			info.setUnderline("");
			info.setBgcolor("");
			new OptionTask().execute(7);
			break;
		case R.id.add_bookmark_iv:
			info.setReserve04("1");
			new OptionTask().execute(8);
			break;
		case R.id.book_note_iv:
			ReadPagerAdapter.DISMISS_TYPE = 1;
			break;
		}
		dismiss();
	}

	/**
	 * 在后台对句子做相应的操作，高亮下划线，书签等
	 * 
	 * @author Administrator
	 * 
	 */
	class OptionTask extends AsyncTask<Integer, Void, Integer> {

		@Override
		protected Integer doInBackground(Integer... params) {
			AssetsDatabaseManager.initManager(activity);
			AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
			SQLiteDatabase db = mg.getDatabase("mxbible.db");
			switch (params[0]) {
			case 1:// 黄色背景
				TBBible.updateHighlights(db, OptionType.CURRENT_TABLE, String.valueOf(info.getBookID()),
						info.getChapter(), info.getSection(), "#f4c600");
				break;
			case 2:// 绿色背景
				TBBible.updateHighlights(db, OptionType.CURRENT_TABLE, String.valueOf(info.getBookID()),
						info.getChapter(), info.getSection(), "#90f297");
				break;
			case 3:// 红色背景
				TBBible.updateHighlights(db, OptionType.CURRENT_TABLE, String.valueOf(info.getBookID()),
						info.getChapter(), info.getSection(), "#e63bd9");
				break;
			case 4:// 蓝色背景
				TBBible.updateHighlights(db, OptionType.CURRENT_TABLE, String.valueOf(info.getBookID()),
						info.getChapter(), info.getSection(), "#44c5f5");
				break;
			case 5:// 灰色背景
				TBBible.updateHighlights(db, OptionType.CURRENT_TABLE, String.valueOf(info.getBookID()),
						info.getChapter(), info.getSection(), "#356bb9");
				break;
			case 6:// 下划线
				TBBible.updateUnderLine(db, OptionType.CURRENT_TABLE, String.valueOf(info.getBookID()),
						info.getChapter(), info.getSection());
				break;
			case 7:// 清除下划线和高亮
				TBBible.deleteHighlightsAndUnderline(db, OptionType.CURRENT_TABLE, String.valueOf(info.getBookID()),
						info.getChapter(), info.getSection());
				break;
			case 8:// 添加书签
				TBBible.addBookMark(db, OptionType.CURRENT_TABLE, String.valueOf(info.getBookID()), info.getChapter(),
						info.getSection());
				break;
			}
			AssetsDatabaseManager.closeAllDatabase();
			return params[0];
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			switch (result) {
			case 8:// 添加书签
				activity.sendBroadcast(new Intent(BookMarkFragment.BROADCAST_ACTION));
				break;
			default:
				activity.sendBroadcast(new Intent(HighLinghtsFragment.BROADCAST_ACTION));
				break;
			}
		}
	}

}
