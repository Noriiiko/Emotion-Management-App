package com.example.emomanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;


public class RecordFeelings extends AppCompatActivity {
    String seleced_mode = "";
    int flag1 = 0;
    int flag2 = 0;
    int flag3 = 0;
    int flag4 = 0;
    int flag5 = 0;
    int flag6 = 0; //0是未被选中，1是被选中
    //int flags = flag1+flag2+flag3+flag4+flag5+flag6;
    private SoundPool soundPool;//音频通知声音播放器
    private int clickSoundID;//音频资源ID
    int year;
    int month;
    int day;
    String primk = "";
    FeelingsDB feelingsdb = new FeelingsDB(this);
    DiaryDatabase diaries = new DiaryDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_feelings);
        //打开数据库


        // 创建actionBar，用于返回上一级
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ImageButton btn_happy = findViewById(R.id.btn_happy);
        ImageButton btn_peace = findViewById(R.id.btn_peace);
        ImageButton btn_sad = findViewById(R.id.btn_sad);
        ImageButton btn_cry = findViewById(R.id.btn_cry);
        ImageButton btn_angry = findViewById(R.id.btn_angry);
        ImageButton btn_ill = findViewById(R.id.btn_ill);
        Button btn_addDiary = findViewById(R.id.btn_addDiary);
        Button btn_addFeelings = findViewById(R.id.btn_addFeelings);
        //读取日历中的时间
        Intent selectedtime = getIntent();
        Bundle timebd = selectedtime.getExtras();
        year = timebd.getInt("year");
        month = timebd.getInt("month");
        day = timebd.getInt("day");
        primk =String.valueOf(year)+String.valueOf(month)+String.valueOf(day);
        //从数据库里找一下有没有当天的数据，有则复原，无则无事发生。
        try {
            String sql_query = "select * from moodDB where time = ?";
            SQLiteDatabase query = feelingsdb.getReadableDatabase();
            Cursor cursor = query.rawQuery(sql_query, new String[]{primk});
            Boolean chec = true;
            if(cursor!=null){
                chec = false;
            }
            Log.d("!!cursor",chec.toString()+primk);
            String selected_f = "";

            while (cursor.moveToNext()) {
                int i = cursor.getColumnIndex("mood");
                selected_f = cursor.getString(i);
                seleced_mode = selected_f;
                Log.d("selected mood",selected_f);
                switch (selected_f) {
                    case "happy":
                        btn_happy.setActivated(true);
                        flag1=1;
                        break;
                    case "peace":
                        btn_peace.setActivated(true);
                        flag2=1;
                        break;
                    case "ill":
                        btn_ill.setActivated(true);
                        flag6=1;
                        break;
                    case "angry":
                        btn_angry.setActivated(true);
                        flag5=1;
                        break;
                    case "cry":
                        btn_cry.setActivated(true);
                        flag4=1;
                        break;
                    case "sad":
                        btn_sad.setActivated(true);
                        flag3=1;
                }

            }
        }catch(RuntimeException re){
            Log.d("feeling database","no record right now");
        }

        feelingsdb.close();

        //读取diary数据库
        TextView diary_region = findViewById(R.id.text_titleOfDiary);
        SQLiteDatabase diarydb = diaries.getReadableDatabase();
        try {
            String sql_query = "select * from diaries where time = ?";
            Cursor cursor = diarydb.rawQuery(sql_query, new String[]{primk});
            Boolean chec = true;
            if(cursor!=null){
                chec = false;
            }
            Log.d("!!diary",chec.toString()+primk);
            String content = "";

            while (cursor.moveToNext()) {
                int i = cursor.getColumnIndex("content");
                content = cursor.getString(i);
                diary_region.setText(content);
                Log.d("read previous diary","...");
            }
        }catch(RuntimeException re){
            Log.d("diary database","no record right now");
        }


        // 初始化声音
        initSound();


        // keep diary 跳转到记日记页面
        btn_addDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RecordFeelings.this, DiaryEditorActivity.class);
               //这里是把选中的时间也传过去了，记得接收
                intent.putExtra("time",primk);
                startActivityForResult(intent, 0);
            }
        });

        btn_addFeelings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                另一种对话框
//                AlertDialog.Builder builder = new AlertDialog.Builder(RecordFeelings.this);
//                builder.setTitle("title");
//                builder.setMessage("content ");
//                builder.create().show();
//                显示提示框，提示：记录心情成功
                // 添加入db，或更新
                int flags = flag1+flag2+flag3+flag4+flag5+flag6;
                Log.d("添加了，flags",""+flags);
                if(flags!=0){
                    String sql1 = "REPLACE INTO moodDB values( ? , ?)";
                    SQLiteDatabase db = feelingsdb.getWritableDatabase();
                    Log.d("before","执行到这");
                    db.execSQL(sql1,new String[]{primk,seleced_mode});
                    Log.d("after","执行到完毕");
                    db.close();
                    Toast toast = Toast.makeText(RecordFeelings.this, "Successfully Recorded", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else{
                    Toast toast = Toast.makeText(RecordFeelings.this, "Please select one mood.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }


            }
        });

        btn_happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 当点击，播放按键音
                playSound();
                switch(flag1){
                    case 0: //未被选中，需要被激活
                        if(seleced_mode.equals("")){
                            seleced_mode = "happy";
                            v.setActivated(true);
                            flag1 = 1;
                            int flags = flag1+flag2+flag3+flag4+flag5+flag6;
                            Log.d("flagsbtn",""+flag1+flags);
                        }
                        else{
                            Toast toast = Toast.makeText(RecordFeelings.this, "Only one mood can be selected.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        break;
                    case 1: //已经被选中了，现在要取消
                        seleced_mode = "";
                        v.setActivated(false);
                        flag1 = 0;
                        break;

                }
            }
        });
        btn_peace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound();
                switch(flag2){
                    case 0: //未被选中，需要被激活
                        if(seleced_mode.equals("")){
                            seleced_mode = "peace";
                            v.setActivated(true);
                            flag2 = 1;
                            int flags = flag1+flag2+flag3+flag4+flag5+flag6;
                            Log.d("flagsbtn",""+flag2+flags);
                        }
                        else{
                            Toast toast = Toast.makeText(RecordFeelings.this, "Only one mood can be selected.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        break;
                    case 1: //已经被选中了，现在要取消
                        seleced_mode = "";
                        v.setActivated(false);
                        flag2 = 0;
                        break;

                }
            }
        });
        btn_sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound();
                switch(flag3){
                    case 0: //未被选中，需要被激活
                        if(seleced_mode.equals("")){
                            seleced_mode = "sad";
                            v.setActivated(true);
                            flag3 = 1;
                            int flags = flag1+flag2+flag3+flag4+flag5+flag6;
                            flags = flag1+flag2+flag3+flag4+flag5+flag6;
                            Log.d("flagsbtn",""+flag3+flags);
                        }
                        else{
                            Toast toast = Toast.makeText(RecordFeelings.this, "Only one mood can be selected.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        break;
                    case 1: //已经被选中了，现在要取消
                        seleced_mode = "";
                        v.setActivated(false);
                        flag3 = 0;
                        break;
                }
            }
        });
        btn_cry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound();

                switch(flag4){
                    case 0: //未被选中，需要被激活
                        if(seleced_mode.equals("")){
                            seleced_mode = "cry";
                            v.setActivated(true);
                            flag4 = 1;
                            int flags = flag1+flag2+flag3+flag4+flag5+flag6;
                            Log.d("flagsbtn",""+flag4+flags);
                        }
                        else{
                            Toast toast = Toast.makeText(RecordFeelings.this, "Only one mood can be selected.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        break;
                    case 1: //已经被选中了，现在要取消
                        seleced_mode = "";
                        v.setActivated(false);
                        flag4 = 0;
                        break;
                }
            }
        });
        btn_angry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound();
                switch(flag5){
                    case 0: //未被选中，需要被激活
                        if(seleced_mode.equals("")){
                            seleced_mode = "angry";
                            v.setActivated(true);
                            flag5 = 1;
                            int flags = flag1+flag2+flag3+flag4+flag5+flag6;
                            Log.d("flagsbtn",""+flag5+flags);
                        }
                        else{
                            Toast toast = Toast.makeText(RecordFeelings.this, "Only one mood can be selected.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        break;
                    case 1: //已经被选中了，现在要取消
                        seleced_mode = "";
                        v.setActivated(false);
                        flag5 = 0;
                        break;
                }
            }
        });
        btn_ill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound();
                switch(flag6){
                    case 0: //未被选中，需要被激活
                        if(seleced_mode.equals("")){
                            seleced_mode = "ill";
                            v.setActivated(true);
                            flag6 = 1;
                            int flags = flag1+flag2+flag3+flag4+flag5+flag6;
                            Log.d("flagsbtn",""+flag6+flags);
                        }
                        else{
                            Toast toast = Toast.makeText(RecordFeelings.this, "Only one mood can be selected.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        break;
                    case 1: //已经被选中了，现在要取消
                        seleced_mode = "";
                        v.setActivated(false);
                        flag6 = 0;
                        break;

                }
            }
        });
    }

    // 增加标题栏返回键
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NewApi")
    private void initSound() {
        soundPool = new SoundPool.Builder().build();
        clickSoundID = soundPool.load(this, R.raw.click, 1);
    }

    private void playSound() {
        soundPool.play(
                clickSoundID,
                0.1f,      //0~1
                0.5f,      //0~1
                0,         //播放优先级,0最低
                0,         //循环模式（0：循环一次，-1：一直循环）
                1          //播放速度（1是正常，范围从0~2）
        );
    }
}