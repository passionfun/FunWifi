package com.xalianhui.wifimanage.control.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.bean.DeviceXQEnry;
import com.xalianhui.wifimanage.consts.Cache;
import com.xalianhui.wifimanage.consts.Constants;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.control.BaseActivityControl;
import com.xalianhui.wifimanage.dialog.AutoDialog;
import com.xalianhui.wifimanage.function.MyCallBack;
import com.xalianhui.wifimanage.function.MyRequestParams;
import com.xalianhui.wifimanage.ui.view.BasePopupHelper;
import com.xalianhui.wifimanage.ui.view.MntDialog;
import com.xalianhui.wifimanage.ui.view.NumberPicker;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


public class OfflineControl extends BaseActivityControl {
	@ViewInject(R.id.tv_offline_repeat)
	private TextView tvRepeat;
	@ViewInject(R.id.tv_offline_off)
	private TextView tvOff;
    @ViewInject(R.id.tv_offline_on)
	private TextView tvOn;
	@ViewInject(R.id.tv_offline_week1)
	private TextView tvWeek1;
	@ViewInject(R.id.tv_offline_week2)
	private TextView tvWeek2;
	@ViewInject(R.id.tv_offline_week3)
	private TextView tvWeek3;
	@ViewInject(R.id.tv_offline_week4)
	private TextView tvWeek4;
	@ViewInject(R.id.tv_offline_week5)
	private TextView tvWeek5;
	@ViewInject(R.id.tv_offline_week6)
	private TextView tvWeek6;
	@ViewInject(R.id.tv_offline_week7)
	private TextView tvWeek7;
	@ViewInject(R.id.ly_select_pppoe4)
	private LinearLayout lyRepeat;
	@ViewInject(R.id.iv_offline_repeat)
	private ImageView ivRepeat;


	private DeviceXQEnry deviceSta;
	private String mac;
	private String weekStr;
	private String weekStrCom;
	private int offStr1,offStr2;
	private int onStr1,onStr2;
	private boolean isShow = false;
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
		setTitle(getResString(R.string.offline_schedule));
		mac =  mActivity.getIntent().getExtras().getString("mac");
		deviceSta = (DeviceXQEnry) mActivity.getIntent().getSerializableExtra("deviceSta");
		getRightBtn().setText(getResString(R.string.wifi_com));
		getRightBtn().setVisibility(View.VISIBLE);
		getRightBtn().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getDeviceStatuHttp();
			}
		});
		if ("0".equals(deviceSta.getIs_timer())){
			setWeekStr(deviceSta.getIs_timer());
			tvRepeat.setText(getResString(R.string.offline_everyday));
			getWeekStr();
			tvOff.setText("00:00");
			tvOn.setText("00:00");
			offStr1 = 0;
			offStr2 = 0;
			onStr1 = 0;
			onStr2 = 0;
		}else {
			String startTime = deviceSta.getStart_time();
			String endTime = deviceSta.getEnd_time();
			if (startTime.length()>=4){
					String str = startTime.substring(0,2);
					String str2 = startTime.substring(2,4);
				onStr1 = Integer.parseInt(str);
				onStr2 = Integer.parseInt(str2);
			}else {
				onStr1 = 0;
				onStr2 = 0;
			}
			if (endTime.length()>=4){
				String str = endTime.substring(0,2);
				String str2 = endTime.substring(2,4);
				offStr1 = Integer.parseInt(str);
				offStr2 = Integer.parseInt(str2);
			}else {
				offStr1 = 0;
				offStr2 = 0;
			}
			tvOff.setText(getStrOnInt(offStr1)+":"+getStrOnInt(offStr2));
			tvOn.setText(getStrOnInt(onStr1)+":"+getStrOnInt(onStr2));
			setWeekNo();
			for (int i = 0; i < deviceSta.getCycle().length; i++) {
				setWeekStr(deviceSta.getCycle()[i]);
			}
			getWeekStr();
			tvRepeat.setText(weekStr);
		}
	}
	@Event(value = {R.id.tv_offline_week1,R.id.tv_offline_week2,R.id.tv_offline_week3,R.id.tv_offline_week4,R.id.tv_offline_week5,R.id.tv_offline_week6,R.id.tv_offline_week7})
	private void selectWeek(View v) {
		switch (v.getId()) {
			case R.id.tv_offline_week1:
				if (tvWeek1.isSelected()){
					tvWeek1.setSelected(false);
				}else {
					tvWeek1.setSelected(true);
				}
				break;
			case R.id.tv_offline_week2:
				if (tvWeek2.isSelected()){
					tvWeek2.setSelected(false);
				}else {
					tvWeek2.setSelected(true);
				}
				break;
			case R.id.tv_offline_week3:
				if (tvWeek3.isSelected()){
					tvWeek3.setSelected(false);
				}else {
					tvWeek3.setSelected(true);
				}
				break;
			case R.id.tv_offline_week4:
				if (tvWeek4.isSelected()){
					tvWeek4.setSelected(false);
				}else {
					tvWeek4.setSelected(true);
				}
				break;
			case R.id.tv_offline_week5:
				if (tvWeek5.isSelected()){
					tvWeek5.setSelected(false);
				}else {
					tvWeek5.setSelected(true);
				}
				break;
			case R.id.tv_offline_week6:
				if (tvWeek6.isSelected()){
					tvWeek6.setSelected(false);
				}else {
					tvWeek6.setSelected(true);
				}
				break;
			case R.id.tv_offline_week7:
				if (tvWeek7.isSelected()){
					tvWeek7.setSelected(false);
				}else {
					tvWeek7.setSelected(true);
				}
				break;
			default:
				break;
		}
		getWeekStr();
		tvRepeat.setText(weekStr);

	}
	@Event(value = {R.id.ly_select_pppoe,R.id.ly_select_pppoe2,R.id.ly_select_pppoe3})
	private void select(View v) {
		switch (v.getId()) {
			case R.id.ly_select_pppoe:
				if (isShow){
					isShow = false;
					lyRepeat.setVisibility(View.GONE);
					ivRepeat.setImageResource(R.mipmap.next_arrow);
				}else {
					isShow = true;
					lyRepeat.setVisibility(View.VISIBLE);
					ivRepeat.setImageResource(R.mipmap.offline_up_lyout);
				}
				break;
			case R.id.ly_select_pppoe2:
				showPopupWindow(1);
				break;
			case R.id.ly_select_pppoe3:
				showPopupWindow(2);
				break;

			default:
				break;
		}
	}
	private PopupWindow mPopup;
	private View mPopLayout;
	public void showPopupWindow(int type) {
		BasePopupHelper.hidePopupWindow(mPopup);

		mPopLayout = mActivity.getLayoutInflater().inflate(
				R.layout.popu_time, null);
		initMenuView(mPopLayout,type);
		WindowManager wm = (WindowManager) mActivity
				.getSystemService(Context.WINDOW_SERVICE);

		int wmHeight = wm.getDefaultDisplay().getHeight();
		float density = mActivity.getResources().getDisplayMetrics().density;
//        mPopup = new PopupWindow(mPopLayout, (int) (width * density + 0.5f), ViewGroup.LayoutParams.MATCH_PARENT, true);
		mPopup = new PopupWindow(mPopLayout, ViewGroup.LayoutParams.MATCH_PARENT, (int) (density*220), true);
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
	private void initMenuView(View view, final int type) {
		TextView btnBind = (TextView) view.findViewById(R.id.btn_bind_router);
		TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
		final NumberPicker pickTime = (NumberPicker)view.findViewById(R.id.numberPicker1);
		final NumberPicker pickTime2 = (NumberPicker)view.findViewById(R.id.numberPicker2);
		String[] timehh = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
		pickTime.setDisplayedValues(timehh);
		pickTime.setMaxValue(timehh.length - 1);
		pickTime.setMinValue(0);

		String[] timemm = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"
				,"24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49"
				,"50","51","52","53","54","55","56","57","58","59"};
		pickTime2.setDisplayedValues(timemm);
		pickTime2.setMaxValue(timemm.length - 1);
		pickTime2.setMinValue(0);
		if (type == 1){
			if (onStr1>=0&&onStr1<24&&onStr2>=0&&onStr2<60){
				pickTime.setValue(onStr1);
				pickTime2.setValue(onStr2);
			}else {
				pickTime.setValue(0);
				pickTime2.setValue(0);
			}

		}else {
			if (offStr1>=0&&offStr1<24&&offStr2>=0&&offStr2<60){
				pickTime.setValue(offStr1);
				pickTime2.setValue(onStr2);
			}else {
				pickTime.setValue(0);
				pickTime2.setValue(0);
			}
		}




		btnBind.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (type == 1){
					onStr1 = 	pickTime.getValue();
					onStr2 = 	pickTime2.getValue();
					tvOn.setText(getStrOnInt(onStr1)+":"+getStrOnInt(onStr2));
				}else {
					offStr1 = 	pickTime.getValue();
					offStr2 = 	pickTime2.getValue();
					if (offStr1<onStr1||(onStr1==offStr1 && offStr2<=onStr2)){
						ShowToast( getResString(R.string.offline_better));
						return;
					}
					tvOff.setText(getStrOnInt(offStr1)+":"+getStrOnInt(offStr2));
				}
				mPopup.dismiss();
			}
		});
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopup.dismiss();
			}
		});
	}
	private void setWeekNo(){
	tvWeek1.setSelected(false);
	tvWeek2.setSelected(false);
	tvWeek3.setSelected(false);
	tvWeek4.setSelected(false);
	tvWeek5.setSelected(false);
	tvWeek6.setSelected(false);
	tvWeek7.setSelected(false);
	}
	private void setWeekStr(String str){
		if ("1".equals(str)){
			tvWeek1.setSelected(true);
		}else if ("2".equals(str)) {
			tvWeek2.setSelected(true);
		}else if ("3".equals(str)) {
			tvWeek3.setSelected(true);
		}else if ("4".equals(str)) {
			tvWeek4.setSelected(true);
		}else if ("5".equals(str)) {
			tvWeek5.setSelected(true);
		}else if ("6".equals(str)) {
			tvWeek6.setSelected(true);
		}else if ("7".equals(str)) {
			tvWeek7.setSelected(true);
		}else  if ("0".equals(str)){
			tvWeek1.setSelected(true);
			tvWeek2.setSelected(true);
			tvWeek3.setSelected(true);
			tvWeek4.setSelected(true);
			tvWeek5.setSelected(true);
			tvWeek6.setSelected(true);
			tvWeek7.setSelected(true);
		}
	}
	private void getWeekStr(){
		StringBuilder sbWeek = new StringBuilder();
		StringBuilder sbWeekCom = new StringBuilder("");
		if (tvWeek1.isSelected()&&tvWeek2.isSelected()&&tvWeek3.isSelected()&&tvWeek4.isSelected()&&tvWeek5.isSelected()&&tvWeek6.isSelected()&&tvWeek7.isSelected()){
			sbWeek.append(getResString(R.string.offline_everyday));
			sbWeekCom.append("1,2,3,4,5,6,7");
			weekStr =sbWeek.toString();
			weekStrCom = sbWeekCom.toString();
			return;
		}
		if (tvWeek1.isSelected()){
			sbWeek.append(getResString(R.string.offline_mon));
			sbWeekCom.append("1");
		}
		if (tvWeek2.isSelected()){
			if (sbWeek.length()>0){
				sbWeek.append(" ");
			}
			sbWeek.append(getResString(R.string.offline_tues));
			if (sbWeekCom.length()>0){
				sbWeekCom.append(",");
			}
			sbWeekCom.append("2");
		}
		if (tvWeek3.isSelected()){
			if (sbWeek.length()>0){
				sbWeek.append(" ");
			}
			sbWeek.append(getResString(R.string.offline_wed));
			if (sbWeekCom.length()>0){
				sbWeekCom.append(",");
			}
			sbWeekCom.append("3");
		}
		if (tvWeek4.isSelected()){
			if (sbWeek.length()>0){
				sbWeek.append(" ");
			}
			sbWeek.append(getResString(R.string.offline_thur));
			if (sbWeekCom.length()>0){
				sbWeekCom.append(",");
			}
			sbWeekCom.append("4");
		}
		if (tvWeek5.isSelected()){
			if (sbWeek.length()>0){
				sbWeek.append(" ");
			}
			sbWeek.append(getResString(R.string.offline_fri));
			if (sbWeekCom.length()>0){
				sbWeekCom.append(",");
			}
			sbWeekCom.append("5");
		}
		if (tvWeek6.isSelected()){
			if (sbWeek.length()>0){
				sbWeek.append(" ");
			}
			sbWeek.append(getResString(R.string.offline_sat));
			if (sbWeekCom.length()>0){
				sbWeekCom.append(",");
			}
			sbWeekCom.append("6");
		}
		if (tvWeek7.isSelected()){
			if (sbWeek.length()>0){
				sbWeek.append(" ");
			}
			sbWeek.append(getResString(R.string.offline_sun));
			if (sbWeekCom.length()>0){
				sbWeekCom.append(",");
			}
			sbWeekCom.append("7");
		}
		weekStr =sbWeek.toString();
		weekStrCom = sbWeekCom.toString();
	}


	private void getDeviceStatuHttp(){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.setTimer_Url);
		if ("0".equals(deviceSta.getIs_timer())){
			params.addBodyParameter("action","add");
			params.addBodyParameter("index","");
		}else {
			params.addBodyParameter("action","edit");
			params.addBodyParameter("index",deviceSta.getTimer_list_id());
		}
		params.addBodyParameter("mac",mac);
		params.addBodyParameter("cycle",weekStrCom);
		params.addBodyParameter("start1",getStrOnInt(onStr1));
		params.addBodyParameter("start2",getStrOnInt(onStr2));
		params.addBodyParameter("end1",getStrOnInt(offStr1));
		params.addBodyParameter("end2",getStrOnInt(offStr2));
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
	private String getStrOnInt(int num){
		if (num<10){
			return "0"+num;
		}else {
			return num+"";
		}
	}
}
