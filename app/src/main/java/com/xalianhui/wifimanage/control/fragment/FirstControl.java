package com.xalianhui.wifimanage.control.fragment;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.bean.IsNewRouter;
import com.xalianhui.wifimanage.consts.Cache;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.control.BaseFragmentControl;
import com.xalianhui.wifimanage.function.IsRouteHelp;
import com.xalianhui.wifimanage.function.MyRequestParams;
import com.xalianhui.wifimanage.interfaces.OnHttpSelector;
import com.xalianhui.wifimanage.ui.activity.SelectLoginActivity;
import com.xalianhui.wifimanage.ui.activity.UserAgreementActivity;
import com.xalianhui.wifimanage.ui.view.BasePopupHelper;
import com.xalianhui.wifimanage.ui.view.MntDialog;
import com.xalianhui.wifimanage.utils.JsonUtil;
import com.xalianhui.wifimanage.utils.TextUtil;
import com.xalianhui.wifimanage.utils.WifiUtils;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import miky.android.common.util.PreferencesUtils;


public class FirstControl extends BaseFragmentControl {
    private static final String tag = "FirstControl";

    @ViewInject(R.id.tv_not_wifi)
    private TextView tvNotWifi;

    @ViewInject(R.id.btn_conn_wifi)
    private TextView btnConnWifi;

    @ViewInject(R.id.btn_set_new_router)
    private TextView btnSetNewWifi;

    @ViewInject(R.id.tv_user_agreement)
    private TextView tvUserAgreement;
    private MntDialog mntDialog;
    private MntDialog bindDialog;
    private boolean isFrist;
    private int isSelect;//0 为未设置 ，1 子母路由 ，2 airbox ，3 金刚路由 ，  4 相框路由
    private boolean isShowTip = false;

    @Override
    public void onInit(View rootView, Context context) {
        super.onInit(rootView, context);
        x.view().inject(this, rootView);
        initView();
        isSelect = -1;
    }

    private void initView() {
        if (WifiUtils.NetworkDetector(mActivity) != 1) {
            tvNotWifi.setText(getResString(R.string.not_wifi));
            btnConnWifi.setVisibility(View.VISIBLE);
            btnSetNewWifi.setVisibility(View.GONE);
            tvUserAgreement.setVisibility(View.GONE);
        } else {
            tvNotWifi.setText(getResString(R.string.conn_router));
            btnConnWifi.setVisibility(View.GONE);
            btnSetNewWifi.setVisibility(View.VISIBLE);
            tvUserAgreement.setVisibility(View.VISIBLE);
        }

        setUserAgreement();
        if (Cache.isConnRouter && Cache.isLogin) {
            if (mntDialog != null && mntDialog.isShowing()) {
                mntDialog.dismiss();
                mntDialog = null;
            }
            Log.i(tag,"firstControl initView mOnFragmentSelector.onFragment");
            mOnFragmentSelector.onFragment(Consts.FRAG_INDEX_MAIN_ROUTER);
        }
    }

    private void setUserAgreement() {
        String text = getResString(R.string.user_agreement);
        tvUserAgreement.setText(Html.fromHtml(text));
    }

    @Event(value = R.id.tv_user_agreement)
    private void toUSerArgeemrnt(View v) {
        Intent intent = new Intent(mActivity, UserAgreementActivity.class);
        mActivity.startActivity(intent);
    }

    @Event(value = R.id.btn_conn_wifi)
    private void connWifi(View v) {
        //fun add
        if (WifiUtils.NetworkDetector(mActivity) != 1) {
            if (android.os.Build.VERSION.SDK_INT > 10) {
                mActivity.startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
            } else {
                mActivity.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
            }
            tvNotWifi.setText(getResString(R.string.not_wifi));
            btnConnWifi.setVisibility(View.VISIBLE);
            btnSetNewWifi.setVisibility(View.GONE);
            tvUserAgreement.setVisibility(View.GONE);
        } else {
            tvNotWifi.setText(getResString(R.string.conn_router));
            btnConnWifi.setVisibility(View.GONE);
            btnSetNewWifi.setVisibility(View.VISIBLE);
            tvUserAgreement.setVisibility(View.VISIBLE);
        }
//		if(android.os.Build.VERSION.SDK_INT > 10) {
//			mActivity.startActivity(new Intent( android.provider.Settings.ACTION_WIFI_SETTINGS));
//		} else {
//			mActivity.startActivity(new Intent( android.provider.Settings.ACTION_WIRELESS_SETTINGS));
//		}
    }

    @Event(value = R.id.btn_set_new_router)
    private void connRouter(View v) {
        Log.i(tag, "点击连接路由");
        //fun add 当没有连接网络的时候显示没有连接网络的布局（没有连接WiFi的按钮）
        if (WifiUtils.NetworkDetector(mActivity) != 1) {
            tvNotWifi.setText(getResString(R.string.not_wifi));
            btnConnWifi.setVisibility(View.VISIBLE);
            btnSetNewWifi.setVisibility(View.GONE);
            tvUserAgreement.setVisibility(View.GONE);
            return;
        }
        IsRouteHelp.getInstance().loginHttp(new OnHttpSelector() {
            @Override
            public void onResult(String result) {
                IsNewRouter mVersion = JsonUtil.getObject(result, IsNewRouter.class);
                if (mVersion != null) {
                    Log.i(tag, "onResult:" + mVersion.getIs_new() + "==" + mVersion.getDev_type());
                    if ("0".equals(mVersion.getIs_new())) {
                        isFrist = false;
                    } else if ("1".equals(mVersion.getIs_new())) {
                        isFrist = true;
                    }
                    if ("0".equals(mVersion.getDev_type())) {
                        isSelect = 1;
                    } else if ("1".equals(mVersion.getDev_type())) {
                        isSelect = 2;
                    } else if ("2".equals(mVersion.getDev_type())) {
                        isSelect = 3;
                    } else if ("3".equals(mVersion.getDev_type())) {
                        isSelect = 4;
                    } else {
                        Log.i(tag, "onResult not 0123");
                        isSelect = -1;
                    }
                } else {
                    Log.i(tag, "onResult:connRouter mVersion = null");
                    isSelect = -1;
                }
            }

            @Override
            public void onFinished() {
                isShowTip = false;
                if (isSelect == -1) {
                    Log.i(tag, "检测到新路由,可能恢复出厂设置，没有检测到任何型号公司的路由器");
                    searchRouter();
                } else {
                    Log.i(tag, "检测到公司的路由");
                    if (!isFrist) {
                        Log.i(tag, "已配置过路由器");
                        String password = PreferencesUtils.getString(mActivity, Consts.KEY_PASSWORD, "");
                        if (!"".equals(password)) {
                            Log.i(tag, "密码不为空");
                            isShowTip = true;
                            loginHttp(password);
                        } else {
                            Log.i(tag, "密码为空");
                            searchRouter();
                        }
                    } else {
                        Log.i(tag, "新路由");
                        searchRouter();
                    }
                }
            }
        });
    }

    private LinearLayout lySearch;
    private LinearLayout lyBtn;
    private Button btnRetry;
    private ImageView ivImage;
    private TextView tvSearchResou;

    private void setSearchView() {
        switch (isSelect) {
            case 1:
                ivImage.setImageResource(R.mipmap.first_dialog_airmesh);
                tvSearchResou.setText(getResString(R.string.first_search_1));
                btnRetry.setText(getResString(R.string.first_quick_setup));
                break;
            case 2:
                ivImage.setImageResource(R.mipmap.first_dialog_airbox);
                tvSearchResou.setText(getResString(R.string.first_search_2));
                btnRetry.setText(getResString(R.string.first_quick_setup));
                break;
            case 3:
                ivImage.setImageResource(R.mipmap.first_dialog_airoptimus);
                tvSearchResou.setText(getResString(R.string.first_search_3));
                btnRetry.setText(getResString(R.string.first_quick_setup));
                break;
            case 4:
                ivImage.setImageResource(R.mipmap.first_dialog_airframe);
                tvSearchResou.setText(getResString(R.string.first_search_4));
                btnRetry.setText(getResString(R.string.first_quick_setup));
                break;
            default:
                ivImage.setImageResource(R.mipmap.first_dialog_router);
                tvSearchResou.setText(getResString(R.string.search_device_no));
                btnRetry.setText(getResString(R.string.btn_retry));
                break;
        }
        lySearch.setVisibility(View.GONE);
        lyBtn.setVisibility(View.VISIBLE);
    }

    private OnHttpSelector onHttpSelector = new OnHttpSelector() {
        @Override
        public void onResult(String result) {
            IsNewRouter mVersion = JsonUtil.getObject(result, IsNewRouter.class);
            if (mVersion != null) {
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
            } else {
                isSelect = -1;
            }

        }

        @Override
        public void onFinished() {
            setSearchView();
        }
    };

    private void searchRouter() {
        WifiUtils.NetworkDetector(mActivity);
        if (mntDialog == null || !mntDialog.isShowing()) {
            mntDialog = new MntDialog(mActivity, R.style.Theme_dialog, R.layout.dialog_search_router, 290, 340);
            mntDialog.setCanceledOnTouchOutside(true);
            lySearch = (LinearLayout) mntDialog.findViewById(R.id.dialog_ly_search);//正在检测设备，请稍后...提示信息
            lyBtn = (LinearLayout) mntDialog.findViewById(R.id.dialog_ly_btn);//未检测设备，请检查连接设备是否正确的提示信息的布局
            btnRetry = (Button) mntDialog.findViewById(R.id.btn_conn_wifi);//点击重试按钮
            tvSearchResou = (TextView) mntDialog.findViewById(R.id.dialog_tv_btn);//未检测设备，请检查连接设备是否正确按钮
            ivImage = (ImageView) mntDialog.findViewById(R.id.imageView3);
            lySearch.setVisibility(View.VISIBLE);
            lyBtn.setVisibility(View.GONE);
            //去掉这一行就会一直“正在检测设备，请稍后”
            IsRouteHelp.getInstance().loginHttp(onHttpSelector);
//		    // (快速安装，点击重试)
            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isSelect == -1) {
                        lySearch.setVisibility(View.VISIBLE);
                        lyBtn.setVisibility(View.GONE);
                        WifiUtils.NetworkDetector(mActivity);
                        IsRouteHelp.getInstance().loginHttp(onHttpSelector);
                    } else {
                        mntDialog.dismiss();
                        String password = PreferencesUtils.getString(mActivity, Consts.KEY_PASSWORD, "");
                        if (!"".equals(password)) {
                            loginHttp(password);
                        } else {
                            logonRouter();
                        }
                    }
                }
            });
            mntDialog.show();
        }

    }

    private void logonRouter() {
        if (bindDialog == null || !bindDialog.isShowing()) {
            bindDialog = new MntDialog(mActivity, R.style.Theme_dialog, R.layout.dialog_edit, 235, 135);
            bindDialog.setCanceledOnTouchOutside(false);

            Button btnBind = (Button) bindDialog.findViewById(R.id.btn_bind_router);
            Button btnCancel = (Button) bindDialog.findViewById(R.id.btn_cancel);
            final ImageView ivPassword = (ImageView) bindDialog.findViewById(R.id.iv_password);
            final EditText etPassword = (EditText) bindDialog.findViewById(R.id.et_dialog_password);
            ivPassword.setSelected(false);
//		// 绑定
//		tv_message.setText(hint_message);
            btnBind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String passStr = etPassword.getText().toString();
                    if (passStr != null && !"".equals(passStr)) {
//                    ToastUtil.show(mActivity,"连接路由器", Toast.LENGTH_SHORT);
                        loginHttp(passStr);
                    } else {
                        ShowToast(getResString(R.string.input_password));
                    }
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bindDialog.dismiss();
                }
            });
            ivPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String passStr = etPassword.getText().toString();
                    if (ivPassword.isSelected()) {
                        ivPassword.setSelected(false);
                        etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    } else {
                        ivPassword.setSelected(true);
                        etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    }
                    etPassword.setSelection(passStr.length());
                }
            });
            bindDialog.show();
        }
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
                    Log.i(tag, "login success");//fun add test
                    Cache.isLogin = true;
//					PreferencesUtils.putString(mActivity, Consts.KEY_USERNAME,name);
                    PreferencesUtils.putString(mActivity, Consts.KEY_PASSWORD, password);
                    bindDialog.dismiss();
                    if (isFrist) {
                        Intent intent = new Intent(mActivity, SelectLoginActivity.class);
                        intent.putExtra("type", 1);
                        mActivity.startActivity(intent);
                    } else {
                        Log.i(tag, "not first login");//fun add test
                        mOnFragmentSelector.onFragment(Consts.FRAG_INDEX_MAIN_ROUTER);
                    }
                } else {
//					Log.i(tag,"not first login,pwd is error");
                    // fun add test 当管理员密码在平台上改变了，客户端提示用户密码已经重置
                    if (isShowTip) {
                        isShowTip = false;
                        ShowToast(getResString(R.string.wifi_password_error_not_equal_oldpwd));
                    } else {
                        ShowToast(getResString(R.string.wifi_password_error));
                    }

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
                if (!Cache.isLogin) {
                    Log.i(tag, "没有登录！");
                    logonRouter();
                } else {
                    Log.i(tag, "已经登录！");
                    //fun add 当切换到公司路由器时，当路由器的密码没有更改时，直接跳转到主界面
                    mOnFragmentSelector.onFragment(Consts.FRAG_INDEX_MAIN_ROUTER);
                }
            }
        });
    }

    @Override
    public void onRecycle() {
        // TODO Auto-generated method stub

    }


    public void Refresh() {
        IsRouteHelp.getInstance().loginHttp(new OnHttpSelector() {
            @Override
            public void onResult(String result) {

            }

            @Override
            public void onFinished() {
                initView();
            }
        });
    }

    public void setNOConnt() {
        searchRouter();
    }

}
