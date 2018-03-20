package com.xalianhui.wifimanage.control.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.control.BaseActivityControl;
import com.xalianhui.wifimanage.dialog.AutoDialog;
import com.xalianhui.wifimanage.function.MyCallBack;
import com.xalianhui.wifimanage.function.MyRequestParams;
import com.xalianhui.wifimanage.ui.activity.HighSetActivity;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import miky.android.common.util.PreferencesUtils;


public class AdminPassControl extends BaseActivityControl {

	@ViewInject(R.id.et_password_old)
	private EditText etPasswordOld;

	@ViewInject(R.id.iv_password_old)
	private ImageView ivPasswordOld;

	@ViewInject(R.id.et_password)
	private EditText etPassword;

	@ViewInject(R.id.iv_password)
	private ImageView ivPassword;

	@ViewInject(R.id.et_password2)
	private EditText etPassword2;

	@ViewInject(R.id.iv_password2)
	private ImageView ivPassword2;


	@Override
	public void onInit(View rootView, Context context) {
		super.onInit(rootView, context);
		x.view().inject(this, rootView);
		initView();
	}


    @Override
	public void onRecycle() {

	}
	@Event(value = R.id.iv_password_old)
	private void setPassword(View v) {
		String passStr = etPasswordOld.getText().toString();
		if (ivPasswordOld.isSelected()){
			ivPasswordOld.setSelected(false);
			etPasswordOld.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}else {
			ivPasswordOld.setSelected(true);
			etPasswordOld.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		}
		etPasswordOld.setSelection(passStr.length());
	}
	@Event(value = R.id.iv_password)
	private void setPassword2(View v) {
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
	@Event(value = R.id.iv_password2)
	private void setPassword3(View v) {
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
	@Event(value = R.id.tv_ok)
	private void onClick(View v) {
		String adminOld = etPasswordOld.getText().toString();
		String adminPass = etPassword.getText().toString();
		String adminPass2 = etPassword2.getText().toString();
		if (adminPass == null || "".equals(adminPass)){
			ShowToast(getResString(R.string.input_password2));
			return;
		}
		if (adminPass != null && !adminPass.equals(adminPass2)){
			ShowToast(getResString(R.string.input_admin_password));
			return;
		}
		loginHttp(adminOld,adminPass);
	}


	private void initView() {
		setTopView(Consts.TopPage.PAGE);
		setTitle(getResString(R.string.menu_management));
		ivPasswordOld.setSelected(false);
		ivPassword.setSelected(false);
		ivPassword2.setSelected(false);
	}
	@Event(value = {R.id.ly_setwifi_high,R.id.ly_setwifi_high2})
	private void connWifi(View v) {
		switch (v.getId()){
			case R.id.ly_setwifi_high:
				Intent intent = new Intent(mActivity, HighSetActivity.class);
				mActivity.startActivity(intent);
				break;
			case R.id.ly_setwifi_high2:
				Intent intent2 = new Intent(mActivity, HighSetActivity.class);
				mActivity.startActivity(intent2);
				break;
		}
	}

	private void loginHttp(String adminOld, final String password){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.setAdminPassword_Url);
		params.addBodyParameter("old_password",adminOld);
		params.addBodyParameter("new_password1",password);
		params.addBodyParameter("new_password2",password);
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if("1".equals(result)){
					PreferencesUtils.putString(mActivity, Consts.KEY_PASSWORD,password);
					new AutoDialog(mActivity,getResString(R.string.set_ok),"").setOnDismissListener(new DialogInterface.OnDismissListener() {
						@Override
						public void onDismiss(DialogInterface dialogInterface) {
							mActivity.finish();
						}
					});
				}else {
					ShowToast(result);
				}
			}
		});
	}





}
