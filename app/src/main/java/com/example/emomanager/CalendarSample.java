package com.example.emomanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.Toast;

public class CalendarSample extends AppCompatActivity{
    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_sample);
        calendarView = (CalendarView) findViewById(R.id.calenderView);
        //calendarView 监听事件
        //不是onclick事件吗？
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange( CalendarView view, int year, int month, int dayOfMonth) {
                //显示用户选择的日期
                Toast.makeText(CalendarSample.this,year + "年" + month + "月" + dayOfMonth + "日",Toast.LENGTH_SHORT).show();
                Intent it = new Intent(CalendarSample.this,RecordFeelings.class);
                Bundle bd = new Bundle();
                bd.putInt("year",year);
                bd.putInt("month",month);
                bd.putInt("day",dayOfMonth);
                it.putExtras(bd);
                startActivity(it);
                Log.d("Calendar", bd.toString());
            }
        });

    }
}