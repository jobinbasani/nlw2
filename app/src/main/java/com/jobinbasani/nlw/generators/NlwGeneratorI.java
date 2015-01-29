package com.jobinbasani.nlw.generators;

import android.content.ContentValues;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by jobin.basani on 1/29/2015.
 */
public interface NlwGeneratorI {
    void fillLongWeekends(List<ContentValues> contentValuesList, DateTime start, DateTime end);
}
