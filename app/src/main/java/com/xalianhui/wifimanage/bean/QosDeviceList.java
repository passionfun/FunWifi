package com.xalianhui.wifimanage.bean;

public class QosDeviceList {

    private String mac;
    private String ip1;
    private String type;
    private String index;
    private String width;
    private String sta_name;
    private String sta_type;

    public void setMac(String mac) {
         this.mac = mac;
     }
     public String getMac() {
         return mac;
     }

    public void setIp1(String ip1) {
         this.ip1 = ip1;
     }
     public String getIp1() {
         return ip1;
     }

    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

    public void setIndex(String index) {
         this.index = index;
     }
     public String getIndex() {
         return index;
     }

    public void setWidth(String width) {
         this.width = width;
     }
     public String getWidth() {
         return width;
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
}