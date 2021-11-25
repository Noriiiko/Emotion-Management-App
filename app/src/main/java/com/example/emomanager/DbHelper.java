package com.example.emomanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = "DbHelper";

    /**
     *  @param context
     *
     */

    public DbHelper(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.VERSION_CODE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //callback on create
        Log.d(TAG, "Create database");
        //create table
        String sql = "create table " + Constants.TABLE_NAME_1 + "(_id integer primary key, date varchar, weekday varchar, mood varchar)";

        db.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        //callback on update
        Log.d(TAG, "Update database");

        //update database by SQL


    }
}
