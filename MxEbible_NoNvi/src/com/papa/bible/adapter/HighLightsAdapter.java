package com.papa.bible.adapter;

import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.papa.bible.R;
import com.papa.bible.bean.VerseInfo;
import com.papa.bible.fr.HighLinghtsFragment;
import com.papa.bible.utils.MyUtils;

/**
 * 高亮的句子
 * 
 * @author Administrator
 * 
 */
@SuppressLint("ResourceAsColor")
public class HighLightsAdapter extends BaseAdapter {

	private static final String TAG = HighLightsAdapter.class.getSimpleName();
	private Activity activity;
	private List<VerseInfo> bibles;
	private LayoutInflater mInflater;

	public HighLightsAdapter(Activity activity, List<VerseInfo> bibles) {
		this.activity = activity;
		this.bibles = bibles;
		this.mInflater = LayoutInflater.from(activity);
	}

	@Override
	public int getCount() {
		return bibles.size();
	}

	@Override
	public Object getItem(int position) {
		return bibles.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.i(TAG, "getView=" + position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.adapter_item_highlights_list, null);
			holder = new ViewHolder();
			holder.title_tv = (TextView) convertView.findViewById(R.id.title_tv);
			holder.note_tv = (TextView) convertView.findViewById(R.id.note_tv);
			holder.note_time_tv = (TextView) convertView.findViewById(R.id.note_time_tv);
			holder.color_iv = (ImageView) convertView.findViewById(R.id.color_iv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		VerseInfo info = bibles.get(position);
		holder.title_tv.setText(info.getBookName() + " " + info.getChapter() + ":" + info.getSection());
		holder.note_tv.setText(info.getVerse());

		String color = info.getBgcolor();
		if (HighLinghtsFragment.currentType == 1) {
			holder.color_iv.setBackgroundColor(MyUtils.getResColorID(color));
			holder.color_iv.setImageResource(android.R.color.transparent);
//			holder.note_time_tv.setText(info.getReserve07());
		} else {
			holder.color_iv.setImageResource(R.drawable.icon_underline);
			holder.color_iv.setBackgroundColor(android.R.color.transparent);
//			holder.note_time_tv.setText(info.getReserve06());
		}

		return convertView;
	}

	class ViewHolder {
		TextView title_tv;
		TextView note_tv;
		TextView note_time_tv;
		ImageView color_iv;
	}

}
