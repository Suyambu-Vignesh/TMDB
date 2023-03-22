package com.tmdb.app.core.principle

import com.tmdb.app.core.principle.usecase.Failure
import com.tmdb.app.core.principle.usecase.GenericFailure
import com.tmdb.app.core.principle.usecase.NoInternetFailure
import com.tmdb.app.core.principle.usecase.Result.Error
import com.tmdb.app.core.principle.usecase.ServiceFailure
import com.tmdb.app.core.principle.usecase.TimeOutFailure
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException

/**
 * Helper method to get [Result.Error] from exe
 *
 * @return [Result.Error] based on exe
 */
internal fun <DataType> Exception.getError(): Error<DataType> {
    return when {
        (this.isConnectionException()) -> {
            Error<DataType>(
                NoInternetFailure(),
                throwable = this.cause
            )
        }

        (this.isTimeOutExe()) -> {
            Error<DataType>(
                TimeOutFailure(),
                throwable = this.cause
            )
        }

        (this is HttpException) -> {
            Error<DataType>(
                ServiceFailure(errorCode = this.code()),
                this.message(),
                throwable = this.cause
            )
        }

        else -> {
            Error<DataType>(
                GenericFailure(),
                throwable = this.cause
            )
        }
    }
}

/**
 * Helper method to get [Failure] from exe
 *
 * @return [Failure] based on exe
 */
internal fun Exception.getFailure(): Failure {
    return when {
        (this.isConnectionException()) -> {
            NoInternetFailure()
        }

        (this.isTimeOutExe()) -> {
            TimeOutFailure()
        }

        (this is HttpException) -> {
            ServiceFailure(errorCode = this.code())
        }

        else -> {
            GenericFailure()
        }
    }
}

/**
 * Helper method help to say if exe is No Internet Exe
 *
 * @return True if Exe if of Type No Internet
 */
private fun Exception.isConnectionException(): Boolean {
    return (this is UnknownServiceException || this is UnknownHostException || this is ConnectException)
}

/**
 * Helper method help to say if exe is TimeOut Exe
 *
 * @return True if Exe if of Type Timeout
 */
private fun Exception.isTimeOutExe(): Boolean {
    return this is SocketTimeoutException
}
