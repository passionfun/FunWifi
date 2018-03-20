package com.xalianhui.wifimanage.control.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.bean.AddDeviceEnty;
import com.xalianhui.wifimanage.bean.BlackEnty;
import com.xalianhui.wifimanage.bean.Device;
import com.xalianhui.wifimanage.bean.DeviceList;
import com.xalianhui.wifimanage.bean.DeviceXQEnry;
import com.xalianhui.wifimanage.bean.QosDeviceList;
import com.xalianhui.wifimanage.consts.Cache;
import com.xalianhui.wifimanage.consts.Constants;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.control.BaseActivityControl;
import com.xalianhui.wifimanage.dialog.AutoDialog;
import com.xalianhui.wifimanage.function.MyCallBack;
import com.xalianhui.wifimanage.function.MyRequestParams;
import com.xalianhui.wifimanage.ui.activity.DeviceXQActivity;
import com.xalianhui.wifimanage.ui.activity.DeviceXiansuActivity;
import com.xalianhui.wifimanage.ui.activity.JiazDeviceActivity;
import com.xalianhui.wifimanage.ui.view.MntAdapter;
import com.xalianhui.wifimanage.ui.view.MntDialog;
import com.xalianhui.wifimanage.ui.view.MntHolder;
import com.xalianhui.wifimanage.utils.JsonUtil;
import com.xalianhui.wifimanage.utils.TextUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import miky.android.common.util.ContextUtil;


public class AddDeviceControl extends BaseActivityControl {
	private int index ;
	private MntAdapter<AddDeviceEnty> adapterDevice;
	@ViewInject(R.id.lv_device)
	private ListView lvDevice;
	private List<AddDeviceEnty> deviceList;
	private DeviceXQEnry deviceSta;
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
		setTitle(getResString(R.string.online_user));
		index = mActivity.getIntent().getExtras().getInt("type");
		adapterDevice = new MntAdapter<AddDeviceEnty>(mActivity, R.layout.item_devicelist, deviceList, true) {
			@Override
			public void setItemView(MntHolder simHolder, AddDeviceEnty airtest, boolean isSelected, int position, boolean isAvailable, boolean isEditable) {
				if (airtest.getSta_name()!= null &&!"".equals(airtest.getSta_name())&&!"*".equals(airtest.getSta_name())) {
					simHolder.setTextView(R.id.tv_iten_name, airtest.getSta_name());
				}else {
					simHolder.setTextView(R.id.tv_iten_name, airtest.getMac());
				}
				if ("pc".equals(airtest.getSta_type())){
					simHolder.setImageView(R.id.iv_item_img,R.mipmap.device_computer);
				}else if ("ipad".equals(airtest.getSta_type())){
					simHolder.setImageView(R.id.iv_item_img,R.mipmap.device_ipad);
				}else {
					simHolder.setImageView(R.id.iv_item_img,R.mipmap.device_phone);
				}
				simHolder.getImageView(R.id.im_ietm_signal).setVisibility(View.VISIBLE);
				if ("1".equals(airtest.getSignal_level())){
					simHolder.setImageView(R.id.im_ietm_signal,R.mipmap.signal_1);
				}else if ("2".equals(airtest.getSignal_level())){
					simHolder.setImageView(R.id.im_ietm_signal,R.mipmap.signal_2);
				}else if ("3".equals(airtest.getSignal_level())){
					simHolder.setImageView(R.id.im_ietm_signal,R.mipmap.signal_3);
				}else if ("4".equals(airtest.getSignal_level())){
					simHolder.setImageView(R.id.im_ietm_signal,R.mipmap.signal_4);
				}else if ("5".equals(airtest.getSignal_level())){
					simHolder.setImageView(R.id.im_ietm_signal,R.mipmap.signal_5);
				}else {
					simHolder.getImageView(R.id.im_ietm_signal).setVisibility(View.INVISIBLE);
				}
				String totalStr = airtest.getDown_rate();
				simHolder.setTextView(R.id.tv_iten_rate, TextUtil.getTraffic1(totalStr));
			}
		};
		lvDevice.setAdapter(adapterDevice);
		lvDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Log.i("onItemClick","onItemClick-------------------------------");
				if (index ==Consts.ADD_DEVICE_BLACK){
					setBlackHttp(deviceList.get(i).getMac());
				}else if (index ==Consts.ADD_DEVICE_JIAZHANG){
					Intent intent1 = new Intent(mActivity, JiazDeviceActivity.class);
					intent1.putExtra("mac",deviceList.get(i).getMac());
					mActivity.startActivity(intent1);
					mActivity.finish();
				}else {
					getDeviceStatuHttp(deviceList.get(i));
				}
			}
		});
		getRouterHttp();
	}

	private void getRouterHttp(){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.getOnlineUsers_Url);
		if (index ==Consts.ADD_DEVICE_BLACK){
			params.addBodyParameter("type","blacklist");
		}else if (index ==Consts.ADD_DEVICE_JIAZHANG){
			params.addBodyParameter("type","parental_controls");
		}else {
			params.addBodyParameter("type","qos");
		}
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if(!"".equals(result)){
					List<AddDeviceEnty> records= JsonUtil.JsonToList(result,AddDeviceEnty.class);
					Log.i("devicelist",records.toString());
					if ( records != null){
						deviceList = records;
						adapterDevice.updata(deviceList);
					}

				}else {
					ShowToast(result);
				}
			}
		});
	}
	private void setBlackHttp(String mac){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.setBlack_Url);
			params.addBodyParameter("action","add");
			params.addBodyParameter("index","");
		params.addBodyParameter("mac",mac);
		params.addBodyParameter("type","1");
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if("1".equals(result)){
					applyBlackHttp();
				}else {
					ShowToast(result);
				}
			}
		});
	}
	private void applyBlackHttp(){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.applyBlack_Url);
		params.addBodyParameter("type","1");
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
								sleep(Constants.RUN_SLEEP_LONG);
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
	private MntDialog prossDialog;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what){
				case 0:
					Cache.isLoading = true;
					prossDialog.dismiss();
					new AutoDialog(mActivity,getResString(R.string.add_device_ok),"").setOnDismissListener(new DialogInterface.OnDismissListener() {
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
	private void getDeviceStatuHttp(final AddDeviceEnty qosDeviceList){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.getDeviceStuta_Url);
		params.addBodyParameter("mac",qosDeviceList.getMac());
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if(!"".equals(result)){
					DeviceXQEnry records= JsonUtil.getObject(result,DeviceXQEnry.class);
					if ( records != null){
						deviceSta = records;
						Intent intent = new Intent(mActivity, DeviceXiansuActivity.class);
						intent.putExtra("ip",qosDeviceList.getIp());
						intent.putExtra("deviceSta",deviceSta);
						mActivity.startActivity(intent);
						mActivity.finish();
					}
				}else {
					ShowToast(result);
				}
			}
		});
	}



}
