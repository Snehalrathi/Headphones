package com.example.rachit.headphones.data;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.rachit.headphones.R;
import com.example.rachit.headphones.data.HeadphoneContract.HeadphoneEntry;

/**
 * Created by Rachit on 13-09-2017.
 */

public class HeadphoneCursorAdapter extends CursorAdapter {

    public HeadphoneCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView headphoneName = (TextView) view.findViewById(R.id.name);
        TextView headphoneSummary = (TextView) view.findViewById(R.id.summary);
        // Extract properties from cursor
        String hName = cursor.getString(cursor.getColumnIndex(HeadphoneEntry.COLUMN_HPHONE_NAME));
        String hDescription = cursor.getString(cursor.getColumnIndex(HeadphoneEntry.COLUMN_HPHONE_DESCRIPTION));
        if (TextUtils.isEmpty(hDescription))
        {
            hDescription = "UNKNOWN";
        }
        // Populate fields with extracted properties
        headphoneName.setText(hName);
        headphoneSummary.setText(hDescription);
    }
}
