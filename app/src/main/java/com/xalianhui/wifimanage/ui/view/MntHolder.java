package com.xalianhui.wifimanage.ui.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;


public class MntHolder {

    private SparseArray<View> views;
    private int position;
    private View convertView;

    public MntHolder(Context context, int resource, int position) {
        this.position = position;
        this.views = new SparseArray<View>();
        this.convertView = LayoutInflater.from(context).inflate(resource, null);
        this.convertView.setTag(this);
    }

    /**
     * 获取或者创建SimHolder对象
     *
     * @param context
     * @param convertView
     * @param resource
     * @param position
     * @return
     */
    public static MntHolder getSimHolder(Context context, View convertView, int resource, int position) {
        MntHolder simHolder = null;
        if (convertView == null) {
            simHolder = new MntHolder(context, resource, position);
        } else {
            simHolder = (MntHolder) convertView.getTag();
        }
        return simHolder;
    }

    public View getConvertView() {
        return convertView;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 获取TextView控件
     *
     * @param viewId
     * @return
     */
    public TextView getTextView(int viewId) {
        return getView(viewId);
    }

    /**
     * 获取EditText控件
     *
     * @param viewId
     * @return
     */
    public EditText getEditText(int viewId) {
        return getView(viewId);
    }
    /**
     * 获取EditText控件
     *
     * @param viewId
     * @return
     */
    public ImageView getImageView(int viewId) {
        return getView(viewId);
    }

    /**
     * 获取Button控件
     *
     * @param viewId
     * @return
     */
    public Button getButton(int viewId) {
        return getView(viewId);
    }

    /**
     * 获取CheckBox控件
     *
     * @param viewId
     * @return
     */
    public CheckBox getCheckBox(int viewId) {
        return getView(viewId);
    }

    /**
     * 获取ToggleButton控件
     *
     * @param viewId
     * @return
     */
    public ToggleButton getToggleButton(int viewId) {
        return getView(viewId);
    }

    /**
     * 设置TextView显示内容
     *
     * @param viewId
     * @param text
     */
    public void setTextView(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
    }
    /**
     * 设置TextView显示内容
     *
     * @param viewId
     * @param text
     */
    public void setTextView(int viewId, int text) {
        TextView textView = getView(viewId);
        textView.setText(text);
    }

    /**
     * 设置TextView显示内容
     *
     * @param viewId
     * @param spannableString
     */
    public void setTextView(int viewId, SpannableString spannableString) {
        TextView textView = getView(viewId);
        textView.setText(spannableString);
    }

    /**
     * 设置Button显示内容
     *
     * @param viewId
     * @param text
     */
    public void setButton(int viewId, String text) {
        Button button = getView(viewId);
        button.setText(text);
    }

    /**
     * 设置CheckBox选中状态
     *
     * @param viewId
     * @param isChecked
     */
    public void setCheckBox(int viewId, boolean isChecked) {
        CheckBox checkBox = getView(viewId);
        checkBox.setChecked(isChecked);
    }

    public void setImageView(int viewId, int resId){
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
    }
    public void setImageViewStr(int viewId, String str){
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(BitmapFactory.decodeFile(str));
    }

    public void setToggleButton(int viewId, String status){
        ToggleButton toggleButton = getView(viewId);
        if(status == null){
            status = "N";
        }else{
            if(status.equals("Y")){
                toggleButton.setChecked(true);
            }else{
                toggleButton.setChecked(false);
            }
        }
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
