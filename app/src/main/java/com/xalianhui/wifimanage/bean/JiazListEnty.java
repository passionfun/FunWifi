package com.xalianhui.wifimanage.bean;

/**
 * Created by liubin on 2017/8/4.
 */

public class JiazListEnty {
    private String index;
    private String mac;
    private String sta_name;
    private String sta_type;
    private String start_time;
    private String end_time;
    private String[] cycle;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSta_name() {
        return sta_name;
    }

    public void setSta_name(String sta_name) {
        this.sta_name = sta_name;
    }

    public String getSta_type() {
        return sta_type;
    }

    public void setSta_type(String sta_type) {
        this.sta_type = sta_type;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String[] getCycle() {
        return cycle;
    }

    public void setCycle(String[] cycle) {
        this.cycle = cycle;
    }
}
