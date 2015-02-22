package com.jobinbasani.nlw.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;

import com.jobinbasani.nlw.R;
import com.jobinbasani.nlw.ReadMoreActivity;
import com.jobinbasani.nlw.generators.AustraliaNlwGenerator;
import com.jobinbasani.nlw.generators.CanadaNlwGenerator;
import com.jobinbasani.nlw.generators.NlwGeneratorI;
import com.jobinbasani.nlw.generators.UkNlwGenerator;
import com.jobinbasani.nlw.generators.UsaNlwGenerator;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;


public class NlwUtil {
	
	public static final String URL_KEY = "url";
    public static final String NLW_UPLOAD = "nlwupload";

	public static int getDateNumber(Context context, DateTime dateTime){
        if(dateTime==null)dateTime = new DateTime();
		return Integer.parseInt(dateTime.toString(context.getResources().getString(R.string.dateFormat)));
	}
	
    public static DateTime getDateTimeFromNumber(Context context, int number){
        return DateTimeFormat.forPattern(context.getResources().getString(R.string.dateFormat)).parseDateTime(number+"");
    }

	public static Intent getOpenCalendarIntent(Context context, int nlwDateNumber){
        DateTime nlwDate = getDateTimeFromNumber(context, nlwDateNumber);
		Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
		builder.appendPath("time");
		builder.appendPath(Long.toString(nlwDate.getMillis()));
		Intent calendarIntent = new Intent(Intent.ACTION_VIEW,builder.build());
		return calendarIntent;
	}
	
	public static Intent getAddEventIntent(Context context, int nlwDateNumber, String title){
		
        DateTime nlwDate = getDateTimeFromNumber(context,nlwDateNumber);
		Intent calendarIntent = new Intent(Intent.ACTION_INSERT);
		calendarIntent.setData(Events.CONTENT_URI);
		calendarIntent.putExtra(Events.ALL_DAY, true);
		calendarIntent.putExtra(Events.TITLE, title);
		calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, nlwDate.plusHours(9).getMillis());
		calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, nlwDate.plusHours(18).getMillis());
		return calendarIntent;
	}
	
	public static Intent getReadMoreIntent(Context context, String url){
		Intent readMoreIntent = new Intent(context,ReadMoreActivity.class);
		readMoreIntent.putExtra(URL_KEY, url);
		return readMoreIntent;
	}
	
	public static Intent getShareDataIntent(String data){
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_TEXT, data);
		shareIntent.setType("text/plain");
		return shareIntent;
	}

    public static List<NlwGeneratorI> getGenerators(Context context){
        List<NlwGeneratorI> generatorList = new ArrayList<>();
        generatorList.add(new AustraliaNlwGenerator(context));
        generatorList.add(new CanadaNlwGenerator(context));
        generatorList.add(new UkNlwGenerator(context));
        generatorList.add(new UsaNlwGenerator(context));
        return generatorList;
    }
    
    public static NlwGeneratorI getGenerator(Context context, String country){
        switch (country.toUpperCase()){
            case "USA":
                return new UsaNlwGenerator(context);
            case "UK":
                return new UkNlwGenerator(context);
            case "AUSTRALIA":
                return new AustraliaNlwGenerator(context);
            case "CANADA":
                return new CanadaNlwGenerator(context);
            default:
                return null;
        }
    }

    public static boolean showRateApp(Context context){
        String installedBy = context.getPackageManager().getInstallerPackageName(context.getPackageName());
        if(installedBy == null || !installedBy.equals(context.getResources().getString(R.string.playStore))){
            return false;
        }
        return true;
    }

    public static Intent getPlaystoreListing(String packageName){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id="+packageName));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        return intent;
    }

}
