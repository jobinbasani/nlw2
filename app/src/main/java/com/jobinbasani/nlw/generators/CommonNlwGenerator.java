package com.jobinbasani.nlw.generators;

import android.content.ContentValues;
import android.content.Context;

import com.jobinbasani.nlw.R;
import com.jobinbasani.nlw.sql.NlwDataContract;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.MutableDateTime;

import java.util.List;

/**
 * Created by jobin.basani on 1/29/2015.
 */
public abstract class CommonNlwGenerator {

    private String country;
    private Context context;

    public CommonNlwGenerator(Context context,String country){
        this.country = country;
        this.context = context;
    }

    public String getCountry(){
        return this.country;
    }

    public Context getContext(){
        return this.context;
    }

    private DateTime getChristmas(int year){
        MutableDateTime dateTime = new MutableDateTime();
        dateTime.setYear(year);
        dateTime.setMonthOfYear(DateTimeConstants.DECEMBER);
        dateTime.setDayOfMonth(25);
        return dateTime.toDateTime();
    }

    public void addChristmas(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime christmasStart = getChristmas(start.getYear());
        String[] christmasData = getContext().getResources().getStringArray(R.array.christmas);

        addHolidayInfo(valueList,christmasData,christmasStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime christmasEnd = getChristmas(end.getYear());
            addHolidayInfo(valueList,christmasData,christmasEnd,start,end);
        }
    }

    public void addGoodFriday(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime goodFridayStart = getEasterDate(start.getYear()).minusDays(2);

        String[] goodFridayData = getContext().getResources().getStringArray(R.array.goodFriday);

        addHolidayInfo(valueList,goodFridayData,goodFridayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime goodFridayEnd = getEasterDate(end.getYear()).minusDays(2);
            addHolidayInfo(valueList,goodFridayData,goodFridayEnd,start,end);
        }
    }

    public ContentValues getContentValue(DateTime date, String[] data){
        ContentValues values = new ContentValues();
        values.put(NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWDATE, date.toString("yyMMdd"));
        values.put(NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWCOUNTRY, getCountry());
        values.put(NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWNAME, data[0]);
        values.put(NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWWIKI, data[1]);
        values.put(NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWTEXT, data[2]);
        return values;
    }

    public DateTime getEasterDate(int year){
        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;
        int n = (h + l - 7 * m + 114) / 31;
        int p = (h + l - 7 * m + 114) % 31;
        MutableDateTime easterDate = new MutableDateTime();
        easterDate.setYear(year);
        easterDate.setMonthOfYear(n);
        easterDate.setDayOfMonth(p+1);
        return easterDate.toDateTime();
    }

    public void addHolidayInfo(List<ContentValues> list,String[] data, DateTime date, DateTime start, DateTime end){
        if(canAcceptDate(date,start,end)) {
            list.add(getContentValue(date, data));
        }
    }

    private boolean canAcceptDate(DateTime event, DateTime start, DateTime end){
        return (event.isAfter(start) && event.isBefore(end));
    }
}
