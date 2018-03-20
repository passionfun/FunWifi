package com.xalianhui.wifimanage.function;

import android.util.Log;

import org.xutils.http.RequestParams;
import org.xutils.http.app.RedirectHandler;
import org.xutils.http.request.UriRequest;

/**
 * Created by liubin on 2017/8/11.
 */

public class MyRequestParams extends RequestParams {
    public MyRequestParams(String uri) {
        super(uri);
        this.setConnectTimeout(2000);
//        this.setRedirectHandler(new RedirectHandler() {
//            @Override
//            public RequestParams getRedirectParams(UriRequest request) throws Throwable {
//               Log.i("MyRequestParams", request.toString());
//                return null;
//            }
//        });
    }
}
