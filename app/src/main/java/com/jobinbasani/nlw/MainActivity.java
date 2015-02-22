package com.jobinbasani.nlw;

import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.jobinbasani.nlw.constants.NlwConstants;
import com.jobinbasani.nlw.receivers.NlwReceiver;
import com.jobinbasani.nlw.sql.NlwDataContract;
import com.jobinbasani.nlw.sql.NlwDataContract.NlwDataEntry;
import com.jobinbasani.nlw.util.NlwUtil;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {
	
	SharedPreferences prefs;

	private int nlwDateNumber;
	private String readMoreLink;
    private static final int LOADER_ID = 1;
    private volatile boolean isLoading = false;
    private BroadcastReceiver mReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        prefs = getPreferences(MODE_PRIVATE);
        setReceiver();
        launchTasks();
        loadData(null);
    }

	@Override
	protected void onStart() {
		super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter(NlwConstants.NLW_UPLOAD));
		EasyTracker.getInstance(this).activityStart(this);
		checkLastLoaded();
	}

	@Override
	protected void onStop() {
		super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
		EasyTracker.getInstance(this).activityStop(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_main_rate_app).setVisible(NlwUtil.showRateApp(this));
        return super.onPrepareOptionsMenu(menu);
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()){
		case R.id.eventMenuItem:
			TextView holidayText = (TextView) findViewById(R.id.nlwHolidayText);
			startActivity(NlwUtil.getAddEventIntent(this,nlwDateNumber, holidayText.getText()+""));
			break;
		case R.id.feedbackMenuItem:
			sendFeedback();
			break;
		case R.id.shareMenuItem:
			shareNlwDetails();
			break;
        case R.id.action_main_rate_app:
            startActivity(NlwUtil.getPlaystoreListing(getPackageName()));
            break;
		
		}
		
		return true;
	}

	private void checkLastLoaded(){
        int lastDate = prefs.getInt(NlwConstants.LAST_CHECKED, 0);
		if(lastDate>0 && (lastDate!=NlwUtil.getDateNumber(this, null))){
            loadData(null);
            updateDatabase();
		}
	}
    
    private void updateDatabase(){
        Intent nlwIntent = new Intent(this, NlwReceiver.class);
        sendBroadcast(nlwIntent);
    }
    
    private void setReceiver(){
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getBooleanExtra(NlwConstants.NLW_RELOAD,false)){
                    loadData(null);
                }
            }
        };
    }
	
	private void loadPreferences(){
		String defaultCountry = prefs.getString(NlwConstants.COUNTRY_KEY, "USA");
		Spinner countrySpinner = (Spinner) findViewById(R.id.countrySelector);
		SpinnerAdapter countryArray = countrySpinner.getAdapter();
		int position = -1;
		for(int i=0;i<countryArray.getCount();i++){
			if(countryArray.getItem(i).toString().equalsIgnoreCase(defaultCountry)){
				position = i;
				break;
			}
		}
		if(position>=0){
			countrySpinner.setSelection(position);
		}
	}
	
	private void setNlwDateCLickListener(){
		TextView nlwDateText = (TextView) findViewById(R.id.nlwDateText);
		nlwDateText.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(NlwUtil.getOpenCalendarIntent(MainActivity.this,nlwDateNumber));
			}
		});
	}
	
	private void sendFeedback(){
		Intent emailIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"+getResources().getString(R.string.feedbackEmail)+"?subject="+Uri.encode(getResources().getString(R.string.feedbackSubject))));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.feedbackSubject));
        startActivity( Intent.createChooser(emailIntent, getResources().getString(R.string.feedbackIntentTitle)) );
	}
	
	private void shareNlwDetails(){
        TextView holidayText = (TextView)findViewById(R.id.nlwHolidayText);
        TextView holidayDetails = (TextView) findViewById(R.id.holidayDetails);
        TextView holidayDate = (TextView) findViewById(R.id.nlwDateText);
        TextView holidayMonth = (TextView) findViewById(R.id.monthYearText);
        String[] holidayMonthArray = holidayMonth.getText().toString().split(" ");
        Intent shareIntent = NlwUtil.getShareDataIntent(holidayText.getText()+" on "+holidayMonthArray[0]+" "+holidayDate.getText()+", "+holidayMonthArray[1]+" - "+holidayDetails.getText()+". "+getResources().getString(R.string.readMoreAt)+" "+readMoreLink);
        startActivity( Intent.createChooser(shareIntent, getResources().getString(R.string.shareAction)) );
	}

    private void loadData(Bundle args){
        if(!isLoading){
            isLoading = true;
            if(getLoaderManager().getLoader(LOADER_ID)!=null){
                getLoaderManager().restartLoader(LOADER_ID,args,this);
            }else{
                getLoaderManager().initLoader(LOADER_ID,args,this);
            }
        }
    }
	
	public void onReadMore(View view){
		if(readMoreLink!=null){
			startActivity(NlwUtil.getReadMoreIntent(this, readMoreLink));
		}
	}
	
	public void onViewAll(View view){
		Intent viewAllIntent = new Intent(this, NlwListActivity.class);
		Spinner countrySpinner = (Spinner) findViewById(R.id.countrySelector);
		viewAllIntent.putExtra(NlwConstants.COUNTRY_KEY, countrySpinner.getSelectedItem().toString());
		startActivity(viewAllIntent);
	}
	
	private void setCountrySelectionListener(){
		final Spinner countrySpinner = (Spinner) findViewById(R.id.countrySelector);
		countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				SharedPreferences.Editor editor = prefs.edit();
				editor.putString(NlwConstants.COUNTRY_KEY, countrySpinner.getSelectedItem().toString());
				editor.commit();
				loadData(null);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
	}
	
	private void launchTasks(){
		setCountrySelectionListener();
		setNlwDateCLickListener();
		loadPreferences();
	}
	
	private void showWeekendInfo(Cursor data) {
		
		TextView monthYearText = (TextView) findViewById(R.id.monthYearText);
		TextView holidayText = (TextView) findViewById(R.id.nlwHolidayText);
		TextView nlwDateText = (TextView) findViewById(R.id.nlwDateText);
		TextView holidayDetails = (TextView) findViewById(R.id.holidayDetails);
		TextView daysToGoText = (TextView) findViewById(R.id.daysToGo);
		int currentDateNumber = NlwUtil.getDateNumber(this, null);
        data.moveToFirst();
		if(data.getCount()>0){
			nlwDateNumber = data.getInt(data.getColumnIndexOrThrow(NlwDataEntry.COLUMN_NAME_NLWDATE));
			readMoreLink = data.getString(data.getColumnIndexOrThrow(NlwDataEntry.COLUMN_NAME_NLWWIKI));
			String holiday = data.getString(data.getColumnIndexOrThrow(NlwDataEntry.COLUMN_NAME_NLWNAME));
			String holidayDetailText = data.getString(data.getColumnIndexOrThrow(NlwDataEntry.COLUMN_NAME_NLWTEXT));
            DateTime nlwDate = NlwUtil.getDateTimeFromNumber(this,nlwDateNumber);
            int year = nlwDate.getYear();
			int date = nlwDate.getDayOfMonth();
			int dateDiff = Days.daysBetween(new DateTime().withTimeAtStartOfDay(),nlwDate).getDays();
            String monthName = nlwDate.toString("MMMM");

			monthYearText.setText(monthName+" "+year);
			nlwDateText.setText(date+"");
			holidayText.setText(holiday);
			holidayDetails.setText(holidayDetailText);
			daysToGoText.setText(getResources().getQuantityString(R.plurals.daysRemaining, dateDiff, dateDiff));

			SharedPreferences.Editor editor = prefs.edit();
			editor.putInt(NlwConstants.LAST_CHECKED, currentDateNumber);
			editor.commit();
			
		}
	}

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Spinner countrySelector = (Spinner) findViewById(R.id.countrySelector);
        String selectedCountry = countrySelector.getSelectedItem().toString();
        String[] selectionArgs = new String[]{NlwUtil.getDateNumber(this, null)+"", selectedCountry};
        return new CursorLoader(MainActivity.this, NlwDataContract.CONTENT_URI_NLW,null,null,selectionArgs,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data!=null){
            showWeekendInfo(data);
        }
        isLoading = false;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
