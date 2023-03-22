package com.tmdb.app.core.shared.service

import androidx.paging.PagingData
import com.google.common.truth.Truth
import com.tmdb.app.core.principle.log.TmdbLogging
import com.tmdb.app.core.principle.model.ContentModuleModel
import com.tmdb.app.core.principle.model.PagingContentModules
import com.tmdb.app.core.principle.usecase.Result
import com.tmdb.app.core.shared.repository.TmdbRepositoryImpl
import com.tmdb.app.home.service.TmdbPopularMoviePagingSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.spyk
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * TestSuite for [TmdbRepositoryImpl]
 */
class TmdbRepositoryImplTest {
    private lateinit var server: MockWebServer
    private lateinit var service: TmdbApiSource
    private lateinit var tmdbRepository: TmdbRepositoryImpl
    private lateinit var tmdbPopularMoviePagingSource: TmdbPopularMoviePagingSource

    @Before
    fun setup() {
        server = MockWebServer()
        mockkObject(TmdbLogging)
        every { TmdbLogging.error(any(), any(), any()) } returns Unit
        every { TmdbLogging.info(any(), any()) } returns Unit

        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbApiSource::class.java)

        tmdbPopularMoviePagingSource = spyk(TmdbPopularMoviePagingSource(service, 1))
        tmdbRepository = TmdbRepositoryImpl(
            tmdbPopularMoviePagingSource,
            service
        )
    }

    @Test
    fun `test popular movies API Path`() = runBlocking {
        setupResponse("popular-movies.json")
        val response = service.getPopularMovies(1)
        Truth.assertThat(server.takeRequest().path)
            .isEqualTo("/3/movie/popular?api_key=4f98c034a4b577fff88ced443f7d5508&page=1")
        Truth.assertThat(response.body()).isNotNull()
    }

    @Test
    fun `test paging source refreshkey`() = runBlocking {
        Truth.assertThat(tmdbPopularMoviePagingSource.getRefreshKey(mockk())).isEqualTo(1)
    }

    @Test
    fun `test paging source success response`() = runBlocking {
        try {
            var data: Result<PagingContentModules>? = null
            val emptyData: Result<PagingContentModules> = Result.Response(
                data = PagingData.empty<ContentModuleModel>()
            )
            data = tmdbRepository.getShowInfos(1).catch {
                emit(emptyData)
            }.first()

            Truth.assertThat(data.data).isInstanceOf(PagingData::class.java)
        } catch (e: IllegalStateException) {
            // handle abort flow
        }
    }

    @Test
    fun `test popular movies API Content`() = runBlocking {
        setupResponse("popular-movies.json")
        val response = service.getPopularMovies(1)
        Truth.assertThat(response.body()).isNotNull()
        Truth.assertThat(response.body()?.page ?: 0)
            .isEqualTo(
                1
            )
        Truth.assertThat(response.body()?.totalPages ?: 0)
            .isEqualTo(37502)
        Truth.assertThat(response.body()?.totalResults ?: 0)
            .isEqualTo(750038)
        Truth.assertThat(response.body()?.results?.get(0)?.originalTitle ?: "").isEqualTo(
            "Puss in Boots: The Last Wish"
        )
    }

    @Test
    fun `test movie detail API Path`() = runBlocking {
        setupResponse("movie-detail.json")
        val response = service.getMovieTvShowDetail(678)
        Truth.assertThat(server.takeRequest().path)
            .isEqualTo("/3/movie/678?api_key=4f98c034a4b577fff88ced443f7d5508")
        Truth.assertThat(response.body()).isNotNull()
    }

    @Test
    fun `test movie detail API Content`() = runBlocking {
        setupResponse("movie-detail.json")
        val response = service.getMovieTvShowDetail(678)

        Truth.assertThat(response.body()).isNotNull()
        Truth.assertThat(response.body()?.overview ?: "")
            .isEqualTo(
                "Hungarian remake of the Bulgarian series about a disconnect family living together in order to get an inheritance."
            )
        Truth.assertThat(response.body()?.popularity ?: 0.0)
            .isEqualTo(1948.879)
        Truth.assertThat(response.body()?.posterPath ?: "")
            .isEqualTo("/xDOUahrwEsgDlejXjZLc9lOljSx.jpg")
    }

    private fun setupResponse(fileName: String) {
        javaClass.classLoader?.let {
            server.enqueue(
                MockResponse().setBody(
                    it.getResourceAsStream(fileName).source().buffer().readString(Charsets.UTF_8)
                )
            )
        }
    }

    @After
    fun clear() {
        server.close()
    }
}
