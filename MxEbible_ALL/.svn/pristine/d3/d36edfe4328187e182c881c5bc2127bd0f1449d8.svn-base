package com.papa.bible.fr;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.papa.bible.ContentActivity;
import com.papa.bible.R;
import com.papa.bible.adapter.VerseSearchAdapter;
import com.papa.bible.bean.VerseInfo;
import com.papa.bible.common.OptionType;
import com.papa.bible.db.AssetsDatabaseManager;
import com.papa.bible.db.TBBible;
import com.papa.bible.utils.MyUtils;
import com.papa.bible.utils.ToastUtils;

/**
 * 笔记表查看
 * 
 * @author Administrator
 * 
 */
public class VerseSearchFragment extends BaseFragment implements OnItemClickListener, OnClickListener {

	private static final int TYPE_CHAPTER = 1;
	private static final int TYPE_VERSE = 2;
	private int currenType = TYPE_VERSE;

	private PullToRefreshListView mynote_lv;
	private VerseSearchAdapter noteAdapter;
	private List<VerseInfo> verseList;
	private ImageButton back_home_btn;
	private Spinner spinner;
	private RadioButton rb_chapter;
	private RadioButton rb_verse;
	private RadioGroup seacherRg;

	private EditText search_word_et;
	private ImageButton search_ivbtn;
	private boolean isFirstComeIn = true;
	private RelativeLayout barRl;
	private Integer page = 0;// 默认起始页为1
	private Integer rows = 30;// 每次查询30条

	@Override
	protected int getFragmentResLayout() {
		return R.layout.fr_verse_search_list;
	}

	@Override
	protected void initView() {
		mynote_lv = (PullToRefreshListView) findViewById(R.id.mynote_lv);
		verseList = new ArrayList<VerseInfo>();
		noteAdapter = new VerseSearchAdapter(activity, verseList);
		mynote_lv.setAdapter(noteAdapter);
		mynote_lv.setOnItemClickListener(this);
		mynote_lv.setMode(PullToRefreshBase.Mode.PULL_FROM_END);// 下拉刷新
		mynote_lv.setOnRefreshListener(mOnRefreshListener2);

		back_home_btn = (ImageButton) findViewById(R.id.back_home_btn);
		back_home_btn.setOnClickListener(this);
		spinner = (Spinner) findViewById(R.id.spinner);
		spinner.setOnItemSelectedListener(new VersionSelectedListener());
		search_word_et = (EditText) findViewById(R.id.search_word_et);
		search_word_et.setOnKeyListener(mOnKeyListener);
		search_ivbtn = (ImageButton) findViewById(R.id.search_ivbtn);
		search_ivbtn.setOnClickListener(this);
		barRl = (RelativeLayout) findViewById(R.id.bar_rl);
		barRl.setOnClickListener(this);
		rb_chapter = (RadioButton) findViewById(R.id.rb_chapter);
		rb_verse = (RadioButton) findViewById(R.id.rb_verse);
		rb_verse.setChecked(true);
		seacherRg = (RadioGroup) findViewById(R.id.type_spinner);
		seacherRg.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
	}

	@Override
	protected void initData() {
		// 初始化mSpinner值
		if (OptionType.CURRENT_TABLE.equals(OptionType.NIV_TABLE)) {
			spinner.setSelection(0);
		} else if (OptionType.CURRENT_TABLE.equals(OptionType.NVI_TABLE)) {
			spinner.setSelection(2);
		} else {
			spinner.setSelection(1);
		}
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_home_btn:
			activity.finish();
			break;
		case R.id.search_ivbtn:
			MyUtils.hideInputMethod(activity);
			verseList.clear();
			noteAdapter.notifyDataSetChanged();
			page = 0;
			startSearch();
			break;
		case R.id.bar_rl:
			break;
		}
	}

	/**
	 * 执行搜索
	 */
	private void startSearch() {
		if (TextUtils.isEmpty(search_word_et.getText().toString())) {
			ToastUtils.showToast(activity, "No input");
			return;
		}
		
		if (search_word_et.getText().toString().indexOf(" ")==0) {
			ToastUtils.showToast(activity, "Input Error");
			return;
		}
		new QueryTask().execute(search_word_et.getText().toString());
	}

	/**
	 * 版本选择的监听
	 * 
	 * @author Administrator
	 * 
	 */
	class VersionSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			String[] tables = getResources().getStringArray(R.array.bible_version);
			String table = tables[position];
			if (table.equals("NIV")) {
				OptionType.CURRENT_TABLE = OptionType.NIV_TABLE;
			} else if (table.equals("NVI")) {
				OptionType.CURRENT_TABLE = OptionType.NVI_TABLE;
			} else {
				OptionType.CURRENT_TABLE = OptionType.NKJV_TABLE;
			}
			if (!isFirstComeIn) {
				verseList.clear();
				noteAdapter.notifyDataSetChanged();
				page = 0;
				startSearch();
			} else {
				isFirstComeIn = false;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}

	}

	/**
	 * 搜索类型选择
	 * 
	 * @author Administrator
	 * 
	 */
	class MyOnCheckedChangeListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			verseList.clear();
			noteAdapter.notifyDataSetChanged();
			switch (checkedId) {
			case R.id.rb_chapter:
				currenType = TYPE_CHAPTER;
				search_word_et.setText("");
				search_word_et.setHint("Input format:Title+Space+chapter+verse e.g James 5:14");
				break;

			case R.id.rb_verse:
				currenType = TYPE_VERSE;
				search_word_et.setText("");
				search_word_et.setHint("Please enter a keyword or verse");
				break;
			}
		}
	}

	/**
	 * 搜索关键字变化
	 * 
	 */
	OnKeyListener mOnKeyListener = new OnKeyListener() {

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_ENTER) {
				MyUtils.hideInputMethod(activity);
				verseList.clear();
				noteAdapter.notifyDataSetChanged();
				page = 0;
				startSearch();
			}
			return false;
		}
	};

	OnRefreshListener2 mOnRefreshListener2 = new OnRefreshListener2<View>() {

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<View> refreshView) {

		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<View> refreshView) {
			startSearch();
		}
	};

	/**
	 * 搜索 诗句
	 * 
	 * @author Administrator
	 * 
	 */
	class QueryTask extends AsyncTask<String, Void, List<VerseInfo>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			barRl.setVisibility(View.VISIBLE);
		}

		@Override
		protected List<VerseInfo> doInBackground(String... params) {
			AssetsDatabaseManager.initManager(activity);
			AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
			SQLiteDatabase db = mg.getDatabase("mxbible.db");
			List<VerseInfo> list = null;
			switch (currenType) {
			case TYPE_CHAPTER:
				String keyword = params[0];

				String bookName = MyUtils.getKeyWordBookName(keyword);
				String chapter = MyUtils.getKeyWordChapter(keyword);
				String section = MyUtils.getKeyWordSection(keyword);
				list = TBBible.searchChapter(db, OptionType.CURRENT_TABLE, bookName, chapter, section, page, rows);
				break;
			case TYPE_VERSE:
				list = TBBible.searchVerse(db, OptionType.CURRENT_TABLE, params[0], page, rows);
				break;
			}
			mg.closeAllDatabase();
			return list;
		}

		@Override
		protected void onPostExecute(List<VerseInfo> result) {
			super.onPostExecute(result);
			mynote_lv.onRefreshComplete();
			barRl.setVisibility(View.GONE);
			if (null == result || result.size() == 0) {
				ToastUtils.showToast(activity, "no data!");
				return;
			}
			page = page + rows;
			verseList.addAll(result);
			noteAdapter.notifyDataSetChanged();
		}

	}

}
