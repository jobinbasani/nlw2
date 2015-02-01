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
        DateTime australiaDayStart = getAustraliaDay(start.getYear());
        String[] australiaDayData = getContext().getResources().getStringArray(R.array.australiaDay);
        addHolidayInfo(valueList,australiaDayData,australiaDayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime australiaDayEnd = getAustraliaDay(end.getYear());
            addHolidayInfo(valueList,australiaDayData,australiaDayEnd,start,end);
        }
    }

    public void addLabourDay(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime labourDayStart = getLabourDay(start.getYear());
        String[] labourDayData = getContext().getResources().getStringArray(R.array.labourDayAus);
        addHolidayInfo(valueList,labourDayData,labourDayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime labourDayEnd = getLabourDay(end.getYear());
            addHolidayInfo(valueList,labourDayData,labourDayEnd,start,end);
        }
    }

    public void addQueensBirthday(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime queensBirthdayStart = getQueensBirthday(start.getYear());
        String[] queensBirthdayData = getContext().getResources().getStringArray(R.array.queensBirthday);
        addHolidayInfo(valueList,queensBirthdayData,queensBirthdayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime queensBirthdayEnd = getQueensBirthday(end.getYear());
            addHolidayInfo(valueList,queensBirthdayData,queensBirthdayEnd,start,end);
        }
    }

    public void addAnzacDay(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime anzacDayStart = getAnzacDay(start.getYear());
        String[] anzacDayData = getContext().getResources().getStringArray(R.array.anzacDay);
        addHolidayInfo(valueList,anzacDayData,anzacDayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime anzacDayEnd = getAnzacDay(end.getYear());
            addHolidayInfo(valueList,anzacDayData,anzacDayEnd,start,end);
        }
    }

    public void addEasterSaturday(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime easterSaturdayStart = getEasterDate(start.getYear()).minusDays(1);
        String[] easterSaturdayData = getContext().getResources().getStringArray(R.array.easterSaturday);
        addHolidayInfo(valueList,easterSaturdayData,easterSaturdayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime easterSaturdayEnd = getEasterDate(end.getYear()).minusDays(1);
            addHolidayInfo(valueList,easterSaturdayData,easterSaturdayEnd,start,end);
        }
    }

    public void addEasterMonday(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime easterMondayStart = getEasterDate(start.getYear()).plusDays(1);
        String[] easterMondayData = getContext().getResources().getStringArray(R.array.easterMondayAus);
        addHolidayInfo(valueList,easterMondayData,easterMondayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime easterMondayEnd = getEasterDate(end.getYear()).plusDays(1);
            addHolidayInfo(valueList,easterMondayData,easterMondayEnd,start,end);
        }
    }

    public void addCanberraDay(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime canberraDayStart = getCanberraDay(start.getYear());
        String[] canberraDayData = getContext().getResources().getStringArray(R.array.canberraDay);
        addHolidayInfo(valueList,canberraDayData,canberraDayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime canberraDayEnd = getCanberraDay(end.getYear());
            addHolidayInfo(valueList,canberraDayData,canberraDayEnd,start,end);
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
