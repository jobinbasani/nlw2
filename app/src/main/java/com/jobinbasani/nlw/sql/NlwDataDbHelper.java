package com.jobinbasani.nlw.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jobinbasani.nlw.generators.NlwGeneratorI;
import com.jobinbasani.nlw.sql.NlwDataContract.NlwDataEntry;
import com.jobinbasani.nlw.util.NlwUtil;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class NlwDataDbHelper extends SQLiteOpenHelper {
	
	private Context context;
    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "NlwData.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + NlwDataEntry.TABLE_NAME + " (" +
        		NlwDataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
        		NlwDataEntry.COLUMN_NAME_NLWDATE + INT_TYPE + COMMA_SEP +
        		NlwDataEntry.COLUMN_NAME_NLWCOUNTRY + TEXT_TYPE + COMMA_SEP +
        		NlwDataEntry.COLUMN_NAME_NLWNAME + TEXT_TYPE + COMMA_SEP +
        		NlwDataEntry.COLUMN_NAME_NLWWIKI + TEXT_TYPE + COMMA_SEP +
        		NlwDataEntry.COLUMN_NAME_NLWTEXT + TEXT_TYPE + 
        " )";
    private static final String SQL_DELETE_ENTRIES =
            new StringBuilder()
                    .append("DROP TABLE IF EXISTS ")
                    .append(NlwDataEntry.TABLE_NAME)
                    .toString();

	public NlwDataDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(SQL_CREATE_ENTRIES);

        List<ContentValues> valueList = new ArrayList<>();
        DateTime start = new DateTime();
        DateTime end = start.plusYears(1);
        for(NlwGeneratorI generator: NlwUtil.getGenerators(this.context)){
            generator.fillLongWeekends(valueList,start,end);
        }

        for(ContentValues values:valueList){
            db.insert(
                    NlwDataEntry.TABLE_NAME,
                    null,
                    values);
        }
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}

}
