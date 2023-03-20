package com.tmdb.app.core.shared.init

import com.tmdb.app.core.shared.repository.TmdbRepositoryImpl
import com.tmdb.app.core.shared.repository.api.TmdbRepository
import com.tmdb.app.core.shared.service.TmdbApiSource
import com.tmdb.app.home.service.TmdbPopularMoviePagingSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class TmdbRepositoryInit {

    @Provides
    fun providesTmdbRepository(
        tmdbPopularMoviePagingSource: TmdbPopularMoviePagingSource,
        tmdbApiSource: TmdbApiSource
    ): TmdbRepository {
        return TmdbRepositoryImpl(
            tmdbPopularMoviePagingSource,
            tmdbApiSource
        )
    }
}
