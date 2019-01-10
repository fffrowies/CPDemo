package com.fffrowies.cpdemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fffrowies.cpdemo.data.NationContract.NationEntry;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private EditText etCountry, etContinent, etWhereToUpdate, etNewContinent, etWhereToDelete, etQueryRowById;
	private Button btnInsert, btnUpdate, btnDelete, btnQueryRowById, btnDisplayAll;

	private static final String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		etCountry 		= findViewById(R.id.etCountry);
		etContinent 	= findViewById(R.id.etContinent);
		etWhereToUpdate = findViewById(R.id.etWhereToUpdate);
		etNewContinent 	= findViewById(R.id.etUpdateContinent);
		etQueryRowById 	= findViewById(R.id.etQueryByRowId);
		etWhereToDelete = findViewById(R.id.etWhereToDelete);

		btnInsert 		= findViewById(R.id.btnInsert);
		btnUpdate 		= findViewById(R.id.btnUpdate);
		btnDelete 		= findViewById(R.id.btnDelete);
		btnQueryRowById = findViewById(R.id.btnQueryByID);
		btnDisplayAll 	= findViewById(R.id.btnDisplayAll);

		btnInsert.setOnClickListener(this);
		btnUpdate.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		btnQueryRowById.setOnClickListener(this);
		btnDisplayAll.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

			case R.id.btnInsert:
				insert();
				break;

			case R.id.btnUpdate:
				update();
				break;

			case R.id.btnDelete:
				delete();
				break;

			case R.id.btnQueryByID:
				queryRowById();
				break;

			case R.id.btnDisplayAll:
				queryAndDisplayAll();
				break;
		}
	}

	private void insert() {

	    String countryName = etCountry.getText().toString();
	    String continentName = etContinent.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NationEntry.COLUMN_COUNTRY, countryName);
        contentValues.put(NationEntry.COLUMN_CONTINENT, continentName);

		Uri uri = NationEntry.CONTENT_URI;
		Uri uriRowInserted = getContentResolver().insert(uri, contentValues);
		Log.i(TAG, "Items inserted at: " + uriRowInserted);
	}

	private void update() {

	    String newContinent = etNewContinent.getText().toString();
	    String whereCountry = etWhereToUpdate.getText().toString();

	    ContentValues contentValues = new ContentValues();
	    contentValues.put(NationEntry.COLUMN_CONTINENT, newContinent);

	    String selection = NationEntry.COLUMN_COUNTRY + " = ?";
	    String[] selectionArgs = { whereCountry };              // WHERE country = Japan (i.e.)

        Uri uri = NationEntry.CONTENT_URI;
        Log.i(TAG, ""+uri);
        int rowsUpdated = getContentResolver().update(uri, contentValues, selection, selectionArgs);
        Log.i(TAG, "Number of rows updated: " + rowsUpdated);
    }

	private void delete() {

	    String countryName = etWhereToDelete.getText().toString();

	    String selection = NationEntry.COLUMN_COUNTRY + " = ? ";
	    String[] selectionArgs = { countryName };               // WHERE country = "Japan" (i.e.)

        Uri uri = Uri.withAppendedPath(NationEntry.CONTENT_URI, countryName);
        Log.i(TAG, ""+uri);
        int rowsDeleted = getContentResolver().delete(uri, selection, selectionArgs);
        Log.i(TAG, "Number of rows deleted: " + rowsDeleted);
    }

	private void queryRowById() {

	    String rowId = etQueryRowById.getText().toString();

        String[] projection = {
                NationEntry._ID,
                NationEntry.COLUMN_COUNTRY,
                NationEntry.COLUMN_CONTINENT
        };

        // Filter results. Make these null if you want to query all rows
        String selection = NationEntry._ID + " = ? ";   // _id = ?
        String[] selectionArgs = { rowId };             // replace '?' by rowId in runtime

        String sortOrder = null;  // ascending or descending...

        Uri uri = Uri.withAppendedPath(NationEntry.CONTENT_URI, rowId);
        Log.i(TAG, ""+uri);
        Cursor cursor = getContentResolver()
                .query(uri, projection, selection, selectionArgs, sortOrder);

        if (cursor != null && cursor.moveToNext()) {

            String str = "";

            String[] columns = cursor.getColumnNames();
            for (String column : columns) {
                str += "\t" + cursor.getString(cursor.getColumnIndex(column));
            }
            str += "\n";

            cursor.close();
            Log.i(TAG, str);
        }
	}

	private void queryAndDisplayAll() {

	    String[] projection = {
	            NationEntry._ID,
	            NationEntry.COLUMN_COUNTRY,
                NationEntry.COLUMN_CONTINENT
        };

	    // Filter results. Make these null if you want to query all rows
	    String selection = null;
	    String[] selectionArgs = null;

	    String sortOrder = null;  // ascending or descending...

        Uri uri = NationEntry.CONTENT_URI;
        Log.i(TAG, ""+uri);
        Cursor cursor = getContentResolver()
                .query(uri, projection, selection, selectionArgs, sortOrder);

        if (cursor != null) {

            String str = "";
            while (cursor.moveToNext()) {       // Cursor iterates through all rows

                String[] columns = cursor.getColumnNames();
                for (String column : columns) {
                    str += "\t" + cursor.getString(cursor.getColumnIndex(column));
                }
                str += "\n";
            }

            cursor.close();
            Log.i(TAG, str);
        }
	}
}
