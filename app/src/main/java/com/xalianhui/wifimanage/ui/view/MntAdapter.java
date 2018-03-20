package com.xalianhui.wifimanage.ui.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class MntAdapter<T> extends BaseAdapter {

    private Context context;// 上下文对象
    private List<T> list;// 数据集合
    private int resource;// 行Item布局资源
    private int selectedItem = -1;// 当前选中项位置
    private boolean isAvailable;// 是否可用
    private boolean isEditable = false;// 是否可编辑

    public MntAdapter(Context context, int resource, List<T> list, boolean isAvailable) {
        setData(list);
        this.context = context;
        this.isAvailable = isAvailable;
        this.resource = resource;
    }

    public void updata(List<T> list) {
        setData(list);
        notifyDataSetChanged();
    }

    private void setData(List<T> list) {
        if (list == null) {
            this.list = new ArrayList<T>();
        } else {
            this.list = list;
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MntHolder simHolder = MntHolder.getSimHolder(context, convertView, resource, position);
        boolean isSelected = false;
        if (selectedItem == position) {
            isSelected = true;
        }
        setItemView(simHolder, getItem(position), isSelected, position, isSelected, isEditable);
        return simHolder.getConvertView();
    }

    /**
     * 设置ItemView的相关属性及数据对外开放接口
     *
     * @param simHolder
     * @param t           数据
     * @param isSelected  当前是否选中
     * @param position    当前行号
     * @param isAvailable 是否可用
     */
    public abstract void setItemView(MntHolder simHolder, T t, boolean isSelected, int position, boolean isAvailable, boolean isEditable);

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
        notifyDataSetChanged();
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }
}
