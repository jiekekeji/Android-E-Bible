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
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.papa.bible.ContentActivity;
import com.papa.bible.R;
import com.papa.bible.adapter.HighLightsAdapter;
import com.papa.bible.bean.VerseInfo;
import com.papa.bible.common.OptionType;
import com.papa.bible.db.AssetsDatabaseManager;
import com.papa.bible.db.TBBible;
import com.papa.bible.widget.DeletePopup;
import com.papa.bible.widget.DeletePopup.DeleteStatusListener;

/**
 * 高亮的句子列表查看
 * 
 * @author Administrator
 * 
 */
public class HighLinghtsFragment extends BaseFragment implements OnItemClickListener, OnClickListener,
		OnItemLongClickListener {
	public static final String BROADCAST_ACTION = "com.mx.bile.fr.HighLinghtsFragment";
	private PullToRefreshListView mynote_lv;
	private HighLightsAdapter noteAdapter;
	private List<VerseInfo> verseList;
	private UpdateHighLinghtReceiver mReceiver;
	private String current_color = "1&#f4c600";
	private RelativeLayout bar_rl;
	private String currentColor;// 当前查询的颜色
	public static Integer currentType;// 当前查询的类型
	private Integer page = 0;// 默认起始页为1
	private Integer rows = 30;// 每次查询30条

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mReceiver = new UpdateHighLinghtReceiver();
		IntentFilter filter = new IntentFilter(BROADCAST_ACTION);
		activity.registerReceiver(mReceiver, filter);
	}

	@Override
	protected int getFragmentResLayout() {
		return R.layout.fr_highlights_list;
	}

	@Override
	protected void initView() {
		mynote_lv = (PullToRefreshListView) findViewById(R.id.mynote_lv);
		verseList = new ArrayList<VerseInfo>();
		noteAdapter = new HighLightsAdapter(activity, verseList);
		mynote_lv.setAdapter(noteAdapter);
		mynote_lv.setOnItemClickListener(this);
		mynote_lv.setMode(PullToRefreshBase.Mode.PULL_FROM_END);// 下拉刷新
		mynote_lv.setOnRefreshListener(mOnRefreshListener2);
		ListView listview = mynote_lv.getRefreshableView();
		listview.setOnItemClickListener(this);
		listview.setOnItemLongClickListener(this);

		findViewById(R.id.underline_iv).setOnClickListener(this);
		findViewById(R.id.gray_btn).setOnClickListener(this);
		findViewById(R.id.blue_btn).setOnClickListener(this);
		findViewById(R.id.red_btn).setOnClickListener(this);
		findViewById(R.id.green_btn).setOnClickListener(this);
		findViewById(R.id.yellow_btn).setOnClickListener(this);

		bar_rl = (RelativeLayout) findViewById(R.id.bar_rl);
		bar_rl.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.underline_iv:
			verseList.clear();
			page = 0;
			currentColor = "xxxxxx";
			currentType = 2;
			new QueryTask(2, null).execute();
			current_color = "2&xxxxxx";
			break;
		case R.id.gray_btn:
			verseList.clear();
			page = 0;
			currentColor = "#356bb9";
			currentType = 1;
			new QueryTask(1, "#356bb9").execute();
			current_color = "1&#356bb9";
			break;
		case R.id.blue_btn:
			verseList.clear();
			page = 0;
			currentColor = "#44c5f5";
			currentType = 1;
			new QueryTask(1, "#44c5f5").execute();
			current_color = "1&#44c5f5";
			break;
		case R.id.red_btn:
			verseList.clear();
			page = 0;
			currentColor = "#e63bd9";
			currentType = 1;
			new QueryTask(1, "#e63bd9").execute();
			current_color = "1&#e63bd9";
			break;
		case R.id.yellow_btn:
			verseList.clear();
			page = 0;
			currentColor = "#f4c600";
			currentType = 1;
			new QueryTask(1, "#f4c600").execute();
			current_color = "1&#f4c600";
			break;
		case R.id.green_btn:
			verseList.clear();
			page = 0;
			currentColor = "#90f297";
			currentType = 1;
			new QueryTask(1, "#90f297").execute();
			current_color = "1&#90f297";
			break;

		}
	}

	@Override
	protected void initData() {
		refreshData();
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
		DeletePopup popup;
		if (2 == Integer.valueOf(current_color.split("&")[0])) {
			popup = new DeletePopup(activity, verseList.get(position - 1), 4);
		} else {
			popup = new DeletePopup(activity, verseList.get(position - 1), 3);
		}
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

	@Override
	public void onDestroy() {
		super.onDestroy();
		activity.unregisterReceiver(mReceiver);
	}

	/**
	 * 刷新数据
	 */
	public void refreshData() {
		verseList.clear();
		page = 0;
		currentColor = "#f4c600";
		currentType = 1;
		new QueryTask(currentType, currentColor).execute();
	}

	OnRefreshListener2 mOnRefreshListener2 = new OnRefreshListener2<View>() {

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<View> refreshView) {

		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<View> refreshView) {
			new QueryTask(currentType, currentColor).execute();
		}
	};

	/**
	 * 接收更新数据的请求
	 * 
	 * @author Administrator
	 * 
	 */
	class UpdateHighLinghtReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (BROADCAST_ACTION.equals(intent.getAction())) {
				page = 0;
				verseList.clear();
				String[] option = current_color.split("&");
				new QueryTask(Integer.valueOf(option[0]), option[1]).execute();
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
		private int type;
		private String color;

		public QueryTask(int type, String color) {
			this.type = type;
			this.color = color;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			bar_rl.setVisibility(View.VISIBLE);
		}

		@Override
		protected List<VerseInfo> doInBackground(Void... params) {
			List<VerseInfo> list = null;
			AssetsDatabaseManager.initManager(activity);
			AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
			SQLiteDatabase db = mg.getDatabase("mxbible.db");
			switch (type) {
			case 1:// 查询高亮
				list = TBBible.queryHighlights(db, OptionType.CURRENT_TABLE, color, page, rows);
				break;
			case 2:// 查询下划线
				list = TBBible.queryUnderline(db, OptionType.CURRENT_TABLE, page, rows);
				break;
			}
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
			page = page + rows;
			mynote_lv.onRefreshComplete();
			verseList.addAll(result);
			noteAdapter.notifyDataSetChanged();
		}

	}

}
