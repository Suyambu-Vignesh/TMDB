package com.tmdb.app.core.principle

import android.content.Context
import android.util.DisplayMetrics
import com.tmdb.app.BuildConfig

object ImageUtils {
    /**
     * Helper fun to get the image size. There were clear doc on image size and so added only 3 image size
     *
     * @param context - Context
     * @param imageUrl - ImageUrl
     */
    fun getThumbnailImageSize(context: Context, imgUrl: String): String {
        val size = when (context.resources.displayMetrics.densityDpi) {
            DisplayMetrics.DENSITY_HIGH -> "w200"
            DisplayMetrics.DENSITY_XHIGH -> "w300"
            else -> "w500"
        }

        return BuildConfig.TMDB_API_IMAGE_BASE_URL + size + "/" + imgUrl
    }

    /**
     * Helper fun to get the image size. There were clear doc on image size and so added only 3 image size
     *
     * @param context - Context
     * @param imageUrl - ImageUrl
     */
    fun getPosterImageSize(context: Context, imgUrl: String): String {
        val size = when (context.resources.displayMetrics.densityDpi) {
            DisplayMetrics.DENSITY_HIGH -> "w300"
            DisplayMetrics.DENSITY_XHIGH -> "w500"
            else -> "w780"
        }

        return BuildConfig.TMDB_API_IMAGE_BASE_URL + size + "/" + imgUrl
    }
}
