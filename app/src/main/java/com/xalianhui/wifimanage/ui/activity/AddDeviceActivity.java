package com.xalianhui.wifimanage.ui.activity;

import android.view.View;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.control.activity.AddDeviceControl;
import com.xalianhui.wifimanage.control.activity.XiansuDeviceControl;
import com.xalianhui.wifimanage.ui.BaseActivity;


public class AddDeviceActivity extends BaseActivity{

    private AddDeviceControl control;

    @Override
    protected int addLayout() {
        control = new AddDeviceControl();
        return R.layout.activity_add_device;
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
