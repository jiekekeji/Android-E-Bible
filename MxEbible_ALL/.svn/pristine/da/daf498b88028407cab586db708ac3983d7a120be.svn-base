package com.papa.bible;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.TextView;

import com.papa.bible.adapter.HelpPagerAdapter;
import com.papa.bible.bean.HelpInfo;

public class HelpCenterActivity extends BaseActivity {

	private ViewPager viewpager;
	private HelpPagerAdapter mAdapter;
	private List<HelpInfo> infos;
	private TextView indictor_tv;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_help_center);
		initView();
		initData();
	}

	private void initView() {
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		infos = new ArrayList<HelpInfo>();
		mAdapter = new HelpPagerAdapter(this, infos);
		viewpager.setAdapter(mAdapter);
		indictor_tv = (TextView) findViewById(R.id.indictor_tv);
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				indictor_tv.setText(position + 1 + "/" + infos.size());
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	private void initData() {
		HelpInfo info1 = new HelpInfo();
		info1.setDrawableId(R.drawable.intro_main);
		HelpInfo info2 = new HelpInfo();
		info2.setDrawableId(R.drawable.intro_do);
		HelpInfo info3 = new HelpInfo();
		info3.setDrawableId(R.drawable.intro_read);

		HelpInfo info4 = new HelpInfo();
		info4.setDrawableId(R.drawable.intro_query);
		HelpInfo info5 = new HelpInfo();
		info5.setDrawableId(R.drawable.intro_delete);
		HelpInfo info6 = new HelpInfo();
		info6.setDrawableId(R.drawable.intro_select);

		infos.add(info1);
		infos.add(info2);
		infos.add(info3);
		infos.add(info4);
		infos.add(info5);
		infos.add(info6);
		mAdapter.notifyDataSetChanged();
		indictor_tv.setText("1/" + infos.size());
	}

	public void finishSelf(View view) {
		finish();
	}
}
