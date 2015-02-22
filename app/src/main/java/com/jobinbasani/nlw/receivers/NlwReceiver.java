package com.jobinbasani.nlw.receivers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.jobinbasani.nlw.services.NlwService;

public class NlwReceiver extends WakefulBroadcastReceiver {
    
    public NlwReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent sIntent = new Intent(context, NlwService.class);
        startWakefulService(context, sIntent);
    }
}
