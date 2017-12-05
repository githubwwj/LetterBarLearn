package com.wwj.letterbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.wwj.letterbar.learn.LetterBar;

public class MainActivity extends AppCompatActivity {

    private TextView tvDialog;
    private LetterBar letterBar;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDialog = findViewById(R.id.tvDialog);
        letterBar = findViewById(R.id.letterBar);

        letterBar.setTextView(tvDialog);

        letterBar.setOnLetterChangeListener(new LetterBar.OnLetterChangeListener() {
            @Override
            public void setOnLetterChangeListener(String s, int charIndex) {
                Log.d(TAG, "==========s=" + s + "========charIndex=" + charIndex);
            }
        });

    }
}
