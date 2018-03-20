package com.xalianhui.wifimanage.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.control.activity.MainControl;
import com.xalianhui.wifimanage.control.activity.MenuSelectControl;
import com.xalianhui.wifimanage.interfaces.OnFragmentSelector;
import com.xalianhui.wifimanage.ui.BaseActivity;


public class MenuSelectActivity extends BaseActivity {

    private MenuSelectControl control;

    @Override
    protected int addLayout() {
        control = new MenuSelectControl();
        return R.layout.activity_menu_select;
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


    /*Acp.getInstance(this).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.READ_PHONE_STATE
            , Manifest.permission.SEND_SMS)
                *//*以下为自定义提示语、按钮文字
                .setDeniedMessage()
                .setDeniedCloseBtn()
                .setDeniedSettingBtn()
                .setRationalMessage()
                .setRationalBtn()*//*
                        .build(),
                new AcpListener() {
        @Override
        public void onGranted() {
            writeSD();
            getIMEI();
        }

        @Override
        public void onDenied(List permissions) {
            makeText(permissions.toString() + "权限拒绝");
        }
    });*/
}
