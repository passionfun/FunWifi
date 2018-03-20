package com.xalianhui.wifimanage.control.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.adapter.MainGradViewAdapter;
import com.xalianhui.wifimanage.consts.Cache;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.control.BaseActivityControl;
import com.xalianhui.wifimanage.db.DBDao;
import com.xalianhui.wifimanage.db.enty.SlideItem;
import com.xalianhui.wifimanage.ui.fragment.FirstFragment;
import com.xalianhui.wifimanage.ui.fragment.RouterFragment;
import com.xalianhui.wifimanage.ui.fragment.SystemFragment;
import com.xalianhui.wifimanage.ui.fragment.ToolFragment;
import com.xalianhui.wifimanage.ui.view.BasePopupHelper;
import com.xalianhui.wifimanage.ui.view.MntAdapter;
import com.xalianhui.wifimanage.ui.view.MntHolder;
import com.xalianhui.wifimanage.utils.WifiUtils;

import org.xutils.ex.DbException;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;


public class MenuSelectControl extends BaseActivityControl {



	@ViewInject(R.id.menu_gridview)
	private GridView mGridView;

	private MainGradViewAdapter adapter;

	private List<SlideItem> menuList = new ArrayList<>();
	private DBDao dbDao;

//    @ViewInject(R.id.blow_menu_router)
//    private LinearLayout lyRouter;

    public float density;
	private int wmHeight;

	@Override
	public void onInit(View rootView, Context context) {
		super.onInit(rootView, context);
		x.view().inject(this, rootView);
		dbDao = new DBDao(mActivity);
        this.density = mActivity.getResources().getDisplayMetrics().density;
		initView();
	}


    @Override
	public void onRecycle() {

	}

	private void initView() {
		setTopView(Consts.TopPage.PAGE);
		setTitle(getResString(R.string.add_a_feature));
		initData();
		WindowManager windowManager = mActivity.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int screenWidth = display.getWidth();
		float Cell_Width = (screenWidth - 3.00f * density)/3 - 50.0f * density;
//		mGridView.setVerticalSpacing(Cell_Width/2);
//		mGridView.setHorizontalSpacing(Cell_Width);
		adapter = new MainGradViewAdapter(mActivity, menuList, (int) Cell_Width);
		mGridView.setAdapter(adapter);
		mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				if (menuList.get(position).getIsShow() == 0){
					menuList.get(position).setIsShow(1);
				}else {
					menuList.get(position).setIsShow(0);
				}
				try {
					dbDao.getDBUtils().update(menuList.get(position));
				} catch (DbException e) {
					e.printStackTrace();
				}
				adapter.notifyDataSetChanged();
			}
		});
	}

	private void initData() {
		menuList = dbDao.getAllMenu();
	}









}
