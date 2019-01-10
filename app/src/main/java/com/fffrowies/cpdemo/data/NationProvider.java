package com.fffrowies.cpdemo.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import static com.fffrowies.cpdemo.data.NationContract.CONTENT_AUTHORITY;
import static com.fffrowies.cpdemo.data.NationContract.NationEntry.TABLE_NAME;
import static com.fffrowies.cpdemo.data.NationContract.PATH_COUNTRIES;

public class NationProvider extends ContentProvider {

    private static final String TAG = NationProvider.class.getSimpleName();

    private NationDbHelper databaseHelper;

    // constants for the operation:
    // For whole table
    private static final int COUNTRIES = 1;
    // For a specific row in a table identified by COUNTRY_NAME
    private static final int COUNTRIES_COUNTRY_NAME = 2;
    // For a specific row in a table identified by _ID
    private static final int COUNTRIES_ID = 3;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // content://com.fffrowies.cpdemo.data.NationProvider/countries
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_COUNTRIES, COUNTRIES);

        // content://com.fffrowies.cpdemo.data.NationProvider/countries/#
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_COUNTRIES + "/#", COUNTRIES_ID);

        // content://com.fffrowies.cpdemo.data.NationProvider/countries/*
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_COUNTRIES + "/*", COUNTRIES_COUNTRY_NAME);
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new NationDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor cursor;

        switch (uriMatcher.match(uri)) {
            case COUNTRIES:
                cursor = database.query(
                        TABLE_NAME,         // the table name
                        projection,         // the columns to return
                        null,      // Selection: WHERE clause OR the condition
                        null,   // Selection arguments for the WHERE clause
                        null,       // don't group the rows
                        null,        // don't filter by row groups
                        sortOrder           // the sort order
                );
                break;
            case COUNTRIES_ID:
                selection = NationContract.NationEntry._ID + " = ? ";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = database.query(
                        TABLE_NAME,         // the table name
                        projection,         // the columns to return
                        selection,          // Selection: WHERE clause OR the condition
                        selectionArgs,      // Selection arguments for the WHERE clause
                        null,       // don't group the rows
                        null,        // don't filter by row groups
                        sortOrder           // the sort order
                );
                break;
            default:
                throw new IllegalArgumentException(TAG + " Insert unknown URI: " + uri);
        }

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        switch (uriMatcher.match(uri)) {
            case COUNTRIES:
                return insertRecord(uri, values, TABLE_NAME);
            default:
                throw new IllegalArgumentException(TAG + "Insert unknown URI: " + uri);
        }
    }

    private Uri insertRecord(Uri uri, ContentValues values, String tableName) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        long rowId = database.insert(tableName, null, values);

        if (rowId == -1) {
            Log.e(TAG, "Insert error for URI " + uri);
            return null;
        }

        return ContentUris.withAppendedId(uri, rowId);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {
        return 0;
    }
}
