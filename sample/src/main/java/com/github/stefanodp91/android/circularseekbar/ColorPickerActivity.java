package com.github.stefanodp91.android.circularseekbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ColorPickerActivity extends AppCompatActivity {
    public static final String SINGLE_SELECTION = "SINGLE_SELECTION";
    public static final String MULTIPLE_SELECTION = "MULTIPLE_SELECTION";

    public static final String SELECTED_COLOR = "SELECTED_COLOR";

    private static final int COLUMNS = 3;

    private List<MaterialColor> mSelectedColorList;
    private static FloatingActionButton mDone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        mSelectedColorList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, COLUMNS);
        recyclerView.setLayoutManager(layoutManager);

        boolean multiple = false;
        if (getIntent() != null && getIntent().getExtras() != null
                && getIntent().getExtras().containsKey(SINGLE_SELECTION) &&
                getIntent().getExtras().getBoolean(SINGLE_SELECTION)) {
            multiple = false;
        } else if (getIntent() != null && getIntent().getExtras() != null
                && getIntent().getExtras().containsKey(MULTIPLE_SELECTION) &&
                getIntent().getExtras().getBoolean(MULTIPLE_SELECTION)) {
            multiple = true;
        }

        final ColorsAdapter colorsAdapter = new ColorsAdapter(getColorList(this));
        final boolean finalMultiple = multiple;
        colorsAdapter.setOnClickListener(new ColorsAdapter.OnClickListener() {
            @Override
            public void onClick(MaterialColor materialColor, int position) {
                if (!finalMultiple) {
                    Intent intent = new Intent();
                    intent.putExtra(SELECTED_COLOR, materialColor.colorInt);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    if (materialColor.selected) {
                        mSelectedColorList.remove(materialColor);
                        materialColor.selected = false;
                        colorsAdapter.notifyDataSetChanged();
                        if (mDone.isShown() && mSelectedColorList.size() == 0) {
                            mDone.hide();
                        }
                    } else {
                        mSelectedColorList.add(materialColor);
                        materialColor.selected = true;
                        colorsAdapter.notifyDataSetChanged();
                        if (!mDone.isShown()) {
                            mDone.show();
                        }
                    }
                }
            }
        });

        recyclerView.setAdapter(colorsAdapter);

        mDone = findViewById(R.id.done);
        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedColorList.size() == 1) {
                    // if one only color is selected, duplicate it.
                    // circular seekbar need at least 2 colors to work
                    MaterialColor color = mSelectedColorList.get(0);
                    mSelectedColorList.add(color);
                } else {
                    int[] materialColors = toArray(mSelectedColorList);
                    Intent intent = new Intent();
                    intent.putExtra(SELECTED_COLOR, materialColors);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private int[] toArray(List<MaterialColor> list) {
        int size = list.size();
        int[] result = new int[size];
        for (int n = 0; n < size; ++n) {
            result[n] = list.get(n).colorInt;
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    private static MaterialColor[] getColorList(Context context) {
        return new MaterialColor[]{

                new MaterialColor("white", ContextCompat.getColor(context, R.color.white)),

                new MaterialColor("black", ContextCompat.getColor(context, R.color.black)),

                new MaterialColor("red_50", ContextCompat.getColor(context, R.color.red_50)),
                new MaterialColor("red_100", ContextCompat.getColor(context, R.color.red_100)),
                new MaterialColor("red_200", ContextCompat.getColor(context, R.color.red_200)),
                new MaterialColor("red_300", ContextCompat.getColor(context, R.color.red_300)),
                new MaterialColor("red_3400", ContextCompat.getColor(context, R.color.red_400)),
                new MaterialColor("red_500", ContextCompat.getColor(context, R.color.red_500)),
                new MaterialColor("red_600", ContextCompat.getColor(context, R.color.red_600)),
                new MaterialColor("red_700", ContextCompat.getColor(context, R.color.red_700)),
                new MaterialColor("red_800", ContextCompat.getColor(context, R.color.red_800)),
                new MaterialColor("red_900", ContextCompat.getColor(context, R.color.red_900)),
                new MaterialColor("red_a_100", ContextCompat.getColor(context, R.color.red_a_100)),
                new MaterialColor("red_a_200", ContextCompat.getColor(context, R.color.red_a_200)),
                new MaterialColor("red_a_400", ContextCompat.getColor(context, R.color.red_a_400)),
                new MaterialColor("red_a_700", ContextCompat.getColor(context, R.color.red_a_700)),

                new MaterialColor("pink_50", ContextCompat.getColor(context, R.color.pink_50)),
                new MaterialColor("pink_100", ContextCompat.getColor(context, R.color.pink_100)),
                new MaterialColor("pink_200", ContextCompat.getColor(context, R.color.pink_200)),
                new MaterialColor("pink_300", ContextCompat.getColor(context, R.color.pink_300)),
                new MaterialColor("pink_400", ContextCompat.getColor(context, R.color.pink_400)),
                new MaterialColor("pink_500", ContextCompat.getColor(context, R.color.pink_500)),
                new MaterialColor("pink_600", ContextCompat.getColor(context, R.color.pink_600)),
                new MaterialColor("pink_700", ContextCompat.getColor(context, R.color.pink_700)),
                new MaterialColor("pink_800", ContextCompat.getColor(context, R.color.pink_800)),
                new MaterialColor("pink_900", ContextCompat.getColor(context, R.color.pink_900)),
                new MaterialColor("pink_a_100", ContextCompat.getColor(context, R.color.pink_a_100)),
                new MaterialColor("pink_a_200", ContextCompat.getColor(context, R.color.pink_a_200)),
                new MaterialColor("pink_a_400", ContextCompat.getColor(context, R.color.pink_a_400)),
                new MaterialColor("pink_a_700", ContextCompat.getColor(context, R.color.pink_a_700)),

                new MaterialColor("purple_50", ContextCompat.getColor(context, R.color.purple_50)),
                new MaterialColor("purple_100", ContextCompat.getColor(context, R.color.purple_100)),
                new MaterialColor("purple_200", ContextCompat.getColor(context, R.color.purple_200)),
                new MaterialColor("purple_300", ContextCompat.getColor(context, R.color.purple_300)),
                new MaterialColor("purple_300", ContextCompat.getColor(context, R.color.purple_400)),
                new MaterialColor("purple_500", ContextCompat.getColor(context, R.color.purple_500)),
                new MaterialColor("purple_600", ContextCompat.getColor(context, R.color.purple_600)),
                new MaterialColor("purple_700", ContextCompat.getColor(context, R.color.purple_700)),
                new MaterialColor("purple_800", ContextCompat.getColor(context, R.color.purple_800)),
                new MaterialColor("purple_900", ContextCompat.getColor(context, R.color.purple_900)),
                new MaterialColor("purple_a_100", ContextCompat.getColor(context, R.color.purple_a_100)),
                new MaterialColor("purple_a_200", ContextCompat.getColor(context, R.color.purple_a_200)),
                new MaterialColor("purple_a_400", ContextCompat.getColor(context, R.color.purple_a_400)),
                new MaterialColor("purple_a_700", ContextCompat.getColor(context, R.color.purple_a_700)),

                new MaterialColor("deep_purple_50", ContextCompat.getColor(context, R.color.deep_purple_50)),
                new MaterialColor("deep_purple_100", ContextCompat.getColor(context, R.color.deep_purple_100)),
                new MaterialColor("deep_purple_200", ContextCompat.getColor(context, R.color.deep_purple_200)),
                new MaterialColor("deep_purple_300", ContextCompat.getColor(context, R.color.deep_purple_300)),
                new MaterialColor("deep_purple_400", ContextCompat.getColor(context, R.color.deep_purple_400)),
                new MaterialColor("deep_purple_500", ContextCompat.getColor(context, R.color.deep_purple_500)),
                new MaterialColor("deep_purple_600", ContextCompat.getColor(context, R.color.deep_purple_600)),
                new MaterialColor("deep_purple_700", ContextCompat.getColor(context, R.color.deep_purple_700)),
                new MaterialColor("deep_purple_800", ContextCompat.getColor(context, R.color.deep_purple_800)),
                new MaterialColor("deep_purple_900", ContextCompat.getColor(context, R.color.deep_purple_900)),
                new MaterialColor("deep_purple_a_100", ContextCompat.getColor(context, R.color.deep_purple_a_100)),
                new MaterialColor("deep_purple_a_200", ContextCompat.getColor(context, R.color.deep_purple_a_200)),
                new MaterialColor("deep_purple_a_400", ContextCompat.getColor(context, R.color.deep_purple_a_400)),
                new MaterialColor("deep_purple_a_700", ContextCompat.getColor(context, R.color.deep_purple_a_700)),

                new MaterialColor("indigo_50", ContextCompat.getColor(context, R.color.indigo_50)),
                new MaterialColor("indigo_100", ContextCompat.getColor(context, R.color.indigo_100)),
                new MaterialColor("indigo_200", ContextCompat.getColor(context, R.color.indigo_200)),
                new MaterialColor("indigo_300", ContextCompat.getColor(context, R.color.indigo_300)),
                new MaterialColor("indigo_400", ContextCompat.getColor(context, R.color.indigo_400)),
                new MaterialColor("indigo_500", ContextCompat.getColor(context, R.color.indigo_500)),
                new MaterialColor("indigo_600", ContextCompat.getColor(context, R.color.indigo_600)),
                new MaterialColor("indigo_700", ContextCompat.getColor(context, R.color.indigo_700)),
                new MaterialColor("indigo_800", ContextCompat.getColor(context, R.color.indigo_800)),
                new MaterialColor("indigo_900", ContextCompat.getColor(context, R.color.indigo_900)),
                new MaterialColor("indigo_a_100", ContextCompat.getColor(context, R.color.indigo_a_100)),
                new MaterialColor("indigo_a_200", ContextCompat.getColor(context, R.color.indigo_a_200)),
                new MaterialColor("indigo_a_400", ContextCompat.getColor(context, R.color.indigo_a_400)),
                new MaterialColor("indigo_a_700", ContextCompat.getColor(context, R.color.indigo_a_700)),

                new MaterialColor("blue_50", ContextCompat.getColor(context, R.color.blue_50)),
                new MaterialColor("blue_100", ContextCompat.getColor(context, R.color.blue_100)),
                new MaterialColor("blue_200", ContextCompat.getColor(context, R.color.blue_200)),
                new MaterialColor("blue_300", ContextCompat.getColor(context, R.color.blue_300)),
                new MaterialColor("blue_400", ContextCompat.getColor(context, R.color.blue_400)),
                new MaterialColor("blue_500", ContextCompat.getColor(context, R.color.blue_500)),
                new MaterialColor("blue_600", ContextCompat.getColor(context, R.color.blue_600)),
                new MaterialColor("blue_700", ContextCompat.getColor(context, R.color.blue_700)),
                new MaterialColor("blue_800", ContextCompat.getColor(context, R.color.blue_800)),
                new MaterialColor("blue_900", ContextCompat.getColor(context, R.color.blue_900)),
                new MaterialColor("blue_a_100", ContextCompat.getColor(context, R.color.blue_a_100)),
                new MaterialColor("blue_a_200", ContextCompat.getColor(context, R.color.blue_a_200)),
                new MaterialColor("blue_a_400", ContextCompat.getColor(context, R.color.blue_a_400)),
                new MaterialColor("blue_a_700", ContextCompat.getColor(context, R.color.blue_a_700)),

                new MaterialColor("light_blue_50", ContextCompat.getColor(context, R.color.light_blue_50)),
                new MaterialColor("light_blue_100", ContextCompat.getColor(context, R.color.light_blue_100)),
                new MaterialColor("light_blue_200", ContextCompat.getColor(context, R.color.light_blue_200)),
                new MaterialColor("light_blue_300", ContextCompat.getColor(context, R.color.light_blue_300)),
                new MaterialColor("light_blue_400", ContextCompat.getColor(context, R.color.light_blue_400)),
                new MaterialColor("light_blue_500", ContextCompat.getColor(context, R.color.light_blue_500)),
                new MaterialColor("light_blue_600", ContextCompat.getColor(context, R.color.light_blue_600)),
                new MaterialColor("light_blue_700", ContextCompat.getColor(context, R.color.light_blue_700)),
                new MaterialColor("light_blue_800", ContextCompat.getColor(context, R.color.light_blue_800)),
                new MaterialColor("light_blue_900", ContextCompat.getColor(context, R.color.light_blue_900)),
                new MaterialColor("light_blue_a_100", ContextCompat.getColor(context, R.color.light_blue_a_100)),
                new MaterialColor("light_blue_a_200", ContextCompat.getColor(context, R.color.light_blue_a_200)),
                new MaterialColor("light_blue_a_400", ContextCompat.getColor(context, R.color.light_blue_a_400)),
                new MaterialColor("light_blue_a_700", ContextCompat.getColor(context, R.color.light_blue_a_700)),

                new MaterialColor("cyan_50", ContextCompat.getColor(context, R.color.cyan_50)),
                new MaterialColor("cyan_100", ContextCompat.getColor(context, R.color.cyan_100)),
                new MaterialColor("cyan_200", ContextCompat.getColor(context, R.color.cyan_200)),
                new MaterialColor("cyan_300", ContextCompat.getColor(context, R.color.cyan_300)),
                new MaterialColor("cyan_400", ContextCompat.getColor(context, R.color.cyan_400)),
                new MaterialColor("cyan_500", ContextCompat.getColor(context, R.color.cyan_500)),
                new MaterialColor("cyan_600", ContextCompat.getColor(context, R.color.cyan_600)),
                new MaterialColor("cyan_700", ContextCompat.getColor(context, R.color.cyan_700)),
                new MaterialColor("cyan_800", ContextCompat.getColor(context, R.color.cyan_800)),
                new MaterialColor("cyan_900", ContextCompat.getColor(context, R.color.cyan_900)),
                new MaterialColor("cyan_a_100", ContextCompat.getColor(context, R.color.cyan_a_100)),
                new MaterialColor("cyan_a_200", ContextCompat.getColor(context, R.color.cyan_a_200)),
                new MaterialColor("cyan_a_400", ContextCompat.getColor(context, R.color.cyan_a_400)),
                new MaterialColor("cyan_a_700", ContextCompat.getColor(context, R.color.cyan_a_700)),

                new MaterialColor("teal_50", ContextCompat.getColor(context, R.color.teal_50)),
                new MaterialColor("teal_100", ContextCompat.getColor(context, R.color.teal_100)),
                new MaterialColor("teal_200", ContextCompat.getColor(context, R.color.teal_200)),
                new MaterialColor("teal_300", ContextCompat.getColor(context, R.color.teal_300)),
                new MaterialColor("teal_400", ContextCompat.getColor(context, R.color.teal_400)),
                new MaterialColor("teal_500", ContextCompat.getColor(context, R.color.teal_500)),
                new MaterialColor("teal_600", ContextCompat.getColor(context, R.color.teal_600)),
                new MaterialColor("teal_700", ContextCompat.getColor(context, R.color.teal_700)),
                new MaterialColor("teal_800", ContextCompat.getColor(context, R.color.teal_800)),
                new MaterialColor("teal_900", ContextCompat.getColor(context, R.color.teal_900)),
                new MaterialColor("teal_a_100", ContextCompat.getColor(context, R.color.teal_a_100)),
                new MaterialColor("teal_a_200", ContextCompat.getColor(context, R.color.teal_a_200)),
                new MaterialColor("teal_a_400", ContextCompat.getColor(context, R.color.teal_a_400)),
                new MaterialColor("teal_a_700", ContextCompat.getColor(context, R.color.teal_a_700)),

                new MaterialColor("green_50", ContextCompat.getColor(context, R.color.green_50)),
                new MaterialColor("green_100", ContextCompat.getColor(context, R.color.green_100)),
                new MaterialColor("green_200", ContextCompat.getColor(context, R.color.green_200)),
                new MaterialColor("green_300", ContextCompat.getColor(context, R.color.green_300)),
                new MaterialColor("green_400", ContextCompat.getColor(context, R.color.green_400)),
                new MaterialColor("green_500", ContextCompat.getColor(context, R.color.green_500)),
                new MaterialColor("green_600", ContextCompat.getColor(context, R.color.green_600)),
                new MaterialColor("green_700", ContextCompat.getColor(context, R.color.green_700)),
                new MaterialColor("green_800", ContextCompat.getColor(context, R.color.green_800)),
                new MaterialColor("green_900", ContextCompat.getColor(context, R.color.green_900)),
                new MaterialColor("green_a_100", ContextCompat.getColor(context, R.color.green_a_100)),
                new MaterialColor("green_a_200", ContextCompat.getColor(context, R.color.green_a_200)),
                new MaterialColor("green_a_400", ContextCompat.getColor(context, R.color.green_a_400)),
                new MaterialColor("green_a_700", ContextCompat.getColor(context, R.color.green_a_700)),

                new MaterialColor("light_green_50", ContextCompat.getColor(context, R.color.light_green_50)),
                new MaterialColor("light_green_100", ContextCompat.getColor(context, R.color.light_green_100)),
                new MaterialColor("light_green_200", ContextCompat.getColor(context, R.color.light_green_200)),
                new MaterialColor("light_green_300", ContextCompat.getColor(context, R.color.light_green_300)),
                new MaterialColor("light_green_400", ContextCompat.getColor(context, R.color.light_green_400)),
                new MaterialColor("light_green_500", ContextCompat.getColor(context, R.color.light_green_500)),
                new MaterialColor("light_green_600", ContextCompat.getColor(context, R.color.light_green_600)),
                new MaterialColor("light_green_700", ContextCompat.getColor(context, R.color.light_green_700)),
                new MaterialColor("light_green_800", ContextCompat.getColor(context, R.color.light_green_800)),
                new MaterialColor("light_green_900", ContextCompat.getColor(context, R.color.light_green_900)),
                new MaterialColor("light_green_a_100", ContextCompat.getColor(context, R.color.light_green_a_100)),
                new MaterialColor("light_green_a_200", ContextCompat.getColor(context, R.color.light_green_a_200)),
                new MaterialColor("light_green_a_400", ContextCompat.getColor(context, R.color.light_green_a_400)),
                new MaterialColor("light_green_a_700", ContextCompat.getColor(context, R.color.light_green_a_700)),

                new MaterialColor("lime_50", ContextCompat.getColor(context, R.color.lime_50)),
                new MaterialColor("lime_100", ContextCompat.getColor(context, R.color.lime_100)),
                new MaterialColor("lime_200", ContextCompat.getColor(context, R.color.lime_200)),
                new MaterialColor("lime_300", ContextCompat.getColor(context, R.color.lime_300)),
                new MaterialColor("lime_400", ContextCompat.getColor(context, R.color.lime_400)),
                new MaterialColor("lime_500", ContextCompat.getColor(context, R.color.lime_500)),
                new MaterialColor("lime_600", ContextCompat.getColor(context, R.color.lime_600)),
                new MaterialColor("lime_700", ContextCompat.getColor(context, R.color.lime_700)),
                new MaterialColor("lime_800", ContextCompat.getColor(context, R.color.lime_800)),
                new MaterialColor("lime_900", ContextCompat.getColor(context, R.color.lime_900)),
                new MaterialColor("lime_a_100", ContextCompat.getColor(context, R.color.lime_a_100)),
                new MaterialColor("lime_a_200", ContextCompat.getColor(context, R.color.lime_a_200)),
                new MaterialColor("lime_a_500", ContextCompat.getColor(context, R.color.lime_a_400)),
                new MaterialColor("lime_a_700", ContextCompat.getColor(context, R.color.lime_a_700)),

                new MaterialColor("yellow_50", ContextCompat.getColor(context, R.color.yellow_50)),
                new MaterialColor("yellow_100", ContextCompat.getColor(context, R.color.yellow_100)),
                new MaterialColor("yellow_200", ContextCompat.getColor(context, R.color.yellow_200)),
                new MaterialColor("yellow_300", ContextCompat.getColor(context, R.color.yellow_300)),
                new MaterialColor("yellow_400", ContextCompat.getColor(context, R.color.yellow_400)),
                new MaterialColor("yellow_500", ContextCompat.getColor(context, R.color.yellow_500)),
                new MaterialColor("yellow_600", ContextCompat.getColor(context, R.color.yellow_600)),
                new MaterialColor("yellow_700", ContextCompat.getColor(context, R.color.yellow_700)),
                new MaterialColor("yellow_800", ContextCompat.getColor(context, R.color.yellow_800)),
                new MaterialColor("yellow_900", ContextCompat.getColor(context, R.color.yellow_900)),
                new MaterialColor("yellow_a_100", ContextCompat.getColor(context, R.color.yellow_a_100)),
                new MaterialColor("yellow_a_200", ContextCompat.getColor(context, R.color.yellow_a_200)),
                new MaterialColor("yellow_a_400", ContextCompat.getColor(context, R.color.yellow_a_400)),
                new MaterialColor("yellow_a_700", ContextCompat.getColor(context, R.color.yellow_a_700)),

                new MaterialColor("amber_50", ContextCompat.getColor(context, R.color.amber_50)),
                new MaterialColor("amber_100", ContextCompat.getColor(context, R.color.amber_100)),
                new MaterialColor("amber_200", ContextCompat.getColor(context, R.color.amber_200)),
                new MaterialColor("amber_300", ContextCompat.getColor(context, R.color.amber_300)),
                new MaterialColor("amber_400", ContextCompat.getColor(context, R.color.amber_400)),
                new MaterialColor("amber_500", ContextCompat.getColor(context, R.color.amber_500)),
                new MaterialColor("amber_60", ContextCompat.getColor(context, R.color.amber_600)),
                new MaterialColor("amber_700", ContextCompat.getColor(context, R.color.amber_700)),
                new MaterialColor("amber_800", ContextCompat.getColor(context, R.color.amber_800)),
                new MaterialColor("amber_90", ContextCompat.getColor(context, R.color.amber_900)),
                new MaterialColor("amber_a_100", ContextCompat.getColor(context, R.color.amber_a_100)),
                new MaterialColor("amber_a_200", ContextCompat.getColor(context, R.color.amber_a_200)),
                new MaterialColor("amber_a_400", ContextCompat.getColor(context, R.color.amber_a_400)),
                new MaterialColor("amber_a_700", ContextCompat.getColor(context, R.color.amber_a_700)),

                new MaterialColor("orange_50", ContextCompat.getColor(context, R.color.orange_50)),
                new MaterialColor("orange_100", ContextCompat.getColor(context, R.color.orange_100)),
                new MaterialColor("orange_200", ContextCompat.getColor(context, R.color.orange_200)),
                new MaterialColor("orange_300", ContextCompat.getColor(context, R.color.orange_300)),
                new MaterialColor("orange_400", ContextCompat.getColor(context, R.color.orange_400)),
                new MaterialColor("orange_500", ContextCompat.getColor(context, R.color.orange_500)),
                new MaterialColor("orange_600", ContextCompat.getColor(context, R.color.orange_600)),
                new MaterialColor("orange_700", ContextCompat.getColor(context, R.color.orange_700)),
                new MaterialColor("orange_800", ContextCompat.getColor(context, R.color.orange_800)),
                new MaterialColor("orange_900", ContextCompat.getColor(context, R.color.orange_900)),
                new MaterialColor("orange_a_100", ContextCompat.getColor(context, R.color.orange_a_100)),
                new MaterialColor("orange_a_200", ContextCompat.getColor(context, R.color.orange_a_200)),
                new MaterialColor("orange_a_400", ContextCompat.getColor(context, R.color.orange_a_400)),
                new MaterialColor("orange_a_700", ContextCompat.getColor(context, R.color.orange_a_700)),

                new MaterialColor("deep_orange_50", ContextCompat.getColor(context, R.color.deep_orange_50)),
                new MaterialColor("deep_orange_100", ContextCompat.getColor(context, R.color.deep_orange_100)),
                new MaterialColor("deep_orange_200", ContextCompat.getColor(context, R.color.deep_orange_200)),
                new MaterialColor("deep_orange_300", ContextCompat.getColor(context, R.color.deep_orange_300)),
                new MaterialColor("deep_orange_400", ContextCompat.getColor(context, R.color.deep_orange_400)),
                new MaterialColor("deep_orange_500", ContextCompat.getColor(context, R.color.deep_orange_500)),
                new MaterialColor("deep_orange_600", ContextCompat.getColor(context, R.color.deep_orange_600)),
                new MaterialColor("deep_orange_700", ContextCompat.getColor(context, R.color.deep_orange_700)),
                new MaterialColor("deep_orange_800", ContextCompat.getColor(context, R.color.deep_orange_800)),
                new MaterialColor("deep_orange_900", ContextCompat.getColor(context, R.color.deep_orange_900)),
                new MaterialColor("deep_orange_a_100", ContextCompat.getColor(context, R.color.deep_orange_a_100)),
                new MaterialColor("deep_orange_a_200", ContextCompat.getColor(context, R.color.deep_orange_a_200)),
                new MaterialColor("deep_orange_a_400", ContextCompat.getColor(context, R.color.deep_orange_a_400)),
                new MaterialColor("deep_orange_a_700", ContextCompat.getColor(context, R.color.deep_orange_a_700)),

                new MaterialColor("brown_50", ContextCompat.getColor(context, R.color.brown_50)),
                new MaterialColor("brown_100", ContextCompat.getColor(context, R.color.brown_100)),
                new MaterialColor("brown_200", ContextCompat.getColor(context, R.color.brown_200)),
                new MaterialColor("brown_300", ContextCompat.getColor(context, R.color.brown_300)),
                new MaterialColor("brown_400", ContextCompat.getColor(context, R.color.brown_400)),
                new MaterialColor("brown_500", ContextCompat.getColor(context, R.color.brown_500)),
                new MaterialColor("brown_600", ContextCompat.getColor(context, R.color.brown_600)),
                new MaterialColor("brown_700", ContextCompat.getColor(context, R.color.brown_700)),
                new MaterialColor("brown_800", ContextCompat.getColor(context, R.color.brown_800)),
                new MaterialColor("brown_900", ContextCompat.getColor(context, R.color.brown_900)),

                new MaterialColor("grey_50", ContextCompat.getColor(context, R.color.grey_50)),
                new MaterialColor("grey_100", ContextCompat.getColor(context, R.color.grey_100)),
                new MaterialColor("grey_200", ContextCompat.getColor(context, R.color.grey_200)),
                new MaterialColor("grey_300", ContextCompat.getColor(context, R.color.grey_300)),
                new MaterialColor("grey_400", ContextCompat.getColor(context, R.color.grey_400)),
                new MaterialColor("grey_500", ContextCompat.getColor(context, R.color.grey_500)),
                new MaterialColor("grey_600", ContextCompat.getColor(context, R.color.grey_600)),
                new MaterialColor("grey_700", ContextCompat.getColor(context, R.color.grey_700)),
                new MaterialColor("grey_800", ContextCompat.getColor(context, R.color.grey_800)),
                new MaterialColor("grey_900", ContextCompat.getColor(context, R.color.grey_900)),

                new MaterialColor("blue_grey_50", ContextCompat.getColor(context, R.color.blue_grey_50)),
                new MaterialColor("blue_grey_100", ContextCompat.getColor(context, R.color.blue_grey_100)),
                new MaterialColor("blue_grey_200", ContextCompat.getColor(context, R.color.blue_grey_200)),
                new MaterialColor("blue_grey_300", ContextCompat.getColor(context, R.color.blue_grey_300)),
                new MaterialColor("blue_grey_400", ContextCompat.getColor(context, R.color.blue_grey_400)),
                new MaterialColor("blue_grey_500", ContextCompat.getColor(context, R.color.blue_grey_500)),
                new MaterialColor("blue_grey_600", ContextCompat.getColor(context, R.color.blue_grey_600)),
                new MaterialColor("blue_grey_7000", ContextCompat.getColor(context, R.color.blue_grey_700)),
                new MaterialColor("blue_grey_800", ContextCompat.getColor(context, R.color.blue_grey_800)),
                new MaterialColor("blue_grey_90", ContextCompat.getColor(context, R.color.blue_grey_900)),
        };
    }

    private static class ColorsAdapter extends RecyclerView.Adapter<ColorsAdapter.ViewHolder> {
        private MaterialColor[] mColors;

        private OnClickListener onClickListener;


        public ColorsAdapter(MaterialColor[] colors) {
            mColors = colors;
        }

        public void setOnClickListener(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        @NonNull
        @Override
        public ColorsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.item_color, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ColorsAdapter.ViewHolder holder, final int position) {
            final MaterialColor color = mColors[position];
            holder.name.setText(color.name);
            holder.color.setBackgroundColor(color.colorInt);

            if (color.selected) {
                holder.selected.setVisibility(View.VISIBLE);
            } else {
                holder.selected.setVisibility(View.GONE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(color, position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mColors.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private AppCompatTextView name;
            private AppCompatImageView color;
            private AppCompatImageView selected;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.name);
                color = itemView.findViewById(R.id.color);
                selected = itemView.findViewById(R.id.selected);
            }
        }

        interface OnClickListener {

            void onClick(MaterialColor materialColor, int position);
        }
    }

    private static class MaterialColor {
        private String name;
        @ColorInt
        private int colorInt;
        private boolean selected;

        MaterialColor(String name, int colorInt) {
            this.name = name;
            this.colorInt = colorInt;
            this.selected = false;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) return false;
            if (obj == this) return true;
            if (!(obj instanceof MaterialColor)) return false;
            MaterialColor o = (MaterialColor) obj;
            return o.name.equals(this.name) && o.colorInt == this.colorInt && o.selected == this.selected;
        }
    }
}
