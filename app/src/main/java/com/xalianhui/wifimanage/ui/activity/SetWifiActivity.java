package com.xalianhui.wifimanage.ui.activity;

import android.content.Intent;
import android.view.View;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.control.activity.BroadbandControl;
import com.xalianhui.wifimanage.control.activity.SetWifiControl;
import com.xalianhui.wifimanage.ui.BaseActivity;


public class SetWifiActivity extends BaseActivity{

    private SetWifiControl control;

    @Override
    protected int addLayout() {
        control = new SetWifiControl();
        return R.layout.activity_set_wifi;
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (control != null) {
//            control.onActivityResult(requestCode, resultCode, data);
//        }
//    }
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
