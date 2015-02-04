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
public class UsaNlwGenerator extends CommonNlwGenerator implements NlwGeneratorI {

    public UsaNlwGenerator(Context context) {
        super(context, "USA");
    }

    @Override
    public void fillLongWeekends(List<ContentValues> contentValuesList, DateTime start, DateTime end) {
        addNewyear(contentValuesList, start, end);
        addMartinLutherDay(contentValuesList, start, end);
        addPresidentsDay(contentValuesList, start, end);
        addMemorialDay(contentValuesList, start, end);
        addIndependenceDay(contentValuesList, start, end);
        addLaborDay(contentValuesList, start, end);
        addColumbusDay(contentValuesList, start, end);
        addVeteransDay(contentValuesList, start, end);
        addThanksgivingDay(contentValuesList, start, end);
        addChristmas(contentValuesList,start,end);
    }

    public void addPresidentsDay(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] presidentsDayData = getContext().getResources().getStringArray(R.array.washingtonDay);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,presidentsDayData,getPresidentsDay(year),start,end);
        }
    }

    public void addMartinLutherDay(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] martinLutherDayData = getContext().getResources().getStringArray(R.array.martinLutherDay);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,martinLutherDayData,getMartinLutherDay(year),start,end);
        }
    }

    public void addThanksgivingDay(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] thanksgivingData = getContext().getResources().getStringArray(R.array.thanksgivingUsa);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,thanksgivingData,getThanksgivingDay(year),start,end);
        }
    }

    public void addVeteransDay(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] veteransDayData = getContext().getResources().getStringArray(R.array.veteransDay);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,veteransDayData,getVeteransDay(year),start,end);
        }
    }

    public void addColumbusDay(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] columbusDayData = getContext().getResources().getStringArray(R.array.columbusDay);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,columbusDayData,getColumbusDay(year),start,end);
        }
    }

    public void addLaborDay(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] laborDayData = getContext().getResources().getStringArray(R.array.laborDayUsa);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,laborDayData,getLaborDay(year),start,end);
        }
    }

    public void addIndependenceDay(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] independenceDayData = getContext().getResources().getStringArray(R.array.independenceDayUsa);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,independenceDayData,getIndependenceDay(year),start,end);
        }
    }

    public void addMemorialDay(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] memorialDayData = getContext().getResources().getStringArray(R.array.memorialDayUsa);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,memorialDayData,getMemorialDay(year),start,end);
        }
    }

    private DateTime getPresidentsDay(int year){
        MutableDateTime presidentsDay = new MutableDateTime(year, DateTimeConstants.FEBRUARY,1,0,0,0,0);
        setFirstMonday(presidentsDay);
        presidentsDay.addWeeks(2);
        return presidentsDay.toDateTime();
    }

    private DateTime getMemorialDay(int year){
        MutableDateTime memorialDay = new MutableDateTime(year,DateTimeConstants.MAY,31,0,0,0,0);
        setLastMonday(memorialDay);
        return memorialDay.toDateTime();
    }

    private DateTime getIndependenceDay(int year){
        return new DateTime(year,DateTimeConstants.JULY,4,0,0);
    }

    private DateTime getLaborDay(int year){
        MutableDateTime laborDay = new MutableDateTime(year, DateTimeConstants.SEPTEMBER,1,0,0,0,0);
        setFirstMonday(laborDay);
        return laborDay.toDateTime();
    }

    private DateTime getColumbusDay(int year){
        MutableDateTime columbusDay = new MutableDateTime(year, DateTimeConstants.OCTOBER,1,0,0,0,0);
        setFirstMonday(columbusDay);
        columbusDay.addWeeks(1);
        return columbusDay.toDateTime();
    }

    private DateTime getVeteransDay(int year){
        return new DateTime(year,DateTimeConstants.NOVEMBER,11,0,0);
    }

    private DateTime getThanksgivingDay(int year){
        MutableDateTime thanksgivingDay = new MutableDateTime(year,DateTimeConstants.NOVEMBER,1,0,0,0,0);
        if(thanksgivingDay.getDayOfWeek()!=DateTimeConstants.THURSDAY){
            if(thanksgivingDay.getDayOfWeek()==DateTimeConstants.SUNDAY){
                thanksgivingDay.addDays(4);
            }else if(thanksgivingDay.getDayOfWeek()<DateTimeConstants.THURSDAY){
                thanksgivingDay.addDays(4-thanksgivingDay.getDayOfWeek());
            } else if (thanksgivingDay.getDayOfWeek()>DateTimeConstants.THURSDAY){
                thanksgivingDay.addDays(11-thanksgivingDay.getDayOfWeek());
            }
        }
        thanksgivingDay.addWeeks(3);
        return thanksgivingDay.toDateTime();
    }

    private DateTime getMartinLutherDay(int year){
        MutableDateTime martinLutherDay = new MutableDateTime(year, DateTimeConstants.JANUARY,1,0,0,0,0);
        setFirstMonday(martinLutherDay);
        martinLutherDay.addWeeks(2);
        return martinLutherDay.toDateTime();
    }
}
