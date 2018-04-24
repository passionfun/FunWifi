package com.xalianhui.wifimanage.dialog;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.consts.Constants;
import com.xalianhui.wifimanage.ui.view.MntDialog;



public class AutoDialog extends MntDialog {

    public AutoDialog(Context context,String title,String content) {
        super(context,  R.style.Theme_dialog, R.layout.dialog_prompt, Constants.SMALL1_WIDTH, Constants.SMALL1_HEIGHT );
        this.setCanceledOnTouchOutside(false);
        this.setCancelable(false);
        TextView tvTitle = (TextView) this.findViewById(R.id.tv_title);
        TextView tvContext = (TextView) this.findViewById(R.id.tv_context);
        tvTitle.setText(title);
        if ("".equals(content)){
            tvContext.setVisibility(View.GONE);
        }else {
            tvContext.setText(content);
        }
        show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AutoDialog.this.dismiss();
            }
        }, Constants.DIALOG_SHOW_TIME);
    }
    public AutoDialog(Context context,String title,String content,int type) {
        super(context,  R.style.Theme_dialog, R.layout.dialog_prompt_black, Constants.SMALL1_WIDTH, Constants.SMALL1_HEIGHT );
        this.setCanceledOnTouchOutside(true);
        TextView tvTitle = (TextView) this.findViewById(R.id.tv_title);
        TextView tvContext = (TextView) this.findViewById(R.id.tv_context);
        tvTitle.setText(title);
        if ("".equals(content)){
            tvContext.setVisibility(View.GONE);
        }else {
            tvContext.setText(content);
        }
        show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AutoDialog.this.dismiss();
            }
        }, Constants.DIALOG_SHOW_TIME);
    }
}
