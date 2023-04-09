package com.tmdb.app.detail

import com.tmdb.app.core.principle.usecase.Result
import com.tmdb.app.core.principle.usecase.api.UseCase
import com.tmdb.app.core.shared.repository.api.TmdbRepository
import com.tmdb.app.detail.model.api.ShowDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase help to get the show Info
 */
class GetShowDetailUseCase @Inject constructor(
    private val tmdbRepository: TmdbRepository
) : UseCase<Result<ShowDetail>, Int> {
    /**
     * Help to fetch the detail info about the show
     */
    override suspend fun performTask(input: Int): Flow<Result<ShowDetail>> {
        return tmdbRepository.getShowsDetails(input)
    }
}
