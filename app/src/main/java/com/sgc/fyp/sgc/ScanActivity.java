package com.sgc.fyp.sgc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ScanActivity extends AppCompatActivity {
    AnimationDrawable animationDrawable;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
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
    public void ScanPlaintext(View v)   {
        Intent intent = new Intent(this, ScanPlaintextActivity.class);
        startActivity(intent);
        finish();
    }
    public void ScanImage(View v)   {
        Intent intent = new Intent(this, ScanImageActivity.class);
        startActivity(intent);
        finish();
    }
    public void ScanAudio(View v)   {
        Intent intent = new Intent(this, ScanAudioActivity.class);
        startActivity(intent);
        finish();
    }
    public void ScanVideo(View v)   {
        Intent intent = new Intent(this, ScanVideoActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(new Intent(ScanActivity.this, MainActivity.class));
        finish();
    }
}
