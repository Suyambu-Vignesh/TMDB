package com.tmdb.app.detail.model

import com.google.gson.annotations.SerializedName
import com.tmdb.app.R

data class ShowDetail(
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("budget")
    val budget: Int? = null,
    @SerializedName("genres")
    val genres: ArrayList<Genres?>? = null,
    @SerializedName("homepage")
    val homepage: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("imdb_id")
    val imdbId: String? = null,
    @SerializedName("original_title")
    val originalTitle: String? = null,
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("popularity")
    val popularity: Double? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    @SerializedName("runtime")
    val runtime: Int? = null,
    @SerializedName("spoken_languages")
    val spokenLanguages: ArrayList<SpokenLanguages??>,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("video")
    val video: Boolean? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null
) {

    companion object {
        const val STATUS_PLANNED = "Planned"
        const val STATUS_IN_PRODUCTION = "In Production"
        const val STATUS_POST_PRODUCTION = "Post Production"
        const val STATUS_RELEASED = "Released"
        const val STATUS_CANCELED = "Canceled"
    }

    fun getStatusColor(): Int? {
        if (status == null) {
            return null
        }

        return when (status) {
            STATUS_PLANNED -> R.color.status_planned
            STATUS_IN_PRODUCTION -> R.color.status_in_prod
            STATUS_POST_PRODUCTION -> R.color.status_post_prod
            STATUS_RELEASED -> R.color.status_released
            STATUS_CANCELED -> R.color.status_cancelled
            else -> R.color.light_gray
        }
    }
}
