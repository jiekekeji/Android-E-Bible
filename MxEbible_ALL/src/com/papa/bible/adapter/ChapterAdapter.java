package com.papa.bible.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.papa.bible.R;

public class ChapterAdapter extends BaseAdapter {

	private List<String> chapters;
	private Activity activity;
	private LayoutInflater mInflater;

	public ChapterAdapter(Activity activity, List<String> chapters) {
		this.chapters = chapters;
		this.activity = activity;
		mInflater = LayoutInflater.from(activity);
	}

	@Override
	public int getCount() {
		return chapters.size();
	}

	@Override
	public Object getItem(int positon) {
		return chapters.get(positon);
	}

	@Override
	public long getItemId(int positon) {
		return positon;
	}

	@Override
	public View getView(int positon, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_chapter_list, null);
			holder = new ViewHolder();
			holder.txTv = (TextView) convertView.findViewById(R.id.chaptername);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txTv.setText(chapters.get(positon));
		return convertView;
	}

	class ViewHolder {
		TextView txTv;
	}

}
