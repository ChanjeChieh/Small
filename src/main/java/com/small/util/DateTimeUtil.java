package com.small.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by skdwj on 2020/3/7.
 */
public class DateTimeUtil {

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date strToDate(String dataTimeStr, String formatStr){
        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormat.parseDateTime(dataTimeStr);
        return dateTime.toDate();
    }

    public static String dateToStr(Date date,String formatStr){
        if(date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }

    public static Date strToDate(String dataTimeStr){
        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = dateTimeFormat.parseDateTime(dataTimeStr);
        return dateTime.toDate();
    }

    public static String dateToStr(Date date){
        if(date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }

//    public static void main(String[] args) {
//        System.out.println(DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
//        System.out.println(DateTimeUtil.strToDate("2010-01-01 11:11:11", "yyyy-MM-dd HH:mm:ss"));
//    }

}
