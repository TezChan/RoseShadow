package com.jaqen.roseshadow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.jaqen.roseshadow.colors.NipponColor;
import com.jaqen.task.TaskItemEditActivity;

public class StartActivity extends AppCompatActivity {

    private LottieAnimationView lottieView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        NipponColor.init(new NipponColor.NipponInitListener() {
            @Override
            public void onInitFinished(int initColor) {
                /*startActivity(new Intent(StartActivity.this, MainActivity.class));
                finish();*/
                startActivity(new Intent(StartActivity.this, TaskItemEditActivity.class));
            }
        });
    }
}
