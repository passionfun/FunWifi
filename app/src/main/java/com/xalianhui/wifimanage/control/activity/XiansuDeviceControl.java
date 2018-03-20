package com.xalianhui.wifimanage.control.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.control.BaseActivityControl;
import com.xalianhui.wifimanage.ui.activity.BlackActivity;
import com.xalianhui.wifimanage.ui.activity.DeviceXiansuActivity;
import com.xalianhui.wifimanage.ui.activity.FkXiansuActivity;
import com.xalianhui.wifimanage.ui.activity.SDXiansuActivity;
import com.xalianhui.wifimanage.ui.activity.XiansuAppActivity;

import org.xutils.view.annotation.Event;
import org.xutils.x;


public class XiansuDeviceControl extends BaseActivityControl {

//	@ViewInject(R.id.et_wifi_name)
//	private EditText etName;
//	@ViewInject(R.id.et_wifi_pass)
//	private EditText etPassword;
//
//	private MntDialog mntDialog;
	@Override
	public void onInit(View rootView, Context context) {
		super.onInit(rootView, context);
		x.view().inject(this, rootView);
		initView();
	}


    @Override
	public void onRecycle() {

	}


	private void initView() {
		setTopView(Consts.TopPage.PAGE);
		setTitle(getResString(R.string.smart_device_list));
	}

	@Event(value = {R.id.ly_select_pppoe,R.id.ly_select_pppoe1,R.id.ly_select_pppoe2})
	private void select(View v) {
		Intent intent;
		switch (v.getId()) {
			case R.id.ly_select_pppoe:
				intent = new Intent(mActivity, DeviceXiansuActivity.class);
				mActivity.startActivity(intent);
				break;
			case R.id.ly_select_pppoe1:
				intent = new Intent(mActivity, DeviceXiansuActivity.class);
				mActivity.startActivity(intent);
				break;
			case R.id.ly_select_pppoe2:
				intent = new Intent(mActivity, DeviceXiansuActivity.class);
				mActivity.startActivity(intent);
				break;


			default:
				break;
		}
	}





}
