package com.jobinbasani.nlw;

import java.util.ArrayList;
import java.util.List;

import com.google.analytics.tracking.android.EasyTracker;
import com.jobinbasani.nlw.generators.CanadaNlwGenerator;
import com.jobinbasani.nlw.generators.NlwGeneratorI;
import com.jobinbasani.nlw.sql.NlwDataContract;
import com.jobinbasani.nlw.sql.NlwDataContract.NlwDataEntry;
import com.jobinbasani.nlw.sql.NlwDataDbHelper;
import com.jobinbasani.nlw.util.NlwUtil;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ShareActionProvider;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.MutableDateTime;

public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
	
	SharedPreferences prefs;
	final public static String COUNTRY_KEY = "country";
	final private static String DB_VERSION_KEY = "dbVersion";
	final public static String LAST_CHECKED = "lastChecked";
	public static Context NLW_CONTEXT;
	private int nlwDateNumber;
	private String readMoreLink;
	private ShareActionProvider mShareActionProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        prefs = getPreferences(MODE_PRIVATE);
        int dbVersion = prefs.getInt(DB_VERSION_KEY, 0);
		NLW_CONTEXT = this;
        launchTasks();
		getLoaderManager().initLoader(1,null,this);


        NlwGeneratorI gen = new CanadaNlwGenerator(this);
        DateTime start = new DateTime();
        DateTime end = start.plusYears(1);
        List<ContentValues> ls = new ArrayList<>();
        gen.fillLongWeekends(ls,start,end);
        System.out.println(ls);

        MutableDateTime dt = new MutableDateTime();
        dt.setYear(2017);
        dt.setMonthOfYear(DateTimeConstants.FEBRUARY);
        dt.setDayOfMonth(1);
        if(dt.getDayOfWeek()!=1){
            dt.addDays(8-dt.getDayOfWeek());
        }
        dt.addWeeks(2);
        System.out.println(dt.toString("dd MMM yyyy"));

    }

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
		checkLastLoaded();
	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem shareItem = menu.findItem(R.id.shareMenuItem);
		mShareActionProvider = (ShareActionProvider) shareItem.getActionProvider();
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()){
		case R.id.eventMenuItem:
			TextView holidayText = (TextView) findViewById(R.id.nlwHolidayText);
			startActivity(NlwUtil.getAddEventIntent(nlwDateNumber, holidayText.getText()+""));
			break;
		case R.id.feedbackMenuItem:
			sendFeedback();
			break;
		case R.id.shareMenuItem:
			shareNlwDetails();
			break;
		
		}
		
		return true;
	}

	private void checkLastLoaded(){
		int lastDate = prefs.getInt(LAST_CHECKED, 0);
		if(lastDate>0 && (lastDate!=NlwUtil.getCurrentDateNumber())){
			//loadNextLongWeekend();
		}
	}
	
	private void loadPreferences(){
		String defaultCountry = prefs.getString(COUNTRY_KEY, "USA");
		Spinner countrySpinner = (Spinner) findViewById(R.id.countrySelector);
		SpinnerAdapter countryArray = countrySpinner.getAdapter();
		if(defaultCountry.equals(countrySpinner.getSelectedItem().toString())){
			//loadNextLongWeekend();
		}
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
				startActivity(NlwUtil.getOpenCalendarIntent(nlwDateNumber));
			}
		});
	}
	
	private void sendFeedback(){
		Intent emailIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"+getResources().getString(R.string.feedbackEmail)+"?subject="+Uri.encode(getResources().getString(R.string.feedbackSubject))));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.feedbackSubject));
		
		List<ResolveInfo> activities = getPackageManager().queryIntentActivities(emailIntent, 0);
		//To prevent Receiver leak bug when only application is available for Intent
		if (activities.size() > 1) {
		    // Create and start the chooser
		    Intent chooser = Intent.createChooser(emailIntent, getResources().getString(R.string.feedbackIntentTitle));
		    startActivity(chooser);

		  } else {
		    startActivity( emailIntent );
		}
	}
	
	private void shareNlwDetails(){
		if(mShareActionProvider != null){
			TextView holidayText = (TextView)findViewById(R.id.nlwHolidayText);
			TextView holidayDetails = (TextView) findViewById(R.id.holidayDetails);
			TextView holidayDate = (TextView) findViewById(R.id.nlwDateText);
			TextView holidayMonth = (TextView) findViewById(R.id.monthYearText);
			String[] holidayMonthArray = holidayMonth.getText().toString().split(" ");
			mShareActionProvider.setShareIntent(NlwUtil.getShareDataIntent(holidayText.getText()+" on "+holidayMonthArray[0]+" "+holidayDate.getText()+", "+holidayMonthArray[1]+" - "+holidayDetails.getText()+". "+getResources().getString(R.string.readMoreAt)+" "+readMoreLink));
		}
	}
	
	
	public void onReadMore(View view){
		if(readMoreLink!=null){
			
			startActivity(NlwUtil.getReadMoreIntent(this, readMoreLink));
		}
	}
	
	public void onViewAll(View view){
		Intent viewAllIntent = new Intent(this, DetailsActivity.class);
		Spinner countrySpinner = (Spinner) findViewById(R.id.countrySelector);
		viewAllIntent.putExtra(COUNTRY_KEY, countrySpinner.getSelectedItem().toString());
		startActivity(viewAllIntent);
	}
	
	private void setCountrySelectionListener(){
		final Spinner countrySpinner = (Spinner) findViewById(R.id.countrySelector);
		countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				SharedPreferences.Editor editor = prefs.edit();
				editor.putString(COUNTRY_KEY, countrySpinner.getSelectedItem().toString());
				editor.commit();
				//loadNextLongWeekend();
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
	
	private void loadNextLongWeekend(Cursor data) {
		
		TextView monthYearText = (TextView) findViewById(R.id.monthYearText);
		TextView holidayText = (TextView) findViewById(R.id.nlwHolidayText);
		TextView nlwDateText = (TextView) findViewById(R.id.nlwDateText);
		TextView holidayDetails = (TextView) findViewById(R.id.holidayDetails);
		TextView daysToGoText = (TextView) findViewById(R.id.daysToGo);
		int currentDateNumber = NlwUtil.getCurrentDateNumber();
        data.moveToFirst();
		if(data.getCount()>0){
			nlwDateNumber = data.getInt(data.getColumnIndexOrThrow(NlwDataEntry.COLUMN_NAME_NLWDATE));
			readMoreLink = data.getString(data.getColumnIndexOrThrow(NlwDataEntry.COLUMN_NAME_NLWWIKI));
			String holiday = data.getString(data.getColumnIndexOrThrow(NlwDataEntry.COLUMN_NAME_NLWNAME));
			String holidayDetailText = data.getString(data.getColumnIndexOrThrow(NlwDataEntry.COLUMN_NAME_NLWTEXT));
			int year = nlwDateNumber/10000;
			int month = (nlwDateNumber-(year*10000))/100;
			int date = nlwDateNumber-(year*10000)-(month*100);
			int dateDiff = NlwUtil.getDateDiff(nlwDateNumber, currentDateNumber);
			String monthName = NlwUtil.getMonthName(month, false);
			year = 2000+year;
			
			monthYearText.setText(monthName+" "+year);
			nlwDateText.setText(date+"");
			holidayText.setText(holiday);
			holidayDetails.setText(holidayDetailText);
			daysToGoText.setText(dateDiff+(dateDiff>1?" days ":" day ")+"to go!");
			NlwUtil.getDateDiff(nlwDateNumber, currentDateNumber);
			if(mShareActionProvider!=null){
				mShareActionProvider.setShareIntent(NlwUtil.getShareDataIntent(holiday+" on "+monthName+" "+date+", "+year+" - "+holidayDetailText+". "+getResources().getString(R.string.readMoreAt)+" "+readMoreLink));
			}
			
			SharedPreferences.Editor editor = prefs.edit();
			editor.putInt(LAST_CHECKED, currentDateNumber);
			editor.commit();
			
		}
	}

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Spinner countrySelector = (Spinner) findViewById(R.id.countrySelector);
        String selectedCountry = countrySelector.getSelectedItem().toString();
        String[] selectionArgs = new String[]{NlwUtil.getCurrentDateNumber()+"", selectedCountry};
        return new CursorLoader(MainActivity.this, NlwDataContract.CONTENT_URI_NLW,null,null,selectionArgs,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data!=null){
            loadNextLongWeekend(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private class DatabaseLoaderTask extends AsyncTask<Void, Void, Void>{
		
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
	        pDialog.setMessage(getResources().getString(R.string.loadingText));
	        pDialog.setIndeterminate(false);
	        pDialog.setCancelable(true);
	        pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			NlwDataDbHelper nlwDbHelper = new NlwDataDbHelper(NLW_CONTEXT);
			SQLiteDatabase db = nlwDbHelper.getWritableDatabase(); //Creates or inserts initial data asynchronously
			db.close();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			pDialog.dismiss();
			SharedPreferences.Editor editor = prefs.edit();
			editor.putInt(DB_VERSION_KEY, NlwDataDbHelper.DATABASE_VERSION);
			editor.commit();
			launchTasks();
		}
		
	}

}
