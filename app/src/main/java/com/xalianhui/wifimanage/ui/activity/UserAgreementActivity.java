package com.xalianhui.wifimanage.ui.activity;

import android.view.View;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.control.activity.OneControl;
import com.xalianhui.wifimanage.control.activity.UserArgeementControl;
import com.xalianhui.wifimanage.ui.BaseActivity;


public class UserAgreementActivity extends BaseActivity{

    private UserArgeementControl control;

    @Override
    protected int addLayout() {
        control = new UserArgeementControl();
        return R.layout.activity_user_agreement;
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
