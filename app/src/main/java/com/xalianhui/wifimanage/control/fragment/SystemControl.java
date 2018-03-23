package com.xalianhui.wifimanage.control.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.bean.LedBean;
import com.xalianhui.wifimanage.bean.WifiHightCfgEnry;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.control.BaseFragmentControl;
import com.xalianhui.wifimanage.control.activity.RestoreControl;
import com.xalianhui.wifimanage.dialog.AutoDialog;
import com.xalianhui.wifimanage.function.MyCallBack;
import com.xalianhui.wifimanage.function.MyRequestParams;
import com.xalianhui.wifimanage.ui.activity.AdminPasswActivity;
import com.xalianhui.wifimanage.ui.activity.FirmWareActivity;
import com.xalianhui.wifimanage.ui.activity.NetSetActivity;
import com.xalianhui.wifimanage.ui.activity.RebootActivity;
import com.xalianhui.wifimanage.ui.activity.RestoreActivity;
import com.xalianhui.wifimanage.ui.activity.SelectLoginActivity;
import com.xalianhui.wifimanage.ui.activity.SetWifiActivity;
import com.xalianhui.wifimanage.ui.view.SwitchButton;
import com.xalianhui.wifimanage.utils.JsonUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


public class SystemControl extends BaseFragmentControl {
    @ViewInject(R.id.tv_title)
    private TextView tvTitle;
	private SwitchButton mShockSet ;


	@Override
	public void onInit(View rootView, Context context) {
		super.onInit(rootView, context);
		x.view().inject(this, rootView);
      initView();
	}

	private void initView() {
		tvTitle.setText(getResString(R.string.blow_system_setting));
		mShockSet = (SwitchButton) mRootView
				.findViewById(R.id.msgpush_voiceset);
		getDeviceStatuHttp();
    }
	@Event(value = {R.id.ly_system_wifi,R.id.ly_system_net,R.id.ly_system_password,R.id.ly_system_reboot,R.id.ly_system_restore,R.id.ly_system_version})
	private void connWifi(View v) {
		Intent intent;
		switch (v.getId()){
			case R.id.ly_system_wifi:
				intent = new Intent(mActivity, SetWifiActivity.class);
				mActivity.startActivity(intent);
				break;
			case R.id.ly_system_net:
				intent = new Intent(mActivity, NetSetActivity.class);
				mActivity.startActivity(intent);
				break;
			case R.id.ly_system_password:
				intent = new Intent(mActivity, AdminPasswActivity.class);
				mActivity.startActivity(intent);
				break;
			case R.id.ly_system_reboot:
				intent = new Intent(mActivity, RebootActivity.class);
				mActivity.startActivity(intent);
				break;
			case R.id.ly_system_restore:
				intent = new Intent(mActivity, RestoreActivity.class);
				mActivity.startActivity(intent);
				break;
			case R.id.ly_system_version:
				intent = new Intent(mActivity, FirmWareActivity.class);
				mActivity.startActivity(intent);
				break;
		}
	}
	private void getDeviceStatuHttp(){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.get_led_Url);
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if(!"".equals(result)){
					LedBean records= JsonUtil.getObject(result,LedBean.class);
					if ( records != null){
							if ("0".equals(records.getLed_switch())){
								mShockSet.setChecked(true);
							}else {
								mShockSet.setChecked(false);
							}
						mShockSet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(CompoundButton buttonView,
														 boolean isChecked) {
								if (isChecked){
									setLedHttp(0);
								}else {
									setLedHttp(1);
								}
							}
						});
					}
				}else {
					ShowToast(result);
				}
			}
		});
	}
	private void setLedHttp( int type){

		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP + Icont.set_led_Url);
		params.addBodyParameter("led_switch", type+"");
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if("1".equals(result)){
					new AutoDialog(mActivity,getResString(R.string.set_ok),"");
				}else {
					ShowToast(result);
				}
			}
		});
	}
	@Override
	public void onRecycle() {
		// TODO Auto-generated method stub

	}


	public void Refresh() {
		initView();
	}
}
