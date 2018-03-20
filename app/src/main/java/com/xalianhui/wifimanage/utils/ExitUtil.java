package com.xalianhui.wifimanage.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class ExitUtil {

	private static List<Activity> actList = new ArrayList<Activity>();
	private static ExitUtil instance;

	public static ExitUtil newInstance() {
		if (instance == null) {
			instance = new ExitUtil();
		}
		return instance;
	}

	public void addAct(Activity act) {
		if (act != null) {
			actList.add(act);
		}
	}
	
	public void exit(){
		if (actList != null && actList.size() > 0) {
			for (Activity act : actList) {
				if (act != null) {
					act.finish();
				}
			}
		}
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}

}
