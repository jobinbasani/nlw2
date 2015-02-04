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
public class AustraliaNlwGenerator extends CommonNlwGenerator implements NlwGeneratorI {

    public AustraliaNlwGenerator(Context context) {
        super(context, "Australia");
    }

    @Override
    public void fillLongWeekends(List<ContentValues> contentValuesList, DateTime start, DateTime end) {
        addNewyear(contentValuesList, start, end);
        addAustraliaDay(contentValuesList, start, end);
        addCanberraDay(contentValuesList, start, end);
        addGoodFriday(contentValuesList, start, end);
        addEasterSaturday(contentValuesList, start, end);
        addEasterMonday(contentValuesList, start, end);
        addAnzacDay(contentValuesList, start, end);
        addQueensBirthday(contentValuesList, start, end);
        addLabourDay(contentValuesList, start, end);
        addChristmas(contentValuesList,start,end);
        addBoxingDay(contentValuesList, start, end);
    }

    public void addAustraliaDay(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] australiaDayData = getContext().getResources().getStringArray(R.array.australiaDay);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,australiaDayData,getAustraliaDay(year),start,end);
        }
    }

    public void addLabourDay(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] labourDayData = getContext().getResources().getStringArray(R.array.labourDayAus);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,labourDayData,getLabourDay(year),start,end);
        }
    }

    public void addQueensBirthday(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] queensBirthdayData = getContext().getResources().getStringArray(R.array.queensBirthday);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,queensBirthdayData,getQueensBirthday(year),start,end);
        }
    }

    public void addAnzacDay(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] anzacDayData = getContext().getResources().getStringArray(R.array.anzacDay);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,anzacDayData,getAnzacDay(year),start,end);
        }
    }

    public void addEasterSaturday(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] easterSaturdayData = getContext().getResources().getStringArray(R.array.easterSaturday);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,easterSaturdayData,getEasterDate(year).minusDays(1),start,end);
        }
    }

    public void addEasterMonday(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] easterMondayData = getContext().getResources().getStringArray(R.array.easterMondayAus);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,easterMondayData,getEasterDate(year).plusDays(1),start,end);
        }
    }

    public void addCanberraDay(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] canberraDayData = getContext().getResources().getStringArray(R.array.canberraDay);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,canberraDayData,getCanberraDay(year),start,end);
        }
    }

    private DateTime getAustraliaDay(int year){
        return new DateTime(year, DateTimeConstants.JANUARY, 26,0,0);
    }

    private DateTime getCanberraDay(int year){
        MutableDateTime canberraDay = new MutableDateTime(year,DateTimeConstants.MARCH,1,0,0,0,0);
        setFirstMonday(canberraDay);
        canberraDay.addWeeks(1);
        return canberraDay.toDateTime();
    }

    private DateTime getAnzacDay(int year){
        return new DateTime(year,DateTimeConstants.APRIL,25,0,0);
    }

    private DateTime getQueensBirthday(int year){
        MutableDateTime queensBirthday = new MutableDateTime(year,DateTimeConstants.JUNE,1,0,0,0,0);
        setFirstMonday(queensBirthday);
        queensBirthday.addWeeks(1);
        return queensBirthday.toDateTime();
    }

    private DateTime getLabourDay(int year){
        MutableDateTime labourDay = new MutableDateTime(year,DateTimeConstants.OCTOBER,1,0,0,0,0);
        setFirstMonday(labourDay);
        return labourDay.toDateTime();
    }

}
