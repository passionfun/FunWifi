package com.xalianhui.wifimanage.control;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.consts.Consts.TopPage;
import com.xalianhui.wifimanage.ui.BaseActivity;
import com.xalianhui.wifimanage.utils.ToastUtil;


public abstract class BaseActivityControl {
	public BaseActivity mActivity = null;
	public View mRootView = null;

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
		// SysApplication.getInstance().addActivity(mBaseActivity);
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

	public void ShowToast(String msg) {
		ToastUtil.showShort(mActivity, msg);
	}

	/**
	 * 得到String中的资源
	 * 
	 * @param str_id
	 * @return
	 */
	public String getResString(int str_id) {
		return mActivity.getResources().getString(str_id);
	}

	/**
	 * 得到Color中的资源
	 * 
	 * @param color_id
	 * @return
	 */
	public int getResColor(int color_id) {
		return mActivity.getResources().getColor(color_id);
	}
	private TextView tvTitle;
	private TextView tvNext;
    private ImageView imBack;
    private Consts.TopPage topPage;
    
    public void setTopView(TopPage topPage){
	    this.topPage = topPage;
	    tvTitle = (TextView) mActivity.findViewById(R.id.tv_title);
		imBack = (ImageView) mActivity.findViewById(R.id.im_back);
		if (topPage == TopPage.MAIN) {

		}else if (topPage == TopPage.PAGE){
			tvNext = (TextView) mActivity.findViewById(R.id.tv_next);
			tvNext.setVisibility(View.GONE);
	    	imBack.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mActivity.finish();
				}
			});
		}else if (topPage == TopPage.PAGE2){
			tvNext = (TextView) mActivity.findViewById(R.id.tv_next);
	    	imBack.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mActivity.finish();
				}
			});
		}
	    
    }
    public void setTitle(String title){
    	if (tvTitle != null && title != null) {
    		tvTitle.setText(title);
		}
    }

	public ImageView getLeftBtn() {
		return imBack;
	}
	public TextView getRightBtn() {
		return tvNext;
	}

    
}
