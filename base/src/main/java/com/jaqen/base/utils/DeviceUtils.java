package com.jaqen.base.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 与设备信息相关的工具类
 *
 * @author kenping.liu
 */
public class DeviceUtils {
    /**
     * Android sdk版本
     */
    public static int client_os_SDK_INT = android.os.Build.VERSION.SDK_INT;

    /**
     * Android 系统版本
     */
    public static String client_os_version = android.os.Build.VERSION.RELEASE;
    /**
     * 手机型号
     */
    public static String client_os_model = android.os.Build.MODEL;

    /**
     * 获取设备id
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * client_os
     */
    public static final String client_os = "android";

    /**
     * 获取app版本号
     *
     * @param context
     * @return
     */
    public static String getApp_version(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = null;
            info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 时间搓转换 秒
     * @param timeStr
     * @return
     */
    public static String formatData(/*String dataFormat, */String timeStr) {
        long timeStamp = Long.parseLong(timeStr);
        if (timeStamp == 0) {
            return "";
        }
        timeStamp = timeStamp * 1000;
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        result = format.format(new Date(timeStamp));
        return result;
    }

    /**
     * 格式化系统时间转换 毫秒
     * @return
     */
    public static String formatDataCurrentTimeMillis() {
        long currentTimeMillis = System.currentTimeMillis();
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        result = format.format(new Date(currentTimeMillis));
        return result;
    }


}
