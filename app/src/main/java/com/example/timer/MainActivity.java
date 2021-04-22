package com.example.timer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {


    TextView lastData, time;
    EditText typeName;
    ImageView play, pause, stop;


    private final String KEY_TIME = "KEY_TIME";
    private final String KEY_NAME = "KEY_NAME";
    private final String KEY_STATUS = "KEY_STATUS";

    public  long playTime;
    public  long pauseTime;
    public  boolean playIng = false;
    public  String newTime;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main_port);
        } else {
            setContentView(R.layout.activity_main_land);
        }


        lastData = findViewById(R.id.lastData);
        time = findViewById(R.id.time);
        typeName = findViewById(R.id.type_val);
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        stop = findViewById(R.id.stop);


        String lastTime = SharedPreferencesUtil.getInstance(this).getString(KEY_TIME);
        String lastName = SharedPreferencesUtil.getInstance(this).getString(KEY_NAME);

        if(lastName.equals("")){
            lastName = "00:00:00";
        }

        lastData.setText("You spent " + lastTime + " on " + lastName + " last time.");

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playIng = false;
                pauseTime += System.currentTimeMillis() - playTime;
                playTime = 0;
                Toast.makeText(MainActivity.this, "Paused", Toast.LENGTH_SHORT).show();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferencesUtil.getInstance(MainActivity.this).putSP(KEY_TIME, newTime);
                SharedPreferencesUtil.getInstance(MainActivity.this).putSP(KEY_NAME, typeName.getText().toString().trim());

                playIng = false;
                playTime = 0;
                pauseTime = 0;
                newTime = null;

                Toast.makeText(MainActivity.this, "Saved success", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void play() {

        if (typeName.getText().toString().trim().length() == 0) {
            Toast.makeText(MainActivity.this, "Please enter your workout type", Toast.LENGTH_SHORT).show();
            return;
        }

        if (playIng) {
            Toast.makeText(MainActivity.this, "Starting", Toast.LENGTH_SHORT).show();
            return;
        }

        start();

    }



    private void start() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                if (playTime == 0) {

                    playTime = System.currentTimeMillis();
                }

                playIng = true;
                long playIngTime;

                while (playIng) {

                    playIngTime = System.currentTimeMillis();

                    newTime = long2Date(playIngTime - playTime + pauseTime);

                    handler.sendEmptyMessage(0);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            time.setText(newTime);

        }
    };

    /* *
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     */
    private String long2Date(long lo) {
        Date date = new Date(lo);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
        sd.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        return sd.format(date);
    }

}