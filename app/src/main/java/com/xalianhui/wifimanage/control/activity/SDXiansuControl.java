package com.xalianhui.wifimanage.control.activity;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.control.BaseActivityControl;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


public class SDXiansuControl extends BaseActivityControl {

	@ViewInject(R.id.seekBar)
	private SeekBar sbUp;
	@ViewInject(R.id.seekBar2)
	private SeekBar sbDown;
	@ViewInject(R.id.tv_xiansu_up)
	private TextView tvUp;
	@ViewInject(R.id.tv_xiansu_down)
	private TextView tvDown;
//
//	private MntDialog mntDialog;
	@Override
	public void onInit(View rootView, Context context) {
		super.onInit(rootView, context);
		x.view().inject(this, rootView);
		initView();
	}


    @Override
	public void onRecycle() {

	}


	private void initView() {
		setTopView(Consts.TopPage.PAGE);
		setTitle(getResString(R.string.smart_manual_qos));
		sbUp.setMax(1024);
		sbUp.setProgress(500);
		sbUp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				tvUp.setText(i+"KB/s");
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
		sbDown.setMax(1024);
		sbDown.setProgress(500);
		sbDown.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				tvDown.setText(i+"KB/s");
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
	}







}
