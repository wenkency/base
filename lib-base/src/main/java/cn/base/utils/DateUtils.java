package cn.base.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    /**
     * 转换时间
     *
     * @param time    时间戳
     * @param pattern 时间格式
     */
    public static String getTime(long time, String pattern) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            Date date = new Date(time);
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 转换时间
     *
     * @param time    时间戳
     * @param pattern 时间格式
     */
    public static String getTime(String time, String pattern) {
        try {
            return getTime(Long.parseLong(time), pattern);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 转换时间
     */
    public static String getTime(long time) {
        return getTime(time, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 转换时间
     */
    public static String getYear(long time) {
        return getTime(time, "yyyy-MM-dd");
    }
}
