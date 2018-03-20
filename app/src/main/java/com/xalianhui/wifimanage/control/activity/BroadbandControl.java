package com.xalianhui.wifimanage.control.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.consts.Cache;
import com.xalianhui.wifimanage.consts.Constants;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.control.BaseActivityControl;
import com.xalianhui.wifimanage.function.MyCallBack;
import com.xalianhui.wifimanage.function.MyRequestParams;
import com.xalianhui.wifimanage.ui.activity.SelectLoginActivity;
import com.xalianhui.wifimanage.ui.activity.SetPasswordActivity;
import com.xalianhui.wifimanage.ui.view.MntDialog;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


public class BroadbandControl extends BaseActivityControl {

	@ViewInject(R.id.et_login_password)
	private EditText etPassword;

	@ViewInject(R.id.et_login_name)
	private EditText etName;

	@ViewInject(R.id.iv_password)
	private ImageView ivPassword;

	@ViewInject(R.id.btn_login)
	private TextView tvBtn;

	private MntDialog mntDialog;
	private MntDialog prossDialog;
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
	@Event(value = R.id.iv_password)
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
	@Event(value = R.id.btn_login)
	private void login(View v) {
		String passStr = etPassword.getText().toString();
		String nameStr = etName.getText().toString();
		if (nameStr == null || "".equals(nameStr)){
			ShowToast(getResString(R.string.input_name));
			return;
		}
		if (passStr == null || "".equals(passStr)){
			ShowToast(getResString(R.string.input_password2));
			return;
		}
		setWanHttp(nameStr,passStr);

	}

	private void initView() {
		setTopView(Consts.TopPage.PAGE2);
		setTitle(getResString(R.string.broadband_conn));
		type =  mActivity.getIntent().getExtras().getInt("type",0);
		if (type == 1){
			tvBtn.setVisibility(View.GONE);
		}else {
			getRightBtn().setVisibility(View.GONE);
		}
		ivPassword.setSelected(false);
		getRightBtn().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(mActivity, SetPasswordActivity.class);
				if (type == 1){
					String passStr = etPassword.getText().toString();
					String nameStr = etName.getText().toString();
					if (nameStr == null || "".equals(nameStr)){
						ShowToast(getResString(R.string.input_name));
						return;
					}
					if (passStr == null || "".equals(passStr)){
						ShowToast(getResString(R.string.input_password2));
						return;
					}
					intent.putExtra("username",nameStr);
					intent.putExtra("password",passStr);
					intent.putExtra("net_type","pppoe");
					intent.putExtra("type",type);

				}
				mActivity.startActivity(intent);
				mActivity.finish();
			}
		});
		getLeftBtn().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mActivity, SelectLoginActivity.class);
				mActivity.startActivity(intent);
				mActivity.finish();
			}
		});
	}


	private void setWanHttp(String name,String password){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.setWan_Url);
		params.addBodyParameter("username",name);
		params.addBodyParameter("password",password);
		params.addBodyParameter("net_type","pppoe");
//		params.addParameter("password","123");
//		params.addHeader("head","android"); //为当前请求添加一个头
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
					prossDialog.dismiss();
					Cache.isLoading = true;
					mntDialog = new MntDialog(mActivity, R.style.Theme_dialog, R.layout.dialog_prompt, Constants.SMALL_WIDTH, Constants.SMALL_HEIGHT);
					mntDialog.setCanceledOnTouchOutside(true);
					mntDialog.show();
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							mntDialog.dismiss();
						}
					}, Constants.DIALOG_SHOW_TIME);
					break;
				case 1:
					//新弹出框

					break;
			}
		}
	};




}
