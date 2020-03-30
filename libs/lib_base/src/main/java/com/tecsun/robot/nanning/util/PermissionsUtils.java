package com.tecsun.robot.nanning.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * 应用动态权限设置工具类
 */
public class PermissionsUtils {

    /**
     * 获取系统状态的权限
     * 打电话权限
     * 读写存储权限
     * 其他权限,非危险权限,但是一块申请了
     */
    public static final String[] MANY_STATE_PERMISSIONS = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    /**
     * 相机需获取的权限
     */
    public static final String[] CAMERA_PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };
    /**
     * 获取系统状态的权限
     */
    public static final String[] PHONE_STATE_PERMISSIONS = {Manifest.permission.READ_PHONE_STATE};
    /**
     * 读写存储权限
     */
    public static final String[] WRITE_EXTERNAL_STORAGE_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    /**
     * 定位权限
     */
    public static final String[] LOCATION_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};


    /**
     * 其他权限
     */
    public static final String[] OTHERS_PERMISSIONS = {Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
            Manifest.permission.REQUEST_INSTALL_PACKAGES,
            Manifest.permission.VIBRATE,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.CALL_PHONE,
    };


    /**
     * 检查是否需要动态获取权限
     *
     * @return true FALSE
     */
    public static boolean isRequestPermission() {
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /***开始提交请求权限*/
    public static boolean startRequestPermission(Activity activity, String[] permissions, int requestCode) {

        boolean isRefuse = false;
        int length = permissions.length;
        for (int i = 0; i < length; i++) {
            int result = ContextCompat.checkSelfPermission(activity, permissions[i]);
            if (result != PackageManager.PERMISSION_GRANTED) {
                isRefuse = true;
                break;
            }
        }
        // 检查该权限是否已经获取
        if (isRefuse) {
            // 如果没有授予该权限，就去提示用户请求
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
            return false;
        } else {
            return true;
        }




//        // 检查该权限是否已经获取
//        int i = ContextCompat.checkSelfPermission(activity, permissions[0]);
//        // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
//        if (i != PackageManager.PERMISSION_GRANTED) {
//            // 如果没有授予该权限，就去提示用户请求
//            ActivityCompat.requestPermissions(activity, permissions, requestCode);
//            return false;
//        } else {
//            return true;
//        }
    }

    @TargetApi(23)
    public static boolean requestPermissionResult(Activity activity, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean isRefuse = false;
        int length = grantResults.length;
        for (int i = 0; i < length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                isRefuse = true;
                break;
            }
        }

        if (isRefuse) {
            // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
            boolean result = activity.shouldShowRequestPermissionRationale(permissions[0]);
            if (!result) {
                // 用户还是想用我的 APP 的
                // 提示用户去应用设置界面手动开启权限
                return false;
            }
        } else {
            return true;
        }
        return false;
    }

}
