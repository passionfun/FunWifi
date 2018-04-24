package com.xalianhui.wifimanage.control.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.bean.Device;
import com.xalianhui.wifimanage.bean.DeviceXQEnry;
import com.xalianhui.wifimanage.consts.Cache;
import com.xalianhui.wifimanage.consts.Constants;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.control.BaseActivityControl;
import com.xalianhui.wifimanage.dialog.AutoDialog;
import com.xalianhui.wifimanage.function.MyCallBack;
import com.xalianhui.wifimanage.function.MyRequestParams;
import com.xalianhui.wifimanage.ui.activity.DeviceXiansuActivity;
import com.xalianhui.wifimanage.ui.activity.JiazDeviceActivity;
import com.xalianhui.wifimanage.ui.view.BasePopupHelper;
import com.xalianhui.wifimanage.ui.view.MntDialog;
import com.xalianhui.wifimanage.ui.view.SwitchButton;
import com.xalianhui.wifimanage.utils.JsonUtil;
import com.xalianhui.wifimanage.utils.TextUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


public class DeviceXQControl extends BaseActivityControl {
	@ViewInject(R.id.tv_up_sudu)
	private TextView upLink;
	@ViewInject(R.id.tv_down_sudu)
	private TextView downLink;
	@ViewInject(R.id.tv_total)
	private TextView totalLink;
	@ViewInject(R.id.tv_up_sudu_kb)
	private TextView upLinkKB;
	@ViewInject(R.id.tv_down_sudu_kb)
	private TextView downLinkKb;
	@ViewInject(R.id.iv_password_old)
	private ImageView ivImg;
	private SwitchButton mShockSet ;
	private Device device;
	private DeviceXQEnry deviceSta;
	private MntDialog prossDialog;

	@Override
	public void onInit(View rootView, Context context) {
		super.onInit(rootView, context);
		x.view().inject(this, rootView);
		initView();
	}


    @Override
	public void onRecycle() {
		mActivity.unregisterReceiver(order_receiver);
	}
	BroadcastReceiver order_receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Device devices = (Device) intent.getSerializableExtra("device");
			if(devices != null){
				Log.i("onReceive",devices.toString());
				device = devices;
				setTitle(device.getSta_name());
				setLink();
			}
//			ShowRedPoint(str);
//  				myRedPoint();

		}
	};
	private  void setLink(){
		String upStr = TextUtil.getTraffic1(device.getUp_rate());
		if (upStr.contains("KB")){
			upLink.setText(TextUtil.removeKB(upStr));
			upLinkKB.setText("KB/s");
		}else if (upStr.contains("MB")){
			upLink.setText(TextUtil.removeKB(upStr));
			upLinkKB.setText("MB/s");
		}else if (upStr.contains("GB")){
			upLink.setText(TextUtil.removeKB(upStr));
			upLinkKB.setText("GB/s");
		}else {
			upLink.setText(TextUtil.removeKB(upStr));
			upLinkKB.setText("KB/s");
		}
		String downStr = TextUtil.getTraffic1(device.getDown_rate());
		if (upStr.contains("KB")){
			downLink.setText(TextUtil.removeKB(downStr));
			downLinkKb.setText("KB/s");
		}else if (upStr.contains("MB")){
			downLink.setText(TextUtil.removeKB(downStr));
			downLinkKb.setText("MB/s");
		}else if (upStr.contains("GB")){
			downLink.setText(TextUtil.removeKB(downStr));
			downLinkKb.setText("GB/s");
		}else {
			downLink.setText(TextUtil.removeKB(downStr));
			downLinkKb.setText("KB/s");
		}
		String totalStr = device.getTotal_flow();
		totalLink.setText(TextUtil.getTraffic(totalStr));
	}
	private void initView() {
		setTopView(Consts.TopPage.PAGE);
		device = (Device) mActivity.getIntent().getSerializableExtra("device");
		mActivity.registerReceiver(order_receiver, new IntentFilter(
				Consts.ACTION_BROADCAST_LOCATION));

		if (device == null){
			mActivity.finish();
			return;
		}
		setTitle(device.getSta_name());
		setLink();
		if ("pc".equals(device.getSta_type())){
			ivImg.setImageResource(R.mipmap.device_phone_wight);
		}else if ("ipad".equals(device.getSta_type())){
			ivImg.setImageResource(R.mipmap.device_phone_wight);
		}else {
			ivImg.setImageResource(R.mipmap.device_phone_wight);
		}
		mShockSet = (SwitchButton) mRootView
				.findViewById(R.id.msgpush_voiceset);
		mShockSet.setChecked(true);

		getDeviceStatuHttp();
	}
	@Event(value = {R.id.ly_select_pppoe,R.id.ly_select_pppoe1})
	private void select(View v) {
		Intent intent;
		switch (v.getId()) {
			case R.id.ly_select_pppoe:
				Intent intent1 = new Intent(mActivity, JiazDeviceActivity.class);
				intent1.putExtra("mac",device.getMac());
				mActivity.startActivity(intent1);
				break;
			case R.id.ly_select_pppoe1:
				intent = new Intent(mActivity, DeviceXiansuActivity.class);
				intent.putExtra("ip",device.getIp());
				intent.putExtra("deviceSta",deviceSta);
				mActivity.startActivity(intent);
				break;
			default:
				break;
		}
	}
	@Event(value = R.id.im_right)
	private void setDeviceName(View v) {
		showPopupWindow();
	}
	private PopupWindow mPopup;
	private View mPopLayout;
	public void showPopupWindow() {
		BasePopupHelper.hidePopupWindow(mPopup);

		mPopLayout = mActivity.getLayoutInflater().inflate(
				R.layout.popu_edit, null);
		initMenuView(mPopLayout);
		WindowManager wm = (WindowManager) mActivity
				.getSystemService(Context.WINDOW_SERVICE);

		int wmHeight = wm.getDefaultDisplay().getHeight();
		float density = mActivity.getResources().getDisplayMetrics().density;
//        mPopup = new PopupWindow(mPopLayout, (int) (width * density + 0.5f), ViewGroup.LayoutParams.MATCH_PARENT, true);
		mPopup = new PopupWindow(mPopLayout, ViewGroup.LayoutParams.MATCH_PARENT, (int) (wmHeight-density*220), true);
		BasePopupHelper.initPopupWindow(mActivity, mPopup, R.style.common_popup_window_bottom_style);
		mPopup.showAtLocation(
				mActivity.getLayoutInflater().inflate(
						R.layout.activity_main, null), Gravity.BOTTOM, 0, 0);
		mPopup.setTouchInterceptor(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					mPopup.dismiss();
					return true;
				}
				return false;
			}
		});

	}
	private void initMenuView(View view) {
		TextView btnBind = (TextView) view.findViewById(R.id.btn_bind_router);
		TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
		 TextView tvName = (TextView) view.findViewById(R.id.tv_title);
		final EditText etPassword = (EditText) view.findViewById(R.id.et_dialog_password);
		ImageView ivPassword = (ImageView) view.findViewById(R.id.iv_password);
		tvName.setText(getResString(R.string.device_rename));
		btnBind.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String passStr = etPassword.getText().toString();
				if (passStr != null && !"".equals(passStr)){
					setDeviceNameHttp(passStr);
				}else {
					ShowToast(getResString(R.string.device_input_rename));
				}
			}
		});
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopup.dismiss();
			}
		});
		ivPassword.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				etPassword.setText("");
			}
		});


	}
	private void setDeviceNameHttp(final String name){
		RequestParams params = new RequestParams(Icont.Url_TopIP+ Icont.setDeviceName_Url);
		params.addBodyParameter("name",name);
		params.addBodyParameter("mac",device.getMac());
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if("1".equals(result)){
					device.setSta_name(name);
					setTitle(device.getSta_name());
					mPopup.dismiss();
				}else {
					ShowToast(result);
				}
			}
		});
	}
	private void getDeviceStatuHttp(){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.getDeviceStuta_Url);
		if(device == null){
			return;
		}
		params.addBodyParameter("mac",device.getMac());
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if(!"".equals(result)){
					DeviceXQEnry records= JsonUtil.getObject(result,DeviceXQEnry.class);
					if ( records != null){
						deviceSta = records;
						mShockSet.setOnCheckedChangeListener(null);
						if ("0".equals(deviceSta.getIs_blocked())) {
							mShockSet.setChecked(true);
						}else {
							mShockSet.setChecked(false);
						}
						mShockSet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(CompoundButton buttonView,
														 boolean isChecked) {
								setBlackHttp(isChecked);
							}
						});
					}
				}else {
					ShowToast(result);
				}
			}
		});
	}
	private void setBlackHttp(boolean isChecked){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.setBlack_Url);
		if (isChecked){
			params.addBodyParameter("action","del");
			params.addBodyParameter("index",deviceSta.getBlack_list_id());
		}else {
			params.addBodyParameter("action","add");
		}
		params.addBodyParameter("mac",device.getMac());
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
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what){
				case 0:
                    Cache.isLoading = true;
					prossDialog.dismiss();
					new AutoDialog(mActivity,getResString(R.string.set_ok),"");
					getDeviceStatuHttp();
					break;
				case 1:
					//新弹出框

					break;
			}
		}
	};
}
