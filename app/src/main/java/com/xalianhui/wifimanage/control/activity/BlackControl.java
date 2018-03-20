package com.xalianhui.wifimanage.control.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.bean.BlackEnty;
import com.xalianhui.wifimanage.consts.Cache;
import com.xalianhui.wifimanage.consts.Constants;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.control.BaseActivityControl;
import com.xalianhui.wifimanage.dialog.AutoDialog;
import com.xalianhui.wifimanage.function.MyCallBack;
import com.xalianhui.wifimanage.function.MyRequestParams;
import com.xalianhui.wifimanage.ui.activity.AddDeviceActivity;
import com.xalianhui.wifimanage.ui.view.MntAdapter;
import com.xalianhui.wifimanage.ui.view.MntDialog;
import com.xalianhui.wifimanage.ui.view.MntHolder;
import com.xalianhui.wifimanage.utils.JsonUtil;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;


public class BlackControl extends BaseActivityControl {

	@ViewInject(R.id.lv_device)
	private ListView lvDevice;
	private MntAdapter<BlackEnty> adapterDevice;
	private List<BlackEnty> blackList;
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
		setTitle(getResString(R.string.menu_blacklist));
		adapterDevice = new MntAdapter<BlackEnty>(mActivity, R.layout.item_blacklist, null, true) {
			@Override
			public void setItemView(MntHolder simHolder, final BlackEnty airtest, boolean isSelected, final int position, boolean isAvailable, boolean isEditable) {
				if (airtest.getSta_name()!= null &&!"".equals(airtest.getSta_name())&&!"*".equals(airtest.getSta_name())) {
					simHolder.setTextView(R.id.tv_black_name, airtest.getSta_name());
				}else {
					simHolder.setTextView(R.id.tv_black_name, airtest.getMac());
				}
				simHolder.getImageView(R.id.iv_black_del).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						setBlackHttp(airtest);
					}
				});
				if ("pc".equals(airtest.getSta_type())){
					simHolder.setImageView(R.id.iv_item_img,R.mipmap.device_computer);
				}else if ("ipad".equals(airtest.getSta_type())){
					simHolder.setImageView(R.id.iv_item_img,R.mipmap.device_ipad);
				}else {
					simHolder.setImageView(R.id.iv_item_img,R.mipmap.device_phone);
				}
			}
		};
		lvDevice.setAdapter(adapterDevice);
	}
	@Event(value = R.id.tv_ok)
	private void onClick(View v) {
		Intent intent = new Intent(mActivity, AddDeviceActivity.class);
		intent.putExtra("type",Consts.ADD_DEVICE_BLACK);
		mActivity.startActivity(intent);
	}
	private void getBlacklistHttp(){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.sgetBlackLiits_Url);
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if(!"".equals(result)){
					List<BlackEnty> records= JsonUtil.JsonToList(result,BlackEnty.class);
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
	private void setBlackHttp(BlackEnty postion){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.setBlack_Url);
			params.addBodyParameter("action","del");
			params.addBodyParameter("index",postion.getIndex());
		params.addBodyParameter("mac",postion.getMac());
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
	private MntDialog prossDialog;
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
					getBlacklistHttp();
					break;
				case 1:
					//新弹出框

					break;
			}
		}
	};

	public void onResume() {
		getBlacklistHttp();
	}
}
