package com.example.rachit.headphones;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.rachit.headphones.data.HeadphoneContract;
import com.example.rachit.headphones.data.HeadphoneCursorAdapter;

public class MainActivity extends AppCompatActivity {

    private HeadphoneCursorAdapter mCursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        String[] projection = {HeadphoneContract.HeadphoneEntry._ID,
                HeadphoneContract.HeadphoneEntry.COLUMN_HPHONE_NAME,
                HeadphoneContract.HeadphoneEntry.COLUMN_HPHONE_DESCRIPTION,
                HeadphoneContract.HeadphoneEntry.COLUMN_HPHONE_QUANTITY,
                HeadphoneContract.HeadphoneEntry.COLUMN_HPHONE_PRICE};

        // Find ListView to populate
        ListView petListView = (ListView) findViewById(R.id.list);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        //View emptyView = findViewById(R.id.empty_view);
        //petListView.setEmptyView(emptyView);

        // Setup cursor adapter using cursor from last step
        Cursor cursor = getContentResolver().query(HeadphoneContract.HeadphoneEntry.CONTENT_URI, projection, null, null, null);
        mCursorAdapter = new HeadphoneCursorAdapter(this, cursor);
        // Attach cursor adapter to the ListView
        petListView.setAdapter(mCursorAdapter);

/*        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                Uri currentPetUri = ContentUris.withAppendedId(HeadphoneContract.HeadphoneEntry.CONTENT_URI, id);
                intent.setData(currentPetUri);
                startActivity(intent);
            }
        });*/

        //mDbHelper = new HeadphoneDbHelper(this);

        //displayDatabaseInfo();

    }

    /*private void displayDatabaseInfo() {

        String [] projection = {HeadphoneContract.HeadphoneEntry._ID,
                HeadphoneContract.HeadphoneEntry.COLUMN_HPHONE_NAME,
                HeadphoneContract.HeadphoneEntry.COLUMN_HPHONE_DESCRIPTION,
                HeadphoneContract.HeadphoneEntry.COLUMN_HPHONE_QUANTITY,
                HeadphoneContract.HeadphoneEntry.COLUMN_HPHONE_PRICE};

        Cursor cursor = getContentResolver().query(HeadphoneContract.HeadphoneEntry.CONTENT_URI,projection,null,null,null);

        try {
            TextView displayView = (TextView) findViewById(R.id.text_view_headphone);
            displayView.setText("Number of rows in pets database table: " + cursor.getCount() + "\n\n");

            displayView.append(HeadphoneContract.HeadphoneEntry._ID + "-" + HeadphoneContract.HeadphoneEntry.COLUMN_HPHONE_NAME + "-"
                    + HeadphoneContract.HeadphoneEntry.COLUMN_HPHONE_DESCRIPTION + "-" + HeadphoneContract.HeadphoneEntry.COLUMN_HPHONE_PRICE + "\n");

            int idColumnIndex = cursor.getColumnIndex(HeadphoneContract.HeadphoneEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HeadphoneContract.HeadphoneEntry.COLUMN_HPHONE_NAME);
            int descriptionColumnIndex = cursor.getColumnIndex(HeadphoneContract.HeadphoneEntry.COLUMN_HPHONE_DESCRIPTION);
            int priceColumnIndex = cursor.getColumnIndex(HeadphoneContract.HeadphoneEntry.COLUMN_HPHONE_PRICE);

            while(cursor.moveToNext()){
                int currentId = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentDescription = cursor.getString(descriptionColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);

                displayView.append("\n" + currentId + "-" + currentName + "-" + currentDescription
                        + "-" + currentPrice);
            }

        } finally {
            cursor.close();
        }

    }*/

    public void insertPet() {

        ContentValues mNewValues = new ContentValues();

        mNewValues.put(HeadphoneContract.HeadphoneEntry.COLUMN_HPHONE_NAME, "Sony");
        mNewValues.put(HeadphoneContract.HeadphoneEntry.COLUMN_HPHONE_IMAGE, R.mipmap.ic_launcher);
        mNewValues.put(HeadphoneContract.HeadphoneEntry.COLUMN_HPHONE_DESCRIPTION, "Wireless Bluetooth");
        mNewValues.put(HeadphoneContract.HeadphoneEntry.COLUMN_HPHONE_QUANTITY, 4);
        mNewValues.put(HeadphoneContract.HeadphoneEntry.COLUMN_HPHONE_PRICE, 380);

        Uri mNewUri = getContentResolver().insert(HeadphoneContract.HeadphoneEntry.CONTENT_URI, mNewValues);

    }

/*    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertPet();
                //displayDatabaseInfo();
                return true;

            case R.id.action_delete_all_entries:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
