package com.xalianhui.wifimanage.control.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.bean.DeviceXQEnry;
import com.xalianhui.wifimanage.bean.QosDeviceList;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.control.BaseActivityControl;
import com.xalianhui.wifimanage.function.MyCallBack;
import com.xalianhui.wifimanage.function.MyRequestParams;
import com.xalianhui.wifimanage.ui.activity.AddDeviceActivity;
import com.xalianhui.wifimanage.ui.activity.DeviceXiansuActivity;
import com.xalianhui.wifimanage.ui.view.MntAdapter;
import com.xalianhui.wifimanage.ui.view.MntHolder;
import com.xalianhui.wifimanage.utils.JsonUtil;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;


public class XiansuControl extends BaseActivityControl {

	@ViewInject(R.id.lv_device)
	private ListView lvDevice;
	private MntAdapter<QosDeviceList> adapterDevice;
	private List<QosDeviceList> blackList;
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
		setTitle(getResString(R.string.menu_smart_qos));
		adapterDevice = new MntAdapter<QosDeviceList>(mActivity, R.layout.item_qos_list, blackList, true) {
			@Override
			public void setItemView(MntHolder simHolder, QosDeviceList airtest, boolean isSelected, final int position, boolean isAvailable, boolean isEditable) {
				if (airtest.getSta_name()!= null &&!"".equals(airtest.getSta_name())&&!"*".equals(airtest.getSta_name())) {
					simHolder.setTextView(R.id.tv_item_qos_title, airtest.getSta_name());
				}else {
					simHolder.setTextView(R.id.tv_item_qos_title, airtest.getMac());
				}
				if ("pc".equals(airtest.getSta_type())){
					simHolder.setImageView(R.id.iv_item_img,R.mipmap.device_computer);
				}else if ("ipad".equals(airtest.getSta_type())){
					simHolder.setImageView(R.id.iv_item_img,R.mipmap.device_ipad);
				}else {
					simHolder.setImageView(R.id.iv_item_img,R.mipmap.device_phone);
				}
				simHolder.setTextView(R.id.tv_item_qos_content,getResString(R.string.qos_lim_width)+ airtest.getWidth()+"KB/s");
			}
		};
		lvDevice.setAdapter(adapterDevice);
		lvDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				getDeviceStatuHttp(blackList.get(i));
			}
		});
	}
	@Event(value = R.id.tv_ok)
	private void onClick(View v) {
		Intent intent = new Intent(mActivity, AddDeviceActivity.class);
		intent.putExtra("type",Consts.ADD_DEVICE_POS);
		mActivity.startActivity(intent);
	}
	private void getBlacklistHttp(){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.getQosList_Url);
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//解析result
				if(!"".equals(result)){
					List<QosDeviceList> records= JsonUtil.JsonToList(result,QosDeviceList.class);
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
	private void getDeviceStatuHttp(final QosDeviceList qosDeviceList){
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
							intent.putExtra("ip",qosDeviceList.getIp1());
							intent.putExtra("deviceSta",deviceSta);
							mActivity.startActivity(intent);
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


}
