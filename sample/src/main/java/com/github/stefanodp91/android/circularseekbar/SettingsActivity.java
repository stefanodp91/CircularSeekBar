package com.github.stefanodp91.android.circularseekbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.github.stefanodp91.android.circularseekbar.ColorPickerActivity.MULTIPLE_SELECTION;
import static com.github.stefanodp91.android.circularseekbar.ColorPickerActivity.SELECTED_COLOR;
import static com.github.stefanodp91.android.circularseekbar.ColorPickerActivity.SINGLE_SELECTION;
import static com.github.stefanodp91.android.circularseekbar.MainActivity.INTENT_COLOR_LIST_PICKER;
import static com.github.stefanodp91.android.circularseekbar.MainActivity.INTENT_TEXT_COLOR_PICKER;

public class SettingsActivity extends AppCompatActivity {

    private static final int DEFAULT_DISABLED_INDICATOR_RADIUS = 0;
    private static final int DEFAULT_INDICATOR_RADIUS = 10;

    static final String STEPS = "STEPS";
    static final String PROGRESS_WIDTH = "PROGRESS_WIDTH";
    static final String ARC_WIDTH = "ARC_WIDTH";
    static final String INDICATOR_ENABLED = "INDICATOR_ENABLED";
    static final String PROGRESS = "PROGRESS";
    static final String INDICATOR_RADIUS = "INDICATOR_RADIUS";
    static final String ENABLED = "ENABLED";
    static final String TEXT = "TEXT";
    static final String TEXT_PROGRESS = "TEXT_PROGRESS";
    static final String TEXT_SIZE = "TEXT_SIZE";
    static final String DYNAMC_TEXT_COLOR = "DYNAMC_TEXT_COLOR";
    static final String TEXT_COLOR = "TEXT_COLOR";
    static final String COLOR_LIST = "COLOR_LIST";

    private Context mContext;

    private int mSteps, mProgressWidth, mArcWidth, mProgress, mIndicatoRadius, mTextSize;
    private @ColorInt
    int mTextColor;
    private @ColorInt
    int[] mColorList;
    private boolean isIndicatorEnabled, isEnabled, isTextProgress, isDynamicTextColorEnabled;
    private String mText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_settings);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setSteps();
        setProgressWidth();
        setArcWidth();
        setIndicatorEnabled();
        setProgress();
        setIndicatorRadius();
        setEnabled();
        setText();
        setTextProgress();
        setTextSize();
        setDynamicTextColorEnabled();
        setTextColor();
        setColorList();

        // save
        FloatingActionButton mSave = findViewById(R.id.save);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra(STEPS, mSteps);
                intent.putExtra(PROGRESS_WIDTH, mProgressWidth);
                intent.putExtra(ARC_WIDTH, mArcWidth);
                intent.putExtra(INDICATOR_ENABLED, isIndicatorEnabled);
                intent.putExtra(PROGRESS, mProgress);
                intent.putExtra(INDICATOR_RADIUS, mIndicatoRadius);
                intent.putExtra(ENABLED, isEnabled);
                intent.putExtra(TEXT_PROGRESS, isTextProgress);
                intent.putExtra(TEXT, mText);
                intent.putExtra(TEXT_SIZE, mTextSize);
                intent.putExtra(TEXT_COLOR, mTextColor);
                intent.putExtra(DYNAMC_TEXT_COLOR, isDynamicTextColorEnabled);
                intent.putExtra(COLOR_LIST, mColorList);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setSteps() {
        AppCompatSeekBar seek = findViewById(R.id.steps);
        final AppCompatTextView value = findViewById(R.id.steps_value);

        // restore data
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(STEPS)) {
                mSteps = getIntent().getExtras().getInt(STEPS);
                seek.setProgress(mSteps);
                value.setText(String.valueOf(mSteps));
            }
        }

        // listen for changes
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSteps = progress;
                value.setText(String.valueOf(mSteps));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setProgressWidth() {
        AppCompatSeekBar seek = findViewById(R.id.progress_width);
        final AppCompatTextView value = findViewById(R.id.progress_width_value);

        // restore data
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(PROGRESS_WIDTH)) {
                mProgressWidth = getIntent().getExtras().getInt(PROGRESS_WIDTH);
                seek.setProgress(mProgressWidth);
                value.setText(String.valueOf(mProgressWidth));
            }
        }

        // listen for changes
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mProgressWidth = progress;
                value.setText(String.valueOf(mProgressWidth));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setArcWidth() {
        AppCompatSeekBar seek = findViewById(R.id.arc_width);
        final AppCompatTextView value = findViewById(R.id.arc_width_value);

        // restore data
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(ARC_WIDTH)) {
                mArcWidth = getIntent().getExtras().getInt(ARC_WIDTH);
                seek.setProgress(mArcWidth);
                value.setText(String.valueOf(mArcWidth));
            }
        }

        // listen for changes
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mArcWidth = progress;
                value.setText(String.valueOf(mArcWidth));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setIndicatorEnabled() {
        SwitchCompat switchCompat = findViewById(R.id.indicator_enabled);

        // restore data
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(INDICATOR_ENABLED)) {
                isIndicatorEnabled = getIntent().getExtras().getBoolean(INDICATOR_ENABLED);
                switchCompat.setChecked(isIndicatorEnabled);
            }
        }

        // listen for changes
        switchCompat.setOnCheckedChangeListener(new SwitchCompat.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isIndicatorEnabled = isChecked;

                // default indicator radius
                if (isIndicatorEnabled && mIndicatoRadius == DEFAULT_DISABLED_INDICATOR_RADIUS) {
                    mIndicatoRadius = DEFAULT_INDICATOR_RADIUS; // update value
                    // update indicator radius views
                    ((AppCompatTextView) findViewById(R.id.indicator_radius_value))
                            .setText(String.valueOf(mIndicatoRadius));
                    ((AppCompatSeekBar) findViewById(R.id.indicator_radius)).
                            setProgress(mIndicatoRadius);
                }

                if (!isIndicatorEnabled) {
                    mIndicatoRadius = DEFAULT_DISABLED_INDICATOR_RADIUS; // update value
                    // update indicator radius views
                    ((AppCompatTextView) findViewById(R.id.indicator_radius_value))
                            .setText(String.valueOf(mIndicatoRadius));
                    ((AppCompatSeekBar) findViewById(R.id.indicator_radius)).
                            setProgress(mIndicatoRadius);
                }
            }
        });
    }

    private void setProgress() {
        AppCompatSeekBar seek = findViewById(R.id.progress);
        final AppCompatTextView value = findViewById(R.id.progress_value);

        // restore data
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(PROGRESS)) {
                mProgress = getIntent().getExtras().getInt(PROGRESS);
                seek.setProgress(mProgress);
                value.setText(String.valueOf(mProgress));
            }
        }

        // listen for changes
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mProgress = progress;
                value.setText(String.valueOf(mProgress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setIndicatorRadius() {
        AppCompatSeekBar seek = findViewById(R.id.indicator_radius);
        final AppCompatTextView value = findViewById(R.id.indicator_radius_value);

        // restore data
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(INDICATOR_RADIUS)) {
                mIndicatoRadius = getIntent().getExtras().getInt(INDICATOR_RADIUS);
                seek.setProgress(mIndicatoRadius);
                value.setText(String.valueOf(mIndicatoRadius));
            }
        }

        // listen for changes
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mIndicatoRadius = progress;
                value.setText(String.valueOf(mIndicatoRadius));

                // update value
                if (mIndicatoRadius > DEFAULT_DISABLED_INDICATOR_RADIUS) {
                    isIndicatorEnabled = true;
                } else {
                    isIndicatorEnabled = false;
                }
                // update view
                ((SwitchCompat) findViewById(R.id.indicator_enabled)).setChecked(isIndicatorEnabled);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setEnabled() {
        SwitchCompat switchCompat = findViewById(R.id.enabled);

        // restore data
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(ENABLED)) {
                isEnabled = getIntent().getExtras().getBoolean(ENABLED);
                switchCompat.setChecked(isEnabled);
            }
        }

        // listen for changes
        switchCompat.setOnCheckedChangeListener(new SwitchCompat.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isEnabled = isChecked;
            }
        });
    }

    private void setText() {
        AppCompatEditText appCompatEditText = findViewById(R.id.text_value);

        // restore data
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(TEXT)) {
                mText = getIntent().getExtras().getString(TEXT);
                appCompatEditText.setText(mText);
            }
        }

        appCompatEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    mText = s.toString();
                    ((SwitchCompat) findViewById(R.id.text_progress)).setChecked(false);
                }
            }
        });
    }

    private void setTextProgress() {
        SwitchCompat switchCompat = findViewById(R.id.text_progress);

        // restore data
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(TEXT_PROGRESS)) {
                isTextProgress = getIntent().getExtras().getBoolean(TEXT_PROGRESS);
                switchCompat.setChecked(isTextProgress);
            }
        }

        // listen for changes
        switchCompat.setOnCheckedChangeListener(new SwitchCompat.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isTextProgress = isChecked;
                if (isTextProgress) {
                    ((AppCompatEditText) findViewById(R.id.text_value)).setText(null);
                }
            }
        });
    }

    private void setTextSize() {
        AppCompatSeekBar seek = findViewById(R.id.text_size);
        final AppCompatTextView value = findViewById(R.id.text_size_value);

        // restore data
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(TEXT_SIZE)) {
                mTextSize = getIntent().getExtras().getInt(TEXT_SIZE);
                seek.setProgress(mTextSize);
                value.setText(String.valueOf(mTextSize));
            }
        }

        // listen for changes
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTextSize = progress;
                value.setText(String.valueOf(mTextSize));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setTextColor() {
        AppCompatImageView imageView = findViewById(R.id.text_color);

        // restore data
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(TEXT_COLOR)) {
                mTextColor = getIntent().getExtras().getInt(TEXT_COLOR);
                imageView.setBackgroundColor(mTextColor);
            }
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ColorPickerActivity.class);
                intent.putExtra(SINGLE_SELECTION, true);
                startActivityForResult(intent, INTENT_TEXT_COLOR_PICKER);
            }
        });
    }

    private void setDynamicTextColorEnabled() {
        SwitchCompat switchCompat = findViewById(R.id.dynamic_text_color);

        // restore data
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(DYNAMC_TEXT_COLOR)) {
                isDynamicTextColorEnabled = getIntent().getExtras().getBoolean(DYNAMC_TEXT_COLOR);
                switchCompat.setChecked(isDynamicTextColorEnabled);
            }
        }

        // listen for changes
        switchCompat.setOnCheckedChangeListener(new SwitchCompat.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isDynamicTextColorEnabled = isChecked;
            }
        });
    }

    private void setColorList() {
        View view = findViewById(R.id.color_list_clickable_overlay);

        // restore data
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(COLOR_LIST)) {
                mColorList = getIntent().getExtras().getIntArray(COLOR_LIST);
                ((CircularSeekBar) findViewById(R.id.color_list)).setColorList(mColorList);
            }
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ColorPickerActivity.class);
                intent.putExtra(MULTIPLE_SELECTION, true);
                startActivityForResult(intent, INTENT_COLOR_LIST_PICKER);
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED, getIntent());
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        // text color
        if (requestCode == INTENT_TEXT_COLOR_PICKER && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getExtras() != null &&
                    data.getExtras().containsKey(SELECTED_COLOR))
                mTextColor = data.getExtras().getInt(SELECTED_COLOR); // update value
            findViewById(R.id.text_color).setBackgroundColor(mTextColor); // update view
        }

        // color list
        if (requestCode == INTENT_COLOR_LIST_PICKER && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getExtras() != null &&
                    data.getExtras().containsKey(SELECTED_COLOR))
                mColorList = data.getExtras().getIntArray(SELECTED_COLOR); // update value
            ((CircularSeekBar) findViewById(R.id.color_list)).setColorList(mColorList);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
