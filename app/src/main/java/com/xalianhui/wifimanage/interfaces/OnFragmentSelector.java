/****************************************************************************** 
 * @Title: OnFragmentSelector.java 
 * @author yangzhendong 
 * @Create Date : 2014�?�?�?
 * @JDK version used:      JDK1.6  
 * @Version : V1.0 
 * @Description: TODO 
 *  
 * History : TODO
 *
 * @Copyright (C), 2001-2012, Xi'an Software Development Co.,Ltd 
 * All rights reserved 
 ******************************************************************************/
package com.xalianhui.wifimanage.interfaces;

import android.os.Bundle;

/**
 * @description :Class description goes here.
 *
 * @author: yangzhendong
 * @Create Date : 2014�?�?�?
 * @version : V1.0
 * @description :
 */

public interface OnFragmentSelector {

	void onFragment(int fragmentIndex);

	void onPageNum(int fragmentIndex, int pageCount, int curPage, Bundle bundle);

}
