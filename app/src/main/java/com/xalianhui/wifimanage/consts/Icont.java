package com.xalianhui.wifimanage.consts;


public class Icont {
	
//		public static String Url_TopIP="http://192.168.1.165:8080";//IP地址和端口号
//		public static String Url_TopIP="http://192.168.0.117:8088";//IP地址和端口号
		public static String Url_TopIP="http://192.168.1.1:80";//IP地址和端口号

	 public static String url_top="/";//项目名称
	 public static String search_Router=url_top+"app/get_dev_info.php";//查找路由器
	 public static String login_Url=url_top+"app/get_login.php";//登陆接口
	 public static String getRouter_Url=url_top+"app/get_router_status.php";//获取连接设备
	 public static String setWan_Url=url_top+"controller/set_wan.php";//设置外网方式
	 public static String setAdminPassword_Url=url_top+"app/set_password.php";//修改管理密码
	 public static String swtWifiPass_Url=url_top+"controller/set_wireless.php";//设置wifi密码
	 public static String setDeviceName_Url=url_top+"app/set_sta_name.php";//修改设备别名
	 public static String getWanStuta_Url=url_top+"app/get_wan_cfg.php";//修改设备别名
	 public static String getDeviceStuta_Url=url_top+"app/get_sta_status.php";//修改设备别名
	 public static String setBlack_Url=url_top+"controller/set_mac_filter.php";//修改黑名单
	 public static String applyBlack_Url=url_top+"controller/set_mac_filter_apply.php";//应用黑名单
	 public static String setPos_Url=url_top+"controller/set_qos.php";//修改限速设备
	 public static String applyPos_Url=url_top+"controller/set_qos_apply.php";//应用限速设备
	 public static String getWifiCfg_Url=url_top+"app/get_basic_wireless_cfg.php";//获取wifi设置
	 public static String getHightCfg_Url=url_top+"app/get_ad_wireless_cfg.php";//获取高级设置
	 public static String get_led_Url=url_top+"app/get_led.php";//获取高级设置
	 public static String get_wifi_mode_Url=url_top+"app/get_wifi_mode.php";//获取高级设置
	 public static String setHightCfg_Url=url_top+"controller/set_ad_wireless.php";//设置高级设置
	 public static String set_wifi_mode_Url=url_top+"app/set_wifi_mode.php";//设置高级设置
	 public static String set_led_Url=url_top+"app/set_led.php";//设置高级设置
	 public static String setReboot_Url=url_top+"controller/set_reboot.php";//重启
	 public static String setFactory_Url=url_top+"controller/set_factory.php";//恢复出厂
	 public static String setOne_Url=url_top+"app/set_network_restart.php";//一键优化
	 public static String sgetBlackLiits_Url=url_top+"app/get_router_blacklist.php";//黑名单列表
	 public static String getOnlineUsers_Url=url_top+"app/get_not_in_list.php";//在线用户
	 public static String getQosList_Url=url_top+"app/get_router_qos_list.php";//获取限速设备
	 public static String setTimer_Url=url_top+"controller/set_timer.php";//提交
	 public static String setTimerApply_Url=url_top+"controller/set_timer_apply.php";//定时上网生效
	 public static String setQuick_Url=url_top+"controller/quick_set.php";//快速设置上网
	 public static String getWanCfg_Url=url_top+"app/get_wan_cfg.php";//定时上网生效
	 public static String geTimerList_Url=url_top+"app/get_router_timer_list.php";//定时上网生效
	 public static String upgrade_Url=url_top+"app/get_upgrade_status.php";//定时上网生效
	 public static String upgrade_up_Url=url_top+"controller/set_remote_upgrade.php";//定时上网生效
}
