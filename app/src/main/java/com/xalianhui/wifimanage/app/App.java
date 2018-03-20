package com.xalianhui.wifimanage.app;


import com.xalianhui.wifimanage.BuildConfig;
import com.xalianhui.wifimanage.utils.CrashHandler;

import org.xutils.x;

import miky.android.common.BaseApplication;


public class App extends BaseApplication{
	private static App mInstance = null;
	// 定位结果
	

	public static App getInstance() {
		return mInstance;
	}
	@Override
	public void initOther() {
		x.Ext.init(this);
		x.Ext.setDebug(BuildConfig.DEBUG);
		CrashHandler crashHandler = CrashHandler.getInstance();
                crashHandler.init(getApplicationContext());
	}
	@Override
	public boolean isDebug() {
		// TODO Auto-generated method stub
		return false;
	}
	

	
	

}
