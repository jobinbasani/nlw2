package com.jobinbasani.nlw.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.LocalBroadcastManager;

import com.jobinbasani.nlw.constants.NlwConstants;
import com.jobinbasani.nlw.generators.NlwGeneratorI;
import com.jobinbasani.nlw.receivers.NlwReceiver;
import com.jobinbasani.nlw.sql.NlwDataContract;
import com.jobinbasani.nlw.util.NlwUtil;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NlwService extends IntentService {

    public NlwService() {
        super("NlwService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String currentDatetime = NlwUtil.getDateNumber(this, null)+"";
        String deletionDatetime = NlwUtil.getDateNumber(this,new DateTime().minusMonths(6))+"";
        NlwGeneratorI generator;
        Cursor cursor = getContentResolver().query(NlwDataContract.CONTENT_URI_NLW_STATS,null,null,new String[]{currentDatetime},null);
        boolean refreshData = false;
        if(cursor!=null){
            List<ContentValues> valueList = new ArrayList<>();
            while (cursor.moveToNext()){
                System.out.println(cursor.getString(cursor.getColumnIndex("country")));
                int remainingCount = cursor.getInt(cursor.getColumnIndex("count"));
                if(remainingCount<=NlwConstants.MIN_ENTRIES){
                    if(remainingCount==0)refreshData = true;
                    int maxDate = cursor.getInt(cursor.getColumnIndex("date"));
                    String country = cursor.getString(cursor.getColumnIndex("country"));
                    generator = NlwUtil.getGenerator(this,country);
                    if(generator!=null){
                        DateTime now = new DateTime();
                        DateTime startTime = NlwUtil.getDateTimeFromNumber(this,maxDate);
                        startTime = startTime.isBefore(now)?now:startTime.plusDays(1);
                        DateTime endTime = startTime.plusMonths(6);
                        generator.fillLongWeekends(valueList,startTime,endTime);
                    }
                }
            }
            if(!valueList.isEmpty()){
                getContentResolver().bulkInsert(NlwDataContract.CONTENT_URI,valueList.toArray(new ContentValues[valueList.size()]));
            }
            cursor.close();
        }
        getContentResolver().delete(NlwDataContract.CONTENT_URI,null,new String[]{deletionDatetime});
        
        Intent nlwIntent = new Intent(NlwUtil.NLW_UPLOAD);
        nlwIntent.putExtra(NlwConstants.NLW_RELOAD, refreshData);
        LocalBroadcastManager.getInstance(this).sendBroadcast(nlwIntent);
        NlwReceiver.completeWakefulIntent(intent);
    }

}
