package com.github.stefanodp91.android.circularseekbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.github.stefanodp91.android.circularseekbar.SettingsActivity.ARC_WIDTH;
import static com.github.stefanodp91.android.circularseekbar.SettingsActivity.COLOR_LIST;
import static com.github.stefanodp91.android.circularseekbar.SettingsActivity.DYNAMC_TEXT_COLOR;
import static com.github.stefanodp91.android.circularseekbar.SettingsActivity.ENABLED;
import static com.github.stefanodp91.android.circularseekbar.SettingsActivity.INDICATOR_ENABLED;
import static com.github.stefanodp91.android.circularseekbar.SettingsActivity.INDICATOR_RADIUS;
import static com.github.stefanodp91.android.circularseekbar.SettingsActivity.PROGRESS;
import static com.github.stefanodp91.android.circularseekbar.SettingsActivity.PROGRESS_WIDTH;
import static com.github.stefanodp91.android.circularseekbar.SettingsActivity.STEPS;
import static com.github.stefanodp91.android.circularseekbar.SettingsActivity.TEXT;
import static com.github.stefanodp91.android.circularseekbar.SettingsActivity.TEXT_COLOR;
import static com.github.stefanodp91.android.circularseekbar.SettingsActivity.TEXT_PROGRESS;
import static com.github.stefanodp91.android.circularseekbar.SettingsActivity.TEXT_SIZE;

public class MainActivity extends AppCompatActivity {

    static final int INTENT_SETTINGS = 0;
    static final int INTENT_TEXT_COLOR_PICKER = 1;
    static final int INTENT_COLOR_LIST_PICKER = 2;

    private CircularSeekBar mCircularSeekBar;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mCircularSeekBar = findViewById(R.id.seek);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            // default values ( will be overridden)
            intent.putExtra(STEPS, (int) mCircularSeekBar.getStep());
            intent.putExtra(PROGRESS_WIDTH, (int) mCircularSeekBar.getProgressWidth());
            intent.putExtra(ARC_WIDTH, (int) mCircularSeekBar.getArcWidth());
            intent.putExtra(INDICATOR_ENABLED, mCircularSeekBar.isIndicatorEnabled());
            intent.putExtra(PROGRESS, (int) mCircularSeekBar.getProgress());
            intent.putExtra(INDICATOR_RADIUS, (int) mCircularSeekBar.getIndicatorRadius());
            intent.putExtra(ENABLED, mCircularSeekBar.isEnabled());
            intent.putExtra(TEXT_SIZE, (int) mCircularSeekBar.getTextSize());
            if (mCircularSeekBar.isEnabled()) {
                intent.putExtra(TEXT, "");
            } else {
                intent.putExtra(TEXT, mCircularSeekBar.getText());
            }
            intent.putExtra(TEXT_PROGRESS, mCircularSeekBar.isTextProgress());
            intent.putExtra(DYNAMC_TEXT_COLOR, mCircularSeekBar.isDynamicTextColor());
            intent.putExtra(TEXT_COLOR, mCircularSeekBar.getTextColor());
            intent.putExtra(COLOR_LIST, mCircularSeekBar.getColorList());
            // settings parameters
            if (extras != null) {
                intent.putExtras(extras);
            }
            startActivityForResult(intent, INTENT_SETTINGS);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == INTENT_SETTINGS && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                extras = data.getExtras();

                // steps
                if (mCircularSeekBar != null && data.getExtras().containsKey(STEPS)) {
                    mCircularSeekBar.setStep(data.getExtras().getInt(STEPS));
                }

                // progress width
                if (mCircularSeekBar != null && data.getExtras().containsKey(PROGRESS_WIDTH)) {
                    int progressWidth = data.getExtras().getInt(PROGRESS_WIDTH);
                    mCircularSeekBar.setProgressWidth(progressWidth);
                }

                // arc width
                if (mCircularSeekBar != null && data.getExtras().containsKey(ARC_WIDTH)) {
                    int arcWidth = data.getExtras().getInt(ARC_WIDTH);
                    mCircularSeekBar.setArcWidth(arcWidth);
                }

                // indicator enabled
                if (mCircularSeekBar != null && data.getExtras().containsKey(INDICATOR_ENABLED)) {
                    boolean isIndicatorEnabled = data.getExtras().getBoolean(INDICATOR_ENABLED);
                    mCircularSeekBar.setIndicatorEnabled(isIndicatorEnabled);
                }

                // progress
                if (mCircularSeekBar != null && data.getExtras().containsKey(PROGRESS)) {
                    int progress = data.getExtras().getInt(PROGRESS);
                    mCircularSeekBar.setProgress(progress);
                }

                // indicator radius
                if (mCircularSeekBar != null && data.getExtras().containsKey(INDICATOR_RADIUS)) {
                    int indicatorRadius = data.getExtras().getInt(INDICATOR_RADIUS);
                    mCircularSeekBar.setIndicatorRadius(indicatorRadius);
                }

                // enabled
                if (mCircularSeekBar != null && data.getExtras().containsKey(ENABLED)) {
                    boolean enabled = data.getExtras().getBoolean(ENABLED);
                    mCircularSeekBar.setEnabled(enabled);
                }

                // text
                if (mCircularSeekBar != null && data.getExtras().containsKey(TEXT)) {
                    String text = data.getExtras().getString(TEXT);
                    mCircularSeekBar.setText(text);
                }

                // text progress
                if (mCircularSeekBar != null && data.getExtras().containsKey(TEXT_PROGRESS)) {
                    boolean textProgress = data.getExtras().getBoolean(TEXT_PROGRESS);
                    mCircularSeekBar.setIsTextProgress(textProgress);
                }

                // text size
                if (mCircularSeekBar != null && data.getExtras().containsKey(TEXT_SIZE)) {
                    int textSize = data.getExtras().getInt(TEXT_SIZE);
                    mCircularSeekBar.setTextSize(textSize);
                }

                //  text color
                if (mCircularSeekBar != null && data.getExtras().containsKey(TEXT_COLOR)) {
                    @ColorInt int textColor = data.getExtras().getInt(TEXT_COLOR);
                    mCircularSeekBar.setTextColor(textColor);
                }

                // dynamic text color
                if (mCircularSeekBar != null && data.getExtras().containsKey(DYNAMC_TEXT_COLOR)) {
                    boolean dynamicTextColor = data.getExtras().getBoolean(DYNAMC_TEXT_COLOR);
                    mCircularSeekBar.setDynamicTextColor(dynamicTextColor);
                }

                // color list
                if (mCircularSeekBar != null && data.getExtras().containsKey(COLOR_LIST)) {
                    @ColorInt int[] colorList = data.getExtras().getIntArray(COLOR_LIST);
                    mCircularSeekBar.setColorList(colorList);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
