package com.xalianhui.wifimanage.bean;

import java.util.List;

/**
 * Created by liubin on 2017/8/4.
 */

public class DeviceList {
    private String uplink;
    private String downlink;
    private List<Device> online_sta;
    private List<Smart> smart_dev;

    public String getUplink() {
        return uplink;
    }

    public void setUplink(String uplink) {
        this.uplink = uplink;
    }

    public String getDownlink() {
        return downlink;
    }

    public void setDownlink(String downlink) {
        this.downlink = downlink;
    }

    public List<Device> getOnline_sta() {
        return online_sta;
    }

    public void setOnline_sta(List<Device> online_sta) {
        this.online_sta = online_sta;
    }

    public List<Smart> getSmart_dev() {
        return smart_dev;
    }

    public void setSmart_dev(List<Smart> smart_dev) {
        this.smart_dev = smart_dev;
    }
}
