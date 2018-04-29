package com.xalianhui.wifimanage.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.control.activity.MainControl;
import com.xalianhui.wifimanage.interfaces.OnFragmentSelector;
import com.xalianhui.wifimanage.ui.BaseActivity;


public class MainActivity extends BaseActivity implements OnFragmentSelector{
    private final String TAG = MainActivity.class.getSimpleName();
    private MainControl control;

    @Override
    protected int addLayout() {
        control = new MainControl();
        return R.layout.activity_main;
    }

    @Override
    protected void initView(View xmlRoot) {
        if (control != null) {
            control.onInit(xmlRoot, this);
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (control != null) {
//            control.onStart();
//        }
//    }
    @Override
    protected void onResume() {
        super.onResume();
        if (control != null) {
            control.onStart();
        }
    }

    @Override
    protected void recycleView() {
        if (control != null) {
            control.onRecycle();
            control = null;
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return control.onKeyDown(keyCode, event);
    }

    @Override
    public void onFragment(int fragmentIndex) {
         control.onFragment(fragmentIndex);
    }

    @Override
    public void onPageNum(int fragmentIndex, int pageCount, int curPage, Bundle bundle) {

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
