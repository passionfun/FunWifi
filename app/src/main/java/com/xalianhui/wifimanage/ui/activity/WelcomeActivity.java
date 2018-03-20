package com.xalianhui.wifimanage.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.xalianhui.wifimanage.R;
import com.xalianhui.wifimanage.consts.Consts;
import com.xalianhui.wifimanage.db.DBDao;
import com.xalianhui.wifimanage.db.enty.SlideItem;
import com.xalianhui.wifimanage.function.IsRouteHelp;
import com.xalianhui.wifimanage.function.LoginHelp;
import com.xalianhui.wifimanage.utils.ImageProcessing;
import com.xalianhui.wifimanage.utils.WifiUtils;

import org.xutils.ex.DbException;

import java.io.File;
import java.util.List;

import miky.android.common.util.PreferencesUtils;


public class WelcomeActivity extends Activity {
	Context context;
	private static final long SPLASH_DISPLAY_LENGHT = 2000;
	Intent intent;
	Bitmap bmpWelcome;
	private DBDao dbDao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//�����ޱ���
		setContentView(R.layout.activity_welcome);
		WifiUtils.NetworkDetector(this);
		String password = PreferencesUtils.getString(this, Consts.KEY_PASSWORD,"");
		boolean  isFirst = PreferencesUtils.getBoolean(this, Consts.KEY_ISFIRST,true);
		context = getApplicationContext();
		if (isFirst){
			deleteFilesByDirectory(context.getCacheDir());
			PreferencesUtils.putBoolean(this, Consts.KEY_ISFIRST,false);
		}
		LoginHelp.getInstance().loginHttp(password);
		IsRouteHelp.getInstance().loginHttp(null);

        ImageView welImg = (ImageView) findViewById(R.id.imgViewWelcome);
        bmpWelcome = ImageProcessing.readBitMap(context, R.mipmap.welcome);
		dbDao = new DBDao(this);
		setMenu();
        welImg.setImageBitmap(bmpWelcome);

  		new Handler().postDelayed(new Runnable()  
		{  @Override  
		      public void run()  
		       {  	
					intent=new Intent(WelcomeActivity.this,MainActivity.class);
		            startActivity(intent); //memory leakage - cause lots of GC  
		            WelcomeActivity.this.finish();  
				}				
		    }, SPLASH_DISPLAY_LENGHT);  
	}

	private void setMenu() {
		 List<SlideItem> menuList = dbDao.getAllMenu();
		int index = -1;
		if (menuList!=null&&menuList.size()>0){
			for (int i = 0; i < menuList.size(); i++) {
				switch (menuList.get(i).getIndex()){
					case 1:
						menuList.get(i).setText(R.string.menu_rocket_boost);
						menuList.get(i).setResId(R.mipmap.menu_one);
						break;
					case 2:
						menuList.get(i).setText(R.string.menu_healthy_mode);
						menuList.get(i).setResId(R.mipmap.menu_jiank);
						break;
					case 3:
						menuList.get(i).setText(R.string.menu_parental_control);
						menuList.get(i).setResId(R.mipmap.menu_jiaz);
						break;
					case 4:
					index = i;
						menuList.get(i).setText(R.string.menu_green_wifi);
						menuList.get(i).setResId(R.mipmap.menu_green);
						break;
					case 5:
						menuList.get(i).setText(R.string.menu_blacklist);
						menuList.get(i).setResId(R.mipmap.menu_black);
						break;
					case 6:
						menuList.get(i).setText(R.string.menu_smart_qos);
						menuList.get(i).setResId(R.mipmap.menu_xiansu);
						break;
					case 7:
						menuList.get(i).setText(R.string.menu_wifi);
						menuList.get(i).setResId(R.mipmap.menu_wifi);
						break;
					case 8:
						menuList.get(i).setText(R.string.menu_network);
						menuList.get(i).setResId(R.mipmap.menu);
						break;
					case 9:
						menuList.get(i).setText(R.string.menu_management);
						menuList.get(i).setResId(R.mipmap.menu_admin);
						break;
					case 10:
						menuList.get(i).setText(R.string.menu_upgrade_firmware);
						menuList.get(i).setResId(R.mipmap.menu_version);
						break;
					case 11:
						menuList.get(i).setText(R.string.menu_reset);
						menuList.get(i).setResId(R.mipmap.menu_restore);
						break;
				}
			}
			if (index != -1 && index >= 0 && index < menuList.size()){
				menuList.remove(index);
			}
			try {
				dbDao.getDBUtils().saveOrUpdate(menuList);
			} catch (DbException e) {
				e.printStackTrace();
			}
		}
	}

	//@Override
    public boolean onTouch(View v,MotionEvent event)
    {
    	if(event.getAction() ==MotionEvent.ACTION_MOVE)
        {
    		int x =(int)event.getX();
            int y =(int)event.getY();
            Log.e("Coordinates= ", "(" + x + ", " + y + ")");
         }
       return false;
    }

	
	@Override
	public void onDestroy() {
		if(!bmpWelcome.isRecycled() && bmpWelcome != null ){
			   bmpWelcome.recycle();   // ����ͼƬ��ռ���ڴ�
			   System.gc();    // ����ϵͳ��ʱ����
		}
		super.onDestroy();
	}
	private  void deleteFilesByDirectory(File directory) {
		if (directory != null && directory.exists() && directory.isDirectory()) {
			for (File item : directory.listFiles()) {
				item.delete();
			}
		}
	}
}
