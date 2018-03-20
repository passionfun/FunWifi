package com.xalianhui.wifimanage.ui.activity;

import android.view.View;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.control.activity.OneControl;
import com.xalianhui.wifimanage.control.activity.RebootControl;
import com.xalianhui.wifimanage.ui.BaseActivity;


public class OneActivity extends BaseActivity{

    private OneControl control;

    @Override
    protected int addLayout() {
        control = new OneControl();
        return R.layout.activity_one;
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
