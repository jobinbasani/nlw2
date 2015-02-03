package com.jobinbasani.nlw.sql;

import android.net.Uri;
import android.provider.BaseColumns;

import com.jobinbasani.nlw.provider.NlwProvider;

public final class NlwDataContract  {

    public static final String AUTHORITY = NlwProvider.class.getPackage().getName();
    public static final String NLW = "nlw";
    public static final String NLW_LIST = "nlwlist";
    public static final String NLW_LIST_PAST = "nlwlistPast";
    public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/");
    public static final Uri CONTENT_URI_NLW = Uri.withAppendedPath(CONTENT_URI, NLW);
    public static final Uri CONTENT_URI_NLW_LIST = Uri.withAppendedPath(CONTENT_URI, NLW_LIST);
    public static final Uri CONTENT_URI_NLW_LIST_PAST = Uri.withAppendedPath(CONTENT_URI, NLW_LIST_PAST);

	public static abstract class NlwDataEntry implements BaseColumns {
        public static final String TABLE_NAME = "nlwentry";
        public static final String COLUMN_NAME_NLWDATE = "date";
        public static final String COLUMN_NAME_NLWNAME = "name";
        public static final String COLUMN_NAME_NLWTEXT = "text";
        public static final String COLUMN_NAME_NLWWIKI = "wiki";
        public static final String COLUMN_NAME_NLWCOUNTRY = "country";
    }

}
