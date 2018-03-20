package com.xalianhui.wifimanage.control.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.xalianhui.wifimanage.ui.activity.SelectLoginActivity;
import com.xalianhui.wifimanage.ui.activity.SetPasswordActivity;
import com.xalianhui.wifimanage.ui.view.MntDialog;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


public class StaticWanControl extends BaseActivityControl {

	@ViewInject(R.id.et_static_dns1)
	private EditText etDns1;
	@ViewInject(R.id.et_static_dns2)
	private EditText etDns2;

	@ViewInject(R.id.et_static_ip)
	private EditText etIp;

	@ViewInject(R.id.et_static_netmask)
	private EditText etNetmask;

	@ViewInject(R.id.et_static_gateway)
	private EditText etGateway;
	@ViewInject(R.id.btn_login)
	private TextView tvBtn;
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
	@Event(value = R.id.btn_login)
	private void login(View v) {
		String ipStr = etIp.getText().toString();
		String netmaskStr = etNetmask.getText().toString();
		String gatewayStr = etGateway.getText().toString();
		String dns1Str = etDns1.getText().toString();
		String dns2Str = etDns2.getText().toString();
		if (ipStr == null || "".equals(ipStr)){
			ShowToast(getResString(R.string.static_input_ip));
			return;
		}
		if (netmaskStr == null || "".equals(netmaskStr)){
			ShowToast(getResString(R.string.static_input_netmask));
			return;
		}
		if (gatewayStr == null || "".equals(gatewayStr)){
			ShowToast(getResString(R.string.static_input_gateway));
			return;
		}
		if (dns1Str == null || "".equals(dns1Str)){
			ShowToast(getResString(R.string.static_input_dns1));
			return;
		}
		setWanHttp(ipStr,netmaskStr,gatewayStr,dns1Str,dns2Str);

	}

	private void initView() {
		setTopView(Consts.TopPage.PAGE2);
		setTitle(getResString(R.string.static_state));
		type =  mActivity.getIntent().getExtras().getInt("type",0);
		if (type == 1){
			tvBtn.setVisibility(View.GONE);
		}else {
			getRightBtn().setVisibility(View.GONE);
		}
		getRightBtn().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(mActivity, SetPasswordActivity.class);
				if (type == 1) {
					String ipStr = etIp.getText().toString();
					String netmaskStr = etNetmask.getText().toString();
					String gatewayStr = etGateway.getText().toString();
					String dns1Str = etDns1.getText().toString();
					String dns2Str = etDns2.getText().toString();
					if (ipStr == null || "".equals(ipStr)) {
						ShowToast(getResString(R.string.static_input_ip));
						return;
					}
					if (netmaskStr == null || "".equals(netmaskStr)) {
						ShowToast(getResString(R.string.static_input_netmask));
						return;
					}
					if (gatewayStr == null || "".equals(gatewayStr)) {
						ShowToast(getResString(R.string.static_input_gateway));
						return;
					}
					if (dns1Str == null || "".equals(dns1Str)) {
						ShowToast(getResString(R.string.static_input_dns1));
						return;
					}
					intent.putExtra("ipaddr",ipStr);
					intent.putExtra("netmask",netmaskStr);
					intent.putExtra("gateway",gatewayStr);
					intent.putExtra("dns1",dns1Str);
					intent.putExtra("dns2",dns2Str);
					intent.putExtra("net_type","static");
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


	private void setWanHttp(String ipStr, String netmaskStr, String gatewayStr, String dns1Str, String dns2Str){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.setWan_Url);
		params.addBodyParameter("ipaddr",ipStr);
		params.addBodyParameter("netmask",netmaskStr);
		params.addBodyParameter("gateway",gatewayStr);
		params.addBodyParameter("dns1",dns1Str);
		params.addBodyParameter("dns2",dns2Str);
		params.addBodyParameter("net_type","static");
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
					Cache.isLoading = true;
					prossDialog.dismiss();
					new AutoDialog(mActivity,getResString(R.string.set_ok),"");
					break;
				case 1:
					//新弹出框

					break;
			}
		}
	};




}
