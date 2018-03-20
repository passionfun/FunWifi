package com.xalianhui.wifimanage.control;

import android.content.Context;
import android.view.View;

import com.xalianhui.wifimanage.interfaces.OnFragmentSelector;
import com.xalianhui.wifimanage.ui.BaseActivity;
import com.xalianhui.wifimanage.utils.ToastUtil;


public abstract class BaseFragmentControl {
    public BaseActivity mActivity = null;
    public View mRootView = null;
    public OnFragmentSelector mOnFragmentSelector= null;
    public abstract void onRecycle();

    /*
     * <p>Title: onInit</p> <p>Description: </p>
     * 
     * @param rootView
     * 
     * @param context
     * 
     * @see
     * com.tl.android.codescanner.interfaces.BaseControlInterface#onInit(android
     * .view.View, android.content.Context)
     */
    public void onInit(View rootView, Context context) {
        mActivity = (BaseActivity) context;
        //SysApplication.getInstance().addActivity(mBaseActivity); 
        mRootView = rootView;
    }
    /*
     * <p>Title: recycleView</p> <p>Description: </p>
     * 
     * @see
     * com.tl.android.codescanner.interfaces.BaseControlInterface#onDestory()
     */
    public final void recycleView() {
        onRecycle();
        System.gc();
    }
    /*
     * <p>Title: setFragmentSelector</p> <p>Description: </p>
     * 
     * @param fragmentSelector
     * 
     * @see
     * com.android.wasulauncher.interfaces.BaseControlInterface#setFragmentSelector
     * (com.android.wasulauncher.interfaces.OnFragmentSelector)
     */
    public final void setFragmentSelector(OnFragmentSelector fragmentSelector) {
        mOnFragmentSelector = fragmentSelector;
    }
    
    public void ShowToast(String msg){
        ToastUtil.showShort(mActivity, msg);
    }
    /**
     * 得到String中的资源
     * @param str_id
     * @return
     */
    public String getResString(int str_id){
        return mActivity.getResources().getString(str_id);
    }
    
    /**
     * 得到Color中的资源
     * @param color_id
     * @return
     */
    public int getResColor(int color_id){
        return mActivity.getResources().getColor(color_id);
    }
       
}
