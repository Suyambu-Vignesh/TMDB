package com.tmdb.app.home

import androidx.paging.PagingData
import com.tmdb.app.core.principle.model.ContentModuleModel
import com.tmdb.app.core.principle.model.PagingContentModules
import com.tmdb.app.core.principle.usecase.Result
import com.tmdb.app.core.principle.usecase.api.UseCase
import com.tmdb.app.core.shared.repository.api.TmdbRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase for fetching the Latest Movies and TvShows from the API service Through Repository
 *
 * @param tmdbRepository - instance of [TmdbRepository]
 */
class GetShowInfoUseCase @Inject constructor(
    private val tmdbRepository: TmdbRepository
) : UseCase<Result<PagingData<ContentModuleModel>>, Int> {
    override suspend fun performTask(pageNumber: Int): Flow<Result<PagingContentModules>> {
        return tmdbRepository.getPopularMovies(pageNumber)
    }
}
