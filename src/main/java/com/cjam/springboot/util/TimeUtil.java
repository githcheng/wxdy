package com.cjam.springboot.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.cjam.springboot.appEntity.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jam on 2016/6/14.
 */
public class TimeUtil {


    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    private final static Logger logger = LoggerFactory.getLogger(TimeUtil.class);

    public static String addDay(String s, int n) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(s));
            calendar.add(Calendar.DATE, n);//增加一天
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    public static String today(){
        return sdf.format(new Date());
    }

    public static String getFormatTime(long timems){
        return sdf.format(new Date(timems));
    }

    public static long getLongTypeTime(String date, String time) throws BizException {
        try {
            Date dateObj = sdf.parse(date + " " + time);
            return dateObj.getTime();
        } catch (ParseException e) {
            logger.error(e.getMessage(),e);
            throw new BizException("请选择正确的日期与时间");
        }
    }


    public static void main(String[] args){
        String today = today();
        String tommorow = addDay(today, 1);
        System.out.println(today+","+tommorow);

        String formatTime = getFormatTime(1486571543094L);
        System.out.println(formatTime);
    }
}
