package com.tmdb.app.core.principle.log

import com.tmdb.app.core.principle.log.api.AppLogger
import javax.inject.Inject

object TmdbLogging : TmdbLoggingBase() {

    fun debug(tag: String, message: String, exe: Throwable? = null) {
        logger.debug(tag, message, exe)
    }

    fun error(tag: String, message: String, exe: Throwable? = null) {
        logger.error(tag, message, exe)
    }

    fun warning(tag: String, message: String, exe: Throwable? = null) {
        logger.warning(tag, message, exe)
    }

    fun info(tag: String, message: String, exe: Throwable? = null) {
        logger.info(tag, message, exe)
    }

    fun verbose(tag: String, message: String, exe: Throwable? = null) {
        logger.verbose(tag, message, exe)
    }
}

open class TmdbLoggingBase {
    @Inject
    lateinit var logger: AppLogger
}
