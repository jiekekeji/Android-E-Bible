package com.papa.bible.widget;

import razerdp.basepopup.BasePopupWindow;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.papa.bible.R;
import com.papa.bible.utils.MyUtils;

/**
 * 亮度调节
 * 
 * @author Administrator
 * 
 */
public class BrightnessPopup extends BasePopupWindow implements OnSeekBarChangeListener {

	protected static final String TAG = BrightnessPopup.class.getSimpleName();
	private Activity activity;
	private View popupView;

	// 视图
	private SeekBar controlBar;

	public BrightnessPopup(Activity context) {
		super(context);
		this.activity = context;
		initView();

	}

	// 加载view
	private void initView() {
		if (null == popupView) {
			return;
		}
		controlBar = (SeekBar) popupView.findViewById(R.id.font_size_control);
		controlBar.setOnSeekBarChangeListener(this);
		controlBar.setMax(255);
		controlBar.setProgress(MyUtils.getScreenBrightness(activity));
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
		popupView = LayoutInflater.from(mContext).inflate(R.layout.popup_brightness, null);
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
		MyUtils.setScreenBrightness(progress, activity);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		Log.i(TAG, "onStartTrackingTouch");
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		Log.i(TAG, "onStopTrackingTouch=" + seekBar.getProgress());
	}

}
