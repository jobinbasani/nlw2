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
        addVictoriaDay(contentValuesList, start, end);
        addCanadaDay(contentValuesList,start,end);
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

    public void addCanadaDay(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime canadaDayStart = getCanadaDay(start.getYear());
        String[] canadaDayData = getContext().getResources().getStringArray(R.array.canadaDay);
        addHolidayInfo(valueList,canadaDayData,canadaDayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime canadaDayEnd = getCanadaDay(end.getYear());
            addHolidayInfo(valueList,canadaDayData,canadaDayEnd,start,end);
        }
    }

    public void addVictoriaDay(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime victoriaDayStart = getVictoriaDay(start.getYear());
        String[] victoriaDayData = getContext().getResources().getStringArray(R.array.victoriaDay);
        addHolidayInfo(valueList,victoriaDayData,victoriaDayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime victoriaDayEnd = getVictoriaDay(end.getYear());
            addHolidayInfo(valueList,victoriaDayData,victoriaDayEnd,start,end);
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

    private DateTime getCanadaDay(int year){
        return new DateTime(year,DateTimeConstants.JULY,1,0,0);
    }

    private DateTime getVictoriaDay(int year){
        MutableDateTime victoriaDay = new MutableDateTime();
        victoriaDay.setYear(year);
        victoriaDay.setMonthOfYear(DateTimeConstants.MAY);
        victoriaDay.setDayOfMonth(25);
        if(victoriaDay.getDayOfWeek()==DateTimeConstants.MONDAY){
            victoriaDay.addWeeks(-1);
        }else{
            victoriaDay.addDays(1-victoriaDay.getDayOfWeek());
        }
        return victoriaDay.toDateTime();
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
