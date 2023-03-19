package com.tmdb.app.home

import com.tmdb.app.core.principle.usecase.api.UseCase
import kotlinx.coroutines.flow.Flow

/**
 * UseCase for fetching the Latest Movies and TvShows from the API service Through Repository
 */
class GetMoviesAndTvShowsUseCase: UseCase<Any, Any> {
    override suspend fun performTask(input: Any): Flow<Any> {
        TODO("Not yet implemented")
    }
}