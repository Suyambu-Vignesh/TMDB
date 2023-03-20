package com.tmdb.app.core.shared.init

import com.google.common.truth.Truth
import com.tmdb.app.core.shared.service.TmdbApiSource
import org.junit.Test

/**
 * TestSuite for [APISourceInit]
 */
class APISourceInitTest {

    @Test
    fun `test new instance of Retrofit`() {
        val module = APISourceInit()
        val retrofit = module.provideRetrofitAPI()
        val url = retrofit.baseUrl().host
        Truth.assertThat(url).isEqualTo("api.themoviedb.org")
    }

    @Test
    fun `test new instance of providesTmdbApiSource`() {
        val module = APISourceInit()
        val apiSource = module.providesTmdbApiSource(module.provideRetrofitAPI())

        Truth.assertThat(apiSource).isInstanceOf(TmdbApiSource::class.java)
    }
}
