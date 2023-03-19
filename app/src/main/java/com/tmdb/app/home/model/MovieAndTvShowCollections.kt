package com.tmdb.app.home.model

import com.google.gson.annotations.SerializedName

/**
 * Data Model holds the collection of Movies/TvShows.
 *
 * @param page The current pageNumber of the collection
 * @param results Collection of Movies/TvShows.
 * @param totalPages Total number of pages are the for the collection
 * @param totalResults total number of Movies/TvShows.
 */
data class MovieAndTvShowCollections(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: ArrayList<MovieAndTvShowsInfo> = arrayListOf(),
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)
