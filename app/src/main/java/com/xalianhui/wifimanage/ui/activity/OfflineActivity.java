package com.xalianhui.wifimanage.ui.activity;

import android.view.View;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.control.activity.JiazDeviceControl;
import com.xalianhui.wifimanage.control.activity.OfflineControl;
import com.xalianhui.wifimanage.ui.BaseActivity;


public class OfflineActivity extends BaseActivity{

    private OfflineControl control;

    @Override
    protected int addLayout() {
        control = new OfflineControl();
        return R.layout.activity_offline;
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
