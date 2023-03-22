package com.tmdb.app.core.principle.log

import android.util.Log

object TmdbLogging : AppLogging {
    override fun debug(tag: String, message: String, exe: Throwable?) {
        Log.d(tag, message, exe)
    }

    override fun error(tag: String, message: String, exe: Throwable?) {
        Log.e(tag, message, exe)
    }

    override fun warning(tag: String, message: String, exe: Throwable?) {
        Log.w(tag, message, exe)
    }

    override fun info(tag: String, message: String, exe: Throwable?) {
        Log.i(tag, message, exe)
    }

    override fun verbose(tag: String, message: String, exe: Throwable?) {
        Log.v(tag, message, exe)
    }
}
