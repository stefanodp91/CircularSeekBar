package com.github.stefanodp91.android.circularseekbar;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

public class MainActivity extends AppCompatActivity {

    private AppCompatTextView mProgress;

//    for your attention:
//    https://issues.sonatype.org/browse/OSSRH-49355
//    https://github.com/stefanodp91/CircularSeekBar


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgress = findViewById(R.id.progress);

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
                mProgress.setText(String.valueOf(progress));
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
