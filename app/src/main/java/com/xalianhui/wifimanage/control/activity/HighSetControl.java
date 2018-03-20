package com.xalianhui.wifimanage.control.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.bean.WifiHightCfgEnry;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.control.BaseActivityControl;
import com.xalianhui.wifimanage.db.enty.SlideItem;
import com.xalianhui.wifimanage.dialog.AutoDialog;
import com.xalianhui.wifimanage.function.MyCallBack;
import com.xalianhui.wifimanage.function.MyRequestParams;
import com.xalianhui.wifimanage.ui.view.BasePopupHelper;
import com.xalianhui.wifimanage.ui.view.MntAdapter;
import com.xalianhui.wifimanage.ui.view.MntHolder;
import com.xalianhui.wifimanage.ui.view.SwitchButton;
import com.xalianhui.wifimanage.utils.JsonUtil;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


public class HighSetControl extends BaseActivityControl {
	private static final String tag = HighSetControl.class.getSimpleName();
	private WifiHightCfgEnry wifiCfgEnry;
	private int index ;

	@ViewInject(R.id.tv_high_xindao)
	private TextView tvXindao;
	@ViewInject(R.id.tv_high_daikuan)
	private TextView tvDaikuan;
	@ViewInject(R.id.tv_high_qiangdu)
	private TextView tvQiangdu;
	private SwitchButton mShockSet ;
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
		setTitle(getResString(R.string.wifi_high_set));
		index = mActivity.getIntent().getExtras().getInt("index");
        Log.i("HighSetControl",index+"---------------------------");
		getRightBtn().setText(getResString(R.string.wifi_com));
		getRightBtn().setVisibility(View.VISIBLE);
		getRightBtn().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (wifiCfgEnry == null){
					ShowToast(getResString(R.string.high_set_xindao));
					return;
				}
				setWanHttp();
			}
		});
		mShockSet = (SwitchButton) mRootView
				.findViewById(R.id.msgpush_voiceset);
		getDeviceStatuHttp();
	}
	@Event(value = {R.id.ly_high_encrypt2,R.id.ly_high_encrypt3,R.id.ly_high_encrypt4})
	private void connWifi(View v) {
		if (wifiCfgEnry == null){
			ShowToast(getResString(R.string.high_set_xindao));
			getDeviceStatuHttp();
			return;
		}
		switch (v.getId()){
			case R.id.ly_high_encrypt2:
				showPopupWindow(1);
				break;
			case R.id.ly_high_encrypt3:
				showPopupWindow(2);
				break;
			case R.id.ly_high_encrypt4:
				showPopupWindow(3);
				break;
		}
	}
	private void setWanHttp(){
		MyRequestParams params = new MyRequestParams(Icont.Url_TopIP + Icont.setHightCfg_Url);
		if (index == 1) {
			params.addBodyParameter("bandwidth1", tvDaikuan.getText().toString());
			params.addBodyParameter("power1_percent", tvQiangdu.getText().toString());
			if (mShockSet.isChecked()){
				params.addBodyParameter("wmm1", "0");
			}else {
				params.addBodyParameter("wmm1", "1");
			}
			params.addBodyParameter("channel_a", tvXindao.getText().toString());

			params.addBodyParameter("bandwidth2", wifiCfgEnry.getBandwidth2());
			params.addBodyParameter("power2_percent", wifiCfgEnry.getPower2_percent());
			params.addBodyParameter("wmm2", wifiCfgEnry.getWmm2());
			params.addBodyParameter("channel_b", wifiCfgEnry.getChannel_b());
		}else {
			params.addBodyParameter("bandwidth2", tvDaikuan.getText().toString());
			params.addBodyParameter("power2_percent", tvQiangdu.getText().toString());
			if (mShockSet.isChecked()){
				params.addBodyParameter("wmm2", "0");
			}else {
				params.addBodyParameter("wmm2", "1");
			}
			params.addBodyParameter("channel_b", tvXindao.getText().toString());

			params.addBodyParameter("bandwidth1", wifiCfgEnry.getBandwidth1());
			params.addBodyParameter("power1_percent", wifiCfgEnry.getPower1_percent());
			params.addBodyParameter("wmm1", wifiCfgEnry.getWmm1());
			params.addBodyParameter("channel_a", wifiCfgEnry.getChannel_a());
		}
		if(wifiCfgEnry.getRegion()!=null&&!"".equals(wifiCfgEnry.getRegion())){
			params.addBodyParameter("region",wifiCfgEnry.getRegion());
		}else {
			params.addBodyParameter("region",wifiCfgEnry.getRange());
		}
		x.http().post(params, new MyCallBack<String>() {
			@Override
			public void onMSuccess(String result) {
				//fun add test
				Log.i(tag,"result:"+result);
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
						if (index == 1) {
							tvQiangdu.setText(wifiCfgEnry.getPower1_percent());
							tvDaikuan.setText(wifiCfgEnry.getBandwidth1());
							tvXindao.setText(wifiCfgEnry.getChannel_a());
							if ("0".equals(wifiCfgEnry.getWmm1())){
								mShockSet.setChecked(true);
							}else {
								mShockSet.setChecked(false);
							}
						}else {
							tvQiangdu.setText(wifiCfgEnry.getPower2_percent());
							tvDaikuan.setText(wifiCfgEnry.getBandwidth2());
							tvXindao.setText(wifiCfgEnry.getChannel_b());
							if ("0".equals(wifiCfgEnry.getWmm2())){
								mShockSet.setChecked(true);
							}else {
								mShockSet.setChecked(false);
							}
						}
					}
				}else {
					ShowToast(result);
				}
			}
		});
	}
    private PopupWindow mPopup;
    private View mPopLayout;
    public void showPopupWindow(int i) {
        BasePopupHelper.hidePopupWindow(mPopup);

        mPopLayout = mActivity.getLayoutInflater().inflate(
                R.layout.popu_number, null);
        initMenuView(mPopLayout,i);
//        mPopup = new PopupWindow(mPopLayout, (int) (width * density + 0.5f), ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopup = new PopupWindow(mPopLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        BasePopupHelper.initPopupWindow(mActivity, mPopup, R.style.common_popup_window_bottom_style);
        mPopup.showAtLocation(
                mActivity.getLayoutInflater().inflate(
                        R.layout.activity_main, null), Gravity.BOTTOM, 0, 0);
    }
	private MntAdapter<SlideItem> adapter;
	private String[] prows = {"auto","100","75","50","25"};
	private String[] bandwidths = {"HT20","HT40","HT80"};
    private void initMenuView(View view, final int type) {
		ListView leftMenu = (ListView) view.findViewById(R.id.lv_menu);
		final List<SlideItem> menuList = new ArrayList<>();
		if (type == 1){
			SlideItem slideItem;
			for (int i=0;i<prows.length;i++){
				slideItem = new SlideItem();
				slideItem.setPage(prows[i]);
				if (prows[i].equals(tvQiangdu.getText().toString())){
					slideItem.setIsShow(1);
				}else {
					slideItem.setIsShow(0);
				}
				menuList.add(slideItem);
			}

		}else if(type == 2){
			SlideItem slideItem;
			int length;
			if(index ==1){
				length = 2;
			}else {
				length = 3;
			}
			for (int i=0;i<length;i++){
				slideItem = new SlideItem();
				slideItem.setPage(bandwidths[i]);
				if (bandwidths[i].equals(tvDaikuan.getText().toString())){
					slideItem.setIsShow(1);
				}else {
					slideItem.setIsShow(0);
				}
				menuList.add(slideItem);
			}
		}else {
			SlideItem slideItem;
			String channelStr;
			if (wifiCfgEnry == null){
				ShowToast(getResString(R.string.high_set_xindao));
				return;
			}
			if(index ==1){
				channelStr = wifiCfgEnry.getChannel_a_range();
			}else {
				channelStr = wifiCfgEnry.getChannel_b_range();
			}
			if (channelStr == null){
				ShowToast(getResString(R.string.high_set_xindao));
				return;
			}
			String[] channels= channelStr.split(",");
			if (channels == null || channels.length == 0){
				ShowToast(getResString(R.string.high_set_xindao));
				return;
			}
			slideItem = new SlideItem();
			slideItem.setPage("auto");
			if ("auto".equals(tvXindao.getText().toString())){
				slideItem.setIsShow(1);
			}else {
				slideItem.setIsShow(0);
			}
			menuList.add(slideItem);
			for (int i=0;i<channels.length;i++){
				slideItem = new SlideItem();
				slideItem.setPage(channels[i]);
				if (channels[i].equals(tvXindao.getText().toString())){
					slideItem.setIsShow(1);
				}else {
					slideItem.setIsShow(0);
				}
				menuList.add(slideItem);
			}
		}
		adapter = new MntAdapter<SlideItem>(mActivity, R.layout.item_high_popu, menuList, true) {
			@Override
			public void setItemView(MntHolder simHolder, SlideItem airtest, boolean isSelected, int position, boolean isAvailable, boolean isEditable) {
				simHolder.setTextView(R.id.item_home_image_pm, airtest.getPage());
				if (airtest.getIsShow()==1){
					simHolder.getImageView(R.id.item_home_image_iv).setVisibility(View.VISIBLE);
					simHolder.getTextView(R.id.item_home_image_pm).setSelected(true);
				}else {
					simHolder.getImageView(R.id.item_home_image_iv).setVisibility(View.INVISIBLE);
					simHolder.getTextView(R.id.item_home_image_pm).setSelected(false);
				}
			}
		};
		leftMenu.setAdapter(adapter);
		leftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				for (int i=0;i<menuList.size();i++){
					if (i == position){
						menuList.get(i).setIsShow(1);
						if (type ==1){
							tvQiangdu.setText(menuList.get(i).getPage());
						}else if(type ==2 ){
							tvDaikuan.setText(menuList.get(i).getPage());
						}else {
							tvXindao.setText(menuList.get(i).getPage());
						}
					}else {
						menuList.get(i).setIsShow(0);
					}
				}
				mPopup.dismiss();
			}
		});
		view.findViewById(R.id.tv_top_popu).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopup.dismiss();
			}
		});
    }
}
