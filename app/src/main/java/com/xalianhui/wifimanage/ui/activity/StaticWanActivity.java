package com.xalianhui.wifimanage.ui.activity;

import android.view.View;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.control.activity.BroadbandControl;
import com.xalianhui.wifimanage.control.activity.StaticWanControl;
import com.xalianhui.wifimanage.ui.BaseActivity;


public class StaticWanActivity extends BaseActivity{

    private StaticWanControl control;

    @Override
    protected int addLayout() {
        control = new StaticWanControl();
        return R.layout.activity_staticwan;
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
