package com.xalianhui.wifimanage.bean;

import java.io.Serializable;

/**
 * Created by liubin on 2017/8/4.
 */

public class Device implements Serializable{
    private String mac;
    private String ip;
    private String sta_name;
    private String total_flow;
    private String down_rate;
    private String up_rate;
    private String sta_type;
    private String signal_level;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSta_name() {
        return sta_name;
    }

    public void setSta_name(String sta_name) {
        this.sta_name = sta_name;
    }

    public String getTotal_flow() {
        return total_flow;
    }

    public void setTotal_flow(String total_flow) {
        this.total_flow = total_flow;
    }

    public String getDown_rate() {
        return down_rate;
    }

    public void setDown_rate(String down_rate) {
        this.down_rate = down_rate;
    }

    public String getUp_rate() {
        return up_rate;
    }

    public void setUp_rate(String up_rate) {
        this.up_rate = up_rate;
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
