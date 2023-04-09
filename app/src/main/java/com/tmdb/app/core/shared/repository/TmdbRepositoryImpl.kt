package com.tmdb.app.core.shared.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.tmdb.app.core.principle.getError
import com.tmdb.app.core.principle.log.TmdbLogging
import com.tmdb.app.core.principle.model.PagingContentModules
import com.tmdb.app.core.principle.usecase.PageLoadException
import com.tmdb.app.core.principle.usecase.Result
import com.tmdb.app.core.principle.usecase.ServiceFailure
import com.tmdb.app.core.shared.repository.api.TmdbRepository
import com.tmdb.app.core.shared.service.TmdbApiSource
import com.tmdb.app.detail.model.api.ShowDetail
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
    override suspend fun getShowInfos(pageNumber: Int): Flow<Result<PagingContentModules>> {
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
                TmdbLogging.error(toString(), pageLoadException.message ?: "", pageLoadException)
                emit(
                    Result.Error<PagingContentModules>(
                        pageLoadException.failure,
                        throwable = pageLoadException.cause
                    )
                )
            } catch (exe: Exception) {
                TmdbLogging.error(toString(), exe.message ?: "", exe)
                emit(
                    exe.getError()
                )
            }
        }
    }

    override suspend fun getShowsDetails(id: Int): Flow<Result<ShowDetail>> {
        return flow {
            try {
                val result = tmdbApiSource.getMovieTvShowDetail(id)
                result.body()?.let {
                    emit(
                        Result.Response(
                            data = it
                        )
                    )
                } ?: kotlin.run {
                    emit(
                        Result.Error(
                            message = result.errorBody()?.string(),
                            failure = ServiceFailure(result.code())
                        )
                    )
                }
            } catch (exe: Exception) {
                TmdbLogging.error(toString(), exe.message ?: "", exe)
                emit(
                    exe.getError()
                )
            }
        }
    }
}
