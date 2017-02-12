package com.papa.bible.fr;

import android.app.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

	protected Activity activity;
	protected View rootView;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (null == rootView) {
			rootView = inflater.inflate(getFragmentResLayout(), container, false);
		}
		initView();
		initData();
		return rootView;
	}

	protected View findViewById(int resId) {
		if (null == rootView) {
			rootView = LayoutInflater.from(activity).inflate(getFragmentResLayout(), null);
		}
		return rootView.findViewById(resId);
	}

	/**
	 * 设置页面布局文件
	 * 
	 * @return 返回布局Id
	 */
	protected abstract int getFragmentResLayout();

	/**
	 * 初始化布局中的子view
	 */
	protected abstract void initView();

	/**
	 * 开始加载数据
	 */
	protected abstract void initData();

}
