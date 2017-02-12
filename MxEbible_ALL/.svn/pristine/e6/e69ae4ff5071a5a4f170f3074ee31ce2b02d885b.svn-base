package com.papa.bible;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import com.papa.bible.R;
import com.papa.bible.common.OptionType;
import com.papa.bible.fr.BookMarkFragment;

/**
 * 我的笔记列表
 * 
 * @author Administrator
 * 
 */
public class BookMarkActivity extends BaseActivity {

	private Spinner mSpinner;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_bookmark);
		initViews();
	}

	private void initViews() {
		mSpinner = (Spinner) findViewById(R.id.spinner);

		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

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
				// 刷新数据
				BookMarkFragment fragment = (BookMarkFragment) getSupportFragmentManager().findFragmentByTag(
						"BookMarkFragment");
				fragment.refreshData();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		// 初始化mSpinner值
		if (OptionType.CURRENT_TABLE.equals(OptionType.NIV_TABLE)) {
			mSpinner.setSelection(0);
		} else if (OptionType.CURRENT_TABLE.equals(OptionType.NVI_TABLE)) {
			mSpinner.setSelection(2);
		} else {
			mSpinner.setSelection(1);
		}

		findViewById(R.id.back_home_btn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
	}

}
