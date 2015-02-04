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
        addNewyear(contentValuesList, start, end);
        addFamilyDay(contentValuesList,start,end);
        addGoodFriday(contentValuesList,start,end);
        addEasterMonday(contentValuesList, start, end);
        addVictoriaDay(contentValuesList, start, end);
        addCanadaDay(contentValuesList,start,end);
        addCivicHoliday(contentValuesList,start,end);
        addLabourDay(contentValuesList,start,end);
        addThanksgiving(contentValuesList,start,end);
        addRemembranceDay(contentValuesList, start, end);
        addChristmas(contentValuesList,start,end);
        addBoxingDay(contentValuesList,start,end);
    }

    public void addFamilyDay(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] familyDayData = getContext().getResources().getStringArray(R.array.familyDay);
        String[] familyDayBCData = getContext().getResources().getStringArray(R.array.familyDayBC);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,familyDayData,getFamilyDay(year,false),start,end);
            addHolidayInfo(valueList,familyDayBCData,getFamilyDay(year,true),start,end);
        }
    }

    public void addBoxingDay(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] boxingDayData = getContext().getResources().getStringArray(R.array.boxingDayCanada);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,boxingDayData,getBoxingDay(year),start,end);
        }
    }

    public void addRemembranceDay(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] remembranceDayData = getContext().getResources().getStringArray(R.array.remembranceDay);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,remembranceDayData,getRemembranceDay(year),start,end);
        }
    }

    public void addThanksgiving(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] thanksgivingData = getContext().getResources().getStringArray(R.array.thanksgivingCanada);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,thanksgivingData,getThanksgiving(year),start,end);
        }
    }

    public void addLabourDay(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] labourDayData = getContext().getResources().getStringArray(R.array.labourDayCanada);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,labourDayData,getLabourDay(year),start,end);
        }
    }

    public void addCivicHoliday(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] civicHolidayData = getContext().getResources().getStringArray(R.array.civicHoliday);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,civicHolidayData,getCivicHoliday(year),start,end);
        }
    }

    public void addCanadaDay(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] canadaDayData = getContext().getResources().getStringArray(R.array.canadaDay);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,canadaDayData,getCanadaDay(year),start,end);
        }
    }

    public void addVictoriaDay(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] victoriaDayData = getContext().getResources().getStringArray(R.array.victoriaDay);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,victoriaDayData,getVictoriaDay(year),start,end);
        }
    }

    public void addEasterMonday(List<ContentValues> valueList, DateTime start, DateTime end){
        String[] easterMondayData = getContext().getResources().getStringArray(R.array.easterMondayCanada);
        for(Integer year:getYears(start,end)){
            addHolidayInfo(valueList,easterMondayData,getEasterDate(year).plusDays(1),start,end);
        }
    }

    private DateTime getCanadaDay(int year){
        return new DateTime(year,DateTimeConstants.JULY,1,0,0);
    }

    private DateTime getRemembranceDay(int year){
        return new DateTime(year,DateTimeConstants.NOVEMBER,11,0,0);
    }

    private DateTime getVictoriaDay(int year){
        MutableDateTime victoriaDay = new MutableDateTime(year, DateTimeConstants.MAY, 25,0,0,0,0);
        if(victoriaDay.getDayOfWeek()==DateTimeConstants.MONDAY){
            victoriaDay.addWeeks(-1);
        }else{
            victoriaDay.addDays(1-victoriaDay.getDayOfWeek());
        }
        return victoriaDay.toDateTime();
    }

    private DateTime getFamilyDay(int year, boolean isBC){
        MutableDateTime familyDay = new MutableDateTime(year, DateTimeConstants.FEBRUARY,1,0,0,0,0);
        setFirstMonday(familyDay);
        familyDay.addWeeks(isBC?1:2);
        return familyDay.toDateTime();
    }

    private DateTime getCivicHoliday(int year){
        MutableDateTime civicHoliday = new MutableDateTime(year,DateTimeConstants.AUGUST,1,0,0,0,0);
        setFirstMonday(civicHoliday);
        return civicHoliday.toDateTime();
    }

    private DateTime getLabourDay(int year){
        MutableDateTime labourDay = new MutableDateTime(year, DateTimeConstants.SEPTEMBER,1,0,0,0,0);
        setFirstMonday(labourDay);
        return labourDay.toDateTime();
    }

    private DateTime getThanksgiving(int year){
        MutableDateTime thanksgiving = new MutableDateTime(year, DateTimeConstants.OCTOBER,1,0,0,0,0);
        setFirstMonday(thanksgiving);
        thanksgiving.addWeeks(1);
        return thanksgiving.toDateTime();
    }

}
