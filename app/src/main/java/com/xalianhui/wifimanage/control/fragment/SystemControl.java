package com.xalianhui.wifimanage.control.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.bean.IsNewRouter;
import com.xalianhui.wifimanage.bean.LedBean;
import com.xalianhui.wifimanage.bean.WifiHightCfgEnry;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.control.BaseFragmentControl;
import com.xalianhui.wifimanage.control.activity.RestoreControl;
import com.xalianhui.wifimanage.dialog.AutoDialog;
import com.xalianhui.wifimanage.function.IsRouteHelp;
import com.xalianhui.wifimanage.function.MyCallBack;
import com.xalianhui.wifimanage.function.MyRequestParams;
import com.xalianhui.wifimanage.interfaces.OnHttpSelector;
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

import javax.xml.parsers.FactoryConfigurationError;


public class SystemControl extends BaseFragmentControl {
	private static final String tag = "SystemControl";
    @ViewInject(R.id.tv_title)
    private TextView tvTitle;
    @ViewInject(R.id.ly_system_light)
	private LinearLayout ly_system_light;
    @ViewInject(R.id.below_divider)
	private View below_divider;
	private SwitchButton mShockSet ;
	private int selectRouter = -1;
	private boolean isFrist = false;
	private String routerType = "-1";

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
		//fun add 根据手机当前所连接的路由器的型号，led开关对字母路由器的灯的控制
		setRouterStyle();
//		setLedStyle();
    }

	public void setLedStyle() {
		routerType = IsRouteHelp.getInstance().getRouterType();
		Log.i(tag,"routerType:"+routerType);
		if(routerType.equals("0")){
			ly_system_light.setVisibility(View.VISIBLE);
			below_divider.setVisibility(View.VISIBLE);
			getDeviceStatuHttp();
		}else{
			ly_system_light.setVisibility(View.GONE);
			below_divider.setVisibility(View.GONE);
		}
	}

	public void setRouterStyle() {
		IsRouteHelp.getInstance().loginHttp(onHttpSelector);
	}
	private OnHttpSelector onHttpSelector = new OnHttpSelector() {
		@Override
		public void onResult(String result) {
			IsNewRouter mVersion = JsonUtil.getObject(result,IsNewRouter.class);
			Log.i("SystemControl","获取路由器的类型is_new："+mVersion.getIs_new()+",type:"+mVersion.getDev_type());
			if(mVersion != null) {
				if ("0".equals(mVersion.getIs_new())) {
					isFrist = false;
				} else if ("1".equals(mVersion.getIs_new())) {
					isFrist = true;
				}
				if ("0".equals(mVersion.getDev_type())) {
					selectRouter = 1;
				} else if ("1".equals(mVersion.getDev_type())) {
					selectRouter = 2;
				} else if ("2".equals(mVersion.getDev_type())) {
					selectRouter = 3;
				} else if ("3".equals(mVersion.getDev_type())) {
					selectRouter = 4;
				} else {
					selectRouter = -1;
				}
			}else {
				selectRouter = -1;
			}

		}
		@Override
		public void onFinished() {
			setSearchView();
		}
	};
	private void setSearchView(){
		switch (selectRouter){
			case 1://AirMesh子母路由器需要灯的控制
				ly_system_light.setVisibility(View.VISIBLE);
				below_divider.setVisibility(View.VISIBLE);
				getDeviceStatuHttp();
				break;
			case 2://AirBox 不需要控制灯
			case 3://AirOptimus 不需要控制灯
			case 4://AirFrame 不需要控制灯
				ly_system_light.setVisibility(View.GONE);
				below_divider.setVisibility(View.GONE);
				break;
			default:
					break;
		}
	}

	@Event(value = {R.id.ly_system_wifi,R.id.ly_system_net,R.id.ly_system_password,R.id.ly_system_reboot,R.id.ly_system_restore,R.id.ly_system_version})
	private void connWifi(View v) {
		Intent intent;
		switch (v.getId()) {
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
							public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
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

	}


	public void Refresh() {
		initView();
	}
}
