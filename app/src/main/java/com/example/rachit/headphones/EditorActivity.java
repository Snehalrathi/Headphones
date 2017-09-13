package com.example.rachit.headphones;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rachit.headphones.data.HeadphoneContract.HeadphoneEntry;
import com.example.rachit.headphones.data.HeadphoneDbHelper;

/**
 * Created by Rachit on 09-09-2017.
 */

public class EditorActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mEditImage;
    private EditText mDescriptionText;
    private EditText mQuantity;
    private EditText mPrice;
    private HeadphoneDbHelper mDbHelper;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mDbHelper = new HeadphoneDbHelper(this);

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_headphone_name);
        mDescriptionText = (EditText) findViewById(R.id.edit_pet_description);
        mPrice = (EditText) findViewById(R.id.edit_headphone_price);

    };

    public void saveHeadphone() {

        String nameString = mNameEditText.getText().toString().trim();
        String descriptionString = mDescriptionText.getText().toString().trim();
        String priceString = mPrice.getText().toString();
        int price = Integer.parseInt(priceString);

        if (TextUtils.isEmpty(nameString) == true && TextUtils.isEmpty(descriptionString) == true &&
                TextUtils.isEmpty(priceString) == true) {
            return;
        } else {
            ContentValues values = new ContentValues();
            values.put(HeadphoneEntry.COLUMN_HPHONE_NAME, nameString);
            values.put(HeadphoneEntry.COLUMN_HPHONE_DESCRIPTION, descriptionString);
            values.put(HeadphoneEntry.COLUMN_HPHONE_PRICE, price);


            Uri newUri = getContentResolver().insert(HeadphoneEntry.CONTENT_URI, values);

            if (newUri == null) {
                Toast.makeText(this, "Error in updating Headphone", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Headphone Updated!", Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_save:
                saveHeadphone();
                finish();
                return true;
            case R.id.action_delete:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
