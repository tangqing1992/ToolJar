package www.tq.weather.unit;

import android.util.Log;

public class DbUnit {

    public static  boolean isdebuge = true;

    public static void setIsdebuge(boolean isdebuge) {
        DbUnit.isdebuge = isdebuge;
    }

    public static void  logd(String tag, String txt){
        if (isdebuge)
            Log.d(tag,txt);
    }
    public static void  loge(String tag,String txt){
        if (isdebuge)
            Log.e(tag,txt);
    }

    public static void  logpaserd(String tag,String txt){
           Log.d(tag,txt);
    }
}
