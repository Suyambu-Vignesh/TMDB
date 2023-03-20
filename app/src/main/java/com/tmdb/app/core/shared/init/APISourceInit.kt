package com.tmdb.app.core.shared.init

import com.tmdb.app.BuildConfig
import com.tmdb.app.core.shared.service.TmdbApiSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class APISourceInit {
    @Provides
    fun providesTmdbApiSource(retrofit: Retrofit): TmdbApiSource {
        return retrofit.create(TmdbApiSource::class.java)
    }

    @Provides
    fun provideRetrofitAPI(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.TMDB_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
