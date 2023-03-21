package com.tmdb.app.detail.init

import com.tmdb.app.core.shared.repository.api.TmdbRepository
import com.tmdb.app.detail.GetShowDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GetShowDetailUseCaseInit {

    @Provides
    fun provides(tmdbRepository: TmdbRepository): GetShowDetailUseCase {
        return GetShowDetailUseCase(tmdbRepository)
    }
}
