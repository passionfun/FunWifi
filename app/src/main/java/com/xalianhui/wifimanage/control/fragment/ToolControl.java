package com.xalianhui.wifimanage.control.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.control.BaseFragmentControl;
import com.xalianhui.wifimanage.ui.activity.BlackActivity;
import com.xalianhui.wifimanage.ui.activity.BroadbandActivity;
import com.xalianhui.wifimanage.ui.activity.GreenWIFIActivity;
import com.xalianhui.wifimanage.ui.activity.JiazhangActivity;
import com.xalianhui.wifimanage.ui.activity.OneActivity;
import com.xalianhui.wifimanage.ui.activity.XiansuActivity;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


public class ToolControl extends BaseFragmentControl {


	@ViewInject(R.id.tv_title)
	private TextView tvTitle;



	@Override
	public void onInit(View rootView, Context context) {
		super.onInit(rootView, context);
		x.view().inject(this, rootView);
      initView();
	}
	
	private void initView() {

		tvTitle.setText(getResString(R.string.blow_tool));
    }
	@Event(value = {R.id.ly_tool_one,R.id.ly_tool_jiaz,R.id.ly_tool_green,R.id.ly_tool_black,R.id.ly_tool_xians})
	private void select(View v) {
		Intent intent;
		switch (v.getId()) {
			case R.id.ly_tool_one:
				 intent = new Intent(mActivity, OneActivity.class);
				mActivity.startActivity(intent);
				break;
			case R.id.ly_tool_jiaz:
				 intent = new Intent(mActivity, JiazhangActivity.class);
				mActivity.startActivity(intent);
				break;
			case R.id.ly_tool_green:
				intent = new Intent(mActivity, GreenWIFIActivity.class);
				mActivity.startActivity(intent);

				break;
			case R.id.ly_tool_black:
				intent = new Intent(mActivity, BlackActivity.class);
				mActivity.startActivity(intent);

				break;
			case R.id.ly_tool_xians:
				intent = new Intent(mActivity, XiansuActivity.class);
				mActivity.startActivity(intent);

				break;

			default:
				break;
		}
	}

	@Override
	public void onRecycle() {
		// TODO Auto-generated method stub

	}


	public void Refresh() {
		initView();
	}
}
