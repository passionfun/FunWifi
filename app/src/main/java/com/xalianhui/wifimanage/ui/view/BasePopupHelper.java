package com.xalianhui.wifimanage.ui.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/6/12.
 */

public class BasePopupHelper {

    public static void hidePopupWindow(PopupWindow mPopupWindow) {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    public static void initPopupWindow(final Activity mActivity, PopupWindow mPopupWindow, int animationStyle) {
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(animationStyle);
        mPopupWindow.update();
        mPopupWindow.setClippingEnabled(true);
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
//        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        fitPopupWindowOverStatusBar(true,mPopupWindow);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(mActivity.getResources(), (Bitmap) null));
        // 设置背景颜色变暗
        final WindowManager.LayoutParams layoutParams = mActivity.getWindow().getAttributes();
        layoutParams.alpha = 0.5f;
        mActivity.getWindow().setAttributes(layoutParams);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha = 1f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
    }
    public static void fitPopupWindowOverStatusBar(boolean needFullScreen, PopupWindow mPopupWindow) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Field mLayoutInScreen = PopupWindow.class.getDeclaredField("mLayoutInScreen");
                mLayoutInScreen.setAccessible(true);
                mLayoutInScreen.set(mPopupWindow, needFullScreen);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
