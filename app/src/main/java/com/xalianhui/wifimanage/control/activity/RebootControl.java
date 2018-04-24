package com.xalianhui.wifimanage.control.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.xalianhui.wifimanage.ui.BaseActivity;
import com.xalianhui.wifimanage.ui.view.MntDialog;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.lang.ref.WeakReference;

import miky.android.common.util.ContextUtil;
import miky.android.common.util.PreferencesUtils;


public class RebootControl extends BaseActivityControl {

//	@ViewInject(R.id.et_wifi_name)
//	private EditText etName;
//	@ViewInject(R.id.et_wifi_pass)
//	private EditText etPassword;
//
//	private MntDialog mntDialog;
	private static final String tag = "RebootControl";
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
		setTitle(getResString(R.string.reboot));
	}
	@Event(value = {R.id.tv_ok})
	private void connWifi(View v) {
		bindDialog = new MntDialog(mActivity, R.style.Theme_dialog, R.layout.dialog_tishi, 235, 135);
		bindDialog.setCanceledOnTouchOutside(false);

		TextView tvTitle = (TextView) bindDialog.findViewById(R.id.tv_dialog_title);
		TextView tvContext = (TextView) bindDialog.findViewById(R.id.tv_context);
		Button btnBind = (Button) bindDialog.findViewById(R.id.btn_bind_router);
		Button btnCancel = (Button) bindDialog.findViewById(R.id.btn_cancel);
//		// 绑定
		tvTitle.setText(getResString(R.string.reboot_router));
		tvContext.setText(getResString(R.string.reboot_router_conetxt));
		btnBind.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bindDialog.dismiss();

				prossDialog = new MntDialog(mActivity, R.style.Theme_dialog, R.layout.dialog_pross, Constants.SMALL_WIDTH, Constants.SMALL_HEIGHT);
				prossDialog.setCanceledOnTouchOutside(false);
				prossDialog.setCancelable(false);
				TextView tv = (TextView) prossDialog.findViewById(R.id.tv_context);
				tv.setText(getResString(R.string.reboot_reboot));
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
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.setReboot_Url);
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if("1".equals(result)){
					Cache.isLoading = false;
					new Thread(){
						@Override
						public void run() {
							try {
								sleep(Constants.RUN_SLEEP_LONG);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							mActivity.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Cache.isLoading = true;
									Cache.isLogin = false;
									if(prossDialog.isShowing() && prossDialog != null){
										prossDialog.dismiss();
										prossDialog = null;
									}
									//fun add 用户重启路由器时用户要输入密码（测试人员小宏说要改）
									PreferencesUtils.putString(mActivity, Consts.KEY_PASSWORD,"");

									new AutoDialog(mActivity,getResString(R.string.set_ok),"").setOnDismissListener(new DialogInterface.OnDismissListener() {
										@Override
										public void onDismiss(DialogInterface dialogInterface) {
											Log.i(tag,"dismiss dialog listener");
											new Handler().postDelayed(new Runnable() {
												@Override
												public void run() {
													Log.i(tag,"finish activity");
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
					Log.i(tag,"RebootControl:reboot fail");
					ShowToast(getResString(R.string.set_fail));
				}
			}
		});
	}
//	private RebootHandler handler = new RebootHandler(mActivity);
//	private class RebootHandler extends Handler{
//		private WeakReference<BaseActivity> weakReference = null;
//		private RebootHandler(BaseActivity baseActivity){
//			weakReference = new WeakReference<BaseActivity>(baseActivity);
//		}
//
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			BaseActivity baseActivity = weakReference.get();
//			if(baseActivity == null){
//				return;
//			}
//			switch (msg.what){
//				case 0:
//					Cache.isLoading = true;
//					prossDialog.dismiss();
//					Log.i("RebootControl","msg.what=0");
//					new AutoDialog(mActivity,getResString(R.string.set_ok),"").setOnDismissListener(new DialogInterface.OnDismissListener() {
//						@Override
//						public void onDismiss(DialogInterface dialogInterface) {
//							//fun add 用户重启路由器时用户要输入密码（测试人员小宏说要改）
//							PreferencesUtils.putString(mActivity, Consts.KEY_PASSWORD,"");
//							PreferencesUtils.putBoolean(mActivity, Consts.KEY_ISFIRST,false);
//							mActivity.finish();
//						}
//					});
//					break;
//				case 1:
//					//新弹出框
//					break;
//			}
//		}
//	}
	//cause memory leak
//	private Handler handler = new Handler(){
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			switch (msg.what){
//				case 0:
//					Log.i(tag,"receive message");
//					Cache.isLoading = true;
//					Cache.isLogin = false;
//					if(prossDialog.isShowing() && prossDialog != null){
//						prossDialog.dismiss();
//						prossDialog = null;
//					}
//                   //fun add 用户重启路由器时用户要输入密码（测试人员小宏说要改）
//					PreferencesUtils.putString(mActivity, Consts.KEY_PASSWORD,"");
//
//					new AutoDialog(mActivity,getResString(R.string.set_ok),"").setOnDismissListener(new DialogInterface.OnDismissListener() {
//						@Override
//						public void onDismiss(DialogInterface dialogInterface) {
//							Log.i(tag,"dismiss dialog listener");
//							handler.postDelayed(new Runnable() {
//								@Override
//								public void run() {
//									Log.i(tag,"finish activity");
//									mActivity.finish();
//								}
//							},100);
//						}
//					});
//					Log.i(tag,"handler last line");
//
//					break;
//				case 1:
//					//新弹出框
//
//					break;
//			}
//		}
//	};




}
