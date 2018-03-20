package com.xalianhui.wifimanage.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

public class MntDialog extends Dialog {

    public MntDialog(Context context, int theme, int layout) {
        this(context, theme, layout, 160, 120);
    }

    /**
     *
     * @param context
     * @param theme
     * @param layout
     * @param width
     * @param height
     */
    public MntDialog(Context context, int theme, int layout, int width, int height) {
        super(context, theme);
        setContentView(layout);
        Window window = getWindow();
        LayoutParams layoutParams = window.getAttributes();
        float density = context.getResources().getDisplayMetrics().density;
        layoutParams.width = (int) (width * density);
        layoutParams.height = (int) (height * density);
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
    }
}