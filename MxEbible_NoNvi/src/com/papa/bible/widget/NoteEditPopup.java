package com.papa.bible.widget;

import razerdp.basepopup.BasePopupWindow;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.papa.bible.R;
import com.papa.bible.bean.VerseInfo;
import com.papa.bible.common.OptionType;
import com.papa.bible.db.AssetsDatabaseManager;
import com.papa.bible.db.TBBible;
import com.papa.bible.fr.NoteFragment;

/**
 * 笔记编辑
 * 
 * @author Administrator
 * 
 */
public class NoteEditPopup extends BasePopupWindow implements View.OnClickListener {

	protected static final String TAG = NoteEditPopup.class.getSimpleName();
	private Activity activity;
	private View popupView;
	private VerseInfo info;

	// 视图
	private TextView title_tv;
	private EditText note_edit_et;
	private Button delete_btn;
	private Button done_btn;

	// 操作类型
	protected static final int UPDATE_TYPE = 1;
	protected static final int DELETE_TYPE = 2;

	public NoteEditPopup(Activity context, VerseInfo info) {
		super(context);
		this.activity = context;
		this.info = info;
		initView();

	}

	// 加载view
	private void initView() {
		if (null == popupView) {
			return;
		}
		title_tv = (TextView) popupView.findViewById(R.id.title_tv);
		note_edit_et = (EditText) popupView.findViewById(R.id.note_edit_et);
		delete_btn = (Button) popupView.findViewById(R.id.delete_btn);
		done_btn = (Button) popupView.findViewById(R.id.done_btn);
		delete_btn.setOnClickListener(this);
		done_btn.setOnClickListener(this);
		initData();
	}

	// 初始化view数据
	private void initData() {
		String content = "Edit Note   " + info.getBookName() + " " + info.getChapter() + ":" + info.getSection();
		title_tv.setText(content);
//		Spannable span = new SpannableString(title_tv.getText());
//		span.setSpan(new AbsoluteSizeSpan(18), 0, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//		title_tv.setText(span);
		// 设置笔记内容
		content = info.getReserve02();
		if (!TextUtils.isEmpty(content)) {
			note_edit_et.setText(content);
		}
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
		popupView = LayoutInflater.from(mContext).inflate(R.layout.popup_edit_note, null);
		return popupView;
	}

	@Override
	public View getAnimaView() {
		return popupView.findViewById(R.id.popup_anima);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.delete_btn:
			info.setReserve01("");
			info.setReserve02("");
			new OptionTask(info).execute(DELETE_TYPE);
			break;
		case R.id.done_btn:
			String note = note_edit_et.getText().toString();
			if (TextUtils.isEmpty(note)) {
				// 没有笔记
				info.setReserve01("");
				info.setReserve02("");
			} else {
				info.setReserve01("1");
				info.setReserve02(note);
			}
			new OptionTask(info).execute(UPDATE_TYPE);
			break;
		}
		dismiss();
	}

	/**
	 * 更新或删除笔记
	 * 
	 * @author Administrator
	 * 
	 */
	class OptionTask extends AsyncTask<Integer, Void, Void> {
		VerseInfo info;

		public OptionTask(VerseInfo info) {
			this.info = info;
		}

		@Override
		protected Void doInBackground(Integer... params) {
			AssetsDatabaseManager.initManager(activity);
			AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
			SQLiteDatabase db = mg.getDatabase("mxbible.db");
			switch (params[0]) {
			case UPDATE_TYPE:
				TBBible.updateNote(db, OptionType.CURRENT_TABLE, String.valueOf(info.getBookID()), info.getChapter(),
						info.getSection(), info.getReserve02());
				break;
			case DELETE_TYPE:
				TBBible.deleteNote(db, OptionType.CURRENT_TABLE, String.valueOf(info.getBookID()), info.getChapter(),
						info.getSection());
				break;
			}
			AssetsDatabaseManager.closeAllDatabase();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			activity.sendBroadcast(new Intent(NoteFragment.BROADCAST_ACTION));
		}
	}

}
