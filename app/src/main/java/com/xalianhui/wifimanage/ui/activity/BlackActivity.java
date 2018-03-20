package com.xalianhui.wifimanage.ui.activity;

import android.view.View;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.control.activity.BlackControl;
import com.xalianhui.wifimanage.ui.BaseActivity;
import com.xalianhui.wifimanage.utils.LogUtil;


public class BlackActivity extends BaseActivity{

    private BlackControl control;

    @Override
    protected int addLayout() {
        control = new BlackControl();
        return R.layout.activity_black;
    }

    @Override
    protected void initView(View xmlRoot) {
        if (control != null) {
            control.onInit(xmlRoot, this);
        }
    }

    @Override
    public int getTitleBg() {
        return R.color.black_bg_black;
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
