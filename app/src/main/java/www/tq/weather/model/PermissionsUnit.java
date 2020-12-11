package www.tq.weather.model;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionsUnit {

    /*安装apk权限*/
    public static final String permission_installapk = "android.permission.REQUEST_INSTALL_PACKAGES";
    /*授权返回*/
    public static final int requestCode = 1009;
    /*基础权限网络、 读/写权限*/
    public static final String[] basepermissions = new String[]{
            "android.permission.CAMERA", "android.permission.INTERNET", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"
    };
    /*照相机权限、 读/写权限*/
    public static final String[] camerapermissions = new String[]{
            "android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"
    };
    /*校验是否授权*/
    public static boolean checkoutPermission(Activity activity, String permssion) {
        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), permssion) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    /*拉起权限*/
    public static void applyforPermission(Activity activity, String permssion) {
        ActivityCompat.requestPermissions(activity, new String[]{permssion}, requestCode);
    }

    /*判断Android版本是否大于等于6.0*/
    public static boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

}
