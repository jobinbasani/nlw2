package com.jobinbasani.nlw.generators;

import android.content.ContentValues;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.MutableDateTime;

import java.util.List;

/**
 * Created by jobin.basani on 1/29/2015.
 */
public abstract class CommonNlwGenerator {

    private DateTime getChristmas(int year){
        MutableDateTime dateTime = new MutableDateTime();
        dateTime.setYear(year);
        dateTime.setMonthOfYear(DateTimeConstants.DECEMBER);
        dateTime.setDayOfMonth(25);
        return dateTime.toDateTime();
    }

    public void addChristmas(String countryCode, List<ContentValues> valueList, DateTime start, DateTime end){
        DateTime christmas = getChristmas(start.getYear());
        if(christmas.isAfter(start)){

        }
    }
}
