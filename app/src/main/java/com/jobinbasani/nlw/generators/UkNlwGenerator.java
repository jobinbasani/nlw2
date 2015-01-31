package com.jobinbasani.nlw.generators;

import android.content.ContentValues;
import android.content.Context;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by jobinbasani on 1/30/15.
 */
public class UkNlwGenerator extends CommonNlwGenerator implements NlwGeneratorI {

    public UkNlwGenerator(Context context) {
        super(context, "UK");
    }

    @Override
    public void fillLongWeekends(List<ContentValues> contentValuesList, DateTime start, DateTime end) {
        addChristmas(contentValuesList,start,end);
    }
}
