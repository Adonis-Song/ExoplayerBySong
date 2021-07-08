package com.google.android.exoplayer2.testdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.exoplayer2.surfacedemo.R;

public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        findViewById(R.id.button).setOnClickListener(v -> {
            startActivity(new Intent(Main2Activity.this, MainActivity.class));
        });
    }
}