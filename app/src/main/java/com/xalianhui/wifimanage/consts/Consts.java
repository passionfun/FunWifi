package com.xalianhui.wifimanage.consts;

public class Consts {
	
	

	public static final String SP_STRING = "wifiManage";
	 /**
     * public preference
     */
	public static final String KEY_IPADDRESS = "ipaddress";
	public static final String KEY_PASSWORD= "password";
	public static final String KEY_ISFIRST= "isfrist";
	public static final String FRIST_PASSWORD= "admin";

    public static final String ACTION_BROADCAST_LOCATION = "action.devicebroadcast.location";//用户传递在路由器主界面的用户点击进入相对应的界面
    public static final String ACTION_REQUEXT_ERROR = "action.norouterbroadcast.location";//新的问题
	public static final String ACTION_REQUEST_RESULT_NULL = "action.resultnullbroadcast.location";//新的问题
	public static final String ACTION_REQUEST_EXCEPTION = "action.exceptionbroadcast.location";//新的问题

	public static final String ACTION_REBOOT_SUCCESS = "action_reboot_success";
	public static final String ACTION_RESTORE_SUCCESS = "action_restore_success";
	public static enum TopPage {
		 MAIN, PAGE, PAGE2
	}

//	public static final int ACTIVITY_REBOOT_RE


	public static final int SELECT_MAIN_CONN_ROUTER = 1000;


	public static final int FRAG_INDEX_MAIN_FIRST = 100;
	public static final int FRAG_INDEX_MAIN_ROUTER = 101;
	public static final int FRAG_INDEX_MAIN_SYSTEM = 102;
	public static final int FRAG_INDEX_MAIN_TOOL = 103;

	public static final int  ADD_DEVICE_BLACK = 1;
	public static final int  ADD_DEVICE_POS = 2;
	public static final int  ADD_DEVICE_JIAZHANG = 3;


}
