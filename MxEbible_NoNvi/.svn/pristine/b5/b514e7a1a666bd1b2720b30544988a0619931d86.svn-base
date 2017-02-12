package com.papa.bible.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 无滚动的ListView
 * 
 * @author Administrator
 * 
 */
public class NScrollListView extends ListView {

	public NScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NScrollListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public NScrollListView(Context context) {
		super(context);
	}

	/**
	 * 设置不滚动
	 */
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}

}
