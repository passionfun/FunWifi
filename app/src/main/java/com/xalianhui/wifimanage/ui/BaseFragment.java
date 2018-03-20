package com.xalianhui.wifimanage.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.interfaces.OnFragmentSelector;


public abstract class BaseFragment extends Fragment {
	/**
	 * 设置逻辑处理类，进行UI分离
	 */

	public OnFragmentSelector mFragmentSelector = null;
	private int mFragmentRes = -1;
	private View mBaseView = null;
	private View mContentView = null;

	protected abstract void getArguments(Bundle bundle,
			OnFragmentSelector fragmentSelector);// 获取参数传�?

	protected abstract void onInit(View childRoot);// 初始化view

	protected abstract int addLayout();// 添加子layout

	protected abstract void recycleView();// 回收子layout view

	@Override
	public final void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mFragmentSelector = (OnFragmentSelector) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentSelector");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mBaseView = inflater.inflate(R.layout.fragment_base, container, false);
		mFragmentRes = addLayout();
		if (mFragmentRes > 0) {
			try {
				mContentView = LayoutInflater.from(getActivity()).inflate(
						mFragmentRes,
						(FrameLayout) mBaseView
								.findViewById(R.id.baseFragment_content));
				// initView(mContentView);
			} catch (Exception e) {
				e.printStackTrace();
				mContentView = null;
			}
		}
		return mBaseView;
	}

	@Override
	public final void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		inital();
	}

	/**
	 * @Title: inital
	 * @Description: TODO 初始�?
	 * @param
	 * @return void
	 * @exception/throws description
	 */
	private void inital() {
		if (mContentView != null) {
			getArguments(getArguments(), mFragmentSelector);
			onInit(mContentView);
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		recycleView();
		mContentView = null;
		System.gc();
	}
}
