package com.jobinbasani.nlw.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.jobinbasani.nlw.R;
import com.jobinbasani.nlw.sql.NlwDataContract;
import com.jobinbasani.nlw.util.NlwUtil;

import org.joda.time.DateTime;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class WeekendList extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String LIST_TYPE = "listType";
    private static final int PAST_LOADER_ID = 3;
    private static final int UPCOMING_LOADER_ID = 4;

    private String listType;
    private int baseTime;
    private String country;

    private OnFragmentInteractionListener mListener;

    public static WeekendList newInstance(String listType) {
        WeekendList fragment = new WeekendList();
        Bundle args = new Bundle();
        args.putString(LIST_TYPE, listType);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WeekendList() {
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(R.color.bgGreenColor));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listType = getArguments().getString(LIST_TYPE);
        }

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
            baseTime = mListener.getDateNumber();
            country = mListener.getCountry();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void loadData(int loaderId){
        if(getLoaderManager().getLoader(loaderId)!=null){
            getLoaderManager().restartLoader(loaderId,null,this);
        }else{
            getLoaderManager().initLoader(loaderId,null,this);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(listType.equals(getResources().getString(R.string.title_section1).toUpperCase())){
            loadData(UPCOMING_LOADER_ID);
        }else{
            loadData(PAST_LOADER_ID);
        }
    }

    private void setListData(Cursor cursor){
        String[] from = new String[] { NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWDATE, NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWNAME, NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWTEXT, NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWDATE, NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWDATE, NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWDATE };
        int[] to = new int[] { R.id.detailDateText, R.id.detailHolidayName, R.id.detailHolidayDetails, R.id.detailYearText, R.id.detailMonthText, R.id.overflowIcon};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.nlw_details, cursor, from, to, SimpleCursorAdapter.NO_SELECTION);
        adapter.setViewBinder(new DetailsViewBinder());
        setListAdapter(adapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] selectionArgs = new String[]{mListener.getCountry(),baseTime+""};
        return (id==UPCOMING_LOADER_ID?new CursorLoader(getActivity(), NlwDataContract.CONTENT_URI_NLW_LIST,null,null,selectionArgs,null):
                new CursorLoader(getActivity(), NlwDataContract.CONTENT_URI_NLW_LIST_PAST,null,null,selectionArgs,null));
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public String getCountry();
        public int getDateNumber();
    }

    private class DetailsViewBinder implements SimpleCursorAdapter.ViewBinder {

        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
            if(view.getId() == R.id.detailDateText){
                TextView dateText = (TextView) view;
                int dateNumber = cursor.getInt(columnIndex);
                DateTime date = NlwUtil.getDateTimeFromNumber(getActivity(),dateNumber);
                dateText.setText(date.getDayOfMonth()+"");
                dateText.setTag(dateNumber+"");
                return true;
            }else if(view.getId() == R.id.detailMonthText){
                TextView monthText = (TextView) view;
                DateTime date = NlwUtil.getDateTimeFromNumber(getActivity(),cursor.getInt(columnIndex));
                monthText.setText(date.toString("MMM"));
                return true;
            }else if(view.getId() == R.id.detailYearText){
                TextView yearText = (TextView) view;
                DateTime date = NlwUtil.getDateTimeFromNumber(getActivity(),cursor.getInt(columnIndex));
                yearText.setText(date.getYear()+"");
                return true;
            }else if(view.getId() == R.id.detailHolidayDetails){
                TextView detailsText = (TextView) view;
                detailsText.setText(cursor.getString(columnIndex)+"");
                detailsText.setTag(cursor.getString(cursor.getColumnIndexOrThrow(NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWWIKI)));
                return true;
            }else if(view.getId() == R.id.overflowIcon){
                final PopupMenu popupMenu = new PopupMenu(getActivity(), view);
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

    private class DetailsMenuClick implements PopupMenu.OnMenuItemClickListener {

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
                    startActivity(NlwUtil.getOpenCalendarIntent(getActivity(),Integer.parseInt(dateText.getTag()+"")));
                    break;
                case R.id.detailsMenuAddEvent:
                    startActivity(NlwUtil.getAddEventIntent(getActivity(),Integer.parseInt(dateText.getTag()+""), holidayText.getText()+""));
                    break;
                case R.id.detailsMenuReadMore:
                    startActivity(NlwUtil.getReadMoreIntent(rl.getContext(),detailsText.getTag()+""));
                    break;
                case R.id.detailsMenuShareAction:
                    int dateNumber = Integer.parseInt(dateText.getTag()+"");
                    DateTime nlwDate = NlwUtil.getDateTimeFromNumber(getActivity(),dateNumber);
                    int year = nlwDate.getYear();
                    int date = nlwDate.getDayOfMonth();
                    startActivity(NlwUtil.getShareDataIntent(holidayText.getText()+" on "+nlwDate.toString("MMM")+" "+date+", "+year+" - "+detailsText.getText()+". "+getResources().getString(R.string.readMoreAt)+" "+detailsText.getTag()));
                    break;
            }

            return true;
        }

    }

}
