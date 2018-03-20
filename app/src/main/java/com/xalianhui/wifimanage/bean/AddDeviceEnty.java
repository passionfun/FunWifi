package com.xalianhui.wifimanage.bean;

import java.io.Serializable;

/**
 * Created by liubin on 2017/8/4.
 */

public class AddDeviceEnty implements Serializable{
    private String mac;
    private String ip;
    private String sta_name;
    private String down_rate;
    private String sta_type;
    private String signal_level;
    public void setMac(String mac) {
        this.mac = mac;
    }
    public String getMac() {
        return mac;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getIp() {
        return ip;
    }

    public void setSta_name(String sta_name) {
        this.sta_name = sta_name;
    }
    public String getSta_name() {
        return sta_name;
    }

    public String getDown_rate() {
        return down_rate;
    }

    public void setDown_rate(String down_rate) {
        this.down_rate = down_rate;
    }

    public String getSta_type() {
        return sta_type;
    }

    public void setSta_type(String sta_type) {
        this.sta_type = sta_type;
    }

    public String getSignal_level() {
        return signal_level;
    }

    public void setSignal_level(String signal_level) {
        this.signal_level = signal_level;
    }
}
