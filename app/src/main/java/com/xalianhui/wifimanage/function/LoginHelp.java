package com.xalianhui.wifimanage.function;

import android.util.Log;

import com.xalianhui.wifimanage.consts.Cache;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.utils.TextUtil;

import org.xutils.common.Callback;
import org.xutils.x;

/**
 * Created by liubin on 2017/8/11.
 */

public class LoginHelp {
    private static final LoginHelp ourInstance = new LoginHelp();

    public static LoginHelp getInstance() {
        return ourInstance;
    }

    private LoginHelp() {
    }
    public void loginHttp(final String password ){
        MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.login_Url);
        params.addBodyParameter("password",password);
        params.addBodyParameter("lang", TextUtil.getLone());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //解析result
                Log.i("result",result);
                Cache.isLogin = false;
                if("1".equals(result)) {
                    Cache.isLogin = true;
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Cache.isLogin = false;
            }
            @Override
            public void onCancelled(CancelledException cex) {

            }
            @Override
            public void onFinished() {

            }
        });
    }
}
