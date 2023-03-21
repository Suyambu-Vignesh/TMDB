package com.tmdb.app.core.shared.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.tmdb.app.core.principle.TmdbLogging
import com.tmdb.app.core.principle.model.PagingContentModules
import com.tmdb.app.core.principle.usecase.GenericFailure
import com.tmdb.app.core.principle.usecase.NoInternetFailure
import com.tmdb.app.core.principle.usecase.PageLoadException
import com.tmdb.app.core.principle.usecase.Result
import com.tmdb.app.core.principle.usecase.ServiceFailure
import com.tmdb.app.core.principle.usecase.TimeOutFailure
import com.tmdb.app.core.shared.repository.api.TmdbRepository
import com.tmdb.app.core.shared.service.TmdbApiSource
import com.tmdb.app.detail.model.ShowDetail
import com.tmdb.app.home.service.TmdbPopularMoviePagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException
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

    /**
     * Helper method to get [Result.Error] from exe
     *
     * @return [Result.Error] based on exe
     */
    private fun <DataType> Exception.getError(): Result.Error<DataType> {
        return when {
            (this.isConnectionException()) -> {
                Result.Error<DataType>(
                    NoInternetFailure(),
                    throwable = this.cause
                )
            }

            (this.isTimeOutExe()) -> {
                Result.Error<DataType>(
                    TimeOutFailure(),
                    throwable = this.cause
                )
            }

            (this is HttpException) -> {
                Result.Error<DataType>(
                    ServiceFailure(errorCode = this.code()),
                    this.message(),
                    throwable = this.cause
                )
            }

            else -> {
                Result.Error<DataType>(
                    GenericFailure(),
                    throwable = this.cause
                )
            }
        }
    }

    /**
     * Helper method help to say if exe is No Internet Exe
     *
     * @return True if Exe if of Type No Internet
     */
    private fun Exception.isConnectionException(): Boolean {
        return (this is UnknownServiceException || this is UnknownHostException || this is ConnectException)
    }

    /**
     * Helper method help to say if exe is TimeOut Exe
     *
     * @return True if Exe if of Type Timeout
     */
    private fun Exception.isTimeOutExe(): Boolean {
        return this is SocketTimeoutException
    }
}
