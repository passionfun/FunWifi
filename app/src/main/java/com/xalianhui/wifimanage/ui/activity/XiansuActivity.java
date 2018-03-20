package com.xalianhui.wifimanage.ui.activity;

import android.view.View;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.control.activity.OneControl;
import com.xalianhui.wifimanage.control.activity.XiansuControl;
import com.xalianhui.wifimanage.ui.BaseActivity;


public class XiansuActivity extends BaseActivity{

    private XiansuControl control;

    @Override
    protected int addLayout() {
        control = new XiansuControl();
        return R.layout.activity_xiansu;
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
    @Override
    protected void onResume() {
        super.onResume();
        if (control != null) {
            control.onResume();
        }
    }
}
