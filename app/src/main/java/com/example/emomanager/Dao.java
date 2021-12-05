package com.example.emomanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class Dao {

    private final FeelingsDB mHelper;

    public Dao(Context context){

        //Create Database
        mHelper = new FeelingsDB(context);
    }

    public void insertDailyMood(String date, String weekday, String mood){

        SQLiteDatabase db = mHelper.getWritableDatabase();

        String sql = "insert into " + Constants.TABLE_NAME_1 + "(date, weekday, mood) values(?,?,?)";
        db.execSQL(sql, new Object[]{date, weekday, mood});
        db.close();




    }
    public void deleteDailyMood(String date){

        SQLiteDatabase db = mHelper.getWritableDatabase();

        String sql = "delete from " + Constants.TABLE_NAME_1 + "where date = "+ date;
        db.execSQL(sql);
        db.close();

    }
    public void updateMood(String date, String newMood){

        SQLiteDatabase db = mHelper.getWritableDatabase();

        String sql = "update " + Constants.TABLE_NAME_1 + "set mood = " + newMood + "where date ="+ date;
        db.execSQL(sql);
        db.close();

    }
    public void query(){

//        SQLiteDatabase db = mHelper.getWritableDatabase();
//
//        String sql = "select * from " + Constants.TABLE_NAME_1 ;
//        Cursor cursor = db.rawQuery(sql, null);
//
//        while(cursor.moveToNext()){
//
//        }
//        db.close();


    }

}
