package com.github.stefanodp91.android.circularseekbar;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CircularSeekBar seekBar = findViewById(R.id.seek);
        seekBar.setOnRoundedSeekChangeListener(new OnCircularSeekBarChangeListener() {
            /**
             * Progress change
             * @param CircularSeekBar
             * @param progress the progress
             */
            @Override
            public void onProgressChange(CircularSeekBar CircularSeekBar, float progress) {
                Log.d("progress", "" + progress);
            }

            /**
             * Indicator touched
             * @param CircularSeekBar
             */
            @Override
            public void onStartTrackingTouch(CircularSeekBar CircularSeekBar) {

            }

            /**
             * Indicator released
             * @param CircularSeekBar
             */
            @Override
            public void onStopTrackingTouch(CircularSeekBar CircularSeekBar) {

            }
        });
    }
}