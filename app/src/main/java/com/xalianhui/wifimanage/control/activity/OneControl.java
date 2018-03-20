package com.xalianhui.wifimanage.control.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.consts.Cache;
import com.xalianhui.wifimanage.consts.Constants;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.control.BaseActivityControl;
import com.xalianhui.wifimanage.dialog.AutoDialog;
import com.xalianhui.wifimanage.function.MyCallBack;
import com.xalianhui.wifimanage.function.MyRequestParams;
import com.xalianhui.wifimanage.ui.view.MntDialog;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.x;


public class OneControl extends BaseActivityControl {

//	@ViewInject(R.id.et_wifi_name)
//	private EditText etName;
//	@ViewInject(R.id.et_wifi_pass)
//	private EditText etPassword;
//
//	private MntDialog mntDialog;
	@Override
	public void onInit(View rootView, Context context) {
		super.onInit(rootView, context);
		x.view().inject(this, rootView);
		initView();
	}


    @Override
	public void onRecycle() {

	}


	private void initView() {
		setTopView(Consts.TopPage.PAGE);
		setTitle(getResString(R.string.rocket_boost));
	}
	@Event(value = {R.id.tv_ok})
	private void connWifi(View v) {
		setWanHttp();
	}
	private MntDialog prossDialog;
	private void setWanHttp(){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.setOne_Url);
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if("1".equals(result)){
					Cache.isLoading = false;
					prossDialog = new MntDialog(mActivity, R.style.Theme_dialog, R.layout.dialog_pross, Constants.SMALL_WIDTH, Constants.SMALL_HEIGHT);
					prossDialog.setCanceledOnTouchOutside(false);
					prossDialog.setCancelable(false);
					TextView tv = (TextView) prossDialog.findViewById(R.id.tv_context);
					tv.setText(getResString(R.string.rocket_boost_ing));
					prossDialog.show();
					new Thread(){
						@Override
						public void run() {
							try {
								sleep(Constants.RUN_SLEEP_LONG);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							handler.sendEmptyMessage(0);
						}
					}.start();

				}else {
					ShowToast(result);
				}
			}
		});
	}

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what){
				case 0:
					Cache.isLoading = true;
					prossDialog.dismiss();
					new AutoDialog(mActivity,getResString(R.string.set_ok),"").setOnDismissListener(new DialogInterface.OnDismissListener() {
						@Override
						public void onDismiss(DialogInterface dialogInterface) {
							mActivity.finish();
						}
					});

					break;
				case 1:
					//新弹出框

					break;
			}
		}
	};






}
