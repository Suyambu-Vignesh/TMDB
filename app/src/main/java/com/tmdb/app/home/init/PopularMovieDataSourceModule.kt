package com.tmdb.app.home.init

import com.tmdb.app.core.shared.service.TmdbApiSource
import com.tmdb.app.home.service.TmdbPopularMoviePagingSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PopularMovieDataSourceModule {
    @Provides
    fun provides(tmdbApiSource: TmdbApiSource): TmdbPopularMoviePagingSource {
        return TmdbPopularMoviePagingSource(tmdbApiSource, 1)
    }
}