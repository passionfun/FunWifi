package com.xalianhui.wifimanage.utils;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageProcessing {
	//change resource to bitmap
	public static Bitmap readBitMap(Context context, int resId){  
		   BitmapFactory.Options opt = new BitmapFactory.Options();  
		   opt.inPreferredConfig = Bitmap.Config.ARGB_4444;
		   opt.inSampleSize = 2;
		   opt.inPurgeable = true;  
		   opt.inInputShareable = true;  
		   // ��ȡ��ԴͼƬ  
		   InputStream is = context.getResources().openRawResource(resId);  
		   return BitmapFactory.decodeStream(is,null,opt);  
		}

}
