package com.tmdb.app.core.shared.init

import com.google.common.truth.Truth
import com.tmdb.app.core.shared.repository.TmdbRepositoryImpl
import com.tmdb.app.core.shared.service.TmdbApiSource
import com.tmdb.app.home.service.TmdbPopularMoviePagingSource
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Testsuite for [TmdbRepositoryInit]
 */
class TmdbRepositoryInitTest {

    @Test
    fun `test instance of TmdbRepository`() {
        val module = TmdbRepositoryInit()

        val server = MockWebServer()
        val service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbApiSource::class.java)
        val tmdbPopularMoviePagingSource = TmdbPopularMoviePagingSource(service, 1)

        val tmdbRepository = module.providesTmdbRepository(tmdbPopularMoviePagingSource, service)

        Truth.assertThat(tmdbRepository).isInstanceOf(TmdbRepositoryImpl::class.java)
    }
}
