package it.fourn.android.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import it.fourn.android.ciruclarseekbar.CircularSeekBar;
import it.fourn.android.ciruclarseekbar.OnCircularSeekBarChangeListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CircularSeekBar seekBar = findViewById(R.id.seek);
        seekBar.setOnRoundedSeekChangeListener(new OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChange(CircularSeekBar CircularSeekBar, float progress) {
                Log.d("progress", "" + progress);
            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar CircularSeekBar) {

            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar CircularSeekBar) {

            }
        });
    }
}
