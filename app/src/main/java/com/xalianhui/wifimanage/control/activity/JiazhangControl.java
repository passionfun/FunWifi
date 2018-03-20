package com.xalianhui.wifimanage.control.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.bean.DeviceXQEnry;
import com.xalianhui.wifimanage.bean.JiazListEnty;
import com.xalianhui.wifimanage.consts.Cache;
import com.xalianhui.wifimanage.consts.Constants;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.control.BaseActivityControl;
import com.xalianhui.wifimanage.dialog.AutoDialog;
import com.xalianhui.wifimanage.function.MyCallBack;
import com.xalianhui.wifimanage.function.MyRequestParams;
import com.xalianhui.wifimanage.ui.activity.AddDeviceActivity;
import com.xalianhui.wifimanage.ui.activity.JiazDeviceActivity;
import com.xalianhui.wifimanage.ui.view.MntAdapter;
import com.xalianhui.wifimanage.ui.view.MntDialog;
import com.xalianhui.wifimanage.ui.view.MntHolder;
import com.xalianhui.wifimanage.utils.JsonUtil;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;


public class JiazhangControl extends BaseActivityControl {

	@ViewInject(R.id.lv_device)
	private ListView lvDevice;
	private MntAdapter<JiazListEnty> adapterDevice;
	private List<JiazListEnty> blackList;
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
		adapterDevice = new MntAdapter<JiazListEnty>(mActivity, R.layout.item_jiazhang_list, null, true) {
			@Override
			public void setItemView(MntHolder simHolder, final JiazListEnty airtest, boolean isSelected, final int position, boolean isAvailable, boolean isEditable) {
				if (airtest.getSta_name()!= null &&!"".equals(airtest.getSta_name())&&!"*".equals(airtest.getSta_name())) {
					simHolder.setTextView(R.id.tv_jiaz_name, airtest.getSta_name());
				}else {
					simHolder.setTextView(R.id.tv_jiaz_name, airtest.getMac());
				}
				if ("pc".equals(airtest.getSta_type())){
					simHolder.setImageView(R.id.iv_item_img,R.mipmap.device_computer);
				}else if ("ipad".equals(airtest.getSta_type())){
					simHolder.setImageView(R.id.iv_item_img,R.mipmap.device_ipad);
				}else {
					simHolder.setImageView(R.id.iv_item_img,R.mipmap.device_phone);
				}
				simHolder.getImageView(R.id.iv_jiaz_del).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						setTimerHttp(airtest.getIndex(),airtest.getMac());
					}
				});
				simHolder.getImageView(R.id.iv_jiaz_edit).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent(mActivity, JiazDeviceActivity.class);
						intent.putExtra("mac",airtest.getMac());
						mActivity.startActivity(intent);
//						setBlackHttp(position);
					}
				});
			}
		};
		lvDevice.setAdapter(adapterDevice);
	}

	@Event(value = R.id.tv_ok)
	private void onClick(View v) {
		Intent intent = new Intent(mActivity, AddDeviceActivity.class);
		intent.putExtra("type",Consts.ADD_DEVICE_JIAZHANG);
		mActivity.startActivity(intent);
	}

	private void getBlacklistHttp(){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.geTimerList_Url);
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if(!"".equals(result)){
					List<JiazListEnty> records= JsonUtil.JsonToList(result,JiazListEnty.class);
					Log.i("devicelist",records.toString());
					if ( records != null){
						blackList = records;
						adapterDevice.updata(blackList);
					}

				}else {
					ShowToast(result);
				}
			}
		});
	}

	public void onResume() {
		getBlacklistHttp();
	}
//	private void getDeviceStatuHttp(final String mac){
//		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.getDeviceStuta_Url);
//		params.addBodyParameter("mac",mac);
//		x.http().post(params, new Callback.CommonCallback<String>() {
//			@Override
//			public void onSuccess(String result) {
//				//解析result
//				Log.i("result",result);
//				if(!"".equals(result)){
//					DeviceXQEnry records= JsonUtil.getObject(result,DeviceXQEnry.class);
//					if ( records != null){
//						DeviceXQEnry deviceSta = records;
//						setTimerHttp(deviceSta,mac);
//					}
//				}else {
//					ShowToast(result);
//				}
//			}
//			@Override
//			public void onError(Throwable ex, boolean isOnCallback) {
//
//			}
//			@Override
//			public void onCancelled(CancelledException cex) {
//
//			}
//			@Override
//			public void onFinished() {
//
//			}
//		});
//	}
	private void setTimerHttp(String index,String mac){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.setTimer_Url);
			params.addBodyParameter("action","del");
			params.addBodyParameter("index",index);


		params.addBodyParameter("mac",mac);
		params.addBodyParameter("cycle","");
		params.addBodyParameter("start1","");
		params.addBodyParameter("start2","");
		params.addBodyParameter("end1","");
		params.addBodyParameter("end2","");
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
	private MntDialog prossDialog;
	private void applyBlackHttp(){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.setTimerApply_Url);
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if("1".equals(result)){
					Cache.isLoading = false;
					prossDialog = new MntDialog(mActivity, R.style.Theme_dialog, R.layout.dialog_pross, Constants.SMALL_WIDTH, Constants.SMALL_HEIGHT);
					prossDialog.setCanceledOnTouchOutside(false);
					prossDialog.setCancelable(false);
					TextView tv = (TextView) prossDialog.findViewById(R.id.tv_context);
					tv.setText(getResString(R.string.login_waning));
					prossDialog.show();
					new Thread(){
						@Override
						public void run() {
							try {
								sleep(Constants.RUN_SLEEP_TIMER);
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
					new AutoDialog(mActivity,getResString(R.string.set_ok),"").setOnDismissListener(new DialogInterface.OnDismissListener() {
						@Override
						public void onDismiss(DialogInterface dialogInterface) {
							getBlacklistHttp();
						}
					});

					break;
				case 1:
					//新弹出框

					break;
			}
		}
	};
}
