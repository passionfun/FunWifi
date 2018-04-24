package com.xalianhui.wifimanage.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.control.fragment.SystemControl;
import com.xalianhui.wifimanage.interfaces.OnFragmentSelector;
import com.xalianhui.wifimanage.ui.BaseFragment;

public class SystemFragment extends BaseFragment {
	private final String tag = "SystemFragment";
	private SystemControl control;

	@Override
	protected void getArguments(Bundle bundle,
			OnFragmentSelector fragmentSelector) {
		if (control == null) {
			control = new SystemControl();
		}
		control.setFragmentSelector(fragmentSelector);
	}

	@Override
	protected void onInit(View childRoot) {
		if (control != null) {
			control.onInit(childRoot, getActivity());
		}
	}

	@Override
	protected int addLayout() {
		return R.layout.fragment_system;
	}

	@Override
	protected void recycleView() {
		if (control != null) {
			control.recycleView();
		}
	}

    public void Refresh() {
		if (control != null) {
			control.Refresh();
		}
    }
    public void setRouterType(){
		if(control != null){
			control.setLedStyle();
		}
	}
}
