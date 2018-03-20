package com.xalianhui.wifimanage.ui.activity;

import android.view.View;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.control.activity.XiansuControl;
import com.xalianhui.wifimanage.control.activity.XiansuDeviceControl;
import com.xalianhui.wifimanage.ui.BaseActivity;


public class XiansuDeviceActivity extends BaseActivity{

    private XiansuDeviceControl control;

    @Override
    protected int addLayout() {
        control = new XiansuDeviceControl();
        return R.layout.activity_device_xiansu;
    }

    @Override
    protected void initView(View xmlRoot) {
        if (control != null) {
            control.onInit(xmlRoot, this);
        }
    }


    @Override
    protected void recycleView() {
        if (control != null) {
            control.onRecycle();
            control = null;
        }
    }

}
