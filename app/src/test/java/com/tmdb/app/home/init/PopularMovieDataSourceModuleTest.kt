package com.tmdb.app.home.init

import com.google.common.truth.Truth
import com.tmdb.app.core.shared.init.APISourceModule
import com.tmdb.app.home.service.TmdbPopularMoviePagingSource
import org.junit.Test

/**
 * Testsuite for [PopularMovieDataSourceModule]
 */
class PopularMovieDataSourceModuleTest {

    @Test
    fun `test instance of PopularMovieDataSourceModule`() {
        val module = PopularMovieDataSourceModule()
        val apiModule = APISourceModule()
        val apiSource = apiModule.providesTmdbApiSource(apiModule.provideRetrofitAPI())

        Truth.assertThat(module.provides(apiSource)).isInstanceOf(TmdbPopularMoviePagingSource::class.java)
    }
}
