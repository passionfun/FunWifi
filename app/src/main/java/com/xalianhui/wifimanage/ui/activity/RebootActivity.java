package com.xalianhui.wifimanage.ui.activity;

import android.view.View;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.control.activity.NetSetControl;
import com.xalianhui.wifimanage.control.activity.RebootControl;
import com.xalianhui.wifimanage.ui.BaseActivity;


public class RebootActivity extends BaseActivity{

    private RebootControl control;

    @Override
    protected int addLayout() {
        control = new RebootControl();
        return R.layout.activity_reboot;
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
        }
    }


}
