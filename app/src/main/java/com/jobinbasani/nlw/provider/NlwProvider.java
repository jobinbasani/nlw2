package com.jobinbasani.nlw.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
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

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(NlwDataContract.AUTHORITY,NlwDataContract.NLW,NLW);
        URI_MATCHER.addURI(NlwDataContract.AUTHORITY,NlwDataContract.NLW_LIST,NLW_LIST);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new NlwDataDbHelper(getContext());
        return true;
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
                        .append(" LIMIT 1").toString(), selectionArgs);
            case NLW_LIST:
                return dbHelper.getReadableDatabase().rawQuery("SELECT * FROM "+ NlwDataContract.NlwDataEntry.TABLE_NAME+" WHERE "+ NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWCOUNTRY+"=? AND "+ NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWDATE+">? ORDER BY "+ NlwDataContract.NlwDataEntry.COLUMN_NAME_NLWDATE, selectionArgs);
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
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
