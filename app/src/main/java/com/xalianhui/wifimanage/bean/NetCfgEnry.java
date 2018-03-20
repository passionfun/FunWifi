package com.xalianhui.wifimanage.bean;

import java.io.Serializable;

/**
 * Created by liubin on 2017/8/4.
 */

public class NetCfgEnry implements Serializable{
    private String wan_state;
    private String net_type;
    private String router_ip;
    private String router_gateway;

    public String getWan_state() {
        return wan_state;
    }

    public void setWan_state(String wan_state) {
        this.wan_state = wan_state;
    }

    public String getNet_type() {
        return net_type;
    }

    public void setNet_type(String net_type) {
        this.net_type = net_type;
    }

    public String getRouter_ip() {
        return router_ip;
    }

    public void setRouter_ip(String router_ip) {
        this.router_ip = router_ip;
    }

    public String getRouter_gateway() {
        return router_gateway;
    }

    public void setRouter_gateway(String router_gateway) {
        this.router_gateway = router_gateway;
    }
}
