package com.xalianhui.wifimanage.control.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.bean.DeviceList;
import com.xalianhui.wifimanage.consts.Cache;
import com.xalianhui.wifimanage.consts.Constants;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.control.BaseActivityControl;
import com.xalianhui.wifimanage.db.DBDao;
import com.xalianhui.wifimanage.db.enty.SlideItem;
import com.xalianhui.wifimanage.function.IsRouteHelp;
import com.xalianhui.wifimanage.function.MyCallBack;
import com.xalianhui.wifimanage.function.MyRequestParams;
import com.xalianhui.wifimanage.ui.activity.MainActivity;
import com.xalianhui.wifimanage.ui.activity.WelcomeActivity;
import com.xalianhui.wifimanage.ui.fragment.FirstFragment;
import com.xalianhui.wifimanage.ui.fragment.RouterFragment;
import com.xalianhui.wifimanage.ui.fragment.SystemFragment;
import com.xalianhui.wifimanage.ui.fragment.ToolFragment;
import com.xalianhui.wifimanage.ui.view.BasePopupHelper;
import com.xalianhui.wifimanage.utils.JsonUtil;
import com.xalianhui.wifimanage.utils.TextUtil;
import com.xalianhui.wifimanage.utils.WifiUtils;

import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import miky.android.common.util.PreferencesUtils;
import miky.android.common.util.StringUtils;


public class MainControl extends BaseActivityControl {
    private static final String tag = "MainControl";
    private FirstFragment firstFragment;
    private RouterFragment routerFragment;
    private SystemFragment systemFragment;
    private ToolFragment toolFragment;
//    private RiverHisChartFragment mapFragment;

    private Fragment currFragment;

    private DBDao dbDao;

    @ViewInject(R.id.blow_menu_router)
    private LinearLayout lyRouter;
    @ViewInject(R.id.blow_menu_system)
    private LinearLayout lySystem;
    @ViewInject(R.id.blow_menu_tool)
    private LinearLayout lyTool;


    @Override
    public void onInit(View rootView, Context context) {
        super.onInit(rootView, context);
        x.view().inject(this, rootView);
        dbDao = new DBDao(mActivity);
        initView();
    }

    //新问题广播接收器
    BroadcastReceiver newwarn_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Consts.ACTION_REQUEXT_ERROR)){
                Log.i(tag,"收到ACTION_REQUEXT_ERROR广播");
               boolean isConnRouter =  intent.getExtras().getBoolean("isRoute");
                if (!isConnRouter) {
//				ShowToast(getResString(R.string.reconn_router));
                    if (!Cache.isConnRouter) {
                        Intent intent2 = new Intent(mActivity, MainActivity.class);
                        mActivity.startActivity(intent2);
                        if (firstFragment != null) {
                            firstFragment.setNOConnt();
                            Log.i(tag, "firstFragment not null");
                        }else{
                            Log.i(tag,"firstFragment is null");
                        }
                        // fun add 侧边栏是打开状态，手动断开网络，侧边栏关闭。（左边侧边栏关闭，在没有WiFi网络的情况下）
                        if(routerFragment != null){
                            Log.i(tag, "routerFragment not null");
                            routerFragment.closeSlideMenu();
                        }else{
                            Log.i(tag, "routerFragment is null");
                        }
                    }
                }
            }else{
                Log.i(tag,"收到其他广播");
            }
//            boolean str = intent.getExtras().getBoolean("isRoute");
//            Log.i("newwarn_receiver", "onReceive =====>" + str);
//            if (!str) {
////				ShowToast(getResString(R.string.reconn_router));
//                if (!Cache.isConnRouter) {
//                    Intent intent2 = new Intent(mActivity, MainActivity.class);
//                    mActivity.startActivity(intent2);
//                    if (firstFragment != null) {
//                        firstFragment.setNOConnt();
//                        Log.i(tag, "firstFragment not null");
//                    }else{
//                        Log.i(tag,"firstFragment is null");
//                    }
//                }
////				onStart();
//            }
        }
    };

    @Override
    public void onRecycle() {
        if (thread.isAlive()) {
            threadLive = false;
        }
        mActivity.unregisterReceiver(newwarn_receiver);
    }

    private void initView() {
        setTopView(Consts.TopPage.MAIN);
        lyRouter.setSelected(true);
        mActivity.registerReceiver(newwarn_receiver, new IntentFilter(
                Consts.ACTION_REQUEXT_ERROR));
//		String username = PreferencesUtils.getString(mActivity, Consts.KEY_USERNAME,"");
        List<SlideItem> menuListAll = dbDao.getAllMenu();
        if (menuListAll.size() == 0) {
            setMenuData();
        }
        initData();
//		WifiUtils.getIPAddress(mActivity);
    }
    private void initData() {
        if (WifiUtils.NetworkDetector(mActivity) != 1 || !Cache.isConnRouter || !Cache.isLogin) {
            setSelectFrag(Consts.FRAG_INDEX_MAIN_FIRST);
            firstFragment.Refresh();
            lyRouter.setSelected(true);
            lySystem.setSelected(false);
            lyTool.setSelected(false);
        } else {
            if (currFragment == null || currFragment == firstFragment || currFragment == routerFragment) {
                setSelectFrag(Consts.FRAG_INDEX_MAIN_ROUTER);
                getRouterHttp();
                lyRouter.setSelected(true);
                lySystem.setSelected(false);
                lyTool.setSelected(false);
            }
        }

    }

    @Event(value = {R.id.blow_menu_router, R.id.blow_menu_system, R.id.blow_menu_tool})
    private void select(View v) {
        //fun remove 3 lines
        if (v.getId() != R.id.blow_menu_router && (!Cache.isConnRouter || !Cache.isLogin)) {
            ShowToast(getResString(R.string.main_login_router));
            return;
        }
        //fun add 1.没有连路由器（wifi）2.没有登陆。两种情况分开提示
//        if (v.getId() != R.id.blow_menu_router) {
//            if (!Cache.isConnRouter) {
//                ShowToast(getResString(R.string.main_login_router_NoWifi));
//                return;
//            } else {
//                if (!Cache.isLogin) {
//                    ShowToast(getResString(R.string.main_login_router));
//                    return;
//                }
//            }
//        }else{
//
//        }

        lyRouter.setSelected(false);
        lySystem.setSelected(false);
        lyTool.setSelected(false);
        switch (v.getId()) {
            case R.id.blow_menu_router:
                lyRouter.setSelected(true);
                if (WifiUtils.NetworkDetector(mActivity) != 1) {
                    setSelectFrag(Consts.FRAG_INDEX_MAIN_FIRST);
                } else if (!Cache.isConnRouter) {
                    setSelectFrag(Consts.FRAG_INDEX_MAIN_FIRST);
                    firstFragment.Refresh();
                }else if(!Cache.isLogin){//fun add 防止用户在没有登录的状态不会显示routerfragment默认的布局界面
                    setSelectFrag(Consts.FRAG_INDEX_MAIN_FIRST);
                }else {
                    setSelectFrag(Consts.FRAG_INDEX_MAIN_ROUTER);
                }
                break;
            case R.id.blow_menu_system:
                lySystem.setSelected(true);
                setSelectFrag(Consts.FRAG_INDEX_MAIN_SYSTEM);
                break;
            case R.id.blow_menu_tool:
                lyTool.setSelected(true);
                setSelectFrag(Consts.FRAG_INDEX_MAIN_TOOL);
                break;

            default:
                break;
        }
    }

    // 禁用返回键，避免启动页强制关闭应用导致的问题
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity).setMessage(
                    getResString(R.string.quit_app)).setPositiveButton(getResString(R.string.quit_sure),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 退出系统
                            // PullParseService.getExitAlert(LoginActivity.this,
                            // userName);
                            threadLive = false;
                            mActivity.finish();
                        }
                    });
            builder.setNegativeButton(getResString(R.string.quit_cancel),
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                        }
                    });
            builder.create().show();
            return true;
        }
        return false;
    }

    private void setSelectFrag(int fragIndex) {
        FragmentManager manager = mActivity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragment(transaction);
        switch (fragIndex) {
            case Consts.FRAG_INDEX_MAIN_FIRST:
                if (firstFragment == null) {
                    firstFragment = new FirstFragment();
                    transaction.add(R.id.main_fragment_context, firstFragment);
                } else {
                    transaction.show(firstFragment);
                }
                currFragment = firstFragment;
                break;
            case Consts.FRAG_INDEX_MAIN_ROUTER:
                if (routerFragment == null) {
                    routerFragment = new RouterFragment();
                    transaction.add(R.id.main_fragment_context, routerFragment);
                } else {
                    transaction.show(routerFragment);
                }
                currFragment = routerFragment;
                break;
            case Consts.FRAG_INDEX_MAIN_SYSTEM:
                if (systemFragment == null) {
                    systemFragment = new SystemFragment();
                    transaction.add(R.id.main_fragment_context, systemFragment);
                } else {
                    transaction.show(systemFragment);
                }
                currFragment = systemFragment;
                break;
            case Consts.FRAG_INDEX_MAIN_TOOL:
                if (toolFragment == null) {
                    toolFragment = new ToolFragment();
                    transaction.add(R.id.main_fragment_context, toolFragment);
                } else {
                    transaction.show(toolFragment);
                }
                currFragment = toolFragment;
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (firstFragment != null) {
            transaction.hide(firstFragment);
        }
        if (routerFragment != null) {
            transaction.hide(routerFragment);
        }
        if (systemFragment != null) {
            transaction.hide(systemFragment);
        }
        if (toolFragment != null) {
            transaction.hide(toolFragment);
        }
    }


    public void onFragment(int fragmentIndex) {
        switch (fragmentIndex) {
            case Consts.SELECT_MAIN_CONN_ROUTER:
                onStart();
                break;
            case Consts.FRAG_INDEX_MAIN_ROUTER:
                onStart();
                break;
        }
    }

    public void onStart() {
        IsRouteHelp.getInstance().loginHttp(null);
//		if (!Cache.isLogin ){
        String password = PreferencesUtils.getString(mActivity, Consts.KEY_PASSWORD, "");
        if (!"".equals(password)) {
            loginHttp(password);
        }
//		}

//			initData();

    }

    private void loginHttp(final String password) {
        MyRequestParams params = new MyRequestParams(Icont.Url_TopIP + Icont.login_Url);
        params.addBodyParameter("password", password);
        params.addBodyParameter("lang", TextUtil.getLone());
//		params.addParameter("password","123");
//		params.addHeader("head","android"); //为当前请求添加一个头
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //解析result
                Log.i("result", result);
                if ("1".equals(result)) {
                    Cache.isLogin = true;
                } else {
                    Cache.isLogin = false;
//					ShowToast(result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                initData();
            }
        });
    }

    private void setMenuData() {
        List<SlideItem> menuListAll = new ArrayList<>();
        menuListAll.add(new SlideItem(R.mipmap.menu_one, R.string.menu_rocket_boost, "com.xalianhui.wifimanage.ui.activity.OneActivity", 0, 1));
        menuListAll.add(new SlideItem(R.mipmap.menu_jiank, R.string.menu_healthy_mode, "com.xalianhui.wifimanage.ui.activity.HealthyActivity", 0, 2));
//		menuListAll.add(new SlideItem(R.mipmap.menu_reboot,R.string.menu_schedule_reboot,"com.xalianhui.wifimanage.ui.activity.RebootActivity",0));
        menuListAll.add(new SlideItem(R.mipmap.menu_jiaz, R.string.menu_parental_control, "com.xalianhui.wifimanage.ui.activity.JiazhangActivity", 1, 3));
//        menuListAll.add(new SlideItem(R.mipmap.menu_green,R.string.menu_green_wifi,"com.xalianhui.wifimanage.ui.activity.GreenWIFIActivity",1,4));
        menuListAll.add(new SlideItem(R.mipmap.menu_black, R.string.menu_blacklist, "com.xalianhui.wifimanage.ui.activity.BlackActivity", 0, 5));
        menuListAll.add(new SlideItem(R.mipmap.menu_xiansu, R.string.menu_smart_qos, "com.xalianhui.wifimanage.ui.activity.XiansuActivity", 1, 6));
//        menuListAll.add(new SlideItem(R.mipmap.menu_fangke,R.string.menu_guest_wifi,"com.xalianhui.wifimanage.ui.activity.FkXiansuActivity",0));
        menuListAll.add(new SlideItem(R.mipmap.menu_wifi, R.string.menu_wifi, "com.xalianhui.wifimanage.ui.activity.SetWifiActivity", 0, 7));
        menuListAll.add(new SlideItem(R.mipmap.menu, R.string.menu_network, "com.xalianhui.wifimanage.ui.activity.NetSetActivity", 0, 8));
        menuListAll.add(new SlideItem(R.mipmap.menu_admin, R.string.menu_management, "com.xalianhui.wifimanage.ui.activity.AdminPasswActivity", 0, 9));
        menuListAll.add(new SlideItem(R.mipmap.menu_version, R.string.menu_upgrade_firmware, "com.xalianhui.wifimanage.ui.activity.FirmWareActivity", 0, 10));
        menuListAll.add(new SlideItem(R.mipmap.menu_restore, R.string.menu_reset, "com.xalianhui.wifimanage.ui.activity.RestoreActivity", 0, 11));
//		menuListAll.add(new SlideItem(R.mipmap.menu_deng,R.string.menu_led,"",0));
        try {
            dbDao.getDBUtils().save(menuListAll);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void getRouterHttp() {
        MyRequestParams params = new MyRequestParams(Icont.Url_TopIP + Icont.getRouter_Url);
        x.http().post(params, new MyCallBack<String>() {
            @Override
            public void onMSuccess(String result) {
                if (!"".equals(result)) {
                    DeviceList records = JsonUtil.getObject(result, DeviceList.class);
                    if (records != null) {
                        Log.i("devicelist", records.toString());
                        if (routerFragment != null) {
                            routerFragment.setDeviceList(records);
                        }
                    }
                } else {
                    ShowToast(getResString(R.string.network_internet_not));
                }
            }

            @Override
            public void onFinished() {
                //解析result
                if (!thread.isAlive()) {
                    thread.start();
                }
            }
        });
    }

    private boolean threadLive = true;
    private Thread thread = new Thread() {
        @Override
        public void run() {
            super.run();
            Log.i("result", "Thread mian");
            while (threadLive) {
                try {
                    sleep(Constants.Load_SLEEP);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Cache.isLoading) {
                    handler.sendEmptyMessage(0);
                }
            }
        }
    };
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    getRouterHttp();
                    // fun add 如果用户在后台把管理员的密码修改了的话，当前router界面显示连接路由按钮的界面（把onStart的方法逻辑重新走一遍）
//                    onStart();
                    break;
                case 1:
//					IsRouteHelp.getInstance().loginHttp(null);
                    break;
            }
        }
    };
}
