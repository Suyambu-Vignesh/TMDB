package com.tmdb.app.core.usecase

/**
 * Result which will be provided by UseCase after processing the request.
 *
 * @param data Optional argument. Holds the data result
 * @param message Optional argument. Holds the message
 * @param failure Optional argument. Holds the Failure sate.
 */
sealed class Result<DataType>(
    val data: DataType? = null,
    val message: String? = null,
    val failure: Failure? = null
) {

    /**
     * Represent the Success Response.
     *
     * @param data The successful response data
     */
    class Response<DataType>(data: DataType) : Result<DataType>(data)

    /**
     * Represent the Loading response.
     *
     * @param message Will help to provide the custom message
     */
    class Loading<DataType>(message: String? = null) : Result<DataType>(null, message)

    /**
     * Represent the Error response
     *
     * @param failure the failure state which triggered this error
     * @param message Optional argument. Holds a message about the error.
     * @param data Optional argument. Holds the API response
     * @param throwable Optional argument. Exception in-case of [GenericFailure]
     */
    class Error<DataType>(
        failure: Failure,
        message: String? = null,
        data: DataType? = null,
        val throwable: Throwable? = null
    ) : Result<DataType>(data = data, message = message, failure = failure)
}

class PageLoadException(
    val failure: Failure,
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception(message, cause)
