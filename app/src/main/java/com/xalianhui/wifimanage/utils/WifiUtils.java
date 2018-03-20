package com.xalianhui.wifimanage.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.consts.Icont;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import miky.android.common.util.PreferencesUtils;

import static android.text.format.Formatter.formatIpAddress;

/**
 * Created by ld on 2017/6/27.
 */

public class WifiUtils {
    //检测网络状态，Activity，无网络返回0，wifi返回1，其他返回2
    public static int NetworkDetector(Activity act) {
    if (act == null){
        return 0;
    }
        ConnectivityManager manager = (ConnectivityManager) act
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return 0;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        if (networkinfo != null && networkinfo.getType() == ConnectivityManager.TYPE_WIFI) {
            getIPAddress(act);
            return 1;
        }else
        if (networkinfo == null || !networkinfo.isAvailable()) {
            return 0;
        }
        return 2;
    }

    public static void getIPAddress(Context ctx){
        WifiManager wifi_service = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifi_service.getDhcpInfo();
        WifiInfo wifiinfo = wifi_service.getConnectionInfo();
        //System.out.println("Wifi info----->"+wifiinfo.getIpAddress());
        String ip = Formatter.formatIpAddress(dhcpInfo.gateway);
        System.out.println("DHCP info gateway----->"+ ip);
        String ipAdd = Icont.Url_TopIP;
        if (!ipAdd.contains(ip)){
            Icont.Url_TopIP = "http://"+ip+":80";
        }
//        return Formatter.formatIpAddress(dhcpInfo.gateway);
    }

}
