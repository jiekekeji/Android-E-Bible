package com.papa.bible.adapter;

import java.util.ArrayList;
import java.util.List;

import razerdp.basepopup.BasePopupWindow.OnDismissListener;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.papa.bible.R;
import com.papa.bible.bean.ChapterInfo;
import com.papa.bible.bean.VerseInfo;
import com.papa.bible.common.OptionType;
import com.papa.bible.db.AssetsDatabaseManager;
import com.papa.bible.db.TBBible;
import com.papa.bible.widget.NoteEditPopup;
import com.papa.bible.widget.OptionContentPopup;

public class ReadPagerAdapter extends PagerAdapter {
	private static final String TAG = ReadPagerAdapter.class.getSimpleName();
	public static int DISMISS_TYPE = 0;// 窗口关闭的类型，点击添加笔记关闭时为1

	private Activity activity;
	private LayoutInflater mInflater;
	private List<ChapterInfo> chapters;

	// 当前选中的页面的view
	private ListView gridView;
	private VerseListViewAdapter adapter;
	private List<VerseInfo> verseList;

	public ReadPagerAdapter(Activity activity, List<ChapterInfo> chapters) {
		this.activity = activity;
		mInflater = LayoutInflater.from(activity);
		this.chapters = chapters;

	}

	@Override
	public int getCount() {
		return chapters.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		Log.i(TAG, "instantiateItem=" + position);
		View itemView = mInflater.inflate(R.layout.item_read_pager_list, container, false);
		gridView = (ListView) itemView.findViewById(R.id.verse_list);
		ProgressBar bar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
		verseList = new ArrayList<VerseInfo>();
		adapter = new VerseListViewAdapter(activity, verseList);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnVerseItemClickListener(verseList, adapter));
		new LoadVerseTask(adapter, verseList, bar).execute(OptionType.CURRENT_TABLE, chapters.get(position).getBookID()
				+ "", chapters.get(position).getChapter());
		container.addView(itemView);
		return itemView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	// *** start PagerAdapter的notifyDataSetChanged无效解决方法***/
	private int mChildCount = 0;

	@Override
	public void notifyDataSetChanged() {
		mChildCount = getCount();
		super.notifyDataSetChanged();
	}

	@Override
	public int getItemPosition(Object object) {
		if (mChildCount > 0) {
			mChildCount--;
			return POSITION_NONE;
		}
		return super.getItemPosition(object);
	}

	// *** end PagerAdapter的notifyDataSetChanged无效解决方法***//
	/**
	 * 诗句被点击的监听
	 */
	class OnVerseItemClickListener implements OnItemClickListener {
		List<VerseInfo> verseList;
		VerseListViewAdapter adapter;

		public OnVerseItemClickListener(List<VerseInfo> verseList, VerseListViewAdapter adapter) {
			this.verseList = verseList;
			this.adapter = adapter;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			final VerseInfo info = verseList.get(position);
			showOptionContentPopup(info);
		}

		// 显示操作选项PopupWindow
		private void showOptionContentPopup(final VerseInfo info) {
			OptionContentPopup popup = new OptionContentPopup(activity, info);
			popup.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss() {
					Log.i(TAG, "onDismiss");
					if (DISMISS_TYPE == 0) {
						adapter.notifyDataSetChanged();
						return;
					}
					// 打开笔记编辑
					showNoteEditPopupWindow(info);
				}

			});
			popup.showPopupWindow();
		}

		// 显示笔记编辑PopupWindow
		private void showNoteEditPopupWindow(final VerseInfo info) {
			NoteEditPopup noteEditPopup = new NoteEditPopup(activity, info);
			noteEditPopup.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss() {
					adapter.notifyDataSetChanged();
				}
			});
			DISMISS_TYPE = 0;
			noteEditPopup.showPopupWindow();
		}
	}

	// 获取某章下的所有诗句
	class LoadVerseTask extends AsyncTask<String, Void, List<VerseInfo>> {
		VerseListViewAdapter adapter;
		List<VerseInfo> verseList;
		ProgressBar bar;

		public LoadVerseTask(VerseListViewAdapter adapter, List<VerseInfo> verseList, ProgressBar bar) {
			this.adapter = adapter;
			this.verseList = verseList;
			this.bar = bar;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (null != bar) {
				bar.setVisibility(View.VISIBLE);
			}
		}

		@Override
		protected List<VerseInfo> doInBackground(String... params) {
			AssetsDatabaseManager.initManager(activity);
			AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
			SQLiteDatabase db = mg.getDatabase("mxbible.db");
			List<VerseInfo> list = TBBible.queryRecordInChapter(db, params[0], params[1], params[2]);
			mg.closeAllDatabase();
			return list;
		}

		@Override
		protected void onPostExecute(List<VerseInfo> result) {
			super.onPostExecute(result);
			if (null != bar) {
				bar.setVisibility(View.GONE);
			}
			verseList.clear();
			verseList.addAll(result);
			this.adapter.notifyDataSetChanged();
			// chapters.addAll(result);
			// mAdapter.notifyDataSetChanged();
		}
	}

}
