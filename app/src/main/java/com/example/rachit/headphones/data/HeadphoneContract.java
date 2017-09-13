package com.example.rachit.headphones.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Rachit on 09-09-2017.
 */

public final class HeadphoneContract {

    public static final String CONTENT_AUTHORITY = "com.example.rachit.headphones";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_HEADPHONE = "headphones";

    private HeadphoneContract() {};

    public static abstract class HeadphoneEntry implements BaseColumns {

        public static final String TABLE_NAME = "Headphones";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_HPHONE_IMAGE = "image";
        public static final String COLUMN_HPHONE_NAME = "name";
        public static final String COLUMN_HPHONE_DESCRIPTION = "description";
        public static final String COLUMN_HPHONE_QUANTITY = "quantity";
        public static final String COLUMN_HPHONE_PRICE = "price";


        /*Content URI*/
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_HEADPHONE);
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HEADPHONE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HEADPHONE;
    }
};
