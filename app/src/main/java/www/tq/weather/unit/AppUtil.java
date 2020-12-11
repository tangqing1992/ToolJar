package www.tq.weather.unit;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

import androidx.core.content.FileProvider;

import java.io.File;

import www.tq.weather.model.DbUnit;

public class AppUtil {

    private static String tag = "AppUtils";
    public static final String key_path = "key_path";

    /*下载成功，开始安装,兼容8.0安装位置来源的权限*/
    public static void installApkO(Activity activity, String downloadApkPath,int requestCode) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                DbUnit.logd(tag,"---->= ---");
                //是否有安装位置来源的权限
                boolean haveInstallPermission = activity.getPackageManager().canRequestPackageInstalls();
                DbUnit.logd(tag,"-------haveInstallPermission="+haveInstallPermission);

                if (haveInstallPermission) {
                    DbUnit.logd(tag,"8.0手机已经拥有安装未知来源应用的权限，直接安装！");
                    installApk(activity, downloadApkPath);
                } else {
                    DbUnit.logd(tag,"无权限");
                    Uri packageUri = Uri.parse("package:"+ AppUtil.getAppPackageName(activity));
                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,packageUri);
                    intent.putExtra(key_path,downloadApkPath);
                    activity.startActivityForResult(intent,requestCode);
                }
            } else {
                DbUnit.logd(tag,"----installApk---");
               installApk(activity, downloadApkPath);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    public static  void installApk(Context context, String downloadApk) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File file = new File(downloadApk);
        DbUnit.logd(tag,"安装路径=="+downloadApk);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(context, getAppPackageName(context)+".fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        }
        context.startActivity(intent);

    }

    /**
     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getAppPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取apk的版本号 currentVersionCode
    public static int getAPPVersionCode(Context ctx) {
        try {
            PackageManager manager = ctx.getPackageManager();
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    //获取apk的版本名 versionName
    public static String getAPPVersionName(Context ctx) {
        try {
            PackageManager manager = ctx.getPackageManager();
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1.0";
    }


    /*获取状态栏高度*/
    public static int getStatusBarHeight(Activity activity) {

        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;

    }

    /*获取底部导航栏高度*/
    public static int getNavigationBarHeight(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            Resources resources = activity.getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            int height = resources.getDimensionPixelSize(resourceId);
            //超出系统默认的导航栏高度以上，则认为存在虚拟导航
            if ((realSize.y - size.y) > (height - 10)) {
                return height;
            }

            return 0;
        } else {
            boolean menu = ViewConfiguration.get(activity).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return 0;
            } else {
                Resources resources = activity.getResources();
                int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
                int height = resources.getDimensionPixelSize(resourceId);
                return height;
            }
        }

    }

    public  static String getImeiOrDeviceId(Context context) {

        String deviceId;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            DbUnit.logd(tag,"------deviceId-------1----");
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return "";
                }
            }
            assert mTelephony != null;
            if (mTelephony.getDeviceId() != null)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    deviceId = mTelephony.getImei();
                    DbUnit.logd(tag,"------deviceId-------2----");
                }else {
                    deviceId = mTelephony.getDeviceId();
                    DbUnit.logd(tag,"------deviceId-------4----");
                }
            } else {
                DbUnit.logd(tag,"------deviceId-------3----");
                deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }
        DbUnit.logd(tag, "------deviceId = "+deviceId);
        return deviceId;
    }

}
