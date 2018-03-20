package com.xalianhui.wifimanage.control.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.consts.Cache;
import com.xalianhui.wifimanage.consts.Constants;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.control.BaseActivityControl;
import com.xalianhui.wifimanage.dialog.AutoDialog;
import com.xalianhui.wifimanage.function.MyCallBack;
import com.xalianhui.wifimanage.function.MyRequestParams;
import com.xalianhui.wifimanage.ui.activity.BroadbandActivity;
import com.xalianhui.wifimanage.ui.view.MntDialog;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import miky.android.common.util.PreferencesUtils;


public class SetPasswordControl extends BaseActivityControl {

	@ViewInject(R.id.et_wifi_name)
	private EditText etWifiName;
	@ViewInject(R.id.et_wifi_admin_pass)
	private EditText etAdminPass;
	@ViewInject(R.id.et_wifi_admin_pass2)
	private EditText etAdminPass2;
	@ViewInject(R.id.et_wifi_pass)
	private EditText etWifiPass;
	@ViewInject(R.id.et_wifi_pass2)
	private EditText etWifiPass2;

	@ViewInject(R.id.iv_wifi_admin_pass)
	private ImageView ivAdminPass;
	@ViewInject(R.id.iv_wifi_admin_pass2)
	private ImageView ivAdminPass2;
	@ViewInject(R.id.iv_wifi_pass2)
	private ImageView ivWifiPass2;
	@ViewInject(R.id.iv_wifi_pass)
	private ImageView ivWifiPass;
	@ViewInject(R.id.checkBox)
	private CheckBox cbPass;

	@ViewInject(R.id.ly_admin_password)
	private LinearLayout lyWifiPass;
	private MntDialog prossDialog;
	private boolean isShowWifiP;
	private int type;
	@Override
	public void onInit(View rootView, Context context) {
		super.onInit(rootView, context);
		x.view().inject(this, rootView);
		initView();
	}


    @Override
	public void onRecycle() {

	}
	@Event(value = {R.id.iv_wifi_admin_pass,R.id.iv_wifi_admin_pass2,R.id.iv_wifi_pass, R.id.iv_wifi_pass2})
	private void setPassword(View v) {
		switch (v.getId()){
			case R.id.iv_wifi_admin_pass:
				setPassword(etAdminPass,ivAdminPass);
				break;
			case R.id.iv_wifi_admin_pass2:
				setPassword(etAdminPass2,ivAdminPass2);
				break;
			case R.id.iv_wifi_pass:
				setPassword(etWifiPass,ivWifiPass);
				break;
			case R.id.iv_wifi_pass2:
				setPassword(etWifiPass2,ivWifiPass2);
				break;
		}

	}
	private void setPassword(EditText tv,ImageView iv) {
		String passStr = tv.getText().toString();
		if (iv.isSelected()){
			iv.setSelected(false);
			tv.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}else {
			iv.setSelected(true);
			tv.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		}
		tv.setSelection(passStr.length());
	}
	@Event(value = R.id.btn_login)
	private void login(View v) {
		String wifiName = etWifiName.getText().toString();
		String adminPass = etAdminPass.getText().toString();
		String adminPass2 = etAdminPass2.getText().toString();
		String wifiPass = etWifiPass.getText().toString();
		String wifiPass2 = etWifiPass2.getText().toString();

		if (wifiName == null || "".equals(wifiName)){
			ShowToast(getResString(R.string.input_wifi_name));
			return;
		}
		if (adminPass == null || "".equals(adminPass)){
			ShowToast(getResString(R.string.input_password_wifi));
			return;
		}
		if (adminPass != null && !adminPass.equals(adminPass2)){
			ShowToast(getResString(R.string.input_wifi_password));
			return;
		}

		if (!isShowWifiP){
			if (wifiPass == null || "".equals(wifiPass)){
				ShowToast(getResString(R.string.input_password));
				return;
			}
			if (wifiPass != null && !wifiPass.equals(wifiPass2)){
				ShowToast(getResString(R.string.input_admin_password));
				return;
			}
		}else {
			wifiPass = adminPass;
		}
		loginHttp(wifiPass);
		if (type == 1){
			setWanHttp2(wifiName,adminPass);
		}else {
			setWanHttp(wifiName,adminPass);
		}

	}

	private void initView() {
		setTopView(Consts.TopPage.PAGE);
		setTitle(getResString(R.string.set_password));
		type =  mActivity.getIntent().getExtras().getInt("type",0);
		cbPass.setChecked(true);
		lyWifiPass.setVisibility(View.INVISIBLE);
		isShowWifiP = true;
		cbPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isShowWifiP = isChecked;
				if (isChecked){
					lyWifiPass.setVisibility(View.INVISIBLE);
				}else {
					lyWifiPass.setVisibility(View.VISIBLE);
				}
			}
		});
		getLeftBtn().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mActivity, BroadbandActivity.class);
				mActivity.startActivity(intent);
				mActivity.finish();
			}
		});
	}
	private void loginHttp(final String password){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.setAdminPassword_Url);
		String passwordOld = PreferencesUtils.getString(mActivity, Consts.KEY_PASSWORD,"");
		params.addBodyParameter("old_password",passwordOld);
		params.addBodyParameter("new_password1",password);
		params.addBodyParameter("new_password2",password);
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if("1".equals(result)){
					PreferencesUtils.putString(mActivity, Consts.KEY_PASSWORD,password);
				}else {
					ShowToast(result);
				}
			}
		});
	}
	private void setWanHttp2(String wifiName, String wifipass){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.setQuick_Url);
		String net_type = mActivity.getIntent().getExtras().getString("net_type");
		if ("dhcp".equals(net_type)){
			params.addBodyParameter("net_type","dhcp");
		}else if ("pppoe".equals(net_type)){
			String name = mActivity.getIntent().getExtras().getString("username");
			String password = mActivity.getIntent().getExtras().getString("password");
			params.addBodyParameter("username",name);
			params.addBodyParameter("password",password);
			params.addBodyParameter("net_type","pppoe");
		}else if("static".equals(net_type)){
			String ipaddr = mActivity.getIntent().getExtras().getString("ipaddr");
			String netmask = mActivity.getIntent().getExtras().getString("netmask");
			String gateway = mActivity.getIntent().getExtras().getString("gateway");
			String dns1 = mActivity.getIntent().getExtras().getString("dns1");
			String dns2 = mActivity.getIntent().getExtras().getString("dns2");
			params.addBodyParameter("ipaddr",ipaddr);
			params.addBodyParameter("netmask",netmask);
			params.addBodyParameter("gateway",gateway);
			params.addBodyParameter("dns1",dns1);
			params.addBodyParameter("dns2",dns2);
			params.addBodyParameter("net_type","static");
		}
		params.addBodyParameter("ssid_1",wifiName);
		params.addBodyParameter("password_1",wifipass);
		params.addBodyParameter("enable_wireless_1","1");
		params.addBodyParameter("ssid_2",wifiName);
		params.addBodyParameter("password_2",wifipass);
		params.addBodyParameter("enable_wireless_2","1");
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
								sleep(Constants.RUN_SLEEP);
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
	private void setWanHttp(String wifiName, String wifipass){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.swtWifiPass_Url);
		params.addBodyParameter("ssid_1",wifiName);
		params.addBodyParameter("password_1",wifipass);
		params.addBodyParameter("enable_wireless_1","1");
		params.addBodyParameter("ssid_2",wifiName);
		params.addBodyParameter("password_2",wifipass);
		params.addBodyParameter("enable_wireless_2","1");
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
								sleep(Constants.RUN_SLEEP);
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
