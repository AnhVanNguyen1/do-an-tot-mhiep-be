package com.shop.shop.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class  DateTimeUtil {
    public  final String DEFAULT_SHORTDATEFORMAT = "dd/MM/yyyy";
    public  final String DEFAULT_SIMPLEDATEFORMAT = "yyyy-MM-dd";
    public  final SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat(DEFAULT_SIMPLEDATEFORMAT);

    public  final SimpleDateFormat TIME = new SimpleDateFormat("hh:mm");
    public  final SimpleDateFormat DATE = new SimpleDateFormat("yyyy-mm-yy");

    public  Date convertFromShortString(final String date) throws ParseException {
        return SHORT_DATE_FORMAT.parse(date);
    }


    public  long convertToPETime(final Date date) {
        final long value = date.getTime();
        return value / 1000;
    }

    public  String getDate(final Date date) {
        return DATE.format(date);
    }

    public  String getTime(final Date date) {
        return TIME.format(date);
    }

    public  long dateTolong(final String strDate) throws ParseException {
        Date date = TIME.parse(strDate);
        return date.getTime();
    }

    public  String dateToString(Date date,String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String strDate = simpleDateFormat.format(date);
        return strDate;
    }

}
