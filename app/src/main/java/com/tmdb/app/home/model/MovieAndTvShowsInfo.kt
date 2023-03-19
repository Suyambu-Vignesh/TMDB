package com.tmdb.app.home.model

import com.google.gson.annotations.SerializedName
import com.tmdb.app.core.model.ContentModuleModel
import com.tmdb.app.core.model.ModuleType

data class MovieAndTvShowsInfo(
    @SerializedName("adult")
    val adult: Boolean = false,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("genre_ids")
    val genreIds: ArrayList<Int> = arrayListOf(),
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("original_language")
    val originalLanguage: String? = null,
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
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("video")
    val video: Boolean? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null
) : ContentModuleModel {
    override fun getType(): ModuleType {
       return ModuleType.MOVIE_TILE
    }

    override fun getId(): String {
        return (id ?: "") as String
    }
}
