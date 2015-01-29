package com.jobinbasani.nlw.generators;

import android.content.ContentValues;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by jobin.basani on 1/29/2015.
 */
public class CanadaNlwGenerator extends CommonNlwGenerator implements NlwGeneratorI {

    private static final String COUNTRY_CODE = "Canada";

    @Override
    public void fillLongWeekends(List<ContentValues> contentValuesList, DateTime start, DateTime end) {
        addChristmas(COUNTRY_CODE, contentValuesList,start,end);
    }
}
