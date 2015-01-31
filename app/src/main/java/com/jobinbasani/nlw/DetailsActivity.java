package com.jobinbasani.nlw;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.jobinbasani.nlw.sql.NlwDataContract;
import com.jobinbasani.nlw.sql.NlwDataContract.NlwDataEntry;
import com.jobinbasani.nlw.util.NlwUtil;

import org.joda.time.DateTime;

public class DetailsActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor>{
	
    private static final int LOADER_ID = 2;
    private static final String COUNTRY="Country";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent detailsIntent = getIntent();
		String country = detailsIntent.getStringExtra(MainActivity.COUNTRY_KEY);
		TextView holidayCountryText = (TextView) findViewById(R.id.holidayCountryInfo);
		holidayCountryText.setText(getResources().getString(R.string.upcomingWeekendsText)+" "+country);

        Bundle args = new Bundle();
        args.putString(COUNTRY, country);

        loadData(args);
	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this); 
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

    private void setListData(Cursor cursor){
        String[] from = new String[] { NlwDataEntry.COLUMN_NAME_NLWDATE, NlwDataEntry.COLUMN_NAME_NLWNAME, NlwDataEntry.COLUMN_NAME_NLWTEXT, NlwDataEntry.COLUMN_NAME_NLWDATE, NlwDataEntry.COLUMN_NAME_NLWDATE, NlwDataEntry.COLUMN_NAME_NLWDATE };
        int[] to = new int[] { R.id.detailDateText, R.id.detailHolidayName, R.id.detailHolidayDetails, R.id.detailYearText, R.id.detailMonthText, R.id.overflowIcon};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(), R.layout.nlw_details, cursor, from, to, SimpleCursorAdapter.NO_SELECTION);
        adapter.setViewBinder(new DetailsViewBinder());
        setListAdapter(adapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] selectionArgs = new String[]{args.getString(COUNTRY),NlwUtil.getCurrentDateNumber(this)+""};
        return new CursorLoader(DetailsActivity.this, NlwDataContract.CONTENT_URI_NLW_LIST,null,null,selectionArgs,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data!=null){
            setListData(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void loadData(Bundle args){
        if(getLoaderManager().getLoader(LOADER_ID)!=null){
            getLoaderManager().restartLoader(LOADER_ID,args,this);
        }else{
            getLoaderManager().initLoader(LOADER_ID,args,this);
        }
    }

    private class DetailsViewBinder implements ViewBinder{

		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			if(view.getId() == R.id.detailDateText){
				TextView dateText = (TextView) view;
				int dateNumber = cursor.getInt(columnIndex);
                DateTime date = NlwUtil.getDateTimeFromNumber(DetailsActivity.this,dateNumber);
				dateText.setText(date.getDayOfMonth()+"");
				dateText.setTag(dateNumber+"");
				return true;
			}else if(view.getId() == R.id.detailMonthText){
				TextView monthText = (TextView) view;
                DateTime date = NlwUtil.getDateTimeFromNumber(DetailsActivity.this,cursor.getInt(columnIndex));
				monthText.setText(date.toString("MMM"));
				return true;
			}else if(view.getId() == R.id.detailYearText){
				TextView yearText = (TextView) view;
                DateTime date = NlwUtil.getDateTimeFromNumber(DetailsActivity.this,cursor.getInt(columnIndex));
				yearText.setText(date.getYear()+"");
				return true;
			}else if(view.getId() == R.id.detailHolidayDetails){
				TextView detailsText = (TextView) view;
				detailsText.setText(cursor.getString(columnIndex)+"");
				detailsText.setTag(cursor.getString(cursor.getColumnIndexOrThrow(NlwDataEntry.COLUMN_NAME_NLWWIKI)));
				return true;
			}else if(view.getId() == R.id.overflowIcon){
				final PopupMenu popupMenu = new PopupMenu(DetailsActivity.this, view);
				popupMenu.getMenuInflater().inflate(R.menu.holiday_details, popupMenu.getMenu());
				view.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						popupMenu.show();
					}
				});
				popupMenu.setOnMenuItemClickListener(new DetailsMenuClick(view.getParent()));
			
				return true;
			}
			return false;
		}
		
	}
	
	private class DetailsMenuClick implements OnMenuItemClickListener{
		
		private RelativeLayout rl;
		
		DetailsMenuClick(ViewParent view){
			this.rl = (RelativeLayout) view;
		}
		
		@Override
		public boolean onMenuItemClick(MenuItem item) {
			TextView dateText = (TextView) rl.findViewById(R.id.detailDateText);
			TextView detailsText = (TextView) rl.findViewById(R.id.detailHolidayDetails);
			TextView holidayText = (TextView) rl.findViewById(R.id.detailHolidayName);
			
			switch(item.getItemId()){
			case R.id.detailsMenuOpenCalendar:
				startActivity(NlwUtil.getOpenCalendarIntent(DetailsActivity.this,Integer.parseInt(dateText.getTag()+"")));
				break;
			case R.id.detailsMenuAddEvent:
				startActivity(NlwUtil.getAddEventIntent(DetailsActivity.this,Integer.parseInt(dateText.getTag()+""), holidayText.getText()+""));
				break;
			case R.id.detailsMenuReadMore:
				startActivity(NlwUtil.getReadMoreIntent(rl.getContext(),detailsText.getTag()+""));
				break;
			case R.id.detailsMenuShareAction:
				int dateNumber = Integer.parseInt(dateText.getTag()+"");
                DateTime nlwDate = NlwUtil.getDateTimeFromNumber(DetailsActivity.this,dateNumber);
				int year = nlwDate.getYear();
				int date = nlwDate.getDayOfMonth();
				startActivity(NlwUtil.getShareDataIntent(holidayText.getText()+" on "+nlwDate.toString("MMM")+" "+date+", "+year+" - "+detailsText.getText()+". "+getResources().getString(R.string.readMoreAt)+" "+detailsText.getTag()));
				break;
			}
			
			return true;
		}
		
	}

}
