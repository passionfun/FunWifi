package com.xalianhui.wifimanage.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.utils.ExitUtil;
import com.xalianhui.wifimanage.utils.LogUtil;
import com.xalianhui.wifimanage.utils.StatusBarCompat;


public abstract class BaseActivity extends FragmentActivity {

	private final String TAG = BaseActivity.class.getSimpleName();
	private final boolean DEBUG = true;

	protected abstract int addLayout();// 添加子layout

	protected abstract void initView(View xmlRoot);// 初始化view

	protected abstract void recycleView();// 回收子layout view

	// base layout view
	private RelativeLayout baseUI = null;
	// 用户资源
	private int resId = -1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		StatusBarCompat.compat(this, getResources().getColor(getTitleBg()));
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 保持唤醒状�?
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		setContentView(R.layout.activity_base);
		ExitUtil.newInstance().addAct(this);
		// 添加子view资源
		resId = addLayout();
		init();
	}

	public int getTitleBg(){
		return R.color.main_blue;
	}
	// 初始化view
	private void init() {
		baseUI = (RelativeLayout) BaseActivity.this.findViewById(R.id.base_ui);
		loadUI();
	}

	/**
	 * @Title: loadUI
	 * @Description: 加载UI
	 */
	private void loadUI() {
		try {
			if (resId > 0) {
				initView(LayoutInflater.from(BaseActivity.this).inflate(resId,
						baseUI));
			} else {
				LogUtil.i(TAG, "the res is invalid...");
			}
		} catch (Exception e) {
			if (DEBUG) {
				e.printStackTrace();
			}
			LogUtil.e(TAG, e.toString());
		}
	}

	/*
	 * <p>Title: onStart</p> <p>Description: </p>
	 * 
	 * @see android.support.v4.app.FragmentActivity#onStart()
	 */
	@Override
	protected void onStart() {
		if (DEBUG) {
			LogUtil.i(TAG, "onStart");
		}
		super.onStart();
	}

	/*
	 * <p>Title: onStop</p> <p>Description: </p>
	 * 
	 * @see android.support.v4.app.FragmentActivity#onStop()
	 */
	@Override
	protected void onStop() {
		if (DEBUG) {
			LogUtil.i(TAG, "onStop");
		}
		super.onStop();
	}

	/*
	 * <p>Title: onResume</p> <p>Description: </p>
	 * 
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		if (DEBUG) {
			LogUtil.i(TAG, "onResume");
		}
		super.onResume();
	}

	/*
	 * <p>Title: onPause</p> <p>Description: </p>
	 * 
	 * @see android.support.v4.app.FragmentActivity#onPause()
	 */
	@Override
	protected void onPause() {
		if (DEBUG) {
			LogUtil.i(TAG, "onPause");
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		destoryView();
//		//fun add 程序启动添加activity到任务栈，程序退出时销毁任务栈
//		ExitUtil.newInstance().exit();
		System.gc();
	}

	/**
	 * @Title: destoryView
	 * @Description: �?���?��view
	 */
	private void destoryView() {
		// 提供外部对象付空
		recycleView();
		baseUI = null;
	}

}
