package com.xalianhui.wifimanage.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class JsonUtil {
	/**
	 * 列表json,转换成对象列表
	 * @param jsonString
	 * @param cls
	 * @return
	 */
	public static List JsonToList(String jsonString, Class cls) {
        List list = new ArrayList();
        try {
            list = JSON.parseArray(jsonString, cls);
        } catch (Exception e) {
        }
        return list;
    }
	/**
	 * json转换成对象
	 * @param jsonString
	 * @param cls
	 * @return
	 */
    public static <T> T  getObject(String jsonString,  Class<T> cls) {
        T t = null;
        try {
            t = JSON.parseObject(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return t;
    }

	public static void main(String[] arg){
		String result=null;
		System.out.println("000000000000000\n11111111111111");
		System.out.println(result);
	}
	
}
