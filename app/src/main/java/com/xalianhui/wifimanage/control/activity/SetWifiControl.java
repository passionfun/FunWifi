package com.xalianhui.wifimanage.control.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.bean.WifiCfgEnry;
import com.xalianhui.wifimanage.consts.Cache;
import com.xalianhui.wifimanage.consts.Constants;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.control.BaseActivityControl;
import com.xalianhui.wifimanage.dialog.AutoDialog;
import com.xalianhui.wifimanage.function.MyCallBack;
import com.xalianhui.wifimanage.function.MyRequestParams;
import com.xalianhui.wifimanage.ui.activity.HighSetActivity;
import com.xalianhui.wifimanage.ui.view.MntDialog;
import com.xalianhui.wifimanage.ui.view.SwitchButton;
import com.xalianhui.wifimanage.utils.JsonUtil;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


public class SetWifiControl extends BaseActivityControl {
	public static final String tag = SetWifiControl.class.getSimpleName();
	@ViewInject(R.id.et_wifi_name)
	private EditText etName;
	@ViewInject(R.id.et_wifi_pass)
	private EditText etPassword;

	@ViewInject(R.id.iv_wifi_pass)
	private ImageView ivPassword;
	@ViewInject(R.id.et_wifi_name2)
	private EditText etName2;

	@ViewInject(R.id.et_wifi_pass2)
	private EditText etPassword2;

	@ViewInject(R.id.iv_wifi_pass2)
	private ImageView ivPassword2;
	private SwitchButton mShockSet ;
	private SwitchButton mShockSet2 ;
	private WifiCfgEnry wifiCfgEnry;
	@Override
	public void onInit(View rootView, Context context) {
		super.onInit(rootView, context);
		x.view().inject(this, rootView);
		initView();
	}


    @Override
	public void onRecycle() {

	}
	@Event(value = R.id.iv_wifi_pass)
	private void setPassword(View v) {
		String passStr = etPassword.getText().toString();
		if (ivPassword.isSelected()){
			ivPassword.setSelected(false);
			etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}else {
			ivPassword.setSelected(true);
			etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		}
		etPassword.setSelection(passStr.length());
	}
	@Event(value = R.id.iv_wifi_pass2)
	private void setPassword2(View v) {
		String passStr = etPassword2.getText().toString();
		if (ivPassword2.isSelected()){
			ivPassword2.setSelected(false);
			etPassword2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}else {
			ivPassword2.setSelected(true);
			etPassword2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		}
		etPassword2.setSelection(passStr.length());
	}

	private void initView() {
		setTopView(Consts.TopPage.PAGE);
		setTitle(getResString(R.string.wifi_setting));
		ivPassword.setSelected(false);
		ivPassword2.setSelected(false);
		getRightBtn().setText(getResString(R.string.wifi_com));
		getRightBtn().setVisibility(View.VISIBLE);
		getRightBtn().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				setWanHttp();
			}
		});
		mShockSet = (SwitchButton) mRootView
				.findViewById(R.id.msgpush_voiceset);
		mShockSet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
										 boolean isChecked) {
				if (isChecked){
					etName.setEnabled(false);
					etPassword.setEnabled(false);
					ivPassword.setEnabled(false);
				}else {
					etName.setEnabled(true);
					etPassword.setEnabled(true);
					ivPassword.setEnabled(true);
				}
			}
		});
		mShockSet2 = (SwitchButton) mRootView
				.findViewById(R.id.msgpush_voiceset2);
		mShockSet2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
										 boolean isChecked) {
				if (isChecked){
					etName2.setEnabled(false);
					etPassword2.setEnabled(false);
					ivPassword2.setEnabled(false);
				}else {
					etName2.setEnabled(true);
					etPassword2.setEnabled(true);
					ivPassword2.setEnabled(true);
				}
			}
		});
		getDeviceStatuHttp();
	}
	@Event(value = {R.id.ly_setwifi_high,R.id.ly_setwifi_high2})
	private void connWifi(View v) {
		switch (v.getId()){
			case R.id.ly_setwifi_high:
				Intent intent = new Intent(mActivity, HighSetActivity.class);
				intent.putExtra("index",1);
				mActivity.startActivity(intent);
				break;
			case R.id.ly_setwifi_high2:
				Intent intent2 = new Intent(mActivity, HighSetActivity.class);
				intent2.putExtra("index",2);
				mActivity.startActivity(intent2);
				break;
		}
	}

	private MntDialog prossDialog;
	private void setWanHttp(){
		String wifiName = "";
		String adminPass = "";
		String wifiName2 = "";
		String adminPass2 = "";
		String enable = "0";
		String enable2 = "0";
        wifiName = etName.getText().toString();
        adminPass = etPassword.getText().toString();
        if (!mShockSet.isChecked()) {
		enable = "1";
		if (wifiName == null || "".equals(wifiName)) {
			ShowToast(getResString(R.string.input_wifi_name));
			return;
		}
//		if (adminPass == null || "".equals(adminPass)) {
//			ShowToast(getResString(R.string.input_password2));
//			return;
//		}
	}
        wifiName2 = etName2.getText().toString();
        adminPass2 = etPassword2.getText().toString();
        if (!mShockSet2.isChecked()) {
        enable2 = "1";
		if (wifiName2 == null || "".equals(wifiName2)) {
			ShowToast(getResString(R.string.input_wifi_name));
			return;
		}
//		if (adminPass2 == null ||"".equals(adminPass2)) {
//			ShowToast(getResString(R.string.input_password2));
//			return;
//		}
	}
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.swtWifiPass_Url);
		params.addBodyParameter("ssid_1",wifiName);
		params.addBodyParameter("password_1",adminPass);
		params.addBodyParameter("enable_wireless_1",enable);
		params.addBodyParameter("ssid_2",wifiName2);
		params.addBodyParameter("password_2",adminPass2);
		params.addBodyParameter("enable_wireless_2",enable2);
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

	private void getDeviceStatuHttp(){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.getWifiCfg_Url);
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if(!"".equals(result)){
					WifiCfgEnry records= JsonUtil.getObject(result,WifiCfgEnry.class);
					if ( records != null){
						wifiCfgEnry = records;
						//fun add 1、不管2.4G开关是否打开都会显示当前wifi的名称和密码（有则显示，无则不显示）
						//2、不管5G开关是否打开都会显示当前wifi的名称和密码（有则显示，无则不显示）
						etName.setText(wifiCfgEnry.getSsid_1());
						etPassword.setText(wifiCfgEnry.getPassword_1());
						etName2.setText(wifiCfgEnry.getSsid_2());
						etPassword2.setText(wifiCfgEnry.getPassword_2());

						if ("0".equals(wifiCfgEnry.getEnable_wireless_1())) {
							mShockSet.setChecked(true);
						}else {
							mShockSet.setChecked(false);
							//fun add修改
//							etName.setText(wifiCfgEnry.getSsid_1());
//							etPassword.setText(wifiCfgEnry.getPassword_1());
						}
						if ("0".equals(wifiCfgEnry.getEnable_wireless_2())) {
							mShockSet2.setChecked(true);
						}else {
							mShockSet2.setChecked(false);
							//fun add修改
//							etName2.setText(wifiCfgEnry.getSsid_2());
//							etPassword2.setText(wifiCfgEnry.getPassword_2());
						}
					}
				}else {
					ShowToast(result);
				}
			}
		});
	}



}
