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
        DateTime presidentsDayStart = getPresidentsDay(start.getYear());
        String[] presidentsDayData = getContext().getResources().getStringArray(R.array.washingtonDay);
        addHolidayInfo(valueList,presidentsDayData,presidentsDayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime presidentsDayEnd = getPresidentsDay(end.getYear());
            addHolidayInfo(valueList,presidentsDayData,presidentsDayEnd,start,end);
        }
    }

    public void addMartinLutherDay(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime martinLutherDayStart = getMartinLutherDay(start.getYear());
        String[] martinLutherDayData = getContext().getResources().getStringArray(R.array.martinLutherDay);
        addHolidayInfo(valueList,martinLutherDayData,martinLutherDayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime martinLutherDayEnd = getMartinLutherDay(end.getYear());
            addHolidayInfo(valueList,martinLutherDayData,martinLutherDayEnd,start,end);
        }
    }

    public void addThanksgivingDay(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime thanksgivingStart = getThanksgivingDay(start.getYear());
        String[] thanksgivingData = getContext().getResources().getStringArray(R.array.thanksgivingUsa);
        addHolidayInfo(valueList,thanksgivingData,thanksgivingStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime thanksgivingEnd = getThanksgivingDay(end.getYear());
            addHolidayInfo(valueList,thanksgivingData,thanksgivingEnd,start,end);
        }
    }

    public void addVeteransDay(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime veteransDayStart = getVeteransDay(start.getYear());
        String[] veteransDayData = getContext().getResources().getStringArray(R.array.veteransDay);
        addHolidayInfo(valueList,veteransDayData,veteransDayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime veteransDayEnd = getVeteransDay(end.getYear());
            addHolidayInfo(valueList,veteransDayData,veteransDayEnd,start,end);
        }
    }

    public void addColumbusDay(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime columbusDayStart = getColumbusDay(start.getYear());
        String[] columbusDayData = getContext().getResources().getStringArray(R.array.columbusDay);
        addHolidayInfo(valueList,columbusDayData,columbusDayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime columbusDayEnd = getColumbusDay(end.getYear());
            addHolidayInfo(valueList,columbusDayData,columbusDayEnd,start,end);
        }
    }

    public void addLaborDay(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime laborDayStart = getLaborDay(start.getYear());
        String[] laborDayData = getContext().getResources().getStringArray(R.array.laborDayUsa);
        addHolidayInfo(valueList,laborDayData,laborDayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime laborDayEnd = getLaborDay(end.getYear());
            addHolidayInfo(valueList,laborDayData,laborDayEnd,start,end);
        }
    }

    public void addIndependenceDay(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime independenceDayStart = getIndependenceDay(start.getYear());
        String[] independenceDayData = getContext().getResources().getStringArray(R.array.independenceDayUsa);
        addHolidayInfo(valueList,independenceDayData,independenceDayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime independenceDayEnd = getIndependenceDay(end.getYear());
            addHolidayInfo(valueList,independenceDayData,independenceDayEnd,start,end);
        }
    }

    public void addMemorialDay(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime memorialDayStart = getMemorialDay(start.getYear());
        String[] memorialDayData = getContext().getResources().getStringArray(R.array.memorialDayUsa);
        addHolidayInfo(valueList,memorialDayData,memorialDayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime memorialDayEnd = getMemorialDay(end.getYear());
            addHolidayInfo(valueList,memorialDayData,memorialDayEnd,start,end);
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
        if(memorialDay.getDayOfWeek()!=DateTimeConstants.MONDAY){
            memorialDay.addDays(1-memorialDay.getDayOfWeek());
        }
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