package com.tmdb.app.core.shared.repository.api

import com.tmdb.app.core.principle.model.PagingContentModules
import com.tmdb.app.core.principle.usecase.Result
import com.tmdb.app.detail.model.api.ShowDetail
import kotlinx.coroutines.flow.Flow

/**
 * Repository help to connect with Tmdb Api
 */
interface TmdbRepository {
    /**
     * Will help to fetch the popular movies/tv shows
     *
     * @param pageNumber - PageNumber from which we are looking the popular movies.
     */
    suspend fun getShowInfos(
        pageNumber: Int
    ): Flow<Result<PagingContentModules>>

    /**
     * Will help to fetch the detail of a Movie or TvShow
     *
     * @param id Id of the movies or tv show
     */
    suspend fun getShowsDetails(
        id: Int
    ): Flow<Result<ShowDetail>>
}
