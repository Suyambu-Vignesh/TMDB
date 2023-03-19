package com.tmdb.app.core.usecase.api

import kotlinx.coroutines.flow.Flow

/**
 * Core-Use case. Helps to perform a Task and give back the result of the task.
 *
 * Place to do the business logic
 */
interface UseCase<ResultType, InputType> {

    /**
     * Will help to perform the Task and Return the [Result] which the customer is looking for
     */
    suspend fun performTask(input: InputType): Flow<ResultType>
}