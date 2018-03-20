package com.xalianhui.wifimanage.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class IsRouteService extends Service {

	public SimpleBinder sBinder;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i("myserver", "service onCreate"); 
//  	    initLocation();
  
	}
	public class SimpleBinder extends Binder{
        /**
         * 获取 Service 实例
         * @return
         */
//        public LocationUploadService getService(){
//            return LocationUploadService.this;
//        }

    }
	@Override
	public void onStart(Intent intent, int startId) {
	    // TODO Auto-generated method stub
	    super.onStart(intent, startId);
	    Log.i("myserver", "service onStart");
	}
		@Override
		public IBinder onBind(Intent intent) {
			// TODO Auto-generated method stub
			   return sBinder;
		}
		
		 @Override  
		    public void onDestroy() {  
		        Log.i("myserver", "service destroy");  
		        super.onDestroy();  
		    }  
		
}
