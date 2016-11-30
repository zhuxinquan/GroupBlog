package com.get_blog_content;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhuxinquan on 16-4-26.
 * 传入一个
 */
public class Time {

    public static void main(String[] args) {
        Long l = 61429750440000L;
        System.out.println(formatTime(new Date(l)));
//        System.out.println(formatTime(new Date(getLongTime(getStandardTime("2009-10-21T03:41:01Z")))));
    }

    /**
     * 将日期格式化成字符串输出
     * @param date
     * @return
     */
    public static String formatTime(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static String getYearMonth(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月");
        return dateFormat.format(date);
    }

    /**
     * 将标准化日期字符串转换成日期
     * @param s
     * @return
     */
    public static Date getDateTime(String s){
        String[] datetime = s.split(" ");
        String[] date = datetime[0].split("-");
        String[] time = datetime[1].split(":");
        Date date1 = new Date(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]), Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));
        return date1;
    }

    public static String getStandardTime(String time) {
        String ans = "";
        if(time.charAt(0) == '2')
        {
            ans += time.substring(0, 4);
            ans += '-';
            int pos = 5;
            while(time.charAt(pos) <= '9'
                    && time.charAt(pos) >= '0') {
                ans += time.charAt(pos);
                pos++;
            }
            pos++;
            ans += '-';
            while(time.charAt(pos) <= '9'
                    && time.charAt(pos) >= '0') {
                ans += time.charAt(pos);
                pos++;
            }
            pos++;
            ans += ' ';
            while(time.charAt(pos) <= '9'
                    && time.charAt(pos) >= '0'){
                ans += time.charAt(pos);
                pos++;
            }
            ans += time.substring(pos, pos+6);
        }
        else {
            String[] m = new String[]{
                    "Jan",
                    "Feb",
                    "Mar",
                    "Apr",
                    "May",
                    "Jun",
                    "Jul",
                    "Aug",
                    "Sep",
                    "Oct",
                    "Nov",
                    "Dec"
            };

            ans += time.substring(12, 16);
            ans += '-';
            String temp = time.substring(8, 11);
            for(int i=0; i<12; i++)
                if(m[i].compareTo(temp) == 0)
                {
                    ans += i+1;
                    break;
                }
            ans += '-';
            if(time.charAt(5) != '0')
                ans += time.charAt(5);
            ans += time.charAt(6);
            ans += ' ';
            ans += time.substring(17, 17+8);
        }
        return ans;
    }
}
