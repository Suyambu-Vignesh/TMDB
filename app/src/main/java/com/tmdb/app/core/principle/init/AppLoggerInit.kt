package com.tmdb.app.core.principle.init

import com.tmdb.app.BuildConfig
import com.tmdb.app.core.principle.log.api.AppLogger
import com.tmdb.app.core.principle.log.impl.AppDebugLoggerImpl
import com.tmdb.app.core.principle.log.impl.AppProdLoggerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppLoggerInit {
    @Provides
    fun provides(): AppLogger {
        return if (BuildConfig.DEBUG) {
            AppDebugLoggerImpl()
        } else {
            AppProdLoggerImpl()
        }
    }
}
