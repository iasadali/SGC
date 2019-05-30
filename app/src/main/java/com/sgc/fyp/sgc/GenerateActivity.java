package com.sgc.fyp.sgc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class GenerateActivity extends AppCompatActivity {
    AnimationDrawable animationDrawable;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);
        linearLayout = findViewById(R.id.linearLayout);
        animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning())
            animationDrawable.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning())
            animationDrawable.stop();
    }
    public void GeneratePlaintext(View v)   {
        Intent intent = new Intent(this, PlaintextActivity.class);
        startActivity(intent);
        finish();

    }
    public void GenerateImage(View v)   {
        Intent intent = new Intent(this, ImageActivity.class);

        startActivity(intent);
        finish();
    }
    public void GenerateAudio(View v)   {
        Intent intent = new Intent(this, AudioActivity.class);
        startActivity(intent);
        finish();

    }
    public void GenerateVideo(View v)   {
        Intent intent = new Intent(this, VideoActivity.class);
        startActivity(intent);
        finish();

    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(new Intent(GenerateActivity.this, MainActivity.class));
        finish();

    }
}

