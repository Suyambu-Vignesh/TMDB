package com.tmdb.app.home.init

import com.google.common.truth.Truth
import com.tmdb.app.core.shared.init.APISourceInit
import com.tmdb.app.home.service.TmdbPopularMoviePagingSource
import org.junit.Test

/**
 * Testsuite for [PopularMovieDataSourceInit]
 */
class PopularMovieDataSourceInitTest {

    @Test
    fun `test instance of PopularMovieDataSourceModule`() {
        val module = PopularMovieDataSourceInit()
        val apiModule = APISourceInit()
        val apiSource = apiModule.providesTmdbApiSource(apiModule.provideRetrofitAPI())

        Truth.assertThat(module.provides(apiSource)).isInstanceOf(TmdbPopularMoviePagingSource::class.java)
    }
}
