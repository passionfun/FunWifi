package com.xalianhui.wifimanage.bean;

import java.io.Serializable;

/**
 * Created by liubin on 2017/8/4.
 */

public class DeviceXQEnry implements Serializable{
    private String is_blocked;
    private String black_list_id;
    private String is_qos;
    private String qos_mode;
    private String qos_width;
    private String qos_list_id;
    private String is_timer;
    private String timer_list_id;
    private String start_time;
    private String end_time;
    private String[] cycle;
    public void setIs_blocked(String is_blocked) {
        this.is_blocked = is_blocked;
    }
    public String getIs_blocked() {
        return is_blocked;
    }

    public void setBlack_list_id(String black_list_id) {
        this.black_list_id = black_list_id;
    }
    public String getBlack_list_id() {
        return black_list_id;
    }

    public void setIs_qos(String is_qos) {
        this.is_qos = is_qos;
    }
    public String getIs_qos() {
        return is_qos;
    }

    public void setQos_mode(String qos_mode) {
        this.qos_mode = qos_mode;
    }
    public String getQos_mode() {
        return qos_mode;
    }

    public void setQos_width(String qos_width) {
        this.qos_width = qos_width;
    }
    public String getQos_width() {
        return qos_width;
    }

    public void setQos_list_id(String qos_list_id) {
        this.qos_list_id = qos_list_id;
    }
    public String getQos_list_id() {
        return qos_list_id;
    }

    public void setIs_timer(String is_timer) {
        this.is_timer = is_timer;
    }
    public String getIs_timer() {
        return is_timer;
    }

    public void setTimer_list_id(String timer_list_id) {
        this.timer_list_id = timer_list_id;
    }
    public String getTimer_list_id() {
        return timer_list_id;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
    public String getStart_time() {
        return start_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
    public String getEnd_time() {
        return end_time;
    }

    public void setCycle(String[] cycle) {
        this.cycle = cycle;
    }
    public String[] getCycle() {
        return cycle;
    }
}
