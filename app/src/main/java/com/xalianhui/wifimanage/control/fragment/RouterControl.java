package com.xalianhui.wifimanage.control.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.bean.Device;
import com.xalianhui.wifimanage.bean.DeviceList;
import com.xalianhui.wifimanage.bean.IsNewRouter;
import com.xalianhui.wifimanage.bean.Smart;
import com.xalianhui.wifimanage.consts.Cache;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.control.BaseFragmentControl;
import com.xalianhui.wifimanage.db.DBDao;
import com.xalianhui.wifimanage.db.enty.SlideItem;
import com.xalianhui.wifimanage.function.IsRouteHelp;
import com.xalianhui.wifimanage.interfaces.OnHttpSelector;
import com.xalianhui.wifimanage.ui.activity.DeviceXQActivity;
import com.xalianhui.wifimanage.ui.view.BasePopupHelper;
import com.xalianhui.wifimanage.ui.view.MntAdapter;
import com.xalianhui.wifimanage.ui.view.MntHolder;
import com.xalianhui.wifimanage.utils.JsonUtil;
import com.xalianhui.wifimanage.utils.TextUtil;
import com.xalianhui.wifimanage.utils.WifiUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import miky.android.common.util.ContextUtil;


public class RouterControl extends BaseFragmentControl {


    @ViewInject(R.id.tv_up_sudu)
    private TextView upLink;
    @ViewInject(R.id.tv_down_sudu)
    private TextView downLink;
    @ViewInject(R.id.tv_up_sudu_kb)
    private TextView upLinkKB;
    @ViewInject(R.id.tv_down_sudu_kb)
    private TextView downLinkKb;
    @ViewInject(R.id.tv_top_left)
    private TextView tvLeft;
    @ViewInject(R.id.tv_top_right)
    private TextView tvRight;
    @ViewInject(R.id.ly_shebei)
    private LinearLayout lyShebei;
    @ViewInject(R.id.ly_device)
    private LinearLayout lyDevice;

    @ViewInject(R.id.lv_device)
    private ListView lvDevice;
    @ViewInject(R.id.lv_smart)
    private ListView lvSmart;

	private List<SlideItem> menuList = new ArrayList<>();
	private DeviceList deviceList;
	private MntAdapter<SlideItem> adapter;
	private MntAdapter<Device> adapterDevice;
	private MntAdapter<Smart> adapterSmart;
	private DBDao dbDao;
	public float density;
	private int wmHeight;
	private String indexmac ="";
	private boolean isLoading = true;
	private boolean isFrist ;
	private int isSelect ;//0 为未设置 ，1 子母路由 ，2 airbox ，3 金刚路由 ，  4 相框路由
	@Override
	public void onInit(View rootView, Context context) {
		super.onInit(rootView, context);
		x.view().inject(this, rootView);
		dbDao = new DBDao(mActivity);
      initView();
	}
	
	private void initView() {
		this.density = mActivity.getResources().getDisplayMetrics().density;
		WindowManager wm = (WindowManager) mActivity
				.getSystemService(Context.WINDOW_SERVICE);

		wmHeight = wm.getDefaultDisplay().getHeight();
		tvLeft.setSelected(true);
		lyDevice.setVisibility(View.VISIBLE);
		adapterDevice = new MntAdapter<Device>(mActivity, R.layout.item_devicelist, null, true) {
			@Override
			public void setItemView(MntHolder simHolder, Device airtest, boolean isSelected, int position, boolean isAvailable, boolean isEditable) {

				simHolder.setTextView(R.id.tv_iten_name, airtest.getSta_name());
				String totalStr = airtest.getDown_rate();
				simHolder.setTextView(R.id.tv_iten_rate, TextUtil.getTraffic1(totalStr));
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

//				simHolder.setImageView(R.id.item_home_image_iv, airtest.getResId());
			}
		};
		lvDevice.setAdapter(adapterDevice);
		adapterSmart = new MntAdapter<Smart>(mActivity, R.layout.item_smartlist, null, true) {
			@Override
			public void setItemView(MntHolder simHolder, Smart airtest, boolean isSelected, int position, boolean isAvailable, boolean isEditable) {
				simHolder.setTextView(R.id.tv_iten_name, airtest.getMac());
				if ("wired".equals(airtest.getLink_type())){
					simHolder.setImageView(R.id.iv_item_img, R.mipmap.router_in_line);
					simHolder.setTextView(R.id.tv_iten_name, getResString(R.string.router_cable));
				}else {
					simHolder.setTextView(R.id.tv_iten_name, getResString(R.string.router_wireless));
					simHolder.setImageView(R.id.iv_item_img, R.mipmap.router_in_wifi);
				}
//				simHolder.setTextView(R.id.tv_iten_rate, airtest.getRate());
			}
		};
		lvSmart.setAdapter(adapterSmart);
		lvDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				indexmac = deviceList.getOnline_sta().get(i).getMac();
				Intent intent = new Intent(mActivity, DeviceXQActivity.class);
				intent.putExtra("device",deviceList.getOnline_sta().get(i));
				mActivity.startActivity(intent);
			}
		});
    }

	@Event(value = R.id.im_back)
	private void showLeftMenu(View v) {
		showPopupWindow();
	}
	@Event(value = {R.id.tv_top_left,R.id.tv_top_right})
	 private void setPage(View v) {
		tvLeft.setSelected(false);
		tvRight.setSelected(false);
		lyDevice.setVisibility(View.GONE);
		lyShebei.setVisibility(View.GONE);
		switch (v.getId()) {
			case R.id.tv_top_left:
				tvLeft.setSelected(true);
				lyDevice.setVisibility(View.VISIBLE);
				break;
			case R.id.tv_top_right:
				tvRight.setSelected(true);
				lyShebei.setVisibility(View.VISIBLE);
				break;

			default:
				break;
		}
	}

	private PopupWindow mPopup;
	private View mPopLayout;
	private static final int width = 180;
	public void showPopupWindow() {
		BasePopupHelper.hidePopupWindow(mPopup);

		mPopLayout = mActivity.getLayoutInflater().inflate(
				R.layout.slide_menu, null);
		initMenuView(mPopLayout);
		// ((MainActivity)mActivity).applyBlur();
//        mPopup = new PopupWindow(mPopLayout, (int) (width * density + 0.5f), ViewGroup.LayoutParams.MATCH_PARENT, true);
		mPopup = new PopupWindow(mPopLayout, (int) (width * density + 0.5f), wmHeight, true);
		BasePopupHelper.initPopupWindow(mActivity, mPopup, R.style.common_popup_window_horizontal_style);
		mPopup.showAtLocation(
				mActivity.getLayoutInflater().inflate(
						R.layout.activity_main, null), Gravity.LEFT
						| Gravity.BOTTOM, 0, 0);
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
		RelativeLayout userLinear = (RelativeLayout) view.findViewById(R.id.linear_user);
		userLinear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopup.dismiss();
			}
		});
		//fun add 根据手机当前所连接的路由器的型号，显示不同的路由器图标和文字提示
		setRouterStyle();

		setSlideItemData();
		ListView leftMenu = (ListView) view.findViewById(R.id.lv_menu);
		adapter = new MntAdapter<SlideItem>(mActivity, R.layout.item_home_image, menuList, true) {
			@Override
			public void setItemView(MntHolder simHolder, SlideItem airtest, boolean isSelected, int position, boolean isAvailable, boolean isEditable) {
				simHolder.setTextView(R.id.item_home_image_pm, airtest.getText());
				simHolder.setImageView(R.id.item_home_image_iv, airtest.getResId());
			}
		};
		leftMenu.setAdapter(adapter);
		leftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				if (!"".equals(menuList.get(position).getPage())) {
					Intent intent = new Intent();
					intent.setAction(menuList.get(position).getPage());
					mActivity.startActivity(intent);
				}else {
					ShowToast(getResString(menuList.get(position).getText()));
				}
				mPopup.dismiss();
			}
		});
		View footView = LayoutInflater.from(mActivity).inflate(R.layout.menu_list_foot, null);
		LinearLayout tv_insert = (LinearLayout) footView.findViewById(R.id.item_menu_layout);
		tv_insert.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("com.xalianhui.wifimanage.ui.activity.MenuSelectActivity");
				mActivity.startActivity(intent);
				mPopup.dismiss();
			}
		});
		leftMenu.addFooterView(footView);
	}
	private OnHttpSelector onHttpSelector = new OnHttpSelector() {
		@Override
		public void onResult(String result) {
			IsNewRouter mVersion = JsonUtil.getObject(result,IsNewRouter.class);
			if(mVersion != null) {
				if ("0".equals(mVersion.getIs_new())) {
					isFrist = false;
				} else if ("1".equals(mVersion.getIs_new())) {
					isFrist = true;
				}
				if ("0".equals(mVersion.getDev_type())) {
					isSelect = 1;
//				setSearchIndex(result);
				} else if ("1".equals(mVersion.getDev_type())) {
					isSelect = 2;
				} else if ("2".equals(mVersion.getDev_type())) {
					isSelect = 3;
				} else if ("3".equals(mVersion.getDev_type())) {
					isSelect = 4;
				} else {
					isSelect = -1;
//				initView();
//				ShowToast(getResString(R.string.find_router_not));
				}
			}else {
				isSelect = -1;
			}
		}
		@Override
		public void onFinished() {
			setSearchView();
		}
	};
	private ImageView iv_routerStyle;
	private TextView tv_routerStyleText;
	private void setSearchView(){
		iv_routerStyle = (ImageView) mPopLayout.findViewById(R.id.tv_menu_image);
		tv_routerStyleText = (TextView) mPopLayout.findViewById(R.id.tv_user);
		switch (isSelect){
			case 1:
				iv_routerStyle.setImageResource(R.mipmap.router0_zm);
				tv_routerStyleText.setText(getResString(R.string.first_search_s1));
				break;
			case 2:
				iv_routerStyle.setImageResource(R.mipmap.router1_airbox);
				tv_routerStyleText.setText(getResString(R.string.first_search_s2));
				break;
			case 3:
				iv_routerStyle.setImageResource(R.mipmap.router2_airoptiums);
				tv_routerStyleText.setText(getResString(R.string.first_search_s3));
				break;
			case 4:
				iv_routerStyle.setImageResource(R.mipmap.first_dialog_airframe);
				tv_routerStyleText.setText(getResString(R.string.first_search_s4));
				break;
			default:
//				iv_routerStyle.setImageResource(R.mipmap.first_dialog_router);
//				tv_routerStyleText.setText(getResString(R.string.search_device_no));
				break;
		}
	}

	private void setRouterStyle() {
		IsRouteHelp.getInstance().loginHttp(onHttpSelector);
	}

	private void  setSlideItemData() {
		menuList = dbDao.getShowMenu();
	}
	@Override
	public void onRecycle() {
		// TODO Auto-generated method stub
		isLoading = false;
	}

	public void setDeviceList(DeviceList deviceList) {
		if ( deviceList != null){
			this.deviceList = deviceList;
			adapterDevice.updata(deviceList.getOnline_sta());
			adapterSmart.updata(deviceList.getSmart_dev());
			String upStr = TextUtil.getTraffic1(deviceList.getUplink());
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
			String downStr = TextUtil.getTraffic1(deviceList.getDownlink());
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
			if (deviceList.getOnline_sta()!= null && deviceList.getOnline_sta().size()>0) {
				for (int i = 0;i<deviceList.getOnline_sta().size();i++) {
					if (indexmac != "" && indexmac.equals(deviceList.getOnline_sta().get(i).getMac())) {
						Intent intent = new Intent();  //用于传输数据的intent
						intent.setAction(Consts.ACTION_BROADCAST_LOCATION);
						intent.putExtra("device", deviceList.getOnline_sta().get(i));//为这个intent加上action，用于广播过滤器过滤
						ContextUtil.getInstance().getApplicationContext().sendBroadcast(intent); //使用sendBroadcast发送广播
					}
				}
			}
		}
	}
	public void Refresh() {
		initView();
	}
	public void closeSlideMenu(){
		if(mPopup != null && mPopup.isShowing()){
			mPopup.dismiss();
			mPopup = null;
		}
	}
}
