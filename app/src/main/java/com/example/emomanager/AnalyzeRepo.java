package com.example.emomanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AnalyzeRepo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze_repo);

        FeelingsDB helper = new FeelingsDB(this);
        helper.getWritableDatabase();
    }
}