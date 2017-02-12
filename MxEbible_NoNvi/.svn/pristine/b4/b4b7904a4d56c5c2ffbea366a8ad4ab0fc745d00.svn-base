package com.papa.bible.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 无滚动的GridView
 * 
 * @author Administrator
 * 
 */
public class NScrollGridView extends GridView {

	public NScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 设置不滚动
	 */
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}

}
