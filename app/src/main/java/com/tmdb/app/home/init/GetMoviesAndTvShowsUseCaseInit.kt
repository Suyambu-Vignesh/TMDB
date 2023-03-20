package com.tmdb.app.home.init

import com.tmdb.app.core.shared.repository.api.TmdbRepository
import com.tmdb.app.home.GetShowInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GetMoviesAndTvShowsUseCaseInit {

    @Provides
    fun provides(tmdbRepository: TmdbRepository): GetShowInfoUseCase {
        return GetShowInfoUseCase(tmdbRepository)
    }
}
