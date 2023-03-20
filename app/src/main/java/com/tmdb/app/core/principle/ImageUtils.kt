package com.tmdb.app.core.principle

import android.content.Context
import android.util.DisplayMetrics

object ImageUtils {
    /**
     * Helper fun to get the image size. There were clear doc on image size and so added only 3 image size
     *
     * @param context - Context
     */
    fun getThumbnailImageSize(context: Context): String {
        return when (context.resources.displayMetrics.densityDpi) {
            DisplayMetrics.DENSITY_HIGH -> "w200"
            DisplayMetrics.DENSITY_XHIGH -> "w300"
            else -> "w500"
        }
    }
}
