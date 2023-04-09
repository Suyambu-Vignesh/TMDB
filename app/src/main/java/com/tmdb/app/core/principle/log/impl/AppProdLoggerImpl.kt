package com.tmdb.app.core.principle.log.impl

import com.tmdb.app.core.principle.log.api.AppLogger

internal class AppProdLoggerImpl : AppLogger {
    override fun debug(tag: String, message: String, exe: Throwable?) {
    }

    override fun error(tag: String, message: String, exe: Throwable?) {
    }

    override fun warning(tag: String, message: String, exe: Throwable?) {
    }

    override fun info(tag: String, message: String, exe: Throwable?) {
    }

    override fun verbose(tag: String, message: String, exe: Throwable?) {
    }
}
