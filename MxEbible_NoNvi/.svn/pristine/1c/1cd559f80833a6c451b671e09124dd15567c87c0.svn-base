package com.papa.bible.adapter;

import java.util.List;

import razerdp.basepopup.BasePopupWindow.OnDismissListener;
import android.app.Activity;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.papa.bible.R;
import com.papa.bible.bean.VerseInfo;
import com.papa.bible.common.OptionType;
import com.papa.bible.utils.ConfigureUtils;
import com.papa.bible.utils.MyUtils;
import com.papa.bible.widget.NoteEditPopup;

public class VerseListViewAdapter extends BaseAdapter implements OnClickListener {

	private static final String TAG = VerseListViewAdapter.class.getSimpleName();
	private Activity activity;
	private List<VerseInfo> bibles;
	private LayoutInflater mInflater;

	public VerseListViewAdapter(Activity activity, List<VerseInfo> bibles) {
		Log.i(TAG, "VerseListViewAdapter init bibles=" + bibles.size());
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
			convertView = mInflater.inflate(R.layout.adapter_item_read_list, null);
			holder = new ViewHolder();
			holder.txTv = (TextView) convertView.findViewById(R.id.tv);
			holder.mark_iv = (ImageView) convertView.findViewById(R.id.mark_iv);
			holder.note_iv = (ImageView) convertView.findViewById(R.id.note_iv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 数据
		VerseInfo info = bibles.get(position);
		holder.note_iv.setOnClickListener(this);
		holder.note_iv.setTag(info);

		// 绑定序号数据
		int sectionLength = 0;
		int section = Integer.valueOf(info.getSection());
		String verse = null;
		Spannable span = null;
		if (OptionType.CURRENT_TABLE == OptionType.NVI_TABLE) {
			if (section == 1) {
				verse = info.getVerse();
				// 设置内容的文字样式
				holder.txTv.setText(verse);
				span = new SpannableString(holder.txTv.getText());
				span.setSpan(new AbsoluteSizeSpan(ConfigureUtils.getContentFontSize(activity) + 12), sectionLength,
						verse.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			} else {
				section = section - 1;
				sectionLength = getSectionLength(section);
				verse = section + " " + info.getVerse();
				// 设置序号的颜色和大小
				holder.txTv.setText(verse);
				span = new SpannableString(holder.txTv.getText());
				span.setSpan(new AbsoluteSizeSpan(ConfigureUtils.getContentFontSize(activity) + 8), 0, sectionLength,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				span.setSpan(new ForegroundColorSpan(Color.RED), 0, sectionLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

				// 设置内容的文字样式
				span.setSpan(new AbsoluteSizeSpan(ConfigureUtils.getContentFontSize(activity)), sectionLength,
						verse.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}

		} else {
			sectionLength = getSectionLength(section);
			verse = section + " " + info.getVerse();

			holder.txTv.setText(verse);
			span = new SpannableString(holder.txTv.getText());

			// 设置序号的颜色和大小
			span.setSpan(new AbsoluteSizeSpan(ConfigureUtils.getContentFontSize(activity) + 8), 0, sectionLength,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			span.setSpan(new ForegroundColorSpan(Color.RED), 0, sectionLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

			// 设置内容的文字样式
			span.setSpan(new AbsoluteSizeSpan(ConfigureUtils.getContentFontSize(activity)), sectionLength,
					verse.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		String color = info.getBgcolor();
		if (!TextUtils.isEmpty(color)) {
			span.setSpan(new BackgroundColorSpan(MyUtils.getResColorID(color)), sectionLength, verse.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		String underline = info.getUnderline();
		if (!TextUtils.isEmpty(underline)) {
			span.setSpan(new UnderlineSpan(), sectionLength, verse.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		holder.txTv.setText(span);

		// 判断是否有笔记
		if ("1".equals(info.getReserve01()) && !TextUtils.isEmpty(info.getReserve02())) {
			holder.note_iv.setVisibility(View.VISIBLE);
		} else {
			holder.note_iv.setVisibility(View.GONE);
		}
		// 判断是否为书签
		if ("1".equals(info.getReserve04())) {
			holder.mark_iv.setVisibility(View.VISIBLE);
		} else {
			holder.mark_iv.setVisibility(View.GONE);
		}

		return convertView;
	}

	/**
	 * 获取序号的长度
	 * 
	 * @param section
	 * @return
	 */
	private int getSectionLength(int section) {
		int sectionLength;
		if (10 > section) {
			sectionLength = 1;
		} else if (10 < section && section < 100) {
			sectionLength = 2;
		} else {
			sectionLength = 3;
		}
		return sectionLength;
	}

	class ViewHolder {
		RelativeLayout verse_rl;
		ImageView mark_iv;
		ImageView note_iv;
		ImageView note_edit_iv;
		TextView txTv;
	}

	@Override
	public void onClick(View v) {
		VerseInfo info = (VerseInfo) v.getTag();
		System.out.println("onClick info=" + info);
		showNoteEditPopup(info);
	}

	/**
	 * 显示笔记编辑Popup
	 * 
	 * @param info
	 */
	private void showNoteEditPopup(VerseInfo info) {
		NoteEditPopup noteEditPopup = new NoteEditPopup(activity, info);
		noteEditPopup.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				notifyDataSetChanged();
			}
		});
		noteEditPopup.showPopupWindow();
	}

}
