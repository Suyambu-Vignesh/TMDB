package com.tmdb.app.core.principle

/**
 * API for sending log output.
 */
interface AppLogging {

    /**
     * Logging Helper help to log the debug message or errors
     *
     * @param tag Identify the source of log
     * @param message Message to be logged
     * @param exe Optional. [Exception] to be logged
     */
    fun debug(tag: String, message: String, exe: Throwable? = null)

    /**
     * Logging Helper help to log the error message or errors
     *
     * @param tag Identify the source of log
     * @param message Message to be logged
     * @param exe Optional. [Exception] to be logged
     */
    fun error(tag: String, message: String, exe: Throwable? = null)

    /**
     * Logging Helper help to log the warning message or errors
     *
     * @param tag Identify the source of log
     * @param message Message to be logged
     * @param exe Optional. [Exception] to be logged
     */
    fun warning(tag: String, message: String, exe: Throwable? = null)

    /**
     * Logging Helper help to log the warning info message or errors
     *
     * @param tag Identify the source of log
     * @param message Message to be logged
     * @param exe Optional. [Exception] to be logged
     */
    fun info(tag: String, message: String, exe: Throwable? = null)

    /**
     * Logging Helper help to log the message or errors
     *
     * @param tag Identify the source of log
     * @param message Message to be logged
     * @param exe Optional. [Exception] to be logged
     */
    fun verbose(tag: String, message: String, exe: Throwable? = null)
}
