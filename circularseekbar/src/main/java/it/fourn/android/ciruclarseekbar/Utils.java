package it.fourn.android.ciruclarseekbar;

import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;

class Utils {

    static int pxToDp(float px) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return Math.round(px / (displayMetrics.density / DisplayMetrics.DENSITY_DEFAULT));
    }

    static int dpToPx(float dp) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.density / DisplayMetrics.DENSITY_DEFAULT));
    }

    static int removeAlpha(int color) {
        return Color.rgb(Color.red(color), Color.green(color), Color.blue(color));
    }
}
