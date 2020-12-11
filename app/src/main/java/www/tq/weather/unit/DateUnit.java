package www.tq.weather.unit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUnit {

    public static String getTime(long time, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format);
            Date date = new Date(time);
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long getTime(String time, String formatStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            Date date = format.parse(time);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
