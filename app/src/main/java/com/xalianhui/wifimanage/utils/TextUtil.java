package com.xalianhui.wifimanage.utils;

import android.content.Context;
import android.util.Log;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * Created by liubin on 2017/8/14.
 */

public class TextUtil {
    public static String getMianStr(String str){
        if (str == null){
            return "000.0";
        }
        if ("".equals(str)||"0".equals(str)){
            return "000.0";
        }
        String outStr = str;
        if (outStr.contains(".")) {
            outStr = outStr.substring(0, outStr.indexOf(".") + 3 < outStr.length() ? outStr.indexOf(".") + 3 : outStr.length());
            while (outStr.length()<5){
                outStr = "0"+outStr;
            }
        }else {
            while (outStr.length()<4){
                outStr = "0"+outStr;
            }
        }

        return outStr;

    }
    public static String getItemStr(String str){
        if (str == null){
            return "0.0";
        }
        if ("".equals(str)||"0".equals(str)){
            return "0.0";
        }
        String outStr = str;
        if (outStr.contains(".")) {
            outStr = outStr.substring(0, outStr.indexOf(".") + 3 < outStr.length() ? outStr.indexOf(".") + 3 : outStr.length());
        }

        return outStr;

    }
    public static String getTraffic(String str){
        if (str == null){
            return "0.0"+"MB";
        }
        if ("".equals(str)||"0".equals(str)){
            return "0.0"+"MB";
        }
        DecimalFormat df = new DecimalFormat("##0.00");
        Float tall = Float.parseFloat(str);
        if (tall <= 0){
            return "0.0"+"MB";
        }else if (tall < 1024 ){
            return df.format(tall) + "KB";
        }else if (tall < 1024*1024 ){
            return df.format(tall/1024)+"MB";
        }else {
            return df.format(tall/1024/1024)+"GB";
        }
//        String outStr = str;
//        if (outStr.contains(".")) {
//            outStr = outStr.substring(0, outStr.indexOf(".") + 3 < outStr.length() ? outStr.indexOf(".") + 3 : outStr.length());
//        }
    }
    public static String removeKB(String str){
        if (str.contains("KB")){
            return str.replace("KB","");
        }else if (str.contains("MB")){
            return str.replace("MB","");
        }else if (str.contains("GB")){
            return str.replace("GB","");
        }else {
            return str;
        }
    }
    public static String getTraffic1(String str){
        if (str == null){
            return "0.0"+"KB";
        }
        if ("".equals(str)||"0".equals(str)){
            return "0.0"+"KB";
        }
        DecimalFormat df = new DecimalFormat("##0.00");
        Float tall = Float.parseFloat(str);
        if (tall <= 0){
            return "0.0"+"KB";
        }else if (tall < 1024 ){
            return df.format(tall) + "KB";
        }else if (tall < 1024*1024 ){
            return df.format(tall/1024)+"MB";
        }else {
            return df.format(tall/1024/1024)+"GB";
        }
//        String outStr = str;
//        if (outStr.contains(".")) {
//            outStr = outStr.substring(0, outStr.indexOf(".") + 3 < outStr.length() ? outStr.indexOf(".") + 3 : outStr.length());
//        }


    }
    public static String getLone(){
        String locale = Locale.getDefault().getLanguage();
        Log.i("lang","lang = "+locale);
        if (locale.endsWith("zh")){
            return "zh-cn";
        }else if (locale.endsWith("en")){
            return "en-us";
        }else {
            return "en-us";
        }
    }
}
