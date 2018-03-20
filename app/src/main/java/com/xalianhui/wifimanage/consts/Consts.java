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

    public static final String ACTION_BROADCAST_LOCATION = "action.broadcast.location";//新的问题
    public static final String ACTION_REQUEXT_ERROR = "action.broadcast.location";//新的问题
	public static enum TopPage {
		 MAIN, PAGE, PAGE2
	}

	public static final int SELECT_MAIN_CONN_ROUTER = 1000;


	public static final int FRAG_INDEX_MAIN_FIRST = 100;
	public static final int FRAG_INDEX_MAIN_ROUTER = 101;
	public static final int FRAG_INDEX_MAIN_SYSTEM = 102;
	public static final int FRAG_INDEX_MAIN_TOOL = 103;

	public static final int  ADD_DEVICE_BLACK = 1;
	public static final int  ADD_DEVICE_POS = 2;
	public static final int  ADD_DEVICE_JIAZHANG = 3;


}
