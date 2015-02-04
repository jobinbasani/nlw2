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
        String[] easterMondayData = getContext().getResources().getStringArray(R.array.easterMondayUk);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,easterMondayData,getEasterDate(year).plusDays(1),start,end);
        }
    }

    public void addHalloween(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] halloweenData = getContext().getResources().getStringArray(R.array.halloween);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,halloweenData,getHalloweenDay(year),start,end);
        }
    }

    public void addGuyFawkes(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] guyFawkesData = getContext().getResources().getStringArray(R.array.guyFawkesDay);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,guyFawkesData,getGuyFawkesDay(year),start,end);
        }
    }

    public void addSpringBankHoliday(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] springBankHolidayData = getContext().getResources().getStringArray(R.array.springBankHoliday);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,springBankHolidayData,getSpringBankHoliday(year),start,end);
        }
    }

    public void addFirstSummerBankHoliday(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] summerBankHolidayData = getContext().getResources().getStringArray(R.array.summerBankHolidayFirst);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,summerBankHolidayData,getFirstSummerBankHoliday(year),start,end);
        }
    }

    public void addLastSummerBankHoliday(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] summerBankHolidayData = getContext().getResources().getStringArray(R.array.summerBankHolidayLast);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,summerBankHolidayData,getLastSummerBankHoliday(year),start,end);
        }
    }

    public void addMayDay(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] mayDayData = getContext().getResources().getStringArray(R.array.mayDayHoliday);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,mayDayData,getMayDay(year),start,end);
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
