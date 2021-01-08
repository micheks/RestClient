package com.alliky.http.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.alliky.http.config.Config;

/**
 * Author wxianing
 * date 2018/6/26
 */
public final class DimenUtil {

    public static int getScreenWidth() {
        final Resources resources = Config.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        final Resources resources = Config.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
