package com.xalianhui.wifimanage.control.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.consts.Cache;
import com.xalianhui.wifimanage.consts.Constants;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.control.BaseActivityControl;
import com.xalianhui.wifimanage.function.MyCallBack;
import com.xalianhui.wifimanage.function.MyRequestParams;
import com.xalianhui.wifimanage.ui.activity.BroadbandActivity;
import com.xalianhui.wifimanage.ui.activity.SetPasswordActivity;
import com.xalianhui.wifimanage.ui.activity.StaticWanActivity;
import com.xalianhui.wifimanage.ui.view.MntDialog;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.x;


public class SelectLoginControl extends BaseActivityControl {


	private MntDialog mntDialog;
	private MntDialog prossDialog;
	private boolean isLink = false;
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

	private void initView() {
		setTopView(Consts.TopPage.PAGE);
		setTitle(getResString(R.string.select_login));
		type =  mActivity.getIntent().getExtras().getInt("type",0);
	}





	@Event(value = {R.id.ly_select_pppoe,R.id.ly_select_dynamic,R.id.ly_select_static})
	 private void select(View v) {
		Intent intent;
	     switch (v.getId()) {
            case R.id.ly_select_pppoe:
				 intent = new Intent(mActivity, BroadbandActivity.class);
				intent.putExtra("type",type);
				mActivity.startActivity(intent);
				mActivity.finish();
                break;
            case R.id.ly_select_dynamic:
            	if (type == 1){
					intent = new Intent(mActivity, SetPasswordActivity.class);
					intent.putExtra("type",type);
					intent.putExtra("net_type","dhcp");
					mActivity.startActivity(intent);
					mActivity.finish();
				}else {
					setWanHttp();
				}
                break;
            case R.id.ly_select_static:
				 intent = new Intent(mActivity, StaticWanActivity.class);
				intent.putExtra("type",type);
				mActivity.startActivity(intent);
				mActivity.finish();
                break;

            default:
                break;
        }
	 }
	private void setWanHttp(){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.setWan_Url);
		params.addBodyParameter("net_type","dhcp");
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
//					isWanHttp();
//					if (type != 1){
//						Intent intent = new Intent(mActivity, SetPasswordActivity.class);
//						mActivity.startActivity(intent);
//					}
					mActivity.finish();
					break;
				case 1:
					//新弹出框

					break;
			}
		}
	};
	private void isWanHttp(){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.getWanStuta_Url);
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if("1".equals(result)){

				}else {
					ShowToast(result);
				}
			}
		});
	}

}
