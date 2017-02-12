package com.papa.bible.widget;

import java.util.ArrayList;
import java.util.List;

import razerdp.basepopup.BasePopupWindow;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;

import com.papa.bible.R;
import com.papa.bible.adapter.ChapterSelAdapter;
import com.papa.bible.bean.ChapterInfo;
import com.wx.wheelview.widget.WheelView;
import com.wx.wheelview.widget.WheelView.OnWheelItemSelectedListener;

/**
 * 章节选择PopupWindow
 * 
 * @author Administrator
 * 
 */
public class ChapterSelPopup extends BasePopupWindow implements View.OnClickListener {

	protected static final String TAG = ChapterSelPopup.class.getSimpleName();
	private Activity activity;
	private View popupView;
	private List<ChapterInfo> chapters;
	private WheelView mainWheelView;
	private WheelView subWheelView;
	private List<ChapterInfo> mainList;
	private List<List<ChapterInfo>> subList;
	// 当前选中的ChapterInfo
	private ChapterInfo currentInfo;

	// 选中的数据
	private String selectedMain;
	private String selectedSub;

	public ChapterSelPopup(Activity context, List<ChapterInfo> chapters) {
		super(context);
		this.activity = context;
		this.chapters = chapters;
		initView();
	}

	// 初始化试图
	private void initView() {
		if (null == popupView) {
			return;
		}
		createMainSubDatas();
		mainWheelView = (WheelView) popupView.findViewById(R.id.main_wheelview);
		subWheelView = (WheelView) popupView.findViewById(R.id.sub_wheelview);
		popupView.findViewById(R.id.ok_select_chapter).setOnClickListener(this);
		initData();
	}

	// 初始化数据
	private void initData() {
		// 主轮数据
		mainWheelView.setWheelAdapter(new ChapterSelAdapter(activity, ChapterSelAdapter.TYPE_MAIN));
		mainWheelView.setWheelData(mainList);
		mainWheelView.setOnWheelItemSelectedListener(new OnWheelItemSelectedListener<ChapterInfo>() {

			@Override
			public void onItemSelected(int position, ChapterInfo data) {
				Log.i(TAG, "position=" + position + " data=" + data.toString());
				currentInfo = data;
			}

		});
		// 从轮数据
		subWheelView.setWheelAdapter(new ChapterSelAdapter(activity, ChapterSelAdapter.TYPE_SUB));
		subWheelView.setWheelData(subList.get(mainWheelView.getSelection()));
		subWheelView.setOnWheelItemSelectedListener(new OnWheelItemSelectedListener<ChapterInfo>() {

			@Override
			public void onItemSelected(int position, ChapterInfo data) {
				Log.i(TAG, "position=" + position + " data=" + data.toString());
				currentInfo = data;
			}

		});
		// 将从轮加到主轮中
		mainWheelView.join(subWheelView);
		mainWheelView.joinDatas(subList);
	}

	@Override
	protected Animation getShowAnimation() {
		return getDefaultScaleAnimation();
	}

	@Override
	protected View getClickToDismissView() {
		return popupView.findViewById(R.id.click_to_dismiss);
	}

	@Override
	public View getPopupView() {
		popupView = LayoutInflater.from(mContext).inflate(R.layout.popup_normal, null);
		return popupView;
	}

	@Override
	public View getAnimaView() {
		return popupView.findViewById(R.id.popup_anima);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ok_select_chapter:
			if (null != onChapterSelectedListener && null != currentInfo) {
				// initPager的格式bookID#chapter;
				onChapterSelectedListener.onChapterSelected(currentInfo.getBookID() + "#" + currentInfo.getChapter());
			}
			break;
		}
		dismiss();
	}

	/**
	 * 创建主轮和副轮数据
	 * 
	 * @return
	 */
	private List<String> createMainSubDatas() {
		mainList = new ArrayList<ChapterInfo>();
		subList = new ArrayList<List<ChapterInfo>>();
		List<ChapterInfo> tempList = null;
		int mainID = -1;
		for (int i = 0; i < chapters.size(); i++) {
			ChapterInfo info = chapters.get(i);
			if (mainID != info.getBookID()) {
				mainList.add(info);
				if (mainID != -1) {
					subList.add(tempList);
				}
				tempList = new ArrayList<ChapterInfo>();
				mainID = info.getBookID();
			}
			if (i == chapters.size() - 1) {
				subList.add(tempList);
			}
			tempList.add(info);
		}
		return null;
	}

	private OnChapterSelectedListener onChapterSelectedListener;

	public void setOnChapterSelectedListener(OnChapterSelectedListener onChapterSelectedListener) {
		this.onChapterSelectedListener = onChapterSelectedListener;
	}

	/**
	 * 删除状态的监听
	 * 
	 * @author Administrator
	 * 
	 */
	public interface OnChapterSelectedListener {
		public void onChapterSelected(String initPager);
	}

}
