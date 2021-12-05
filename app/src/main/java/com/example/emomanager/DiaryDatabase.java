package com.example.emomanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DiaryDatabase extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "diaries";
    public static final String ID = "_id";      //这个不知道是什么，id也行吧
    public static final String CONTENT = "content";     // 日记本体+字体（最好是）
    public static final String TIME = "time";       //时间，很重要，每天只能有一条！
    public static final String FONT = "font";


    public DiaryDatabase(@Nullable Context context) {
        super(context, "diaries", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {       //创建数据库？
        /*db.execSQL("CREATE TABLE " + TABLE_NAME
                +"("
                +ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                +CONTENT + " TEXT NOT NULL,"
                +TIME + "TEXT NOT NULL,"
                +MODE + " INTEGER DEFAULT 1)"
        );*/
        db.execSQL("CREATE TABLE diaries (time TEXT NOT NULL PRIMARY KEY, content TEXT NOT NULL,font TEXT)");
                //目前想的是日记需要包括字体和时间，以时间为key查找
               // +MODE + " INTEGER DEFAULT 1)"

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
