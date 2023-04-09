package com.tmdb.app.core.principle.log.impl

import android.util.Log
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class AppProdLoggerImplTest {

    @Before
    fun setup() {
        mockkStatic(Log::class)
    }

    @Test
    fun `test AppLogger error`() {

        val appLogger = AppProdLoggerImpl()
        appLogger.error("tag", "message", null)

        verify(exactly = 0) { Log.e("tag", "message", null) }
        verify(exactly = 0) { Log.d("tag", "message", null) }
        verify(exactly = 0) { Log.v("tag", "message", null) }
        verify(exactly = 0) { Log.i("tag", "message", null) }
        verify(exactly = 0) { Log.w("tag", "message", null) }
    }

    @Test
    fun `test AppLogger error with error`() {

        val appLogger = AppProdLoggerImpl()
        val exe = java.lang.RuntimeException()
        appLogger.error("tag", "message", exe)

        verify(exactly = 0) { Log.e("tag", "message", exe) }
        verify(exactly = 0) { Log.d("tag", "message", null) }
        verify(exactly = 0) { Log.v("tag", "message", null) }
        verify(exactly = 0) { Log.i("tag", "message", null) }
        verify(exactly = 0) { Log.w("tag", "message", null) }
    }

    @Test
    fun `test AppLogger verbose`() {

        val appLogger = AppProdLoggerImpl()
        appLogger.verbose("tag", "message", null)

        verify(exactly = 0) { Log.v("tag", "message", null) }
        verify(exactly = 0) { Log.d("tag", "message", null) }
        verify(exactly = 0) { Log.e("tag", "message", null) }
        verify(exactly = 0) { Log.i("tag", "message", null) }
        verify(exactly = 0) { Log.w("tag", "message", null) }
    }

    @Test
    fun `test AppLogger verbose with exe`() {

        val appLogger = AppProdLoggerImpl()
        val exe = java.lang.RuntimeException()
        appLogger.verbose("tag", "message", exe)

        verify(exactly = 0) { Log.v("tag", "message", exe) }
        verify(exactly = 0) { Log.d("tag", "message", null) }
        verify(exactly = 0) { Log.e("tag", "message", null) }
        verify(exactly = 0) { Log.i("tag", "message", null) }
        verify(exactly = 0) { Log.w("tag", "message", null) }
    }

    @Test
    fun `test AppLogger info`() {

        val appLogger = AppProdLoggerImpl()
        appLogger.info("tag", "message", null)

        verify(exactly = 0) { Log.v("tag", "message", null) }
        verify(exactly = 0) { Log.d("tag", "message", null) }
        verify(exactly = 0) { Log.e("tag", "message", null) }
        verify(exactly = 0) { Log.i("tag", "message", null) }
        verify(exactly = 0) { Log.w("tag", "message", null) }
    }

    @Test
    fun `test AppLogger info with exe`() {

        val appLogger = AppProdLoggerImpl()
        val exe = java.lang.RuntimeException()
        appLogger.info("tag", "message", exe)

        verify(exactly = 0) { Log.v("tag", "message", null) }
        verify(exactly = 0) { Log.d("tag", "message", null) }
        verify(exactly = 0) { Log.e("tag", "message", null) }
        verify(exactly = 0) { Log.i("tag", "message", exe) }
        verify(exactly = 0) { Log.w("tag", "message", null) }
    }

    @Test
    fun `test AppLogger warning`() {

        val appLogger = AppProdLoggerImpl()
        appLogger.warning("tag", "message", null)

        verify(exactly = 0) { Log.v("tag", "message", null) }
        verify(exactly = 0) { Log.d("tag", "message", null) }
        verify(exactly = 0) { Log.e("tag", "message", null) }
        verify(exactly = 0) { Log.i("tag", "message", null) }
        verify(exactly = 0) { Log.w("tag", "message", null) }
    }

    @Test
    fun `test AppLogger warning with exe`() {

        val appLogger = AppProdLoggerImpl()
        val exe = java.lang.RuntimeException()
        appLogger.warning("tag", "message", exe)

        verify(exactly = 0) { Log.v("tag", "message", null) }
        verify(exactly = 0) { Log.d("tag", "message", null) }
        verify(exactly = 0) { Log.e("tag", "message", null) }
        verify(exactly = 0) { Log.i("tag", "message", null) }
        verify(exactly = 0) { Log.w("tag", "message", exe) }
    }

    @Test
    fun `test AppLogger debug`() {

        val appLogger = AppProdLoggerImpl()
        appLogger.debug("tag", "message", null)

        verify(exactly = 0) { Log.v("tag", "message", null) }
        verify(exactly = 0) { Log.d("tag", "message", null) }
        verify(exactly = 0) { Log.e("tag", "message", null) }
        verify(exactly = 0) { Log.i("tag", "message", null) }
        verify(exactly = 0) { Log.w("tag", "message", null) }
    }

    @Test
    fun `test AppLogger debug with exe`() {

        val appLogger = AppProdLoggerImpl()
        val exe = java.lang.RuntimeException()
        appLogger.debug("tag", "message", exe)

        verify(exactly = 0) { Log.v("tag", "message", null) }
        verify(exactly = 0) { Log.d("tag", "message", exe) }
        verify(exactly = 0) { Log.e("tag", "message", null) }
        verify(exactly = 0) { Log.i("tag", "message", null) }
        verify(exactly = 0) { Log.w("tag", "message", null) }
    }
}
