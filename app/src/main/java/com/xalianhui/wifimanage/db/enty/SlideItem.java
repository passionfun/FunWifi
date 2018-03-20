package com.xalianhui.wifimanage.db.enty;

/**
 * Created by Administrator on 2016/6/15 0015.
 */

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * onCreated = "sql"：当第一次创建表需要插入数据时候在此写sql语句
 */
@Table(name = "slide_item",onCreated = "")
public class SlideItem {
    /**
     * name = "id"：数据库表中的一个字段
     * isId = true：是否是主键
     * autoGen = true：是否自动增长
     * property = "NOT NULL"：添加约束
     */
    @Column(name = "id",isId = true,autoGen = true,property = "NOT NULL")
    private int id;
    @Column(name = "resId")
    private int resId;
    @Column(name = "text")
    private int text;
    @Column(name = "page")
    private String page;
    @Column(name = "isShow")
    private int isShow;//0不显示；1显示
    @Column(name = "index")
    private int index;//0不显示；1显示

    public SlideItem() {
    }

    public SlideItem( int resId, int text, String page, int isShow,int index) {
        this.resId = resId;
        this.text = text;
        this.page = page;
        this.isShow = isShow;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getText() {
        return text;
    }

    public void setText(int text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }
}
