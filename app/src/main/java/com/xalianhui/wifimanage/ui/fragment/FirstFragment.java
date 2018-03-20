package com.xalianhui.wifimanage.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.control.fragment.FirstControl;
import com.xalianhui.wifimanage.interfaces.OnFragmentSelector;
import com.xalianhui.wifimanage.ui.BaseFragment;

public class FirstFragment extends BaseFragment {

	private FirstControl control;

	@Override
	protected void getArguments(Bundle bundle,
			OnFragmentSelector fragmentSelector) {
		if (control == null) {
			control = new FirstControl();
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
		return R.layout.fragment_first;
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

    public void setNOConnt() {
		if (control != null) {
			control.setNOConnt();
		}
    }
}
