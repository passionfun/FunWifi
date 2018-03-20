package com.xalianhui.wifimanage.control.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.bean.NewVersion;
import com.xalianhui.wifimanage.consts.Cache;
import com.xalianhui.wifimanage.consts.Constants;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.control.BaseActivityControl;
import com.xalianhui.wifimanage.dialog.AutoDialog;
import com.xalianhui.wifimanage.function.MyCallBack;
import com.xalianhui.wifimanage.function.MyRequestParams;
import com.xalianhui.wifimanage.ui.view.MntDialog;
import com.xalianhui.wifimanage.utils.JsonUtil;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import static android.R.attr.password;


public class FirmWareControl extends BaseActivityControl {

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
		setTitle(getResString(R.string.menu_upgrade_firmware));
	}
	@Event(value = {R.id.tv_ok})
	private void connWifi(View v) {
		setWanHttp();
	}
	private MntDialog prossDialog;
	private void setWanHttp(){
		prossDialog = new MntDialog(mActivity, R.style.Theme_dialog, R.layout.dialog_pross_black, Constants.SMALL_WIDTH, Constants.SMALL_HEIGHT);
		prossDialog.setCanceledOnTouchOutside(false);
		prossDialog.setCancelable(false);
		TextView tv = (TextView) prossDialog.findViewById(R.id.tv_context);
		tv.setText(getResString(R.string.check_new_version));
		prossDialog.show();
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.upgrade_Url);
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				if(!"".equals(result)){
					NewVersion mVersion = JsonUtil.getObject(result,NewVersion.class);
					if(mVersion != null){
						if ("1".equals(mVersion.getIs_has_new())){
							newVersion(mVersion);
						}else {
							new AutoDialog(mActivity,getResString(R.string.not_new_version),"",1);
						}
					}
				}else {
					ShowToast(result);
				}
			}

			@Override
			public void onFinished() {
				prossDialog.dismiss();
			}
		});
	}
	private void newVersion(final NewVersion mVersion) {
		bindDialog = new MntDialog(mActivity, R.style.Theme_dialog, R.layout.dialog_tishi_black, 235, 135);
		bindDialog.setCanceledOnTouchOutside(false);
		TextView tvTitle = (TextView) bindDialog.findViewById(R.id.tv_dialog_title);
		TextView tvContext = (TextView) bindDialog.findViewById(R.id.tv_context);
		tvTitle.setVisibility(View.INVISIBLE);
		String testStr = mActivity.getResources().getString(R.string.get_new_version);
		String result = String.format(testStr,mVersion.getVersion());
		tvContext.setText(result);
		Button btnBind = (Button) bindDialog.findViewById(R.id.btn_bind_router);
		Button btnCancel = (Button) bindDialog.findViewById(R.id.btn_cancel);
		btnBind.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bindDialog.dismiss();
				logonRouter(mVersion);
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
	private void logonRouter(NewVersion mVersion) {
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.upgrade_up_Url);
		params.addBodyParameter("download_url",mVersion.getDown_url());
		params.addBodyParameter("firmware_md5sum",mVersion.getMd5sum());
		params.addBodyParameter("firmware_version",mVersion.getVersion());
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if("1".equals(result)){
					Cache.isLoading = false;
					prossDialog = new MntDialog(mActivity, R.style.Theme_dialog, R.layout.dialog_pross, Constants.SMALL_WIDTH, Constants.SMALL_HEIGHT);
					prossDialog.setCanceledOnTouchOutside(false);
					prossDialog.setCancelable(false);
					prossDialog.show();
					new Thread(){
						@Override
						public void run() {
							try {
								sleep(Constants.RUN_SLEEP_UP);
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
