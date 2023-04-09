package com.tmdb.app.core.shared.service

import com.tmdb.app.BuildConfig
import com.tmdb.app.detail.model.ShowDetailImpl
import com.tmdb.app.home.model.MovieAndTvShowCollections
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API interface to access TMDB API.
 */
interface TmdbApiSource {
    /**
     * TMDB API to get the popular movies
     *
     * @param pageNumber - PageNumber from which we are looking the popular movies.
     */
    @GET("${BuildConfig.TMBD_API_VERSION}/movie/popular?api_key=${BuildConfig.TMDB_API_KEY}")
    suspend fun getPopularMovies(
        @Query("page")
        pageNumber: Int
    ): Response<MovieAndTvShowCollections>

    /**
     * TMDB API to get the detail of a Movie or TvShow
     *
     * @param movieId Id of the movies or tv show
     */
    @GET("${BuildConfig.TMBD_API_VERSION}/movie/{id}?api_key=${BuildConfig.TMDB_API_KEY}")
    suspend fun getMovieTvShowDetail(
        @Path("id")
        id: Int
    ): Response<ShowDetailImpl>
}
