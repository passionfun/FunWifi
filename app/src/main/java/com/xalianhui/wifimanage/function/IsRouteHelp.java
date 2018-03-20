package com.xalianhui.wifimanage.function;

import android.util.Log;

import com.xalianhui.wifimanage.consts.Cache;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.interfaces.OnHttpSelector;

import org.xutils.common.Callback;
import org.xutils.x;

import static android.R.attr.password;

/**
 * Created by liubin on 2017/8/11.
 */

public class IsRouteHelp {
    private static final IsRouteHelp ourInstance = new IsRouteHelp();
    private OnHttpSelector onHttpSelector;

    public static IsRouteHelp getInstance() {
        return ourInstance;
    }

    private IsRouteHelp() {
    }
    public void loginHttp(final OnHttpSelector onHttpSelector){
        MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.search_Router);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //解析result
                Log.i("IsRouteHelp——result",result);
                if (result.startsWith("{")){
                    Cache.isConnRouter = true;
                    Cache.isLoading = true;
                    if (onHttpSelector != null){
                        onHttpSelector.onResult(result);
                    }
                }else {
                    Cache.isConnRouter = false;
                    Cache.isLoading = false;
                    if (onHttpSelector != null){
                        onHttpSelector.onResult("");
                    }
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("IsRouteHelp——result","error");
                Cache.isConnRouter = false;
                if (onHttpSelector != null){
                    onHttpSelector.onResult("-1");
                }
            }
            @Override
            public void onCancelled(CancelledException cex) {

            }
            @Override
            public void onFinished() {
                Log.i("IsRouteHelp——result","onFinish");
                if (onHttpSelector != null){
                    onHttpSelector.onFinished();
                }
            }
        });
    }
}
