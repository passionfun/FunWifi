package com.xalianhui.wifimanage.bean;

import java.io.Serializable;

/**
 * Created by liubin on 2017/8/4.
 */

public class WifiHightCfgEnry implements Serializable{
    private String bandwidth1;
    private String power1_percent;
    private String wmm1;
    private String channel_a;
    private String channel_a_range;
    private String bandwidth2;
    private String power2_percent;
    private String wmm2;
    private String channel_b;
    private String channel_b_range;
    private String region;
    private String range;
    public void setBandwidth1(String bandwidth1) {
        this.bandwidth1 = bandwidth1;
    }
    public String getBandwidth1() {
        return bandwidth1;
    }

    public void setPower1_percent(String power1_percent) {
        this.power1_percent = power1_percent;
    }
    public String getPower1_percent() {
        return power1_percent;
    }

    public void setWmm1(String wmm1) {
        this.wmm1 = wmm1;
    }
    public String getWmm1() {
        return wmm1;
    }

    public void setChannel_a(String channel_a) {
        this.channel_a = channel_a;
    }
    public String getChannel_a() {
        return channel_a;
    }

    public void setChannel_a_range(String channel_a_range) {
        this.channel_a_range = channel_a_range;
    }
    public String getChannel_a_range() {
        return channel_a_range;
    }

    public void setBandwidth2(String bandwidth2) {
        this.bandwidth2 = bandwidth2;
    }
    public String getBandwidth2() {
        return bandwidth2;
    }

    public void setPower2_percent(String power2_percent) {
        this.power2_percent = power2_percent;
    }
    public String getPower2_percent() {
        return power2_percent;
    }

    public void setWmm2(String wmm2) {
        this.wmm2 = wmm2;
    }
    public String getWmm2() {
        return wmm2;
    }

    public void setChannel_b(String channel_b) {
        this.channel_b = channel_b;
    }
    public String getChannel_b() {
        return channel_b;
    }

    public void setChannel_b_range(String channel_b_range) {
        this.channel_b_range = channel_b_range;
    }
    public String getChannel_b_range() {
        return channel_b_range;
    }

    public void setRegion(String region) {
        this.region = region;
    }
    public String getRegion() {
        return region;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
}
