package com.xalianhui.wifimanage.control.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.bean.DeviceXQEnry;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.control.BaseActivityControl;
import com.xalianhui.wifimanage.function.MyCallBack;
import com.xalianhui.wifimanage.function.MyRequestParams;
import com.xalianhui.wifimanage.ui.activity.OfflineActivity;
import com.xalianhui.wifimanage.utils.JsonUtil;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.x;


public class JiazDeviceControl extends BaseActivityControl {


	private DeviceXQEnry deviceSta;
	private String mac;
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
		setTitle(getResString(R.string.menu_parental_control));
		mac =  mActivity.getIntent().getExtras().getString("mac");
		getDeviceStatuHttp(mac);
	}





	@Event(value = R.id.ly_select_pppoe)
	 private void select(View v) {
				Intent intent = new Intent(mActivity, OfflineActivity.class);
				intent.putExtra("mac",mac);
				intent.putExtra("deviceSta",deviceSta);
				mActivity.startActivity(intent);
				mActivity.finish();
	 }

	private void getDeviceStatuHttp(String mac){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.getDeviceStuta_Url);
		params.addBodyParameter("mac",mac);
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if(!"".equals(result)){
					DeviceXQEnry records= JsonUtil.getObject(result,DeviceXQEnry.class);
					if ( records != null){
						deviceSta = records;
					}
				}else {
					ShowToast(result);
				}
			}
		});
	}

}
