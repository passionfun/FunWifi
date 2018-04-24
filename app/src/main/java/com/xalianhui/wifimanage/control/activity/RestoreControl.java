package com.xalianhui.wifimanage.control.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.consts.Cache;
import com.xalianhui.wifimanage.consts.Constants;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.control.BaseActivityControl;
import com.xalianhui.wifimanage.dialog.AutoDialog;
import com.xalianhui.wifimanage.function.MyCallBack;
import com.xalianhui.wifimanage.function.MyRequestParams;
import com.xalianhui.wifimanage.ui.BaseActivity;
import com.xalianhui.wifimanage.ui.activity.RestoreActivity;
import com.xalianhui.wifimanage.ui.view.MntDialog;

import org.jsoup.Connection;
import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.lang.ref.WeakReference;

import miky.android.common.util.ContextUtil;
import miky.android.common.util.PreferencesUtils;


public class RestoreControl extends BaseActivityControl {
	private final String tag = "RestoreControl";
//	@ViewInject(R.id.et_wifi_name)
//	private EditText etName;
//	@ViewInject(R.id.et_wifi_pass)
//	private EditText etPassword;
//
//	private MntDialog mntDialog;
private MntDialog bindDialog;
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
		setTitle(getResString(R.string.retore_factory));
	}
	@Event(value = {R.id.tv_ok})
	private void connWifi(View v) {
		bindDialog = new MntDialog(mActivity, R.style.Theme_dialog, R.layout.dialog_tishi, 235, 135);
		bindDialog.setCanceledOnTouchOutside(false);

		Button btnBind = (Button) bindDialog.findViewById(R.id.btn_bind_router);
		Button btnCancel = (Button) bindDialog.findViewById(R.id.btn_cancel);
//		// 绑定
//		tv_message.setText(hint_message);
		btnBind.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bindDialog.dismiss();

				prossDialog = new MntDialog(mActivity, R.style.Theme_dialog, R.layout.dialog_pross, Constants.SMALL_WIDTH, Constants.SMALL_HEIGHT);
				prossDialog.setCanceledOnTouchOutside(false);
				prossDialog.setCancelable(false);
				TextView tv = (TextView) prossDialog.findViewById(R.id.tv_context);
				tv.setText(getResString(R.string.restore_factory_ing));
				prossDialog.show();
				setWanHttp();
			}
		});
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bindDialog.dismiss();
			}
		});
		bindDialog.show();

	}
	private MntDialog prossDialog;
	private void setWanHttp(){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.setFactory_Url);
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				Log.i("RestoreControl","restore result:"+result);
				//解析result
				if("1".equals(result)){
					Cache.isLoading = false;

					new Thread(){
						@Override
						public void run() {
							try {
								Log.i("RestoreControl","begin countdown 30s");
								sleep(Constants.RUN_SLEEP_LONG);
							} catch (InterruptedException e) {
								Log.i("RestoreControl","countdown exception");
								e.printStackTrace();
							}
							Log.i("RestoreControl","30s end .begin send message 0");
							mActivity.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Cache.isLoading = true;
									Cache.isLogin = false;
									if(prossDialog.isShowing() && prossDialog != null){
										prossDialog.dismiss();
										prossDialog = null;
									}
									//fun add 用户对路由器进行恢复出厂设置时用户要输入密码，并且要进入向导页（测试人员小宏说要改）
									PreferencesUtils.putString(mActivity, Consts.KEY_PASSWORD,"");
									new AutoDialog(mActivity,getResString(R.string.set_ok),"").setOnDismissListener(new DialogInterface.OnDismissListener() {
										@Override
										public void onDismiss(DialogInterface dialogInterface) {
											//fun add防止activity比dialog先关闭
											new Handler().postDelayed(new Runnable() {
												@Override
												public void run() {
													mActivity.finish();
												}
											},100);
										}
									});
								}
							});
							//fun remove a line
//							handler.sendEmptyMessage(0);
						}
					}.start();

				}else {
					ShowToast(getResString(R.string.set_fail));
				}
			}
		});
	}
	//cause memory leak
//	private Handler handler = new Handler(){
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			switch (msg.what){
//				case 0:
//					Cache.isLoading = true;
//					Cache.isLogin = false;
//					if(prossDialog.isShowing() && prossDialog != null){
//						prossDialog.dismiss();
//						prossDialog = null;
//					}
//					//fun add 用户对路由器进行恢复出厂设置时用户要输入密码，并且要进入向导页（测试人员小宏说要改）
//					PreferencesUtils.putString(mActivity, Consts.KEY_PASSWORD,"");
////					PreferencesUtils.putBoolean(mActivity, Consts.KEY_ISFIRST,true);
//					new AutoDialog(mActivity,getResString(R.string.set_ok),"").setOnDismissListener(new DialogInterface.OnDismissListener() {
//						@Override
//						public void onDismiss(DialogInterface dialogInterface) {
//							//fun add防止activity比dialog先关闭
//							handler.postDelayed(new Runnable() {
//								@Override
//								public void run() {
//									mActivity.finish();
//								}
//							},100);
//						}
//					});
//					break;
//				case 1:
//
//					break;
//			}
//		}
//	};
}
