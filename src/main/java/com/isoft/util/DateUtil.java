package com.isoft.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    /**
     * java.util.Date 转换为字符串
     * @param date
     * @return 转换结果字符串格式为 yyyy-MM-dd HH:mm:ss
     */
    public static String date2Str(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd") ;
        return df.format(date) ;
    }

    /**
     * 字符串转Date
     * @param dateStr  格式必须是 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date str2Date(String dateStr) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd") ;
        try {
            return df.parse(dateStr) ;
        } catch (ParseException e) {
            e.printStackTrace();
            return null ;
        }
    }
}
