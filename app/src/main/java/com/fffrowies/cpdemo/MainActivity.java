package com.fffrowies.cpdemo;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fffrowies.cpdemo.data.NationDbHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private EditText etCountry, etContinent, etWhereToUpdate, etNewContinent, etWhereToDelete, etQueryRowById;
	private Button btnInsert, btnUpdate, btnDelete, btnQueryRowById, btnDisplayAll;

	private static final String TAG = MainActivity.class.getSimpleName();

	private SQLiteDatabase database;
	private NationDbHelper databaseHelper;

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

		databaseHelper = new NationDbHelper(this);
		database = databaseHelper.getWritableDatabase();    // READ/WRITE
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

	}

	private void update() {

	}

	private void delete() {

	}

	private void queryRowById() {

	}

	private void queryAndDisplayAll() {

	}

    @Override
    protected void onDestroy() {
	    database.close();           // Close database connection
        super.onDestroy();
    }
}
