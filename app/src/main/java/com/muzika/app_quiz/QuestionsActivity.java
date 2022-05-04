package com.muzika.app_quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class QuestionsActivity extends AppCompatActivity {

    private static float col = 0;
    private TextView timer;
    private CountDownTimer myTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        ViewPager2 pager = findViewById(R.id.pager);
        FragmentStateAdapter pageAdapter = new MyAdapter(this);
        pager.setUserInputEnabled(false);

        pager.setAdapter(pageAdapter);
        timer();
    }

    public void timer(){
        timer = findViewById(R.id.timer);
        myTimer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(Long.toString(millisUntilFinished/1000) + " seconds left");
            }

            @Override
            public void onFinish() {

            }
        };
        myTimer.start();
    }

}