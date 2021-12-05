package com.example.emomanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;

public class FeelingsDB extends SQLiteOpenHelper {

    private static final String TAG = "DbHelper";
    private static final String TIME = "time";
    private static final String MOOD = "feelings";
    private static final String DBNAME = "moodDB";
    /**
     *  @param context
     *
     */

    public FeelingsDB(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //callback on create
        Log.d(TAG, "Create feelings database");
        //create table
        String sql = "create table moodDB (time VARCHAR primary key, mood VARCHAR)" ;
                //只有mode和time的世界

        db.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        //callback on update
        Log.d(TAG, "Update feelings database");

        //update database by SQL


    }
}
