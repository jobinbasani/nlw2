package com.jobinbasani.nlw.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.jobinbasani.nlw.sql.NlwDataContract;
import com.jobinbasani.nlw.sql.NlwDataDbHelper;
import com.jobinbasani.nlw.util.NlwUtil;

/**
 * Created by jobin.basani on 1/29/2015.
 */
public class NlwProvider extends ContentProvider {

    private NlwDataDbHelper dbHelper;
    private static UriMatcher URI_MATCHER;
    private static final int NLW = 1;
    private static final int NLW_LIST = 2;
    private static final int NLW_LIST_PAST = 3;
    private static final int NLW_STATS = 4;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(NlwDataContract.AUTHORITY,NlwDataContract.NLW,NLW);
        URI_MATCHER.addURI(NlwDataContract.AUTHORITY,NlwDataContract.NLW_LIST,NLW_LIST);
        URI_MATCHER.addURI(NlwDataContract.AUTHORITY,NlwDataContract.NLW_LIST_PAST,NLW_LIST_PAST);
        URI_MATCHER.addURI(NlwDataContract.AUTHORITY,NlwDataContract.NLW_STATS,NLW_STATS);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new NlwDataDbHelper(getContext());
        return true;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int rowsAdded = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try{
            for(ContentValues value:values){
                db.insert(NlwDataContract.NlwDataEntry.TABLE_NAME, null, value);
                rowsAdded++;
            }
            db.setTransactionSuccessful();
        }catch(Exception e){

        }finally{
            db.endTransaction();
        }
        return rowsAdded;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        int uriType = URI_MATCHER.match(uri);
        switch (uriType){
            case NLW:
                return dbHelper.getReadableDatabase().rawQuery(new StringBuilder()
                        .append("SELECT * FROM ")
                        .append(NlwDataContract.NlwDataEntry.TABLE_NAME)
                        .append(" WHERE ")
                        .append(NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWDATE)
                        .append(">? AND ")
                        .append(NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWCOUNTRY)
                        .append("=? ORDER BY ").append(NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWDATE)
                        .append(" LIMIT 1")
                        .toString(), selectionArgs);
            case NLW_LIST:
                return dbHelper.getReadableDatabase().rawQuery(new StringBuilder()
                        .append("SELECT * FROM ")
                        .append(NlwDataContract.NlwDataEntry.TABLE_NAME)
                        .append(" WHERE ")
                        .append(NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWCOUNTRY)
                        .append("=? AND ")
                        .append(NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWDATE)
                        .append(">? ORDER BY ")
                        .append(NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWDATE)
                        .toString(), selectionArgs);
            case NLW_LIST_PAST:
                return dbHelper.getReadableDatabase().rawQuery(new StringBuilder()
                        .append("SELECT * FROM ")
                        .append(NlwDataContract.NlwDataEntry.TABLE_NAME)
                        .append(" WHERE ")
                        .append(NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWCOUNTRY)
                        .append("=? AND ")
                        .append(NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWDATE)
                        .append("<? ORDER BY ")
                        .append(NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWDATE)
                        .append(" DESC ")
                        .toString(), selectionArgs);
            case NLW_STATS:
                return dbHelper.getReadableDatabase().rawQuery(new StringBuilder()
                        .append("select maxdata.country,maxdata.date,countdata.count from (select max(date) as date, ")
                        .append("country from nlwentry group by country) maxdata ")
                        .append("left join (select count(*)as count,country as c_country from nlwentry where date>? group by country) countdata ")
                        .append("on maxdata.country=countdata.c_country")
                        .toString(),selectionArgs);
        }

        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return dbHelper.getWritableDatabase().delete(NlwDataContract.NlwDataEntry.TABLE_NAME, "date<?", selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
