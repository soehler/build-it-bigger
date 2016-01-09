package com.udacity.gradle.androidlibs.jokeshow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayJokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_joke);

        Intent intent = getIntent();

        TextView tvJoke = (TextView) findViewById(R.id.tvJoke);
        tvJoke.setText(intent.getStringExtra(Intent.EXTRA_TEXT));

        Button butDismiss = (Button) findViewById(R.id.butDismissJoke);

        butDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
