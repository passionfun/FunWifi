package com.xalianhui.wifimanage.ui.activity;

import android.view.View;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.control.activity.FKXiansuControl;
import com.xalianhui.wifimanage.control.activity.SDXiansuControl;
import com.xalianhui.wifimanage.ui.BaseActivity;


public class FkXiansuActivity extends BaseActivity{

    private FKXiansuControl control;

    @Override
    protected int addLayout() {
        control = new FKXiansuControl();
        return R.layout.activity_fkxiansu;
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
