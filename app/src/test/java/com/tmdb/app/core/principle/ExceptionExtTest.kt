package com.tmdb.app.core.principle

import com.google.common.truth.Truth
import com.tmdb.app.core.principle.usecase.NoInternetFailure
import com.tmdb.app.core.principle.usecase.Result
import com.tmdb.app.core.principle.usecase.TimeOutFailure
import org.junit.Test
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException

/**
 * Test suite for [ExceptionExt]
 */
class ExceptionExtTest {

    @Test
    fun `test UnknownServiceException gives NoInternetFailure`() {
        val exe = UnknownServiceException()
        val error: Result<Any> = exe.getError()

        Truth.assertThat(exe.getFailure()).isInstanceOf(NoInternetFailure::class.java)
        Truth.assertThat(error.failure).isInstanceOf(NoInternetFailure::class.java)
    }

    @Test
    fun `test UnknownHostException gives NoInternetFailure`() {
        val exe = UnknownHostException()
        val error: Result<Any> = exe.getError()

        Truth.assertThat(exe.getFailure()).isInstanceOf(NoInternetFailure::class.java)
        Truth.assertThat(error.failure).isInstanceOf(NoInternetFailure::class.java)
    }

    @Test
    fun `test ConnectException gives NoInternetFailure`() {
        val exe = ConnectException()
        val error: Result<Any> = exe.getError()

        Truth.assertThat(exe.getFailure()).isInstanceOf(NoInternetFailure::class.java)
        Truth.assertThat(error.failure).isInstanceOf(NoInternetFailure::class.java)
    }

    @Test
    fun `test SocketTimeoutException gives NoInternetFailure`() {
        val exe = SocketTimeoutException()
        val error: Result<Any> = exe.getError()

        Truth.assertThat(exe.getFailure()).isInstanceOf(TimeOutFailure::class.java)
        Truth.assertThat(error.failure).isInstanceOf(TimeOutFailure::class.java)
    }
}
