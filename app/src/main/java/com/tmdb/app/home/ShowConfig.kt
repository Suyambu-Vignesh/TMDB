package com.tmdb.app.home

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Helps to give a detail about the show config. Can we be extended for more
 */
sealed class ShowConfig() : Parcelable {
    @Parcelize
    class PopularMovieShowConfig : ShowConfig()
}
