package com.example.rachit.headphones.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.example.rachit.headphones.data.HeadphoneContract.HeadphoneEntry;

/**
 * Created by Rachit on 09-09-2017.
 */

public class HeadphoneProvider extends ContentProvider {

    public static final String LOG_TAG = HeadphoneProvider.class.getSimpleName();
    private static final int HEADPHONES = 100;
    private static final int HEADPHONE_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private HeadphoneDbHelper mDbHelper;

    static {
        sUriMatcher.addURI(HeadphoneContract.CONTENT_AUTHORITY, HeadphoneContract.PATH_HEADPHONE, HEADPHONES);
        sUriMatcher.addURI(HeadphoneContract.CONTENT_AUTHORITY, HeadphoneContract.PATH_HEADPHONE + "/#", HEADPHONE_ID);
    }



    @Override
    public boolean onCreate() {
        mDbHelper = new HeadphoneDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case HEADPHONES:
                cursor = database.query(HeadphoneEntry.TABLE_NAME, projection, null, null,
                        null, null, null);
                break;
            case HEADPHONE_ID:
                selection = HeadphoneEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(HeadphoneEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case HEADPHONES:
                return HeadphoneEntry.CONTENT_LIST_TYPE;
            case HEADPHONE_ID:
                return HeadphoneEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case HEADPHONES:
                return insertPet(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertPet(Uri uri, ContentValues values) {

        // Check that the name is not null
        String name = values.getAsString(HeadphoneEntry.COLUMN_HPHONE_NAME);
        Log.i(LOG_TAG, name);
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Headphone requires a name");
        }

        // If the weight is provided, check that it's greater than or equal to 0 kg
        Integer price = values.getAsInteger(HeadphoneEntry.COLUMN_HPHONE_PRICE);
        if (price != null && price < 0) {
            throw new IllegalArgumentException("Headphone requires valid weight");
        }


        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(HeadphoneEntry.TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int rows_deleted;
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case HEADPHONES:
                // Delete all rows that match the selection and selection args
                rows_deleted = database.delete(HeadphoneEntry.TABLE_NAME, selection, selectionArgs);
                if (rows_deleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);}
                return rows_deleted;
            case HEADPHONE_ID:
                // Delete a single row given by the ID in the URI
                selection = HeadphoneEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rows_deleted = database.delete(HeadphoneEntry.TABLE_NAME, selection, selectionArgs);
                if (rows_deleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);}
                return rows_deleted;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update(Uri uri,
                      ContentValues values, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case HEADPHONES:
                return updateHeadphone(uri, values, selection, selectionArgs);
            case HEADPHONE_ID:
                selection = HeadphoneEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateHeadphone(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateHeadphone(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(HeadphoneEntry.COLUMN_HPHONE_NAME)) {
            String name = values.getAsString(HeadphoneEntry.COLUMN_HPHONE_NAME);
            if (TextUtils.isEmpty(name)) {
                throw new IllegalArgumentException("Headphone requires a name");
            }
        }

        if (values.containsKey(HeadphoneEntry.COLUMN_HPHONE_DESCRIPTION)) {
            String description = values.getAsString(HeadphoneEntry.COLUMN_HPHONE_DESCRIPTION);
            if (TextUtils.isEmpty(description)) {
                throw new IllegalArgumentException("Headphone requires a description");
            }
        }


        // If the {@link PetEntry#COLUMN_PET_WEIGHT} key is present,
        // check that the weight value is valid.
        if (values.containsKey(HeadphoneEntry.COLUMN_HPHONE_PRICE)) {
            // Check that the weight is greater than or equal to 0 kg
            Integer price = values.getAsInteger(HeadphoneEntry.COLUMN_HPHONE_PRICE);
            if (price != null && price < 0) {
                throw new IllegalArgumentException("Description requires valid weight");
            }
        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int updated_rows = database.update(HeadphoneEntry.TABLE_NAME,values,selection,selectionArgs);

        return updated_rows;
    }

}
