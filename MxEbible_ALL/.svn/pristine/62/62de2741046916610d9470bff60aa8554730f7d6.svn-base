package com.papa.bible.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.papa.bible.R;
import com.papa.bible.bean.ChapterInfo;
import com.wx.wheelview.adapter.BaseWheelAdapter;

public class ChapterSelAdapter extends BaseWheelAdapter {

	public static final int TYPE_MAIN = 1;
	public static final int TYPE_SUB = 2;
	private Activity activity;
	private LayoutInflater mInflater;
	private int type;

	public ChapterSelAdapter(Activity context, int type) {
		activity = context;
		mInflater = LayoutInflater.from(activity);
		this.type = type;
	}

	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_chapter_sel_pop, null);
			holder = new ViewHolder();
			holder.txTv = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ChapterInfo info = (ChapterInfo) mList.get(position);
		switch (type) {
		case TYPE_MAIN:
			holder.txTv.setText(info.getBookName());
			break;
		case TYPE_SUB:
			holder.txTv.setText(info.getChapter());
			break;
		}

		return convertView;
	}

	class ViewHolder {
		TextView txTv;
	}

}
