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

    public void addBoxingDay(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime boxingDayStart = getChristmas(start.getYear()).plusDays(1);
        String[] boxingDayData = getContext().getResources().getStringArray(R.array.boxingDayCanada);
        addHolidayInfo(valueList,boxingDayData,boxingDayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime boxingDayEnd = getChristmas(end.getYear()).plusDays(1);
            addHolidayInfo(valueList,boxingDayData,boxingDayEnd,start,end);
        }
    }

    public void addRemembranceDay(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime remembranceDayStart = getRemembranceDay(start.getYear());
        String[] remembranceDayData = getContext().getResources().getStringArray(R.array.remembranceDay);

        addHolidayInfo(valueList,remembranceDayData,remembranceDayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime remembranceDayEnd = getRemembranceDay(end.getYear());
            addHolidayInfo(valueList,remembranceDayData,remembranceDayEnd,start,end);
        }
    }

    public void addThanksgiving(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime thanksgivingStart = getThanksgiving(start.getYear());
        String[] thanksgivingData = getContext().getResources().getStringArray(R.array.thanksgivingCanada);
        addHolidayInfo(valueList,thanksgivingData,thanksgivingStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime thanksgivingEnd = getThanksgiving(end.getYear());
            addHolidayInfo(valueList,thanksgivingData,thanksgivingEnd,start,end);
        }
    }

    public void addLabourDay(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime labourDayStart = getLabourDay(start.getYear());
        String[] labourDayData = getContext().getResources().getStringArray(R.array.labourDayCanada);
        addHolidayInfo(valueList,labourDayData,labourDayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime labourDayEnd = getLabourDay(end.getYear());
            addHolidayInfo(valueList,labourDayData,labourDayEnd,start,end);
        }
    }

    public void addCivicHoliday(List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime civicHolidayStart = getCivicHoliday(start.getYear());
        String[] civicHolidayData = getContext().getResources().getStringArray(R.array.civicHoliday);
        addHolidayInfo(valueList,civicHolidayData,civicHolidayStart,start,end);
        if(start.getYear()!=end.getYear()){
            DateTime civicHolidayEnd = getCivicHoliday(end.getYear());
            addHolidayInfo(valueList,civicHolidayData,civicHolidayEnd,start,end);
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
