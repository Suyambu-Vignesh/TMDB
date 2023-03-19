package com.tmdb.app.core.shared.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tmdb.app.core.principle.model.ContentModuleModel
import com.tmdb.app.core.shared.repository.api.TmdbRepository
import com.tmdb.app.core.shared.service.TmdbApiSource
import com.tmdb.app.core.principle.usecase.PageLoadException
import com.tmdb.app.core.principle.usecase.Result
import com.tmdb.app.core.principle.usecase.ServiceFailure
import com.tmdb.app.detail.model.MovieTvShowDetail
import com.tmdb.app.home.service.TmdbPopularMoviePagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Impl of TmdbRepository. Helps to connect with TMDB API.
 *
 * @param tmdbPopularMoviePagingSource Paging Source helps to fetch the Popular Movies and TV Shows.
 * @param tmdbApiSource TMDBApiSource Which helps to connect with TMDBApi.
 */
class TmdbRepositoryImpl @Inject constructor(
    private val tmdbPopularMoviePagingSource: TmdbPopularMoviePagingSource,
    private val tmdbApiSource: TmdbApiSource
) : TmdbRepository {
    override suspend fun getPopularMovies(pageNumber: Int): Flow<Result<PagingData<ContentModuleModel>>> {
        return flow {
            try {
                Pager(
                    PagingConfig(20)
                ) {
                    tmdbPopularMoviePagingSource
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
