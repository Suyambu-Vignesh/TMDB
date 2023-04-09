package com.tmdb.app.detail.model

import com.google.gson.annotations.SerializedName
import com.tmdb.app.detail.model.api.ShowDetail
import com.tmdb.app.detail.model.api.ShowGenre
import com.tmdb.app.detail.model.api.ShowLanguage

data class ShowDetailImpl(
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
    val spokenLanguages: ArrayList<SpokenLanguages?>?,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("video")
    val video: Boolean? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null
) : ShowDetail {

    private var showGenres: ArrayList<ShowGenre>? = null

    companion object {
        const val STATUS_PLANNED = "Planned"
        const val STATUS_IN_PRODUCTION = "In Production"
        const val STATUS_POST_PRODUCTION = "Post Production"
        const val STATUS_RELEASED = "Released"
        const val STATUS_CANCELED = "Canceled"
    }

    override fun getShowScore(): Double? {
        return voteAverage
    }

    override fun getShowOverview(): String? {
        return overview
    }

    override fun getShowTitle(): String? {
        return originalTitle
    }

    override fun getImageUrlPath(): String? {
        return backdropPath
    }

    override fun getShowImdbId(): String? {
        return imdbId
    }

    override fun getShowGenre(): ArrayList<ShowGenre>? {
        return genres?.let {
            val genres: ArrayList<ShowGenre> = ArrayList()
            for (genre in it) {
                if (genre?.getShowGenre() == null || genre.getShowGenre().isEmpty()) {
                    continue
                }

                genres.add(genre)
            }
            genres
        } ?: null
    }

    override fun getShowReleaseDate(): String? {
        return releaseDate
    }

    override fun getShowRunningTime(): String? {
        return runtime?.let {
            it.toString()
        }
    }

    override fun getShowsHomePageDestination(): String? {
        return homepage
    }

    override fun getShowLanguages(): ArrayList<ShowLanguage>? {
        return spokenLanguages?.let {
            val listOfLanguages = ArrayList<ShowLanguage>()

            for (languageInfo in it) {
                languageInfo?.name?.let {
                    listOfLanguages.add(languageInfo)
                }
            }

            listOfLanguages
        } ?: null
    }

    override fun getShowReleaseStatus(): String? {
        return status
    }
}
