package com.papa.bible.widget;

import razerdp.basepopup.BasePopupWindow;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.papa.bible.R;
import com.papa.bible.bean.VerseInfo;
import com.papa.bible.common.OptionType;
import com.papa.bible.db.AssetsDatabaseManager;
import com.papa.bible.db.TBBible;
import com.papa.bible.utils.MyProgressDialog;

/**
 * 章节选择PopupWindow
 * 
 * @author Administrator
 * 
 */
public class DeletePopup extends BasePopupWindow implements View.OnClickListener {

	protected static final String TAG = DeletePopup.class.getSimpleName();
	private Activity activity;
	private View popupView;
	private TextView delete_content_tv;
	private TextView delete_btn;
	private TextView delete_title_tv;
	private VerseInfo info;
	private int type;

	public DeletePopup(Activity context, VerseInfo info, int type) {
		super(context);
		this.info = info;
		this.info = info;
		this.activity = context;
		this.type = type;
		initView();

	}

	// 初始化试图
	private void initView() {
		if (null == popupView) {
			return;
		}
		delete_content_tv = (TextView) popupView.findViewById(R.id.delete_content_tv);
		delete_btn = (TextView) popupView.findViewById(R.id.delete_btn);
		delete_title_tv = (TextView) popupView.findViewById(R.id.delete_title_tv);
		delete_btn.setOnClickListener(this);
		initData();
	}

	// 初始化数据
	private void initData() {
		switch (type) {
		case 1:// 删除笔记
			// delete_title_tv.setText("Delete   " + "myNote");
			break;
		case 2:// 删除书签
			// delete_title_tv.setText("Delete   " + "bookMark");
			break;
		case 3:// 删除高亮
			// delete_title_tv.setText("Delete   " + "highLights");
			break;
		case 4:// 删除下划线
			// delete_title_tv.setText("Delete   " + "highLights");
			break;
		}
		String text = delete_title_tv.getText().toString();
		// Spannable span = new SpannableString(text);
		// span.setSpan(new AbsoluteSizeSpan(16), 0, 6,
		// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		// span.setSpan(new AbsoluteSizeSpan(10), 6, text.length(),
		// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		// delete_title_tv.setText(text);
		delete_content_tv.setText(info.getBookName() + " " + info.getChapter() + ":" + info.getSection());
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
		popupView = LayoutInflater.from(mContext).inflate(R.layout.popup_delete, null);
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
			new DeleteTask().execute(info);
			break;
		}
		dismiss();
	}

	/**
	 * 删除操作
	 * 
	 * @author Administrator
	 * 
	 */
	class DeleteTask extends AsyncTask<VerseInfo, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			MyProgressDialog.showDialog(activity);
		}

		@Override
		protected Boolean doInBackground(VerseInfo... params) {
			VerseInfo info = params[0];
			Boolean b = false;
			AssetsDatabaseManager.initManager(activity);
			AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
			SQLiteDatabase db = mg.getDatabase("mxbible.db");
			switch (type) {
			case 1:// 删除笔记
				b = TBBible.deleteNote(db, OptionType.CURRENT_TABLE, String.valueOf(info.getBookID()),
						info.getChapter(), info.getSection());
				break;
			case 2:// 删除书签
				b = TBBible.deleteBookMark(db, OptionType.CURRENT_TABLE, String.valueOf(info.getBookID()),
						info.getChapter(), info.getSection());
				break;
			case 3:// 删除高亮
				b = TBBible.deleteHighlights(db, OptionType.CURRENT_TABLE, String.valueOf(info.getBookID()),
						info.getChapter(), info.getSection());
				break;
			case 4:// 删除下划线
				b = TBBible.deleteUnderLine(db, OptionType.CURRENT_TABLE, String.valueOf(info.getBookID()),
						info.getChapter(), info.getSection());
				break;
			}
			mg.closeAllDatabase();
			return b;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			MyProgressDialog.closeDialog();
			if (!result) {
				return;
			}

			if (null != mDeleteStatusListener) {
				mDeleteStatusListener.onDeleteSuccess(info);
			}

		}
	}

	private DeleteStatusListener mDeleteStatusListener;

	public void setDeleteStatusListener(DeleteStatusListener mDeleteStatusListener) {
		this.mDeleteStatusListener = mDeleteStatusListener;
	}

	/**
	 * 删除状态的监听
	 * 
	 * @author Administrator
	 * 
	 */
	public interface DeleteStatusListener {
		public void onDeleteSuccess(VerseInfo info);
	}

}
