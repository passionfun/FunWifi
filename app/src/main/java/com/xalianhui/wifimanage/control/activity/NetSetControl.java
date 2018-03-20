package com.xalianhui.wifimanage.control.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.bean.NetCfgEnry;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.control.BaseActivityControl;
import com.xalianhui.wifimanage.function.MyCallBack;
import com.xalianhui.wifimanage.function.MyRequestParams;
import com.xalianhui.wifimanage.ui.activity.SelectLoginActivity;
import com.xalianhui.wifimanage.utils.JsonUtil;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


public class NetSetControl extends BaseActivityControl {

	@ViewInject(R.id.tv_net_status)
	private TextView tvNetStatus;
	@ViewInject(R.id.tv_net_type)
	private TextView tvNetType;
	@ViewInject(R.id.tv_net_ip)
	private TextView tvNetIP;
	@ViewInject(R.id.tv_net_gateway)
	private TextView tvNetGateway;
//	@ViewInject(R.id.et_wifi_pass)
//	private EditText etPassword;
//
//	private MntDialog mntDialog;
	private  NetCfgEnry netCfgEnry;
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
		setTitle(getResString(R.string.menu_network));
		getDeviceStatuHttp();
	}
	@Event(value = {R.id.ly_high_encrypt2})
	private void connWifi(View v) {
		switch (v.getId()){
			case R.id.ly_high_encrypt2:
				Intent intent = new Intent(mActivity, SelectLoginActivity.class);
				mActivity.startActivity(intent);
				break;
			case R.id.ly_setwifi_high2:

				break;
		}
	}
	private void getDeviceStatuHttp(){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.getWanCfg_Url);
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if(!"".equals(result)){
					NetCfgEnry records= JsonUtil.getObject(result,NetCfgEnry.class);
					if ( records != null){
						netCfgEnry = records;
						if ("on_line".equals(netCfgEnry.getWan_state())){
							tvNetStatus.setText(getResString(R.string.network_internet_ok));
						}else {
							tvNetStatus.setText(getResString(R.string.network_internet_not));
						}
						if ("dhcp".equals(netCfgEnry.getNet_type())){
							tvNetType.setText(getResString(R.string.network_dymanic_ip));
						}else if ("static".equals(netCfgEnry.getNet_type())){
							tvNetType.setText(getResString(R.string.static_state));
						}else {
							tvNetType.setText(getResString(R.string.pppoe));
						}
						if (netCfgEnry.getRouter_ip()!= null&&!"".equals(netCfgEnry.getRouter_ip())) {
							tvNetIP.setText("IP:" + netCfgEnry.getRouter_ip());
						}
						if (netCfgEnry.getRouter_gateway()!= null&&!"".equals(netCfgEnry.getRouter_gateway())) {
							tvNetGateway.setText("IP:" + netCfgEnry.getRouter_gateway());
						}
					}
				}else {
					ShowToast(result);
				}
			}
		});
	}






}
