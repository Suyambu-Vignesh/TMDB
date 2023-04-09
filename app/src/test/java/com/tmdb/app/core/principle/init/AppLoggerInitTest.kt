package com.tmdb.app.core.principle.init

import com.google.common.truth.Truth
import com.tmdb.app.core.principle.log.impl.AppDebugLoggerImpl
import com.tmdb.app.core.principle.log.impl.AppProdLoggerImpl
import com.tmdb.app.utils.MockBuildConfig
import org.junit.Test

class AppLoggerInitTest {

    @Test
    fun `test App Logger Init when build config is debug`() {
        MockBuildConfig.mockBuildConfigDebug(
            "DEBUG",
            true
        )
        Truth.assertThat(AppLoggerInit().provides()).isInstanceOf(AppDebugLoggerImpl::class.java)
    }

    @Test
    fun `test App Logger Init when build config is prod`() {
        MockBuildConfig.mockBuildConfigDebug(
            "DEBUG",
            false
        )
        Truth.assertThat(AppLoggerInit().provides()).isInstanceOf(AppProdLoggerImpl::class.java)
    }
}
