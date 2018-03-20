package com.xalianhui.wifimanage.control.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.bean.DeviceXQEnry;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.control.BaseActivityControl;
import com.xalianhui.wifimanage.dialog.AutoDialog;
import com.xalianhui.wifimanage.function.MyCallBack;
import com.xalianhui.wifimanage.function.MyRequestParams;
import com.xalianhui.wifimanage.ui.view.SwitchButton;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


public class DeviceXiansuControl extends BaseActivityControl {

	@ViewInject(R.id.seekBar)
	private SeekBar sbUp;
	@ViewInject(R.id.tv_xiansu_up)
	private TextView tvUp;
	@ViewInject(R.id.iv_decive_big)
	private ImageView ivBig;
	@ViewInject(R.id.iv_decive_smal)
	private ImageView ivSmal;
	@ViewInject(R.id.ly_decive_big)
	private LinearLayout lyBig;
	@ViewInject(R.id.ly_decive_smal)
	private LinearLayout lySmal;
	@ViewInject(R.id.ly_device_xiansu)
	private LinearLayout lyQos;
	private SwitchButton mShockSet ;
	private DeviceXQEnry deviceSta;
	private String mIp;
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
		setTitle(getResString(R.string.device_smart_list));
		mIp = mActivity.getIntent().getExtras().getString("ip");
		deviceSta = (DeviceXQEnry) mActivity.getIntent().getSerializableExtra("deviceSta");
		if (mIp == null|| deviceSta == null){
			mActivity.finish();
			return;
		}
		sbUp.setMax(1024*8);
		sbUp.setProgress(500*8);
		sbUp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				tvUp.setText(i/8+"KB/s");
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
		mShockSet = (SwitchButton) mRootView
				.findViewById(R.id.msgpush_voiceset);
		if ("0".equals(deviceSta.getIs_qos())) {
			mShockSet.setChecked(true);
			ivBig.setEnabled(false);
			ivSmal.setEnabled(false);
			lyBig.setEnabled(false);
			lySmal.setEnabled(false);
			sbUp.setEnabled(false);
			lyQos.setVisibility(View.INVISIBLE);
		}else {
			lyQos.setVisibility(View.VISIBLE);
			mShockSet.setChecked(false);
			ivBig.setEnabled(true);
			ivSmal.setEnabled(true);
			lyBig.setEnabled(true);
			lySmal.setEnabled(true);
			if ("0".equals(deviceSta.getQos_mode())){

				ivBig.setSelected(true);
				ivSmal.setSelected(false);
			}else {
				ivBig.setSelected(false);
				ivSmal.setSelected(true);
			}
			int pros = Integer.parseInt(deviceSta.getQos_width());
			sbUp.setProgress(pros);
		}
		mShockSet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
										 boolean isChecked) {
				if (isChecked){
					lyQos.setVisibility(View.INVISIBLE);
					ivBig.setEnabled(false);
					ivSmal.setEnabled(false);
					lyBig.setEnabled(false);
					lySmal.setEnabled(false);
					sbUp.setEnabled(false);
				}else {
					lyQos.setVisibility(View.VISIBLE);
					ivBig.setEnabled(true);
					ivSmal.setEnabled(true);
					lyBig.setEnabled(true);
					lySmal.setEnabled(true);
					sbUp.setEnabled(true);
					ivBig.setSelected(true);
					ivSmal.setSelected(false);

				}
			}
		});
	}
	@Event(value = R.id.btn_login)
	private void setSop(View v){
		String isPos ;
		String isPosMode = "" ;
		String isPosWidth = "" ;
		if (mShockSet.isChecked()){
			isPos = "0";
		}else {
			isPos = "1";
			if(ivBig.isSelected()){
				isPosMode = "0";
			}else {
				isPosMode = "1";
			}
			isPosWidth = sbUp.getProgress()+"";
		}
		setSopHttp(isPos,isPosMode,isPosWidth);

	}
	@Event(value = {R.id.ly_decive_smal,R.id.ly_decive_big})
	private void setSopMode(View v){
		ivBig.setSelected(false);
		ivSmal.setSelected(false);
		switch (v.getId()){
			case R.id.ly_decive_big:
				ivBig.setSelected(true);
				break;
			case R.id.ly_decive_smal:
				ivSmal.setSelected(true);
				break;
		}

	}

	private void setSopHttp(String isPos,String isMode,String wight){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.setPos_Url);
		if ("0".equals(deviceSta.getIs_qos())&&"1".equals(isPos)){
			params.addBodyParameter("action","add");
			params.addBodyParameter("index","");
		}else if("0".equals(deviceSta.getIs_qos())&&"0".equals(isPos)){
			ShowToast(getResString(R.string.smart_no_set));
			return;
		}else if("1".equals(deviceSta.getIs_qos())&&"0".equals(isPos)){
			params.addBodyParameter("action","del");
			params.addBodyParameter("index",deviceSta.getQos_list_id());
		}else if("1".equals(deviceSta.getIs_qos())&&"1".equals(isPos)){
			params.addBodyParameter("action","edit");
			params.addBodyParameter("index",deviceSta.getQos_list_id());
		}else{
			ShowToast(getResString(R.string.smart_no_set));
		}
		params.addBodyParameter("ip1",mIp);
		params.addBodyParameter("type",isMode);
		params.addBodyParameter("width",wight);

		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if("1".equals(result)){
					applySopHttp();
				}else {
					ShowToast(result);
				}
			}
		});
	}
	private void applySopHttp(){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.applyPos_Url);
		params.addBodyParameter("total","100000");

		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if("1".equals(result)){
					new AutoDialog(mActivity,getResString(R.string.set_ok),"").setOnDismissListener(new DialogInterface.OnDismissListener() {
						@Override
						public void onDismiss(DialogInterface dialogInterface) {
							mActivity.finish();
						}
					});
				}else {
					ShowToast(result);
				}
			}
		});
	}


}
