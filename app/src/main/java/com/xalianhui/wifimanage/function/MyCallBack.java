package com.xalianhui.wifimanage.function;

import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.xalianhui.wifimanage.consts.Cache;
import com.xalianhui.wifimanage.consts.Constants;
import com.xalianhui.wifimanage.consts.Consts;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;

import miky.android.common.util.ContextUtil;


/**
 * Created by liubin on 2017/8/11.
 */

public abstract class MyCallBack<ResultType> implements Callback.CommonCallback<ResultType> {
    @Override
    public void onSuccess(ResultType result) {
        //可以根据公司的需求进行统一的请求成功的逻辑处理
        if (result != null) {
            if (result.toString().startsWith("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"") || "".equals(result.toString())) {
                Log.i("MyCallBack", "document title exp");
//                sendBroadcast();
            } else {
                Log.i("MyCallBack", "success request:" + result.toString());
                Cache.isConnRouter = true;
                Cache.isLoading = true;
                onMSuccess(result);
            }
        } else {
            Log.i("MyCallBack", "result = null:" + result.toString());
            sendBroadcast();
        }
    }

    public abstract void onMSuccess(ResultType result);

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        //可以根据公司的需求进行统一的请求网络失败的逻辑处理
//        if (ex.getCause().)
//       if(ex instanceof HttpException) {
//           int code = ((HttpException)ex).getCode();
//           if(code == 404){
        //手动断开wifi  当连接上非公司生产的路由器或者断开无线连接
        Log.i("MyCallBack", "result onError");
        sendBroadcast();
//           }
//       }
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }
    @Override
    public void onFinished() {

    }

    private void sendBroadcast() {
        Cache.isConnRouter = false;
        Cache.isLoading = false;
        Cache.isLogin = false;
        Intent intent = new Intent();  //用于传输数据的intent
        intent.setAction(Consts.ACTION_REQUEXT_ERROR);
        intent.putExtra("isRoute", false);//为这个intent加上action，用于广播过滤器过滤
        ContextUtil.getInstance().getApplicationContext().sendBroadcast(intent); //使用sendBroadcast发送广播
    }

    private void sendRouterBroadcast() {
        Cache.isConnRouter = true;
        Cache.isLoading = false;
        Intent intent = new Intent();  //用于传输数据的intent
        intent.setAction(Consts.ACTION_REQUEST_EXCEPTION);
        intent.putExtra("isRoute", true);//为这个intent加上action，用于广播过滤器过滤
        ContextUtil.getInstance().getApplicationContext().sendBroadcast(intent); //使用sendBroadcast发送广播
    }
}
