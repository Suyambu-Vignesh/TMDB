package com.tmdb.app.core.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tmdb.app.core.model.ContentModuleModel
import com.tmdb.app.core.repository.api.TmdbRepository
import com.tmdb.app.core.service.TmdbApiSource
import com.tmdb.app.core.usecase.GenericFailure
import com.tmdb.app.core.usecase.PageLoadException
import com.tmdb.app.core.usecase.Result
import com.tmdb.app.core.usecase.ServiceFailure
import com.tmdb.app.detail.model.MovieTvShowDetail
import com.tmdb.app.home.service.TmdbPagingSource
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.isActive
import kotlinx.coroutines.isActive
import javax.inject.Inject

/**
 * Impl of TmdbRepository. Helps to connect with TMDB API.
 *
 * @param tmdbPagingSource Paging Source helps to fetch the Popular Movies and TV Shows.
 * @param tmdbApiSource TMDBApiSource Which helps to connect with TMDBApi.
 */
class TmdbRepositoryImpl @Inject constructor(
    private val tmdbPagingSource: TmdbPagingSource,
    private val tmdbApiSource: TmdbApiSource
) : TmdbRepository {
    override suspend fun getPopularMovies(pageNumber: Int): Flow<Result<PagingData<ContentModuleModel>>> {
        return flow {
            try {
                Pager(
                    PagingConfig(20)
                ) {
                    tmdbPagingSource
                }.flow.distinctUntilChanged().collect {
                    emit(Result.Response(it))
                }
            } catch (pageLoadException: PageLoadException) {
                emit(
                    Result.Error<PagingData<ContentModuleModel>>(
                        pageLoadException.failure,
                        throwable = pageLoadException.cause
                    )
                )
            }
        }
    }

    override suspend fun getMovieTvShowDetail(id: String): Flow<Result<MovieTvShowDetail>> {
        return flow {
            val result = tmdbApiSource.getMovieTvShowDetail(id)
            result.body()?.let {
                Result.Response(
                    data = it
                )
            } ?: kotlin.run {
                Result.Error(
                    message = result.errorBody()?.string(),
                    failure = ServiceFailure(result.code())
                )
            }
        }
    }
}
