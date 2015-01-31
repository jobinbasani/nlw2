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

    public DateTime getChristmas(int year){
        return new DateTime(year, DateTimeConstants.DECEMBER,25,0,0);
    }

    public void addChristmas(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime christmasStart = getChristmas(start.getYear());
        String[] christmasData = getContext().getResources().getStringArray(R.array.christmas);

        addHolidayInfo(valueList,christmasData,christmasStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime christmasEnd = getChristmas(end.getYear());
            addHolidayInfo(valueList, christmasData, christmasEnd, start, end);
        }
    }

    public void addNewyear(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime newyearStart = getNewyear(start.getYear());
        String[] newyearData = getContext().getResources().getStringArray(R.array.newYear);
        addHolidayInfo(valueList,newyearData,newyearStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime newyearEnd = getNewyear(end.getYear());
            addHolidayInfo(valueList,newyearData,newyearEnd,start,end);
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
        values.put(NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWDATE, date.toString(getContext().getResources().getString(R.string.dateFormat)));
        values.put(NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWCOUNTRY, getCountry());
        values.put(NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWNAME, data[0]);
        values.put(NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWWIKI, data[1]);
        values.put(NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWTEXT, data[2]);
        return values;
    }

    private DateTime getNewyear(int year){
        return new DateTime(year,DateTimeConstants.JANUARY,1,0,0);
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

        return new DateTime(year,n,p+1,0,0);
    }

    public void addHolidayInfo(List<ContentValues> list,String[] data, DateTime date, DateTime start, DateTime end){
        if(canAcceptDate(date,start,end)) {
            list.add(getContentValue(date, data));
        }
    }

    public void setFirstMonday(MutableDateTime date){
        if(date.getDayOfWeek()!=DateTimeConstants.MONDAY){
            date.addDays(8 - date.getDayOfWeek());
        }
    }

    private boolean canAcceptDate(DateTime event, DateTime start, DateTime end){
        return (event.isAfter(start) && event.isBefore(end));
    }
}
