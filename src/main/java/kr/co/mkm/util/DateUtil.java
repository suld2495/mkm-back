package kr.co.mkm.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {

    public static String getDateTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(cal.getTime());
    }
}
