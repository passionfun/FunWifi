package com.xalianhui.wifimanage.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.bean.DeviceList;
import com.xalianhui.wifimanage.control.fragment.FirstControl;
import com.xalianhui.wifimanage.control.fragment.RouterControl;
import com.xalianhui.wifimanage.interfaces.OnFragmentSelector;
import com.xalianhui.wifimanage.ui.BaseFragment;

public class RouterFragment extends BaseFragment {

	private RouterControl control;

	@Override
	protected void getArguments(Bundle bundle,
			OnFragmentSelector fragmentSelector) {
		if (control == null) {
			control = new RouterControl();
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
		return R.layout.fragment_router;
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

    public void setDeviceList(DeviceList records) {
		if (control != null) {
			control.setDeviceList(records);
		}
    }
}
