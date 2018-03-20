package com.xalianhui.wifimanage.bean;

import java.io.Serializable;

/**
 * Created by liubin on 2017/8/4.
 */

public class WifiCfgEnry implements Serializable{
    private String ssid_1;
    private String password_1;
    private String enable_wireless_1;
    private String ssid_2;
    private String password_2;
    private String enable_wireless_2;
    public void setSsid_1(String ssid_1) {
        this.ssid_1 = ssid_1;
    }
    public String getSsid_1() {
        return ssid_1;
    }

    public void setPassword_1(String password_1) {
        this.password_1 = password_1;
    }
    public String getPassword_1() {
        return password_1;
    }

    public void setEnable_wireless_1(String enable_wireless_1) {
        this.enable_wireless_1 = enable_wireless_1;
    }
    public String getEnable_wireless_1() {
        return enable_wireless_1;
    }

    public void setSsid_2(String ssid_2) {
        this.ssid_2 = ssid_2;
    }
    public String getSsid_2() {
        return ssid_2;
    }

    public void setPassword_2(String password_2) {
        this.password_2 = password_2;
    }
    public String getPassword_2() {
        return password_2;
    }

    public void setEnable_wireless_2(String enable_wireless_2) {
        this.enable_wireless_2 = enable_wireless_2;
    }
    public String getEnable_wireless_2() {
        return enable_wireless_2;
    }

}
