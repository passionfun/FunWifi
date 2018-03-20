package com.xalianhui.wifimanage.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.control.fragment.SystemControl;
import com.xalianhui.wifimanage.control.fragment.ToolControl;
import com.xalianhui.wifimanage.interfaces.OnFragmentSelector;
import com.xalianhui.wifimanage.ui.BaseFragment;

public class ToolFragment extends BaseFragment {

	private ToolControl control;

	@Override
	protected void getArguments(Bundle bundle,
			OnFragmentSelector fragmentSelector) {
		if (control == null) {
			control = new ToolControl();
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
		return R.layout.fragment_tool;
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
}
