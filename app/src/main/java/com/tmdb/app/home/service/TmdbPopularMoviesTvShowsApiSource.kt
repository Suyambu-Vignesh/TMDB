package com.tmdb.app.home.service

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tmdb.app.core.principle.model.ContentModuleModel
import com.tmdb.app.core.shared.service.TmdbApiSource
import com.tmdb.app.core.principle.usecase.PageLoadException
import com.tmdb.app.core.principle.usecase.ServiceFailure
import com.tmdb.app.home.model.MovieAndTvShowCollections
import retrofit2.Response

/**
 * Paging API for fetching the popular movies/tv shows
 *
 * @param tmdbApiSource [TmdbApiSource] to fetch data from tmdb api
 * @param refreshKey Initial or refresh key
 */
class TmdbPopularMoviePagingSource(
    private val tmdbApiSource: TmdbApiSource,
    private val refreshKey: Int
) : PagingSource<Int, ContentModuleModel>() {
    override fun getRefreshKey(state: PagingState<Int, ContentModuleModel>): Int? {
        return refreshKey
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ContentModuleModel> {
        val currentPageNumber = params.key ?: 1
        return tmdbApiSource.getPopularMovies(pageNumber = currentPageNumber)
            .getCollectionResult(currentPageNumber)
    }
}

/**
 * Extension Function which helps to convert the [Response] to [ContentModuleCollectionResult]
 */
private fun Response<MovieAndTvShowCollections>.getCollectionResult(
    currentPageNumber: Int
): PagingSource.LoadResult<Int, ContentModuleModel> {
    try {
        this.body()?.let {
            return PagingSource.LoadResult.Page(
                data = it.results,
                prevKey = if (currentPageNumber == 1) null else currentPageNumber - 1,
                nextKey = currentPageNumber + 1
            )
        }
    } catch (exe: Exception) {
        return PagingSource.LoadResult.Error(PageLoadException(failure = ServiceFailure(this.code()), cause = exe))
    }

    return PagingSource.LoadResult.Error(PageLoadException(ServiceFailure(this.code())))
}
