package com.papa.bible.widget;

import razerdp.basepopup.BasePopupWindow;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.papa.bible.R;
import com.papa.bible.utils.ConfigureUtils;

/**
 * 字体大小调整
 * 
 * @author Administrator
 * 
 */
public class FontSizePopup extends BasePopupWindow implements OnSeekBarChangeListener {

	protected static final String TAG = FontSizePopup.class.getSimpleName();
	private Activity activity;
	private View popupView;

	// 视图
	private TextView content_tv;
	private SeekBar controlBar;

	// 监听者
	private OnFontSizeSelected mFontSizeSelected;

	public FontSizePopup(Activity context) {
		super(context);
		this.activity = context;
		initView();

	}

	// 加载view
	private void initView() {
		if (null == popupView) {
			return;
		}
		content_tv = (TextView) popupView.findViewById(R.id.content_tv);
		controlBar = (SeekBar) popupView.findViewById(R.id.font_size_control);
		controlBar.setOnSeekBarChangeListener(this);
		controlBar.setProgress(ConfigureUtils.getContentFontSize(activity));
		initData();
	}

	// 初始化view数据
	private void initData() {
		controlBar.setMax(64);
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
		popupView = LayoutInflater.from(mContext).inflate(R.layout.popup_font_size, null);
		return popupView;
	}

	@Override
	public View getAnimaView() {
		return popupView.findViewById(R.id.popup_anima);
	}

	// start seekbar滑动的监听
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		Log.i(TAG, "onProgressChanged");
		content_tv.setTextSize(progress + 16);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		Log.i(TAG, "onStartTrackingTouch");
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		Log.i(TAG, "onStopTrackingTouch=" + seekBar.getProgress());
		if (null != mFontSizeSelected) {
			mFontSizeSelected.onFontSizeSelected(seekBar.getProgress() + 16);
		}
	}

	// end seekbar滑动的监听
	public interface OnFontSizeSelected {
		public void onFontSizeSelected(int size);
	}

	/**
	 * 设置字体大小变化的监听
	 * 
	 * @param onFontSizeSelected
	 */
	public void setFontSizeSelected(OnFontSizeSelected onFontSizeSelected) {
		this.mFontSizeSelected = onFontSizeSelected;
	}
}
