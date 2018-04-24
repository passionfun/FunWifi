package com.xalianhui.wifimanage.function;

import android.util.Log;

import com.xalianhui.wifimanage.bean.IsNewRouter;
import com.xalianhui.wifimanage.consts.Cache;
import com.xalianhui.wifimanage.consts.Icont;
import com.xalianhui.wifimanage.interfaces.OnHttpSelector;
import com.xalianhui.wifimanage.utils.JsonUtil;

import org.xutils.common.Callback;
import org.xutils.x;

import static android.R.attr.password;

/**
 * Created by liubin on 2017/8/11.
 */

public class IsRouteHelp {
    private static final IsRouteHelp ourInstance = new IsRouteHelp();
    private OnHttpSelector onHttpSelector;
    IsNewRouter mVersion = null;
    public static IsRouteHelp getInstance() {
        return ourInstance;
    }

    private IsRouteHelp() {
    }

    /**
     * 获取路由器的型号（0、1/2/3）
     * @return
     */
    public String getRouterType(){
        return mVersion.getDev_type();
    }
    public void loginHttp(final OnHttpSelector onHttpSelector){
        MyRequestParams params = new MyRequestParams(Icont.Url_TopIP+ Icont.search_Router);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //解析result
                Log.i("IsRouteHelp——result",result);
                if (result.startsWith("{")){
                    mVersion = JsonUtil.getObject(result,IsNewRouter.class);
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
                Log.i("IsRouteHelp——result","onError");
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
                Log.i("IsRouteHelp——result","onFinished");
                if (onHttpSelector != null){
                    onHttpSelector.onFinished();
                }
            }
        });
    }
}
