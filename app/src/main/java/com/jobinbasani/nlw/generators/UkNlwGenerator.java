package com.jobinbasani.nlw.generators;

import android.content.ContentValues;
import android.content.Context;

import com.jobinbasani.nlw.R;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.MutableDateTime;

import java.util.List;

/**
 * Created by jobinbasani on 1/30/15.
 */
public class UkNlwGenerator extends CommonNlwGenerator implements NlwGeneratorI {

    public UkNlwGenerator(Context context) {
        super(context, "UK");
    }

    @Override
    public void fillLongWeekends(List<ContentValues> contentValuesList, DateTime start, DateTime end) {
        addNewyear(contentValuesList, start, end);
        addGoodFriday(contentValuesList, start, end);
        addEasterMonday(contentValuesList, start, end);
        addMayDay(contentValuesList, start, end);
        addSpringBankHoliday(contentValuesList, start, end);
        addFirstSummerBankHoliday(contentValuesList, start, end);
        addLastSummerBankHoliday(contentValuesList, start, end);
        addHalloween(contentValuesList, start, end);
        addChristmas(contentValuesList,start,end);
        addGuyFawkes(contentValuesList, start, end);
        addBoxingDay(contentValuesList, start, end);
    }

    public void addEasterMonday(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime easterMondayStart = getEasterDate(start.getYear()).plusDays(1);
        String[] easterMondayData = getContext().getResources().getStringArray(R.array.easterMondayUk);
        addHolidayInfo(valueList,easterMondayData,easterMondayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime easterMondayEnd = getEasterDate(end.getYear()).plusDays(1);
            addHolidayInfo(valueList,easterMondayData,easterMondayEnd,start,end);
        }
    }

    public void addHalloween(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime halloweenStart = getHalloweenDay(start.getYear());
        String[] halloweenData = getContext().getResources().getStringArray(R.array.halloween);
        addHolidayInfo(valueList,halloweenData,halloweenStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime halloweenEnd = getHalloweenDay(end.getYear());
            addHolidayInfo(valueList,halloweenData,halloweenEnd,start,end);
        }
    }

    public void addGuyFawkes(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime guyFawkesStart = getGuyFawkesDay(start.getYear());
        String[] guyFawkesData = getContext().getResources().getStringArray(R.array.guyFawkesDay);
        addHolidayInfo(valueList,guyFawkesData,guyFawkesStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime guyFawkesEnd = getGuyFawkesDay(end.getYear());
            addHolidayInfo(valueList,guyFawkesData,guyFawkesEnd,start,end);
        }
    }

    public void addSpringBankHoliday(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime springBankHolidayStart = getSpringBankHoliday(start.getYear());
        String[] springBankHolidayData = getContext().getResources().getStringArray(R.array.springBankHoliday);
        addHolidayInfo(valueList,springBankHolidayData,springBankHolidayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime springBankHolidayEnd = getSpringBankHoliday(end.getYear());
            addHolidayInfo(valueList,springBankHolidayData,springBankHolidayEnd,start,end);
        }
    }

    public void addFirstSummerBankHoliday(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime summerBankHolidayStart = getFirstSummerBankHoliday(start.getYear());
        String[] summerBankHolidayData = getContext().getResources().getStringArray(R.array.summerBankHolidayFirst);
        addHolidayInfo(valueList,summerBankHolidayData,summerBankHolidayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime summerBankHolidayEnd = getFirstSummerBankHoliday(end.getYear());
            addHolidayInfo(valueList,summerBankHolidayData,summerBankHolidayEnd,start,end);
        }
    }

    public void addLastSummerBankHoliday(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime summerBankHolidayStart = getLastSummerBankHoliday(start.getYear());
        String[] summerBankHolidayData = getContext().getResources().getStringArray(R.array.summerBankHolidayLast);
        addHolidayInfo(valueList,summerBankHolidayData,summerBankHolidayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime summerBankHolidayEnd = getLastSummerBankHoliday(end.getYear());
            addHolidayInfo(valueList,summerBankHolidayData,summerBankHolidayEnd,start,end);
        }
    }

    public void addMayDay(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime mayDayStart = getMayDay(start.getYear());
        String[] mayDayData = getContext().getResources().getStringArray(R.array.mayDayHoliday);
        addHolidayInfo(valueList,mayDayData,mayDayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime mayDayEnd = getMayDay(end.getYear());
            addHolidayInfo(valueList,mayDayData,mayDayEnd,start,end);
        }
    }

    private DateTime getMayDay(int year){
        MutableDateTime mayDay = new MutableDateTime(year, DateTimeConstants.MAY,1,0,0,0,0);
        setFirstMonday(mayDay);
        return mayDay.toDateTime();
    }

    private DateTime getSpringBankHoliday(int year){
        MutableDateTime springBankHoliday = new MutableDateTime(year,DateTimeConstants.MAY,31,0,0,0,0);
        setLastMonday(springBankHoliday);
        return springBankHoliday.toDateTime();
    }

    private DateTime getFirstSummerBankHoliday(int year){
        MutableDateTime firstSummerBankHoliday = new MutableDateTime(year,DateTimeConstants.AUGUST,1,0,0,0,0);
        setFirstMonday(firstSummerBankHoliday);
        return firstSummerBankHoliday.toDateTime();
    }

    private DateTime getLastSummerBankHoliday(int year){
        MutableDateTime lastSummerBankHoliday = new MutableDateTime(year,DateTimeConstants.AUGUST,31,0,0,0,0);
        setLastMonday(lastSummerBankHoliday);
        return lastSummerBankHoliday.toDateTime();
    }

    private DateTime getHalloweenDay(int year){
        return new DateTime(year,DateTimeConstants.OCTOBER,31,0,0);
    }

    private DateTime getGuyFawkesDay(int year){
        return new DateTime(year,DateTimeConstants.NOVEMBER,5,0,0);
    }
}
