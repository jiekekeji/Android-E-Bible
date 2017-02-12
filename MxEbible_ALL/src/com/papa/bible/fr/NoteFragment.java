package com.papa.bible.fr;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.RelativeLayout;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.papa.bible.ContentActivity;
import com.papa.bible.R;
import com.papa.bible.adapter.MyNoteAdapter;
import com.papa.bible.bean.VerseInfo;
import com.papa.bible.common.OptionType;
import com.papa.bible.db.AssetsDatabaseManager;
import com.papa.bible.db.TBBible;
import com.papa.bible.widget.DeletePopup;
import com.papa.bible.widget.DeletePopup.DeleteStatusListener;

/**
 * 笔记表查看
 * 
 * @author Administrator
 * 
 */
public class NoteFragment extends BaseFragment implements OnItemClickListener, OnItemLongClickListener, OnClickListener {

	public static final String BROADCAST_ACTION = "com.mx.bile.fr.NoteFragment";
	private PullToRefreshListView mynote_lv;
	private MyNoteAdapter noteAdapter;
	private List<VerseInfo> verseList;
	private UpdateNoteReceiver mReceiver;
	private RelativeLayout bar_rl;
	private Integer page = 0;// 默认起始页为1
	private Integer rows = 30;// 每次查询30条

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		IntentFilter filter = new IntentFilter(BROADCAST_ACTION);
		mReceiver = new UpdateNoteReceiver();
		activity.registerReceiver(mReceiver, filter);
	}

	@Override
	protected int getFragmentResLayout() {
		return R.layout.fr_mynote_list;
	}

	@Override
	protected void initView() {
		mynote_lv = (PullToRefreshListView) findViewById(R.id.mynote_lv);
		verseList = new ArrayList<VerseInfo>();
		noteAdapter = new MyNoteAdapter(activity, verseList);
		mynote_lv.setAdapter(noteAdapter);
		mynote_lv.setOnItemClickListener(this);
		mynote_lv.setMode(PullToRefreshBase.Mode.PULL_FROM_END);// 下拉刷新
		mynote_lv.getRefreshableView().setOnItemLongClickListener(this);
		mynote_lv.setOnRefreshListener(mOnRefreshListener2);
		bar_rl = (RelativeLayout) findViewById(R.id.bar_rl);
		bar_rl.setOnClickListener(this);
	}

	@Override
	protected void initData() {
		refreshData();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		activity.unregisterReceiver(mReceiver);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		VerseInfo info = verseList.get(position - 1);
		Intent intent = new Intent(activity, ContentActivity.class);
		intent.putExtra("pagePosition", info.getBookID() + "#" + info.getChapter());
		startActivity(intent);
		activity.finish();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		DeletePopup popup = new DeletePopup(activity, verseList.get(position - 1), 1);
		popup.setDeleteStatusListener(new DeleteStatusListener() {

			@Override
			public void onDeleteSuccess(VerseInfo info) {
				verseList.remove(info);
				noteAdapter.notifyDataSetChanged();
				activity.sendBroadcast(new Intent(ReaderFragment.BROADCAST_ACTION));
			}
		});
		popup.showPopupWindow();
		return true;
	}

	/**
	 * 刷新数据
	 */
	public void refreshData() {
		verseList.clear();
		page = 0;
		new QueryTask().execute();
	}

	@Override
	public void onClick(View v) {

	}

	OnRefreshListener2 mOnRefreshListener2 = new OnRefreshListener2<View>() {

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<View> refreshView) {

		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<View> refreshView) {
			new QueryTask().execute();
		}
	};

	/**
	 * 接收更新数据的请求
	 * 
	 * @author Administrator
	 * 
	 */
	class UpdateNoteReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (BROADCAST_ACTION.equals(intent.getAction())) {
				refreshData();
			}
		}
	}

	/**
	 * 查询我的笔记
	 * 
	 * @author Administrator
	 * 
	 */
	class QueryTask extends AsyncTask<Void, Void, List<VerseInfo>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			bar_rl.setVisibility(View.VISIBLE);
		}

		@Override
		protected List<VerseInfo> doInBackground(Void... params) {
			AssetsDatabaseManager.initManager(activity);
			AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
			SQLiteDatabase db = mg.getDatabase("mxbible.db");
			List<VerseInfo> list = TBBible.queryNotes(db, OptionType.CURRENT_TABLE, page, rows);
			mg.closeAllDatabase();
			return list;
		}

		@Override
		protected void onPostExecute(List<VerseInfo> result) {
			super.onPostExecute(result);
			bar_rl.setVisibility(View.GONE);
			if (null == result) {
				return;
			}
			mynote_lv.onRefreshComplete();
			page = page + rows;
			verseList.addAll(result);
			noteAdapter.notifyDataSetChanged();
			bar_rl.setVisibility(View.GONE);
		}

	}

}
