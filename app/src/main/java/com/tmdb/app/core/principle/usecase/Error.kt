package com.tmdb.app.core.principle.usecase

/**
 * Abstract representation of Failure State
 */
sealed interface Failure

/**
 * Failure State which happened because of Network
 */
sealed class NetworkFailure() : Failure

/**
 * Failure State thrown when there is no internet.
 */
class TimeOutFailure : NetworkFailure()

/**
 * Failure State thrown when customer network is bad or Response not return form API
 */
class NoInternetFailure : NetworkFailure()

/**
 * Failure happened because of API service side issue.
 *
 * @param errorCode HTTP status code return by the service
 */
class ServiceFailure(val errorCode: Int) : Failure

/**
 * Failure happened because of Exception or un-handled error.
 */
class GenericFailure : Failure


