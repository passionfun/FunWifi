package com.xalianhui.wifimanage.bean;

/**
 * Created by liubin on 2017/9/21.
 */

public class NewVersion {
    private String is_has_new;
    private String version;
    private String down_url;
    private String md5sum;

    public String getIs_has_new() {
        return is_has_new;
    }

    public void setIs_has_new(String is_has_new) {
        this.is_has_new = is_has_new;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDown_url() {
        return down_url;
    }

    public void setDown_url(String down_url) {
        this.down_url = down_url;
    }

    public String getMd5sum() {
        return md5sum;
    }

    public void setMd5sum(String md5sum) {
        this.md5sum = md5sum;
    }
}
