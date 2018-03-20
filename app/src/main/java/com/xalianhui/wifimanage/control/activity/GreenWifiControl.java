package com.xalianhui.wifimanage.control.activity;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.bean.GreenBean;
import com.xalianhui.wifimanage.bean.WifiHightCfgEnry;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.control.BaseActivityControl;
import com.xalianhui.wifimanage.dialog.AutoDialog;
import com.xalianhui.wifimanage.function.MyCallBack;
import com.xalianhui.wifimanage.function.MyRequestParams;
import com.xalianhui.wifimanage.utils.JsonUtil;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


public class GreenWifiControl extends BaseActivityControl {

	@ViewInject(R.id.iv_green_sleep)
	private ImageView ivSleep;
	@ViewInject(R.id.iv_green_bianzhun)
	private ImageView ivBiaoz;
	@ViewInject(R.id.iv_green_chuanq)
	private ImageView ivChuanq;
	@ViewInject(R.id.iv_green_auto)
	private ImageView ivAuto;
	private WifiHightCfgEnry wifiCfgEnry;
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
		setTitle(getResString(R.string.menu_green_wifi));
		getDeviceStatuHttp();
		getGreenStatuHttp();
	}
	@Event(value = {R.id.ly_select_pppoe1,R.id.ly_select_pppoe2,R.id.ly_select_pppoe3,R.id.ly_select_pppoe4})
	private void setSopMode(View v){
		switch (v.getId()){
			case R.id.ly_select_pppoe1:
				if (!ivSleep.isSelected()){
					setSelectNo();
					ivSleep.setSelected(true);
					setGreenHttp(1);
				}
				break;
			case R.id.ly_select_pppoe2:
				if (!ivBiaoz.isSelected()){
					setSelectNo();
					ivBiaoz.setSelected(true);
					setGreenHttp(2);
				}
				break;
			case R.id.ly_select_pppoe3:
				if (!ivChuanq.isSelected()){
					setSelectNo();
					ivChuanq.setSelected(true);
					setGreenHttp(3);
				}
				break;
			case R.id.ly_select_pppoe4:
				if (!ivAuto.isSelected()){
					setSelectNo();
					ivAuto.setSelected(true);
					setGreenHttp(4);
				}
				break;
		}

	}
	private void setSelectNo(){
		ivSleep.setSelected(false);
		ivBiaoz.setSelected(false);
		ivChuanq.setSelected(false);
		ivAuto.setSelected(false);
	}

	private void getDeviceStatuHttp(){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.getHightCfg_Url);
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if(!"".equals(result)){
					WifiHightCfgEnry records= JsonUtil.getObject(result,WifiHightCfgEnry.class);
					if ( records != null){
						wifiCfgEnry = records;
//						if ("25".equals(wifiCfgEnry.getPower1_percent())&&"25".equals(wifiCfgEnry.getPower2_percent())){
//							ivSleep.setSelected(true);
//						}else if ("75".equals(wifiCfgEnry.getPower1_percent())&&"75".equals(wifiCfgEnry.getPower2_percent())){
//							ivBiaoz.setSelected(true);
//						}else if ("100".equals(wifiCfgEnry.getPower1_percent())&&"100".equals(wifiCfgEnry.getPower2_percent())){
//							ivChuanq.setSelected(true);
//						}else {
//							ivAuto.setSelected(true);
//						}
					}
				}else {
					ShowToast(result);
				}
			}
		});
	}
	private void getGreenStatuHttp(){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.get_wifi_mode_Url);
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if(!"".equals(result)){
					GreenBean records= JsonUtil.getObject(result,GreenBean.class);
					if ( records != null){
						if ("1".equals(records.getMode())){
							ivSleep.setSelected(true);
						}else if ("2".equals(records.getMode())){
							ivBiaoz.setSelected(true);
						}else if ("3".equals(records.getMode())){
							ivChuanq.setSelected(true);
						}else {
							ivAuto.setSelected(true);
						}
					}
				}else {
					ShowToast(result);
				}
			}
		});
	}
	private void setGreenHttp(final int type){

		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP + Icont.set_wifi_mode_Url);
					params.addBodyParameter("mode", type+"");
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if("1".equals(result)){
					setWanHttp(type);
				}else {
					ShowToast(result);
				}
			}
		});
	}
	private void setWanHttp(int type){
		if(wifiCfgEnry == null){
			ShowToast(getResString(R.string.high_set_xindao));
			getDeviceStatuHttp();
			return;
		}
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP + Icont.setHightCfg_Url);
			switch (type){
				case 1:
					params.addBodyParameter("bandwidth2", "HT80");
					params.addBodyParameter("bandwidth1", "HT40");
					params.addBodyParameter("power1_percent", "25");
					params.addBodyParameter("power2_percent","25");
					break;
				case 2:
					params.addBodyParameter("bandwidth2", "HT80");
					params.addBodyParameter("bandwidth1", "HT40");
					params.addBodyParameter("power1_percent", "75");
					params.addBodyParameter("power2_percent","75");
					break;
				case 3:
					params.addBodyParameter("bandwidth2", "HT40");
					params.addBodyParameter("bandwidth1", "HT20");
					params.addBodyParameter("power1_percent", "100");
					params.addBodyParameter("power2_percent","100");
					break;
				default:
					params.addBodyParameter("bandwidth2", wifiCfgEnry.getBandwidth2());
					params.addBodyParameter("bandwidth1", wifiCfgEnry.getBandwidth1());
					params.addBodyParameter("power1_percent", wifiCfgEnry.getPower1_percent());
					params.addBodyParameter("power2_percent", wifiCfgEnry.getPower2_percent());
					break;
			}


		params.addBodyParameter("wmm2", wifiCfgEnry.getWmm2());
		params.addBodyParameter("channel_b", wifiCfgEnry.getChannel_b());

			params.addBodyParameter("wmm1", wifiCfgEnry.getWmm1());
			params.addBodyParameter("channel_a", wifiCfgEnry.getChannel_a());
		if(wifiCfgEnry.getRegion()!=null&&!"".equals(wifiCfgEnry.getRegion())){
			params.addBodyParameter("region",wifiCfgEnry.getRegion());
		}else {
			params.addBodyParameter("region",wifiCfgEnry.getRange());
		}

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


}
