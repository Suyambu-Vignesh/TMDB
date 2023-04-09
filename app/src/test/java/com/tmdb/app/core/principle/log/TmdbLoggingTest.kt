package com.tmdb.app.core.principle.log

import com.tmdb.app.core.principle.log.api.AppLogger
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class TmdbLoggingTest {

    @Test
    fun `test log info`() {
        val mock = mockk<AppLogger>(
            relaxed = true,
            relaxUnitFun = true
        )
        TmdbLogging.logger = mock

        TmdbLogging.info("tag", "message", null)

        verify(exactly = 1) { mock.info(any(), any(), any()) }
        verify(exactly = 0) { mock.verbose(any(), any(), any()) }
        verify(exactly = 0) { mock.debug(any(), any(), any()) }
        verify(exactly = 0) { mock.error(any(), any(), any()) }
        verify(exactly = 0) { mock.warning(any(), any(), any()) }
    }

    @Test
    fun `test log info with exe`() {
        val mock = mockk<AppLogger>(
            relaxed = true,
            relaxUnitFun = true
        )
        TmdbLogging.logger = mock

        TmdbLogging.info("tag", "message", java.lang.RuntimeException())

        verify(exactly = 1) { mock.info(any(), any(), any()) }
        verify(exactly = 0) { mock.verbose(any(), any(), any()) }
        verify(exactly = 0) { mock.debug(any(), any(), any()) }
        verify(exactly = 0) { mock.error(any(), any(), any()) }
        verify(exactly = 0) { mock.warning(any(), any(), any()) }
    }

    @Test
    fun `test log error`() {
        val mock = mockk<AppLogger>(
            relaxed = true,
            relaxUnitFun = true
        )
        TmdbLogging.logger = mock

        TmdbLogging.error("tag", "message", null)

        verify(exactly = 0) { mock.info(any(), any(), any()) }
        verify(exactly = 0) { mock.verbose(any(), any(), any()) }
        verify(exactly = 0) { mock.debug(any(), any(), any()) }
        verify(exactly = 1) { mock.error(any(), any(), any()) }
        verify(exactly = 0) { mock.warning(any(), any(), any()) }
    }

    @Test
    fun `test log error with exe`() {
        val mock = mockk<AppLogger>(
            relaxed = true,
            relaxUnitFun = true
        )
        TmdbLogging.logger = mock

        TmdbLogging.error("tag", "message", java.lang.RuntimeException())

        verify(exactly = 0) { mock.info(any(), any(), any()) }
        verify(exactly = 0) { mock.verbose(any(), any(), any()) }
        verify(exactly = 0) { mock.debug(any(), any(), any()) }
        verify(exactly = 1) { mock.error(any(), any(), any()) }
        verify(exactly = 0) { mock.warning(any(), any(), any()) }
    }

    @Test
    fun `test log verbose`() {
        val mock = mockk<AppLogger>(
            relaxed = true,
            relaxUnitFun = true
        )
        TmdbLogging.logger = mock

        TmdbLogging.verbose("tag", "message", null)

        verify(exactly = 0) { mock.info(any(), any(), any()) }
        verify(exactly = 1) { mock.verbose(any(), any(), any()) }
        verify(exactly = 0) { mock.debug(any(), any(), any()) }
        verify(exactly = 0) { mock.error(any(), any(), any()) }
        verify(exactly = 0) { mock.warning(any(), any(), any()) }
    }

    @Test
    fun `test log verbose with exe`() {
        val mock = mockk<AppLogger>(
            relaxed = true,
            relaxUnitFun = true
        )
        TmdbLogging.logger = mock

        TmdbLogging.verbose("tag", "message", java.lang.RuntimeException())

        verify(exactly = 0) { mock.info(any(), any(), any()) }
        verify(exactly = 1) { mock.verbose(any(), any(), any()) }
        verify(exactly = 0) { mock.debug(any(), any(), any()) }
        verify(exactly = 0) { mock.error(any(), any(), any()) }
        verify(exactly = 0) { mock.warning(any(), any(), any()) }
    }

    @Test
    fun `test log debug`() {
        val mock = mockk<AppLogger>(
            relaxed = true,
            relaxUnitFun = true
        )
        TmdbLogging.logger = mock

        TmdbLogging.debug("tag", "message", null)

        verify(exactly = 0) { mock.info(any(), any(), any()) }
        verify(exactly = 0) { mock.verbose(any(), any(), any()) }
        verify(exactly = 1) { mock.debug(any(), any(), any()) }
        verify(exactly = 0) { mock.error(any(), any(), any()) }
        verify(exactly = 0) { mock.warning(any(), any(), any()) }
    }

    @Test
    fun `test log debug with exe`() {
        val mock = mockk<AppLogger>(
            relaxed = true,
            relaxUnitFun = true
        )
        TmdbLogging.logger = mock

        TmdbLogging.debug("tag", "message", java.lang.RuntimeException())

        verify(exactly = 0) { mock.info(any(), any(), any()) }
        verify(exactly = 0) { mock.verbose(any(), any(), any()) }
        verify(exactly = 1) { mock.debug(any(), any(), any()) }
        verify(exactly = 0) { mock.error(any(), any(), any()) }
        verify(exactly = 0) { mock.warning(any(), any(), any()) }
    }

    @Test
    fun `test log warning`() {
        val mock = mockk<AppLogger>(
            relaxed = true,
            relaxUnitFun = true
        )
        TmdbLogging.logger = mock

        TmdbLogging.warning("tag", "message", null)

        verify(exactly = 0) { mock.info(any(), any(), any()) }
        verify(exactly = 0) { mock.verbose(any(), any(), any()) }
        verify(exactly = 0) { mock.debug(any(), any(), any()) }
        verify(exactly = 0) { mock.error(any(), any(), any()) }
        verify(exactly = 1) { mock.warning(any(), any(), any()) }
    }

    @Test
    fun `test log warning with exe`() {
        val mock = mockk<AppLogger>(
            relaxed = true,
            relaxUnitFun = true
        )
        TmdbLogging.logger = mock

        TmdbLogging.warning("tag", "message", java.lang.RuntimeException())

        verify(exactly = 0) { mock.info(any(), any(), any()) }
        verify(exactly = 0) { mock.verbose(any(), any(), any()) }
        verify(exactly = 0) { mock.debug(any(), any(), any()) }
        verify(exactly = 0) { mock.error(any(), any(), any()) }
        verify(exactly = 1) { mock.warning(any(), any(), any()) }
    }
}
