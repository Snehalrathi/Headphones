package com.example.rachit.headphones.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.rachit.headphones.data.HeadphoneContract.HeadphoneEntry;

/**
 * Created by Rachit on 10-09-2017.
 */

public class HeadphoneDbHelper extends SQLiteOpenHelper {


    public static final String LOG_TAG = HeadphoneDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "inventory.db";
    private static final int DATABASE_VERSION = 1;

    public HeadphoneDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_HPHONES_TABLE =  "CREATE TABLE " + HeadphoneEntry.TABLE_NAME + " ("
                + HeadphoneEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HeadphoneEntry.COLUMN_HPHONE_IMAGE + " BLOB, "
                + HeadphoneEntry.COLUMN_HPHONE_NAME + " TEXT NOT NULL, "
                + HeadphoneEntry.COLUMN_HPHONE_DESCRIPTION + " TEXT, "
                + HeadphoneEntry.COLUMN_HPHONE_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                + HeadphoneEntry.COLUMN_HPHONE_PRICE + " INTEGER NOT NULL DEFAULT 0);";
        db.execSQL(SQL_CREATE_HPHONES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
