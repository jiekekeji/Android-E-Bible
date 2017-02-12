package com.papa.bible.adapter;

import java.util.List;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.papa.bible.R;
import com.papa.bible.bean.HelpInfo;
import com.papa.bible.widget.PinchImageView;

public class HelpPagerAdapter extends PagerAdapter {
	private static final String TAG = HelpPagerAdapter.class.getSimpleName();

	private Activity activity;
	private LayoutInflater mInflater;
	private List<HelpInfo> infos;

	public HelpPagerAdapter(Activity activity, List<HelpInfo> infos) {
		this.activity = activity;
		mInflater = LayoutInflater.from(activity);
		this.infos = infos;

	}

	@Override
	public int getCount() {
		return infos.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View itemView = mInflater.inflate(R.layout.item_help_pager, container, false);
		PinchImageView imageView = (PinchImageView) itemView.findViewById(R.id.intro_img);
		TextView textView = (TextView) itemView.findViewById(R.id.intro_tv);

		HelpInfo info = infos.get(position);
		imageView.setImageResource(info.getDrawableId());
		// textView.setText(info.getStringID());

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

}
