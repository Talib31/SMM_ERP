package com.android.erp.Utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class GeneralUtils {
    public static int convertDpToPixel(int dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        int px = (int) (dp * (metrics.densityDpi / 160f));
        return Math.round(px);
    }
}
