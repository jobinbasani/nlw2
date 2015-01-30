package com.jobinbasani.nlw.generators;

import android.content.ContentValues;
import android.content.Context;

import com.jobinbasani.nlw.R;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.MutableDateTime;

import java.util.List;

/**
 * Created by jobin.basani on 1/29/2015.
 */
public class CanadaNlwGenerator extends CommonNlwGenerator implements NlwGeneratorI {

    public CanadaNlwGenerator(Context context){
        super(context,"Canada");
    }

    @Override
    public void fillLongWeekends(List<ContentValues> contentValuesList, DateTime start, DateTime end) {
        addFamilyDay(contentValuesList,start,end);
        addGoodFriday(contentValuesList,start,end);
        addEasterMonday(contentValuesList, start, end);
        addChristmas(contentValuesList,start,end);
    }

    public void addFamilyDay(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime familyDayStart = getFamilyDay(start.getYear(),false);
        DateTime familyDayBCStart = getFamilyDay(start.getYear(),true);
        String[] familyDayData = getContext().getResources().getStringArray(R.array.familyDay);
        String[] familyDayBCData = getContext().getResources().getStringArray(R.array.familyDayBC);
        addHolidayInfo(valueList,familyDayData,familyDayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime familyDayEnd = getFamilyDay(end.getYear(),false);
            addHolidayInfo(valueList,familyDayData,familyDayEnd,start,end);
        }

        addHolidayInfo(valueList,familyDayBCData,familyDayBCStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime familyDayBCEnd = getFamilyDay(end.getYear(),true);
            addHolidayInfo(valueList,familyDayBCData,familyDayBCEnd,start,end);
        }
    }

    public void addEasterMonday(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime easterMondayStart = getEasterDate(start.getYear()).plusDays(1);
        String[] easterMondayData = getContext().getResources().getStringArray(R.array.easterMondayCanada);

        addHolidayInfo(valueList,easterMondayData,easterMondayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime easterMondayEnd = getEasterDate(end.getYear()).plusDays(1);
            addHolidayInfo(valueList,easterMondayData,easterMondayEnd,start,end);
        }
    }

    private DateTime getFamilyDay(int year, boolean isBC){
        MutableDateTime familyDay = new MutableDateTime();
        familyDay.setYear(year);
        familyDay.setMonthOfYear(DateTimeConstants.FEBRUARY);
        familyDay.setDayOfMonth(1);
        if(familyDay.getDayOfWeek()!=DateTimeConstants.MONDAY){
            familyDay.addDays(8 - familyDay.getDayOfWeek());
        }
        familyDay.addWeeks(isBC?1:2);
        return familyDay.toDateTime();
    }
}
